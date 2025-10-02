package edu.ucne.francis_castillo_ap2_1.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.data.repository.EntradasHuacalesRepository
import edu.ucne.francis_castillo_ap2_1.ui.entradashuacales.EntradasState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EntradasHuacalesViewModel(
    private val repository: EntradasHuacalesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EntradasState())
    val state: StateFlow<EntradasState> = _state

    // Helpers para acceder más fácil desde UI
    val entradas: List<EntradasHuacalesEntity> get() = _state.value.entradas
    val currentEntrada: EntradasHuacalesEntity? get() = _state.value.currentEntrada

    // Cargar todas las entradas
    fun loadAllEntradas() {
        viewModelScope.launch {
            try {
                val lista = repository.getAllEntradas() // Flow<List<EntradasHuacalesEntity>>
                    .first()
                _state.update { it.copy(entradas = lista, isLoading = false, error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Buscar entradas por nombre de cliente
    fun searchEntradas(query: String) {
        viewModelScope.launch {
            try {
                val lista = repository.searchEntradas(query).first()
                _state.update { it.copy(entradas = lista, isLoading = false, error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Obtener una entrada por ID para edición
    fun getEntradaById(id: Int) {
        viewModelScope.launch {
            try {
                val entrada = repository.getEntradaById(id)
                _state.update { it.copy(currentEntrada = entrada) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    // Insertar una nueva entrada
    fun insertEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            try {
                repository.insertEntrada(entrada)
                loadAllEntradas()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    // Actualizar entrada existente
    fun updateEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            try {
                repository.updateEntrada(entrada)
                loadAllEntradas()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    // Eliminar entrada
    fun deleteEntrada(entrada: EntradasHuacalesEntity) {
        viewModelScope.launch {
            try {
                repository.deleteEntrada(entrada)
                loadAllEntradas()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    // Funciones para errores de validación de UI
    fun setNombreError(message: String?) {
        _state.update { it.copy(nombreClienteError = message) }
    }

    fun setCantidadError(message: String?) {
        _state.update { it.copy(cantidadError = message) }
    }

    fun setPrecioError(message: String?) {
        _state.update { it.copy(precioError = message) }
    }

    // Limpiar entrada actual
    fun clearCurrentEntrada() {
        _state.update { it.copy(currentEntrada = null) }
    }
}
