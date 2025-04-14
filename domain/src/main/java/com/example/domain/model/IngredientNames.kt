package com.example.domain.model


data class IngredientNames(
    val ingredientNames: List<IngredientName>
)
data class IngredientName(
    val name: String,
    val color : String
)

data class CusineNames(
    val cusineNames: List<CusineName>
)
data class CusineName(
    val name:String,
    val color : String
)