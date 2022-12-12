object day12 {

    fun walk(map: List<String>, current: Pos, target: Pos, steps: Int = 0) {
        visited.put(current, steps)
        val currentHeight = if (map[current.y][current.x] == 'S') 'a'.code else map[current.y][current.x].code
        val possible = current.neighbors().filter { isPossible(it, currentHeight, map, steps) }
        possible.forEach { walk(map, it, target, steps+1) }
    }

    fun isPossible(p: Pos, currentHeight: Int, map: List<String>, steps: Int): Boolean {
        if (!(p.x >= 0 && p.x < map[0].length && p.y >= 0 && p.y < map.size)) return false
        val h = if (map[p.y][p.x] == 'E') 'z'.code else map[p.y][p.x].code
        if (h - currentHeight > 1) return false
        return visited.getOrDefault(p, Int.MAX_VALUE) > steps+1
    }

    fun findPosOf(c: Char, map: List<String>): Pos =
        map.foldIndexed(Pos.ORIGIN) { row, acc, line ->
            if (line.contains(c)) Pos(line.indexOf(c), row) else acc
        }

    val visited = mutableMapOf<Pos, Int>()

    fun pt1(input: List<String>): Int {
        val start = findPosOf('S', input)
        val goal = findPosOf('E', input)
        walk(input, start, goal, 0)
        return visited[goal] ?: -1
    }

    fun pt2(input: List<String>): Int {
        val start = findPosOf('S', input)
        val goal = findPosOf('E', input)
        val options = start.neighbors().filter { (input.getOrNull(it.y)?.getOrNull(it.x) ?: 'z') == 'a' }
        val best = options.fold(Int.MAX_VALUE) { acc, p ->
            visited.clear()
            walk(input, p, goal)
            if (visited[goal] ?: Int.MAX_VALUE < acc) visited[goal] ?: Int.MAX_VALUE else acc
        }
        return best
    }
}