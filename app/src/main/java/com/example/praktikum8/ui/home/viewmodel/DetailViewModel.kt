package com.example.praktikum8.ui.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val mhsRepository: MahasiswaRepository) : ViewModel() {

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    fun getDetailMahasiswa(nim: String) {
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            try {
                val mahasiswa = mhsRepository.getMahasiswaByNim(nim)
                _detailUiState.value = DetailUiState.Success(mahasiswa)
            } catch (e: Exception) {
                _detailUiState.value = DetailUiState.Error
            }
        }
    }
}