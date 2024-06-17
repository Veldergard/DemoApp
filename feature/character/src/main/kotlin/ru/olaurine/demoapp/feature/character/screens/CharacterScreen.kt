package ru.olaurine.demoapp.feature.character.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.olaurine.demoapp.common.components.DataPoint
import ru.olaurine.demoapp.common.components.DataPointComponent
import ru.olaurine.demoapp.common.components.LoadingState
import ru.olaurine.demoapp.data.model.CharacterDto
import ru.olaurine.demoapp.feature.character.components.CharacterImage
import ru.olaurine.demoapp.feature.character.components.CharacterNamePlateComponent
import ru.olaurine.demoapp.feature.character.viewmodels.CharacterViewModel

sealed interface CharacterViewState {
    data object Loading : CharacterViewState

    data class Error(
        val message: String
    ) : CharacterViewState

    data class Success(
        val character: CharacterDto,
        val characterDataPoints: List<DataPoint>
    ) : CharacterViewState
}

@Composable
fun CharacterScreen(
    characterId: Int,
    viewModel: CharacterViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.syncCharacter(characterId)
    })

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        when (val viewState = state) {
            CharacterViewState.Loading -> {
                item { LoadingState() }
            }

            is CharacterViewState.Error -> {
                // TODO
            }

            is CharacterViewState.Success -> {
                item {
                    CharacterNamePlateComponent(
                        name = viewState.character.name,
                        status = viewState.character.status
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }

                item { CharacterImage(imageUrl = viewState.character.imageUrl) }

                items(viewState.characterDataPoints) {
                    Spacer(modifier = Modifier.height(32.dp))
                    DataPointComponent(dataPoint = it)
                }

                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}