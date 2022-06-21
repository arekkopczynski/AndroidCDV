package com.example.lab4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

interface PlantDetailsFragmentListener {
    fun getSelectedPlant(name: String): Plant
}

const val PLANT_NAME_KEY = "plantName"

class PlantDetailsFragment : Fragment() {
    var plantName = ""
    private lateinit var listener: PlantDetailsFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_details, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as PlantDetailsFragmentListener
    }

    override fun onStart() {
        super.onStart()

        try {
            val plant = listener.getSelectedPlant(plantName)
            fillPlantDetails(plant)
        } catch (err: Error) {
            println(err)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) {
            plantName = savedInstanceState.getString(PLANT_NAME_KEY) ?: ""
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PLANT_NAME_KEY, plantName)
    }

    //Privates
    private fun fillPlantDetails(plant: Plant) {
        val nameTextView = view?.findViewById<TextView>(R.id.plantName)
        val raceTextView = view?.findViewById<TextView>(R.id.plantRace)
        val detailsTextView = view?.findViewById<TextView>(R.id.plantDetails)

        nameTextView?.text = "Nazwa:\n" + plant.name
        raceTextView?.text = "Kategoria rośliny:\n" + plant.category.categoryName
        detailsTextView?.text = "Opis\n" + plant.description
    }
}