package com.binod.quotessansar.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.binod.quotessansar.R
import com.binod.quotessansar.data.remote.models.QuotesDataItem
import com.binod.quotessansar.ui.theme.poppinsFontFamily
import com.binod.quotessansar.utils.defaultGradients
import com.binod.quotessansar.utils.feelingLowGradients
import com.binod.quotessansar.utils.happyGradients
import com.binod.quotessansar.utils.loveGradients
import com.binod.quotessansar.utils.sadGradients
import com.binod.quotessansar.utils.saveImageToGallery
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope", "CoroutineCreationDuringComposition")
@Composable
fun QuotesFullCardItem(
    modifier: Modifier = Modifier,
    quotesDataItem: QuotesDataItem,
    onImageDownloadClick: () -> Unit,
    onShuffleClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val cardHeight = remember { screenHeight * 0.7f }
    val captureController = rememberCaptureController()
    val scope = rememberCoroutineScope()
    var isDownloadBitmap by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val category = quotesDataItem.category?.lowercase(Locale.ROOT)
    val cardGradient: List<Brush> = when (category) {
        "love" -> loveGradients
        "sad" -> sadGradients
        "happy" -> happyGradients
        "feelinglow" -> feelingLowGradients
        else -> defaultGradients
    }
    var gradient by remember { mutableStateOf(cardGradient.random()) }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = modifier
                .capturable(captureController)
                .fillMaxWidth()
                .height(cardHeight),
            color = MaterialTheme.colorScheme.onSurface,
            shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(gradient),
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    Text(
                        text = quotesDataItem.quote ?: "No Quote", style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            color = Color.White,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = ("- " + quotesDataItem.author) ?: "Unknown Author",
                        style = TextStyle(
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }

                if (!isDownloadBitmap)
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {

                        IconButton(onClick =
                        {
                            onImageDownloadClick()
                            isDownloadBitmap = true
                        }) {

                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(8.dp, shape = CircleShape, clip = false)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_image_download),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }

                        }
                        IconButton(
                            onClick =
                            {
                                gradient = cardGradient.random()
                                onShuffleClick()
                            }

                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .shadow(8.dp, shape = CircleShape, clip = false)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_shuffle),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }

                        }
                        IconButton(onClick = onBookmarkClick) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(8.dp, shape = CircleShape, clip = false)
                            )
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_bookmark),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }

                        }
                        IconButton(onClick = onCopyClick) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(8.dp, shape = CircleShape, clip = false)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_copy),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                        IconButton(onClick = onShareClick) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .shadow(8.dp, shape = CircleShape, clip = false)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_share),
                                    contentDescription = null,
                                    modifier = Modifier.size(30.dp),
                                    tint = MaterialTheme.colorScheme.surface
                                )
                            }

                        }

                    }
                }
            }
        }
    }
    if (isDownloadBitmap) {
        scope.launch {
            val bitmapAsync = captureController.captureAsync()
            try {
                val bitmap = bitmapAsync.await()
                saveImageToGallery(context,bitmap.asAndroidBitmap())
            } catch (error: Throwable) {
                // Error occurred, do something.
            }
            isDownloadBitmap=false

        }

    }

}

