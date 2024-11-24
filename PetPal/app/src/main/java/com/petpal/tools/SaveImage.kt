package com.petpal.tools

import android.content.Context
import android.net.Uri
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory

object ImageUtils {

    // Save image to internal storage and return the saved file's name
    fun saveImageToInternalStorage(context: Context, imageUri: Uri): String? {
        val resolver = context.contentResolver
        val inputStream: InputStream? = resolver.openInputStream(imageUri)

        // Get the bitmap from the input stream
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

        // Create a file to save the image in internal storage
        val directory = context.filesDir // Use internal storage directory
        val fileName = "saved_image_${System.currentTimeMillis()}.jpg"
        val file = File(directory, fileName)

        // Save the bitmap to the file
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        // Close the streams
        outputStream.flush()
        outputStream.close()

        // Return the full URI path of the saved file
        return Uri.fromFile(file).toString() // Use Uri.fromFile(file) to get the URI
    }
}
