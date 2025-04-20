package com.example.foodrecipe.ui.composables.recipeHomepage.ingredientbottomsheet.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.data.Convertor
import com.example.domain.model.IngredientName
import com.example.foodrecipe.ui.base.MviViewModel
import com.example.foodrecipe.ui.composables.foodrecipemainscreen.viewmodel.models.ViewEffect
import com.example.foodrecipe.ui.composables.recipeHomepage.RecipeHomePageRoute
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class IngredientBottomSheetViewModel(
    private val savedStateHandle: SavedStateHandle
) : MviViewModel<ViewEvent, ViewResult, ViewState, Effects>(ViewState()) {

    init {
        Log.d("CLICKED","fire event get allIngredient")
        viewModelScope.launch {
            processEvent(ViewEvent.GetAllIngredientEvent)
        }
    }

    override fun Flow<ViewEvent>.toResult(): Flow<ViewResult> {
        return merge(
            filterIsInstance<ViewEvent.GetAllIngredientEvent>().toGetAllIngredient(),
            filterIsInstance<ViewEvent.IngredientClickEvent>().goToIngredientSearchPage()
        )
    }

    override fun ViewResult.reduce(
        initialState: ViewState
    ): ViewState {
        Log.d("REDUCE","reduce called with result - ${this}")
        return when (this) {
            is ViewResult.GoToSearchScreen -> initialState
            is ViewResult.OnAllIngredient -> initialState.copy(
                ingredients = this.ingredients,
                isLoading = false,
                hasError = false
            )
            ViewResult.OnNoIngredientFound -> initialState.copy(hasError = true, isLoading = false)
        }
    }

    override fun Flow<ViewResult>.toEffect(): Flow<Effects> {
        return merge(
            filterIsInstance<ViewResult.GoToSearchScreen>().toSearchScreen()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<ViewEvent.GetAllIngredientEvent>.toGetAllIngredient(): Flow<ViewResult> {
        Log.d("CLICKED","view event to result get all ingredient")
        return flatMapLatest {
            try {
                val allIngredients =
                    savedStateHandle.toRoute<RecipeHomePageRoute.IngredientBottomSheet>().ingredientNames
                if (allIngredients.isNotEmpty()) {
                    val  data = Convertor.convertJsonToData<ArrayList<IngredientName>>(allIngredients)
                    if(data != null && data.isNotEmpty()){
                        Log.d("CLICKED","ingredients ${data.size}")
                        flowOf(ViewResult.OnAllIngredient(data))
                    }else{
                        flowOf(ViewResult.OnNoIngredientFound)
                    }
                } else {
                    flowOf(ViewResult.OnNoIngredientFound)
                }
            }catch (e: Exception){
                e.printStackTrace()
                flowOf(ViewResult.OnNoIngredientFound)
            }

        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<ViewEvent.IngredientClickEvent>.goToIngredientSearchPage(): Flow<ViewResult.GoToSearchScreen> {
        return flatMapLatest {
            flowOf(ViewResult.GoToSearchScreen(it.name))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun Flow<ViewResult.GoToSearchScreen>.toSearchScreen(): Flow<Effects> {
        return flatMapLatest {
            val ingredientName = it.ingredientName
            flowOf(Effects.OpenIngredientSearchPage(ingredientName))
        }
    }

}