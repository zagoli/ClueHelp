package it.zagoli.cluehelp.ui.allQuestions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.QuestionListElementBinding
import it.zagoli.cluehelp.domain.Question

class QuestionAdapter: ListAdapter<Question, QuestionViewHolder>(QuestionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class QuestionViewHolder(private val binding: QuestionListElementBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): QuestionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = QuestionListElementBinding.inflate(layoutInflater, parent, false)
            return QuestionViewHolder(binding)
        }
    }

    fun bind(item: Question) {
        binding.question = item
        binding.executePendingBindings()
    }

}