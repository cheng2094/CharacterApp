package com.example.characterapp.view

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.characterapp.model.character.Character
import com.example.characterapp.model.character.Transformation
import com.example.characterapp.viewmodel.CharacterViewModel
import com.example.characterapp.utils.Result

@Composable
fun CharacterScreen(
    viewModel: CharacterViewModel = hiltViewModel()
){
    val uiState = viewModel.state.collectAsState()

    when (val result = uiState.value) {
        is Result.Loading -> Text("Loading characters...")
        is Result.Success -> {
            LazyColumn {
                items(result.data.characters) { char ->
                    CharacterCard(char, viewModel)
                }
            }
        }
        is Result.Error -> Text("Error loading characters")
    }
}

@Composable
fun CharacterCard(
    character: Character,
    viewModel: CharacterViewModel,
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
                      Text(
                          text = character.race,
                          style = MaterialTheme.typography.titleSmall
                      )
                  }
                  Row{
                      Text(
                          text = "Ki: ${character.ki}",
                          style = MaterialTheme.typography.titleSmall
                      )
                  }
                  Row{
                      Text(
                          text = "Affiliation: ${character.affiliation}",
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
              //Get transformations
              val result by viewModel
                  .transformations(character.id)
                  .collectAsState()

              when {
                  result.isLoading -> Text("Loading...")
                  result.errorMessage != null -> Text("Error: ${result.errorMessage}")
                  result.data.isEmpty() -> Text(
                      "This character has no transformations",
                      Modifier.padding(16.dp)
                  )
                  else -> {
                      val transformations = (result.data)
                      transformations.forEach { t ->
                          TransformationCard(transformation = t)
                      }
                  }
              }
          }
      }
  }
}

@Composable
fun TransformationCard(
    transformation: Transformation,
    modifier: Modifier = Modifier
){
    Row {
        Surface(
            modifier.size(120.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        ) {
            AsyncImage(
                model = transformation.image,
                contentDescription = transformation.name,
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = transformation.name,
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = "Ki: ${transformation.ki}",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

