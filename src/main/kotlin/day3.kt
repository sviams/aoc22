object day3 {

    fun priority(c: Char): Int = if (c.isLowerCase()) c.code - 96 else c.code - 38

    fun pt1(input: List<String>): Int =
        input.sumOf { line ->
            line.take(line.length/2).fold(emptyList<Char>()) { common, item ->
                if (line.drop(line.length/2).contains(item)) common + item else common
            }.distinct().sumOf { priority(it) }
        }

    fun pt2(input: List<String>): Int =
        input.windowed(3, 3).fold(emptyList<Char>()) { badges, group ->
            badges + group.first().fold(emptyList<Char>()) { common, item ->
                if (group[1].contains(item) && group[2].contains(item)) common + item else common
            }.distinct()
        }.sumOf { priority(it) }
}