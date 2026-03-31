package com.example.afneyzikirmatik.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimary
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimaryVariant
import com.example.afneyzikirmatik.ui.theme.GoldAccent

@Composable
fun CounterButton(
    count: Int,
    onIncrement: () -> Unit,
    isMilestone: Boolean = false,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100)
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (count % 33 == 0 && count > 0) 0.3f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Glow effect for milestones
        if (glowAlpha > 0f) {
            Box(
                modifier = Modifier
                    .size(260.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                GoldAccent.copy(alpha = glowAlpha),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        Box(
            modifier = Modifier
                .size(220.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = CircleShape,
                    ambientColor = EmeraldPrimary.copy(alpha = 0.2f),
                    spotColor = EmeraldPrimary.copy(alpha = 0.2f)
                )
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            EmeraldPrimaryVariant,
                            EmeraldPrimary
                        )
                    )
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onIncrement
                )
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.displayLarge.copy(
                    color = Color.White
                )
            )
        }
    }
}
