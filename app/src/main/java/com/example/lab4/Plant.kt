package com.example.lab4

enum class CATEGORIES(val categoryName: String){
    DRZEWO("Drzewo"),
    KRZEW("Krzew"),
    CHWAST("Chwast pospolity")
}

data class Plant (
    val name: String,
    val catalogNumber: Int,
    val description: String,
    val category: CATEGORIES,
    val wateringFrequency: String,
    val plantPreferences: String
){}

