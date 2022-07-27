package com.example.audio

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private  var mediaPlayer:MediaPlayer?=null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mediaPlayer= MediaPlayer.create(this,R.raw.friends)
        seekBar=findViewById(R.id.seekBar)
        handler=Handler(Looper.getMainLooper())

        val playButton=findViewById<FloatingActionButton>(R.id.fabplay)
        playButton.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.friends)
                initializeSeekBar()
            }
            mediaPlayer?.start()
        }
        val pauseButton=findViewById<FloatingActionButton>(R.id.fabpause)
        pauseButton.setOnClickListener {
           mediaPlayer?.pause()
        }
        val stopButton=findViewById<FloatingActionButton>(R.id.fabstop)
        stopButton.setOnClickListener {
           mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer= null
            handler.removeCallbacks(runnable)
            seekBar.progress=0
        }
    }
    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
     if (fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        seekBar.max = mediaPlayer!!.duration
        val tvPlayed=findViewById<TextView>(R.id.tvPlayed)
        val tvDue=findViewById<TextView>(R.id.tvDue)
        runnable= Runnable {
            seekBar.progress=mediaPlayer!!.currentPosition
            val playedTime=mediaPlayer!!.currentPosition/1000//to convert this to seconds
            tvPlayed.text="$playedTime sec"
            val duration=mediaPlayer!!.duration/1000
            val dueTime=duration-playedTime
            tvDue.text="$dueTime sec"

            handler.postDelayed(runnable,1000)
        }
           handler.postDelayed(runnable,1000)
    }
}