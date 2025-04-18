package com.example.data.datamodels

import com.example.domain.model.CusineName
import com.example.domain.model.CusineNames
import com.example.domain.model.IngredientName
import com.example.domain.model.IngredientNames


data class IngredientDataModelList(
    val ingredients: List<IngredientDataModel>
)
data class IngredientDataModel(
    val name: String,
    val color : String
)

fun getDummyIngredients(): IngredientDataModelList{
    val ingredients = listOf(
        IngredientDataModel(name = "Avacado", color = "#FF6F61"),
        IngredientDataModel(name = "Apple", color = "#6B5B95"),
        IngredientDataModel(name = "Artichoke", color = "#88B04B"),
        IngredientDataModel(name = "Egg", color = "#F7CAC9"),
        IngredientDataModel(name = "Basil", color = "#92A8D1"),
        IngredientDataModel(name = "Tomato", color = "#955251"),
        IngredientDataModel(name = "Grape", color = "#B565A7"),
        IngredientDataModel(name = "Almond", color = "#009B77"),
        IngredientDataModel(name = "Borocoli", color = "#DD4124"),
        IngredientDataModel(name = "Mint", color = "#45B8AC"),
        IngredientDataModel(name = "Cauliflower", color = "#EFC050"),
        IngredientDataModel(name = "Coconut", color = "#5B5EA6"),
        IngredientDataModel(name = "Chicken", color = "#9B2335"),
        IngredientDataModel(name = "Beetroot", color = "#DFCFBE"),
        IngredientDataModel(name = "Garlic", color = "#BC243C"),
        IngredientDataModel(name = "Olive Oil", color = "#C3447A"),
        IngredientDataModel(name = "Flour", color = "#98B4D4"),
        IngredientDataModel(name = "Butter", color = "#C0AB8E"),
        IngredientDataModel(name = "Corn", color = "#F4E1D2")
        )
            return IngredientDataModelList(
        ingredients = ingredients
    )
}

data class CusinesDataModelList(
    val cusines : List<CusineDataModel>
)
data class CusineDataModel(
    val name: String,
    val color: String
)

fun IngredientDataModel.toDomainModel():IngredientName{
    return IngredientName(
        name = this.name,
        color = this.color
    )
}

fun IngredientDataModelList.toDomainModel():IngredientNames{
    val ingredients = this.ingredients.map { it.toDomainModel() }
    return IngredientNames(ingredientNames = ingredients)
}

fun CusineDataModel.toDomainModel():CusineName{
    return CusineName(
        name = this.name,
        color = this.color
    )
}

fun getDummyCusines(): CusinesDataModelList{
    val cusines = listOf(
        CusineDataModel(name = "Vietnamese", color = "#FF6F61"),
        CusineDataModel(name = "American", color = "#6B5B95"),
        CusineDataModel(name = "Chinese", color = "#88B04B"),
        CusineDataModel(name = "German", color = "#F7CAC9"),
        CusineDataModel(name = "French", color = "#92A8D1"),
        CusineDataModel(name = "Korean", color = "#955251"),
        CusineDataModel(name = "Mexican", color = "#B565A7"),
        CusineDataModel(name = "Spanish", color = "#009B77"),
        CusineDataModel(name = "African", color = "#EFC050"),
    )
    return CusinesDataModelList(
        cusines = cusines
    )
}

fun CusinesDataModelList.toDomainModel():CusineNames{
    val cusines = this.cusines.map { it.toDomainModel() }
    return CusineNames(cusineNames = cusines)
}