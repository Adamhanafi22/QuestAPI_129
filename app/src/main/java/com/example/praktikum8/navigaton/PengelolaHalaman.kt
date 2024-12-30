package com.example.praktikum8.navigaton

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktikum8.ui.home.screen.DestinasiEntry
import com.example.praktikum8.ui.home.screen.DestinasiHome
import com.example.praktikum8.ui.home.screen.DetailMahasiswaview
import com.example.praktikum8.ui.home.screen.EntryMhsScreen
import com.example.praktikum8.ui.home.screen.HomeScreen
import com.example.praktikum8.ui.home.screen.UpdateView


@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ){
        composable(DestinasiHome.route){
            HomeScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntry.route)},
                onDetailClick = {nim ->
                    navController.navigate("detail/$nim")
                }
            )
        }
        composable(DestinasiEntry.route){
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
        composable("detail/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            DetailMahasiswaview(
                nim = nim,
                navigateBack = { navController.popBackStack() },
                onUpdateClick = { nim ->
                    navController.navigate("update/$nim")
                }
            )
        }
        composable("update/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim") ?: ""
            UpdateView(
                nim = nim,
                navigateBack = { navController.popBackStack() }
            )
        }

    }
}