import java.io.File

fun readFileAsLinesUsingReadLines(fileName: String): List<String>
  = File(fileName).readLines()

val rows = readFileAsLinesUsingReadLines("./input.txt")

fun validPassport(passport: String): Boolean {
	return passport.contains("byr") &&
		passport.contains("iyr") &&
		passport.contains("eyr") &&
		passport.contains("hgt") &&
		passport.contains("hcl") &&
		passport.contains("ecl") &&
		passport.contains("pid") //&&
		//passport.contains("cid")
}

fun getFieldValue(passport: String, field: String): String {
	var pos = passport.indexOf(field);
	var subsequentSpacePos = passport.indexOf(" ", pos);
	if (subsequentSpacePos == -1) {
		subsequentSpacePos = passport.length
	}
	return passport.subSequence(pos+4, subsequentSpacePos).toString();
}

fun strictValidPassport(passport: String): Boolean {
	if (!validPassport(passport)) {
		return false
	}
	var byr = getFieldValue(passport, "byr").toInt()
	if (byr < 1920 || byr > 2002) { return false }

	var iyr = getFieldValue(passport, "iyr").toInt()
	if (iyr < 2010 || iyr > 2020) { return false }

	var eyr = getFieldValue(passport, "eyr").toInt()
	if (eyr < 2020 || eyr > 2030) { return false }

	var hgt = getFieldValue(passport, "hgt")
	if (Regex("[0-9]*cm").matches(hgt)) {
		var value = hgt.dropLast(2).toInt()
		if (value < 150 || value > 193 ) {
			return false
		}
	} else if (Regex("[0-9]*in").matches(hgt)) {
		var value = hgt.dropLast(2).toInt()
		if (value < 59 || value > 76 ) {
			return false
		}
	} else {
		return false
	}

	var hcl = getFieldValue(passport, "hcl")
	if (!Regex("#[0-9a-f]{6}").matches(hcl)){
		return false
	}

	var ecl = getFieldValue(passport, "ecl")
	if (!(ecl in listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth"))){
		return false
	}

	var pid = getFieldValue(passport, "pid")
	if (!Regex("[0-9]{9}").matches(pid)){
		return false
	}
	return true
}


fun main(args: Array<String>) {
	var passports = mutableListOf<String>()
	var passport = ""
	for (line in rows) {
		if (line == ""){
			passports.add(passport)
			passport = ""
		} else {
			passport += " " + line
		}
	}
	// Last passport doesn't have a blank line at the end
	passports.add(passport)
	var validCount = 0
	for (passport in passports){
		if (validPassport(passport)){
			validCount += 1
		}
	}
	println(validCount)

	validCount = 0
	for (passport in passports){
		if (strictValidPassport(passport)){
			validCount += 1
		}
	}
	println(validCount)
}