package pl.maniak.catfacts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import io.reactivex.disposables.Disposable
import pl.maniak.catfacts.databinding.ActivityCatFactBinding

class CatFactActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable
    private lateinit var binding: ActivityCatFactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatFactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val catFactViewModel = di.catFactViewModel

        catFactViewModel.observableState.observe(this) { state ->
            renderState(binding, state)
        }

        binding.getFactButton.setOnClickListener {
            catFactViewModel.dispatch(CatFactAction.GetFactButtonClicked)
        }
    }

    private fun renderState(binding: ActivityCatFactBinding, state: CatFactState) {
        with(state) {
            if (catFact.isNotEmpty()) {
                binding.catFactView.text = catFact
            }
            binding.progressBar.isVisible = loading
            binding.errorView.isVisible = displayError
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
