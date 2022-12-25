import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2215: Spek({

    val testInput = readLines("15_test.txt")
    val realInput  = readLines("15_real.txt")

    group("pt1") {

        test("with test input") {
            day15.pt1(testInput, 10) `should be equal to` 26
        }

        test("with real input") {
            day15.pt1(realInput, 2000000) `should be equal to` 5403290
        }

    }

    group("pt2") {

        test("with test input") {
            day15.pt2(testInput) `should be equal to` 56000011
        }
        test("with real input") {
            day15.pt2(realInput) `should be equal to` 10291582906626
        }

    }

})