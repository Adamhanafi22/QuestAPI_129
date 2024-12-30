package com.example.praktikum8.ui.home.viewmodel


import coil.network.HttpException
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeUiState {
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object  Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var mhsUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            mhsUIState = HomeUiState.Loading
            mhsUIState = try {
                HomeUiState.Success(mhs.getAllMahasiswa())
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteMhs(nim:String){
        viewModelScope.launch {
            try {
                mhs.deletedMahasiswa(nim)
            }catch (e: IOException){
                HomeUiState.Error
            }catch ( e:HttpException){
                HomeUiState.Error
            }
        }
    }
}