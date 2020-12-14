package it.zagoli.cluehelp.ui.addQuestion

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.AddQuestionFragmentBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.extensions.rooms
import it.zagoli.cluehelp.extensions.suspects
import it.zagoli.cluehelp.extensions.weapons
import it.zagoli.cluehelp.ui.mainGameFragment.MainGameViewModel

class AddQuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel = ViewModelProvider(this).get(MainGameViewModel::class.java)
        val binding = AddQuestionFragmentBinding.inflate(inflater)

        // who asked spinner
        binding.whoAskedSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            // players with placeholder
            mutableListOf(Player("")).let {
                it.addAll(viewModel.players)
                it.toTypedArray()
            }
        )

        // suspect spinner
        binding.suspectSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            // suspects with placeholder
            mutableListOf(GameObject("", GameObjectType.SUSPECT)).let {
                viewModel.gameObjects.value?.let { gameObjects -> it.addAll(gameObjects.suspects) }
                it.toTypedArray()
            }
        )

        // weapon spinner
        binding.weaponSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            // weapons with placeholder
            mutableListOf(GameObject("", GameObjectType.WEAPON)).let {
                viewModel.gameObjects.value?.let { gameObjects -> it.addAll(gameObjects.weapons) }
                it.toTypedArray()
            }
        )

        // room spinner
        binding.roomSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            // rooms with placeholder
            mutableListOf(GameObject("", GameObjectType.ROOM)).let {
                viewModel.gameObjects.value?.let { gameObjects -> it.addAll(gameObjects.rooms) }
                it.toTypedArray()
            }
        )

        // who answered spinner
        binding.whoAnsweredSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.playersWithPlaceholderAndNobodyArray
        )

        return binding.root
    }

}