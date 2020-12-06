import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()



fun main(args: Array<String>) {
	val input = readFileAsLinesUsingReadLines("./input.txt")
	var validCount = 0
	for (entry in input){
		val fields = entry.split(" ")
		val range = fields[0].split("-").map{ it.toInt() };
		val character = fields[1].split(":")[0].single()
		val password = fields[2]
		val count = password.count{ it == character }
		if ( count >= range[0] && count <= range[1] ) {
			validCount += 1
		}
	}
	println(validCount)
	println("**")
	validCount = 0
	for (entry in input){
		val fields = entry.split(" ")
		val positions = fields[0].split("-").map{ it.toInt() };
		val character = fields[1].split(":")[0].single()
		val password = fields[2]
		val valid = (password.get(positions[0]-1) == character).xor( password.get(positions[1]-1) == character)
		if (valid) {
			validCount += 1
		}
	}
	println(validCount)
}