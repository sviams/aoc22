import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2203: Spek({

    val testInput = readLines("3_test.txt")
    val realInput  = readLines("3_real.txt")

    group("pt1") {

        test("with test input") {
            day3.pt1(testInput) `should be equal to` 157
        }

        test("with real input") {
            day3.pt1(realInput) `should be equal to` 7742
        }

    }

    group("pt2") {

        test("with test input") {
            day3.pt2(testInput) `should be equal to` 70
        }

        test("with real input") {
            day3.pt2(realInput) `should be equal to` 2276
        }

    }

})