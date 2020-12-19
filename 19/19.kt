import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")

var rules = mutableMapOf<String, List<String>>()

fun matches(input: String, ruleString: String): Boolean {
	if (input == "" && ruleString == "") return true
	if (input == "" || ruleString == "") return false
	var ruleStringSplit = ruleString.split(" ")
	var nextRule = ruleStringSplit[0]
	if (rules[nextRule]!![0][0] == "\"".single()){
		if (input.first() == rules[nextRule]!![0][1]){
			return matches(input.drop(1), ruleStringSplit.drop(1).joinToString(" "))
		}
	} else {
		for (subrule in rules[nextRule]!!){
			var resolvedRule = (subrule.split(" ") + ruleStringSplit.drop(1)).joinToString(" ")
			if(matches(input, resolvedRule)){
				return true
			}
		}
	}
	return false
}


fun main() {
	var matchCount = 0
	var rulesRead = false;
	for (line in input){
		if (line == ""){
			rulesRead = true
		} else if (!rulesRead) {
			var (id, rule) = line.split(": ")
			rules[id] = rule.split(" | ")
		} else { // we need to figure out of this string matches the rules.
			if (matches(line, rules["0"]!![0])){
				matchCount += 1
			}
		}
	}
	println(matchCount)

	matchCount = 0
	rulesRead = false;
	for (line in input){
		if (line == ""){
			rulesRead = true
			rules["8"] = "42 | 42 8".split(" | ")
			rules["11"] = "42 31 | 42 11 31".split(" | ")
		} else if (!rulesRead) {
			var (id, rule) = line.split(": ")
			rules[id] = rule.split(" | ")
		} else { // we need to figure out of this string matches the rules.
			if (matches(line, rules["0"]!![0])){
				matchCount += 1
			}
		}
	}
	println(matchCount)

}