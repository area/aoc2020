import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt").map{it.split("")}
var space = mutableMapOf<Triple<Long, Long, Long>, String>().withDefault{"."}
var space4 = mutableMapOf<List<Long>, String>().withDefault{"."}

fun countActiveNeighbors(space: MutableMap<Triple<Long, Long, Long>, String>, x: Long, y: Long, z: Long): Int{
	var count = 0
	for (l in x-1..x+1){
		for (m in y-1..y+1){
			for (n in z-1..z+1){
				if (Triple(l,m,n)!=Triple(x,y,z)){
					if (space.getValue(Triple(l,m,n)) == "#"){
						count += 1
					}
				}
			}
		}
	}

	return count
}

fun countActiveNeighbors4(space: MutableMap<List<Long>, String>, x: Long, y: Long, z: Long, w: Long): Int{
	var count = 0
	for (l in x-1..x+1){
		for (m in y-1..y+1){
			for (n in z-1..z+1){
				for (k in w-1..w+1){
					if (listOf(l,m,n,k)!=listOf(x,y,z,w)){
						if (space.getValue(listOf(l,m,n,k)) == "#"){
							count += 1
						}
					}
				}
			}
		}
	}

	return count
}


fun evolve(space: MutableMap<Triple<Long, Long, Long>, String>): MutableMap<Triple<Long, Long, Long>, String>{
	var newSpace = mutableMapOf<Triple<Long, Long, Long>, String>().withDefault{"."}
	for ((coord, value) in space){
		var (x, y, z) = coord
		for (l in x-1..x+1){
			for (m in y-1..y+1){
				for (n in z-1..z+1){
					if (Triple(l,m,n)!=coord){
						if (newSpace[Triple(l,m,n)] == null){
							var activeNeighbors = countActiveNeighbors(space, l,m,n)
							if (space.getValue(Triple(l,m,n)) == "#"){
								if (activeNeighbors == 2 || activeNeighbors == 3){
									newSpace[Triple(l,m,n)] = "#"
								} else {
									// It dies, so no need to set as the default as .
								}
							} else if (space.getValue(Triple(l,m,n)) == "." && activeNeighbors == 3){
								newSpace[Triple(l,m,n)] = "#"
							}
						}
					}
				}
			}
		}
	}
	return newSpace
}


fun evolve4(space: MutableMap<List<Long>, String>): MutableMap<List<Long>, String>{
	var newSpace = mutableMapOf<List<Long>, String>().withDefault{"."}
	for ((coord, value) in space){
		var (x, y, z, w) = coord
		for (l in x-1..x+1){
			for (m in y-1..y+1){
				for (n in z-1..z+1){
					for (k in w-1..w+1){
						if (listOf(l,m,n,k)!=coord){
							if (newSpace[listOf(l,m,n,k)] == null){
								var activeNeighbors = countActiveNeighbors4(space, l,m,n,k)
								if (space.getValue(listOf(l,m,n,k)) == "#"){
									if (activeNeighbors == 2 || activeNeighbors == 3){
										newSpace[listOf(l,m,n,k)] = "#"
									} else {
										// It dies, so no need to set as the default as .
									}
								} else if (space.getValue(listOf(l,m,n,k)) == "." && activeNeighbors == 3){
									newSpace[listOf(l,m,n,k)] = "#"
								}
							}
						}
					}
				}
			}
		}
	}
	return newSpace
}


fun main() {
	for ((idx, row) in input.withIndex()){
		for ((idy, value) in row.withIndex()){
			if (value == "#"){
				space[Triple(idx.toLong(), idy.toLong(), 0.toLong())] = "#"
			}
		}
	}
	for (i in 1..6){
		space = evolve(space)
	}
	println(space.keys.size)


	for ((idx, row) in input.withIndex()){
		for ((idy, value) in row.withIndex()){
			if (value == "#"){
				space4[listOf(idx.toLong(), idy.toLong(), 0.toLong(), 0.toLong())] = "#"
			}
		}
	}
	for (i in 1..6){
		space4 = evolve4(space4)
	}
	println(space4.keys.size)


}