package it.zagoli.cluehelp.ui.mainGameFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Question
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore

class MainGameViewModel : ViewModel() {

    /**
     * backing property for [gameObjects]
     */
    private val _gameObjects = MutableLiveList<GameObject>()

    /**
     * holds the main list of [GameObject]
     */
    val gameObjects: LiveData<MutableList<GameObject>>
        get() = _gameObjects

    /**
     * container for all the questions
     */
    private val questionList: MutableList<Question> = mutableListOf()

    /**
     * this function performs the calculations after a new owner for one
     * object is discovered.
     * @param gameObject is the object with the new owner
     */
    fun newObjectOwnerDiscovered(gameObject: GameObject) {

    }

    /**
     * this function performs the calculations after a new question is added
     * @param question the new question added
     */
    fun newQuestionAdded(question: Question) {

    }


    init {
        // initialize the list with the previously collected data
        _gameObjects.addAll(TempStore.gameObjects)
    }

}