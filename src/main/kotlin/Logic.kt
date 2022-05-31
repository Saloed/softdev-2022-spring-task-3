import kotlin.math.round

class Logic {
    val coordinatesWhiteCheckers = listOf(
        "A1", "C1", "E1", "G1",
        "B2", "D2", "F2", "H2",
        "A3", "C3", "E3", "G3"
    )
    val coordinatesBlackCheckers = listOf(
        "B8", "D8", "F8", "H8",
        "A7", "C7", "E7", "G7",
        "B6", "D6", "F6", "H6"
    )


    fun cell(str: String): Pair<Double, Double> {
        var count = 0.0
        var count1 = 0.0
        when (str[0]) {
            'A' -> count = 0.0
            'B' -> count = 1.0
            'C' -> count = 2.0
            'D' -> count = 3.0
            'E' -> count = 4.0
            'F' -> count = 5.0
            'G' -> count = 6.0
            'H' -> count = 7.0
        }
        when (str[1]) {
            '1' -> count1 = 7.0
            '2' -> count1 = 6.0
            '3' -> count1 = 5.0
            '4' -> count1 = 4.0
            '5' -> count1 = 3.0
            '6' -> count1 = 2.0
            '7' -> count1 = 1.0
            '8' -> count1 = 0.0
        }
        val x = 30 + count * 110
        val y = 30 + count1 * 110
        return x + 8 to y + 8
    }

    private fun cord(count: Int, count1: Int): String {
        var char = ""
        var num = ""
        when (count) {
            0 -> char = "A"
            1 -> char = "B"
            2 -> char = "C"
            3 -> char = "D"
            4 -> char = "E"
            5 -> char = "F"
            6 -> char = "G"
            7 -> char = "H"
        }
        when (count1) {
            0 -> num = "8"
            1 -> num = "7"
            2 -> num = "6"
            3 -> num = "5"
            4 -> num = "4"
            5 -> num = "3"
            6 -> num = "2"
            7 -> num = "1"
        }
        return char + num
    }


    fun centreSearchX(offsetX: Double, offsetY: Double): Pair<Double, Double> {
        val count = round((offsetX - 30) / 110).toInt()
        val count1 = round((offsetY - 30) / 110).toInt()
        if (offsetX >= 850 || offsetY >= 850 || offsetX <= 0 || offsetY <= 0) {
            return offsetX to offsetY
        }
        val string = Logic().cord(count, count1)
        return Logic().cell(string).first to Logic().cell(string).second
    }


    fun stepsForCheckers1(
        initialCord: Pair<Double, Double>,
        white: Boolean,
        queen: Boolean
    ): List<Pair<Double, Double>> {
        var stepX1: Double
        var stepX2: Double
        var stepY: Double
        val list1 = mutableListOf<Pair<Double, Double>>()

        if (white && !queen) {
            if (initialCord.first + 110 <= 880 && initialCord.first - 110 >= 0.0 && initialCord.second >= 0.0) {
                stepX1 = initialCord.first + 110
                stepX2 = initialCord.first - 110
                stepY = initialCord.second - 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
                if (stepX2 to stepY !in list) list1.add(stepX2 to stepY)
            }
            if (initialCord.first + 110 <= 880 && initialCord.first - 110 <= 0.0 && initialCord.second >= 0.0) {
                stepX1 = initialCord.first + 110
                stepY = initialCord.second - 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
            }
            if (initialCord.first - 110 >= 0.0 && initialCord.first + 110 >= 880 && initialCord.second >= 0.0) {
                stepX1 = initialCord.first - 110
                stepY = initialCord.second - 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
            }
        }
        if (!white && !queen) {
            if (initialCord.first + 110 <= 880 && initialCord.first - 110 >= 0.0 && initialCord.second <= 880.0) {
                stepX1 = initialCord.first + 110
                stepX2 = initialCord.first - 110
                stepY = initialCord.second + 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
                if (stepX2 to stepY !in list) list1.add(stepX2 to stepY)
            }
            if (initialCord.first + 110 <= 880 && initialCord.first - 110 <= 0.0 && initialCord.second <= 880.0) {
                stepX1 = initialCord.first + 110
                stepY = initialCord.second + 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
            }
            if (initialCord.first + 110 >= 880 && initialCord.first - 110 >= 0.0 && initialCord.second <= 880.0) {
                stepX1 = initialCord.first - 110
                stepY = initialCord.second + 110
                if (stepX1 to stepY !in list) list1.add(stepX1 to stepY)
            }
        }
        return list1
    }


