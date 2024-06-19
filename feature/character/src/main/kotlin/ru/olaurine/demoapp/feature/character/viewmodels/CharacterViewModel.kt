package ru.olaurine.demoapp.feature.character.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.olaurine.demoapp.common.components.DataPoint
import ru.olaurine.demoapp.data.repository.CharacterRepository
import ru.olaurine.demoapp.feature.character.screens.CharacterViewState

@HiltViewModel(assistedFactory = CharacterViewModel.Factory::class)
class CharacterViewModel @AssistedInject constructor(
    @Assisted val characterId: Int,
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private val _characterDataFlow = characterRepository
        .getCharacterDataFlow(characterId = characterId)

    private val _isError = MutableStateFlow(false)

    val stateFlow = combine(_characterDataFlow, _isError) { character, isError ->
        if (isError) {
            CharacterViewState.Error("Error")
        } else {
            if (character == null) {
                CharacterViewState.Loading
            } else {
                val dataPoints = buildList {
                    add(DataPoint("Last known location", character.location.name))
                    add(DataPoint("Species", character.species))
                    add(DataPoint("Gender", character.gender.displayName))
                    character.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataPoint("Type", type))
                    }
                    add(DataPoint("Origin", character.origin.name))
                    add(DataPoint("Episode count", character.episodeIds.size.toString()))
                }
                CharacterViewState.Success(character = character, characterDataPoints = dataPoints)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, CharacterViewState.Loading)

    fun syncCharacter(characterId: Int) = viewModelScope.launch {
        _isError.update { false }

        try {
            characterRepository.syncCharacter(characterId)
        } catch (e: Exception) {
            _isError.update { true }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(characterId: Int): CharacterViewModel
    }
}