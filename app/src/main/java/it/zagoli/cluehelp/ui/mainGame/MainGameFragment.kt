package it.zagoli.cluehelp.ui.mainGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import it.zagoli.cluehelp.databinding.MainGameFragmentBinding
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.mainGame.adapters.GameObjectMainGameAdapter
import timber.log.Timber

class MainGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel : MainGameViewModel by activityViewModels() //shared viewModel
        val binding = MainGameFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this

        // adapter for the recyclerview of gameObjects
        val gameObjectMainGameAdapter = GameObjectMainGameAdapter(
            viewModel = viewModel,
            // definition of an array adapter to show the players in the spinner
            playerAdapter = ArrayAdapter<Player>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                viewModel.playersWithPlaceholderAndNobodyArray
            ),
            // we need to pass the players only to calculate the position of selected player in the spinner
            players = viewModel.playersWithPlaceholderAndNobodyArray
        )
        binding.gameObjectList.adapter = gameObjectMainGameAdapter

        // update the gameObjects recyclerview on screen
        viewModel.gameObjects.observe(viewLifecycleOwner, {
            it?.let {
                gameObjectMainGameAdapter.addHeadersAndSubmitList(it)
            }
        })

        // navigation to addQuestionFragment
        binding.addQuestionFab.setOnClickListener {
            binding.root.findNavController().navigate(MainGameFragmentDirections.actionMainGameFragmentToAddQuestionFragment())
            Timber.i("Navigation to add question")
        }

        return binding.root
    }

}