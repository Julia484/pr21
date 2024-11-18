package com.example.pr21
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel //Объявляет переменную viewModel типа ViewModel. lateinit означает, что она будет инициализирована позже.

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState) //Вызов метода onCreate родительского класса.
        setContentView(R.layout.activity_main) //Загружает макет activity_main.xml


        val t: TextView = findViewById(R.id.timerTextView)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        viewModel.elapsedTime.observe(this, Observer { elapsedTime -> //Начинает наблюдение за изменениями в elapsedTime (объект LiveData в ViewModel).
            // Когда значение elapsedTime меняется, вызывается лямбда-выражение { elapsedTime -> t.text = formatTime(elapsedTime) },
            // которое форматирует время и устанавливает его в TextView.
            t.text = formatTime(elapsedTime)
        })

    }

    fun startButton (view : View){
        viewModel.start()
    }
    fun stopButton (view: View) {
        viewModel.stop()
    }
    fun pauseButton (view: View) {
        viewModel.pause()
    }


    private fun formatTime(milliseconds: Long): String { //Эта приватная функция форматирует время из миллисекунд в формат “мм:сс”.
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d", minutes, remainingSeconds)
    }
}