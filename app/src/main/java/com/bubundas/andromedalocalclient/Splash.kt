package com.bubundas.andromedalocalclient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.util.*


class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val mainApp = Intent(this, MainActivity::class.java)
        mainApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val task = object : TimerTask() {

            override fun run() {
                startActivity(mainApp)
            }
        }
        val t = Timer()
        t.schedule(task, 1000)
        val task2 = object : TimerTask() {

            override fun run() {
                finish()
            }
        }
        val t2 = Timer()
        t2.schedule(task2, 10000)
    }
}
