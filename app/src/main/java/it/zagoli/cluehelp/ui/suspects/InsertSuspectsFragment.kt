package it.zagoli.cluehelp.ui.suspects

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.InsertSuspectsFragmentBinding
import it.zagoli.cluehelp.ui.suspects.SuspectAdapter
import it.zagoli.cluehelp.ui.suspects.SuspectClickListener

class InsertSuspectsFragment : Fragment() {

    private lateinit var viewModel: InsertSuspectsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(InsertSuspectsViewModel::class.java)
        val binding = InsertSuspectsFragmentBinding.inflate(inflater)

        // add new suspect listener on keyboard event
        binding.newSuspectText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.addSuspect(binding.newSuspectText.text.toString())
                binding.newSuspectText.text.clear()
            }
            true
        }

        // add the remove suspect button listener
        val adapter = SuspectAdapter(SuspectClickListener { suspect ->
            viewModel.removeSuspect(suspect)
        })
        binding.suspectsList.adapter = adapter

        //update list on screen
        binding.lifecycleOwner = this
        viewModel.suspects.observe(viewLifecycleOwner, {
            it?.let {
                //ArrayList(it): weird hack because i need to pass a different list,
                //not the same even if it's changed to update the recyclerview
                adapter.submitList(ArrayList(it))
            }
        })
        
        return binding.root
    }

}