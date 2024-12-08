package com.smartdevices.petpal.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.petpal.R
import com.smartdevices.petpal.db.Pet


@Composable
fun DetailsScreen(pet: Pet) {
    val context = LocalContext.current
    var visible by remember { mutableStateOf(false) }

    // Trigger visibility change after initial composition (for animation)
    LaunchedEffect(Unit) {
        visible = true
    }

    Column(
        modifier = Modifier
            .background(color = colorResource(id = R.color.bg))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.bg))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Details",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.outline_info_32),
                    contentDescription = "Edit",
                    modifier = Modifier.padding(16.dp)
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Adjusted spacing here
            ) {
                // Passing visible state to each DetailRow for animation
                DetailRow(pet, "name", "Name:", visible)
                DetailRow(pet, "species", "Species:", visible)
                DetailRow(pet, "breed", "Breed:", visible)
                DetailRow(pet, "birthday", "Birthday:", visible)
            }
        }
    }
}

@Composable
fun DetailRow(pet: Pet, petInfo: String, title: String, visibleState: Boolean) {

    // Using AnimatedVisibility to apply the pop-in effect based on visibility state
    AnimatedVisibility(
        visible = visibleState, // Controlled by the parent state
        enter = scaleIn(
            initialScale = 0.1f, // Start smaller
            animationSpec = tween(durationMillis = 1000) // Customize the duration for the animation
        ),
        exit = scaleOut(
            targetScale = 0.8f, // Scale down when exiting
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.wrapContentWidth().padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.ll_blue))
            ) {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Card(
                modifier = Modifier.weight(1f).padding(4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lll_blue))
            ) {
                Text(
                    text = when (petInfo) {
                        "name" -> pet.name
                        "birthday" -> pet.birthDate.replace("-", ".")
                        "species" -> pet.species
                        "breed" -> pet.breed
                        else -> ""
                    },
                    fontSize = 20.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

data class Pet(val name: String, val species: String, val breed: String, val birthDate: String)





//@Preview(showBackground = true)
//@Composable
//fun DetailsScreenPreview() {
//    val navController = rememberNavController()
//    JetpackComposeTestTheme(preferenceManager = PreferenceManager(LocalContext.current)) {
//        DetailsScreen(navController = navController, pet = Pet(0, "Buddy", "Dog", "Golden Retriever", "2021-01-01", medicalInfo = com.smartdevices.petpal.db.MedicalInfo(listOf("Peanuts","Banana"), "Grain-free", "10")))
//    }
//}

