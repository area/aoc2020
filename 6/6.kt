import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val rows = readFileAsLinesUsingReadLines("./input.txt")


fun main(args: Array<String>) {
	var count = 0;
	var group = "";
	for (line in rows) {
		if (line == ""){ // group finished
			var nQuestions = group.split("").drop(1).dropLast(1).distinct().count();
			count += nQuestions
			group = ""
		} else {
			group += line;
		}
	}
	// Again, no newline for last group
	var nQuestions = group.split("").drop(1).dropLast(1).distinct().count();
	count += nQuestions

	println(count)
	count = 0
	var wholeGroupAnswered = mutableListOf<String>()
	var processingGroup = false
 	for (line in rows) {
		if (line == ""){ // group finished
			count += wholeGroupAnswered.count()
			processingGroup = false;
			wholeGroupAnswered = mutableListOf<String>()
		} else if (!processingGroup) {
			processingGroup = true;
			line.split("").drop(1).dropLast(1).map{wholeGroupAnswered.add(it)}
		} else {
			var personAnswered = mutableListOf<String>()
			line.split("").drop(1).dropLast(1).map{personAnswered.add(it)}
			var questionsMissed = mutableListOf<String>()
			for (q in wholeGroupAnswered){
				if (!personAnswered.contains(q)){
					questionsMissed.add(q)
				}
			}
			for (q in questionsMissed){
				wholeGroupAnswered.remove(q)
			}
		}
	}
	// Again no newline for last group
	count += wholeGroupAnswered.count()

	println(count)

}