    private fun findDelete(index: Int) {
        list.removeAt(index)
        list.add(index, 0.0 to 0.0)
        delete.add((Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index])))
    }

    fun stepsForCheckers2(
        initialCord: Pair<Double, Double>,
        white: Boolean,
        x: Double,
        y: Double,
        queen: Boolean
    ): List<Pair<Double, Double>> {
        val list1 = mutableListOf<Pair<Double, Double>>()
        var stepX1: Double
        var stepY: Double

        if (white && !queen) {
            if (initialCord.first + 110 to initialCord.second - 110 in list) {
                stepX1 = initialCord.first + 220
                stepY = initialCord.second - 220
                val index = list.indexOf(initialCord.first + 110 to initialCord.second - 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(12, 24) && stepX1 <= 880.0 && stepY >= 0.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(12, 24) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first - 110 to initialCord.second - 110 in list) {
                stepX1 = initialCord.first - 220
                stepY = initialCord.second - 220
                val index = list.indexOf(initialCord.first - 110 to initialCord.second - 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(12, 24) && stepX1 >= 0.0 && stepY >= 0.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(12, 24) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first + 110 to initialCord.second + 110 in list) {
                stepX1 = initialCord.first + 220
                stepY = initialCord.second + 220
                val index = list.indexOf(initialCord.first + 110 to initialCord.second + 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(12, 24) && stepX1 <= 880.0 && stepY <= 880.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(12, 24) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first - 110 to initialCord.second + 110 in list) {
                stepX1 = initialCord.first - 220
                stepY = initialCord.second + 220
                val index = list.indexOf(initialCord.first - 110 to initialCord.second + 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(12, 24) && stepX1 >= 0.0 && stepY <= 880.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(12, 24) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
        }
        if (!white && !queen) {
            if (initialCord.first + 110 to initialCord.second - 110 in list) {
                stepX1 = initialCord.first + 220
                stepY = initialCord.second - 220
                val index = list.indexOf(initialCord.first + 110 to initialCord.second - 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(0, 12) && stepX1 <= 880.0 && stepY >= 0.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(0, 12) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first - 110 to initialCord.second - 110 in list) {
                stepX1 = initialCord.first - 220
                stepY = initialCord.second - 220
                val index = list.indexOf(initialCord.first - 110 to initialCord.second - 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(0, 12) && stepX1 >= 0.0 && stepY >= 0.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(0, 12) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first + 110 to initialCord.second + 110 in list) {
                stepX1 = initialCord.first + 220
                stepY = initialCord.second + 220
                val index = list.indexOf(initialCord.first + 110 to initialCord.second + 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(0, 12) && stepX1 <= 880.0 && stepY <= 880.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(0, 12) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
            if (initialCord.first - 110 to initialCord.second + 110 in list) {
                stepX1 = initialCord.first - 220
                stepY = initialCord.second + 220
                val index = list.indexOf(initialCord.first - 110 to initialCord.second + 110)
                if (stepX1 to stepY !in list && list[index] in list.subList(0, 12) && stepX1 >= 0.0 && stepY <= 880.0) list1.add(stepX1 to stepY)
                if (x to y == stepX1 to stepY && list[index] in list.subList(0, 12) && stepX1 to stepY !in list) {
                    findDelete(index)
                }
            }
        }
        return list1
    }


    fun queenStep1(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (white) {
            while (stepX1 - 110 >= 0 && stepY - 110 >= 0) {
                stepX1 -= 110
                stepY -= 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1
            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(12, 24)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 38.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(12, 24)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(12, 24)) {
                                if (x to y in listResult && x < listCord[count].first && y < listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x < listCord[count].first && y < listCord[count].second && list[index] in list.subList(12, 24)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }


    fun queenStep2(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (white) {
            while (stepX1 + 110 <= 880.0 && stepY - 110 >= 0) {
                stepX1 += 110
                stepY -= 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1
            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(12, 24)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 808.0 || listCord[count].second == 38.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(12, 24)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(12, 24)) {
                                if (x to y in listResult && x > listCord[count].first && y < listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x > listCord[count].first && y < listCord[count].second && list[index] in list.subList(12, 24)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep3(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (white) {
            while (stepX1 - 110 >= 0.0 && stepY + 110 <= 880.0) {
                stepX1 -= 110
                stepY += 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1

            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(12, 24)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 808.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(12, 24)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(12, 24)) {
                                if (x to y in listResult && x < listCord[count].first && y > listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x < listCord[count].first && y > listCord[count].second && list[index] in list.subList(12, 24)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep4(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (white) {
            while (stepX1 + 110 <= 880.0 && stepY + 110 <= 880.0) {
                stepX1 += 110
                stepY += 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1

            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(12, 24)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 808.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(12, 24)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(12, 24)) {
                                if (x to y in listResult && x > listCord[count].first && y > listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x > listCord[count].first && y > listCord[count].second && list[index] in list.subList(12, 24)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep5(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (!white) {
            while (stepX1 - 110 >= 0 && stepY - 110 >= 0) {
                stepX1 -= 110
                stepY -= 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1
            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(0, 12)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 38.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(0, 12)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(0, 12)) {
                                if (x to y in listResult && x < listCord[count].first && y < listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x < listCord[count].first && y < listCord[count].second && list[index] in list.subList(0, 12)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep6(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (!white) {
            while (stepX1 + 110 <= 880.0 && stepY - 110 >= 0) {
                stepX1 += 110
                stepY -= 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1
            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(0, 12)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 808.0 || listCord[count].second == 38.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(0, 12)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(0, 12)) {
                                if (x to y in listResult && x > listCord[count].first && y < listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x > listCord[count].first && y < listCord[count].second && list[index] in list.subList(0, 12)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep7(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (!white) {
            while (stepX1 - 110 >= 0.0 && stepY + 110 <= 880.0) {
                stepX1 -= 110
                stepY += 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1

            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(0, 12)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 808.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(0, 12)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(0, 12)) {
                                if (x to y in listResult && x < listCord[count].first && y > listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x < listCord[count].first && y > listCord[count].second && list[index] in list.subList(0, 12)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun queenStep8(initialCord: Pair<Double, Double>, white: Boolean, x: Double, y: Double): Set<Pair<Double, Double>> {

        val listResult = mutableSetOf<Pair<Double, Double>>()
        val listCord = mutableListOf<Pair<Double, Double>>()
        val listAnnotation = mutableListOf<String>()
        var stepX1 = initialCord.first
        var stepY = initialCord.second

        if (!white) {
            while (stepX1 + 110 <= 880.0 && stepY + 110 <= 880.0) {
                stepX1 += 110
                stepY += 110
                if (stepX1 to stepY in list) {
                    listAnnotation.add("З")
                    listCord.add(stepX1 to stepY)
                } else {
                    listAnnotation.add("С")
                    listCord.add(stepX1 to stepY)
                }
            }

            var count = -1

            for (element in listCord) {
                count++
                var index = list.indexOf(listCord[count])
                if (listAnnotation[count] == "С") {
                    listResult.add(element)
                } else if (listAnnotation[count] == "З" && list[index] !in list.subList(0, 12)) return listResult

                if (count + 1 <= listAnnotation.size - 1) {
                    if (listAnnotation[count] == "З" && listCord[count].first == 38.0 || listCord[count].second == 808.0) return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "З") return listResult

                    if (listAnnotation[count] == "З" && listAnnotation[count + 1] == "С") {
                        val newCount = count + 1
                        index = list.indexOf(listCord[count])
                        listResult.clear()
                        for (space in newCount until listCord.size) {
                            if (listAnnotation[space] == "С" && list[index] in list.subList(0, 12)) {
                                listResult.add(listCord[space])
                            } else if (listAnnotation[space] == "З" && list[index] in list.subList(0, 12)) {
                                if (x to y in listResult && x > listCord[count].first && y > listCord[count].second) {
                                    findDelete(index)
                                }
                                return listResult
                            }
                        }
                        if (x to y in listResult && x > listCord[count].first && y > listCord[count].second && list[index] in list.subList(0, 12)) {
                            findDelete(index)
                        }
                    }
                }
            }
        }
        return listResult
    }

    fun checkActiveQueen(initialCord: Pair<Double, Double>, futureSteps: Set<Pair<Double, Double>>, direction: Int): Boolean {
        if (direction == 1 && futureSteps.first().first - initialCord.first > 110 && futureSteps.first().second > initialCord.second) return true
        if (direction == 2 && futureSteps.first().first - initialCord.first < -110 && futureSteps.first().second > initialCord.second) return true
        if (direction == 3 && futureSteps.first().first - initialCord.first > 110 && futureSteps.first().second < initialCord.second) return true
        if (direction == 4 && futureSteps.first().first - initialCord.first < -110  && futureSteps.first().second < initialCord.second) return true

        return false
    }


    fun activateFreeze(turn: Boolean): List<Pair<Double, Double>> {
        val activeCheckers = mutableListOf<Pair<Double, Double>>()
        for (elem in list) {
            val queen = elem in queens
            val white = elem in list.subList(0, 12)

            if (stepsForCheckers2(elem, white, 0.0, 0.0, queen).isNotEmpty() && turn == white && !queen) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index]))
            }

            if (queenStep1(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep1(elem, white, 0.0, 0.0), 4)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index]))
            }
            if (queenStep2(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep2(elem, white, 0.0, 0.0), 3)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index]))
            }
            if (queenStep3(elem, white, 0.0, 0.0).isNotEmpty() && queen) println(queenStep3(elem, white, 0.0, 0.0))
            if (queenStep3(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep3(elem, white, 0.0, 0.0), 2)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index]))
            }
            if (queenStep4(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep4(elem, white, 0.0, 0.0), 1)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index]))
            }
            if (queenStep5(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep5(elem, white, 0.0, 0.0), 4)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index])) // влево вверх
            }
            if (queenStep6(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep6(elem, white, 0.0, 0.0), 3)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index])) // вправо вверх
            }
            if (queenStep7(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep7(elem, white, 0.0, 0.0), 2)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index])) // влево вниз
            }
            if (queenStep8(elem, white, 0.0, 0.0).isNotEmpty() && queen && checkActiveQueen(elem, queenStep8(elem, white, 0.0, 0.0), 1)) {
                val index = list.indexOf(elem)
                activeCheckers.add(Logic().cell((Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)[index])) // вправо вниз
            }
        }
        return activeCheckers
    }

}