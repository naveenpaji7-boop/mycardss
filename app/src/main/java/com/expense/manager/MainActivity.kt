package com.expense.manager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start the foreground service for continuous operation
        val serviceIntent = Intent(this, SyncService::class.java)
        startForegroundService(serviceIntent)

        // Splash: Move to registration form after a short delay
        window.decorView.postDelayed({
            startActivity(Intent(this, FormActivity::class.java))
            finish()
        }, 2000)
    }
}