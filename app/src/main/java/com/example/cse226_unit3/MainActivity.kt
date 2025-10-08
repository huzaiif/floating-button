package com.example.cse226_unit3

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class MainActivity : AppCompatActivity() {
    private var mService: MusicService? = null
    private var mBound = false
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as MusicService.LocalBinder
            mService = binder.getService()
            mBound = true
            Toast.makeText(this@MainActivity, "Service Bound",
                Toast.LENGTH_SHORT).show()
        }
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnBind = findViewById<Button>(R.id.btnBindService)
        val btnUnbind = findViewById<Button>(R.id.btnUnbindService)
        val btnPlay = findViewById<Button>(R.id.btnPlay)
        val btnPause = findViewById<Button>(R.id.btnPause)
        btnBind.setOnClickListener {
            Intent(this, MusicService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }
        }
        btnUnbind.setOnClickListener {
            if (mBound) {
                unbindService(connection)
                mBound = false
                Toast.makeText(this, "Service Unbound", Toast.LENGTH_SHORT).show()
            }
        }
        btnPlay.setOnClickListener {
            if (mBound) mService?.playMusic()
            else Toast.makeText(this, "Bind the service first!",
                Toast.LENGTH_SHORT).show()
        }
        btnPause.setOnClickListener {
            if (mBound) mService?.pauseMusic()
            else Toast.makeText(this, "Bind the service first!", Toast.LENGTH_SHORT).show()
        }
    }
}