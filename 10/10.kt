import java.io.File
import kotlin.math.pow

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

fun validArrangement(sequence: List<Int>): Boolean{
	for (n in sequence.indices){
		if (n==0){continue}
		if (sequence[n] - sequence[n-1] > 3){return false}
	}
	return true;
}

fun candidateArrangement(sequence: List<Int>, mask: Int): List<Int>{
	// Binary mask to remove some number of the middle elements

	var arrangement: MutableList<Int> = mutableListOf()
	for (n in 0..sequence.size-1){
		if (n == 0 || n == sequence.size-1){arrangement.add(sequence[n])}
		else if (mask and 2.toDouble().pow(n-1).toInt() == 0 ){arrangement.add(sequence[n])}
	}
	return arrangement
}

fun nValidArrangements(sequence: List<Int>): Int{
	var nValid = 1;
	// For each of these subsequences, the first and last elements must be present,
	// but we might be able to remove some others.
	for (n in 1..2.toDouble().pow(sequence.size-2).toInt()-1){
		if (validArrangement(candidateArrangement(sequence, n))){
			nValid += 1
		}
	}
	return nValid
}

fun main() {
	val numbersAsStrings = readFileAsLinesUsingReadLines("./input.txt")
	var numbers: MutableList<Int> = numbersAsStrings.map{ it.toInt() }.toMutableList()
	numbers.add(0) // The outlet
	numbers = numbers.sorted().toMutableList()

	var diff1Count = 0
	var diff3Count = 1 // Counting the device

	for (n in numbers.indices){
		if (n==0){continue}
		if (numbers[n] - numbers[n-1] == 1){diff1Count+=1}
		if (numbers[n] - numbers[n-1] == 3){diff3Count+=1}
	}
	println(diff1Count * diff3Count)
	println("**")
	// We start with the list that has every adapter in it.
	// Every jump of 3 must be in every valid sequence.
	// Split in to subsequences on those breaks
	var subsequences: MutableList<List<Int>> = mutableListOf()
	var nextStart = 0
	for (n in numbers.indices){
		if (n==0){continue}
		if (numbers[n] - numbers[n-1] == 3){
			subsequences.add(numbers.slice(nextStart..n-1))
			nextStart = n
		}
	}
	// Add the last subsequence
	subsequences.add(numbers.slice(nextStart..numbers.size-1))

	// How many sub-subsequences of each subsequence are valid? Multiply those numbers all together
	var combinations: Long = 1;
	for (s in subsequences){
		combinations *= nValidArrangements(s)
	}
	println(combinations)
}