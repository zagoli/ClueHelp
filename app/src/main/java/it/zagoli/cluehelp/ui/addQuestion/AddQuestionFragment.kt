package it.zagoli.cluehelp.ui.addQuestion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.AddQuestionFragmentBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.extensions.rooms
import it.zagoli.cluehelp.extensions.suspects
import it.zagoli.cluehelp.extensions.weapons
import it.zagoli.cluehelp.ui.NavigationStatus
import it.zagoli.cluehelp.ui.mainGame.MainGameViewModel

class AddQuestionFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: MainGameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AddQuestionFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

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
        binding.whoAskedSpinner.onItemSelectedListener = this

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
        binding.suspectSpinner.onItemSelectedListener = this

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
        binding.weaponSpinner.onItemSelectedListener = this

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
        binding.roomSpinner.onItemSelectedListener = this

        // who answered spinner
        binding.whoAnsweredSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            viewModel.playersWithPlaceholderAndNobodyArray
        )
        binding.whoAnsweredSpinner.onItemSelectedListener = this

        // navigation to main game
        viewModel.navigationAddQuestionToMainGameEvent.observe(viewLifecycleOwner, { shouldNavigate ->
            when (shouldNavigate) {
                NavigationStatus.OK -> {
                    viewModel.navigateToMainGameAndAddQuestion()
                    binding.root.findNavController().navigate(AddQuestionFragmentDirections.actionAddQuestionFragmentToMainGameFragment())
                    viewModel.questionAddedNavigationComplete()
                }
                NavigationStatus.IMPOSSIBLE -> {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.fill_all_question_fields),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.questionAddedNavigationComplete()
                }
                else -> {}
            }
        })

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // check if the selected item event was triggered by creation or placeholder was selected
        var invalidSelection = false
        when (val o = parent.getItemAtPosition(position)) {
            is Player -> invalidSelection = (o.name == "")
            is GameObject -> invalidSelection = (o.name == "")
        }
        if (!invalidSelection) {
            when (parent.id) {
                R.id.who_asked_spinner -> viewModel.questionAsks = (parent.getItemAtPosition(position) as Player)
                R.id.suspect_spinner -> viewModel.questionSuspect = (parent.getItemAtPosition(position) as GameObject)
                R.id.weapon_spinner -> viewModel.questionWeapon = (parent.getItemAtPosition(position) as GameObject)
                R.id.room_spinner -> viewModel.questionRoom = (parent.getItemAtPosition(position) as GameObject)
                R.id.who_answered_spinner -> viewModel.questionAnswers = (parent.getItemAtPosition(position) as Player)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}