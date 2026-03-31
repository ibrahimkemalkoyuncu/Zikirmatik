package com.example.afneyzikirmatik.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.afneyzikirmatik.domain.model.Zikr

@Composable
fun ZikrSelector(
    zikrs: List<Zikr>,
    currentZikrId: String,
    onZikrSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        zikrs.forEach { zikr ->
            ZikrItem(
                zikr = zikr,
                isSelected = zikr.id == currentZikrId,
                onClick = { onZikrSelected(zikr.id) }
            )
        }
    }
}

@Composable
private fun ZikrItem(
    zikr: Zikr,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                             else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = zikr.arabic,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = zikr.translation,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
