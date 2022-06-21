package com.example.lab4


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import android.database.Cursor
import android.database.sqlite.SQLiteException

class MainActivity : AppCompatActivity(), PlantsListFragmentListener,
    PlantDetailsFragmentListener {
    private lateinit var namesCursor: Cursor
    private lateinit var db: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment =
            supportFragmentManager.findFragmentById(R.id.plantListsFragment) as PlantsListFragment
        fragment.plantsToDisplay = plants

        //Database
        try {
            db = DatabaseHelper(this)
            namesCursor = prepareNamesCursor()
            setupSpinner()
        } catch (e: SQLiteException) {
            handleError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        namesCursor.close()
        db.close()
    }


    override fun onClickPlant(position: Int) {
        val plant = plants[position]
        val fragmentContainer = findViewById<View>(R.id.plantDetailsFragment_container)

        if (fragmentContainer != null) {
            onClickPlantWithFragment(plant)
        } else {
            onClickPlantWithoutFragment(plant)
        }
    }

    override fun getSelectedPlant(name: String): Plant {
        val error = getString(R.string.error_no_plant)
        return plants.find { it.name == name } ?: throw Error(error)
    }

    private fun onClickPlantWithoutFragment(plant: Plant) {
        val intent = Intent(baseContext, PlantDetailActivity::class.java)

        intent.putExtra(PlantDetailActivity.PLANT_NAME, plant.name)
        startActivity(intent)
    }

    private fun onClickPlantWithFragment(plant: Plant) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = PlantDetailsFragment()
        fragment.plantName = plant.name


        transaction.replace(R.id.plantDetailsFragment_container, fragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    private fun setText(text: String) {
        //val textView = findViewById<TextView>(R.id.text)
        //textView.text = text
    }

    private fun handleError() {
        val toast = Toast.makeText(this, getString(R.string.databaseError), Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun prepareNamesCursor(): Cursor {
        return db.readableDatabase.query(TABLE_NAME,
            arrayOf("_id", NAME_COL),
            null,
            null,
            null,
            null,
            "$NAME_COL ASC")
    }

    private fun prepareDescriptionCursor(name: String): Cursor {
        return db.readableDatabase.query(TABLE_NAME,
            arrayOf(DESCRIPTION_COL),
            "$NAME_COL = ?",
            arrayOf(name),
            null,
            null,
            "$NAME_COL ASC")
    }

    private fun getDescriptionByName(name: String): String {
        val cursor = prepareDescriptionCursor(name)
        var description = ""

        if (cursor.moveToFirst()) {
            description = cursor.getString(0)
        } else {
            handleError()
        }

        cursor.close()
        return description
    }

    private fun setupSpinner() {
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long,
            ) {
                println(id)
                val cursor = spinner.selectedItem as Cursor
                val selectedName = cursor.getString(1)
                val description = getDescriptionByName(selectedName)
                setText(description)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        */
    }
}