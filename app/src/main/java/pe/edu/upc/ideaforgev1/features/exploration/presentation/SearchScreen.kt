package pe.edu.upc.ideaforgev1.features.exploration.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pe.edu.upc.ideaforgev1.core.presentation.components.IdeaForgeIdeaCard

@Composable
fun SearchScreen(
    onIdeaClick: (Long) -> Unit = {},
    viewModel: ExplorationViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadIdeas()
    }

    val state = viewModel.uiState

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Find ideas, roles, skills or people.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                OutlinedTextField(
                    value = state.query,
                    onValueChange = { query ->
                        viewModel.searchIdeas(query)
                    },
                    label = { Text("Search ideas...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Tech", "Design", "Business").forEach { filter ->
                        AssistChip(
                            onClick = {
                                viewModel.applyQuickFilter(filter)
                            },
                            label = { Text(filter) }
                        )
                    }
                }

                Text(
                    text = "Results",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }

        if (state.ideas.isEmpty()) {
            item {
                Text(
                    text = "No ideas found yet. Try another search.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        } else {
            items(state.ideas) { idea ->
                IdeaForgeIdeaCard(
                    idea = idea,
                    onActionClick = {
                        onIdeaClick(idea.id)
                    }
                )
            }
        }
    }
}
