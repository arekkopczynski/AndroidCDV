package com.example.lab4

enum class RACES(val printableName: String)  {
    ELF("elf"),
    POTTER("Postać z Harrego Pottera"),
    SMERFY("Postać z Smerfów")
}

data class Person(val name: String, val category: CATEGORIES,  val description: String) {}