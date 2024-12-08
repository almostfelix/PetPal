package com.smartdevices.petpal.db

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(context: Context) : ViewModel() {

    private val roomDB = RoomDB(context) // Local database access

    // State flows for UI updates
    private val _petsList = MutableStateFlow<List<Pet>>(emptyList())
    val petsList: StateFlow<List<Pet>> = _petsList

    private val _currentPet = MutableStateFlow<Pet?>(null)
    val currentPet: StateFlow<Pet?> = _currentPet

    private val _eventsList = MutableStateFlow<List<Event>>(emptyList())
    val eventsList: StateFlow<List<Event>> = _eventsList

    private val _mediaList = MutableStateFlow<List<Media>>(emptyList())
    val mediaList: StateFlow<List<Media>> = _mediaList

    private val _thumbnails = MutableStateFlow<List<Media>>(emptyList())
    val thumbnails: StateFlow<List<Media>> = _thumbnails

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState

    // --- Pets Management ---

    fun loadPets() {
        viewModelScope.launch {
            try {
                _petsList.value = roomDB.getPets()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun getPetById(petId: Int) {
        viewModelScope.launch {
            try {
                _currentPet.value = roomDB.getPet(petId)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun addPet(pet: Pet): Int {
        var newPetId = -1
        viewModelScope.launch {
            try {
                val lastPet = roomDB.getLastPet()
                val newPet = pet.copy(petId = (lastPet?.petId ?: -1) + 1) // Generate new ID
                roomDB.addPet(newPet)
                loadPets() // Refresh pets
                newPetId = newPet.petId
            } catch (e: Exception) {
                handleError(e)
            }
        }
        return newPetId
    }

    fun addPetWithMedia(pet: Pet, media: Media) {
        viewModelScope.launch {
            try {
                val lastPet = roomDB.getLastPet()
                val newPet = pet.copy(petId = (lastPet?.petId ?: -1) + 1) // Generate new ID
                roomDB.addPet(newPet)
                roomDB.addMedia(media.copy(mediaId = (roomDB.getLastMedia()?.mediaId ?: -1) + 1, petId = newPet.petId))
                loadPets() // Refresh pets
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            try {
                roomDB.updatePet(pet)
                loadPets() // Refresh pets
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun deletePet(petId: Int) {
        viewModelScope.launch {
            try {
                val pet = roomDB.getPet(petId)
                pet?.let {
                    roomDB.deletePet(it)
                    loadPets() // Refresh pets
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    // --- Events Management ---

    fun loadEventsForPet(petId: Int) {
        viewModelScope.launch {
            try {
                _eventsList.value = roomDB.getEventsForPet(petId)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun addEventToPet(petId: Int, event: Event) {
        viewModelScope.launch {
            try {
                val lastEvent = roomDB.getLastEvent()
                roomDB.addEvent(event.copy(eventId = (lastEvent?.eventId ?: -1) + 1, petId = petId))
                loadEventsForPet(petId) // Refresh events
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun deleteEventFromPet(event: Event) {
        viewModelScope.launch {
            try {
                roomDB.deleteEvent(event)
                loadEventsForPet(event.petId) // Refresh events
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    // --- Media Management ---

    fun loadMediaForPet(petId: Int) {
        viewModelScope.launch {
            try {
                _mediaList.value = roomDB.getMediaForPet(petId)
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun getThumbnails() {
        viewModelScope.launch {
            try {
                _thumbnails.value = roomDB.getThumbnails()
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun addMediaToPet(petId: Int, media: Media) {
        viewModelScope.launch {
            try {
                val lastMedia = roomDB.getLastMedia()
                val newMedia = media.copy(mediaId = (lastMedia?.mediaId ?: -1) + 1, petId = petId)
                roomDB.addMedia(newMedia)
                loadMediaForPet(petId) // Refresh media
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun updateMedia(media: Media) {
        viewModelScope.launch {
            try {
                roomDB.updateMedia(media)
                loadMediaForPet(media.petId) // Refresh media
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun deleteMedia(media: Media) {
        viewModelScope.launch {
            try {
                roomDB.deleteMedia(media)
                loadMediaForPet(media.petId) // Refresh media
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    // --- Error Handling ---

    private fun handleError(e: Exception) {
        _errorState.value = e.localizedMessage
    }
}
