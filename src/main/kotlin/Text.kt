import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun longText() {

    Text(
        text = "A B C D E F G H",
        modifier = Modifier.offset(75.dp, 916.dp),
        letterSpacing = 49.sp,
        overflow = TextOverflow.Clip,
        maxLines = 1,
        softWrap = false,
    )
    Text(
        text = "A B C D E F G H",
        modifier = Modifier.offset(22.dp, 6.dp).rotate(-180f),
        letterSpacing = 49.sp,
        overflow = TextOverflow.Clip,
        maxLines = 1,
        softWrap = false,
    )

    Column{
        Text(
            text = "8",
            modifier = Modifier.offset(9.dp, 75.dp)
        )
        Text(
            text = "7",
            modifier = Modifier.offset(9.dp, 170.dp)
        )
        Text(
            text = "6",
            modifier = Modifier.offset(9.dp, 265.dp)
        )
        Text(
            text = "5",
            modifier = Modifier.offset(9.dp, 360.dp)
        )
        Text(
            text = "4",
            modifier = Modifier.offset(9.dp, 455.dp)
        )
        Text(
            text = "3",
            modifier = Modifier.offset(9.dp, 550.dp)
        )
        Text(
            text = "2",
            modifier = Modifier.offset(9.dp, 645.dp)
        )
        Text(
            text = "1",
            modifier = Modifier.offset(9.dp, 740.dp)
        )

    }
    Column{
        Text(
            text = "1",
            modifier = Modifier.offset(920.dp, 75.dp).rotate(-180f)
        )
        Text(
            text = "2",
            modifier = Modifier.offset(920.dp, 170.dp).rotate(-180f)
        )
        Text(
            text = "3",
            modifier = Modifier.offset(920.dp, 265.dp).rotate(-180f)
        )
        Text(
            text = "4",
            modifier = Modifier.offset(920.dp, 360.dp).rotate(-180f)
        )
        Text(
            text = "5",
            modifier = Modifier.offset(920.dp, 455.dp).rotate(-180f)
        )
        Text(
            text = "6",
            modifier = Modifier.offset(920.dp, 550.dp).rotate(-180f)
        )
        Text(
            text = "7",
            modifier = Modifier.offset(920.dp, 645.dp).rotate(-180f)
        )
        Text(
            text = "8",
            modifier = Modifier.offset(920.dp, 740.dp).rotate(-180f)
        )
    }
}