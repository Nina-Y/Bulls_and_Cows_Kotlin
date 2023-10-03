package bullscows

fun main() {
    println("Input the length of the secret code:")
    val len = readln()
    try {
        len.toInt()
    } catch (e: NumberFormatException) {
        println("Error: \"$len\" isn't a valid number.")
        return
    }
    val length = len.toInt()
    println("Input the number of possible symbols in the code:")
    val posSym = readln().toInt()
    if (length == 0) println("Error: the length should be > 0")
    else if (posSym < length) println("Error: it's not possible to generate a code with a length of $length with $posSym unique symbols.")
    else if (posSym > 36) println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).")
    else playGame(length, posSym)
}

fun playGame(length: Int, posSym: Int) {
    val code = generateCode(length, posSym)
    preparedSecret(length, posSym)
    println("Okay, let's start a game!")
    var turnCount = 1
    while (true) {
        println("Turn $turnCount:")
        val input = readln()
        var countB = 0
        var countC = 0
        for (i in input.indices) {
            if (input[i] == code[i]) countB++
            else if (input[i] in code && input[i] != code[i]) countC++
        }
        if (countB == length) {
            println("Grade: $countB bulls\nCongratulations! You guessed the secret code.")
            return
        }
        val none = if (countB == 0 && countC == 0) "None" else ""
        val bulls = if (countB > 0) "$countB bull(s)" else ""
        val cows = if (countC > 0) "$countC cow(s)" else ""
        val both = if (countB > 0 && countC > 0) " and " else ""
        println("Grade: $bulls$both$cows$none.")
        turnCount++
    }
}

fun generateCode(length: Int, posSym: Int): String {
    val digits = ('0'..'9').toMutableList()
    val letters = if (posSym > 10) ('a'..'z').toMutableList().subList(0, posSym - 10) else emptyList()
    val code = (digits + letters).shuffled()
    return code.subList(0, length).joinToString("")
}

fun preparedSecret(length: Int, posSym: Int) {
    print("The secret is prepared: ")
    for (i in 0 until length) { print('*') }
    if (posSym <= 10) println(" (0-9).") else println(" (0-9, a-${'a' + (posSym - 11)}).")
}
