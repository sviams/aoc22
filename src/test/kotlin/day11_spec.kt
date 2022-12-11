import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2211: Spek({

    val testInput = readLines("11_test.txt")
    val realInput  = readLines("11_real.txt")

    group("pt1") {

        test("with test input") {
            day11.pt1(testInput) `should be equal to` 10605
        }

        test("with real input") {
            day11.pt1(realInput) `should be equal to` 64032
        }

    }

    group("pt2") {

        test("with test input") {
            day11.pt2(testInput) `should be equal to` 2713310158
        }
        test("with real input") {
            day11.pt2(realInput) `should be equal to` 12729522272
        }

    }

})