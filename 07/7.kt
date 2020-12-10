import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val rules = readFileAsLinesUsingReadLines("./input.txt")
var graph = mutableMapOf<String, MutableMap<String, Int>>().withDefault{mutableMapOf<String, Int>().withDefault{0}}



fun canContainGold(bagType: String): Boolean {
	for ((key) in graph[bagType]!!){
		if (key=="shiny gold"){
			return true
		} else if (canContainGold(key)){
			return true
		}
	}
	return false
}

fun countBagsInside(bagType: String): Int {
	var count = 0
	for ((key) in graph.getValue(bagType)){
		count += (1 + countBagsInside(key)) * graph.getValue(bagType).getValue(key)
	}
	return count
}

fun main() {

	var nCanContainGold = 0

	for (rule in rules){
		var (container, contains) = rule.split(" contain ")
		container = container.replaceAfterLast(" ", "").trim();
		var containsSplit = contains.split(", ").map{it.replaceAfterLast(" ","").trim()}
		var existingRules = graph.getValue(container)
		for (entry in containsSplit){
			val (n, bagType) = entry.split(" ", limit=2);
			if (n != "no"){
				existingRules[bagType] = n.toInt()
			}
		}
		graph.put(container, existingRules)
	}

	for ((key) in graph){
		var valid = canContainGold(key)
		if(valid){
			nCanContainGold+=1
		}
	}
	println(nCanContainGold)

	println(countBagsInside("shiny gold"))

}