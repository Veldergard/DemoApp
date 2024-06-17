package ru.olaurine.demoapp.feature.character.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.olaurine.demoapp.data.model.CharacterStatus

@Composable
fun CharacterNamePlateComponent(name: String, status: CharacterStatus) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CharacterStatusComponent(characterStatus = status)
        CharacterNameComponent(name = name)
    }
}

@Preview
@Composable
fun NamePlatePreviewAlive() {
    CharacterNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Alive)
}

@Preview
@Composable
fun NamePlatePreviewDead() {
    CharacterNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Dead)
}

@Preview
@Composable
fun NamePlatePreviewUnknown() {
    CharacterNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Unknown)
}