import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")



fun main() {
	var pubkeys = mutableListOf<Long>()
	for (line in input){
		pubkeys.add(line.toLong())
	}
	println(pubkeys)

	var secretLoopSizes = mutableListOf<Long>()
	for (k in pubkeys){
		var value = 1.toLong()
		var subjectNumber = 7
		var loops = 0.toLong()
		while (value != k){
			value *= subjectNumber
			value %= 20201227
			loops += 1
		}
		secretLoopSizes.add(loops)
	}
	println(secretLoopSizes)

	var value = 1.toLong()
	var subjectNumber = pubkeys[0]
	for (i in 1..secretLoopSizes[1]){
		value *= subjectNumber
		value %= 20201227

	}
	println(value)
}