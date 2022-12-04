import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2204: Spek({

    val testInput = readLines("4_test.txt")
    val realInput  = readLines("4_real.txt")

    group("pt1") {

        test("with test input") {
            day4.pt1(testInput) `should be equal to` 2
        }

        test("with real input") {
            day4.pt1(realInput) `should be equal to` 644
        }

    }

    group("pt2") {

        test("with test input") {
            day4.pt2(testInput) `should be equal to` 4
        }

        test("with real input") {
            day4.pt2(realInput) `should be equal to` 926
        }

    }

})