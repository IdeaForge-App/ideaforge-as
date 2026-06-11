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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pe.edu.upc.ideaforgev1.core.presentation.components.IdeaForgeIdeaCard

@Composable
fun HomeScreen(
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Hi, Eduardo! 👋",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "Let's build something amazing today.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Text(
                        text = "E",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Search ideas, skills, or people...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 22.dp),
                    singleLine = true,
                    enabled = false
                )

                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Developer", "Design", "Marketing").forEach { filter ->
                        AssistChip(
                            onClick = {},
                            label = { Text(filter) }
                        )
                    }
                }

                Text(
                    text = "Ideas for you",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }

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
