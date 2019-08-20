package com.makentoshe.ascicd

interface Repository<K, V> {
    fun get(key: K): V
}
