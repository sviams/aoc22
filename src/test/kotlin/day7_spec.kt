import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2207: Spek({

    val testInput = readLines("7_test.txt")
    val realInput  = readLines("7_real.txt")

    group("pt1") {

        test("with test input") {
            day7.pt1(testInput) `should be equal to` 95437
        }

        test("with real input") {
            day7.pt1(realInput) `should be equal to` 1449447
        }

    }

    group("pt2") {

        test("with test input") {
            day7.pt2(testInput) `should be equal to` 24933642
        }
        test("with real input") {
            day7.pt2(realInput) `should be equal to` 8679207
        }

    }

})