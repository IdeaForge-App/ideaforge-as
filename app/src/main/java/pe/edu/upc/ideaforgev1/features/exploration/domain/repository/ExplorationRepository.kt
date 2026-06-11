package pe.edu.upc.ideaforgev1.features.exploration.domain.repository

import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard

interface ExplorationRepository {
    suspend fun getIdeas(): List<IdeaCard>
    suspend fun searchIdeas(query: String): List<IdeaCard>
}
