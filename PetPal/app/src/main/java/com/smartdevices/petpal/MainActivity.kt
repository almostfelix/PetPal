package com.smartdevices.petpal

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartdevices.petpal.db.PetViewModel
import com.smartdevices.petpal.ui.theme.JetpackComposeTestTheme
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.petpal.R
import com.smartdevices.petpal.db.PetViewModelFactory
import com.smartdevices.petpal.tools.PreferenceManager
import com.smartdevices.petpal.ui.AddNewPetScreen
import com.smartdevices.petpal.ui.MainScreen
import com.smartdevices.petpal.ui.PetUi
import com.smartdevices.petpal.ui.settings.SettingsScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var petViewModel: PetViewModel
    private var keepSplashScreen = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashscreen = installSplashScreen()
        splashscreen.setKeepOnScreenCondition { keepSplashScreen.value }
        val splashScreenView = findViewById<View>(android.R.id.content)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        splashScreenView.startAnimation(fadeInAnimation)

        // Pass the factory with context
        val factory = PetViewModelFactory(applicationContext)
        petViewModel = ViewModelProvider(this, factory)[PetViewModel::class.java]

        val preferenceManager = PreferenceManager(applicationContext)

        lifecycleScope.launch {
            delay(100)
            keepSplashScreen.value = false
        }

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
                var startDestination = "main_screen"
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

                    composable("pet_ui_screen/{petId}") { backStackEntry ->
                        val petId = backStackEntry.arguments?.getString("petId")?.toInt()
                        Log.d("Debug", "Pet: $petId")
                        petId?.let {
                            PetUi(
                                navController = navController,
                                petId = petId,
                                petViewModel = petViewModel
                            )
                        }
                    }

                }
            }
        }
    }

    /*
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
    }*/
}

