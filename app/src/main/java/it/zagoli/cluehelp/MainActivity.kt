package it.zagoli.cluehelp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import it.zagoli.cluehelp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

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