package com.example.cse226_unit3

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
class MusicService: Service() {
    private val binder = LocalBinder()
    private var mediaPlayer: MediaPlayer? = null
    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }
    override fun onBind(intent: Intent): IBinder {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sample)
            mediaPlayer?.isLooping = true
        }
        return binder
    }
    fun playMusic() {
        mediaPlayer?.start()
    }
    fun pauseMusic() {
        mediaPlayer?.pause()
    }
    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}