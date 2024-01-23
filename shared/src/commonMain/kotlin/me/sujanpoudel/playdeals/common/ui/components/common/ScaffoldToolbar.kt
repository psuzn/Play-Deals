package me.sujanpoudel.playdeals.common.ui.components.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sujanpoudel.playdeals.common.navigation.Navigator
import me.sujanpoudel.playdeals.common.strings.Strings

@Composable
fun rememberTextTitle(title: String): ScaffoldToolbar.ScaffoldTitle {
  return remember(title) {
    ScaffoldToolbar.ScaffoldTitle.TextTitle(title)
  }
}

object ScaffoldToolbar {

  sealed class ScaffoldTitle {
    data object None : ScaffoldTitle()

    @Immutable
    data class TextTitle(val text: String) : ScaffoldTitle()

    @Immutable
    class SearchBarTitle(val text: State<String>, val onTextUpdated: (String) -> Unit) : ScaffoldTitle()
  }

  @Composable
  fun NavigationIcon(navigator: Navigator) {
    IconButton(onClick = navigator::pop) {
      Icon(Icons.Default.ArrowBack, contentDescription = "")
    }
  }

  @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
  @Composable
  operator fun invoke(
    modifier: Modifier = Modifier,
    title: ScaffoldTitle,
    showNavIcon: Boolean = true,
    alwaysShowNavIcon: Boolean = false,
    navigationIcon: @Composable (Navigator) -> Unit = { it -> NavigationIcon(it) },
    actions: (@Composable (Navigator) -> Unit)? = null,
    behaviour: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
  ) {
    val navigator = Navigator.current
    CenterAlignedTopAppBar(
      modifier = modifier.windowInsetsPadding(WindowInsets(top = 20.dp)),
      title = { ToolbarTitle(title) },
      navigationIcon =
        {
          if (alwaysShowNavIcon || (showNavIcon && navigator.backStackCount.value > 1)) {
            navigationIcon(navigator)
          }
        },
      actions =
        { actions?.invoke(navigator) },
      colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
      ),
      scrollBehavior = behaviour,
    )
  }

  @Composable
  private fun ToolbarTitle(title: ScaffoldTitle) {
    val contentTransform = remember {
      fadeIn(animationSpec = tween(220, delayMillis = 90))
        .togetherWith(fadeOut(animationSpec = tween(90)))
    }

    AnimatedContent(
      modifier = Modifier.fillMaxWidth().heightIn(min = 24.dp),
      targetState = title,
      transitionSpec = { contentTransform },
    ) { scaffoldTitle ->
      when (scaffoldTitle) {
        ScaffoldTitle.None -> {}
        is ScaffoldTitle.SearchBarTitle -> SearchBarTitle(scaffoldTitle)
        is ScaffoldTitle.TextTitle -> Text(
          text = scaffoldTitle.text,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
          textAlign = TextAlign.Center,
        )
      }
    }
  }

  @OptIn(ExperimentalMaterialApi::class)
  @Composable
  private fun SearchBarTitle(title: ScaffoldTitle.SearchBarTitle) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
      focusRequester.requestFocus()
    }

    BasicTextField(
      modifier = Modifier.fillMaxWidth().height(44.dp)
        .focusRequester(focusRequester),
      value = title.text.value,
      onValueChange = title.onTextUpdated,
      interactionSource = interactionSource,
      singleLine = true,
      textStyle = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = 16.sp,
      ),
      cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
      decorationBox = { innerTextField ->
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
          value = title.text.value,
          contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
            top = 8.dp,
            bottom = 8.dp,
          ),
          innerTextField = innerTextField,
          placeholder = {
            Text(
              Strings.search,
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
              ),
            )
          },
          enabled = true,
          singleLine = true,
          visualTransformation = VisualTransformation.None,
          interactionSource = interactionSource,
          colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
          ),
        )
      },
    )
  }
}
