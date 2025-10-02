package edu.ucne.francis_castillo_ap2_1.ui.navigation

sealed class Screen(val route: String) {
    object EntradasList : Screen("entradas_list")
    object EntradasForm : Screen("entradas_form/{entradaId}") {
        fun passId(id: Int): String {
            return "entradas_form/$id"
        }
    }
}
