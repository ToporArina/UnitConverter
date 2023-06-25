package converter

import kotlin.system.exitProcess


var userInput = ""

enum class Types {
    LENGTH, WEIGHT, TEMPERATURE
}

enum class Units(
    val type: Types,
    val from: (Double) -> Double,
    val to: (Double) -> Double,
    val list: List<String>
) {
    METER(Types.LENGTH, { it }, { it }, listOf("m", "meter", "meters")),
    KILOMETER(Types.LENGTH, { it * 1000 }, { it / 1000 }, listOf("km", "kilometer", "kilometers")),
    MILLIMETER(Types.LENGTH, { it * 0.001 }, { it / 0.001 }, listOf("mm", "millimeter", "millimeters")),
    CENTIMETER(Types.LENGTH, { it * 0.01 }, { it / 0.01 }, listOf("cm", "centimeter", "centimeters")),
    MILE(Types.LENGTH, { it * 1609.35 }, { it / 1609.35 }, listOf("mi", "mile", "miles")),
    YARD(Types.LENGTH, { it * 0.9144 }, { it / 0.9144 }, listOf("yd", "yard", "yards")),
    FOOT(Types.LENGTH, { it * 0.3048 }, { it / 0.3048 }, listOf("ft", "foot", "feet")),
    INCH(Types.LENGTH, { it * 0.0254 }, { it / 0.0254 }, listOf("in", "inch", "inches")),
    GRAM(Types.WEIGHT, { it }, { it }, listOf("g", "gram", "grams")),
    KILOGRAM(Types.WEIGHT, { it * 1000.0 }, { it / 1000.0 }, listOf("kg", "kilogram", "kilograms")),
    MILLIGRAMS(Types.WEIGHT, { it * 0.001 }, { it / 0.001 }, listOf("mg", "milligram", "milligrams")),
    POUNDS(Types.WEIGHT, { it * 453.592 }, { it / 453.592 }, listOf("lb", "pound", "pounds")),
    OUNCES(Types.WEIGHT, { it * 28.3495 }, { it / 28.3495 }, listOf("oz", "ounce", "ounces")),
    CELSIUS(Types.TEMPERATURE, { it }, { it }, listOf("c", "degree Celsius", "degrees Celsius", "dc", "celsius")),
    FAHRENHEIT(
        Types.TEMPERATURE,
        { (it - 32.0) * (5.0 / 9) },
        { (it * (9.0 / 5) + 32.0) },
        listOf("f", "degree Fahrenheit", "degrees Fahrenheit", "df", "fahrenheit")
    ),
    KELVIN(Types.TEMPERATURE, { it - 273.15 }, { it + 273.15 }, listOf("k", "kelvin", "kelvins"))
}

fun main() {
    while (true) {
        print("Enter what you want to convert (or exit): ")
        userInput = readln()
        if (userInput == "exit") {
            exitProcess(0)
        }
        var inp = listOf<String>(" ", " ", " ", " ")
        try {
            splitUserInput(userInput)[0].toDouble()
        } catch (e: Exception) {
            println("Parse error")
            println()
            continue
        }
        inp = splitUserInput(userInput)
        val from = inp[1]
        val to = inp[3]
        if (findUnitByValue(from)?.type == findUnitByValue(to)?.type && Types.values()
                .contains(findUnitByValue(from)?.type)
        ) {
            if (inp[0].toDouble() < 0 && findUnitByValue(from)?.type != Types.TEMPERATURE) {
                if (findUnitByValue(from)?.type == Types.WEIGHT) {
                    println("Weight shouldn't be negative")
                    continue
                }
                if (findUnitByValue(from)?.type == Types.LENGTH) {
                    println("Length shouldn't be negative.")
                    continue
                }
            }
            println(
                "${inp[0].toDouble()} ${
                    if (inp[0].toDouble() == 1.0) findUnitByValue(from)?.list?.get(1) else findUnitByValue(
                        from
                    )?.list?.get(2)
                } is ${calc(inp[0], findUnitByValue(from)!!.from, findUnitByValue(to)!!.to)} ${
                    if (calc(inp[0], findUnitByValue(from)!!.from, findUnitByValue(to)!!.to) == 1.0) {
                        findUnitByValue(to)?.list?.get(1)
                    } else {
                        findUnitByValue(to)?.list?.get(2)
                    }
                }"
            )
        } else {
            println(
                "Conversion from ${
                    if (findUnitByValue(from)?.list?.get(2) == null) "???" else findUnitByValue(from)?.list?.get(
                        2
                    )
                } to ${
                    if (findUnitByValue(
                            to
                        )?.list?.get(2) == null
                    ) "???" else findUnitByValue(to)?.list?.get(2)
                } is impossible "
            )
        }
        println()
    }
}

fun calc(value: String, from: (Double) -> Double, to: (Double) -> Double): Double {
    return to(from(value.toDouble()))
}

fun splitUserInput(inp: String): List<String> {
    val splt = mutableListOf<String>()
    val splitByToOrIn = inp.split("( to | in | convertTo )".toRegex())
    val firstPart = splitByToOrIn[0].trim().split(" ")
    val secPart = splitByToOrIn[1].trim().split(" ")
    if (firstPart.size == 3) {
        splt.add(firstPart[0])
        splt.add("${firstPart[1]} ${firstPart[2]}")
    } else {
        splt.addAll(firstPart)
    }
    splt.add("to")
    if (secPart.size == 2) {
        splt.add("${secPart[0]} ${secPart[1]}")
    } else {
        splt.addAll(secPart)
    }
    return splt
}

fun findUnitByValue(value: String): Units? {
    return Units.values().find { unit ->
        unit.list.map { it.lowercase() }.contains(value.lowercase())
    }
}