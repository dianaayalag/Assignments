package com.emedinaa.kotlinapp.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * https://github.com/android/architecture-components-samples/blob/main/BasicSample/app/src/main/java/com/example/android/persistence/AppExecutors.java
 */
class AppExecutors(
    val diskIO: Executor = Executors.newSingleThreadExecutor(),
    val networkIO: Executor = Executors.newFixedThreadPool(THREAD_COUNT), val mainThread: Executor =
        MainThreadExecutor()
) {
    companion object {
        const val THREAD_COUNT: Int = 3
    }

    //constructor():this(Executors.newSingleThreadExecutor(),Executors.newFixedThreadPool(THREAD_COUNT),MainThreadExecutor())

    internal class MainThreadExecutor : Executor {

        private val mainThreadHandler: Handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}