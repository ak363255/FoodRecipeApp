package com.example.domain.model

// Domain model for the entire list of recipes
data class RecipeList(
    val recipes: List<Recipe>
)

data class Recipe(
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Int,
    val gaps: String,
    val preparationMinutes: String,
    val cookingMinutes: String,
    val aggregateLikes: Int,
    val healthScore: Int,
    val creditsText: String,
    val license: String,
    val sourceName: String,
    val pricePerServing: Double,
    val ingredients: List<Ingredient>,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val instructions: String,
    val analyzedInstructions: List<Instruction>,
    val originalId: String,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String
)

data class Ingredient(
    val id: Int,
    val aisle: String,
    val image: String,
    val consistency: String,
    val name: String,
    val nameClean: String,
    val original: String,
    val originalName: String,
    val amount: Double,
    val unit: String,
    val meta: List<String>,
    val measures: Measures,
)

data class Measures(
    val us: UnitMeasure,
    val metric: UnitMeasure
)

data class UnitMeasure(
    val amount: Double,
    val unitShort: String,
    val unitLong: String
)

data class Instruction(
    val name: String,
    val steps: List<Step>
)

data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<SimpleIngredient>,
    val equipment: List<Equipment>
)
data class Equipment(
     val id:Int,
    val name: String,
     val image: String,
    val localizedName: String,
)
data class SimpleIngredient(
    val id: Int,
    val name: String,
    val localizedName: String,
    val image: String
)
