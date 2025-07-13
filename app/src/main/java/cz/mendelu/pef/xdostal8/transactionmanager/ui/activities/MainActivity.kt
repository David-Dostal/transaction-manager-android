package cz.mendelu.pef.xdostal8.transactionmanager.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import cz.mendelu.pef.xdostal8.transactionmanager.ui.activities.viewmodels.MainActivityViewModel
import cz.mendelu.pef.xdostal8.transactionmanager.architecture.BaseActivity
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.Destination
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.NavGraph
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.TransactionManagerTheme
import cz.mendelu.pef.xdostal8.transactionmanager.utils.LocaleUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity() : BaseActivity<MainActivityViewModel>(MainActivityViewModel::class.java) {

    override val viewModel: MainActivityViewModel by viewModel()

    companion object {
        fun createIntent(context: AppCompatActivity): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            val context = LocalContext.current
            LaunchedEffect(Unit) {

                LocaleUtils.setLocale(context, viewModel.dataStoreRepository)

            }


            TransactionManagerTheme {
                NavGraph(startDestination = Destination.TransactionListScreen.route)
            }
        }
    }
}