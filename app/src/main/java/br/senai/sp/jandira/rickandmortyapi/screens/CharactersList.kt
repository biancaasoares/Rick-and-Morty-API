package br.senai.sp.jandira.rickandmortyapi.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.model.Results
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListAllCharacters(modifier: Modifier = Modifier) {

    var characterList by remember {
        mutableStateOf(listOf<Character>())
    }

    val charactersCall = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    charactersCall.enqueue(
        object : Callback<Results>{
            override fun onResponse(p0: Call<Results>, response: Response<Results>) {
                characterList = response.body()!!.results
            }

            override fun onFailure(p0: Call<Results>, response: Throwable) {

            }

        }
    )

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = Color.DarkGray
    ){
       Column (
           modifier = Modifier.padding(16.dp)
       ){
           Text(
               text = "Rick and Morty",
               fontSize = 24.sp,
               fontWeight = FontWeight.Bold,
               color = Color.White
               )
           Spacer(modifier = Modifier.height(16.dp))
           LazyColumn {
               items(characterList){
                   CharacterCard(it)
               }
           }
       }
    }

}

@Composable
fun CharacterCard(character: Character) {

     val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(bottom = 4.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable {
                Toast
                    .makeText(
                        context,
                        "Personagem: ${character.id}",
                        Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
        Row {
           Card(
               modifier = Modifier
                   .size(100.dp),
               colors = CardDefaults
                   .cardColors(
                       containerColor = Color.Magenta
                   )
           ) {
               AsyncImage(
                   model = character.image,
                   contentDescription = "",
               )
           }
            Column (
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
            ){
                Text(
                    text = character.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    )
                Text(text = character.species,
                    fontSize = 18.sp,
                    )
            }
        }
    }
    
}

@Preview
@Composable
private fun CharacterCardPreview() {
    CharacterCard(Character())
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListAllCharactersPreview() {
    ListAllCharacters()
}