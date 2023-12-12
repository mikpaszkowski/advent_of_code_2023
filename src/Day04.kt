
data class Card(val winningNumber: List<Int>, val outNumbers: List<Int>)
fun main() {
    var input = readInput("data-04")

    input.map { line ->
        var (_, winningNumbersAsTest, userNumbersAsTest) = line.split(":", "|")
        val winningNumbers = winningNumbersAsTest.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val userNumbers = userNumbersAsTest.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        winningNumbers to userNumbers
    }.map { (winningNumbers, userNumbers) ->
        val count = winningNumbers.count { it in userNumbers }
        if (count == 0) 0
        else 1.shl(count - 1)
    }
        .onEach(::println)
        .sum()
        .also(::println)

    input.map { line ->
        var (_, winningNumbersAsTest, userNumbersAsTest) = line.split(":", "|")
        val winningNumbers = winningNumbersAsTest.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val userNumbers = userNumbersAsTest.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val count = winningNumbers.count { it in userNumbers }
        Card(winningNumbers, userNumbers) to count
    }
        .let { pair ->
            val cardCounter = MutableList(pair.size) { 1 }
            pair.mapIndexed { index, (_, count) ->
                (1..count).forEach {
                    cardCounter[index + it] += cardCounter[index]
                }
            }
            cardCounter
        }
         .onEach(::println)
        .sum()
        .also(::println)
}