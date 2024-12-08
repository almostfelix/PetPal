package com.smartdevices.petpal.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petpal.R
import com.smartdevices.petpal.db.Event
import com.smartdevices.petpal.db.Pet
import com.smartdevices.petpal.db.PetViewModel
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

@Composable
fun MedicalScreen(pet: Pet, events: List<Event>, petViewModel: PetViewModel) {
    val medicalInfo = pet.medicalInfo
    val hazeState = remember { HazeState() }
    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg))
    ) {

        Spacer(modifier = Modifier.size(16.dp))
        Log.d("PetPal", "MedicalScreen: allergies=${pet.medicalInfo.allergies}")
        if (medicalInfo.allergies.isNotEmpty() || medicalInfo.diet.isNotEmpty() || medicalInfo.weight.isNotEmpty()) {
            Log.d("PetPal", "MedicalScreen: allergies2=${medicalInfo.allergies}")
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.bg)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = colorResource(id = R.color.bg))
                ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Medical information",
                            color = colorResource(id = R.color.black_icon),
                            fontSize = 18.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.outline_medical_services_32),
                            contentDescription = "Edit",
                            tint = colorResource(id = R.color.black_icon),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    ) {

                        if (medicalInfo.allergies.isNotEmpty()) {
                            // Allergies Title
                            Text(
                                text = "Allergies",
                                color = colorResource(id = R.color.black_icon),
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )

                            // Allergies Grid
                            val allergies = medicalInfo.allergies
                            val allergiesPerRow = 3

                            // Grid-like layout with fixed rows
                            for (i in allergies.indices step allergiesPerRow) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)  // Spaced arrangement between cards
                                ) {
                                    for (j in 0 until allergiesPerRow) {
                                        val index = i + j
                                        if (index < allergies.size) {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth() // Make sure each card takes up the full width of the row
                                                    .weight(1f) // Ensures the cards expand if there are fewer than the max per row
                                                    .padding(horizontal = 4.dp)
                                                    .defaultMinSize(minHeight = 80.dp),
                                                elevation = CardDefaults.cardElevation(8.dp),
                                                colors = CardDefaults.cardColors(
                                                    containerColor = colorResource(id = R.color.llll_blue)
                                                )

                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .padding(8.dp)
                                                        .fillMaxWidth()
                                                        .defaultMinSize(minHeight = 80.dp)
                                                ) {
                                                    Text(
                                                        text = allergies[index],
                                                        color = colorResource(id = R.color.black_icon),
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.align(Alignment.Center),
                                                        fontSize = 16.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (medicalInfo.diet.isNotEmpty()) {
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Diet",
                                color = colorResource(id = R.color.black_icon),
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth() // Make sure each card takes up the full width of the row
                                    .padding(horizontal = 4.dp)
                                    .defaultMinSize(minHeight = 80.dp),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.llll_blue)
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 80.dp)
                                ) {
                                    Text(
                                        text = medicalInfo.diet,
                                        color = colorResource(id = R.color.black_icon),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                        if (medicalInfo.weight.isNotEmpty()) {
                            Spacer(modifier = Modifier.size(16.dp))
                            Text(
                                text = "Weight",
                                color = colorResource(id = R.color.black_icon),
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth() // Make sure each card takes up the full width of the row
                                    .padding(horizontal = 4.dp)
                                    .defaultMinSize(minHeight = 80.dp),
                                elevation = CardDefaults.cardElevation(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.llll_blue)
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .defaultMinSize(minHeight = 80.dp)
                                ) {
                                    Text(
                                        text = medicalInfo.weight,
                                        color = colorResource(id = R.color.black_icon),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        EventCard(events = events, currentPet = pet, petViewModel = petViewModel)

    }
}