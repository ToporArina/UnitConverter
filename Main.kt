package converter

import kotlin.system.exitProcess


var userInput = ""

enum class Types {
    LENGTH, WEIGHT
}

enum class Units(val type: Types, val unit: Double, val short: String, val single: String, val multi: String) {
    METER(Types.LENGTH, 1.0, "m", "meter", "meters"),
    KILOMETER(Types.LENGTH, 1000.00, "km", "kilometer", "kilometers"),
    MILLIMETER(Types.LENGTH, 0.001, "mm", "millimeter", "millimeters"),
    CENTIMETER(Types.LENGTH, 0.01, "cm", "centimeter", "centimeters"),
    MILE(Types.LENGTH, 1609.35, "mi", "mile", "miles"),
    YARD(Types.LENGTH, 0.9144, "yd", "yard", "yards"),
    FOOT(Types.LENGTH, 0.3048, "ft", "foot", "feet"),
    INCH(Types.LENGTH, 0.0254, "in", "inch", "inches"),
    GRAM(Types.WEIGHT, 1.0, "g", "gram", "grams"),
    KILOGRAM(Types.WEIGHT, 1000.0, "kg", "kilogram", "kilograms"),
    MILLIGRAMS(Types.WEIGHT, 0.001, "mg", "milligram", "milligrams"),
    POUNDS(Types.WEIGHT, 453.592, "lb", "pound", "pounds"),
    OUNCES(Types.WEIGHT, 28.3495, "oz", "ounce", "ounces")
}

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        userInput = readln()
        if (userInput == "exit") {
            exitProcess(0)
        }
        val inp = userInput.lowercase().split(" ")
        val from = inp[1]
        val to = inp[3]
        if (findUnitByValue(from)?.type == findUnitByValue(to)?.type && Types.values().contains(findUnitByValue(from)?.type)) {
            println(
                "${inp[0].toDouble()} ${
                    if (inp[0].toDouble() == 1.0) findUnitByValue(from)?.single else findUnitByValue(
                        from
                    )?.multi
                } is ${inp[0].toDouble() * findUnitByValue(from)?.unit!! / findUnitByValue(to)?.unit!!} ${
                    if (inp[0].toDouble() * findUnitByValue(from)?.unit!! / findUnitByValue(to)?.unit!! == 1.0) {
                        findUnitByValue(to)?.single
                    } else {
                        findUnitByValue(to)?.multi
                    }
                }"
            )
        } else {
            println(
                "Conversion from ${if (findUnitByValue(from)?.multi == null) "???" else findUnitByValue(from)?.multi} to ${
                    if (findUnitByValue(
                            to
                        )?.multi == null
                    ) "???" else findUnitByValue(to)?.multi
                } is impossible "
            )
        }
        println()
    }
}

fun findUnitByValue(value: String): Units? {
    return Units.values().find { unit ->
        unit.short == value || unit.single == value || unit.multi == value
    }
}