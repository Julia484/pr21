package com.example.pr21

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.concurrent.thread

class ViewModel : ViewModel() {

    private val _elapsedTime = MutableLiveData<Long>() //private val _elapsedTime = MutableLiveData<Long>(): Объявляет приватный изменяемый объект LiveData с именем _elapsedTime. Хранит значение Long, представляющее прошедшее время в миллисекундах
    val elapsedTime: LiveData<Long> //Объявляет публичный неизменяемый объект LiveData с именем elapsedTime. Это свойство только для чтения, возвращающее значение _elapsedTime
        get() = _elapsedTime

    private var isRunning = false // Булева переменная, указывающая, запущен ли таймер.
    private var startTime: Long = 0 //Хранит время запуска таймера.
    private var pausedTime: Long = 0 //: Хранит накопленное время паузы.

    init {
        _elapsedTime.value = 0 //Это блок инициализации. Устанавливает начальное значение _elapsedTime равным 0.
    }

    fun start() { //Эта функция запускает таймер. Проверяет, не запущен ли таймер уже. Обновляет startTime, учитывая время паузы.
        //Использует kotlin.concurrent.thread для создания фонового потока, который обновляет _elapsedTime каждую секунду (Thread.sleep(1000)). postValue() используется, потому что это обновление происходит во фоновом потоке.
        if (!isRunning) {
            isRunning = true
            startTime = System.currentTimeMillis() - pausedTime

            thread {
                while (isRunning) {
                    val currentTime = System.currentTimeMillis()
                    val elapsedTime = currentTime - startTime

                    _elapsedTime.postValue(elapsedTime)

                    Thread.sleep(1000)
                }
            }
        }
    }
    fun pause() { //Проверяет, запущен ли таймер
        if (isRunning) {
            isRunning = false
            pausedTime = System.currentTimeMillis() - startTime
        }
    }

    fun stop() {
        isRunning = false
        pausedTime = 0
        _elapsedTime.value = 0
    }
}