package android.bignerdranch.com

import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.launch
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope


private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

class MainActivity : AppCompatActivity() {
    private var characterData = CharacterGenerator.generate()
    private var Bundle.characterData
        get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
        set(value) = putSerializable(CHARACTER_DATA_KEY, value)


    // Serialization is the process in which objects are stored. When we serialize objects, we break it into basic data types
    // And only serializable objects can be stored in a bundle
    // So this function helps passes on the activity so that when the android lifeCycle destroys and recreates the activity, it is passed on
    fun onSavedInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)     // so the activity will be passed on because we set characterData from the savedInstanceState
//        outState.putSerializable(CHARACTER_DATA_KEY, characterData) // (key, serializableData) that's how it works
        outState.characterData = characterData
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This is reading the characterData from the saved instance state. If non-null, we just cast it back as CharacterData
        /**characterData = savedInstanceState?.let {
            it.getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
        } ?: CharacterGenerator.generate()   // But if null we just generate a new character **/
        characterData = savedInstanceState?.characterData ?:
                CharacterGenerator.generate()


        val generateButton = findViewById<Button>(R.id.generateButton)

        // setOnClickListener method is called to work on Button Interfaces
        // this code specifies what we want the button to do when clicked which is to execute the generate Character behaviour
        generateButton.setOnClickListener {
            characterData = CharacterGenerator.generate()
            displayCharacterData()
        }

        displayCharacterData()
    }
    private fun displayCharacterData() {

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val raceTextView = findViewById<TextView>(R.id.raceTextView)
        val dexTextView = findViewById<TextView>(R.id.dexterityTextView)
        val wisdomTextView = findViewById<TextView>(R.id.wisdomTextView)
        val strengthTextView = findViewById<TextView>(R.id.strengthTextView)

        // We use standard run function to implicitly pass all the textView variables to all properties of CharacterData
        characterData.run {
            nameTextView.text = name
            raceTextView.text = race
            dexTextView.text = dex
            wisdomTextView.text = wis
            strengthTextView.text = str
        }
    }
}

/** GlobalScope.launch(Dispatchers.Main) {
    characterData = fetchCharacterDataAsync().await()
    displayCharacterData()
} **/

