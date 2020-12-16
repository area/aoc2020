import java.io.File
import kotlin.math.*

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val input = readFileAsLinesUsingReadLines("./input.txt")

fun main() {
	var restrictions = mutableMapOf<String, List<String>>()
	var validTickets = mutableListOf<String>()
	var myTicket = ""
	var nBlanks = 0
	var invalidTotal = 0
	for (l in input){
		if (l == ""){
			nBlanks += 1
			continue
		}
		if (nBlanks == 0){
			val (label, ranges) = l.split(": ", limit = 2)
			restrictions[label] = ranges.split(" or ")
		}
		if (nBlanks == 1){
			if (l == "your ticket:"){continue}
			myTicket = l
			validTickets.add(l)
		}
		if (nBlanks == 2){
			if (l == "nearby tickets:"){continue}
			val values = l.split(",").map{it.toInt()}
			var validTicket = true;
			for (v in values){
				var validField = false;
				for ((k, ranges) in restrictions){
					for (r in ranges){
						val (min, max) = r.split("-", limit = 2).map{it.toInt()}
						if (v >= min && v <= max){
							validField = true
							continue
						}
					}
				}
				if (!validField){
					invalidTotal += v
					validTicket = false;
				}
			}
			if (validTicket){
				validTickets.add(l)
			}
		}
	}
	println(invalidTotal)
	var fieldsToLocation = mutableMapOf<String, Int>().withDefault{-1}
	var locationToFields = mutableMapOf<Int, MutableSet<String>>()
	for (i in 0..restrictions.keys.size-1){
		locationToFields[i] = restrictions.keys.toMutableSet()
	}
	for (valid in validTickets){
		val values = valid.split(",").map({it.toInt()})
		for ((idx,v) in values.withIndex()){
			var invalidFields = mutableSetOf<String>()
			for (field in locationToFields[idx]!!) {
				var ranges = restrictions[field]
				var plausibleField = false
				for (r in ranges!!){
					val (min, max) = r.split("-", limit = 2).map{it.toInt()}
					if (v >= min && v <= max){
						plausibleField = true
						break
					}
				}
				if (!plausibleField){
					invalidFields.add(field)
				}
			}
			for (f in invalidFields){
				locationToFields[idx]!!.remove(f)
			}
		}
		var reduceable = true
		while (reduceable){
			reduceable = false;
			for ((location, set) in locationToFields){
				if (set.size ==1){
					if (fieldsToLocation.getValue(set.first()) == -1){
						// Then we've not eliminated it from other fields in the past
						for ((l, otherSet) in locationToFields){
							if (set != otherSet && otherSet.contains(set.first())){
								otherSet.remove(set.first())
								if (otherSet.size == 1){
									reduceable = true
								}
							}
						}
						fieldsToLocation[set.first()] = location
					}
				}
			}
		}
	}

	var product = 1.toLong()
	for ((field, location) in fieldsToLocation){
		if (field.length > 10 && field.slice(0..8) == "departure"){
			product *= myTicket.split(",")[location].toLong()
		}
	}
	println(product)
}