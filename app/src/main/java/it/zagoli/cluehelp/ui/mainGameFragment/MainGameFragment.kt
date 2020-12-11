package it.zagoli.cluehelp.ui.mainGameFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import it.zagoli.cluehelp.R

class MainGameFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val viewModel = ViewModelProvider(this).get(MainGameViewModel::class.java)
        return inflater.inflate(R.layout.main_game_fragment, container, false)
    }

}