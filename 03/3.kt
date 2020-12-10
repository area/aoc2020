import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val rows = readFileAsLinesUsingReadLines("./input.txt")
val width = rows[0].length;

fun countTrees(right: Int, down: Int): Int {
	var col = 0
	var row = 0
	var treeCount = 0;
	while (row < rows.size){
		if (rows[row].get(col) == "#".single()) {
			treeCount += 1
		}
		row = row + down
		col = (col + right) % width
	}
	return treeCount
}


fun main(args: Array<String>) {
	println(countTrees(3,1))
	var treeCounts = listOf(
		listOf(1,1),
		listOf(3,1),
		listOf(5,1),
		listOf(7,1),
		listOf(1,2),
	).map{ countTrees(it[0], it[1])}
	println(treeCounts.reduce {acc, i -> acc * i})
}