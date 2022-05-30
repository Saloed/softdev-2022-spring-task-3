package view

import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.io.File


class InitialWindow {
    @Composable
    fun InitialView() {
        if (show.value == 0) {
            Box {
                Image(
                    painter = BitmapPainter(image = remember(File("src/main/resources/drawable/bg.jpg")) {
                        loadImageBitmap(File("src/main/resources/drawable/bg.jpg").inputStream()) }),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize(),
                    contentDescription = null
                )
                Column(Modifier.fillMaxSize(), Arrangement.spacedBy(10.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "2048",
                        style = TextStyle(
                            color = Color.DarkGray,
                            fontSize = 185.sp,
                            fontStyle = FontStyle.Italic,
                            letterSpacing = 10.sp,
                            textAlign = TextAlign.Center,
                            shadow = Shadow(color = Color(250, 163, 245), blurRadius = 10f, offset = Offset(5f, 5f)),
                        )
                    )
                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = "Choose the field size",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 50.sp,
                                letterSpacing = 5.sp,
                                textAlign = TextAlign.Center,
                                shadow = Shadow(color = Color(250, 163, 245), blurRadius = 5f, offset = Offset(5f, 5f))
                            )
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            FormatButton("3x3")
                            FormatButton("4x4")
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            FormatButton("5x5")
                            FormatButton("6x6")
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun FormatButton(text: String) {
        Button(
            modifier = Modifier.size(180.dp),
            onClick = {
                when (text) {
                    "3x3" -> show.value = 3
                    "4x4" -> show.value = 4
                    "5x5" -> show.value = 5
                    "6x6" -> show.value = 6
                }
            },
            shape = RoundedCornerShape(50.dp),
            border = BorderStroke(5.dp, Color.DarkGray),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(237, 197, 245))
        ) {
            Text(
                text = text,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 65.sp,
                    letterSpacing = 4.sp,
                    shadow = Shadow(color = Color.White, blurRadius = 5f, offset = Offset(5f, 5f))
                )
            )
        }
    }
}