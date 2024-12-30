package com.example.praktikum8.ui.home.screen

import com.example.praktikum8.navigaton.DestinasiNavigasi

object DestinasiUpdate : DestinasiNavigasi {
    override val route = "update"
    const val NIM = "nim"
    val routesWithArg = "$route/{$NIM}"
    override val titleRes = "Update Mhs"
}