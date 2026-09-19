package com.custom.xtreamplayer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Wait 2 seconds, then decide where to route the user
        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPrefs = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
            val savedServer = sharedPrefs.getString("SERVER_URL", "")

            if (!savedServer.isNullOrEmpty()) {
                // User is already logged in, go straight to the main menu
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                // No credentials found, go to the login screen
                startActivity(Intent(this, MainActivity::class.java))
            }
            
            // Destroy the splash screen so the user can't hit the 'back' button to return to it
            finish()
        }, 2000)
    }
}