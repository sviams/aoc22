import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek

object AoC2205: Spek({

    val testInput = readLines("5_test.txt")
    val realInput  = readLines("5_real.txt")

    group("pt1") {

        test("with test input") {
            day5.pt1(testInput) `should be equal to` "CMZ"
        }

        test("with real input") {
            day5.pt1(realInput) `should be equal to` "QNNTGTPFN"
        }

    }

    group("pt2") {

        test("with test input") {
            day5.pt2(testInput) `should be equal to` "MCD"
        }

        test("with real input") {
            day5.pt2(realInput) `should be equal to` "GGNPJBTTR"
        }

    }

})