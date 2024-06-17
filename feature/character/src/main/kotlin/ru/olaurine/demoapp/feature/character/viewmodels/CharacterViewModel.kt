package ru.olaurine.demoapp.feature.character.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.olaurine.demoapp.common.components.DataPoint
import ru.olaurine.demoapp.data.repository.CharacterRepository
import ru.olaurine.demoapp.feature.character.screens.CharacterViewState
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {
    private val _internalStorageFlow = MutableStateFlow<CharacterViewState>(
        value = CharacterViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun syncCharacter(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update {
            return@update CharacterViewState.Loading
        }
        try {
            characterRepository.syncCharacter(characterId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        characterRepository.observeCharacter(characterId).collect { character ->
            if (character == null) {
                _internalStorageFlow.update {
                    return@update CharacterViewState.Loading
                }
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
                _internalStorageFlow.update {
                    return@update CharacterViewState.Success(
                        character = character,
                        characterDataPoints = dataPoints
                    )
                }
            }
        }
    }
}