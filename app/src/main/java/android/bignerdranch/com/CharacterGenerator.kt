package android.bignerdranch.com

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import java.io.Serializable
import java.net.URL

// const variable to hold the link for the web api
//private const val CHARACTER_DATA_API = "https://chargen-api.herokuapp.com/"

/**suspend fun main() {
    val CLIENT_REQUEST = HttpClient(CIO)
    val httpResponse : HttpResponse = CLIENT_REQUEST.get("https://chargen-api.herokuapp.com/")
    httpResponse.close()

} **/
//val stringReceive :String = httpResponse.receive()



// From my guess this is a generic EXTENSION function which accepts a list of any kind
private fun <T> List<T>.rand () = shuffled().first()
private fun Int.roll() = (0 until this)  // this is a dice logic - one die actually
    .map { (1..6).toList().rand() }
    .sum()
    .toString()



private val firstName = listOf("Eli","Alex","Sophie")
private val lastName = listOf("LightWeaver","GreatFoot","Oakenfeld")

// object OR singleton in which the instantiation is triggered when referenced with a function declared within it
object CharacterGenerator {
    data class CharacterData(var name: String, val race:String, val dex:String, val wis:String, val str:String) : Serializable // to make this class serializable


    // This function converts copied the data from the web api splitting it into a list by its the commas and returning the destructured
    // result to a CharacterData Instance
    fun fromApiData(apiData: String): CharacterData {
        val(race, name, dex, wis, str) = apiData.split(",")
        return CharacterData(name, race, dex, wis, str)
    }

    private fun name() = "${firstName.rand()} ${lastName.rand()}"

    private fun race() = listOf("dwarf","elf","human","halfling").rand()

    private fun dex() = 4.roll()

    private fun wis() = 3.roll()

    private fun str() = 5.roll()

    fun generate() = CharacterData(name = name(), race = race(), dex = dex(), wis = wis(), str = str())
}

// This function reads the live data from the web api
/**fun fetchCharacterDataAsync(): Deferred<CharacterGenerator.CharacterData>
{
    return GlobalScope.async {
        val apiData = URL(stringReceive).readText()
        return@async CharacterGenerator.fromApiData(apiData)
    }
} **/
