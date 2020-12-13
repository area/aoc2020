import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val notes = readFileAsLinesUsingReadLines("./input.txt")

fun main() {
	val timestamp = notes[0].toInt()
	val buses = notes[1].split(',')
	var smallestDepartureDelay = 10000000
	var earliestBus = 0
	for (b in buses){
		if (b == "x") { continue }
		var t = b.toInt() - timestamp % b.toInt()
		if (t < smallestDepartureDelay) {
			smallestDepartureDelay = t
			earliestBus = b.toInt()
		}
	}
	println(smallestDepartureDelay*earliestBus)

	var busDelays: MutableMap<Long, Long> = mutableMapOf()



	for (i in buses.indices){
		if (buses[i] == "x") { continue }
		busDelays[buses[i].toLong()] = i.toLong()
	}

	var offset = 1.toLong()
	var candidate = 1.toLong()
	for ((k,v) in busDelays){
		if (v == 0.toLong()){
			candidate = k
			offset = k
			continue
		}

		while (candidate % k != k - busDelays[k]!!%k){
			candidate += offset;
		}
		offset *= k
	}
	println(candidate)
}