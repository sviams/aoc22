object day14 {

    tailrec fun parseRocks(input: List<String>, result: Set<Pos> = hashSetOf()): Set<Pos> {
        if (input.isEmpty()) return result
        val stops = input.first().split(" -> ").map { p ->
            val x = p.split(",").map { it.toInt() }
            Pos(x.first(), x.last())
        }
        val allPoints = stops.drop(1).fold(listOf(stops.first())) { acc, stop ->
            acc + Pos.pointsBetween(acc.last(), stop)
        }.toHashSet()
        return parseRocks(input.drop(1), (result + allPoints) as HashSet<Pos>)
    }

    fun grainFall(pos: Pos, obstacles: Set<Pos>, limitY: Int): Pos {
        val down = pos.copy(y = pos.y+1)
        if (down.y > limitY) return Pos.ORIGIN
        if (!obstacles.contains(down)) return grainFall(down, obstacles, limitY)
        val downLeft = down.copy(x = pos.x-1)
        if (!obstacles.contains(downLeft)) return grainFall(downLeft, obstacles, limitY)
        val downRight = down.copy(x = pos.x+1)
        if (!obstacles.contains(downRight)) return grainFall(downRight,obstacles, limitY)
        return pos
    }

    tailrec fun doSand(rocks: Set<Pos>, sand: Set<Pos>, limitY: Int): Set<Pos> {
        val next = grainFall(Pos(500,0), rocks.toSet() + sand, limitY)
        return if (next == Pos.ORIGIN || next == Pos(500,0)) sand else doSand(rocks, sand + next, limitY)
    }

    fun pt1(input: List<String>): Int {
        val rocks = parseRocks(input)
        return doSand(rocks, emptySet(), rocks.maxOf { it.y }).size
    }

    fun pt2(input: List<String>): Int {
        val rocks = parseRocks(input)
        val limitY = rocks.maxOf { it.y }
        val minX = rocks.minOf { it.x }
        val maxX = rocks.maxOf { it.x }
        val floor = Pos.pointsBetween(Pos(minX-limitY, limitY+2), Pos(maxX+limitY, limitY+2)).toSet()
        return doSand(rocks+floor, emptySet(), limitY+3).size + 1
    }
}