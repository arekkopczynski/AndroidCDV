package com.example.lab4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Error

class PlantDetailActivity : AppCompatActivity(), PlantDetailsFragmentListener {
    companion object {
        const val PLANT_NAME = "plantName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_detail)

        val plantName = intent.getStringExtra(PLANT_NAME)
        val fragment =
            supportFragmentManager.findFragmentById(R.id.plantDetailsFragment) as PlantDetailsFragment

        if (plantName != null) {
            fragment.plantName = plantName
        }
    }

    override fun getSelectedPlant(name: String): Plant {
        val error = getString(R.string.error_no_plant)
        return plants.find { it.name == name } ?: throw Error(error)
    }
}