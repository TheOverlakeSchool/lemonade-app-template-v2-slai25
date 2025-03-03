package com.example.generictemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.generictemplate.ui.theme.GenericTemplateTheme
import com.example.generictemplate.ui.theme.GenericTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GenericTemplateTheme {
                MakeLemonade()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeLemonade() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }
    var isNextEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Lemonade Maker!",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentStep) {
                1 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.taptree,
                        drawableResourceId = R.drawable.lemon_tree,
                        contentDescriptionResourceId = R.string.lemon_tree_content_description,
                        onImageClick = {
                            squeezeCount = (2..4).random() // Random squeeze count
                            isNextEnabled = true // Enable Next button after the image is tapped
                        },
                        onNextClick = {
                            currentStep = 2
                            isNextEnabled = false // Disable Next button after proceeding to next step
                        },
                        isNextEnabled = isNextEnabled
                    )
                }
                2 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.squeezelemon,
                        drawableResourceId = R.drawable.lemon_squeeze,
                        contentDescriptionResourceId = R.string.lemon_content_description,
                        onImageClick = {
                            squeezeCount-- // Decrement squeeze count
                            if (squeezeCount == 0) {
                                isNextEnabled = true // Enable Next button when squeezing is complete
                            }
                        },
                        onNextClick = {
                            currentStep = 3
                            isNextEnabled = false // Disable Next button after proceeding to next step
                        },
                        isNextEnabled = isNextEnabled
                    )
                }
                3 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.tapdrink,
                        drawableResourceId = R.drawable.lemon_drink,
                        contentDescriptionResourceId = R.string.lemonade_content_description,
                        onImageClick = {
                            isNextEnabled = true // Enable Next button after tapping the drink
                        },
                        onNextClick = {
                            currentStep = 4
                            isNextEnabled = false // Disable Next button after proceeding to next step
                        },
                        isNextEnabled = isNextEnabled
                    )
                }
                4 -> {
                    LemonTextAndImage(
                        textLabelResourceId = R.string.taprestart,
                        drawableResourceId = R.drawable.lemon_restart,
                        contentDescriptionResourceId = R.string.empty_glass_content_description,
                        onImageClick = {
                            isNextEnabled = true // Enable Next button after tapping restart
                        },
                        onNextClick = {
                            currentStep = 1
                            isNextEnabled = false // Reset to first step and disable Next
                        },
                        isNextEnabled = isNextEnabled
                    )
                }
            }
        }
    }
}


@Composable
fun LemonTextAndImage(
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    onNextClick: () -> Unit, // Added onNextClick to handle "Next" button click
    isNextEnabled: Boolean, // Added to enable or disable "Next" button
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Image and Text Display
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = stringResource(contentDescriptionResourceId),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(dimensionResource(R.dimen.button_image_height))
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))

            Text(
                text = stringResource(textLabelResourceId),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space between text and "Next" button

            // "Next" button positioned below the description
            Button(
                onClick = onNextClick,
                enabled = isNextEnabled, // Enable or disable the button
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text(text = "Next", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GenericTemplateTheme {
        MakeLemonade()
    }
}
