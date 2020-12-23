import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")

fun getCupNAway(from: Int, n: Int): Int{
	var steps = n
	var currentCup = from
	while (steps>0){
		currentCup = cups.getValue(currentCup)
		steps-=1
	}
	return currentCup
}

fun printCups(){
	var output = ""
 	var p = 1
	for (i in 0..input[0].length-2){
		output += cups[p]
		p = cups[p]!!
	}
	println(output)
}

var cups = mutableMapOf<Int,Int>().withDefault{it + 1}
fun main() {

	var setup = input[0].split("").drop(1).dropLast(1).map{it.toInt()}
	var pointer = setup[0]
	for (i in 0..setup.size-1){
		cups[setup[i]] = setup[(i+1)% setup.size]
	}
	for (i in 0..99){
		// Remove three cups from circle
		var removed = listOf<Int>(getCupNAway(pointer,1),getCupNAway(pointer,2),getCupNAway(pointer,3))
		cups[pointer] = getCupNAway(pointer, 4)
		// Work out destination cup
		var destination = pointer - 1
		if (destination == 0) { destination = setup.size }
		while (destination in removed){
			destination -= 1
			if (destination == 0) { destination = setup.size }
		}
		// Place cups
		cups[removed[2]] = cups[destination]!!
		cups[destination] = removed[0]
		// Change active cup
		pointer = cups[pointer]!!
	}
	printCups()


	pointer = setup[0]
	for (i in 0..setup.size-1){
		cups[setup[i]] = setup[(i+1)% setup.size]
	}
	cups[setup[setup.size-1]] = setup.size + 1
	cups[1000000] = setup[0]

	for (i in 0..9999999){
		// Remove three cups from circle
		var removed = listOf<Int>(getCupNAway(pointer,1),getCupNAway(pointer,2),getCupNAway(pointer,3))
		cups[pointer] = getCupNAway(pointer, 4)
		// Work out destination cup
		var destination = pointer - 1
		if (destination == 0) { destination = 1000000 }
		while (destination in removed){
			destination -= 1
			if (destination == 0) { destination = 1000000 }
		}
		// Place cups
		cups[removed[2]] = cups.getValue(destination)
		cups[destination] = removed[0]
		// Change active cup
		pointer = cups.getValue(pointer)
	}
	println(getCupNAway(1, 1).toLong() * getCupNAway(1, 2).toLong())
}