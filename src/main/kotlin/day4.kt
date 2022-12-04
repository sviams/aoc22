object day4 {

    fun parse(input: List<String>): List<Pair<IntRange, IntRange>> =
        input.fold(emptyList()) { acc, line ->
            val (a,b,c,d) = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex().find(line)!!.destructured
            acc + Pair(IntRange(a.toInt(),b.toInt()), IntRange(c.toInt(),d.toInt()))
        }

    fun pt1(input: List<String>): Int =
        parse(input).count { it.first.contains(it.second) || it.second.contains(it.first) }

    fun pt2(input: List<String>): Int =
        parse(input).count { it.first.intersects(it.second) }
}