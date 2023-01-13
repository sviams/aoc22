import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2216: Spek({

    val testInput = readLines("16_test.txt")
    val realInput  = readLines("16_real.txt")

    group("pt1") {

        test("with test input") {
            day16.pt1(testInput) `should be equal to` 1651
        }

        test("with real input") {
            day16.pt1(realInput) `should be equal to` 2080
        }

    }

    group("pt2") {

        test("with test input") {
            day16.pt2(testInput) `should be equal to` 1705
        }
        test("with real input") {
            day16.pt2(realInput) `should be equal to` 2752
        }

    }

})