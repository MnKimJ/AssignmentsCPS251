package com.example.assignment10

import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val NoteLightColors = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFBB86FC),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFFF0F0F0),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFB00020),
    onError = Color.White
)

private val NoteDarkColors = darkColorScheme(
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF3700B3),
    onPrimaryContainer = Color.White,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    error = Color(0xFFCF6679),
    onError = Color.Black
)

private val NoteShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun NoteTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) NoteDarkColors else NoteLightColors,
        shapes = NoteShapes,
        typography = Typography(),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(viewModel: NoteViewModel) {
    NoteTheme {
        val notes by viewModel.notes.collectAsState()
        var title by remember { mutableStateOf("") }
        var content by remember { mutableStateOf("") }
        var titleError by remember { mutableStateOf<String?>(null) }
        var contentError by remember { mutableStateOf<String?>(null) }
        var editingNote by remember { mutableStateOf<Note?>(null) }
        var noteToDelete by remember { mutableStateOf<Note?>(null) }
        val starredNotes = remember { mutableStateMapOf<Int, Boolean>() }
        val expandedNotes = remember { mutableStateMapOf<Int, Boolean>() }
        val dateFormat = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val titleFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        fun clearForm() {
            title = ""
            content = ""
            titleError = null
            contentError = null
            editingNote = null
        }

        fun validate(): Boolean {
            var valid = true
            if (title.isBlank()) {
                titleError = "Title cannot be empty"
                valid = false
            } else titleError = null
            if (content.isBlank()) {
                contentError = "Content cannot be empty"
                valid = false
            } else contentError = null
            return valid
        }

        fun submitNote() {
            if (!validate()) return
            val now = dateFormat.format(Date())
            if (editingNote == null) {
                viewModel.addNote(title, content, now)
                scope.launch { snackbarHostState.showSnackbar("Note added!") }
            } else {
                viewModel.deleteNote(editingNote!!)
                viewModel.addNote(title, content, now)
                scope.launch { snackbarHostState.showSnackbar("Note updated!") }
            }
            clearForm()
        }

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
                                text = "Material Notes",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        clearForm()
                        titleFocusRequester.requestFocus()
                        focusManager.moveFocus(FocusDirection.Up)
                    },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (editingNote == null) "Create New Note" else "Edit Note",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = title,
                            onValueChange = {
                                title = it
                                titleError = null
                            },
                            label = { Text("Title") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(titleFocusRequester),
                            isError = titleError != null,
                            supportingText = {
                                if (titleError != null) {
                                    Text(
                                        text = titleError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = content,
                            onValueChange = {
                                content = it
                                contentError = null
                            },
                            label = { Text("Content") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 0.dp),
                            minLines = 3,
                            isError = contentError != null,
                            supportingText = {
                                if (contentError != null) {
                                    Text(
                                        text = contentError!!,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = { submitNote() },
                                enabled = title.isNotBlank() && content.isNotBlank()
                            ) {
                                Text(if (editingNote == null) "Add Note" else "Update Note")
                            }
                            if (editingNote != null) {
                                OutlinedButton(onClick = { clearForm() }) {
                                    Text("Cancel Edit")
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "Your Notes",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(notes) { note ->
                        val isStarred = starredNotes[note.id] ?: false
                        val isExpanded = expandedNotes[note.id] ?: false
                        NoteCard(
                            note = note,
                            isStarred = isStarred,
                            isExpanded = isExpanded,
                            onCardClick = {
                                editingNote = note
                                title = note.title
                                content = note.content
                                expandedNotes[note.id] = !(expandedNotes[note.id] ?: false)
                            },
                            onStarToggle = {
                                starredNotes[note.id] = !isStarred
                            },
                            onDeleteClick = {
                                noteToDelete = note
                            }
                        )
                    }
                }

                if (noteToDelete != null) {
                    AlertDialog(
                        onDismissRequest = { noteToDelete = null },
                        title = { Text("Delete Note") },
                        text = {
                            Text("Are you sure you want to delete this note: \"${noteToDelete!!.title}\"?")
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val id = noteToDelete!!.id
                                    viewModel.deleteNote(noteToDelete!!)
                                    starredNotes.remove(id)
                                    expandedNotes.remove(id)
                                    scope.launch { snackbarHostState.showSnackbar("Note deleted!") }
                                    if (editingNote?.id == id) clearForm()
                                    noteToDelete = null
                                }
                            ) {
                                Text(
                                    "Delete",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { noteToDelete = null }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NoteCard(
    note: Note,
    isStarred: Boolean,
    isExpanded: Boolean,
    onCardClick: () -> Unit,
    onStarToggle: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val targetColor = if (isStarred) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val animatedColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = tween(300)
    )
    val targetElevation = if (isStarred) 8.dp else 2.dp
    val elevation by animateDpAsState(
        targetValue = targetElevation,
        animationSpec = tween(300)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(300))
            .clickable { onCardClick() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = animatedColor),
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(300)),
                exit = shrinkVertically(animationSpec = tween(300))
            ) {
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Last updated: ${note.date}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        IconButton(onClick = onStarToggle) {
                            Icon(
                                imageVector = if (isStarred) Icons.Filled.Star else Icons.Filled.StarBorder,
                                contentDescription = null,
                                tint = if (isStarred) {
                                    MaterialTheme.colorScheme.secondary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                }
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = onDeleteClick) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}
