package com.smartdevices.petpal.ui

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.petpal.R
import com.smartdevices.petpal.db.Media
import com.smartdevices.petpal.db.PetViewModel
import com.smartdevices.petpal.tools.ImageUtils
import kotlin.math.min
import kotlin.math.pow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Memories(mediaList: List<Media>, petViewModel: PetViewModel, petId: Int) {
    Log.d("Memories", "Media list: $mediaList")
    val context = LocalContext.current

    // State to track if selection mode is active and selected items
    val isSelectionMode = remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Media>() }
    // Automatically turn off selection mode if no items are selected
    if (selectedItems.isEmpty() && isSelectionMode.value) {
        isSelectionMode.value = false
    }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { result: List<Uri>? ->
        result?.let {
            if (it.isNotEmpty()) {
                Log.d("Image Selected", "Selected Images: ${it.joinToString()}")
                // Save all the images
                val mediaList = mutableListOf<Media>()
                it.forEach { uri ->
                    val img = ImageUtils.saveImageToInternalStorage(context = context, imageUri = uri) ?: ""
                    mediaList.add(Media(url = img))
                    Log.d("Image Selected", "Saved image: $img")
                }
                petViewModel.addMediasToPet(petId, mediaList)
            } else {
                Log.d("Image Selected", "No image selected.")
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val itemsPerRow = 3
        val rows = mediaList.chunked(itemsPerRow)

        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            rows.forEach { rowItems ->
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        rowItems.forEach { media ->
                            // Detect long press
                            val isSelected = selectedItems.contains(media)
                            val borderWidth by animateDpAsState(
                                targetValue = if (isSelected) 4.dp else 0.dp, // Animate to a wider border when selected and back to 0 when deselected
                                animationSpec = tween(durationMillis = 300)
                            )

                            val borderColor = if (borderWidth == 0.dp) Color.Transparent else colorResource(id = R.color.prim)

                            // Card
                            Card(
                                modifier = Modifier
                                    .size(100.dp)
                                    .border(
                                        borderWidth,
                                        borderColor,
                                        RoundedCornerShape(12.dp)
                                    ),
                                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg_shadow)),
                            ) {
                                Log.d("Memories", "Loading image: ${media.url}")
                                val painter = rememberAsyncImagePainter(media.url)
                                val state = painter.state

                                val transition by animateFloatAsState(
                                    targetValue = if (state is AsyncImagePainter.State.Success) 1f else if (state is AsyncImagePainter.State.Loading) 0f else 1f,
                                    animationSpec = tween(durationMillis = 300)
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "custom transition based on painter state",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .scale(.2f + (.8f * transition))
                                        .alpha(min(1f, transition / .2f))
                                        .combinedClickable(
                                            onLongClick = {
                                                if (!isSelectionMode.value) {
                                                    isSelectionMode.value = true
                                                }
                                                if (isSelected) {
                                                    selectedItems.remove(media)
                                                } else {
                                                    selectedItems.add(media)
                                                }
                                            },
                                            onClick = {
                                                if (isSelectionMode.value) {
                                                    if (isSelected) {
                                                        selectedItems.remove(media)
                                                    } else {
                                                        selectedItems.add(media)
                                                    }
                                                }
                                            }
                                        )
                                )
                            }
                        }
                        if (rowItems.size < itemsPerRow) {
                            repeat(itemsPerRow - rowItems.size) {
                                Spacer(modifier = Modifier.size(100.dp))
                            }
                        }
                    }
                }
            }
        }

        // Delete button with bounce animation
        val scale by animateFloatAsState(
            targetValue = if (isSelectionMode.value && selectedItems.isNotEmpty()) 1.1f else 1f,
            animationSpec = tween(
                durationMillis = 300, // Duration for the bounce
                easing = { fraction -> 1 - (1 - fraction).pow(2f) } // Easing function for a bounce effect
            )
        )

        if (isSelectionMode.value && selectedItems.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 16.dp, vertical = 32.dp)
            ) {
                Card(
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .scale(scale), // Apply the bounce scale here
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.ll_blue)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 16.dp,
                        pressedElevation = 0.dp
                    ),
                    shape = RoundedCornerShape(25.dp),
                    onClick = {
                        // Delete selected items
                        petViewModel.deleteMedias(petId = petId, medias = selectedItems)
                        selectedItems.clear() // Clear selection after deletion
                        isSelectionMode.value = false // Exit selection mode
                    }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_delete_forever_32),
                            contentDescription = null,
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }

        // Add image button with bounce animation
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(64.dp)
                    .height(64.dp)
                    .scale(scale), // Apply the bounce scale here
                colors = if (isSelectionMode.value && selectedItems.isNotEmpty())
                    CardDefaults.cardColors(containerColor = colorResource(id = R.color.g_red))
                else
                    CardDefaults.cardColors(containerColor = colorResource(id = R.color.ll_blue)),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 16.dp,
                    pressedElevation = 0.dp
                ),
                shape = RoundedCornerShape(25.dp),
                onClick = {
                    if (isSelectionMode.value && selectedItems.isNotEmpty()) {
                        petViewModel.deleteMedias(petId = petId, medias = selectedItems)
                        selectedItems.clear() // Clear selection after deletion
                    } else {
                        imagePickerLauncher.launch("image/*")
                    }
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = if (isSelectionMode.value && selectedItems.isNotEmpty())
                            painterResource(R.drawable.round_delete_forever_32)
                        else
                            painterResource(R.drawable.baseline_add_32),
                        contentDescription = null,
                        tint = colorResource(id = R.color.black_icon),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}


