import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val setup = readFileAsLinesUsingReadLines("./input.txt")

fun combat(d1: MutableList<Int>, d2: MutableList<Int>): Pair<Int, MutableList<Int>>{
	while (d1.size != 0 && d2.size != 0){
		var c1 = d1.removeFirst()
		var c2 = d2.removeFirst()
		if (c1>c2){
			d1.add(c1)
			d1.add(c2)
		} else {
			d2.add(c2)
			d2.add(c1)
		}
	}
	var winningDeck = d1
	var winner = 1
	if (d1.size == 0){
		winningDeck = d2
		winner = 2
	}

	return Pair(winner, winningDeck)
}


fun rcombat(d1: MutableList<Int>, d2: MutableList<Int>): Pair<Int, MutableList<Int>>{
	var history = mutableMapOf<String,Boolean>()
	while (d1.size != 0 && d2.size != 0){
		if (history[d1.toString() + "|" + d2.toString()] == true){
			return Pair(1, d1)
		} else {
			history[d1.toString() + "|" + d2.toString()] = true
		}

		var c1 = d1.removeFirst()
		var c2 = d2.removeFirst()
		var winner: Int
		if (c1 <= d1.size && c2 <= d2.size){
			winner = rcombat(d1.take(c1).toMutableList(), d2.take(c2).toMutableList()).first
		} else if (c1>c2){
			winner = 1
		} else {
			winner = 2
		}
		if (winner == 1){
			d1.add(c1)
			d1.add(c2)
		} else {
			d2.add(c2)
			d2.add(c1)
		}
	}

	var winningDeck = d1
	var winner = 1
	if (d1.size == 0){
		winningDeck = d2
		winner = 2
	}

	return Pair(winner, winningDeck)
}

fun score (d: MutableList<Int>): Int{
	var score = 0
	for ((i, card) in d.withIndex()){
		score += card * (d.size-i)
	}
	return score
}

fun main() {
	var p1deck = mutableListOf<Int>()
	var p2deck = mutableListOf<Int>()
	var activeDeck = p1deck
	for (line in setup){
		if (line == "Player 1:" || line == ""){ continue }
		if (line == "Player 2:"){
			activeDeck = p2deck
			continue
		}
		activeDeck.add(line.toInt())

	}
	var result = combat(p1deck, p2deck)
	println(score(result.second))


	p1deck = mutableListOf<Int>()
	p2deck = mutableListOf<Int>()
	activeDeck = p1deck
	for (line in setup){
		if (line == "Player 1:" || line == ""){ continue }
		if (line == "Player 2:"){
			activeDeck = p2deck
			continue
		}
		activeDeck.add(line.toInt())

	}
	result = rcombat(p1deck, p2deck)
	println(score(result.second))

}