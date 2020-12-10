import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

fun pairSumTo(pool: List<Long>, target: Long): Boolean {
	for (n in 0..(pool.size-1)){
		for (m in n..(pool.size-1)){
			if (pool[n] + pool[m] == target){
				return true
			}
		}
	}
	return false
}

fun main() {
	val numbersAsStrings = readFileAsLinesUsingReadLines("./input.txt")
	val numbers = numbersAsStrings.map{ it.toLong() }
	val preamble = 25
	var invalidNumber = 0.toLong();
	for (n in 0..(numbers.size-1)) {
		if (n < preamble){
			continue
		}
		if (!pairSumTo(numbers.slice(n-preamble..n-1), numbers[n])){
			invalidNumber = numbers[n]
		}
	}
	println(invalidNumber)
	println("**")
	var start = 0
	var end = 0
	var total = 0.toLong();
	while (total != invalidNumber){
		if (total < invalidNumber){
			end += 1
		}
		if (total > invalidNumber){
			start += 1
		}
		total = numbers.slice(start..end+1).sum()
	}
	var minContributor = numbers.slice(start..end+1).minOrNull()
	var maxContributor = numbers.slice(start..end+1).maxOrNull()
	val result = if (minContributor != null && maxContributor != null) minContributor+maxContributor else null
	println(result)
}