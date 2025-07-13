package cz.mendelu.pef.xdostal8.transactionmanager.ui.screens.export

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.halfMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.roundedCorner
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.navigation.INavigationRouter
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.BackIconScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.LoadingScreen
import cz.mendelu.pef.xdostal8.transactionmanager.ui.elements.MyDropDownMenu
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.smallText
import org.koin.androidx.compose.getViewModel

@Composable
fun ExportScreen(
    navigation: INavigationRouter,
    viewModel: ExportScreenViewModel = getViewModel(),
) {
    var data: ExportScreenData by remember {
        mutableStateOf(viewModel.data)
    }

    var loadingScreen = true


    viewModel.exportScreenUIState.value.let {
        when (it) {
            ExportScreenUIState.Default -> {
            }
            ExportScreenUIState.Loaded -> {
                data = viewModel.data
                loadingScreen = false
            }
            is ExportScreenUIState.ExportSettingsChanged -> {
                data = viewModel.data
                loadingScreen = false

            }
            ExportScreenUIState.Saved -> {
                LaunchedEffect(it) {
                    navigation.navigateBack()
                }
            }
            ExportScreenUIState.Loading -> {
                LaunchedEffect(it) {
                    viewModel.loadFormat()
                }
            }
        }
    }






    BackIconScreen(appBarTitle = stringResource(R.string.title_export), onBackClick = {
        navigation.navigateBack()
    }) {
        if (loadingScreen) {
            LoadingScreen()
        } else {
            ExportScreenContent(
                actions = viewModel, data = data
            )
        }
    }

}

@Composable
fun ExportScreenContent(
    actions: ExportScreenActions, data: ExportScreenData
) {
    val formats = listOf("JPEG", "PNG")


    Box {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = stringResource(R.string.text_quality) + " (" + data.quality.toString() + "%)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = data.quality.toFloat(),
                onValueChange = { actions.onQualityChange(it.toInt()) },
                valueRange = 0f..100f,
                steps = 25,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Spacer(modifier = Modifier.height(halfMargin()))


    MyDropDownMenu(
        options = formats, currentValue = data.format, onValueChange = { newFormat ->
            data.format = newFormat
            actions.onFormatChange(newFormat)
        }, label = R.string.text_format, iconDescription = R.string.description_format_dropdown
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
    Spacer(modifier = Modifier.height(basicMargin()))


    Text(
        text = stringResource(R.string.click_on_graph_title),
        textAlign = TextAlign.Center,
        style = smallText(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    )
}