package com.makentoshe.ascicd

interface Action<A> {
    fun execute(arg: A)
}

