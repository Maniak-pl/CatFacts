package pl.maniak.catfacts.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pl.maniak.catfacts.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getFactButton = findViewById<Button>(R.id.getFactButton)
        val loadingIndicator = findViewById<ProgressBar>(R.id.progressBar)
        val errorView = findViewById<TextView>(R.id.errorView)
        val catFactView = findViewById<TextView>(R.id.catFactView)

        getFactButton.setOnClickListener {

        }
    }
}
