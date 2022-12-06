import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2206: Spek({

    val testInput = readLines("6_test.txt").first()
    val realInput  = readLines("6_real.txt").first()

    group("pt1") {

        test("with test input") {
            day6.pt1(testInput) `should be equal to` 7
        }

        test("with real input") {
            day6.pt1(realInput) `should be equal to` 1287
        }

    }

    group("pt2") {

        test("with test input") {
            day6.pt2(testInput) `should be equal to` 19
        }
        test("with real input") {
            day6.pt2(realInput) `should be equal to` 3716
        }

    }

})