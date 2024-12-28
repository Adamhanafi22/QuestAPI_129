package com.example.praktikum8

import android.app.Application

class MahasiswaApplications: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}