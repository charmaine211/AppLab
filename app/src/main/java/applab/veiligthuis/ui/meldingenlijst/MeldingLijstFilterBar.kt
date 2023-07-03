package applab.veiligthuis.ui.meldingenlijst

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.R
import applab.veiligthuis.ui.common.CustomSwitchThumb

import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.theme.filter_grey
import applab.veiligthuis.ui.theme.veilig_thuis_blauw

@Composable
fun FilterButtonsBar(
    toggleInkomendSelected: (Boolean) -> Unit,
    onClickExpandFilter: () -> Unit,
    selectedFiltersCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(11.dp, 40.dp, 11.dp, 25.dp)
        ) {
            CustomSwitchThumb(
                leftText = stringResource(R.string.filter_inkomend),
                rightText = stringResource(R.string.filter_afgesloten),
                trackColor = filter_grey,
                thumbColor = veilig_thuis_blauw,
                textColor = Color.White,
                trackWidth = 200.dp, trackHeight = 30.dp,
                thumbWidth = 100.dp,
                onSwipe = toggleInkomendSelected,
            )
            Button(
                onClick = onClickExpandFilter,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = filter_blue),
                modifier = modifier.height(30.dp)
            ) {
                if (selectedFiltersCount == 0) {
                    Text(
                        text = "Filter",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Filters: $selectedFiltersCount",
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUnexpandedBar() {
    FilterButtonsBar({ }, { }, selectedFiltersCount = 2)
}