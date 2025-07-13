package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.manage_currencies

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.roundedCorner
import cz.mendelu.pef.xdostal8.transactionmanager.R

import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.BackIconScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.LoadingScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.MyTextField

import org.koin.androidx.compose.getViewModel


@Composable
fun ManageCurrenciesScreen(
    navigation: INavigationRouter,
    viewModel: ManageCurrenciesViewModel = getViewModel(),
) {
    var data: ManageCurrenciesScreenData by remember {
        mutableStateOf(viewModel.data)
    }
    var loadingScreen = true



    viewModel.manageCurrenciesScreenUIState.value.let {
        when (it) {
            ManageCurrenciesScreenUIState.Default -> {
                loadingScreen = false

            }


            ManageCurrenciesScreenUIState.Saved -> {
                LaunchedEffect(it) {
                    navigation.navigateBack()
                    navigation.navigateBack()

                }
            }
            is ManageCurrenciesScreenUIState.CurrencyChanged -> {
                data = viewModel.data
                loadingScreen = false
            }
        }


        BackIconScreen(appBarTitle = stringResource(R.string.title_manage_currencies),
            onBackClick = {
                navigation.navigateBack()
            }) {
            if (loadingScreen) {
                LoadingScreen()
            } else {
                ManageCurrenciesScreenContent(
                    actions = viewModel, data = data
                )
            }
        }

    }
}


@Composable
fun ManageCurrenciesScreenContent(
    actions: ManageCurrenciesActions, data: ManageCurrenciesScreenData
) {

    MyTextField(
        value = data.currency, label = stringResource(R.string.text_currency), onValueChange = {
            actions.onCurrencyChange(it)
        }, error = if (data.currencyError != null) stringResource(id = data.currencyError!!) else ""
    )
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
}
