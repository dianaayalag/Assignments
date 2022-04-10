package com.emedinaa.kotlinapp.utils

import java.util.concurrent.Executor
import java.util.concurrent.Executors

val diskIOExecutor: Executor = Executors.newSingleThreadExecutor()
fun diskThread(block:() -> Unit) {
    diskIOExecutor.execute(block)
}