object day7 {

    data class Entry(val name: String, val fileSize: Int, val isDir: Boolean, val children: List<Entry>, val parent: Entry?) {
        val size: Int by lazy { if (!isDir) fileSize else children.sumOf { it.size } }
    }

    tailrec fun unrollStack(current: Entry, stack: List<Entry>): Entry {
        if (stack.isEmpty()) return current
        val parent = stack.last()
        val newChildren = parent.children.filter { it.name != current.name }.plus(current)
        return unrollStack(parent.copy(children = newChildren), stack.dropLast(1))
    }

    fun parse(input: List<String>, current: Entry = Entry("/", -1, true, emptyList(), null), stack: List<Entry> = emptyList()): Entry {
        if (input.isEmpty()) return unrollStack(current, stack)
        val next = input.first()
        return when {
            next.startsWith("$ ls") -> {
                val block = input.drop(1).takeWhile { !it.startsWith("$") }
                val contents = block.map {
                    val (left, right) = it.split(" ")
                    if (left == "dir") Entry(right, -1, true, emptyList(), current)
                    else Entry(right, left.toInt(), false, emptyList(), current)
                }
                parse(input.drop(contents.size+1), current.copy(children = contents), stack)
            }
            next.startsWith("$ cd") -> {
                val dirName = next.split(" ").last()
                if (dirName == "..") {
                    val parent = stack.last()
                    val newChildren = parent.children.filter { it.name != current.name }.plus(current)
                    parse(input.drop(1), parent.copy(children = newChildren), stack.dropLast(1))
                } else parse(input.drop(1), current.children.first { it.name == dirName }, stack + current)
            }
            else -> current
        }
    }
    fun filterDirsBy(root: Entry, filter: (Entry) -> Boolean): List<Entry> {
        val dirs = root.children.filter { it.isDir }
        return dirs.filter(filter) + dirs.flatMap { filterDirsBy(it, filter) }
    }

    fun pt1(input: List<String>): Int = filterDirsBy(parse(input.drop(1))) { it.size <= 100000 }.sumOf { it.size }

    fun pt2(input: List<String>): Int {
        val root = parse(input.drop(1))
        val needed = 30000000 - (70000000 - root.size)
        return filterDirsBy(root) { it.size >= needed }.minByOrNull { it.size }!!.size
    }

}