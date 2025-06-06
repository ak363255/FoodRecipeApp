package com.example.foodrecipe.ui.composables.recipeHomepage.viewmodel

import com.example.domain.model.Result
import com.example.domain.usecase.GetCuisineUsecase
import com.example.domain.usecase.GetIngredientUsecase
import com.example.domain.usecase.GetRandomRecipeUseCase
import com.example.foodrecipe.ui.base.MviViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge

class RecipeHomePageViewModel(
    private val getRandomRecipeUseCase: GetRandomRecipeUseCase,
    private val getIngredientUsecase: GetIngredientUsecase,
    private val getCusiUsecase: GetCuisineUsecase
): MviViewModel<RecipeHomePageViewEvent, RecipeHomePageViewResult, RecipeHomePageViewState, RecipeHomePageEffects>(
    RecipeHomePageViewState()
) {
    init {
        processEvent(RecipeHomePageViewEvent.GetAllRecipeHomePageContent)
    }
    override fun Flow<RecipeHomePageViewEvent>.toResult(): Flow<RecipeHomePageViewResult> {
        return merge(
            filterIsInstance<RecipeHomePageViewEvent.GetAllRecipeHomePageContent>().togetHomePageContent(),
            filterIsInstance<RecipeHomePageViewEvent.ViewAllIngredientClick>().toOpenIngredientBottomSheet(),
            filterIsInstance<RecipeHomePageViewEvent.RecipeItemClick>().detailPage()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<RecipeHomePageViewEvent.ViewAllIngredientClick>.toOpenIngredientBottomSheet(): Flow<RecipeHomePageViewResult>{
        return flatMapLatest {
            flowOf(RecipeHomePageViewResult.OnIngredientSheet(it.ingredients))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<RecipeHomePageViewEvent.RecipeItemClick>.detailPage(): Flow<RecipeHomePageViewResult>{
        return flatMapLatest {
            flowOf(RecipeHomePageViewResult.OnRecipeDetailPage(it.recipe))
        }
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<RecipeHomePageViewEvent.GetAllRecipeHomePageContent>.togetHomePageContent():Flow<RecipeHomePageViewResult>{
        return flatMapLatest {
            combine(
                getRandomRecipeUseCase(GetRandomRecipeUseCase.GetRandomRecipeUseCaseParams(tags = "")),
                getIngredientUsecase(Unit),
                getCusiUsecase(Unit)
            ){r1,r2,r3 ->
                if(r1 is Result.Error || r2 is Result.Error || r3 is Result.Error){
                    RecipeHomePageViewResult.NoResultFound
                }else{
                    RecipeHomePageViewResult.RecipeHomePageContentResult(
                        recipeList = if(r1 is Result.Success)r1.data.recipes else emptyList(),
                        ingredients = if(r2 is Result.Success)r2.data.ingredientNames else emptyList(),
                        cuisines = if(r3 is Result.Success)r3.data.cusineNames else emptyList()
                    )
                }

            }
        }
    }

    override fun RecipeHomePageViewResult.reduce(
        initialState: RecipeHomePageViewState
    ): RecipeHomePageViewState {
          return when(this){
              is RecipeHomePageViewResult.NoResultFound -> initialState.copy(hasError = true, isLoading = false)
              is RecipeHomePageViewResult.OnCuisineSearchPage -> initialState
              is RecipeHomePageViewResult.OnIngredientSearchPage -> initialState
              is RecipeHomePageViewResult.OnIngredientSheet -> initialState
              is RecipeHomePageViewResult.OnRecipeDetailPage -> initialState
              is RecipeHomePageViewResult.RecipeHomePageContentResult -> initialState.copy(
                  isLoading = false,
                  randomRecipes = this.recipeList,
                  cuisines = this.cuisines,
                  ingredients = this.ingredients
              )
          }
    }

    override fun Flow<RecipeHomePageViewResult>.toEffect(): Flow<RecipeHomePageEffects> {
        return merge(
            filterIsInstance<RecipeHomePageViewResult.OnIngredientSheet>().openIngredientBottomSheet(),
            filterIsInstance<RecipeHomePageViewResult.OnRecipeDetailPage>().openDetailPage()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<RecipeHomePageViewResult.OnIngredientSheet>.openIngredientBottomSheet():Flow<RecipeHomePageEffects>{
        return this.mapLatest {
            RecipeHomePageEffects.OpenIngredientSheet(it.ingredients)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<RecipeHomePageViewResult.OnRecipeDetailPage>.openDetailPage():Flow<RecipeHomePageEffects>{
        return this.mapLatest {
            RecipeHomePageEffects.OpenRecipeDetailPage(it.recipe)
        }

    }

}