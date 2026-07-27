# Agent Guide: HNotes Project

Welcome, AI Agent! This document provides the essential context and guidelines for working on the **HNotes** Android project.

## 🏗 Project Architecture

HNotes follows a **Modular Architecture** with a clear separation of concerns.

### 📁 Module Organization

*   **`:app`**: The entry point of the application. It ties all features together.
*   **`:feature:*`**: Contains functional features (e.g., `:feature:note`, `:feature:notes`, `:feature:search`).
    *   Most features follow the **API/Impl** pattern:
        *   `:api`: Public interfaces, navigation routes, and models needed by other modules.
        *   `:impl`: Private implementation, ViewModels, and UI (Compose).
*   **`:core:*`**: Shared infrastructure and utilities.
    *   `:core:data`: Repositories and data sources.
    *   `:core:database`: Room database implementation.
    *   `:core:model`: Shared domain models.
    *   `:core:ui`: Shared Compose components and design system.
    *   `:core:navigation`: Navigation infrastructure.
    *   `:core:common`: General utilities and base classes.
*   **`:build-logic`**: Custom Gradle convention plugins for consistent build configuration across modules.

## 🛠 Tech Stack

*   **Language**: Kotlin
*   **UI**: Jetpack Compose
*   **Dependency Injection**: Hilt (Dagger)
*   **Database**: Room
*   **Navigation**: Type-safe Jetpack Navigation (Compose)
*   **Concurrency**: Kotlin Coroutines & Flow
*   **Build System**: Gradle with Kotlin DSL and Version Catalogs (`libs.versions.toml`)

## 📋 Coding Guidelines for Agents

### 1. Layout & Performance (Compose)
*   **Avoid Nested Scrolling**: Never place a vertically scrollable component (like `LazyColumn` or `LazyVerticalGrid`) inside a layout with `Modifier.verticalScroll()`. This causes infinite height constraints and crashes the app.
*   **Single Scroll Scope**: Use `LazyColumn`'s `item` and `items` DSL to combine headers, footers, and lists into a single scrollable container.

### 2. Hilt & Assisted Injection
When using `@AssistedInject` in ViewModels:
*   Use `@HiltViewModel(assistedFactory = ...::class)`.
*   Ensure the `@AssistedFactory` interface is **not** inside a `companion object`.
*   Example:
    ```kotlin
    @HiltViewModel(assistedFactory = MyViewModelFactory::class)
    class MyViewModel @AssistedInject constructor(
        @Assisted navKey: MyNavKey,
        private val repository: MyRepository
    ) : ViewModel() { ... }

    @AssistedFactory
    interface MyViewModelFactory {
        fun create(navKey: MyNavKey): MyViewModel
    }
    ```

### 3. Feature Structure (API/Impl)
*   Expose only what is necessary in the `:api` module.
*   Keep Compose screens and ViewModels in the `:impl` module.
*   Define navigation routes (using `@Serializable` classes) in the `:api` module.

### 4. Gradle Convention Plugins
*   Avoid adding complex logic directly to module-level `build.gradle.kts` files.
*   Use the existing plugins in `:build-logic` (e.g., `hnotes.android.feature.impl`, `hnotes.android.library.compose`).

### 5. Code Style
*   **Resources**: Keep resources (strings, icons) in the module they are used in. Avoid bloating `:core:ui` or `:app` with feature-specific strings.
*   Prefer `StateFlow` and `collectAsStateWithLifecycle` for UI state.
*   Use `androidx.datetime` for time handling.
*   Maintain the existing MVI-like pattern in ViewModels (e.g., `uiState` field, `onEvent` method).

## 🚀 Common Commands

*   **Build Project**: `./gradlew assembleDebug`
*   **Run KSP**: `./gradlew kspDebugKotlin` (Useful for checking Hilt/Room issues)
*   **Clean Build**: `./gradlew clean`
*   **Spotless Check**: `./gradlew spotlessCheck`
*   **Spotless Apply**: `./gradlew spotlessApply`

## 🏷 Feature Reference
*   **Labels**: Note-label associations are handled in `:feature:label`. This feature uses a many-to-many relationship with a cross-reference table in the database.

## 🔍 Navigation Index
*   [Root README](README.md)
*   [Build Logic README](build-logic/README.md)
*   [Note Feature README](feature/note/README.md)
