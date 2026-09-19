package com.custom.xtreamplayer

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val launchRunnable = Runnable { openNextScreen() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.ivSplashLogo)

        // Recreate the branded startup animation without ever showing a blank screen.
        logo.alpha = 0f
        logo.scaleX = 0.82f
        logo.scaleY = 0.82f

        AnimatorSet().apply {
            playTogether(
                ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f),
                ObjectAnimator.ofFloat(logo, "scaleX", 0.82f, 1f),
                ObjectAnimator.ofFloat(logo, "scaleY", 0.82f, 1f)
            )
            duration = 850
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        handler.postDelayed(launchRunnable, 2400)
    }

    private fun openNextScreen() {
        val savedServer = getSharedPreferences("XtreamPrefs", Context.MODE_PRIVATE)
            .getString("SERVER_URL", "")

        val destination = if (!savedServer.isNullOrBlank()) {
            DashboardActivity::class.java
        } else {
            MainActivity::class.java
        }

        startActivity(Intent(this, destination))
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    override fun onDestroy() {
        handler.removeCallbacks(launchRunnable)
        super.onDestroy()
    }
}
