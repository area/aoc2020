import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val instructions = readFileAsLinesUsingReadLines("./input.txt").map{listOf(it.substring(0,1), it.substring(1))}

fun main() {
	var x = 0
	var y = 0
	var dir = 90
	for (i in instructions){
		if (i[0] == "N"){
			y += i[1].toInt()
		} else if (i[0] == "S"){
			y -= i[1].toInt()
		} else if (i[0] == "W"){
			x -= i[1].toInt()
		} else if (i[0] == "E"){
			x += i[1].toInt()
		} else if (i[0] == "L"){
			dir -= i[1].toInt()
			dir += 360
			dir %= 360
		} else if (i[0] == "R"){
			dir += i[1].toInt()
			dir %= 360
		} else if (i[0] == "F"){
			if (dir==0){
				y += i[1].toInt()
			} else if (dir == 90){
				x += i[1].toInt()
			} else if (dir == 180){
				y -= i[1].toInt()
			} else if (dir == 270){
				x -= i[1].toInt()
			}
		}
	}
	println(kotlin.math.abs(x) + kotlin.math.abs(y))

	x = 0
	y = 0
	var wx = 10
	var wy = 1
	for (i in instructions){
		if (i[0] == "N"){
			wy += i[1].toInt()
		} else if (i[0] == "S"){
			wy -= i[1].toInt()
		} else if (i[0] == "W"){
			wx -= i[1].toInt()
		} else if (i[0] == "E"){
			wx += i[1].toInt()
		} else if (i[0] == "L"){
			for (j in 90..i[1].toInt() step 90){
				var t = wx
				wx = -wy
				wy = t
			}
		} else if (i[0] == "R"){
			for (j in 90..i[1].toInt() step 90){
				var t = wx
				wx = wy
				wy = -t
			}
		} else if (i[0] == "F"){
			x += wx * i[1].toInt()
			y += wy * i[1].toInt()
		}
	}
	println(kotlin.math.abs(x) + kotlin.math.abs(y))
}