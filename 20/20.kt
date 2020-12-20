import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadText(fileName: String): String
= File(fileName).readText()

val input = readFileAsLinesUsingReadText("./input.txt")

class Tile (textTile: String){
	var id = 0;
	var orientation = 0 //0-7, rotated in 90 degree chunks clockwise, then same but flipped horizontally
	var frame = mutableListOf<String>()
	var sideLength = 0
	init {
		var rows = textTile.split("\n")
		id = rows[0].split(" ")[1].dropLast(1).toInt()
		sideLength = rows.size-1
		for (i in 1..sideLength){
			frame.add(rows[i])
		}
	}

	fun getRow(n:Int): String{
		return getRow(n, orientation)
	}

	fun getRow(n: Int, orientation: Int): String {
		if (n > sideLength-1){ return ""}
		var tile = getTile(orientation)
		return tile.split("\n")[n]
	}

	fun getColumn(n: Int, orientation:Int): String{
		if (n > sideLength-1){ return ""}
		var tile = getTile(orientation).split("\n")
		var col = ""
		for (row in tile){
			col += row[n]
		}
		return col
	}

	fun getTile(orientation: Int): String {
		var newTile = frame.toMutableList();
		var o = orientation
		if (o >= 4){
			for ((idx, row) in newTile.withIndex()){
				newTile[idx] = row.reversed()
			}
			o -= 4
		}
		var pixels =newTile.map{it.split("").drop(1).dropLast(1).toMutableList()}

		var rotatedPixels =pixels.map{it.toMutableList()}
		while (o>0){
			for (i in 0..sideLength -1){
				for (j in 0..sideLength-1){
					rotatedPixels[i][j] = pixels[sideLength-1-j][i]
				}
			}
			pixels = rotatedPixels.map{it.toMutableList()}
			o-=1
		}
		return rotatedPixels.map{it.joinToString("")}.joinToString("\n")
	}

	fun getTile(): String{
		return getTile(orientation);
	}

}

// // Heap's algorithm, non-recursive, shamelessly stolen from wikipedia
// // Ultimately not used, leaving here in case it's needed in the future.
// fun permutations(input: List<Int>) = sequence {
// 	var A = input.toMutableList();
// 	var n = input.size
// 	var c = mutableListOf<Int>();
// 	for (i in 0..n){
// 		c.add(0)
// 	}
// 	yield(A)
// 	var i = 0;
// 	while (i < n){
// 		if (c[i]< i){
// 			if (i%2 ==0){
// 				A[0] = A[i].also{ A[i] = A[0]}
// 			} else {
// 				A[c[i]] = A[i].also{ A[i] = A[c[i]]}
// 			}
// 			yield(A)
// 			c[i] += 1
// 			i = 0
// 		} else {
// 			c[i] = 0
// 			i += 1
// 		}
// 	}
// }

var seaMonstersMap = mutableMapOf<Pair<Int, Int>, Boolean>().withDefault{false}

fun isSeaMonster(image: Tile, o: Int, x:Int, y:Int): Boolean {
	var seaMonster =
		"""                  #
		#    ##    ##    ###
		 #  #  #  #  #  #"""
  	seaMonster = seaMonster.replace("\t", "")

  	var rowOffset = 0;
  	var charOffset = -seaMonster.indexOf("#")
  	for (char in seaMonster){
  		if (char == ' '){
  			charOffset+= 1
  			continue
  		}
  		if (char == '\n') {
  			rowOffset += 1
  			charOffset = -seaMonster.indexOf("#")
  			continue
  		}
  		if (image.getRow(y+rowOffset, o).getOrNull(x+charOffset) != '#'){
  			return false
  		} else {
  			charOffset += 1
  		}
  	}

  	// It's a sea monster, let's mark it on the map
  	rowOffset = 0;
  	charOffset = -seaMonster.indexOf("#")
  	for (char in seaMonster){
  		if (char == ' '){
  			charOffset+= 1
  			continue
  		}
  		if (char == '\n') {
  			rowOffset += 1
  			charOffset = -seaMonster.indexOf("#")
  			continue
  		}
  		seaMonstersMap[Pair(x+charOffset, y+rowOffset)] = true;
  		charOffset += 1
  	}
	return true

}

