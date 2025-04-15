package com.example.data.datamodels

import com.example.domain.model.Equipment
import com.example.domain.model.Ingredient
import com.example.domain.model.Instruction
import com.example.domain.model.RecipeList
import com.example.domain.model.SimpleIngredient
import com.example.domain.model.Step
import com.example.domain.model.UnitMeasure
import com.google.gson.annotations.SerializedName

data class RecipesData(

    @SerializedName("recipes") val recipes: ArrayList<Recipe> = arrayListOf()
)

data class Us(

    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unitShort") var unitShort: String? = null,
    @SerializedName("unitLong") var unitLong: String? = null

)

data class Metric(

    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unitShort") var unitShort: String? = null,
    @SerializedName("unitLong") var unitLong: String? = null

)

data class Measures(

    @SerializedName("us") var us: Us? = Us(),
    @SerializedName("metric") var metric: Metric? = Metric()

)

data class ExtendedIngredients(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("aisle") var aisle: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("consistency") var consistency: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("nameClean") var nameClean: String? = null,
    @SerializedName("original") var original: String? = null,
    @SerializedName("originalName") var originalName: String? = null,
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unit") var unit: String? = null,
    @SerializedName("meta") var meta: ArrayList<String> = arrayListOf(),
    @SerializedName("measures") var measures: Measures? = Measures()

)

data class Ingredients(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("localizedName") var localizedName: String? = null,
    @SerializedName("image") var image: String? = null

)

data class Steps(

    @SerializedName("number") var number: Int? = null,
    @SerializedName("step") var step: String? = null,
    @SerializedName("ingredients") var ingredients: ArrayList<Ingredients> = arrayListOf(),
    @SerializedName("equipment") var equipment: ArrayList<EquipmentData> = arrayListOf()

)

data class EquipmentData(
    @SerializedName("id") var id:Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("localizedName") var localizedName: String?,
)

data class AnalyzedInstructions(

    @SerializedName("name") var name: String? = null,
    @SerializedName("steps") var steps: ArrayList<Steps> = arrayListOf()

)

