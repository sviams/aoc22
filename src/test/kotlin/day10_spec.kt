import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2210: Spek({

    val testInput = readLines("10_test.txt")
    val realInput  = readLines("10_real.txt")

    group("pt1") {

        test("with test input") {
            day10.pt1(testInput) `should be equal to` 13140
        }

        test("with real input") {
            day10.pt1(realInput) `should be equal to` 12640
        }

    }

    group("pt2") {

        test("with test input") {
            day10.pt2(testInput) `should be equal to` -1
        }
        test("with real input") {
            day10.pt2(realInput) `should be equal to` -1
        }

    }

})