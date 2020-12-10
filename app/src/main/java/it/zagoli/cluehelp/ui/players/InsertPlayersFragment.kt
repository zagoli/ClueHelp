package it.zagoli.cluehelp.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.InsertPlayersFragmentBinding
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.gameObjectsUtils.NavigationStatus
import timber.log.Timber

class InsertPlayersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertPlayersViewModel::class.java)
        val binding = InsertPlayersFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        // add new player listener on keyboard event
        binding.newPlayerText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.addPlayer(binding.newPlayerText.text.toString())
                binding.newPlayerText.text.clear()
            }
            true
        }

        // add the remove player button listener
        val adapter = PlayerAdapter(PlayerClickListener { player ->
            viewModel.removePlayer(player)
        })
        binding.playersList.adapter = adapter

        //update list on screen
        binding.lifecycleOwner = this
        viewModel.players.observe(viewLifecycleOwner, {
            it?.let {
                //ArrayList(it): weird hack because i need to pass a different list,
                //not the same even if it's changed to update the recyclerview
                adapter.submitList(ArrayList(it))
            }
        })

        //navigation to insert suspects
        viewModel.navigateInsertSuspectsEvent.observe(viewLifecycleOwner, { shouldNavigate ->
            when (shouldNavigate) {
                NavigationStatus.OK -> {
                    binding.root.findNavController().navigate(InsertPlayersFragmentDirections.actionInsertPlayersFragmentToInsertSuspectsFragment())
                    viewModel.navigateToInsertSuspectsComplete()
                    Timber.i("navigation to insert suspects.")
                    Timber.i("players: ${TempStore.players.map(Player::name)}")
                }
                NavigationStatus.IMPOSSIBLE -> {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.at_least_two_players_needed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.navigateToInsertSuspectsComplete()
                }
                else -> {}
            }
        })

        return binding.root
    }

}