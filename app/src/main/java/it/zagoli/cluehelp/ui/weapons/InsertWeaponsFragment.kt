package it.zagoli.cluehelp.ui.weapons

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import it.zagoli.cluehelp.databinding.InsertWeaponsFragmentBinding
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectAdapter
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectClickListener

class InsertWeaponsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertWeaponsViewModel::class.java)
        val binding = InsertWeaponsFragmentBinding.inflate(inflater)
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

        return binding.root
    }

}