fun main() {
	var textTiles = input.split("\n\n")
	var tiles = mutableSetOf<Tile>()
	for (tile in textTiles){
		tiles.add(Tile(tile))
	}

	var arraySide = sqrt(tiles.size.toDouble()).toInt()
	var reassembled = mutableMapOf<Pair<Int,Int>,Tile>()

	reassembled[Pair(0,0)] = tiles.first()
	tiles.remove(tiles.first())
	while (tiles.size > 0){
		var placedTile : Tile? = null
		var placedCoord = Pair(0,0)

		tileLoop@ for ((coord,tile) in reassembled){
			if (reassembled[Pair(coord.first, coord.second+1)] == null){
				candidateLoop@ for (candidateTile in tiles){
					for (o in 0..7){
						if(tile.getRow(0, tile.orientation) == candidateTile.getRow(9, o)){
							placedTile= candidateTile
							tiles.remove(candidateTile)
							placedTile.orientation = o
							placedCoord=Pair(coord.first, coord.second+1)
							break@tileLoop
						}
					}
				}
			}
			if (reassembled[Pair(coord.first, coord.second-1)] == null){
				candidateLoop@ for (candidateTile in tiles){
					for (o in 0..7){
						if(tile.getRow(9, tile.orientation) == candidateTile.getRow(0, o)){
							placedTile= candidateTile
							tiles.remove(candidateTile)
							placedTile.orientation = o
							placedCoord=Pair(coord.first, coord.second-1)
							break@tileLoop
						}
					}
				}
			}
			if (reassembled[Pair(coord.first-1, coord.second)] == null){
				candidateLoop@ for (candidateTile in tiles){
					for (o in 0..7){
						if(tile.getColumn(0, tile.orientation) == candidateTile.getColumn(9, o)){
							placedTile = candidateTile
							tiles.remove(candidateTile)
							placedTile.orientation = o
							placedCoord=Pair(coord.first-1, coord.second)
							break@tileLoop
						}
					}
				}
			}
			if (reassembled[Pair(coord.first+1, coord.second)] == null){
				candidateLoop@ for (candidateTile in tiles){
					for (o in 0..7){
						if(tile.getColumn(9, tile.orientation) == candidateTile.getColumn(0, o)){
							placedTile = candidateTile
							tiles.remove(candidateTile)
							placedTile.orientation = o
							placedCoord=Pair(coord.first+1, coord.second)
							break@tileLoop
						}
					}
				}
			}


		}

		if (placedTile != null){
			reassembled[placedCoord] = placedTile
		}
	}
	var xmax = -arraySide
	var ymax = -arraySide
	var xmin = arraySide
	var ymin = arraySide

	for ((coord,) in reassembled){
		if (coord.first>xmax){
			xmax = coord.first
		}
		if (coord.first < xmin){
			xmin = coord.first
		}
		if (coord.second>ymax){
			ymax = coord.second
		}
		if (coord.second < ymin){
			ymin = coord.second
		}
	}
	println(reassembled[Pair(xmin, ymin)]!!.id.toLong()*
		reassembled[Pair(xmax, ymin)]!!.id.toLong()*
		reassembled[Pair(xmin, ymax)]!!.id.toLong()*
		reassembled[Pair(xmax, ymax)]!!.id.toLong()
	)

	var fullImage = mutableMapOf<Int, String>().withDefault{""}

	for (y in ymax downTo ymin){
		for (x in xmin..xmax){
			var tile = reassembled[Pair(x,y)]!!.getTile().split("\n")
			for ((rowidx, row) in tile.drop(1).dropLast(1).asReversed().withIndex()){
				fullImage[(y-ymin)*8+rowidx] = fullImage.getValue((y-ymin)*8+rowidx) + row.drop(1).dropLast(1)
			}
		}
	}
	var p = "Tile 1: "
	for (i in 0..fullImage.size-1){
		p += "\n" + fullImage[i]
	}
	var fullFrame = Tile(p)

	// Search all orientations until we find seamonsters
	for (o in 0..7){
		var monsterCount = 0
  		for ((rowIdx, row) in fullFrame.getTile(o).split("\n").withIndex()){
	  		for ((colIdx,) in row.withIndex()){
	  			if(isSeaMonster(fullFrame, o, rowIdx, colIdx)){
	  				monsterCount += 1
	  			}
	  		}
	  	}
	  	if (monsterCount > 0){
		  	break
	  	}
	}

	// How many #s are sea monsters?
	var seaMonsterPartCount = seaMonstersMap.size
	var hashCount = fullFrame.getTile().count{ "#".contains(it)}
	println(hashCount - seaMonsterPartCount)
}