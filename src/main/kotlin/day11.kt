object day11 {

    data class Monkey(val items: List<Long>, val op: (Long) -> Long, val target: (Long) -> Int)

    fun parse(input: List<String>): List<Monkey> =
        input.groupBy { it.isNotEmpty() }.map { raw ->
            val start = raw[1].drop(18).split(", ").map { it.toLong() }
            val op = raw[2].drop(23).split(" ")
            val worryOp: (Long) -> Long = { old ->
                if (op[0] == "+") {
                    if (op[1] == "old") old + old else old + op[1].toInt()
                } else {
                    if (op[1] == "old") old * old else old * op[1].toInt()
                }
            }
            val d = raw[3].split(" ").last().toLong()
            val ttrue = raw[4].split(" ").last().toInt()
            val tfalse = raw[5].split(" ").last().toInt()
            val divOp : (Long) -> Int = { a -> if (a % d == 0L) ttrue else tfalse }
            Monkey(start, worryOp, divOp)
        }

    tailrec fun execute(monkeys: List<Monkey>, items: Map<Int, List<Long>>, rounds: Int, lcd: Long, round: Int = 0, insp: MutableMap<Int, Int> = mutableMapOf()): MutableMap<Int, Int> {
        if (round == rounds) return insp
        val afterInsp = monkeys.foldIndexed(items) { monkeyIndex, acc, monkey ->
            val monkeyItems = acc[monkeyIndex]!!
            val newAcc = monkeyItems.fold(acc) { itemAcc, item ->
                val newWorry = if (rounds > 20) monkey.op(item) % lcd else monkey.op(item) / 3
                val targetMonkey = monkey.target(newWorry)
                val meWithout = itemAcc[monkeyIndex]!!.drop(1)
                val targetWith = itemAcc[targetMonkey]!!.plus(newWorry)
                val inspValue = (insp[monkeyIndex] ?: 0) + 1
                insp[monkeyIndex] = inspValue
                itemAcc.minus(monkeyIndex).plus(monkeyIndex to meWithout).minus(targetMonkey).plus(targetMonkey to targetWith)
            }
            newAcc
        }
        return execute(monkeys, afterInsp, rounds, lcd, round+1, insp)
    }

    fun solve(input: List<String>, rounds: Int): Long {
        val monkeys = parse(input)
        val lcd = input.filter { it.startsWith("  Test") }.map { it.split(" ").last().toLong() }.fold(1L) { acc, i -> acc * i}
        val items = monkeys.foldIndexed(emptyMap<Int, List<Long>>()) { index, acc, monkey -> acc.plus(index to monkey.items) }
        return execute(monkeys, items, rounds, lcd).values.sortedDescending().take(2).fold(1) { acc, i -> acc * i }
    }

    fun pt1(input: List<String>): Long = solve(input, 20)

    fun pt2(input: List<String>): Long = solve(input, 10000)
}