fun main() {


    val input = readInput("data-03")
    val matrix = processInputToMatrix(input)

    val p1_result = p1(matrix)
    println(p1_result)
    var p2_result = p2(matrix)
    println(p2_result)
}

fun Array<Array<Char>>.isNumberAtRangeEngineSchematic(start: Int, end: Int, row: Int): Boolean {
    val directions = listOf(-1, 0, 1) // Represents movement in 8 directions
    val maxColumnIndex = this[row].size - 1

    for (i in start..end) {
        for (dx in directions) {
            for (dy in directions) {
                if (dx == 0 && dy == 0) continue // Skip the center cell itself

                val newX = i + dx
                val newY = row + dy

                // Check boundaries and neighboring character
                if (newX in 0..maxColumnIndex && newY in this.indices &&
                    !this[newY][newX].isDigit() && this[newY][newX] != '.'
                ) {
                    return true
                }
            }
        }
    }
    return false
}

fun Array<Array<Char>>.getPositionOfGearSymbol(start: Int, end: Int, row: Int): Pair<Int, Int> {
    val directions = listOf(-1, 0, 1) // Represents movement in 8 directions
    val maxColumnIndex = this[row].size - 1

    for (i in start..end) {
        for (dx in directions) {
            for (dy in directions) {
                if (dx == 0 && dy == 0) continue // Skip the center cell itself

                val newX = i + dx
                val newY = row + dy

                // Check boundaries and neighboring character
                if (newX in 0..maxColumnIndex && newY in this.indices &&
                    !this[newY][newX].isDigit() && this[newY][newX] != '.'
                ) {
                    return Pair(newX, newY)
                }
            }
        }
    }
    return Pair(-1, -1)
}

fun p1(matrix: Array<Array<Char>>): Int {
    var engineSchematicNumSum = 0

    for (i in matrix.indices) {
        var numberIndexRange: Pair<Int, Int> = Pair(-1, -1)
        for (j in matrix[i].indices) {
            if (Character.isDigit(matrix[i][j])) {
                numberIndexRange = Pair(if (numberIndexRange.first != -1) numberIndexRange.first else j, j)
            }
            if (!Character.isDigit(matrix[i][j]) || j == matrix[i].size - 1) {
                if (numberIndexRange.first != -1 && matrix.isNumberAtRangeEngineSchematic(
                        numberIndexRange.first,
                        numberIndexRange.second,
                        i
                    )
                ) {
                    engineSchematicNumSum += matrix.concatRangeInRowToInt(
                        i,
                        numberIndexRange.first,
                        numberIndexRange.second
                    )
                }
                numberIndexRange = Pair(-1, -1)
            }
        }
    }
    return engineSchematicNumSum
}

fun p2(matrix: Array<Array<Char>>): Int {

    val numberAndGearSymbolPositionPairs: MutableList<Pair<Int, Pair<Int, Int>>> = mutableListOf()

    for (i in matrix.indices) {
        var numberIndexRange: Pair<Int, Int> = Pair(-1, -1)
        for (j in matrix[i].indices) {
            if (Character.isDigit(matrix[i][j])) {
                numberIndexRange = Pair(if (numberIndexRange.first != -1) numberIndexRange.first else j, j)
            }
            if (!Character.isDigit(matrix[i][j]) || j == matrix[i].size - 1) {
                val positionOfGearSymbol =
                    matrix.getPositionOfGearSymbol(numberIndexRange.first, numberIndexRange.second, i)
                if (numberIndexRange.first != -1 && positionOfGearSymbol.first != -1) {
                    val gearNumber = matrix.concatRangeInRowToInt(i, numberIndexRange.first, numberIndexRange.second)
                    numberAndGearSymbolPositionPairs.add(Pair(gearNumber, positionOfGearSymbol))
                }
                numberIndexRange = Pair(-1, -1)
            }
        }
    }
    return numberAndGearSymbolPositionPairs.groupBy({ it.second }, { it.first })
        .filter { it.value.size >= 2 }
        .mapValues { (_, values) -> values.reduce { acc, i -> acc * i } }
        .values.sum()
}

fun processInputToMatrix(lines: List<String>): Array<Array<Char>> {
    return lines.map { it.toCharArray().toTypedArray() }.toTypedArray()
}

fun Array<Array<Char>>.concatRangeInRowToInt(rowIndex: Int, startIndex: Int, endIndex: Int): Int {
    val stringBuilder = StringBuilder()

    for (i in startIndex..endIndex) {
        if (i in this[rowIndex].indices) {
            stringBuilder.append(this[rowIndex][i])
        }
    }

    return stringBuilder.toString().toInt()
}