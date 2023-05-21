package applab.veiligthuis.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import applab.veiligthuis.R

@Composable
fun veiligThuisToolbar() {
    TopAppBar(
        title = {},
        backgroundColor =  Color.White,
        navigationIcon = {
            IconButton(onClick = {/* Do Something*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.veiligthuis_logo),
                    contentDescription = "Veilig thuis logo knop",
                    modifier = Modifier
                        .padding(start=15.dp, top=3.dp, end=15.dp, bottom=3.dp)
                        .height(56.dp)
                        .width(56.dp)
                )
            }
        }, actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Image(
                    painter = painterResource(id = R.drawable.veiligthuis_gebruiker),
                    contentDescription = "Veilig thuis gebruiker knop",
                    modifier = Modifier
                        .padding(start=3.dp, top=3.dp, end=15.dp, bottom=3.dp)
                        .height(56.dp)
                        .width(56.dp)
                )
            }
        },
        modifier = Modifier
            .height(72.dp)
            .fillMaxWidth(),
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun previewToolbar() {
    veiligThuisToolbar()
}