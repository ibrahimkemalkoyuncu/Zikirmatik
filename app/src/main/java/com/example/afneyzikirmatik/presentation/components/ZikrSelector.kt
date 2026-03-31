package com.example.afneyzikirmatik.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun ZikrSelectorModal(
    zikrs: List<Zikr>,
    currentZikrId: String,
    onZikrSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        scrimColor = Color.Black.copy(alpha = 0.32f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            // Header
            Text(
                text = "Zikri Seç",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Zikr list
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = zikrs,
                    key = { it.id }
                ) { zikr ->
                    ZikrSelectorCard(
                        zikr = zikr,
                        isSelected = zikr.id == currentZikrId,
                        onClick = {
                            onZikrSelected(zikr.id)
                            onDismiss()
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun ZikrSelectorCard(
    zikr: Zikr,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundBrush = if (isSelected) {
        Brush.horizontalGradient(
            colors = listOf(
                EmeraldPrimaryVariant.copy(alpha = 0.15f),
                EmeraldPrimary.copy(alpha = 0.05f)
            )
        )
    } else {
        Brush.horizontalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                Color.Transparent
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
        ),
        border = if (isSelected) {
            CardDefaults.outlinedCardBorder().copy(
                brush = Brush.horizontalGradient(
                    colors = listOf(EmeraldPrimary, EmeraldPrimaryVariant)
                )
            )
        } else null
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Arabic text
            Text(
                text = zikr.arabic,
                style = MaterialTheme.typography.headlineMedium,
                color = if (isSelected) EmeraldPrimary else MaterialTheme.colorScheme.onSurface
            )

            // Translation
            Text(
                text = zikr.translation,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // English
            Text(
                text = zikr.english,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Selection indicator
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "✓ Seçili",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldPrimary
                )
            }
        }
    }
}
