package pl.maniak.catfacts.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pl.maniak.catfacts.R

class MainActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getFactButton = findViewById<Button>(R.id.getFactButton)
        val loadingIndicator = findViewById<ProgressBar>(R.id.progressBar)
        val errorView = findViewById<TextView>(R.id.errorView)
        val catFactView = findViewById<TextView>(R.id.catFactView)

        val catFactRepository = di.catFactRepository
        getFactButton.setOnClickListener {
            disposable = catFactRepository.getFact()
                .toObservable()
                .onErrorResumeNext(Observable.empty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingIndicator.isVisible = true
                }
                .doOnError {
                    loadingIndicator.isVisible = false
                    errorView.isVisible = true
                }
                .subscribe { fact ->
                    loadingIndicator.isVisible = false
                    catFactView.text = fact
                    errorView.isVisible = false
                }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
