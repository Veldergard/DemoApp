package ru.olaurine.demoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.olaurine.demoapp.common.ui.theme.DemoAppPrimary
import ru.olaurine.demoapp.common.ui.theme.DemoAppSecondary
import ru.olaurine.demoapp.common.ui.theme.DemoAppTheme
import ru.olaurine.demoapp.common.ui.theme.Purple40
import ru.olaurine.demoapp.feature.character.screens.CharacterScreen

data class BottomNavigationItem(
    val title: String,
    val navDest: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasBadge: Boolean,
    val badgeCount: Int? = null
)

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val items = listOf(
                BottomNavigationItem(
                    title = "Home",
                    navDest = "home",
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    hasBadge = false,
                ), BottomNavigationItem(
                    title = "Settings",
                    navDest = "settings",
                    selectedIcon = Icons.Filled.Settings,
                    unselectedIcon = Icons.Outlined.Settings,
                    hasBadge = true,
                )
            )
            var selectedItemIndex by rememberSaveable {
                mutableIntStateOf(0)
            }

            DemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
                    NavigationBar(
                        containerColor = DemoAppSecondary,
                        contentColor = DemoAppPrimary
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = index == selectedItemIndex,
                                colors = NavigationBarItemColors(
                                    selectedIndicatorColor = Purple40,
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.White,
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    disabledIconColor = Color.White,
                                    disabledTextColor = Color.White
                                ),
                                onClick = {
                                    selectedItemIndex = index
                                    navController.navigate(item.navDest)
                                },
                                label = {
                                    Text(text = item.title)
                                },
                                icon = {
                                    BadgedBox(badge = {
                                        if (item.badgeCount != null) {
                                            Badge {
                                                Text(text = item.badgeCount.toString())
                                            }
                                        } else if (item.hasBadge) {
                                            Badge()
                                        }
                                    }) {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) {
                                                item.selectedIcon
                                            } else item.unselectedIcon, contentDescription = item.title
                                        )
                                    }
                                }
                            )
                        }
                    }
                }) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = DemoAppPrimary
                    ) {
                        NavHost(navController = navController, startDestination = "home") {
                            composable(route = "home") {
                                CharacterScreen(characterId = 1)
                            }
                            composable("settings") {
                                Text(
                                    text = "Settings TODO", modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
