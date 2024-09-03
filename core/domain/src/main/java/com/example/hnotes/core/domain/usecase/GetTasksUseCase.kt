package com.example.hnotes.core.domain.usecase

import com.example.hnotes.core.data.repository.TaskRepository
import com.example.hnotes.core.model.data.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {

    operator fun invoke(): Flow<Map<Boolean, List<Task>>> =
        taskRepository.tasks
            .map {
                it
                    .sortedByDescending(Task::reminder)
                    .groupBy(Task::isCompleted)
                    .toSortedMap()
            }
}