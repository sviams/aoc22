object day17 {

    data class Shape(val units: List<Pos>) {
        val fb: List<Pos> by lazy {
            val xRange = units.map { it.x }.distinct()
            xRange.map { x ->
                val asd = units.filter { it.x == x }.minOf { it.y }
                Pos(x,asd)
            }
        }

        val rightEdge: List<Pos> by lazy {
            units.map { it.y }.distinct().map { y -> units.filter { it.y == y }.maxBy { it.x } }
        }

        val leftEdge: List<Pos> by lazy {
            units.map { it.y }.distinct().map { y -> units.filter { it.y == y }.minBy { it.x } }
        }
    }

    val BAR = Shape(listOf(Pos(0,0), Pos(1,0), Pos(2,0), Pos(3,0)))
    val PLUS = Shape(listOf(Pos(0, 1), Pos(1, 2), Pos(1, 1), Pos(1, 0), Pos(2,1)))
    val ANGLE = Shape(listOf(Pos(0,0),Pos(1,0),Pos(2,0),Pos(2,1),Pos(2,2)))
    val SPEAR = Shape(listOf(Pos(0,0), Pos(0,1), Pos(0,2), Pos(0,3)))
    val CUBE = Shape(listOf(Pos(0,0), Pos(0,1), Pos(1,0), Pos(1,1)))
    val SHAPES = listOf(BAR, PLUS, ANGLE, SPEAR, CUBE)

    fun rightSideCollides(falling: Shape, bottom: Map<Int, Set<Int>>, dX: Int, dY: Int): Boolean {
        return falling.rightEdge.any { p ->
            val actualY = p.y + dY
            val collidesWithWall = p.x+dX > 6
            collidesWithWall || bottom[p.x+dX]!!.contains(actualY)
        }
    }

    fun leftSideCollides(falling: Shape, bottom: Map<Int, Set<Int>>, dX: Int, dY: Int): Boolean {
        return falling.leftEdge.any { p ->
            val actualY = p.y + dY
            val collidesWithWall = p.x + dX < 0
            collidesWithWall || bottom[p.x+dX]!!.contains(actualY)
        }
    }

    tailrec fun solve(jet: String, limit: Long, bottom: Map<Int, Set<Int>> = (0 .. 6).map { it to setOf(0) }.toMap(), falling: Shape = BAR, dX: Int = 2, dY: Int = 4, jetIndex: Int = 0, shapeIndex: Int = 0, atRest: Long = 0, memo: MutableMap<String, Pair<Long, Int>> = mutableMapOf()): Long {
        if (atRest == limit) return bottom.values.maxOf { it.max() }.toLong()
        val newDx =
            when (jet[jetIndex]) {
                '>' -> if (rightSideCollides(falling, bottom, dX+1, dY)) dX else dX + 1
                else -> if (leftSideCollides(falling, bottom, dX-1, dY)) dX else dX - 1
            }

        val atRiskOfCollision = falling.fb.map { Pos(it.x+newDx, dY+it.y-1) }
        val wouldLand = atRiskOfCollision.any { bottom[it.x]!!.contains(it.y) }
        val newShapeIndex = if (wouldLand) (shapeIndex+1) % SHAPES.size else shapeIndex
        val newShape = if (wouldLand) SHAPES[newShapeIndex] else falling
        val newAtRest = if (wouldLand) atRest+1 else atRest
        val newJetIndex = (jetIndex+1) % jet.length

        val newBottom = if (wouldLand) {
            falling.units.fold(bottom) { acc, unit ->
                acc.plus(unit.x+newDx to acc[unit.x+newDx]!!.plus(unit.y+dY))
            }
        } else bottom

        val newDy = if (wouldLand) newBottom.values.maxOf { it.max() } + 4 else dY - 1

        val columnPeaks = (0 .. 6).map { newBottom[it]!!.max() }
        val height = columnPeaks.max()
        val minPeak = columnPeaks.min()
        val normalizedColumnPeaks = columnPeaks.map { it - minPeak }
        val m = "$jetIndex $shapeIndex $dX $normalizedColumnPeaks"

        if (!memo.contains(m)) memo.put(m, newAtRest to height)
        else {
            val (prevAtRest, prevHeight) = memo[m]!!
            val cycleRocks = newAtRest - prevAtRest
            if (newAtRest.toLong() % cycleRocks == limit % cycleRocks) {
                val cycleHeight = height - prevHeight
                val remainingRocks = limit - newAtRest
                val cyclesLeft = (remainingRocks / cycleRocks) + 1
                return prevHeight + (cycleHeight * cyclesLeft)
            }
        }
        return solve(jet, limit, newBottom, newShape, if (wouldLand) 2 else newDx, newDy, newJetIndex, newShapeIndex, newAtRest, memo)
    }

    fun pt1(input: List<String>): Long = solve(input.first(), 2022)

    fun pt2(input: List<String>): Long = solve(input.first(), 1000000000000L)
}