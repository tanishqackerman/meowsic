package com.meow.meowsic.activities

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.meow.meowsic.R
import com.meow.meowsic.databinding.ActivityMainBinding
import com.meow.meowsic.databinding.ActivitySongPlayerBinding

class SongPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySongPlayerBinding

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Meowsic)
        binding = ActivitySongPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.blurBg.setRenderEffect(RenderEffect.createBlurEffect(10F, 10F, Shader.TileMode.MIRROR))
    }
}