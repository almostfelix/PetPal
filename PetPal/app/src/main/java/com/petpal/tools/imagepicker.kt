package com.petpal.tools

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.petpal.tools.ImageUtils

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

