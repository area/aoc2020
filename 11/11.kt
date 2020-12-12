import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val seating = readFileAsLinesUsingReadLines("./input.txt").map{it.split("")}

fun countAdjacentOccupied(seating: List<List<String>>, i: Int, j: Int): Int {
	var count = 0
	for (m in -1..1){
		for (n in -1..1){
			if (m != 0 || n!= 0){
				if (seating.elementAtOrNull(i+m) != null && seating[i+m].elementAtOrNull(j+n) != null){
					if (seating[i+m][j+n] == "#"){
						count +=1
					}
				}
			}
		}
	}
	return count
}

fun countVisibleOccupied(seating: List<List<String>>, i: Int, j: Int): Int {
	var count = 0
	for (m in -1..1){
		for (n in -1..1){
			if (m != 0 || n!= 0){
				var multiplier = 1
				var seen = false
				while (seen == false && seating.elementAtOrNull(i+m*multiplier) != null && seating[i+m*multiplier].elementAtOrNull(j+n*multiplier) != null){
					var space = seating[i+m*multiplier][j+n*multiplier]
					if ( space == "#"){
						count +=1
						seen = true
					} else if ( space == "L"){
						seen = true
					}
					multiplier +=1
				}
			}
		}
	}
	return count
}


fun evolve(seating: List<List<String>>): List<List<String>>{
	var newSeating = mutableListOf<MutableList<String>>()
	for (i in seating.indices){
		newSeating.add(mutableListOf<String>())
		var row = seating[i]
		for (j in row.indices){
			var adjacentOccupied = countAdjacentOccupied(seating, i, j);
			if (adjacentOccupied == 0 && seating[i][j] == "L"){
				newSeating[i].add("#")
			} else if (adjacentOccupied >= 4 && seating[i][j] == "#"){
				newSeating[i].add("L")
			} else {
				newSeating[i].add(seating[i][j])
			}
		}
	}

	return newSeating
}


fun evolve2(seating: List<List<String>>): List<List<String>>{
	var newSeating = mutableListOf<MutableList<String>>()
	for (i in seating.indices){
		newSeating.add(mutableListOf<String>())
		var row = seating[i]
		for (j in row.indices){
			var visibleOccupied = countVisibleOccupied(seating, i, j);
			if (visibleOccupied == 0 && seating[i][j] == "L"){
				newSeating[i].add("#")
			} else if (visibleOccupied >= 5 && seating[i][j] == "#"){
				newSeating[i].add("L")
			} else {
				newSeating[i].add(seating[i][j])
			}
		}
	}

	return newSeating
}

fun main() {
	var prevSeating = seating;
	var newSeating = evolve(prevSeating);
	while (prevSeating.flatten().joinToString() != newSeating.flatten().joinToString()){
		prevSeating = newSeating
		newSeating = evolve(prevSeating);
	}
	println(newSeating.flatten().joinToString().count{ it == "#".single() })

	prevSeating = seating;
	newSeating = evolve2(prevSeating);
	while (prevSeating.flatten().joinToString() != newSeating.flatten().joinToString()){
		prevSeating = newSeating
		newSeating = evolve2(prevSeating);
	}
	println(newSeating.flatten().joinToString().count{ it == "#".single() })

}