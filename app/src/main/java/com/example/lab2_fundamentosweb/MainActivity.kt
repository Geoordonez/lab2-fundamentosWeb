package com.example.lab2_fundamentosweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CharacterScreen()
            }
        }
    }
}

@Composable
fun CharacterScreen() {


    var str by remember { mutableIntStateOf(0) }
    var dex by remember { mutableIntStateOf(0) }
    var intStat by remember { mutableIntStateOf(0) }

    val total = str + dex + intStat
    val scope = rememberCoroutineScope()

    fun rollStat(onResult: (Int) -> Unit) {
        scope.launch {
            repeat(10) {
                onResult((1..20).random())
                delay(60)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Character Creation",
            color = Color.Red,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold


        )

        Spacer(modifier = Modifier.height(24.dp))

        // 🔽 Step 1: Reusable Rows
        StatRow("STR", str) { rollStat { str = it } }
        StatRow("DEX", dex) { rollStat { dex = it } }
        StatRow("INT", intStat) { rollStat { intStat = it } }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔢 Total
        Text(
            text = "Total: $total",
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ⚠️ Validation Rule
        if (total < 30) {
            Text(
                text = "Re-roll recommended!",
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        } else if (total >= 50) {
            Text(
                text = "Godlike!",
                color = Color(0xFFDAA520), // dorado
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun StatRow(
    name: String,
    value: Int,
    onRoll: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = value.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )

            Button(onClick = onRoll) {
                Text("Roll")
            }
        }
    }
}
