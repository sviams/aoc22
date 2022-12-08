object day8 {

    fun isVisibleFromAnyDirection(col: Int, row: Int, forest: List<String>): Boolean {
        val thisTree = forest[row][col].digitToInt()
        val above = (0 until row).any { r -> forest[r][col].digitToInt() >= thisTree }
        val below = (row+1 until forest.size).any { r -> forest[r][col].digitToInt() >= thisTree }
        val left = (0 until col).any { c -> forest[row][c].digitToInt() >= thisTree }
        val right = (col+1 until forest.first().length).any { c -> forest[row][c].digitToInt() >= thisTree }
        return !above || !below || !left || !right
    }

    fun scenicScore(col: Int, row: Int, forest: List<String>): Int {
        val thisTree = forest[row][col].digitToInt()
        val above = (0 until row).reversed().toList().takeWhileInclusive { r -> forest[r][col].digitToInt() < thisTree }.count()
        val below = (row+1 until forest.size).toList().takeWhileInclusive { r -> forest[r][col].digitToInt() < thisTree }.count()
        val left = (0 until col).reversed().toList().takeWhileInclusive { c -> forest[row][c].digitToInt() < thisTree }.count()
        val right = (col+1 until forest.first().length).toList().takeWhileInclusive { c -> forest[row][c].digitToInt() < thisTree }.count()
        return above * below * left * right
    }

    fun pt1(input: List<String>): Int =
        input.foldIndexed(0) { rowIndex, acc, row ->
            acc + row.foldIndexed(0) { colIndex, colAcc, _ ->
                colAcc + if (isVisibleFromAnyDirection(colIndex, rowIndex, input)) 1 else 0
            }
        }

    fun pt2(input: List<String>): Int =
        input.foldIndexed(0) { rowIndex, acc, row ->
            val rowScore = row.foldIndexed(0) { colIndex, colAcc, _ ->
                val score = scenicScore(colIndex, rowIndex, input)
                if (score > colAcc) score else colAcc
            }
            if (rowScore > acc) rowScore else acc
        }
}