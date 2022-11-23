package com.raf.topanime

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raf.topanime.model.Anime
import com.raf.topanime.model.AnimeDataSource
import com.raf.topanime.ui.theme.Shapes
import com.raf.topanime.ui.theme.TopAnimeTheme
import com.raf.topanime.ui.theme.Typography

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimeList(animes: List<Anime>, modifier: Modifier = Modifier) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(animes) {
                AnimeCard(
                    anime = it,
                    modifier = modifier
                        .animateEnterExit(
                            enter = expandVertically(
                                animationSpec = spring(
                                    dampingRatio = DampingRatioMediumBouncy,
                                    stiffness = StiffnessLow
                                )
                            ),
                            exit = shrinkVertically()
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AnimeCard(anime: Anime, modifier: Modifier = Modifier) {

    var expanded by remember {
        mutableStateOf(false)
    }
    val onClick: () -> Unit = { expanded = !expanded }

    var imageSize = 150.dp
    var imageFullSize by remember {
        mutableStateOf(false)
    }
    val onImageClick: () -> Unit = { imageFullSize = !imageFullSize }
    if (imageFullSize) imageSize = Int.MAX_VALUE.dp

    //get screen size
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    Card(
        elevation = 2.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val indexOfAnime = AnimeDataSource.animes
            Text(
                text = anime.topText + "${indexOfAnime.indexOf(anime) + 1} to watch!",
                style = MaterialTheme.typography.h2,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                shape = Shapes.medium,
                onClick = onImageClick,
                modifier = modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = DampingRatioMediumBouncy,
                            stiffness = StiffnessLow
                        )
                    )
            ) {
                Image(
                    painter = painterResource(anime.imageRes),
                    contentDescription = stringResource(anime.titleRes),
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(imageSize),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                color = MaterialTheme.colors.background.copy(alpha = 0.3F),
                shape = Shapes.medium,
                onClick = onClick,
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = DampingRatioMediumBouncy,
                            stiffness = StiffnessMedium
                        )
                    )
            ) {
                Row(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(screenWidth - 100.dp)
                    ) {
                        Text(
                            text = stringResource(anime.titleRes),
                            style = Typography.h2,
                            maxLines = if (expanded) Int.MAX_VALUE else 1,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(
                            color = MaterialTheme.colors.onSurface
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(anime.descRes),
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Justify,
                            maxLines = if (expanded) Int.MAX_VALUE else 3,
                            overflow = if (expanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                        )
                    }
                    Spacer(modifier = modifier.weight(1f))
                    IconButton(
                        onClick = onClick,
                        modifier = modifier.padding(top = 16.dp, end = 16.dp)
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardPreviewDark() {
    TopAnimeTheme(darkTheme = true) {
        AnimeCard(anime = AnimeDataSource.animes[1])
    }
}

@Preview
@Composable
fun CardPreviewLight() {
    TopAnimeTheme(darkTheme = false) {
        AnimeCard(anime = AnimeDataSource.animes[2])
    }
}