class Cell(cord: Coordinates) {
    var centerX = listOfLetters1.indexOf(Letters.valueOf(cord.toString()[0].toString())) * 70.0 + 22
    var centerY = listOfNumbers1.indexOf(cord.toString()[1].digitToInt()) * 70.0 + 22
}