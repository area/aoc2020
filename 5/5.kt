import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val passes = readFileAsLinesUsingReadLines("./input.txt")

fun getSeatId(pass: String): Int {
	var rowmin = 0
	var rowmax = 127
	for (c in pass.subSequence(0,7)){
		if (c == "F".single()){
			rowmax -= (rowmax-rowmin) / 2 + 1
		} else if (c == "B".single()){
			rowmin += (rowmax-rowmin) / 2 + 1
		} else {
			println("???")
		}
	}
	var colmin = 0
	var colmax = 7
	for (c in pass.subSequence(7,10)){
		if (c == "R".single()){
			colmin += (colmax - colmin) / 2 + 1
		} else if (c == "L".single()){
			colmax -= (colmax - colmin) / 2 + 1
		} else {
			println("???")
		}
	}
	return rowmax*8 + colmax
}


fun main(args: Array<String>) {
	var passIds = passes.map{getSeatId(it)}
	println(passIds.maxOrNull())

	passIds = passIds.sorted()
	for ((index, value) in passIds.withIndex()){
		if (passIds[index] +2 == passIds[index+1]){
			println(passIds[index] + 1)
			break
		}
	}
}