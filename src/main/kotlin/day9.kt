object day9 {

    data class Rope(val knots: List<Pos>, val tailHistory: List<Pos> = listOf(Pos.ORIGIN))

    tailrec fun stepRope(head: Pos, rope: Rope): Rope {
        val newKnots = rope.knots.fold(emptyList<Pos>()) { acc, start ->
            val end = if (acc.isEmpty()) head else acc.last()
            val xDiff = end.x - start.x
            val yDiff = end.y - start.y
            val newX = if (xDiff > 0) start.x + 1 else if (xDiff < 0) start.x - 1 else start.x
            val newY = if (yDiff > 0) start.y + 1 else if (yDiff < 0) start.y - 1 else start.y
            val newPos = Pos(newX, newY)
            acc + if (newPos == end) start else newPos
        }
        return if (newKnots == rope.knots) rope
        else stepRope(head, Rope(newKnots, (rope.tailHistory + newKnots.last())))
    }

    tailrec fun doMoves(moves: List<String>, rope: Rope, head: Pos = Pos.ORIGIN): List<Pos> {
        if (moves.isEmpty()) return rope.tailHistory.distinct()
        val (d, c) = """(\S+) (\d+)""".toRegex().find(moves.first())!!.destructured
        val newHead = when (d) {
            "U" -> head.copy(y = head.y + c.toInt())
            "D" -> head.copy(y = head.y - c.toInt())
            "R" -> head.copy(x = head.x + c.toInt())
            else -> head.copy(x = head.x - c.toInt())
        }
        val newRope = stepRope(newHead, rope)
        return doMoves(moves.drop(1), newRope, newHead)
    }

    fun pt1(input: List<String>): Int = doMoves(input, Rope(listOf(Pos.ORIGIN))).size

    fun pt2(input: List<String>): Int = doMoves(input, Rope((0 .. 8).map { Pos.ORIGIN })).size
}