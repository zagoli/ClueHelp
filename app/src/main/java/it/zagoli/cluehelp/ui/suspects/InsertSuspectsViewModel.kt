package it.zagoli.cluehelp.ui.suspects

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.extensions.MutableLiveList
import timber.log.Timber

class InsertSuspectsViewModel : ViewModel() { //init block below!
    /**
     * backing property for [suspects]
     */
    private val _suspects = MutableLiveList<GameObject>()

    /**
     * list of [GameObject] of suspect type
     */
    val suspects: LiveData<MutableList<GameObject>>
        get() = _suspects

    /**
     * add new suspect in [_suspects]
     * @param suspectName the name of the suspect
     */
    fun addSuspect(suspectName: String) {
        if (suspectName.isNotBlank() && suspectName.isNotEmpty()) {
            val suspect = GameObject(suspectName, GameObjectType.SUSPECT)
            _suspects.addElementToList(suspect)
            Timber.i("suspect $suspectName added")
        } else {
            Timber.i("empty or blank text, suspect not added")
        }
    }

    fun removeSuspect(suspect: GameObject) {
        _suspects.removeElementFromList(suspect)
        Timber.i("suspect ${suspect.name} deleted")
    }

    private fun loadDefaultSuspects() {
        val defaultSuspects = listOf("Plum", "White", "Scarlet", "Green", "Mustard", "Peacock")
            .map { name ->
                GameObject(name, GameObjectType.SUSPECT)
            }
        _suspects.addAll(defaultSuspects)
        Timber.i("default suspects loaded")
    }

    init {
        loadDefaultSuspects()
    }
}