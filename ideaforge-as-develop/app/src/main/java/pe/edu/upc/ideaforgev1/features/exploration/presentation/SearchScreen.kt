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
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
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
                    singleLine = true,
                    trailingIcon = {
                        if (state.query.isNotEmpty()) {
                            TextButton(onClick = { viewModel.searchIdeas("") }) {
                                Text("Clear")
                            }
                        }
                    }
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quick filters:",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    listOf("Tech", "Design", "Business").forEach { filter ->
                        AssistChip(
                            onClick = { viewModel.applyQuickFilter(filter) },
                            label = { Text(filter) },
                            colors = if (state.query == filter) {
                                AssistChipDefaults.assistChipColors(
                                    labelColor = MaterialTheme.colorScheme.primary
                                )
                            } else {
                                AssistChipDefaults.assistChipColors()
                            }
                        )
                    }
                }

                if (state.query.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Showing results for: \"${state.query}\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        TextButton(onClick = { viewModel.searchIdeas("") }) {
                            Text("Clear all")
                        }
                    }
                }

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Results",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    
                    if (state.ideas.isNotEmpty()) {
                        Text(
                            text = "(${state.ideas.size} found)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                if (state.query.isNotEmpty() && state.ideas.isNotEmpty()) {
                    Text(
                        text = "Matches all your current filters.",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        if (state.ideas.isEmpty() && !state.isLoading) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "No ideas found matching your criteria.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Try removing some filters or searching for more general terms like 'App' or 'Web'.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    TextButton(onClick = { viewModel.searchIdeas("") }) {
                        Text("View all projects")
                    }
                }
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
