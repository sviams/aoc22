object day1 {

    private fun sumsPerElf(input: List<String>) =
        input.groupBy { it.isNotBlank() }.map { elf -> elf.sumOf { it.toInt() } }

    fun pt1(input: List<String>): Int = sumsPerElf(input).max()

    fun pt2(input: List<String>): Int = sumsPerElf(input).sortedDescending().take(3).sum()

}