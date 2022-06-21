package com.example.lab4

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

interface PlantsListFragmentListener {
    fun onClickPlant(position: Int)
}

class PlantsListFragment : Fragment() {
    lateinit var plantsToDisplay: Array<Plant>
    lateinit var listener: PlantsListFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plants_list, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as PlantsListFragmentListener
    }

    override fun onStart() {
        super.onStart()

        setupPlantsList()
        setupOnClickPlantListener()
    }

    private fun setupPlantsList() {
        /*
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter =
            SimpleCursorAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                namesCursor,
                arrayOf(NAME_COL),
                intArrayOf(android.R.id.text1),
                0
            )
        */



        val plantsNames = plantsToDisplay.map { it.name }
        val adapter =
            ArrayAdapter(
                requireContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                plantsNames
            )

        val list = view?.findViewById<ListView>(R.id.plantsList)
        list?.adapter = adapter

    }

    private fun setupOnClickPlantListener() {
        val list = view?.findViewById<ListView>(R.id.plantsList)

        list?.setOnItemClickListener { _, _, position, _ ->
            listener.onClickPlant(position)
        }
    }


}