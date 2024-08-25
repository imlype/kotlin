import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: ForcaViewModel by viewModels()

    private lateinit var tvDisplayedWord: TextView
    private lateinit var tvRemainingAttempts: TextView
    private lateinit var glLetters: GridLayout
    private lateinit var btnRestart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplayedWord = findViewById(R.id.tvDisplayedWord)
        tvRemainingAttempts = findViewById(R.id.tvRemainingAttempts)
        glLetters = findViewById(R.id.glLetters)
        btnRestart = findViewById(R.id.btnRestart)

        setupLetterButtons()
        observeViewModel()

        btnRestart.setOnClickListener {
            viewModel.resetGame()
        }
    }

    private fun setupLetterButtons() {
        val alphabet = ('A'..'Z').toList()
        alphabet.forEach { letter ->
            val button = Button(this).apply {
                text = letter.toString()
                setOnClickListener {
                    viewModel.onLetterGuessed(letter)
                }
            }
            glLetters.addView(button)
        }
    }

    private fun observeViewModel() {
        viewModel.uiState.observe(this) { state ->
            tvDisplayedWord.text = state.displayedWord
            tvRemainingAttempts.text = "Tentativas restantes: ${state.remainingAttempts}"
            updateLetterButtons(state.guessedLetters)

            if (state.isGameOver) {
                btnRestart.visibility = View.VISIBLE
                glLetters.visibility = View.GONE
            } else {
                btnRestart.visibility = View.GONE
                glLetters.visibility = View.VISIBLE
            }
        }
    }

    private fun updateLetterButtons(guessedLetters: List<Char>) {
        for (i in 0 until glLetters.childCount) {
            val button = glLetters.getChildAt(i) as Button
            button.isEnabled = !guessedLetters.contains(button.text[0])
        }
    }
}

class ForcaViewModel : ViewModel() {
    private val _uiState = MutableLiveData(ForcaUiState())
    val uiState: LiveData<ForcaUiState> = _uiState

    private val wordToGuess = "KOTLIN"
    private val maxAttempts = 6

    init {
        resetGame()
    }

    fun onLetterGuessed(letter: Char) {
        val guessedLetters = _uiState.value?.guessedLetters?.toMutableList() ?: mutableListOf()
        guessedLetters.add(letter)
        val displayedWord = wordToGuess.map { if (guessedLetters.contains(it)) it else '_' }.joinToString(" ")

        val remainingAttempts = if (wordToGuess.contains(letter)) {
            _uiState.value?.remainingAttempts ?: maxAttempts
        } else {
            (_uiState.value?.remainingAttempts ?: maxAttempts) - 1
        }

        val isWon = displayedWord.replace(" ", "") == wordToGuess
        val isGameOver = isWon || remainingAttempts <= 0

        _uiState.value = ForcaUiState(
            displayedWord = displayedWord,
            guessedLetters = guessedLetters,
            remainingAttempts = remainingAttempts,
            isGameOver = isGameOver,
            isWon = isWon
        )
    }

    fun resetGame() {
        _uiState.value = ForcaUiState(
            displayedWord = "_ ".repeat(wordToGuess.length).trim(),
            remainingAttempts = maxAttempts
        )
    }
}

data class ForcaUiState(
    val displayedWord: String = "",
    val guessedLetters: List<Char> = emptyList(),
    val remainingAttempts: Int = 0,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false,
)
