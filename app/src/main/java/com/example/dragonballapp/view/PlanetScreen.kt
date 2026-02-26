package com.example.dragonballapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.dragonballapp.model.planet.Planet
import com.example.dragonballapp.utils.Result
import com.example.dragonballapp.viewmodel.PlanetViewModel

@Composable
fun PlanetScreen(
    modifier: Modifier = Modifier,
    viewModel: PlanetViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    when(state) {
        is Result.Loading -> {
            Text("Loading planets...")
        }

        is Result.Error -> {
            Text("Error loading planets")
        }

        is Result.Success -> {
            val planets = (state as Result.Success).data.planets

            LazyColumn(modifier = modifier) {
                items(planets){ planet ->
                    PlanetCard(planet)
                }
            }
        }
    }



}

@Composable
fun PlanetCard(
    planet: Planet,
    modifier: Modifier = Modifier
){
    var expanded by remember {
        mutableStateOf(false)
    }

  Card(
      shape = MaterialTheme.shapes.medium,
      modifier = modifier
          .fillMaxWidth()
          .padding(horizontal = 8.dp, vertical = 4.dp)
          .animateContentSize(
              animationSpec = spring(
                  dampingRatio = Spring.DampingRatioMediumBouncy,
                  stiffness = Spring.StiffnessLow //speed of animation
              )
          )
  ){
      Column {
          Row{
              Surface(
                  modifier.size(120.dp),
                  color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
              ) {
                  AsyncImage(
                      model = planet.image,
                      contentDescription = planet.name,
                      contentScale = ContentScale.Fit
                  )
              }

              Column(
                  modifier
                      .padding(16.dp)
                      .align(Alignment.CenterVertically)
                      .weight(1f)
              ){
                  Text(
                      text = planet.name,
                      style = MaterialTheme.typography.titleLarge
                  )
                  Row(
                      verticalAlignment = Alignment.CenterVertically
                  )
                  {
                    val color = when (planet.isDestroyed){
                        true -> Color.Red
                        false -> Color.Green
                    }
                      Box(
                          modifier
                              .padding(2.dp)
                              .clip(CircleShape)
                              .background(color)
                              .size(12.dp)
                      )
                      Text(
                          text = if (planet.isDestroyed) "Destroyed" else "Active",
                          style = MaterialTheme.typography.titleSmall
                      )
                  }
              }
              IconButton(
                  onClick = {
                      expanded = !expanded
                  },
                  modifier.align(Alignment.CenterVertically)
              ) {
                  Icon(
                      imageVector = if(expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                      contentDescription = "More information"
                  )
              }
          }
          if(expanded){
            Row(
                modifier.padding(16.dp)
            ) {
                Column {
                    Text(text = planet.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
          }
      }
  }
}

