package com.example.jogodavelhamquinasimples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import com.example.jogodavelhamquinasimples.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val tabuleiro = arrayOf(
        arrayOf("A", "B", "C"),
        arrayOf("D", "E", "F"),
        arrayOf("G", "H", "I")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
    }

    fun buttonClick(view: View) {
        val buttonSelecionado = view as Button
        buttonSelecionado.text = "X"
        buttonSelecionado.isEnabled = false

        when (buttonSelecionado.id) {
            R.id.buttonZero -> tabuleiro[0][0] = "X"
            R.id.buttonUm -> tabuleiro[0][1] = "X"
            R.id.buttonDois -> tabuleiro[0][2] = "X"
            R.id.buttonTres -> tabuleiro[1][0] = "X"
            R.id.buttonQuatro -> tabuleiro[1][1] = "X"
            R.id.buttonCinco -> tabuleiro[1][2] = "X"
            R.id.buttonSeis -> tabuleiro[2][0] = "X"
            R.id.buttonSete -> tabuleiro[2][1] = "X"
            R.id.buttonOito -> tabuleiro[2][2] = "X"
        }

        var rX: Int
        var rY: Int
        var i = 0

        while (i < 9) {
            rX = Random.nextInt(0, 3)
            rY = Random.nextInt(0, 3)

            if (tabuleiro[rX][rY] != "X" && tabuleiro[rX][rY] != "O") {
                tabuleiro[rX][rY] = "O"
                val posicao = rX * 3 + rY

                when (posicao) {
                    0 -> {
                        binding.buttonZero.text = "O"
                        binding.buttonZero.isEnabled = false
                    }
                    1 -> {
                        binding.buttonUm.text = "O"
                        binding.buttonUm.isEnabled = false
                    }
                    2 -> {
                        binding.buttonDois.text = "O"
                        binding.buttonDois.isEnabled = false
                    }
                    3 -> {
                        binding.buttonTres.text = "O"
                        binding.buttonTres.isEnabled = false
                    }
                    4 -> {
                        binding.buttonQuatro.text = "O"
                        binding.buttonQuatro.isEnabled = false
                    }
                    5 -> {
                        binding.buttonCinco.text = "O"
                        binding.buttonCinco.isEnabled = false
                    }
                    6 -> {
                        binding.buttonSeis.text = "O"
                        binding.buttonSeis.isEnabled = false
                    }
                    7 -> {
                        binding.buttonSete.text = "O"
                        binding.buttonSete.isEnabled = false
                    }
                    8 -> {
                        binding.buttonOito.text = "O"
                        binding.buttonOito.isEnabled = false
                    }
                }
                break
            }
            i++
        }
    }
}




