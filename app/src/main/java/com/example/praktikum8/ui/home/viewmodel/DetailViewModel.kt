package com.example.praktikum8.ui.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.repository.MahasiswaRepository
import com.example.praktikum8.ui.home.screen.DestinasiDetail

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}


class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val mhs: MahasiswaRepository
) : ViewModel() {

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    // StateFlow untuk menyimpan status UI
    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        DetailMahasiswa()
    }

    fun DetailMahasiswa() {
        viewModelScope.launch {
            try {
                // Set loading state
                _detailUiState.value = DetailUiState.Loading

                // Fetch mahasiswa data dari repository
                val mahasiswa = mhs.getMahasiswabyNim(_nim)

                if (mahasiswa != null) {
                    // Jika data ditemukan, emit sukses
                    _detailUiState.value = DetailUiState.Success(mahasiswa)
                } else {
                    // Jika data tidak ditemukan, emit error
                    _detailUiState.value = DetailUiState.Error
                }
            } catch (e: Exception) {
                // Emit error jika terjadi exception
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}


fun Mahasiswa.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan
    )
}