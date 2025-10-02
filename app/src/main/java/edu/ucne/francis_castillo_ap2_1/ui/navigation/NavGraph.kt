package edu.ucne.francis_castillo_ap2_1.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.ucne.francis_castillo_ap2_1.ui.screens.EntradasHuacalesListScreen
import edu.ucne.francis_castillo_ap2_1.ui.entradashuacales.EntradasHuacalesFormScreen


object Routes {
    const val ENTRADAS_LIST = "entradas_list"
    const val ENTRADAS_FORM = "entradas_form/{entradaId}"

    fun formWithId(id: Int): String = "entradas_form/$id"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.ENTRADAS_LIST
    ) {
        composable(Routes.ENTRADAS_LIST) {
            EntradasHuacalesListScreen(
                onAddClick = { navController.navigate(Routes.formWithId(0)) },
                onEditClick = { id -> navController.navigate(Routes.formWithId(id)) }
            )
        }

        composable(Routes.ENTRADAS_FORM) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("entradaId")?.toInt() ?: 0
            EntradasHuacalesFormScreen(
                entradaId = id,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
