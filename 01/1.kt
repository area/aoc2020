import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()



fun main(args: Array<String>) {
	val numbersAsStrings = readFileAsLinesUsingReadLines("./input.txt")
	val numbers = numbersAsStrings.map{ it.toInt() }

	for (n in 0..(numbers.size-1)) {
		for (m in n..(numbers.size-1)){
			if (numbers[n] + numbers[m] == 2020) {
				println(numbers[n])
				println(numbers[m])
				println(numbers[n]*numbers[m])
			}
		}
	}
	println("**")
	for (n in 0..(numbers.size-1)) {
		for (m in n..(numbers.size-1)){
			for (l in m..(numbers.size-1)){
				if (numbers[n] + numbers[m] + numbers[l] == 2020) {
					println(numbers[l])
					println(numbers[n])
					println(numbers[m])
					println(numbers[n]*numbers[m]*numbers[l])
				}
			}
		}
	}

}