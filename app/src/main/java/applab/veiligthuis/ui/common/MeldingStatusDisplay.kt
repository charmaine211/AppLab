package applab.veiligthuis.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.domain.model.MeldingStatus

@Composable
fun MeldingStatusDisplay(
    modifier: Modifier = Modifier,
    meldingStatus: MeldingStatus = MeldingStatus.ONBEHANDELD,
    ){
        Text (
            text = meldingStatus.status,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = modifier
                .background(color = meldingStatus.color, shape = RoundedCornerShape(45))
                .padding(8.dp, 2.dp).width(80.dp),
        )
}

@Preview
@Composable
fun PreviewOnbehandeld() {
    MeldingStatusDisplay()
}

@Preview
@Composable
fun PreviewInBehandeling() {
    MeldingStatusDisplay(meldingStatus = MeldingStatus.IN_BEHANDELING)
}

@Preview
@Composable
fun PreviewAfgerond() {
    MeldingStatusDisplay(meldingStatus = MeldingStatus.AFGESLOTEN)
}