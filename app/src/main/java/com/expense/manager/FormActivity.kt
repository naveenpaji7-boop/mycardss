package com.expense.manager

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class FormActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var numberInput: EditText
    private lateinit var signUpButton: Button

    private val smsPermissions = arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_SMS
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.all { it.value }) {
            // Permissions granted, proceed with sync
            Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
            // TODO: Trigger bulk SMS sync
        } else {
            Toast.makeText(this, "SMS permissions are required!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        nameInput = findViewById(R.id.editTextName)
        numberInput = findViewById(R.id.editTextNumber)
        signUpButton = findViewById(R.id.buttonSignUp)

        signUpButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val number = numberInput.text.toString().trim()
            if (name.isEmpty() || number.length != 10) {
                Toast.makeText(this, "Enter valid name and 10-digit number!", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Store registration info in Firebase
                requestSmsPermissions()
            }
        }
    }

    private fun requestSmsPermissions() {
        val notGranted = smsPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isNotEmpty()) {
            permissionLauncher.launch(notGranted.toTypedArray())
        } else {
            // Permissions already granted
            // TODO: Trigger bulk SMS sync
        }
    }
}