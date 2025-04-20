package com.example.domain.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable


data class IngredientNames(
    val ingredientNames: List<IngredientName>
)

@Serializable
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