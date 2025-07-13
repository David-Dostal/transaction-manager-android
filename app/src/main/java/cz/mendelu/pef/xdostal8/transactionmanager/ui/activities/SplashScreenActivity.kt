package cz.mendelu.pef.xdostal8.transactionmanager.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.states.SplashScreenUiState
import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels.SplashScreenActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }

        lifecycleScope.launchWhenResumed {
            viewModel.splashScreenState.collect { value ->
                when (value) {
                    is SplashScreenUiState.Default -> {
                        viewModel.checkAppState()
                    }
                    SplashScreenUiState.ContinueToApp -> {
                        continueToAList()
                    }
                    is SplashScreenUiState.RunForAFirstTime -> {
                        runAppIntro()
                    }
                }
            }
        }

    }

    private fun runAppIntro() {
        startActivity(AppIntroActivity.createIntent(this))
        finish()
    }

    private fun continueToAList() {
        startActivity(MainActivity.createIntent(this))
        finish()
    }
}

