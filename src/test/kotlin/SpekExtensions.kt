import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.dsl.TestBody

fun String.toLines(): List<String> = this.split("\n").map { it.trimEnd() }

fun TestBody.readLine(name: String) : String = this::class.java.classLoader.getResource(name).readText()

fun TestBody.readLines(name: String) : List<String> = this::class.java.classLoader.getResource(name).readText().toLines()

fun Root.readLines(name: String) : List<String> = this::class.java.classLoader.getResource(name).readText().toLines()