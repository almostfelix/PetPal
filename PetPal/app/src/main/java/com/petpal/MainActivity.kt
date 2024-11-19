package com.petpal

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.petpal.db.Pet
import com.petpal.db.PetViewModel
import com.petpal.ui.theme.JetpackComposeTestTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.shadow
import androidx.lifecycle.ViewModelProvider
import com.petpal.db.PetViewModelFactory
import com.petpal.login.LoginActivity
import com.petpal.tools.PreferenceManager
import com.petpal.ui.LoginScreen
import com.petpal.ui.MainScreen
import com.petpal.ui.MainScreenBody
import com.petpal.ui.PetUi
import com.petpal.ui.TopAppBarMainScreen
import com.petpal.ui.settings.AccountSettings
import com.petpal.ui.settings.ApperearanceSettings
import com.petpal.ui.settings.NotifSettings
import com.petpal.ui.settings.PrivacySettings

class MainActivity : ComponentActivity() {

    private lateinit var petViewModel: PetViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Pass the factory with context
        val factory = PetViewModelFactory(applicationContext)
        petViewModel = ViewModelProvider(this, factory)[PetViewModel::class.java]
        petViewModel.loadPets()

        val preferenceManager = PreferenceManager(applicationContext)

        //Log.d("Debug", petViewModel.getAllPets().toString())

        //preferenceManager = PreferenceManager(this, petViewModel)
        /*
        Log.d("Debug", "onCreate")
        val newPet = Pet(
            name = "Rex",
            species = "Dog",
            breed = "Labrador",
            birthDate = "2020-05-10",
            allergies = listOf("Peanuts")
        )
        petViewModel.addPet(newPet)
        */

        //var pets = petViewModel.fetchPets()
        //Log.d("Debug", "Pets: $pets")

        setContent {
            JetpackComposeTestTheme {
                // Set up NavController
                val navController = rememberNavController()
                // Determine start destination based on saved preference
                val startDestination = "main_screen"
                NavHost(
                    navController = navController,
                    startDestination = startDestination,
                    enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Start,
                            tween(700)
                        )
                    },
                    popEnterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    },
                    popExitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.End,
                            tween(700)
                        )
                    }
                ) {
                    composable("main_screen") {
                        MainScreen(
                            navController = navController,
                            viewModel = petViewModel
                        )
                    }
                    composable("settings_screen") {
                        SettingsScreen(navController = navController)
                    }
                    composable("add_pet_screen") {
                        AddNewPetScreen(
                            navController = navController,
                            petViewModel = petViewModel
                        )
                    }
                    composable("settings/account_settings") {
                        AccountSettings(navController = navController)

                    }
                    composable("settings/appear_settings") {
                        ApperearanceSettings(navController = navController)
                    }
                    composable("settings/notif_settings") {
                        NotifSettings(navController = navController)
                    }
                    composable("settings/privacy_settings") {
                        PrivacySettings(navController = navController)
                    }
                    composable("pet_ui_screen") {
                        Log.d("Debug", "Pets: ${petViewModel.petsList.value}")
                        petViewModel.petsList.value?.let { it1 -> PetUi(navController = navController, pet = it1[0], petViewModel = petViewModel) }
                    }

                }
            }
        }
    }

    @Preview(showBackground = true, name = "MainScreenPreview")
    @Composable
    fun MainScreenPreview() {
        // Mock data for preview
        val navController = rememberNavController()
        val pets = listOf(
            Pet(
                name = "Max",
                species = "Labrador",
                breed = "Labrador",
                birthDate = "2020-05-10",
                allergies = listOf("Peanuts")
            ),
            Pet(
                name = "Bella",
                species = "Golden Retriever",
                breed = "Retriever",
                birthDate = "2019-04-16",
                allergies = listOf("None")
            ),
        )

        JetpackComposeTestTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.bg)),
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBarMainScreen(navController = navController)
                MainScreenBody(pets = pets, navController = navController)
            }
        }
    }
}
