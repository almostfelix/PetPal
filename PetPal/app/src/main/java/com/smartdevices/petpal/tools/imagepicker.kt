package com.smartdevices.petpal.tools

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun ImagePickerLauncher(
    context: Context,
    onImageSelected: (imageName: String?) -> Unit
) {
    // Create the activity result launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                // Handle the selected image URI and extract its name
                val imageName = uri.lastPathSegment ?: "default_image.jpg"
                onImageSelected(imageName)
            } else {
                // Handle the case where no image was selected
                Log.d("ImagePicker", "No image selected")
                onImageSelected(null)
            }
        }
    )

    // Function to launch the image picker
    fun launchImagePicker() {
        imagePickerLauncher.launch(PickVisualMediaRequest())
    }

    // You can invoke this function whenever necessary
    launchImagePicker()
}

