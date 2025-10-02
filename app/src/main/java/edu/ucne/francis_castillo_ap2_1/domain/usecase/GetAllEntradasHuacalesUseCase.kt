package edu.ucne.francis_castillo_ap2_1.domain.usecase

import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.data.repository.EntradasHuacalesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllEntradasHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    operator fun invoke(): Flow<List<EntradasHuacalesEntity>> {
        return repository.getAllEntradas()
    }
}
