package com.example.planerwyprawy

import android.content.IntentSender
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RadioButton
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
        val wybranaRasa = findViewById<Spinner>(R.id.spinner)
        val dzien = findViewById<DatePicker>(R.id.dzien)
        val godzina = findViewById<TimePicker>(R.id.godzina)

        val checkbox1 = findViewById<CheckBox>(R.id.cb1)
        val checkbox2 = findViewById<CheckBox>(R.id.cb2)
        val checkbox3 = findViewById<CheckBox>(R.id.cb3)


        var selectedText = ""
        if (checkbox1.isChecked) selectedText += "${checkbox1.text} "
        if (checkbox2.isChecked) selectedText += "${checkbox2.text} "
        if (checkbox3.isChecked) selectedText += "${checkbox3.text} "

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)


        val ocena = findViewById<RatingBar>(R.id.ocena)

        val przycisk = findViewById<Button>(R.id.klik)
        val podsumowanie = findViewById<TextView>(R.id.podsumowanie)


        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val countdownBT = findViewById<Button>(R.id.countdownBT)
        var countDownTimer: CountDownTimer? = null
        val czasOdliczanie = 60
        progressBar.max = czasOdliczanie
        countdownBT.setOnClickListener {
            progressBar.progress = 0
            countDownTimer?.cancel()

            countDownTimer = object : CountDownTimer(czasOdliczanie * 1000L, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val progress = ((czasOdliczanie * 1000L - millisUntilFinished) / 1000).toInt()
                    progressBar.progress = progress
                }

                override fun onFinish() {
                    progressBar.progress = czasOdliczanie
                    Toast.makeText(this@MainActivity, "Czas na wymarsz!", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }


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


        var running = false;
        var pauseOffset: Long = 0
        val chronometer: Chronometer = findViewById(R.id.myChronometer)

        chronometer.base = SystemClock.elapsedRealtime()
        val start = findViewById<Button>(R.id.startBT)
        val stop = findViewById<Button>(R.id.stopBT)
        start.setOnClickListener {
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
            chronometer.start()
            running = true
        }
        stop.setOnClickListener{
            chronometer.stop()
        }

        val imieEdit = findViewById<EditText>(R.id.imieEdit)

        przycisk.setOnClickListener {

            val imieText = imieEdit.text.toString()

            val rasaText = wybranaRasa.selectedItem.toString()

            val selectedCheckBoxes = mutableListOf<String>()
            if (checkbox1.isChecked) selectedCheckBoxes.add(checkbox1.text.toString())
            if (checkbox2.isChecked) selectedCheckBoxes.add(checkbox2.text.toString())
            if (checkbox3.isChecked) selectedCheckBoxes.add(checkbox3.text.toString())
            val wyposazenie = selectedCheckBoxes.joinToString(", ")

            val priorytetRadio = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val priorytet = priorytetRadio?.text ?: "Nie wybrano"

            val czasMarszuSek = (SystemClock.elapsedRealtime() - chronometer.base) / 1000

            val day = dzien.dayOfMonth
            val month = dzien.month + 1
            val year = dzien.year
            val hour = godzina.hour
            val minute = godzina.minute

            val morale = ocena.rating

            podsumowanie.text =
                "Bohater: $imieText ($rasaText)\n" +
                        "Priorytet: $priorytet\n" +
                        "Wyposażenie: $wyposazenie\n" +
                        "Czas marszu: $czasMarszuSek s\n" +
                        "Morale: $morale/5\n" +
                        "Termin: $day/$month/$year o $hour:$minute"
        }

    }
}