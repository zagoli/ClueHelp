package it.zagoli.cluehelp.ui.room

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
import it.zagoli.cluehelp.databinding.InsertRoomsFragmentBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.extensions.rooms
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectAdapter
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectClickListener
import it.zagoli.cluehelp.ui.gameObjectsUtils.NavigationStatus
import timber.log.Timber

class InsertRoomsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertRoomsViewModel::class.java)
        val binding = InsertRoomsFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel

        // add new room listener on keyboard event
        binding.newRoomText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.addRoom(binding.newRoomText.text.toString())
                binding.newRoomText.text.clear()
            }
            true
        }

        // add the remove room button listener
        val adapter = GameObjectAdapter(GameObjectClickListener { room ->
            viewModel.removeRoom(room)
        })
        binding.roomsList.adapter = adapter

        //update list on screen
        binding.lifecycleOwner = this
        viewModel.rooms.observe(viewLifecycleOwner, {
            it?.let {
                //ArrayList(it): weird hack because i need to pass a different list,
                //not the same even if it's changed to update the recyclerview
                adapter.submitList(ArrayList(it))
            }
        })

        //navigation to MainGame
        viewModel.navigateMainGameEvent.observe(viewLifecycleOwner, {shouldNavigate ->
            when (shouldNavigate) {
                NavigationStatus.OK -> {
                    binding.root.findNavController().navigate(InsertRoomsFragmentDirections.actionInsertRoomsFragmentToMainGameFragment())
                    viewModel.navigateToMainGameComplete()
                    Timber.i("rooms: ${TempStore.gameObjects.rooms.map(GameObject::name)}")
                    Timber.i("navigation to main game.")
                }
                NavigationStatus.IMPOSSIBLE -> {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.at_least_nine_rooms_needed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    viewModel.navigateToMainGameComplete()
                }
                else -> {}
            }
        })

        return binding.root
    }

}