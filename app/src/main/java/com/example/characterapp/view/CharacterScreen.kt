package com.example.characterapp.view

import android.util.Log
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
import com.example.characterapp.model.Character
import com.example.characterapp.ui.theme.DarkGreen
import com.example.characterapp.ui.theme.Purple
import com.example.characterapp.viewmodel.CharacterViewModel

@Composable
fun CharacterScreen(
    modifier: Modifier = Modifier,
    viewModel: CharacterViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()
    //Log.d("Cant Items", "${state.characters.size}")
    LazyColumn(modifier = modifier) {
        items(state.characters){ character ->
            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
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
                      model = character.image,
                      contentDescription = character.name,
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
                      text = character.name,
                      style = MaterialTheme.typography.titleLarge
                  )
                  Row(
                      verticalAlignment = Alignment.CenterVertically
                  )
                  {
                    val color = when (character.race){
                        "Saiyan" -> Color.Yellow
                        "Human" -> Color.Cyan
                        "Namekian" -> DarkGreen
                        "Android" -> Color.Green
                        "Frieza Race" -> Purple//Purple
                        else -> Color.Gray
                    }
                      Box(
                          modifier
                              .padding(2.dp)
                              .clip(CircleShape)
                              .background(color)
                              .size(12.dp)
                      )
                      Text(
                          text = character.race,
                          style = MaterialTheme.typography.titleSmall
                      )
                  }
                  Row(

                  ) {
                      Text(
                          text = "Max Ki: ${character.maxKi}",
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
                    Text(text = character.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
          }
      }
  }
}

