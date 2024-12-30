package com.example.praktikum8.ui.home.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.repository.MahasiswaRepository
import com.example.praktikum8.ui.home.screen.DestinasiUpdate
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {

    // Retrieve the NIM from SavedStateHandle
    val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    var uiState = mutableStateOf(InsertUiState())
        private set

    init {
        ambilMahasiswa()
    }

    // Fetch the student data using NIM
    private fun ambilMahasiswa() {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswabyNim(_nim)
                mahasiswa?.let {
                    uiState.value = it.toInsertUIEvent() // Update state with the fetched data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the mahasiswa information
    fun updateMhs(nim: String, mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                mhs.updateMahasiswa(nim, mahasiswa)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Update the UI state with a new InsertUiEvent
    fun updateMhsState(insertUiEvent: InsertUiEvent) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

fun Mahasiswa.toInsertUIEvent(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent()
)