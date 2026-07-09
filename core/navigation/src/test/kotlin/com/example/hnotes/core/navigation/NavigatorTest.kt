package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.junit.Before

class NavigatorTest {

    private lateinit var navigationState: NavigationState
    private lateinit var navigator: Navigator

    @Before
    fun setup() {
        val startKey = TestKeyFirst
        val backStack = NavBackStack<NavKey>(startKey)

        navigationState = NavigationState(
            startKey = startKey,
            backStack = backStack,
        )
        navigator = Navigator()
    }


}

private object TestKeyFirst : NavKey
private object TestKeySecond : NavKey