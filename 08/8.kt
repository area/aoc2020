import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val instructions = readFileAsLinesUsingReadLines("./input.txt")

fun getAccAtStartOfLoopOrEnd(instructions: List<String>): List<Int> {
	var accumulator = 0
	var pointer = 0
	var history = mutableMapOf<Int, Boolean>().withDefault{false}

	while (!history.getValue(pointer)){
		if (pointer == instructions.size){
			return listOf(accumulator, 1)
		}
		history.set(pointer, true)
		var (instruction, arg) = instructions[pointer].split(" ", limit = 2)
		if (instruction == "nop"){
			pointer += 1
		} else if (instruction == "acc") {
			accumulator += arg.toInt()
			pointer += 1
		} else if (instruction == "jmp") {
			pointer += arg.toInt()
		}
	}
	return listOf(accumulator, 0)
}

fun main() {
	println(getAccAtStartOfLoopOrEnd(instructions)[0])

	for ((idx, line) in instructions.withIndex()){
		var (instruction, arg) = line.split(" ", limit = 2)
		var changedInstructions = instructions.toMutableList()
		if (instruction == "jmp"){
			changedInstructions[idx] = "nop " + arg
			var (acc, status) = getAccAtStartOfLoopOrEnd(changedInstructions)
			if ( status == 1){
				println(acc)
			}
		} else if (instruction == "nop"){
			changedInstructions[idx] = "jmp " + arg
			var (acc, status) = getAccAtStartOfLoopOrEnd(changedInstructions)
			if (status == 1){
				println(acc)
			}
		}
	}
}