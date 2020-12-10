package it.zagoli.cluehelp.ui.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.zagoli.cluehelp.ClueHelpApplication
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.extensions.MutableLiveList
import timber.log.Timber

class InsertRoomsViewModel(application: Application) : AndroidViewModel(application) { //init block below!
    /**
     * backing property for [rooms]
     */
    private val _rooms = MutableLiveList<GameObject>()

    /**
     * list of [GameObject] of room type
     */
    val rooms: LiveData<MutableList<GameObject>>
        get() = _rooms

    /**
     * add new room in [_rooms]
     * @param roomName the name of the room
     */
    fun addRoom(roomName: String) {
        if (roomName.isNotBlank() && roomName.isNotEmpty()) {
            val room = GameObject(roomName, GameObjectType.ROOM)
            _rooms.addElementToList(room)
            Timber.i("room $roomName added")
        } else {
            Timber.i("empty or blank text, room not added")
        }
    }

    /**
     * remove room from [_rooms]
     * @param room the [GameObject] that represents the room you want to remove
     */
    fun removeRoom(room: GameObject) {
        _rooms.removeElementFromList(room)
        Timber.i("room ${room.name} deleted")
    }

    /**
     * loads the default rooms in [_rooms]
     */
    private fun loadDefaultRooms() {
        val defaultRooms = loadRoomsNamesFromResources().map { name ->
                GameObject(name, GameObjectType.ROOM)
            }
        _rooms.addAll(defaultRooms)
        Timber.i("default rooms loaded")
    }

    private fun loadRoomsNamesFromResources(): List<String> {
        val app = getApplication<ClueHelpApplication>()
        return listOf(
            app.getString(R.string.Courtyard),
            app.getString(R.string.Game_Room),
            app.getString(R.string.Study),
            app.getString(R.string.Dining_Room),
            app.getString(R.string.Garage),
            app.getString(R.string.Living_Room),
            app.getString(R.string.Kitchen),
            app.getString(R.string.Bedroom),
            app.getString(R.string.Bathroom)
        )
    }

    init {
        loadDefaultRooms()
    }
}