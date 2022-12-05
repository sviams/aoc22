object day5 {

    tailrec fun parseStackLine(input: String, result: List<Char> = emptyList(), index: Int = 1): List<Char> {
        val next = input.getOrNull(index) ?: return result
        return parseStackLine(input, result.plus(next), index+4)
    }

    fun parseStacks(input: List<String>): Map<Int, String> =
        input.reversed().fold(emptyMap<Int, String>()) { acc, line ->
            val asd = parseStackLine(line)
            asd.foldIndexed(acc) { index, res: Map<Int, String>, col: Char ->
                if (col.isLetter()) res.plus(index to res[index] + col) else res
            }
        }.map { it.key to it.value.drop(4) }.toMap()

    tailrec fun move(moves: List<String>, stacks: Map<Int, String>, reverse: Boolean): Map<Int, String> {
        if (moves.isEmpty()) return stacks
        val (amount,s,t) = """move (\d+) from (\d+) to (\d+)""".toRegex().find(moves.first())!!.destructured
        val source = s.toInt()-1
        val target = t.toInt()-1
        val toMove = stacks[source]!!.takeLast(amount.toInt())
        val afterAdd = stacks.plus(target to stacks[target] + if (reverse) toMove.reversed() else toMove)
        val afterRemove = afterAdd.plus(source to stacks[source]!!.dropLast(amount.toInt()))
        return move(moves.drop(1), afterRemove, reverse)
    }

    fun solve(input: List<String>, reverse: Boolean): String {
        val parts = input.groupBy { it.isNotBlank() }
        val afterMoves = move(parts.last(), parseStacks(parts.first().dropLast(1)), reverse)
        return afterMoves.values.fold("") { acc, c -> acc + c.last() }
    }

    fun pt1(input: List<String>): String = solve(input, true)

    fun pt2(input: List<String>): String = solve(input, false)

}