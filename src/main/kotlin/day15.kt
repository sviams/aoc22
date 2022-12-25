import kotlin.math.abs

object day15 {

    data class Sensor(val pos: Pos, val beacon: Pos) {
        val radius = pos.distanceTo(beacon)
        val borders: List<Vector> by lazy {
            val up = pos.copy(y = pos.y-radius-1)
            val down = pos.copy(y = pos.y+radius+1)
            val left = pos.copy(x = pos.x-radius-1)
            val right = pos.copy(x = pos.x+radius+1)
            listOf(Vector(up, right), Vector(right, down), Vector(down, left), Vector(left, up))
        }
    }

    fun parse(input: List<String>): List<Sensor> =
        input.fold(emptyList()) { acc, line ->
            val (sx,sy,bx,by) = """Sensor at x\=(\S+)\, y\=(\S+)\: closest beacon is at x\=(\S+)\, y\=(\S+)""".toRegex().find(line)!!.destructured
            acc + Sensor(Pos(sx.toInt(), sy.toInt()), Pos(bx.toInt(), by.toInt()))
        }

    fun pt1(input: List<String>, y: Int): Int {
        val sensors = parse(input)
        val minX = sensors.minBy { it.pos.x }
        val maxX = sensors.maxBy { it.pos.x }
        val impossible = (minX.pos.x-minX.radius .. maxX.pos.x+maxX.radius).fold(0) { acc, x ->
            if (isPosInSensorRange(Pos(x,y), sensors)) acc + 1 else acc
        }
        return impossible-1
    }

    tailrec fun findCandidates(borders: List<Vector>, sensors: List<Sensor>, result: Map<Pos, Int> = emptyMap()): Map<Pos, Int> {
        if (borders.isEmpty()) return result
        val b = borders.first()
        val x = findPointsOnMultipleBorders(b.start, b.end, sensors)
        val newResult = if (x == null) result else result.plus(x to countBorders(x, sensors))
        return findCandidates(borders.drop(1), sensors, newResult)
    }

    tailrec fun isPosInSensorRange(p: Pos, sensors: List<Sensor>): Boolean {
        if (sensors.isEmpty()) return false
        val s = sensors.first()
        return if (p.distanceTo(s.pos) <= s.radius) true else isPosInSensorRange(p, sensors.drop(1))
    }

    fun isPointOnLine(p: Pos, v: Vector): Boolean {
        val xDiffStart = abs(v.start.x - p.x)
        val yDiffStart = abs(v.start.y - p.y)
        val xDiffEnd = abs(v.end.x - p.x)
        val yDiffEnd = abs(v.end.y - p.y)
        return xDiffStart == yDiffStart && xDiffEnd == yDiffEnd
    }

    fun countBorders(p: Pos, sensors: List<Sensor>): Int = sensors.count { s -> s.borders.any { b -> isPointOnLine(p, b) } }

    tailrec fun findPointsOnMultipleBorders(start: Pos, end: Pos, sensors: List<Sensor>): Pos? {
        if (countBorders(start, sensors) >= 4) return start
        if (start == end) return null
        val newX = if (start.x < end.x) start.x+1 else if (start.x > end.x) start.x-1 else start.x
        val newY = if (start.y < end.y) start.y+1 else if (start.y > end.y) start.y-1 else start.y
        return findPointsOnMultipleBorders(Pos(newX, newY), end, sensors)
    }

    fun pt2(input: List<String>): Long {
        val sensors = parse(input)
        val candidates = findCandidates(sensors.flatMap { it.borders }, sensors)
        val p = candidates.keys.first { c -> !isPosInSensorRange(c, sensors) }
        return p.x * 4000000L + p.y
    }
}