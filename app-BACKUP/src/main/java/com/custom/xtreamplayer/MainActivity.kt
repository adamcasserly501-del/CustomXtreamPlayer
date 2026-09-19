package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check if user is already logged in
        val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
        val savedServer = sharedPrefs.getString("SERVER_URL", "")
        if (!savedServer.isNullOrEmpty()) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
            return
        }

        // Catching the input fields. If your XML uses different names, let me know!
        val etServer = findViewById<EditText>(R.id.etServer)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val server = etServer.text.toString().trim()
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (server.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save credentials
            val editor = sharedPrefs.edit()
            editor.putString("SERVER_URL", server)
            editor.putString("USERNAME", user)
            editor.putString("PASSWORD", pass)
            editor.apply()

            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}