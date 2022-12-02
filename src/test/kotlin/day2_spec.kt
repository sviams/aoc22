import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2202: Spek({

    val testInput = readLines("2_test.txt")
    val realInput  = readLines("2_real.txt")

    group("pt1") {

        test("with test input") {
            day2.pt1(testInput) `should be equal to` 15
        }

        test("with real input") {
            day2.pt1(realInput) `should be equal to` 10718
        }

    }

    group("pt2") {

        test("with test input") {
            day2.pt2(testInput) `should be equal to` 12
        }

        test("with real input") {
            day2.pt2(realInput) `should be equal to` 14652
        }

    }

})