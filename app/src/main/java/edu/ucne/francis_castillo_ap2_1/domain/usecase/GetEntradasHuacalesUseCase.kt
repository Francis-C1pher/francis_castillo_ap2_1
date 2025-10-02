package edu.ucne.francis_castillo_ap2_1.domain.usecase

import edu.ucne.francis_castillo_ap2_1.data.entity.EntradasHuacalesEntity
import edu.ucne.francis_castillo_ap2_1.data.repository.EntradasHuacalesRepository
import javax.inject.Inject

class GetEntradasHuacalesUseCase @Inject constructor(
    private val repository: EntradasHuacalesRepository
) {
    suspend operator fun invoke(entradaId: Int): EntradasHuacalesEntity? {
        return repository.getEntradaById(entradaId)
    }
}
