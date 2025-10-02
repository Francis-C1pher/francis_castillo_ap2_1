package edu.ucne.francis_castillo_ap2_1.ui.screens



import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.data.viewmodel.EntradasHuacalesViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntradasHuacalesListScreen(
    onAddClick: () -> Unit,
    onEditClick: (Int) -> Unit,
    viewModel: EntradasHuacalesViewModel = hiltViewModel()
) {
    val entradas by viewModel.entradas.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var showFilters by remember { mutableStateOf(false) }

    // Calcular totales
    val totalEntradas = entradas.size
    val totalCantidad = entradas.sumOf { it.cantidad }
    val totalMonto = entradas.sumOf { it.cantidad * it.precio }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Control de Huacales") },
                actions = {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            if (showFilters) Icons.Default.KeyboardArrowUp
                            else Icons.Default.KeyboardArrowDown,
                            "Filtros"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, "Agregar Entrada")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Panel de Filtros
            if (showFilters) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = {
                                searchQuery = it
                                if (it.isEmpty()) {
                                    viewModel.loadAllEntradas()
                                } else {
                                    viewModel.searchEntradas(it)
                                }
                            },
                            label = { Text("Buscar por Cliente") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = {
                                        searchQuery = ""
                                        viewModel.loadAllEntradas()
                                    }) {
                                        Icon(Icons.Default.Close, "Limpiar")
                                    }
                                }
                            }
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }

            // Panel de Totales
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text(
                        "Resumen",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Total Entradas:", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "$totalEntradas",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text("Total Huacales:", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "$totalCantidad",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text("Total Monto:", style = MaterialTheme.typography.bodyMedium)
                            Text(
                                "$${"%.2f".format(totalMonto)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Lista de Entradas
            if (entradas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay entradas registradas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(entradas) { entrada ->
                        EntradaItem(
                            entrada = entrada,
                            onEditClick = { onEditClick(entrada.entradaId ?: 0) },
                            onDeleteClick = { viewModel.deleteEntrada(entrada) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EntradaItem(
    entrada: EntradasHuacalesEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "ID: ${entrada.entradaId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    entrada.nombreCliente,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Fecha: ${dateFormat.format(entrada.fecha)}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(
                        "Cantidad: ${entrada.cantidad}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        "Precio: $${entrada.precio}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Text(
                    "Total: $${"%.2f".format(entrada.cantidad * entrada.precio)}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column {
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, "Editar", tint = Color.Blue)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Está seguro de eliminar esta entrada?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Eliminar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}