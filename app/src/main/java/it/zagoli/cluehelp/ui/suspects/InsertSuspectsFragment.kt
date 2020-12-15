package it.zagoli.cluehelp.ui.suspects

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
import it.zagoli.cluehelp.databinding.InsertSuspectsFragmentBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.extensions.suspects
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectAdapter
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectClickListener
import it.zagoli.cluehelp.ui.NavigationStatus
import timber.log.Timber

class InsertSuspectsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertSuspectsViewModel::class.java)
        val binding = InsertSuspectsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        // add new suspect listener on keyboard event
        binding.newSuspectText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.addSuspect(binding.newSuspectText.text.toString())
                binding.newSuspectText.text.clear()
            }
            true
        }

        // add the remove suspect button listener
        val adapter = GameObjectAdapter(GameObjectClickListener { suspect ->
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

        //navigation to insert weapons
        viewModel.navigateInsertWeaponsEvent.observe(viewLifecycleOwner, {shouldNavigate ->
            when (shouldNavigate) {
                NavigationStatus.OK -> {
                    binding.root.findNavController().navigate(InsertSuspectsFragmentDirections.actionInsertSuspectsFragmentToInsertWeaponsFragment())
                    viewModel.navigateToInsertWeaponsComplete()
                    Timber.i("suspects: ${TempStore.gameObjects.suspects.map(GameObject::name)}")
                    Timber.i("navigation to insert weapons.")
                }
                NavigationStatus.IMPOSSIBLE -> {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.at_least_six_suspects_needed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.navigateToInsertWeaponsComplete()
                }
                else -> {}
            }
        })
        
        return binding.root
    }

}