package it.zagoli.cluehelp.ui.room

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import it.zagoli.cluehelp.databinding.InsertRoomsFragmentBinding
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectAdapter
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectClickListener

class InsertRoomsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(InsertRoomsViewModel::class.java)
        val binding = InsertRoomsFragmentBinding.inflate(inflater)
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

        return binding.root
    }

}