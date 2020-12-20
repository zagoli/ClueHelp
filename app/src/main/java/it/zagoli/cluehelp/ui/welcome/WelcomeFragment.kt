package it.zagoli.cluehelp.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import it.zagoli.cluehelp.R

class WelcomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragment = inflater.inflate(R.layout.welcome_fragment, container, false)
        fragment.findViewById<Button>(R.id.start_button).setOnClickListener {
            this.findNavController().navigate(R.id.action_welcomeFragment_to_insertPlayersFragment)
        }
        return fragment
    }

}