package pe.edu.upc.ideaforgev1.features.exploration.data.repository

import pe.edu.upc.ideaforgev1.core.data.fake.FakeData
import pe.edu.upc.ideaforgev1.features.exploration.domain.model.IdeaCard
import pe.edu.upc.ideaforgev1.features.exploration.domain.repository.ExplorationRepository

class FakeExplorationRepository : ExplorationRepository {

    override suspend fun getIdeas(): List<IdeaCard> {
        return FakeData.ideas
    }

    override suspend fun searchIdeas(query: String): List<IdeaCard> {
        if (query.isBlank()) return FakeData.ideas

        return FakeData.ideas.filter { idea ->
            idea.title.contains(query, ignoreCase = true) ||
                    idea.description.contains(query, ignoreCase = true) ||
                    idea.category.contains(query, ignoreCase = true) ||
                    idea.roles.any { role ->
                        role.contains(query, ignoreCase = true)
                    }
        }
    }
}
