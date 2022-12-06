object day6 {

    fun solve(input: String, size: Int): Int {
        val unique = input.windowed(size).takeWhileInclusive { it.toList() != it.toList().distinct() }.last()
        return input.indexOf(unique) + size
    }

    fun pt1(input: String): Int = solve(input, 4)

    fun pt2(input: String): Int = solve(input, 14)

}