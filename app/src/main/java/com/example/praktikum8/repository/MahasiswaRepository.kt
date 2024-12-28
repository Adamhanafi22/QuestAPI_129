package com.example.praktikum8.repository

import android.os.Parcel
import android.os.Parcelable
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.service_api.MahasiswaService
import java.io.IOException

interface MahasiswaRepository{

    suspend fun getMahasiswa(): List<Mahasiswa>

    suspend fun insertMahasiswa(mahasiswa: Mahasiswa)

    suspend fun updateMahasiswa(nim : String,mahasiswa: Mahasiswa)

    suspend fun deletedMahasiswa(nim: String)

    suspend fun getMahasiswa(nim: String): Mahasiswa
}

class NetworkMahasiswaRepository(
    private val MahasiswaApiService: MahasiswaService
): MahasiswaRepository {
    override suspend fun getMahasiswa(): List<Mahasiswa> =
        MahasiswaApiService.getAllMahasiswa()


    override suspend fun insertMahasiswa(mahasiswa: Mahasiswa) {
        MahasiswaApiService.insertMahasiswa(mahasiswa)
    }

    override suspend fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        MahasiswaApiService.updateMahasiswa(nim, mahasiswa)
    }

    override suspend fun deleteMahasiswa(nim: String) {
        try {
            val response = MahasiswaApiService.deleteMahasiswa(nim)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Mahasiswa. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMahasiswabyNim(nim: String): Mahasiswa {
        return MahasiswaApiService.getMahasiswabyNim(nim)
    }

}