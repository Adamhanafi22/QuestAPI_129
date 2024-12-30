package com.example.praktikum8.ui.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum8.repository.MahasiswaRepository

import kotlinx.coroutines.launch

class UpdateViewModel(private val mhsRepository: MahasiswaRepository) : ViewModel() {

    var uiState by mutableStateOf(UpdateUiState())
        private set

    // Fetch existing Mahasiswa data based on ID and set the state
    fun loadingMahasiswaData(mahasiswaId: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhsRepository.getMahasiswaByNim(mahasiswaId)
                uiState = UpdateUiState(insertUiEvent = mahasiswa.toInsertUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update Mahasiswa data
    fun updateMahasiswa() {
        viewModelScope.launch {
            try {
                val nim = uiState.insertUiEvent.nim
                val mahasiswa = uiState.insertUiEvent.toMhs()
                mhsRepository.updateMahasiswa(nim, mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) {
        uiState = UpdateUiState(insertUiEvent = insertUiEvent)
    }
}

data class UpdateUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)