package pl.maniak.catfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.maniak.catfacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }
}
