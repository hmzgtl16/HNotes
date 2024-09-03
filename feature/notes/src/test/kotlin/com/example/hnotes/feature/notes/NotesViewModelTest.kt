package com.example.hnotes.feature.notes

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.hnotes.core.domain.usecase.GetNotesUseCase
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.testing.repository.TestNoteRepository
import com.example.hnotes.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class NotesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var noteRepository: TestNoteRepository
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var viewModel: NotesViewModel

    @Before
    fun setup() {
        noteRepository = TestNoteRepository()
        getNotesUseCase = GetNotesUseCase(noteRepository = noteRepository)
        viewModel = NotesViewModel(
            noteRepository = noteRepository,
            getNotesUseCase = getNotesUseCase
        )
    }

    @Test
    fun stateIsInitiallyLoading() = runTest {
        assertEquals(
            expected = NotesUiState.Loading,
            actual = viewModel.uiState.value,
        )
    }

    @Test
    fun stateIsSuccessWhenNotesAreEmpty() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        noteRepository.addNotes(notes = emptyList())

        assertEquals(
            NotesUiState.Success(notes = emptyMap()),
            viewModel.uiState.value,
        )

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccessWhenNotesAreNotEmpty() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        noteRepository.addNotes(notes = sampleNotes)

        assertEquals(
            expected = NotesUiState.Success(notes = sampleNotes.groupBy { it.isPinned }),
            actual = (viewModel.uiState.value as NotesUiState.Success)
        )

        collectJob.cancel()
    }

    @Test
    fun stateI() = runTest {
        val x = Color(0)
        val y = Color.Unspecified.toArgb()

        //assertEquals(x, Color.Unspecified)
        assertEquals(y, 0)
    }
}

private val sampleNotes = listOf(
    Note(
        id = 1,
        title = "Kotlin",
        description = "Programming Language.",
        created = Instant.fromEpochMilliseconds(1),
        updated = Instant.fromEpochMilliseconds(1),
        isPinned = true
    ),
    Note(
        id = 2,
        title = "JetBrains",
        description = "JetBrains creates intelligent software development tools consistently used and trusted by 11.4 million professionals and 88 Fortune Global Top 100 companies. Our lineup of more than 30 products includes IDEs for most programming languages and technologies, such as IntelliJ IDEA, PyCharm, and others, as well as products for team collaboration, like YouTrack and TeamCity. JetBrains is also known for creating the Kotlin programming language, a cross-platform language used by more than 5 million developers worldwide yearly and recommended by Google as the preferred language for Android development. The company is headquartered in Prague, Czech Republic, and has offices around the world.",
        created = Instant.fromEpochMilliseconds(2),
        updated = Instant.fromEpochMilliseconds(2),
        isPinned = false
    ),
    Note(
        id = 3,
        title = "Spring",
        description = "Spring makes Java simple.",
        created = Instant.fromEpochMilliseconds(3),
        updated = Instant.fromEpochMilliseconds(3),
        isPinned = true
    ),
    Note(
        id = 4,
        title = "Data safety",
        description = "Safety starts with understanding how developers collect and share your data. Data privacy and security practices may vary based on your use, region, and age. The developer provided this information and may update it over time.",
        created = Instant.fromEpochMilliseconds(4),
        updated = Instant.fromEpochMilliseconds(4),
        isPinned = false
    ),
    Note(
        id = 5,
        title = "Notes",
        description = "Notes is a good helper to manage your schedules and notes. It gives you a quick and simple notepad editing experience when you write notes, memo, email, message, shopping list and to do list. It makes to take a note easier than any other notepad and memo apps.",
        created = Instant.fromEpochMilliseconds(5),
        updated = Instant.fromEpochMilliseconds(5),
        isPinned = true
    ),
    Note(
        id = 6,
        title = "Note-taking",
        description = "Note-taking is the practice of recording information from different sources and platforms. By taking notes, the writer records the essence of the information, freeing their mind from having to recall everything.",
        created = Instant.fromEpochMilliseconds(6),
        updated = Instant.fromEpochMilliseconds(6),
        isPinned = false
    )
)