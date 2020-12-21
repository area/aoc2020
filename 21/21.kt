import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val foods = readFileAsLinesUsingReadLines("./input.txt")


fun main() {
	var allergenFreeSet = mutableSetOf<String>();
	var allergenMap = mutableMapOf<String, MutableSet<String>>()
	for (food in foods){
		var (ing, all) = food.split(" (contains ")
		var ingredients = ing.split(" ")
		ingredients.map{allergenFreeSet.add(it)}
		var allergens = all.dropLast(1).split(", ")
		for (allergen in allergens){
			var eliminated = mutableSetOf<String>()
			if (allergenMap[allergen] == null){
				allergenMap[allergen] = mutableSetOf()
				ingredients.map{allergenMap[allergen]!!.add(it)}
			} else {
				for (i in allergenMap[allergen]!!){
					if (ingredients.indexOf(i) == -1){
						eliminated.add(i)
					}
				}
			}
			eliminated.map{allergenMap[allergen]!!.remove(it)}
		}
	}

	var reduceable = true
	while (reduceable){
		reduceable = false;
		for ((allergen, set) in allergenMap){
			if (set.size == 1){
				allergenFreeSet.remove(set.first())
				for ((a, otherSet) in allergenMap){
					if (set != otherSet && otherSet.contains(set.first())){
						otherSet.remove(set.first())
						if (otherSet.size == 1){
							reduceable = true
						}
					}
				}
			}
		}
	}

	var allergyFreeCount = 0
	for (food in foods){
		var (ing,) = food.split(" (contains ")
		var ingredients = ing.split(" ")
		for (ingredient in ingredients){
			if(allergenFreeSet.contains(ingredient)){
				allergyFreeCount+=1
			}
		}
	}
	println(allergyFreeCount)
	var canonicalList = ""
	for ((allergen, ingredient) in allergenMap.toSortedMap()){
		canonicalList += ingredient.first() + ","
	}
	println(canonicalList.dropLast(1))
}