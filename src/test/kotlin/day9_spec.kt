import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2209: Spek({

    val testInput = readLines("9_test.txt")
    val testInput2 = readLines("9_test2.txt")
    val realInput  = readLines("9_real.txt")

    group("pt1") {

        test("with test input") {
            day9.pt1(testInput) `should be equal to` 13
        }

        test("with real input") {
            day9.pt1(realInput) `should be equal to` 6357
        }

    }

    group("pt2") {

        test("with test input") {
            day9.pt2(testInput) `should be equal to` 1
        }

        test("with test input 2") {
            day9.pt2(testInput2) `should be equal to` 36
        }

        test("with real input") {
            day9.pt2(realInput) `should be equal to` 2627
        }

    }

})