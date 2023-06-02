package converter

fun main() {

    print("Enter a number and a measure of length: ")
    val inp = readLine()?.split(" ") ?: return
    try {
        println(
            "${inp[0].toDouble()} ${
                convertToMeters(
                    inp[0].toDouble(),
                    inp[1]
                ).second
            } is ${
                convertToMeters(
                    inp[0].toDouble(),
                    inp[1]
                ).first
            } ${convertToMeters(convertToMeters(inp[0].toDouble(), inp[1]).first!!, "meter").second}"
        )
    } catch (e: Exception) {
        println("Wrong input. Unknown unit ${inp[1]}")
    }
}

fun convertToMeters(value: Double, unit: String): Pair<Double?, String?> {
    val lowerCaseUnit = unit.toLowerCase()
    return when (lowerCaseUnit) {
        "m", "meter", "meters" -> value to if (value == 1.0) "meter" else "meters"
        "km", "kilometer", "kilometers" -> value * 1000 to if (value == 1.0) "kilometer" else "kilometers"
        "cm", "centimeter", "centimeters" -> value * 0.01 to if (value == 1.0) "centimeter" else "centimeters"
        "mm", "millimeter", "millimeters" -> value * 0.001 to if (value == 1.0) "millimeter" else "millimeters"
        "mi", "mile", "miles" -> value * 1609.35 to if (value == 1.0) "mile" else "miles"
        "yd", "yard", "yards" -> value * 0.9144 to if (value == 1.0) "yard" else "yards"
        "ft", "foot", "feet" -> value * 0.3048 to if (value == 1.0) "foot" else "feet"
        "in", "inch", "inches" -> value * 0.0254 to if (value == 1.0) "inch" else "inches"
        else -> null to null
    }
}
