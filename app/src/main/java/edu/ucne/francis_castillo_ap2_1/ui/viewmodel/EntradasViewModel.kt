package edu.ucne.francis_castillo_ap2_1.data.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity

import edu.ucne.francis_castillo_ap2_1.data.repository.EntradasHuacalesRepository
import edu.ucne.francis_castillo_ap2_1.domain.usecase.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntradasHuacalesViewModel @Inject constructor(
    private val getAllUseCase: GetAllEntradasHuacalesUseCase,
    private val getUseCase: GetEntradasHuacalesUseCase,
    private val insertUseCase: InsertEntradasHuacalesUseCase,
    private val updateUseCase: UpdateEntradasHuacalesUseCase,
    private val deleteUseCase: DeleteEntradasHuacalesUseCase,
    private val searchUseCase: SearchEntradasHuacalesUseCase
) : ViewModel() {

    private val _entradas = MutableStateFlow<List<EntradasHuacalesEntity>>(emptyList())
    val entradas: StateFlow<List<EntradasHuacalesEntity>> = _entradas.asStateFlow()

    val currentEntrada: MutableState<EntradasHuacalesEntity?> = mutableStateOf(null)

    init {
        loadAllEntradas()
    }

    fun loadAllEntradas() {
        viewModelScope.launch {
            _entradas.value = getAllUseCase()
        }
    }

    fun getEntradaById(id: Int) {
        viewModelScope.launch {
            currentEntrada.value = getUseCase(id)
        }
    }

    fun insertEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            insertUseCase(entrada)
            loadAllEntradas()
        }
    }

    fun updateEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            updateUseCase(entrada)
            loadAllEntradas()
        }
    }

    fun deleteEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            deleteUseCase(entrada)
            loadAllEntradas()
        }
    }

    fun searchEntradas(query: String) {
        viewModelScope.launch {
            _entradas.value = searchUseCase(query)
        }
    }
}
