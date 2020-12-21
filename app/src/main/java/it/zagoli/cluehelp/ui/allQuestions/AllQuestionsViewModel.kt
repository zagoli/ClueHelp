package it.zagoli.cluehelp.ui.allQuestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.Question
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore
import timber.log.Timber

class AllQuestionsViewModel : ViewModel() {

    /**
     * backing property for [questionList]
     */
    private val _questionList = MutableLiveList<Question>()

    /**
     * observable list of questions
     */
    val questionList: LiveData<MutableList<Question>>
        get() = _questionList

    fun sortQuestionsByWhoAsks() {
        Timber.i("sorting questions by who asks")
        _questionList.value?.sortBy {
            it.asks.name
        }
        _questionList.notifyObservers()
    }

    fun sortQuestionsBySuspects() {
        Timber.i("sorting questions by suspects")
        _questionList.value?.sortBy {
            it.gameObjects[0].gameObject.name
        }
        _questionList.notifyObservers()
    }

    fun sortQuestionsByWeapons() {
        Timber.i("sorting questions by weapons")
        _questionList.value?.sortBy {
            it.gameObjects[1].gameObject.name
        }
        _questionList.notifyObservers()
    }

    fun sortQuestionsByRooms() {
        Timber.i("sorting questions by rooms")
        _questionList.value?.sortBy {
            it.gameObjects[2].gameObject.name
        }
        _questionList.notifyObservers()
    }

    fun sortQuestionsByWhoAnswers() {
        Timber.i("sorting questions by who answers")
        _questionList.value?.sortBy {
            it.answers.name
        }
        _questionList.notifyObservers()
    }

    init {
        _questionList.addAll(TempStore.questions)
    }

}