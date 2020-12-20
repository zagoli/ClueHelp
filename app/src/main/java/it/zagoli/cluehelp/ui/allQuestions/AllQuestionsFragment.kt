package it.zagoli.cluehelp.ui.allQuestions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import it.zagoli.cluehelp.databinding.AllQuestionsFragmentBinding

class AllQuestionsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(AllQuestionsViewModel::class.java)
        val binding = AllQuestionsFragmentBinding.inflate(inflater)

        // adapter for recyclerView
        val adapter = QuestionAdapter()
        binding.questionsList.adapter = adapter

        // update screen with changed question list
        binding.lifecycleOwner = this
        viewModel.questionList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(ArrayList(it))
            }
        })

        return binding.root
    }

}