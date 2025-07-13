package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xdostal8.transactionmanager.BuildConfig
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.roundedCorner
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.BackIconScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.LoadingScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.MyDropDownMenu
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.smallText
import org.koin.androidx.compose.getViewModel
import java.util.*


@Composable
fun SettingsScreen(
    navigation: INavigationRouter,
    viewModel: SettingsScreenViewModel = getViewModel(),
) {
    var data: SettingsScreenData by remember {
        mutableStateOf(viewModel.data)
    }

    var loadingScreen = true


    viewModel.settingsScreenUIState.value.let {
        when (it) {
            SettingsScreenUIState.Default -> {
                LaunchedEffect(it) {
                    viewModel.loadLanguage()
                }
            }
            SettingsScreenUIState.Loaded -> {
                loadingScreen = false
            }
            is SettingsScreenUIState.SettingsChanged -> {
                data = viewModel.data
                loadingScreen = false

            }
            SettingsScreenUIState.Saved -> {
                SetAppLanguage(language = data.language)
                LaunchedEffect(it) {
                    navigation.navigateBack()
                }
            }
        }
    }


    BackIconScreen(

        appBarTitle = stringResource(R.string.title_settings), onBackClick = {
            navigation.navigateBack()
        }) {
        if (loadingScreen) {
            LoadingScreen()
        } else {
            SettingsScreenContent(
                data = data, actions = viewModel, navigation = navigation
            )
        }
    }
}

@Composable
fun SettingsScreenContent(
    actions: SettingsScreenActions, data: SettingsScreenData, navigation: INavigationRouter
) {


    val languages = listOf("us", "cs")

    val currencies = listOf("Kč", "$", "€")






    MyDropDownMenu(
        options = languages,
        currentValue = data.language,
        onValueChange = { actions.onLanguageChange(it) },
        label = R.string.text_language,
        iconDescription = R.string.description_language_dropdown
    )

    Spacer(modifier = Modifier.height(basicMargin()))


    MyDropDownMenu(
        options = currencies,
        currentValue = data.currency,
        onValueChange = { actions.onCurrencyChange(it) },
        label = R.string.text_currency,
        iconDescription = R.string.description_currency_dropdown,
    )
    Spacer(modifier = Modifier.height(basicMargin()))


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        OutlinedButton(

            onClick = { navigation.navigateToManageCurrenciesScreen() },
            shape = RoundedCornerShape(roundedCorner()),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White, containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(text = stringResource(R.string.navigate_to_manage_currencies))
        }
    }


    Spacer(modifier = Modifier.height(basicMargin()))
    Spacer(modifier = Modifier.height(basicMargin()))



    OutlinedButton(
        modifier = Modifier.fillMaxWidth(), onClick = {
            actions.saveChanges()
        }, shape = RoundedCornerShape(roundedCorner()), colors = ButtonDefaults.buttonColors(
            contentColor = Color.White, containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = stringResource(R.string.save))
    }

    Spacer(modifier = Modifier.height(basicMargin()))



    DisplayVersion()


}

@Composable
fun SetAppLanguage(language: String) {
    val context = LocalContext.current
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    context.createConfigurationContext(configuration)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

@Composable
fun DisplayVersion() {

    Column(
        modifier = Modifier.padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.version_text),

            textAlign = TextAlign.Center,
            style = smallText(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        val version =
            stringResource(R.string.version_name) + " " + BuildConfig.VERSION_NAME + "\n" + stringResource(
                R.string.version_code
            ) + " " + BuildConfig.VERSION_CODE.toString()

        Text(
            text = version, textAlign = TextAlign.Center, style = smallText(),


            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}