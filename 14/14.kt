import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val prog = readFileAsLinesUsingReadLines("./input.txt").map{ it.split(" = ")}

fun maskValue(mask: String, value: Long): Long{
	var newValue = value;
	for ((index, char) in mask.reversed().withIndex()){
		if (char == "1".single()){
			newValue = newValue or 2.toDouble().pow(index).toLong()
		} else if (char == "0".single()){
			newValue = newValue and 2.toDouble().pow(index).toLong().inv()
		}
	}
	return newValue
}

fun resolveFloatingBit(bits: String): List<String>{
	var retVal = mutableListOf<String>()
	var resolved = false
	var newBits1 = ""
	var newBits0 = ""
	for (char in bits){
		if (char == "X".single() && resolved == false){
			newBits1 = newBits1 + "1"
			newBits0 = newBits0 + "0"
			resolved = true
		}else{
			newBits1 += char;
			newBits0 += char;
		}
	}
	if (resolved){
		resolveFloatingBit(newBits1).map{retVal.add(it)}
		resolveFloatingBit(newBits0).map{retVal.add(it)}
	}else {
		retVal.add(newBits1)
	}
	return retVal
}

fun maskSlot(mask: String, value: String): String{
	var newValue = ""
	var binValue = value.toLong().toString(2).padStart(36, "0".single());
	for ((index, char) in mask.withIndex()){
		if (char == "1".single()){
			newValue += "1"
		} else if (char == "0".single()){
			newValue += binValue[index]
		} else if (char == "X".single()){
			newValue += "X".single()
		}
	}
	return newValue
}

fun main() {
	var mask = ""
	var memory = mutableMapOf<String, Long>()
	for (line in prog){
		if (line[0] == "mask"){
			mask = line[1]
			continue
		}
		var match = Regex("mem\\[([0-9]*)\\]").find(line[0])!!
		val slot = match.destructured.toList()[0]
		memory[slot] = maskValue(mask, line[1].toLong())
	}
	var total = 0.toLong()
	for ((slot, value) in memory){
		total += value
	}
	println(total)

	memory = mutableMapOf<String, Long>()
	for (line in prog){
		if (line[0] == "mask"){
			mask = line[1]
			continue
		}
		var match = Regex("mem\\[([0-9]*)\\]").find(line[0])!!
		val slot = match.destructured.toList()[0]
		for (s in resolveFloatingBit(maskSlot(mask, slot))){
			memory[s] = line[1].toLong()
		}
	}
	total = 0.toLong()
	for ((slot, value) in memory){
		total += value
	}
	println(total)

}