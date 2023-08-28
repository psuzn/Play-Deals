package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.strings.Strings
import me.sujanpoudel.playdeals.common.ui.components.common.LazyImage
import me.sujanpoudel.playdeals.common.ui.screens.home.LocalLinkOpener
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object AppDealItem {
  @Composable
  operator fun invoke(
    appDeal: AppDeal,
    modifier: Modifier,
    isAppNewlyAdded: Boolean,
  ) {
    val appDealActionHandler = LocalLinkOpener.current

    Box(
      modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.background),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(),
      ) {
        Box {
          FeaturedImages(appDeal)

          if (isAppNewlyAdded) {
            FloatingNewChip(
              modifier = Modifier
                .padding(bottom = 6.dp, end = 6.dp)
                .align(Alignment.BottomEnd),
            )
          }
        }

        AppDetails(appDeal)
      }

      PriceButton(
        normalPrice = appDeal.formattedNormalPrice(),
        currentPrice = appDeal.formattedCurrentPrice(),
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(end = 16.dp),
      ) {
        appDealActionHandler.openLink(appDeal.storeUrl)
      }
    }
  }

  @Composable
  private fun AppDetails(appDeal: AppDeal) {
    Row(
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 10.dp)
        .height(80.dp)
        .fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      AppIcon(
        appName = appDeal.name,
        url = appDeal.icon,
      )

      Column(
        verticalArrangement = Arrangement.spacedBy(1.dp),
        modifier = Modifier.align(Alignment.CenterVertically),
      ) {
        Text(
          appDeal.name,
          style = MaterialTheme.typography.titleSmall,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.fillMaxWidth(),
        )

        AppDetail2ndRow(appDeal)

        AppDetail3rdRow(appDeal)

        Row(
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            "r/googlePlayDeals",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
          )
        }
      }
    }
  }

  @OptIn(ExperimentalFoundationApi::class)
  @Composable
  private fun FeaturedImages(appDeal: AppDeal) {
    val listState = rememberLazyListState()

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      state = listState,
      flingBehavior = rememberSnapFlingBehavior(listState),
      contentPadding = PaddingValues(horizontal = 16.dp),
      modifier = Modifier.aspectRatio(2.22f, false),
    ) {
      items(appDeal.images) {
        LazyImage(
          model = it,
          contentDescription = appDeal.name,
          modifier =
            Modifier.fillMaxHeight()
              .clip(RoundedCornerShape(percent = 5))
              .defaultMinSize(minWidth = 100.dp)
              .animateContentSize(animationSpec = tween(500)),
          contentScale = ContentScale.FillHeight,
        )
      }
    }
  }

  @Composable
  private fun AppIcon(
    appName: String,
    url: String,
  ) {
    LazyImage(
      model = url,
      contentDescription = appName,
      modifier = Modifier.fillMaxHeight()
        .aspectRatio(1f, true)
        .fillMaxSize()
        .clip(RoundedCornerShape(percent = 10)),
      contentScale = ContentScale.Inside,
    )
  }

  @Composable
  fun FloatingNewChip(
    modifier: Modifier = Modifier,
  ) {
    Row(
      modifier = modifier
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.primary)
        .padding(vertical = 4.dp, horizontal = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        Icons.Outlined.Info,
        "New item",
        tint = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier.size(16.dp),
      )
      Text(
        text = "new",
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.labelSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 1.dp),
      )
    }
  }

  @Composable
  private fun AppDetail2ndRow(appDeal: AppDeal) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        appDeal.category,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Box(
        Modifier
          .padding(horizontal = 2.dp)
          .size(4.dp)
          .align(Alignment.CenterVertically)
          .clip(RoundedCornerShape(percent = 100))
          .background(MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA)),
      )

      Icon(
        Icons.Default.SaveAlt,
        contentDescription = Strings.downloads,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Text(
        appDeal.downloads,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )
    }
  }

  @Composable
  private fun AppDetail3rdRow(appDeal: AppDeal) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        appDeal.ratingFormatted,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Icon(
        Icons.Outlined.Star,
        Strings.rating,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Text(
        text = appDeal.formattedExpiryInfo(),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )
    }
  }

  @Composable
  fun PriceButton(
    modifier: Modifier = Modifier,
    normalPrice: String,
    currentPrice: String,
    onClick: () -> Unit,
  ) {
    Row(
      modifier = modifier
        .height(36.dp)
        .clip(RoundedCornerShape(corner = CornerSize(percent = 100)))
        .background(MaterialTheme.colorScheme.primary)
        .clickable(onClick = onClick)
        .padding(horizontal = 12.dp, vertical = 2.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Text(
        text = normalPrice,
        style = MaterialTheme.typography.labelMedium,
        textDecoration = TextDecoration.LineThrough,
        color = MaterialTheme.colorScheme.onPrimary,
      )

      Text(
        text = currentPrice,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary,
      )
    }
  }
}
