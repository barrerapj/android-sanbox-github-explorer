package com.astrear.composeplayground.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.astrear.composeplayground.R
import com.astrear.composeplayground.domain.models.GithubRepository
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.CustomTypography
import com.astrear.composeplayground.ui.theme.Typography
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GithubRepositoryItem(data: GithubRepository) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(5.dp)),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.height(80.dp)) {
                Column(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(end = 4.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.ownerProfilePictureUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.image_placeholder_500px),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .fillMaxWidth(),
                        text = data.ownerName,
                        style = CustomTypography.MicroBold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
                Column(
                    modifier = Modifier.weight(0.85f)
                ) {
                    Text(
                        text = data.name,
                        style = Typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier,
                        text = data.description,
                        style = Typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 5.dp),
            ) {
                Row(modifier = Modifier.weight(0.8f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(id = R.drawable.ic_star_64px),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = data.stars.toString(),
                            style = CustomTypography.ExtraSmall
                        )
                    }
                    Row(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.size(15.dp),
                            painter = painterResource(id = R.drawable.ic_code_64_px),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp),
                            text = data.language,
                            style = CustomTypography.ExtraSmall
                        )
                    }
                }
                Text(
                    modifier = Modifier.weight(0.2f),
                    text = data.visibility.uppercase(),
                    style = CustomTypography.ExtraSmall,
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun RepositoryItemPreview() {
    ComposePlaygroundTheme(dynamicColor = false) {
        GithubRepositoryItem(
            data = GithubRepository(
                "Large user name",
                "https://i.pravatar.cc/300",
                "Too much large, really large, super super super large sample title",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur nec arcu auctor, egestas nisi non, congue urna. Nam eget lorem sed enim molestie ornare ac nec ante",
                "C",
                "public",
                100,
                Date(),
                Date()
            )
        )
    }
}
