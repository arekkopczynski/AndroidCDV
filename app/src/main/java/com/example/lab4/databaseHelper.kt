package com.example.lab4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DATABASE_NAME = "plantsList"
const val DATABASE_VERSION = 1

const val TABLE_NAME = "PLANT"
const val NAME_COL = "NAME"
const val CATALOG_NUMBER_COL = "CATALOG_NUMBER"
const val DESCRIPTION_COL = "DESCRIPTION"
const val CATEGORY_COL = "CATEGORY"
const val WATERING_FREQUENCY_COL = "WATERING_FREQUENCY"
const val PLANT_PREFERENCES_COL = "PLANT_PREFERENCES"

val INIT_PLANTS = plants;

class DatabaseHelper(private val context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        createDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 1) {
            createDatabase(db)
        }
    }

    private fun insertOnePlant(db: SQLiteDatabase, plant: Plant) {
        val plantValues = ContentValues()
        plantValues.put(NAME_COL, plant.name)
        plantValues.put(CATALOG_NUMBER_COL, plant.catalogNumber)
        plantValues.put(DESCRIPTION_COL, plant.description)
        plantValues.put(CATEGORY_COL, "plant.category")
        plantValues.put(WATERING_FREQUENCY_COL, plant.wateringFrequency)
        plantValues.put(PLANT_PREFERENCES_COL, plant.plantPreferences)
        db.insert(TABLE_NAME, null, plantValues)
    }

    fun updateDescriptionById(db: SQLiteDatabase, id: Int,) {
        db.delete(TABLE_NAME,
            "_id = ?",
            arrayOf(id.toString()))
    }

    fun removePlantById(db: SQLiteDatabase, name: String, description: String) {
        val values = ContentValues()
        values.put(DESCRIPTION_COL, description)

        db.update(TABLE_NAME,
            values,
            "NAME_COL = ?",
            arrayOf(name))
    }

    private fun createDatabase(db: SQLiteDatabase?) {
        if (db != null) {
            val createTableSql =
                "CREATE TABLE PLANT (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "NAME TEXT, CATALOG_NUMBER INT, DESCRIPTION TEXT, CATEGORY TEXT, " +
                        "WATERING_FREQUENCY TEXT, PLANT_PREFERENCES_COL TEXT)"
            db.execSQL(createTableSql)
            INIT_PLANTS.forEach { insertOnePlant(db, it) }
        }
    }
}
