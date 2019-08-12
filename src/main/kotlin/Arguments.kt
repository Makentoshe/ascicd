import java.io.File

data class Arguments(
    val project: File,
    val lib: File,
    val solution: File,
    val template: File,
    val answer: File,
    val description: File
)
