package it.zagoli.cluehelp.ui.mainGame

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.MainGameFragmentBinding
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.mainGame.adapters.GameObjectMainGameAdapter
import timber.log.Timber

class MainGameFragment : Fragment() {

    // shared viewModel
    val viewModel: MainGameViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
            binding.root.findNavController().navigate(R.id.action_mainGameFragment_to_addQuestionFragment)
            Timber.i("Navigation to add question")
        }

        // navigation to allQuestionsFragment
        viewModel.navigationAllQuestionsEvent.observe(viewLifecycleOwner, { shouldNavigate ->
            if (shouldNavigate) {
                binding.root.findNavController().navigate(R.id.action_mainGameFragment_to_allQuestionsFragment)
                viewModel.onNavigatedAllQuestions()
            }
        })

        // menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.maingame_navigation_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_all_questions -> viewModel.navigateAllQuestions()
        }
        return true
    }
}