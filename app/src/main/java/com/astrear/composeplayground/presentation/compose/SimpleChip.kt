package com.astrear.composeplayground.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.astrear.composeplayground.ui.theme.ComposePlaygroundTheme
import com.astrear.composeplayground.ui.theme.CustomTypography

@Composable
fun SimpleChip(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(16.dp)
            ),
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
            text = text,
            style = CustomTypography.Micro,
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xff000000)
@Composable
fun SimpleChipPreview() {
    ComposePlaygroundTheme(dynamicColor = false) {
        SimpleChip(text = "Distributed microprocessor")
    }
}