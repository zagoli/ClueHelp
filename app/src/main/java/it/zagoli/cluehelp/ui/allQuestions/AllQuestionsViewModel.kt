package it.zagoli.cluehelp.ui.allQuestions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.Question
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore

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


    init {
        _questionList.addAll(TempStore.questions)
    }

}