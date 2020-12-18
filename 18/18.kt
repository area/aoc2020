import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")

fun evaluate(x:Long, y:Long, op:String): Long{
	if (op == "+"){
		return x+y
	} else if (op == "*"){
		return x*y
	}
	return 0
}

fun isOperator(op: String): Boolean{
	if (op=="*") { return true}
	if (op=="+") { return true}
	return false
}

fun main() {
	var total = 0.toLong()
	for (line in input){
		var operands = mutableListOf<Long>()
		var operators = mutableListOf<String>()
		var elements = line.replace(")", " ) ").replace("(", " ( ").replace("  ", " ").split(" ")
		for (e in elements){
			// println(operands);
			// println(operators);
			// println("---")

			if (e.toLongOrNull() != null){
				operands.add(e.toLong())
				if (operators.lastOrNull() != null && isOperator(operators.last())){
					var x = operands.removeLast()
					var y = operands.removeLast()
					var op = operators.removeLast()
					operands.add(evaluate(x, y, op))
				}
			} else if ( e == "(") {
				operators.add(e)
			} else if ( isOperator(e) && operators.size == 0){
				operators.add(e)
			} else if (isOperator(e)){
				while (operators.lastOrNull() != null && operators.last() != "("){
					var x = operands.removeLast()
					var y = operands.removeLast()
					var op = operators.removeLast()
					operands.add(evaluate(x, y, op))
				}
				operators.add(e)
			} else if ( e == ")"){
				while (operators.last() != "("){
					var x = operands.removeLast()
					var y = operands.removeLast()
					var op = operators.removeLast()
					operands.add(evaluate(x, y, op))
				}
				operators.removeLast()
			}
		}
		while (operators.size > 0){
			var x = operands.removeLast()
			var y = operands.removeLast()
			var op = operators.removeLast()
			operands.add(evaluate(x, y, op))
		}
		total += operands[0].toLong()
	}
	println(total)

	var precedence = mutableListOf("*", "+")
	total = 0.toLong()

	for (line in input){
		var operands = mutableListOf<Long>()
		var operators = mutableListOf<String>()
		var elements = line.replace(")", " ) ").replace("(", " ( ").replace("  ", " ").split(" ")
		for (e in elements){
			if (e == ""){ continue }
			var characterPending = true;
			while (characterPending){
				characterPending = false;
				if (e.toLongOrNull() != null){
					operands.add(e.toLong())
				} else if ( e == "(") {
					operators.add(e)
				} else if ( isOperator(e) && operators.size == 0){
					operators.add(e)
				} else if ( isOperator(e) && operators.size > 0 && precedence.indexOf(operators.last()) < precedence.indexOf(e)){
					operators.add(e)
				} else if ( e == ")"){
					while (operators.last() != "("){
						var x = operands.removeLast()
						var y = operands.removeLast()
						var op = operators.removeLast()
						operands.add(evaluate(x, y, op))
					}
					operators.removeLast()
				} else {
					characterPending = true;
					var x = operands.removeLast()
					var y = operands.removeLast()
					var op = operators.removeLast()
					operands.add(evaluate(x, y, op))
				}
			}
		}
		while (operators.size > 0){
			var x = operands.removeLast()
			var y = operands.removeLast()
			var op = operators.removeLast()
			operands.add(evaluate(x, y, op))
		}
		total += operands[0].toLong()
	}
	println(total)
}