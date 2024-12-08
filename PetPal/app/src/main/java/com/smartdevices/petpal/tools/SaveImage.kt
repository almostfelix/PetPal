package com.smartdevices.petpal.tools

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

object ImageUtils {

    // Save image to internal storage and return the saved file's name
    fun saveImageToInternalStorage(context: Context, imageUri: Uri): String? {
        val resolver = context.contentResolver
        val inputStream: InputStream? = resolver.openInputStream(imageUri)

        // Get the bitmap from the input stream
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        // Get the image's EXIF orientation and adjust the bitmap
        val exifInterface = ExifInterface(resolver.openInputStream(imageUri) ?: return null)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val rotatedBitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(originalBitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(originalBitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(originalBitmap, 270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flipBitmap(originalBitmap, horizontal = true)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flipBitmap(originalBitmap, horizontal = false)
            else -> originalBitmap
        }

        // Create a file to save the image in internal storage
        val directory = context.filesDir // Use internal storage directory
        val fileName = "saved_image_${System.currentTimeMillis()}.jpg"
        val file = File(directory, fileName)

        // Save the bitmap to the file
        val outputStream = FileOutputStream(file)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

        // Close the streams
        outputStream.flush()
        outputStream.close()

        // Return the full URI path of the saved file
        return Uri.fromFile(file).toString() // Use Uri.fromFile(file) to get the URI
    }

    private fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix().apply { postRotate(angle) }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun flipBitmap(bitmap: Bitmap, horizontal: Boolean): Bitmap {
        val matrix = Matrix().apply {
            if (horizontal) postScale(-1f, 1f)
            else postScale(1f, -1f)
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

}
