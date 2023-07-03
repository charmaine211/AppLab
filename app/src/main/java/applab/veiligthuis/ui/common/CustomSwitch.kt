package applab.veiligthuis.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import applab.veiligthuis.ui.theme.AppTheme
import applab.veiligthuis.ui.theme.filter_blue
import applab.veiligthuis.ui.theme.filter_grey
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomSwitchThumb(
    leftText: String,
    rightText: String,
    trackColor: Color,
    thumbColor: Color,
    textColor: Color,
    trackWidth: Dp,
    trackHeight: Dp,
    thumbWidth: Dp,
    onSwipe: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { thumbWidth.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)

    Box(
        modifier = modifier
            .width(trackWidth)
            .height(trackHeight)
            .background(color = trackColor, shape = RoundedCornerShape(50))
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            ),
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .width(thumbWidth)
                .height(trackHeight)
                .background(color = thumbColor, shape = RoundedCornerShape(50)),
        )
        Row {
            Box(
                modifier = Modifier
                    .width(thumbWidth)
                    .height(30.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = leftText, color = textColor)
            }
            Box(
                modifier = Modifier
                    .width(thumbWidth)
                    .height(30.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = rightText, color = textColor)
            }
        }
    }
    LaunchedEffect(swipeableState.currentValue) {
        onSwipe(swipeableState.currentValue == 0)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomSwitchThumb() {
    AppTheme {
        CustomSwitchThumb(
            "Inkomend",
            "Afgesloten",
            filter_grey,
            filter_blue,
            Color.White,
            200.dp,
            30.dp,
            100.dp,
            {})
    }
}
