package com.raf.topanime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raf.topanime.model.AnimeDataSource
import com.raf.topanime.ui.theme.TopAnimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopAnimeTheme {
                TopAnimeApp()
            }
        }
    }
}

@Composable
fun TopAnimeApp() {
    Scaffold(
        topBar = { TopAppBar() }
    ) {
        val animes = AnimeDataSource.animes
        AnimeList(animes = animes)
    }
}

@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.best_anime),
            style = MaterialTheme.typography.h1
        )
    }
}

@Preview
@Composable
fun LightThemePreview() {
    TopAnimeTheme(darkTheme = false) {
        TopAnimeApp()
    }
}

@Preview
@Composable
fun DarkThemePreview() {
    TopAnimeTheme(darkTheme = true) {
        TopAnimeApp()
    }
}