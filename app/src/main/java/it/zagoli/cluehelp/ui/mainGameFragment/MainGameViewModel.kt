package it.zagoli.cluehelp.ui.mainGameFragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.ClueHelpApplication
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.domain.Question
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore
import timber.log.Timber

/**
 * viewModel for MainGameFragment, AddQuestionFragment
 */
class MainGameViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * list of players
     */
    val players = TempStore.players

    /**
     * the player that represents nobody. We store it here so it's created only once
     */
    private val nobody = Player(getApplication<ClueHelpApplication>().getString(R.string.player_nobody))

    /**
     * an array with the placeholder and nobody player for the spinners
     */
    val playersWithPlaceholderAndNobodyArray : Array<Player>
        get() {
            return mutableListOf(Player("")).let {
                it.addAll(players)
                it.add(nobody)
                it.toTypedArray()
            }
        }

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
        Timber.i("new object owner: ${gameObject.owner?.name} for object ${gameObject.name}")
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