package it.zagoli.cluehelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import it.zagoli.cluehelp.databinding.MainActivityBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    companion object {
        /**
         * used to store the players list temporarily.
         * I prefer this method to passing this in the bundle.
         * Assigned one time only
         */
        lateinit var players: List<Player>
        /**
         * used to store the players list temporarily.
         * it's a [MutableList] because we need to incrementally add values to it (three times)
         */
        var gameObjects: MutableList<GameObject> = mutableListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        setSupportActionBar(binding.myToolbar)
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(binding.myToolbar, navController)
    }

    override fun onSupportNavigateUp() = this.findNavController(R.id.nav_host_fragment).navigateUp()


}