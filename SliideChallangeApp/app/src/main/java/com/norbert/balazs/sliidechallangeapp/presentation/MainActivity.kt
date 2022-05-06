package com.norbert.balazs.sliidechallangeapp.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.common.base.BaseActivity
import com.norbert.balazs.sliidechallangeapp.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityBinding>() {

    private val viewModel: MainViewModel by viewModels()

    override fun getLayoutId() = R.layout.main_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        showSplashScreen()
        super.onCreate(savedInstanceState)
        initToolbar()
        configureNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.menu_item_refresh) {
            viewModel.loadUsersAsync()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun showSplashScreen() {
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = getString(R.string.home)
        setSupportActionBar(toolbar)
    }

    private fun configureNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        layout.toolbar.setupWithNavController(navController)
    }
}