package com.example.praktikum8.repository

import android.os.Parcel
import android.os.Parcelable
import com.example.praktikum8.model.Mahasiswa
import com.example.praktikum8.service_api.MahasiswaService

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetworkMahasiswaRepository> {
        override fun createFromParcel(parcel: Parcel): NetworkMahasiswaRepository {
            return NetworkMahasiswaRepository(parcel)
        }

        override fun newArray(size: Int): Array<NetworkMahasiswaRepository?> {
            return arrayOfNulls(size)
        }


    }
}