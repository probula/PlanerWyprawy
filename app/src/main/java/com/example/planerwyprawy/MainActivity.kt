package com.example.planerwyprawy

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val zdjecie: ImageView = findViewById(R.id.zdjecie)

        val spinner: Spinner = findViewById(R.id.spinner)
        val rasa = arrayOf("hobbit", "człowiek", "elf", "krasnolud", "czarodziej")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rasa)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (rasa[position]) {
                    "hobbit" -> zdjecie.setImageResource(R.drawable.pierscien)
                    "elf" -> zdjecie.setImageResource(R.drawable.luk)
                    "krasnolud" -> zdjecie.setImageResource(R.drawable.miecz)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }
        }
    }
}