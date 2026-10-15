package com.example.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Tampilan awal (Splash/Welcome Screen) persis seperti Gambar 2:
 * - Latar belakang panorama persawahan & balai warga Desa Jambudipa
 * - Logo rumah putih
 * - Tulisan "si-RT/RW"
 * - Badge "SISTEM INFORMASI RT/RW"
 * - Subtitle "Desa Jambudipa Kec. Warungkondang"
 * - Transisi otomatis ke website setelah 2.5 detik
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit
) {
    val progress = remember { Animatable(0f) }
    val contentAlpha = remember { Animatable(0f) }
    val contentScale = remember { Animatable(0.85f) }

    LaunchedEffect(Unit) {
        // Animate content entrance
        launch {
            contentAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        }
        launch {
            contentScale.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
        }
        // Progress bar fills over 2.5 seconds
        launch {
            progress.animateTo(1f, animationSpec = tween(2500, easing = LinearEasing))
        }
        delay(2500)
        onNavigateToHome()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("splash_screen")
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                // Sentuh layar untuk langsung masuk tanpa menunggu sisa timer
                onNavigateToHome()
            }
    ) {
        // Background photo: Rural village scenery of Desa Jambudipa
        Image(
            painter = painterResource(id = R.drawable.img_splash_bg),
            contentDescription = "Pemandangan Desa Jambudipa",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient overlay matching Gambar 2 blueish atmospheric tint
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x33003B6D),
                            Color(0x22005A9C),
                            Color(0x55002C4C),
                            Color(0x99001D33)
                        )
                    )
                )
        )

        // Center Content Container matching Gambar 2 exactly
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .alpha(contentAlpha.value)
                .scale(contentScale.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // House with People & Document Logo Icon
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .shadow(elevation = 12.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF0077C8).copy(alpha = 0.90f))
                    .padding(8.dp)
                    .testTag("splash_logo"),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sitrw_house),
                    contentDescription = "Logo si-RT/RW",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // App Title: si-RT/RW in bold white
            Text(
                text = "si-RT/RW",
                style = TextStyle(
                    fontSize = 44.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.White,
                    letterSpacing = 1.sp,
                    shadow = Shadow(
                        color = Color(0xAA000000),
                        offset = Offset(2f, 4f),
                        blurRadius = 8f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("splash_title")
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Rounded pill badge: SISTEM INFORMASI RT/RW
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF64B5F6).copy(alpha = 0.88f))
                    .padding(horizontal = 20.dp, vertical = 7.dp)
                    .testTag("splash_badge"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SISTEM INFORMASI RT/RW",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.2.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Subtitle: Desa Jambudipa Kec. Warungkondang
            Text(
                text = "Desa Jambudipa Kec. Warungkondang",
                style = TextStyle(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    shadow = Shadow(
                        color = Color(0xBB000000),
                        offset = Offset(1f, 3f),
                        blurRadius = 6f
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.testTag("splash_subtitle")
            )
        }

        // Bottom progress indicator & hint
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 28.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { progress.value },
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = Color(0xFF64B5F6),
                trackColor = Color.White.copy(alpha = 0.35f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Ketuk layar untuk membuka langsung",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White.copy(alpha = 0.75f),
                    shadow = Shadow(color = Color.Black, blurRadius = 4f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
