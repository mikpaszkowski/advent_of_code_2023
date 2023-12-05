fun main() {
    fun getNumPs1(seq: String): Int {
        var num = ""
        for (char in seq) {
            if (Character.isDigit(char)) {
                num += char
            }
        }
        return 10 * (num[0] - '0') + (num[num.length - 1] - '0')
    }


    fun part1(input: List<String>): Int {
        var sum = 0
        for (seq in input) {
            sum += getNumPs1(seq)
        }
        return sum
    }

    val numbers = arrayOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

    fun getNumPs2(seq: String): Int {
        var num = ""
        var i = 0
        while (i < seq.length) {
            for (index in 0 until numbers.size) {
                if (numbers[index].length + i <= seq.length && seq.substring(
                        i,
                        numbers[index].length + i
                    ) == numbers[index]
                ) {
                    num += ('1' + index).toString()
                    break
                }
            }
            if (Character.isDigit(seq[i])) {
                num += seq[i]
            }
            ++i
        }
        return (num[0] - '0') * 10 + (num[num.length - 1] - '0')
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (seq in input) {
            sum += getNumPs2(seq)
        }
        return sum
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
