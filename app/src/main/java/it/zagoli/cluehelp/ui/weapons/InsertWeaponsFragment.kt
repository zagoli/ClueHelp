package it.zagoli.cluehelp.ui.weapons

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.databinding.InsertWeaponsFragmentBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectAdapter
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectClickListener
import it.zagoli.cluehelp.ui.gameObjectsUtils.NavigationStatus
import it.zagoli.cluehelp.ui.suspects.InsertSuspectsFragmentDirections
import timber.log.Timber

class InsertWeaponsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertWeaponsViewModel::class.java)
        val binding = InsertWeaponsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        // add new weapon listener on keyboard event
        binding.newWeaponText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.addWeapon(binding.newWeaponText.text.toString())
                binding.newWeaponText.text.clear()
            }
            true
        }

        // add the remove weapon button listener
        val adapter = GameObjectAdapter(GameObjectClickListener { weapon ->
            viewModel.removeWeapon(weapon)
        })
        binding.weaponsList.adapter = adapter

        //update list on screen
        binding.lifecycleOwner = this
        viewModel.weapons.observe(viewLifecycleOwner, {
            it?.let {
                //ArrayList(it): weird hack because i need to pass a different list,
                //not the same even if it's changed to update the recyclerview
                adapter.submitList(ArrayList(it))
            }
        })

        //navigation to insert rooms
        viewModel.navigateInsertRoomsEvent.observe(viewLifecycleOwner, {shouldNavigate ->
            when (shouldNavigate) {
                NavigationStatus.OK -> {
                    binding.root.findNavController().navigate(InsertWeaponsFragmentDirections.actionInsertWeaponsFragmentToInsertRoomsFragment())
                    viewModel.navigateToInsertRoomsComplete()
                    Timber.i("navigation to insert rooms.")
                    Timber.i("weapons: ${TempStore.gameObjects.filter{ g -> g.type == GameObjectType.WEAPON}.map(GameObject::name)}")
                }
                NavigationStatus.IMPOSSIBLE -> {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.at_least_six_weapons_needed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.navigateToInsertRoomsComplete()
                }
                else -> {}
            }
        })

        return binding.root
    }

}