package edu.ucne.francis_castillo_ap2_1.ui.entradashuacales

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.data.viewmodel.EntradasHuacalesViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradasHuacalesFormScreen(
    entradaId: Int,
    onBack: () -> Unit,
    viewModel: EntradasHuacalesViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    var fecha by remember { mutableStateOf(Date()) }
    var nombreCliente by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    var nombreClienteError by remember { mutableStateOf<String?>(null) }
    var cantidadError by remember { mutableStateOf<String?>(null) }
    var precioError by remember { mutableStateOf<String?>(null) }

    val isEditMode = entradaId > 0

    // Cargar datos si es edición
    LaunchedEffect(entradaId) {
        if (isEditMode) {
            viewModel.getEntradaById(entradaId)
        }
    }

    LaunchedEffect(viewModel.currentEntrada.value) {
        viewModel.currentEntrada.value?.let { entrada ->
            fecha = entrada.fecha
            nombreCliente = entrada.nombreCliente
            cantidad = entrada.cantidad.toString()
            precio = entrada.precio.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Editar Entrada" else "Nueva Entrada") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Selector de Fecha
            OutlinedCard {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Fecha",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            calendar.time = fecha
                            DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    fecha = Calendar.getInstance().apply {
                                        set(year, month, day)
                                    }.time
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.DateRange, null)
                        Spacer(Modifier.width(8.dp))
                        Text(dateFormat.format(fecha))
                    }
                }
            }

            // Nombre Cliente
            OutlinedTextField(
                value = nombreCliente,
                onValueChange = {
                    nombreCliente = it
                    nombreClienteError = null
                },
                label = { Text("Nombre del Cliente *") },
                modifier = Modifier.fillMaxWidth(),
                isError = nombreClienteError != null,
                supportingText = {
                    nombreClienteError?.let { Text(it, color = Color.Red) }
                },
                leadingIcon = { Icon(Icons.Default.Person, null) }
            )

            // Cantidad
            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                        cantidad = it
                        cantidadError = null
                    }
                },
                label = { Text("Cantidad de Huacales *") },
                modifier = Modifier.fillMaxWidth(),
                isError = cantidadError != null,
                supportingText = {
                    cantidadError?.let { Text(it, color = Color.Red) }
                },
                leadingIcon = { Icon(Icons.Default.ShoppingCart, null) }
            )

            // Precio
            OutlinedTextField(
                value = precio,
                onValueChange = {
                    if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                        precio = it
                        precioError = null
                    }
                },
                label = { Text("Precio por Unidad *") },
                modifier = Modifier.fillMaxWidth(),
                isError = precioError != null,
                supportingText = {
                    precioError?.let { Text(it, color = Color.Red) }
                },
                leadingIcon = { Icon(Icons.Default.ShoppingCart, null) }
            )

            // Mostrar total calculado
            if (cantidad.isNotEmpty() && precio.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "$${"%.2f".format(cantidad.toIntOrNull()?.times(precio.toDoubleOrNull() ?: 0.0) ?: 0.0)}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botones
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        // Validaciones
                        var hasError = false

                        if (nombreCliente.isBlank()) {
                            nombreClienteError = "El nombre es obligatorio"
                            hasError = true
                        }

                        if (cantidad.isBlank() || cantidad.toIntOrNull() == null || cantidad.toInt() <= 0) {
                            cantidadError = "Cantidad inválida"
                            hasError = true
                        }

                        if (precio.isBlank() || precio.toDoubleOrNull() == null || precio.toDouble() < 0) {
                            precioError = "Precio inválido"
                            hasError = true
                        }

                        if (!hasError) {
                            val entrada = EntradasHuacalesEntity(
                                entradaId = if (isEditMode) entradaId else null,
                                fecha = fecha,
                                nombreCliente = nombreCliente.trim(),
                                cantidad = cantidad.toInt(),
                                precio = precio.toDouble()
                            )

                            if (isEditMode) {
                                viewModel.updateEntrada(entrada)
                            } else {
                                viewModel.insertEntrada(entrada)
                            }
                            onBack()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(8.dp))
                    Text(if (isEditMode) "Actualizar" else "Guardar")
                }

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Close, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cancelar")
                }
            }
        }
    }
}