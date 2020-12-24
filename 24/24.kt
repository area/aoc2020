import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")


var tiles = mutableMapOf<Pair<Int,Int>,Boolean>().withDefault{false}
var adjacentDirections = setOf(Pair(0,1), Pair(1,1), Pair(1,0), Pair(0,-1), Pair(-1,-1), Pair(-1,0))

fun countAdjacentBlack(coord: Pair<Int, Int>): Int{
	var adjacentBlack = 0
	for (adjacent in adjacentDirections){
		var x = adjacent.first + coord.first
		var y = adjacent.second + coord.second
		if (tiles.getValue(Pair(x,y))){
			adjacentBlack += 1
		}
	}
	return adjacentBlack
}

fun main() {
	for (line in input){
		var x = 0
		var y = 0
		var directions = line.split("(?<=[ew])".toRegex())
		if (directions[directions.size -1 ] == ""){ directions = directions.dropLast(1)}
		for (dir in directions){
			if (dir == "e"){
				x +=1
			} else if (dir == "w"){
				x -= 1
			} else if (dir == "nw"){
				y += 1
			} else if (dir == "sw"){
				y -= 1
				x -= 1
			} else if (dir == "ne"){
				y+=1
				x+=1
			} else if (dir == "se"){
				y -=1
			}
		}
		tiles[Pair(x,y)] = !tiles.getValue(Pair(x,y))
	}
	var count = 0
	for ((t, isBlack) in tiles){
		if (isBlack){
			count += 1
		}
	}
	println(count)

	for (g in 0..99){
		var newTiles = mutableMapOf<Pair<Int,Int>,Boolean>().withDefault{false}
		for ((coord, ) in tiles){
			for (adjacent in adjacentDirections.union(setOf(Pair(0,0)))){
				var x = adjacent.first + coord.first
				var y = adjacent.second + coord.second
				if (tiles.getValue(Pair(x,y))){ // ie. if the tile is black
					if (countAdjacentBlack(Pair(x,y)) == 1 || countAdjacentBlack(Pair(x,y)) == 2){
						newTiles[Pair(x,y)] = true
					}
				} else {
					if (countAdjacentBlack(Pair(x,y)) == 2){
						newTiles[Pair(x,y)] = true
					}
				}
			}
		}
		tiles = newTiles
	}
	count = 0
	for ((t, isBlack) in tiles){
		if (isBlack){
			count += 1
		}
	}
	println(count)
}