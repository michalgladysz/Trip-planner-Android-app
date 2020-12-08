package com.example.trip_planner_andrid_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Pair
import com.example.trip_planner_andrid_app.flights.FlightsListActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.authentication_activity.*
import kotlinx.android.synthetic.main.search_for_flights_activity.*
import java.text.SimpleDateFormat
import java.util.*


class SearchForFlightsActivity : AppCompatActivity() {

    private val formatDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val formatText = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())

    private var outboundDateString : String = ""
    private var inboundDateString : String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_for_flights_activity)

        setAutocomplete()

        button2.setOnClickListener{
            val originPlace = wylotZ.text.toString().split("-")[1].trim() + "-sky"
            val destinationPlace = przylotDo.text.toString().split("-")[1].trim() + "-sky"
            val intent = Intent(this, FlightsListActivity::class.java)

            intent.putExtra("originPlace", originPlace)
            intent.putExtra("destinationPlace", destinationPlace)
            if (two_way.isChecked) {
                intent.putExtra("inboundDateString", inboundDateString)
            }
            else {
                intent.putExtra("inboundDateString", "")
            }
            intent.putExtra("outboundDateString", outboundDateString)

            if (TextUtils.isEmpty(originPlace)) {
                wylotZ.error = "Wprowadź miejsce wylotu"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(destinationPlace)) {
                przylotDo.error = "Wprowadź miejsce przylotu"
                return@setOnClickListener
            }
            
            setIntent(intent)
            startActivity(intent)
        }

        var oneWay = true
        data_przylotu.visibility = View.INVISIBLE
        przylotText.visibility = View.INVISIBLE

        one_way.setOnClickListener {
            oneWay = true
            data_przylotu.visibility = View.INVISIBLE
            przylotText.visibility = View.INVISIBLE
        }

        two_way.setOnClickListener {
            oneWay = false
            data_przylotu.visibility = View.VISIBLE
            przylotText.visibility = View.VISIBLE
        }

        btn_datePicker.setOnClickListener {
            if (oneWay) {
                println(oneWay)
                val builder = MaterialDatePicker.Builder.datePicker()
                val constraintsBuilderRange = CalendarConstraints.Builder()
                val dateValidator = DateValidatorPointForward.now()
                constraintsBuilderRange.setValidator(dateValidator)
                builder.setCalendarConstraints(constraintsBuilderRange.build())
                builder.setTitleText("Lot w jedną stronę")
                val picker: MaterialDatePicker<Long> = builder.build()
                picker.addOnPositiveButtonClickListener { selection ->
                    outboundDateString = formatDate.format(selection)
                    data_wylotu.text = formatText.format(selection)
                }
                picker.show(supportFragmentManager, picker.toString())

            } else {
                println(oneWay)
                val builder: MaterialDatePicker.Builder<Pair<Long, Long>> = MaterialDatePicker.Builder.dateRangePicker()
                val constraintsBuilderRange = CalendarConstraints.Builder()
                val dateValidator = DateValidatorPointForward.now()
                constraintsBuilderRange.setValidator(dateValidator)
                builder.setCalendarConstraints(constraintsBuilderRange.build())
                builder.setTitleText("Lot w obie strony")
                val picker: MaterialDatePicker<Pair<Long, Long>> = builder.build()
                picker.addOnPositiveButtonClickListener { selection ->
                    val startDate = selection.first
                    val endDate = selection.second
                    outboundDateString = formatDate.format(startDate)
                    inboundDateString = formatDate.format(endDate)
                    data_wylotu.text = formatText.format(startDate)
                    data_przylotu.text = " - " + formatText.format(endDate)
                }
                picker.show(supportFragmentManager, picker.toString())
            }
        }
    }

    private fun setAutocomplete() {
        val jsonFileString = getJsonDataFromAsset(applicationContext, "data.json")
        val jsonFileString2 = getJsonDataFromAsset(applicationContext, "countries.json")

        val airports =  GsonBuilder().create().fromJson(jsonFileString, AirportGson.airportsList::class.java)
        val countries =  GsonBuilder().create().fromJson(jsonFileString2, CountriesGson.countriesList::class.java)
        val autocompleteList = ArrayList<String>()

        for(c in countries.countries) {
            autocompleteList.add(c.country + ", wszystkie lotniska - " + c.alfa_2)
        }

        for(a in airports.airports) {
            autocompleteList.add(a.city + " " + a.name + " - " + a.iata)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line, autocompleteList
        )

        val textView = findViewById<AutoCompleteTextView>(R.id.wylotZ)
        val textView2 = findViewById<AutoCompleteTextView>(R.id.przylotDo)

        textView.setAdapter(adapter)
        textView2.setAdapter(adapter)
    }

    private fun getJsonDataFromAsset(context: Context, filename: String): String? {
        return context.assets.open(filename).bufferedReader().use { it.readText() }

    }
}


