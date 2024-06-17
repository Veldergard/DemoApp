package ru.olaurine.demoapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.olaurine.demoapp.data.model.CharacterDto
import ru.olaurine.demoapp.data.model.CharacterGender
import ru.olaurine.demoapp.data.model.CharacterStatus
import ru.olaurine.demoapp.database.dao.CharacterDao
import ru.olaurine.demoapp.database.entities.CharacterEntity
import ru.olaurine.demoapp.network.CharacterDataSource
import ru.olaurine.demoapp.network.model.CharacterResponse
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDataSource: CharacterDataSource,
    private val characterDao: CharacterDao
) : CharacterRepository {
    override suspend fun syncCharacter(characterId: Int) {
        characterDataSource.getCharacterInfo(id = characterId).toEntity().let {
            characterDao.insertCharacter(it)
        }
    }

    override fun observeCharacter(characterId: Int): Flow<CharacterDto?> {
        return characterDao.observeCharacter(characterId)
            .map { characterEntity ->
                characterEntity?.toDto()
            }
    }

    private fun CharacterEntity.toDto(): CharacterDto {
        val characterGender = when (gender.lowercase()) {
            "male" -> CharacterGender.Male
            "female" -> CharacterGender.Female
            "genderless" -> CharacterGender.Genderless
            else -> CharacterGender.Unknown
        }
        val characterStatus = when (status.lowercase()) {
            "alive" -> CharacterStatus.Alive
            "dead" -> CharacterStatus.Dead
            else -> CharacterStatus.Unknown
        }
        return CharacterDto(
            created = created,
            episodeIds = episodeIds,
            gender = characterGender,
            id = id,
            imageUrl = imageUrl,
            location = CharacterDto.Location(name = locationName, url = locationUrl),
            name = name,
            origin = CharacterDto.Origin(name = originName, url = originUrl),
            species = species,
            status = characterStatus,
            type = type
        )
    }

    private fun CharacterResponse.toEntity(): CharacterEntity {
        return CharacterEntity(
            created = created,
            episodeIds = episode.map { it.substring(it.lastIndexOf("/") + 1).toInt() },
            gender = gender,
            id = id,
            imageUrl = image,
            locationName = location.name,
            locationUrl = location.url,
            name = name,
            originName = origin.name,
            originUrl = origin.url,
            species = species,
            status = status,
            type = type
        )
    }
}