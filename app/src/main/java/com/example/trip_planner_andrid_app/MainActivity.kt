package com.example.trip_planner_andrid_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import com.example.trip_planner_andrid_app.flights.FlightDetails
import com.example.trip_planner_andrid_app.flights.FlightsListActivity
import com.example.trip_planner_andrid_app.flights.data.NewFlightDetails

//import com.example.trip_planner_andrid_app.flights.data.SkyscannerQuery

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button4.setOnClickListener {
            startActivity(Intent(this, SearchForFlightsActivity::class.java))
        }

        dbButton.setOnClickListener {
            startActivity(Intent(this, FirestoreConnectionActivity::class.java))
        }

        authButton.setOnClickListener {
            startActivity(Intent(this, AuthenticationActivity::class.java))

        }

        button4.setOnClickListener() {
            startActivity(Intent(this, SearchForFlightsActivity::class.java))
        }

        button6.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        button4.setOnClickListener() {
            startActivity(Intent(this, SearchForFlightsActivity::class.java))
        }

        button6.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        button2137.setOnClickListener(){
            startActivity(Intent(this, NewFlightDetails::class.java))
        }

        mapButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
