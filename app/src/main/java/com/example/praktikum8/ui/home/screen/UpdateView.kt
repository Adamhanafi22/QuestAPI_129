package com.example.praktikum8.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.praktikum8.ui.CostumeTopAppBar
import com.example.praktikum8.ui.home.viewmodel.InsertUiEvent
import com.example.praktikum8.ui.home.viewmodel.PenyediaViewModel
import com.example.praktikum8.ui.home.viewmodel.UpdateUiState
import com.example.praktikum8.ui.home.viewmodel.UpdateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateView(
    nim: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()

    // Muat data mahasiswa berdasarkan ID
    LaunchedEffect(nim) {
        viewModel.loadingMahasiswaData(nim)
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = "Update Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMahasiswa()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun UpdateBody(
    updateUiState: UpdateUiState,
    onSiswaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = updateUiState.insertUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}