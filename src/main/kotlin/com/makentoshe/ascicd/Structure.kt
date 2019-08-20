package com.makentoshe.ascicd

import java.io.File

data class Structure(
    val project: File,
    val lib: File,
    val solution: File,
    val template: File,
    val answer: File,
    val description: File
)
