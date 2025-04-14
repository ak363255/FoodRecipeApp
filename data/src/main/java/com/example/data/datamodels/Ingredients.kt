package com.example.data.datamodels


data class IngredientDataModelList(
    val ingredients: List<IngredientDataModel>
)
data class IngredientDataModel(
    val name: String,
    val color : String
)

fun getDummyIngredients(): IngredientDataModelList{
    val ingredients = listOf(
        IngredientDataModel(name = "Avacado", color = "#d8232a"),
        IngredientDataModel(name = "Apple", color = "#d8232a"),
        IngredientDataModel(name = "Artichoke", color = "#d8232a"),
        IngredientDataModel(name = "Egg", color = "#d8232a"),
        IngredientDataModel(name = "Basil", color = "#d8232a"),
        IngredientDataModel(name = "Tomato", color = "#d8232a"),
        IngredientDataModel(name = "Grape", color = "#d8232a"),
        IngredientDataModel(name = "Almond", color = "#d8232a"),
        IngredientDataModel(name = "Borocoli", color = "#d8232a"),
        IngredientDataModel(name = "Mint", color = "#d8232a"),
        IngredientDataModel(name = "Cauliflower", color = "#d8232a"),
        IngredientDataModel(name = "Coconut", color = "#d8232a"),
        IngredientDataModel(name = "Chicken", color = "#d8232a"),
        IngredientDataModel(name = "Beetroot", color = "#d8232a"),
        IngredientDataModel(name = "Garlic", color = "#d8232a"),
        IngredientDataModel(name = "Olive Oil", color = "#d8232a"),
        IngredientDataModel(name = "Flour", color = "#d8232a"),
        IngredientDataModel(name = "Butter", color = "#d8232a"),
        IngredientDataModel(name = "Corn", color = "#d8232a"),
        IngredientDataModel(name = "BlueBerry", color = "#d8232a"),
        IngredientDataModel(name = "Carrot", color = "#d8232a"),
        IngredientDataModel(name = "EggPlant", color = "#d8232a"),
        IngredientDataModel(name = "Acai Berries", color = "#d8232a"),
        IngredientDataModel(name = "Banana", color = "#d8232a"),
        IngredientDataModel(name = "Date", color = "#d8232a"),
        IngredientDataModel(name = "Apricot", color = "#d8232a"),
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

fun getDummyCusines(): CusinesDataModelList{
    val cusines = listOf(
        CusineDataModel(name = "Vietnamese", color = "#d8232a"),
        CusineDataModel(name = "American", color = "#d8232a"),
        CusineDataModel(name = "Chinese", color = "#d8232a"),
        CusineDataModel(name = "German", color = "#d8232a"),
        CusineDataModel(name = "French", color = "#d8232a"),
        CusineDataModel(name = "Korean", color = "#d8232a"),
        CusineDataModel(name = "Mexican", color = "#d8232a"),
        CusineDataModel(name = "Spanish", color = "#d8232a"),
        CusineDataModel(name = "African", color = "#d8232a"),
    )
    return CusinesDataModelList(
        cusines = cusines
    )
}