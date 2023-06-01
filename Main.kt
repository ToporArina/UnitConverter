package converter



fun main() {
    print("Enter a number and a measure: ")
    val inp = readLine()?.split(" ")?: return
    when (inp[1].lowercase()) {
        "km", "kilometer", "kilometers" -> {
            println("${inp[0]} kilometer" + (if (inp[0].toInt()!=1) "s" else "") + " is ${inp[0].toInt()*1000} meters")
        }
        else -> println("Wrong input")
    }
}
