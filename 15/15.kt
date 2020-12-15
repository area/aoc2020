import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val numbers = readFileAsLinesUsingReadLines("./input.txt")[0].split(",").map{ it.toInt()}

fun nthNumber(turnLimit: Int): Int{
	var history = mutableMapOf<Int,Pair<Int, Int>>().withDefault{Pair(0,0)}
	var turn = 0
	var lastSpoken = 0
	for (n in numbers){
		turn += 1
		history[n] = Pair(turn, history.getValue(n).first)
		lastSpoken = n
	}
	while (turn < turnLimit){
		turn += 1
		if (history.getValue(lastSpoken).second == 0) {
			// Then it's not been said before
			lastSpoken = 0
			history[0] = Pair(turn, history.getValue(0).first)
		} else {
			lastSpoken = history.getValue(lastSpoken).first - history.getValue(lastSpoken).second
			history[lastSpoken] = Pair(turn, history.getValue(lastSpoken).first)
		}
	}
	return lastSpoken

}

fun main() {
	println(nthNumber(2020))
	println(nthNumber(30000000))
}