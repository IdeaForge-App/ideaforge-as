package pe.edu.upc.ideaforgev1.features.ideas.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateIdeaScreen(
    onIdeaCreated: () -> Unit = {},
    viewModel: IdeaViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var problem by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }
    var selectedRoles by remember { mutableStateOf(setOf<String>()) }

    val state = viewModel.uiState

    val availableRoles = listOf(
        "Developer",
        "Design",
        "Marketing",
        "Business",
        "Tech",
        "Data"
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Create Idea",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Publish a project idea and find people to build with.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Idea title") },
                        placeholder = { Text("Example: StudyBuddy") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Short description") },
                        placeholder = { Text("Describe your idea in one or two lines") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        placeholder = { Text("TECHNOLOGY, EDUCATION, BUSINESS, SOCIAL...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = problem,
                        onValueChange = { problem = it },
                        label = { Text("Problem") },
                        placeholder = { Text("What problem does your idea solve?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    OutlinedTextField(
                        value = solution,
                        onValueChange = { solution = it },
                        label = { Text("Solution") },
                        placeholder = { Text("How would your idea solve it?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2
                    )

                    Text(
                        text = "Required roles",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        availableRoles.forEach { role ->
                            val selected = selectedRoles.contains(role)

                            FilterChip(
                                selected = selected,
                                onClick = {
                                    selectedRoles = if (selected) {
                                        selectedRoles - role
                                    } else {
                                        selectedRoles + role
                                    }
                                },
                                label = {
                                    Text(role)
                                }
                            )
                        }
                    }

                    if (state.errorMessage != null) {
                        Text(
                            text = state.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.createIdea(
                                title = title,
                                description = description,
                                category = category,
                                problem = problem,
                                solution = solution,
                                roles = selectedRoles.toList(),
                                onSuccess = {
                                    title = ""
                                    description = ""
                                    category = ""
                                    problem = ""
                                    solution = ""
                                    selectedRoles = emptySet()
                                    onIdeaCreated()
                                }
                            )
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Text("Publish Idea")
                        }
                    }
                }
            }
        }
    }
}
