package com.meow.meowsic.backgroundTask

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide

class ColorPaletteFromImage(val context: Context?, val paletteCallback: PaletteCallback) : AsyncTask<String, Void, Palette>() {

    private lateinit var bitmap: Bitmap

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: String?): Palette? {
        val url = params[0]
        try {
            if (context != null) bitmap = Glide.with(context).asBitmap().load(url).into(640, 640).get()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Palette.from(bitmap).generate()
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(result: Palette?) {
        super.onPostExecute(result)

        paletteCallback.onPostExecute(result)
    }

    interface PaletteCallback {
        fun onPostExecute(palette: Palette?)
    }
}