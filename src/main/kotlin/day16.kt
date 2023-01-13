import com.github.shiguruikai.combinatoricskt.combinations
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.toPersistentHashMap

object day16 {

    data class Valve(val id: String, val flowRate: Int, val leadsTo: List<String>)

    data class DistanceState(val from: Valve, val to: Valve, val valves: Map<String, Valve>, val currentDistance: Int = 0, val visited: List<Valve> = listOf(from))

    data class State(val open: HashSet<Valve>, val current: Valve, val minsLeft: Int, val accFlow: Int)

    fun parse(input: List<String>): Map<String, Valve> =
        input.fold(emptyMap()) { acc, line ->
            val (id, fr, links) = """Valve (\S+) has flow rate\=(\S+)\;.*valve[s |\s](.+)""".toRegex().find(line)!!.destructured
            val l = links.split(", ").map { it.trim() }
            acc + (id to Valve(id, fr.toInt(), l))
        }

    tailrec fun calcShortestDistances(valves: Map<String, Valve>, positiveValves: List<Valve>, left: List<Valve> = positiveValves, result: PersistentMap<Pair<Valve, Valve>, Int> = persistentHashMapOf()): PersistentMap<Pair<Valve, Valve>, Int> {
        if (left.isEmpty()) return result
        val nextValve = left.first()
        val distances = positiveValves.minus(nextValve).map { it to minDistance(DistanceState(nextValve, it, valves)) }
        val newResult = distances.fold(result) { acc, (target, distance) ->
            val sorted = listOf(nextValve, target).sortedBy { it.id }
            val pair = sorted.first() to sorted.last()
            if (acc.getOrDefault(pair, Int.MAX_VALUE) > distance) acc.plus(pair to distance).toPersistentHashMap() else acc
        }
        return calcShortestDistances(valves, positiveValves, left.minus(nextValve), newResult)
    }

    val minDistance = DeepRecursiveFunction { s: DistanceState ->
        if (s.from == s.to) s.currentDistance
        val options = s.from.leadsTo.map { s.valves[it]!! }.filter { !s.visited.contains(it) }
        if (options.contains(s.to)) s.currentDistance +1
        else if (options.isEmpty()) Int.MAX_VALUE
        else options.minOf { callRecursive(s.copy(from = it, to = s.to, currentDistance = s.currentDistance+1, visited = s.visited.plus(it))) as Int}
    }

    fun solve(input: List<String>, part1: Boolean): Int {
        val valves = parse(input)
        val valvesWithPositiveFlow = valves.values.filter { it.flowRate > 0 }
        val shortestDistances: PersistentMap<Pair<Valve, Valve>, Int> = calcShortestDistances(valves, valvesWithPositiveFlow + valves["AA"]!!)
        val mem: MutableMap<HashSet<Valve>, Int> = mutableMapOf()

        val findMaxFlows = DeepRecursiveFunction { s: State ->
            mem.put(s.open, maxOf(mem.getOrDefault(s.open, Int.MIN_VALUE), s.accFlow))
            if (s.minsLeft == 0 || s.open.size == valvesWithPositiveFlow.size) s.accFlow
            else if (mem.getOrDefault(s.open, Int.MIN_VALUE) > s.accFlow) -1
            else {
                valvesWithPositiveFlow.minus(s.open).maxOf { option ->
                    val sortedKey = listOf(s.current, option).sortedBy { it.id }
                    val shortestPath = shortestDistances[sortedKey.first() to sortedKey.last()]!!
                    val timeLeft = s.minsLeft - shortestPath - 1
                    if (timeLeft < 0) s.accFlow else
                    callRecursive(s.copy(
                        open = s.open.plus(option).toHashSet(),
                        current = option,
                        minsLeft = timeLeft,
                        accFlow = s.accFlow + timeLeft * option.flowRate
                    )) as Int
                }
            }
        }

        val highest = findMaxFlows(State(hashSetOf(), valves["AA"]!!, if (part1) 30 else 26, 0))

        return if (part1) highest else
            mem.filter { (k,v) -> k.isNotEmpty() }
                .toList()
                .combinations(2)
                .filter { (a,b) -> a.first.intersect(b.first).isEmpty() }
                .map { (a,b) -> a.second + b.second }
                .max()
    }

    fun pt1(input: List<String>): Int = solve(input, true)

    fun pt2(input: List<String>): Int = solve(input, false)
}