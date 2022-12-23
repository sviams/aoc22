object day13 {

    enum class Result { CORRECT, INCORRECT, UNKNOWN }

    data class IntOrList(val value: Int?, val children: List<IntOrList>) : Comparable<IntOrList> {

        fun isEmpty(): Boolean = (value == null && children.isEmpty())
        fun isInt(): Boolean = value != null
        fun toList(): IntOrList = list(listOf(this))

        companion object {
            fun empty(): IntOrList = IntOrList(null, emptyList())
            fun int(v: Int): IntOrList = IntOrList(v, emptyList())
            fun list(l: List<IntOrList>): IntOrList = IntOrList(null, l)
        }

        override fun compareTo(other: IntOrList): Int {
            return if (compare(this, other) == Result.CORRECT) -1 else 1
        }
    }

    private tailrec fun parsePacket(line:String, result: IntOrList = IntOrList.empty()): Pair<String, IntOrList> {
        if (line.isEmpty()) return line to result.children.first()
        val next = line.first()

        return if (next.isDigit()) {
            val digits = line.takeWhile { it.isDigit() }
            parsePacket(line.drop(digits.length), result.copy(children = result.children + IntOrList.int(digits.toInt())))
        }
        else if (next == ',') parsePacket(line.drop(1), result)
        else if (next == '[') {
            val (remaining, child) = parsePacket(line.drop(1))
            parsePacket(remaining, result.copy(children = result.children + child))
        }
        else line.drop(1) to result
    }

    fun compare(left: IntOrList, right: IntOrList): Result {

        if (left.isEmpty() && !right.isEmpty()) return Result.CORRECT
        if (right.isEmpty() && !left.isEmpty()) return Result.INCORRECT
        if (left.isEmpty() && right.isEmpty()) return Result.UNKNOWN

        if (left.isInt() && right.isInt()) {
            return if (left.value!! < right.value!!) Result.CORRECT
            else if (left.value > right.value) Result.INCORRECT
            else Result.UNKNOWN
        }

        val leftList = if (left.isInt()) left.toList() else left
        val rightList = if (right.isInt()) right.toList() else right

        val firstDecisive = leftList.children.zip(rightList.children).takeWhileInclusive { compare(it.first, it.second) == Result.UNKNOWN }.last()
        val r = compare(firstDecisive.first, firstDecisive.second)
        if (r != Result.UNKNOWN) return r

        return if (leftList.children.size < rightList.children.size) Result.CORRECT
        else if (leftList.children.size > rightList.children.size) Result.INCORRECT
        else Result.UNKNOWN
    }

    fun pt1(input: List<String>): Int {
        val packets = input.groupBy { it.isNotEmpty() }.map { l -> parsePacket(l.first()).second to parsePacket(l.last()).second }
        return packets.foldIndexed(emptyList<Int>()) { index, acc, pair -> if (compare(pair.first, pair.second) == Result.CORRECT) acc + (index + 1) else acc }.sum()
    }

    fun pt2(input: List<String>): Int {
        val div1 = parsePacket("[[2]]").second
        val div2 = parsePacket("[[6]]").second
        val packets = input.filter { it.isNotEmpty() }.map { parsePacket(it).second }.plus(listOf(div1, div2)).sorted()
        return (packets.indexOf(div1)+1) * (packets.indexOf(div2)+1)
    }
}