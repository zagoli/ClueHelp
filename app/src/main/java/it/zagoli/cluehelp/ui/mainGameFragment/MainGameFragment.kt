package it.zagoli.cluehelp.ui.mainGameFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import it.zagoli.cluehelp.databinding.MainGameFragmentBinding
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.TempStore

class MainGameFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(MainGameViewModel::class.java)
        val binding = MainGameFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val playersList = TempStore.players

        // adapter for the recyclerview of gameObjects
        val gameObjectMainGameAdapter = GameObjectMainGameAdapter(
            viewModel = viewModel,
            // definition of an array adapter to show the players in the spinner
            playerAdapter = ArrayAdapter<Player>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                //players plus a placeholder player that will go in the spinner
                mutableListOf(Player("")).let {
                    it.addAll(playersList)
                    it.toTypedArray()
                }
            )
        )
        binding.gameObjectList.adapter = gameObjectMainGameAdapter

        // update the gameObjects recyclerview on screen
        viewModel.gameObjects.observe(viewLifecycleOwner, {
            it?.let {
                // same hack as always
                gameObjectMainGameAdapter.submitList(ArrayList(it))
            }
        })

        return binding.root
    }

}