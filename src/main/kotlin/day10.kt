object day10 {

    tailrec fun execute(program: List<String>, cycles: Int = 1, x: Int = 1, result: List<Int> = emptyList()): List<Int> {
        if (program.isEmpty()) return result
        val line = program.first()
        val instr = line.toList().takeWhile { !it.isWhitespace() }.joinToString("")
        val newX = if (instr == "addxnow") x + line.split(" ").last().toInt() else x
        val newProgram = if (instr == "addx") listOf("addxnow ${line.split(" ").last().toInt()}").plus(program.drop(1)) else program.drop(1)
        return execute(newProgram, cycles + 1, newX, result.plus(x))
    }

    fun render(cycles: List<Int>) {
        (0 .. 6).forEach { row ->
            (0 .. 39).forEach { col ->
                val c = cycles[row * 40 + col]
                if (col in listOf(c-1, c, c+1)) print("#") else print(".")
            }
            println()
        }
    }

    fun pt1(input: List<String>): Int {
        val cycles = execute(input)
        return listOf(20, 60, 100, 140, 180, 220).sumOf { it * cycles[it-1] }
    }

    fun pt2(input: List<String>): Int {
        //render(execute(input)) - EHBZLRJR
        return -1
    }
}