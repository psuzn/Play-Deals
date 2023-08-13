package me.sujanpoudel.playdeals.common.ui.components.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.sujanpoudel.playdeals.common.Strings
import me.sujanpoudel.playdeals.common.domain.models.AppDeal
import me.sujanpoudel.playdeals.common.ui.components.common.LazyImage
import me.sujanpoudel.playdeals.common.ui.components.home.HomeScreen.PriceButton
import me.sujanpoudel.playdeals.common.ui.screens.home.LocalAppDealActionHandler
import me.sujanpoudel.playdeals.common.ui.theme.SOFT_COLOR_ALPHA

object AppDealItem {
  @Composable
  operator fun invoke(
    appDeal: AppDeal,
    modifier: Modifier,
  ) {


    val appDealActionHandler = LocalAppDealActionHandler.current

    Box(
      modifier
        .fillMaxWidth()
        .background(MaterialTheme.colors.background),
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(),
      ) {
        Box {
          FeaturedImages(appDeal)

          FloatingNewChip()
        }

        AppDetails(appDeal)
      }

      PriceButton(
        normalPrice = appDeal.formattedNormalPrice(),
        currentPrice = appDeal.formattedCurrentPrice(),
        onClick = {
          appDealActionHandler.handle(appDeal)
        },
      )
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
        verticalArrangement = Arrangement.spacedBy(2.2.dp),
        modifier = Modifier.align(Alignment.CenterVertically),
      ) {
        Text(
          appDeal.name,
          style = MaterialTheme.typography.body2.copy(fontSize = 16.sp),
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
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
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
  fun BoxScope.FloatingNewChip() {
    Row(
      modifier = Modifier
        .padding(bottom = 6.dp, end = 6.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colors.primary)
        .align(Alignment.BottomEnd)
        .padding(vertical = 4.dp, horizontal = 6.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        Icons.Outlined.Info,
        "New item",
        tint = MaterialTheme.colors.onPrimary,
        modifier = Modifier.size(16.dp),
      )
      Text(
        text = "new",
        color = MaterialTheme.colors.onPrimary,
        style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
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
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Box(
        Modifier
          .padding(horizontal = 2.dp)
          .size(4.dp)
          .align(Alignment.CenterVertically)
          .clip(RoundedCornerShape(percent = 100))
          .background(MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA)),
      )

      Icon(
        Icons.Default.SaveAlt,
        contentDescription = Strings.HomeScreen.DOWNLOADS,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Text(
        appDeal.downloads,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
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
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Icon(
        Icons.Outlined.Star,
        Strings.HomeScreen.RATINGS,
        modifier = Modifier.size(14.dp),
        tint = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )

      Text(
        text = appDeal.formattedExpiryInfo(),
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onBackground.copy(alpha = SOFT_COLOR_ALPHA),
      )
    }
  }
}