data class Recipe(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("imageType") var imageType: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("readyInMinutes") var readyInMinutes: Int? = null,
    @SerializedName("servings") var servings: Int? = null,
    @SerializedName("sourceUrl") var sourceUrl: String? = null,
    @SerializedName("vegetarian") var vegetarian: Boolean? = null,
    @SerializedName("vegan") var vegan: Boolean? = null,
    @SerializedName("glutenFree") var glutenFree: Boolean? = null,
    @SerializedName("dairyFree") var dairyFree: Boolean? = null,
    @SerializedName("veryHealthy") var veryHealthy: Boolean? = null,
    @SerializedName("cheap") var cheap: Boolean? = null,
    @SerializedName("veryPopular") var veryPopular: Boolean? = null,
    @SerializedName("sustainable") var sustainable: Boolean? = null,
    @SerializedName("lowFodmap") var lowFodmap: Boolean? = null,
    @SerializedName("weightWatcherSmartPoints") var weightWatcherSmartPoints: Int? = null,
    @SerializedName("gaps") var gaps: String? = null,
    @SerializedName("preparationMinutes") var preparationMinutes: String? = null,
    @SerializedName("cookingMinutes") var cookingMinutes: String? = null,
    @SerializedName("aggregateLikes") var aggregateLikes: Int? = null,
    @SerializedName("healthScore") var healthScore: Int? = null,
    @SerializedName("creditsText") var creditsText: String? = null,
    @SerializedName("license") var license: String? = null,
    @SerializedName("sourceName") var sourceName: String? = null,
    @SerializedName("pricePerServing") var pricePerServing: Double? = null,
    @SerializedName("extendedIngredients") var extendedIngredients: ArrayList<ExtendedIngredients> = arrayListOf(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("cuisines") var cuisines: ArrayList<String> = arrayListOf(),
    @SerializedName("dishTypes") var dishTypes: ArrayList<String> = arrayListOf(),
    @SerializedName("diets") var diets: ArrayList<String> = arrayListOf(),
    @SerializedName("occasions") var occasions: ArrayList<String> = arrayListOf(),
    @SerializedName("instructions") var instructions: String? = null,
    @SerializedName("analyzedInstructions") var analyzedInstructions: ArrayList<AnalyzedInstructions> = arrayListOf(),
    @SerializedName("originalId") var originalId: String? = null,
    @SerializedName("spoonacularScore") var spoonacularScore: Double? = null,
    @SerializedName("spoonacularSourceUrl") var spoonacularSourceUrl: String? = null
)




// RecipesData → RecipeList
fun RecipesData.toDomain(): RecipeList {
    return RecipeList(
        recipes = recipes.map { it.toDomain() }
    )
}

// Recipe → Recipe
fun Recipe.toDomain(): com.example.domain.model.Recipe {
    return com.example.domain.model.Recipe(
        id = id ?: 0,
        image = image.orEmpty(),
        imageType = imageType.orEmpty(),
        title = title.orEmpty(),
        readyInMinutes = readyInMinutes ?: 0,
        servings = servings ?: 0,
        sourceUrl = sourceUrl.orEmpty(),
        vegetarian = vegetarian ?: false,
        vegan = vegan ?: false,
        glutenFree = glutenFree ?: false,
        dairyFree = dairyFree ?: false,
        veryHealthy = veryHealthy ?: false,
        cheap = cheap ?: false,
        veryPopular = veryPopular ?: false,
        sustainable = sustainable ?: false,
        lowFodmap = lowFodmap ?: false,
        weightWatcherSmartPoints = weightWatcherSmartPoints ?: 0,
        gaps = gaps.orEmpty(),
        preparationMinutes = preparationMinutes.orEmpty(),
        cookingMinutes = cookingMinutes.orEmpty(),
        aggregateLikes = aggregateLikes ?: 0,
        healthScore = healthScore ?: 0,
        creditsText = creditsText.orEmpty(),
        license = license.orEmpty(),
        sourceName = sourceName.orEmpty(),
        pricePerServing = pricePerServing ?: 0.0,
        ingredients = extendedIngredients.map { it.toDomain() },
        summary = summary.orEmpty(),
        cuisines = cuisines,
        dishTypes = dishTypes,
        diets = diets,
        occasions = occasions,
        instructions = instructions.orEmpty(),
        analyzedInstructions = analyzedInstructions.map { it.toDomain() },
        originalId = originalId.orEmpty(),
        spoonacularScore = spoonacularScore ?: 0.0,
        spoonacularSourceUrl = spoonacularSourceUrl.orEmpty()
    )
}

// ExtendedIngredients → Ingredient
fun ExtendedIngredients.toDomain(): Ingredient {
    return Ingredient(
        id = id ?: 0,
        aisle = aisle.orEmpty(),
        image = image.orEmpty(),
        consistency = consistency.orEmpty(),
        name = name.orEmpty(),
        nameClean = nameClean.orEmpty(),
        original = original.orEmpty(),
        originalName = originalName.orEmpty(),
        amount = amount ?: 0.0,
        unit = unit.orEmpty(),
        meta = meta,
        measures = measures?.toDomain() ?: com.example.domain.model.Measures(UnitMeasure(0.0, "", ""), UnitMeasure(0.0, "", ""))
    )
}

// Measures → Measures
fun Measures.toDomain(): com.example.domain.model.Measures {
    return com.example.domain.model.Measures(
        us = us?.toDomain() ?: UnitMeasure(0.0, "", ""),
        metric = metric?.toDomain() ?: UnitMeasure(0.0, "", "")
    )
}

// Us / Metric → UnitMeasure
fun Us.toDomain(): UnitMeasure {
    return UnitMeasure(
        amount = amount ?: 0.0,
        unitShort = unitShort.orEmpty(),
        unitLong = unitLong.orEmpty()
    )
}

fun Metric.toDomain(): UnitMeasure {
    return UnitMeasure(
        amount = amount ?: 0.0,
        unitShort = unitShort.orEmpty(),
        unitLong = unitLong.orEmpty()
    )
}

// AnalyzedInstructions → Instruction
fun AnalyzedInstructions.toDomain(): Instruction {
    return Instruction(
        name = name.orEmpty(),
        steps = steps.map { it.toDomain() }
    )
}

// Steps → Step
fun Steps.toDomain(): Step {
    return Step(
        number = number ?: 0,
        step = step.orEmpty(),
        ingredients = ingredients.map { it.toDomain() },
        equipment = equipment.map { it.toDomainModel() }
    )
}

// Ingredients → SimpleIngredient
fun Ingredients.toDomain(): SimpleIngredient {
    return SimpleIngredient(
        id = id ?: 0,
        name = name.orEmpty(),
        localizedName = localizedName.orEmpty(),
        image = image.orEmpty()
    )
}
fun EquipmentData.toDomainModel(): Equipment{
    return Equipment(
        id = this.id?:0,
        image = this.image?:"",
        name = this.name?:"",
        localizedName = this.localizedName?:""
    )
}
