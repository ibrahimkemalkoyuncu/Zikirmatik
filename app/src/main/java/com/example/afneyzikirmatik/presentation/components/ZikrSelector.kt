package com.example.afneyzikirmatik.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.domain.model.Zikr
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimary
import com.example.afneyzikirmatik.ui.theme.EmeraldPrimaryVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZikrSelector(
    zikrs: List<Zikr>,
    currentZikrId: String,
    onZikrSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = { /* Handle dismiss if needed */ },
        modifier = modifier,
        sheetState = rememberModalBottomSheetState(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Select Dhikr",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            zikrs.forEach { zikr ->
                ZikrCard(
                    zikr = zikr,
                    isSelected = zikr.id == currentZikrId,
                    onClick = { onZikrSelected(zikr.id) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun ZikrCard(
    zikr: Zikr,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundBrush = if (isSelected) {
        Brush.horizontalGradient(
            colors = listOf(
                EmeraldPrimaryVariant.copy(alpha = 0.2f),
                EmeraldPrimary.copy(alpha = 0.1f)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant,
                MaterialTheme.colorScheme.surface
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(brush = backgroundBrush)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = zikr.arabic,
                style = MaterialTheme.typography.headlineLarge,
                color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = zikr.translation,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (isSelected) {
                Text(
                    text = "✓ Selected",
                    style = MaterialTheme.typography.labelMedium,
                    color = EmeraldPrimary
                )
            }
        }
    }
}
