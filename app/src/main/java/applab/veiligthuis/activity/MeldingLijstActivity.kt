package applab.veiligthuis.activity



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import applab.veiligthuis.model.Melding
import applab.veiligthuis.ui.theme.*
import applab.veiligthuis.viewmodel.MeldingenLijstViewModel

class MeldingLijstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                meldingLijstScreen(MeldingenLijstViewModel())
            }
        }
    }
}

@Composable
fun meldingLijstScreen(meldingenLijstViewModel: MeldingenLijstViewModel = MeldingenLijstViewModel(), modifier: Modifier = Modifier.padding(1.dp)) {

    val viewModel = meldingenLijstViewModel

    val meldingenLijst = viewModel.meldingenLijst.observeAsState()

    Scaffold(
        topBar = { topBar() },
        content = { padding -> meldingenLijst.value?.let { meldingList(meldingList = it, modifier = modifier.padding(padding)) } },
        bottomBar = {
            BottomAppBar(modifier = modifier.fillMaxHeight(0.1F), backgroundColor = Color.White, elevation = 0.dp) {
                Row(horizontalArrangement = Arrangement.Center, modifier = modifier.fillMaxWidth()){
                    Text(
                        text = "Privacy verklaring",
                    )
                }

            }
        },
        modifier = modifier.padding(22.dp, 10.dp)
    )
}

@Composable
fun topBar(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .padding(5.dp)
                .background(Color.White),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 50.dp, top = 4.dp)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = blue_rect,
                    modifier = modifier
                        .size(48.dp)
                        .padding(start = 4.dp)
                )
                Icon(Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = blue_rect,
                    modifier = modifier
                        .size(48.dp)
                        .padding(end = 4.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ){
                Row() {
                    Text(
                        text = "Inkomend",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = modifier
                            .background(color = blue_rect, shape = RoundedCornerShape(50))
                            .padding(15.dp, 5.dp)

                    )
                    Text(
                        text = "Afgesloten",
                        fontSize = 12.sp,
                        color = Color.White,
                        modifier = modifier
                            .background(color = grey_rect, shape = RoundedCornerShape(50))
                            .padding(15.dp, 5.dp)
                    )


                }
                Text(
                    text = "Filter",
                    fontSize = 12.sp,
                    color = Color.White,
                    modifier = modifier
                        .background(color = blue_filter, shape = RoundedCornerShape(50))
                        .padding(15.dp, 5.dp)

                )
            }
            Divider(
                color = Color.Black,
                modifier = modifier
                    .padding(4.dp, 15.dp, 4.dp, 0.dp),
                thickness = 1.dp
            )
        }

}


@Composable
fun meldingList(meldingList: List<Melding>, modifier: Modifier = Modifier){
    LazyColumn(){
        items(meldingList){ melding ->
            meldingCard(datum = "placeholder", shortDescription = melding.meldingInfo, status = "Onbehandeld", anoniem = melding.isAnomiem)
        }
    }
}

@Composable
fun meldingCard(
    datum: String,
    shortDescription: String,
    status: String,
    anoniem: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = 4.dp

    ) {
        Column(
            modifier = modifier.padding(4.dp),
            //horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth(0.6F)
                    .padding(start = 5.dp)){
                Text(
                    text = datum,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                var anoniemText: String
                if (anoniem){
                    anoniemText = "Anoniem"
                } else {
                    anoniemText = "Niet Anoniem"
                }

                Text(
                    text = anoniemText,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                Text(
                    text = shortDescription,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = modifier
                .padding(end = 5.dp)
                //.align(Alignment.CenterVertically)
                ){
                Text(
                    text = status,
                    fontSize = 10.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(color = status_onbehandeld, shape = RoundedCornerShape(45))
                        .padding(8.dp, 2.dp),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun previewMeldingCard() {
    AppTheme {
        meldingCard(datum = "23 maart 2023 - 18.19", shortDescription = "Mijn melding gaat over...", status = "Onbehandeld", anoniem = true)
    }
}

@Preview
@Composable
fun previewTopbar() {
    AppTheme {
        topBar()
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingList() {
    AppTheme {
        var meldingen : List<Melding> = listOf(Melding(), Melding(), Melding(), Melding(), Melding(), Melding(), Melding())
        meldingList(meldingList = meldingen)
    }
}

@Preview(showBackground = true)
@Composable
fun previewMeldingenScreen() {
    AppTheme {
        meldingLijstScreen(meldingenLijstViewModel = MeldingenLijstViewModel())
    }
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
     MaterialTheme(
         colors = lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
         ),
        typography = Typography(
            body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )),
        content = content
     )
}
