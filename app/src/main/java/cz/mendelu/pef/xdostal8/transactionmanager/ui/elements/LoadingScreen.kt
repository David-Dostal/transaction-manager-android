package cz.mendelu.pef.xdostal8.transactionmanager.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicMargin
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.basicText
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getBasicTextColor
import cz.mendelu.pef.xdostal8.transactionmanager.R
import cz.mendelu.pef.xdostal8.transactionmanager.ui.theme.getBackGroundColor

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(getBackGroundColor())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(basicMargin())
        ) {


            Image(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(300.dp),
                colorFilter = ColorFilter.tint(getBasicTextColor()),
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "loading screen icon"
            )
            Spacer(modifier = Modifier.height(basicMargin()))
            Text(
                text = stringResource(R.string.loading),
                style = basicText(),
                textAlign = TextAlign.Center,
                color = getBasicTextColor()
            )
        }
    }
}