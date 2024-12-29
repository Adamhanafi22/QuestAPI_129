package com.example.praktikum8.ui.home.screen

import com.example.praktikum8.navigaton.DestinasiNavigasi

object DestinasiDetail : DestinasiNavigasi {
    override val route = "Detail"
    override val titleRes = "Detail Mahasiswa" // Implementasi wajib
    const val NIM = "nim"
    val routeWithArg = "$route/{$NIM}"
}