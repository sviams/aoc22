import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2201: Spek({

    val testInput = readLines("1_test.txt")
    val realInput  = readLines("1_real.txt")

    group("pt1") {

        test("with test input") {
            day1.pt1(testInput) `should be equal to` 24000
        }

        test("with real input") {
            day1.pt1(realInput) `should be equal to` 74198
        }

    }

    group("pt2") {

        test("with test input") {
            day1.pt2(testInput) `should be equal to` 45000
        }

        test("with real input") {
            day1.pt2(realInput) `should be equal to` 209914
        }

    }

})