import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2212: Spek({

    val testInput = readLines("12_test.txt")
    val realInput  = readLines("12_real.txt")

    group("pt1") {

        test("with test input") {
            day12.pt1(testInput) `should be equal to` 31
        }

        test("with real input") {
            day12.pt1(realInput) `should be equal to` 447
        }

    }

    group("pt2") {

        test("with test input") {
            day12.pt2(testInput) `should be equal to` 30
        }
        test("with real input") {
            day12.pt2(realInput) `should be equal to` 446
        }

    }

})