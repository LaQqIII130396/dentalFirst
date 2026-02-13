package com.tomtom.dentalfirst

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tomtom.dentalfirst.ui.catalog.CatalogScreen
import com.tomtom.dentalfirst.ui.details.ProductDetailsScreen
import com.tomtom.dentalfirst.ui.theme.DentalFirstTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DentalFirstTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "catalog",
                        modifier = Modifier.fillMaxSize()
                    ) {
                        composable("catalog") {
                            CatalogScreen(
                                modifier = Modifier.fillMaxSize(), onItemClick = { productId ->
                                    navController.navigate("details/$productId")
                                })
                        }
                        composable(
                            route = "details/{productId}", arguments = listOf(
                                navArgument("productId") {
                                    type = NavType.StringType
                                })
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId")
                                ?: return@composable
                            ProductDetailsScreen(
                                productId = productId, onBackClick = {
                                    navController.navigateUp()
                                })
                        }
                    }
                }
            }
        }
    }
}