object day2 {

    private fun solve(input: List<String>, meFunc: (String, String) -> String): Int =
        input.fold(0) { acc, line ->
            val (you,right) = """(\S+) (\S+)""".toRegex().find(line)!!.destructured
            val me = meFunc(right, you)
            acc + me.codePointAt(0)-64 + if (wins.contains(me to you)) 6 else if (me == you) 3 else 0
        }

    private val wins = listOf("A" to "C", "B" to "A", "C" to "B")

    fun pt1(input: List<String>): Int =
        solve(input) { right, _ -> mapOf("X" to "A", "Y" to "B", "Z" to "C")[right]!! }

    fun pt2(input: List<String>): Int =
        solve(input) { right, left ->
            when (right) {
                "X" -> wins.first { it.first == left }.second
                "Y" -> left
                else -> wins.first { it.second == left }.first
            }
        }
}