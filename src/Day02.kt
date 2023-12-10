import javax.xml.stream.events.Characters

fun main() {

    val RED = 12
    val GREEN = 13
    val BLUE = 14

    fun isPossible(blue: Int, red: Int, green: Int): Boolean {
        return blue <= BLUE && red <= RED && green <= GREEN
    }

    val colors = arrayOf("red", "green", "blue")

    val input = readInput("data")

    fun checkAndGetIdIfPossible(line: String): Int {
        var trimmedLine = line.substring(5)
        var id = ""
        var i = 0
        while (i < trimmedLine.length) {
            var character = trimmedLine[i]
            if (character == ':') break
            id += character
            ++i
        }

        var gamesOnlyString = trimmedLine.substring(i + 2)
        var j = 0
        var blueScore = 0
        var redScore = 0
        var greenScore = 0
        var number = ""
        while (j < gamesOnlyString.length) {
            if (gamesOnlyString[j] == ';') {
                blueScore = 0
                redScore = 0
                greenScore = 0
            }
            if (Character.isDigit(gamesOnlyString[j])) {
                number += gamesOnlyString[j]
            }
            for (index in 0 until colors.size) {
                if (colors[index].length + j < gamesOnlyString.length && colors[index] == gamesOnlyString.substring(
                        j,
                        colors[index].length + j
                    )
                ) {
                    if (colors[index] == "red") {
                        redScore += number.toInt()
                        number = ""
                    }
                    if (colors[index] == "green") {
                        greenScore += number.toInt()
                        number = ""
                    }
                    if (colors[index] == "blue") {
                        blueScore += number.toInt()
                        number = ""
                    }
                    j += colors[index].length - 1
                    break
                }
            }
            if (!isPossible(blueScore, redScore, greenScore)) return 0
            ++j
        }
        return id.toInt()
    }

    fun checkAndGetPower(line: String): Int {
        var trimmedLine = line.substring(line.indexOf(':') + 2, line.length)
        var j = 0
        var blueScore = 0
        var redScore = 0
        var greenScore = 0
        var temp_blueScore = 0
        var temp_redScore = 0
        var temp_greenScore = 0
        var number = ""
        while (j < trimmedLine.length) {
            blueScore = if (temp_blueScore > blueScore) temp_blueScore else blueScore
            redScore = if (temp_redScore > redScore) temp_redScore else redScore
            greenScore = if (temp_greenScore > greenScore) temp_greenScore else greenScore
            if (Character.isDigit(trimmedLine[j])) {
                number += trimmedLine[j]
            }
            for (index in 0 until colors.size) {
                if (colors[index].length + j <= trimmedLine.length && colors[index] == trimmedLine.substring(
                        j,
                        colors[index].length + j
                    )
                ) {
                    if (colors[index] == "red") {
                        temp_redScore = number.toInt()
                    }
                    if (colors[index] == "green") {
                        temp_greenScore = number.toInt()
                    }
                    if (colors[index] == "blue") {
                        temp_blueScore = number.toInt()
                    }
                    number = ""
//                    j += colors[index].length - 1
                    break
                }
            }
            j++
        }
        return blueScore * redScore * greenScore
    }

    fun p1(): Int {
        var sum = 0;
        for (line in input) {
            sum += checkAndGetIdIfPossible(line)
        }
        return sum
    }

    fun p2(): Int {
        var sum = 0;
        for (line in input) {
            sum += checkAndGetPower(line)
        }
        return sum
    }

//    val p1_result = p1()
    val p2_result = p2()
//    println(p1_result)
    println(p2_result)
}