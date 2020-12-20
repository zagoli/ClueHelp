package it.zagoli.cluehelp.ui.allQuestions

import androidx.recyclerview.widget.DiffUtil
import it.zagoli.cluehelp.domain.Question

class QuestionDiffCallback: DiffUtil.ItemCallback<Question>() {

    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem == newItem
    }

}
