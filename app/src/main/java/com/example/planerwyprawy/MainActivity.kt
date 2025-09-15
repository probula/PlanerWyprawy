package com.example.planerwyprawy

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast


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
        val imie = findViewById<TextView>(R.id.imie)
        val wybranaRasa = findViewById<Spinner>(R.id.spinner)
        val dzien = findViewById<DatePicker>(R.id.dzien)
        val godzina = findViewById<TimePicker>(R.id.godzina)
        val switch = findViewById<Switch>(R.id.sciezki)

        val checkbox1 = findViewById<CheckBox>(R.id.cb1)
        val checkbox2 = findViewById<CheckBox>(R.id.cb2)
        val checkbox3 = findViewById<CheckBox>(R.id.cb3)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val selectedId = radioGroup.checkedRadioButtonId

        val poziom = findViewById<SeekBar>(R.id.poziom)

        val timer = findViewById<Chronometer>(R.id.myChronometer)
        val czas = timer.text.toString()

        val ocena = findViewById<RatingBar>(R.id.ocena)

        val przycisk = findViewById<Button>(R.id.klik)
        val podsumowanie = findViewById<TextView>(R.id.podsumowanie)





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
            }

        }
        val seekBar = findViewById<SeekBar>(R.id.poziom)
        val pokazSB = findViewById<TextView>(R.id.poziomPokaz)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                pokazSB.text = "Czas marszu: $progress min"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@MainActivity, "Zacząłeś przesuwać suwak", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@MainActivity, "Zakończyłeś przesuwanie suwaka", Toast.LENGTH_SHORT).show()
            }
        })


        var pokazwyjscie = findViewById<TextView>(R.id.infowyjscie)
        godzina.setOnTimeChangedListener { _, hourOfDay, minute ->
          val day = dzien.dayOfMonth
          val month = dzien.month + 1
          val year = dzien.year

          pokazwyjscie.text = "Wyruszasz: $day/$month/$year o $hourOfDay:$minute"
      }


    }
}