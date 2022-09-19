package com.example.demo.logic;

public class WinLine {

    private Matrix matrix;

    public WinLine(Matrix matrix) {
        this.matrix = matrix;
    }

    public String redWin(Matrix matrix, int x, int y) {
        String winner = "";
        matrix.getElement(x, y);
        //horizontalWinLine
        if (x < 11) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y) == "red") &&
                            (matrix.getElement(x + 2, y) == "red") && (matrix.getElement(x + 3, y) == "red") &&
                            (matrix.getElement(x + 4, y) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 12) && (x > 0)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y) == "red") &&
                            (matrix.getElement(x + 2, y) == "red") && (matrix.getElement(x + 3, y) == "red") &&
                            (matrix.getElement(x - 1, y) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 13) && (x > 1)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y) == "red") &&
                            (matrix.getElement(x + 2, y) == "red") && (matrix.getElement(x - 1, y) == "red") &&
                            (matrix.getElement(x - 2, y) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 14) && (x > 2)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y) == "red") &&
                            (matrix.getElement(x - 1, y) == "red") && (matrix.getElement(x - 2, y) == "red") &&
                            (matrix.getElement(x - 3, y) == "red")
            )
                winner = "Победил красный";
        }
        if (x > 3) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x - 1, y) == "red") &&
                            (matrix.getElement(x - 2, y) == "red") && (matrix.getElement(x - 3, y) == "red") &&
                            (matrix.getElement(x - 4, y) == "red")
            )
                winner = "Победил красный";
        }


        //verticalWinLine
        if (y < 11) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x, y + 1) == "red") &&
                            (matrix.getElement(x, y + 2) == "red") && (matrix.getElement(x, y + 3) == "red") &&
                            (matrix.getElement(x, y + 4) == "red")
            )
                winner = "Победил красный";
        }
        if ((y < 12) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x, y + 1) == "red") &&
                            (matrix.getElement(x, y + 2) == "red") && (matrix.getElement(x, y + 3) == "red") &&
                            (matrix.getElement(x, y - 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((y < 13) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x, y + 1) == "red") &&
                            (matrix.getElement(x, y + 2) == "red") && (matrix.getElement(x, y - 2) == "red") &&
                            (matrix.getElement(x, y - 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((y < 14) && (y > 2)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x, y + 1) == "red") &&
                            (matrix.getElement(x, y - 1) == "red") && (matrix.getElement(x, y - 2) == "red") &&
                            (matrix.getElement(x, y - 3) == "red")
            )
                winner = "Победил красный";
        }
        if (y > 3) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x, y - 1) == "red") &&
                            (matrix.getElement(x, y - 2) == "red") && (matrix.getElement(x, y - 3) == "red") &&
                            (matrix.getElement(x, y - 4) == "red")
            )
                winner = "Победил красный";
        }


        //leftToRightDiagonalWinLIne
        if ((x < 11) && (y < 11)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y + 1) == "red") &&
                            (matrix.getElement(x + 2, y + 2) == "red") && (matrix.getElement(x + 3, y + 3) == "red") &&
                            (matrix.getElement(x + 4, y + 4) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 12) && (y < 12) && (x > 0) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y + 1) == "red") &&
                            (matrix.getElement(x + 2, y + 2) == "red") && (matrix.getElement(x + 3, y + 3) == "red") &&
                            (matrix.getElement(x - 1, y - 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 13) && (y < 13) && (x > 1) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y + 1) == "red") &&
                            (matrix.getElement(x + 2, y + 2) == "red") && (matrix.getElement(x - 2, y - 2) == "red") &&
                            (matrix.getElement(x - 1, y - 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 14) && (y < 14) && (x > 2) && (y > 2)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y + 1) == "red") &&
                            (matrix.getElement(x - 1, y - 1) == "red") && (matrix.getElement(x - 2, y - 2) == "red") &&
                            (matrix.getElement(x - 3, y - 3) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 15) && (y < 15) && (x > 3) && (y > 3)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x - 1, y - 1) == "red") &&
                            (matrix.getElement(x - 2, y - 2) == "red") && (matrix.getElement(x - 3, y - 3) == "red") &&
                            (matrix.getElement(x - 4, y - 4) == "red")
            )
                winner = "Победил красный";
        }


        //rightToLeftDiagonalWinLine
        if ((x < 11) && (y > 3)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y - 1) == "red") &&
                            (matrix.getElement(x + 2, y - 2) == "red") && (matrix.getElement(x + 3, y - 3) == "red") &&
                            (matrix.getElement(x + 4, y - 4) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 12) && (y > 2) && (x > 0) && (y < 14)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y - 1) == "red") &&
                            (matrix.getElement(x + 2, y - 2) == "red") && (matrix.getElement(x + 3, y - 3) == "red") &&
                            (matrix.getElement(x - 1, y + 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 13) && (y < 13) && (x > 1) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y - 1) == "red") &&
                            (matrix.getElement(x + 2, y - 2) == "red") && (matrix.getElement(x - 2, y + 2) == "red") &&
                            (matrix.getElement(x - 1, y + 1) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 14) && (y < 12) && (x > 2) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x + 1, y - 1) == "red") &&
                            (matrix.getElement(x - 1, y + 1) == "red") && (matrix.getElement(x - 2, y + 2) == "red") &&
                            (matrix.getElement(x - 3, y + 3) == "red")
            )
                winner = "Победил красный";
        }
        if ((x < 15) && (y < 11) && (x > 3)) {
            if (
                    (matrix.getElement(x, y) == "red") && (matrix.getElement(x - 1, y + 1) == "red") &&
                            (matrix.getElement(x - 2, y + 2) == "red") && (matrix.getElement(x - 3, y + 3) == "red") &&
                            (matrix.getElement(x - 4, y + 4) == "red")
            )
                winner = "Победил красный";
        }


        return winner;
    }


    public String greenWin(Matrix matrix, int x, int y) {
        String winner = "";
        matrix.getElement(x, y);
        //horizontalWinLine
        if (x < 11) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y) == "green") &&
                            (matrix.getElement(x + 2, y) == "green") && (matrix.getElement(x + 3, y) == "green") &&
                            (matrix.getElement(x + 4, y) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 12) && (x > 0)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y) == "green") &&
                            (matrix.getElement(x + 2, y) == "green") && (matrix.getElement(x + 3, y) == "green") &&
                            (matrix.getElement(x - 1, y) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 13) && (x > 1)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y) == "green") &&
                            (matrix.getElement(x + 2, y) == "green") && (matrix.getElement(x - 1, y) == "green") &&
                            (matrix.getElement(x - 2, y) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 14) && (x > 2)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y) == "green") &&
                            (matrix.getElement(x - 1, y) == "green") && (matrix.getElement(x - 2, y) == "green") &&
                            (matrix.getElement(x - 3, y) == "green")
            )
                winner = "Победил зелёный";
        }
        if (x > 3) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x - 1, y) == "green") &&
                            (matrix.getElement(x - 2, y) == "green") && (matrix.getElement(x - 3, y) == "green") &&
                            (matrix.getElement(x - 4, y) == "green")
            )
                winner = "Победил зелёный";
        }


        //verticalWinLine
        if (y < 11) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x, y + 1) == "green") &&
                            (matrix.getElement(x, y + 2) == "green") && (matrix.getElement(x, y + 3) == "green") &&
                            (matrix.getElement(x, y + 4) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((y < 12) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x, y + 1) == "green") &&
                            (matrix.getElement(x, y + 2) == "green") && (matrix.getElement(x, y + 3) == "green") &&
                            (matrix.getElement(x, y - 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((y < 13) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x, y + 1) == "green") &&
                            (matrix.getElement(x, y + 2) == "green") && (matrix.getElement(x, y - 2) == "green") &&
                            (matrix.getElement(x, y - 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((y < 14) && (y > 2)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x, y + 1) == "green") &&
                            (matrix.getElement(x, y - 1) == "green") && (matrix.getElement(x, y - 2) == "green") &&
                            (matrix.getElement(x, y - 3) == "green")
            )
                winner = "Победил зелёный";
        }
        if (y > 3) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x, y - 1) == "green") &&
                            (matrix.getElement(x, y - 2) == "green") && (matrix.getElement(x, y - 3) == "green") &&
                            (matrix.getElement(x, y - 4) == "green")
            )
                winner = "Победил зелёный";
        }


        //leftToRightDiagonalWinLIne
        if ((x < 11) && (y < 11)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y + 1) == "green") &&
                            (matrix.getElement(x + 2, y + 2) == "green") && (matrix.getElement(x + 3, y + 3) == "green") &&
                            (matrix.getElement(x + 4, y + 4) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 12) && (y < 12) && (x > 0) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y + 1) == "green") &&
                            (matrix.getElement(x + 2, y + 2) == "green") && (matrix.getElement(x + 3, y + 3) == "green") &&
                            (matrix.getElement(x - 1, y - 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 13) && (y < 13) && (x > 1) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y + 1) == "green") &&
                            (matrix.getElement(x + 2, y + 2) == "green") && (matrix.getElement(x - 2, y - 2) == "green") &&
                            (matrix.getElement(x - 1, y - 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 14) && (y < 14) && (x > 2) && (y > 2)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y + 1) == "green") &&
                            (matrix.getElement(x - 1, y - 1) == "green") && (matrix.getElement(x - 2, y - 2) == "green") &&
                            (matrix.getElement(x - 3, y - 3) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 15) && (y < 15) && (x > 3) && (y > 3)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x - 1, y - 1) == "green") &&
                            (matrix.getElement(x - 2, y - 2) == "green") && (matrix.getElement(x - 3, y - 3) == "green") &&
                            (matrix.getElement(x - 4, y - 4) == "green")
            )
                winner = "Победил зелёный";
        }


        //rightToLeftDiagonalWinLine
        if ((x < 11) && (y > 3)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y - 1) == "green") &&
                            (matrix.getElement(x + 2, y - 2) == "green") && (matrix.getElement(x + 3, y - 3) == "green") &&
                            (matrix.getElement(x + 4, y - 4) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 12) && (y > 2) && (x > 0) && (y < 14)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y - 1) == "green") &&
                            (matrix.getElement(x + 2, y - 2) == "green") && (matrix.getElement(x + 3, y - 3) == "green") &&
                            (matrix.getElement(x - 1, y + 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 13) && (y < 13) && (x > 1) && (y > 1)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y - 1) == "green") &&
                            (matrix.getElement(x + 2, y - 2) == "green") && (matrix.getElement(x - 2, y + 2) == "green") &&
                            (matrix.getElement(x - 1, y + 1) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 14) && (y < 12) && (x > 2) && (y > 0)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x + 1, y - 1) == "green") &&
                            (matrix.getElement(x - 1, y + 1) == "green") && (matrix.getElement(x - 2, y + 2) == "green") &&
                            (matrix.getElement(x - 3, y + 3) == "green")
            )
                winner = "Победил зелёный";
        }
        if ((x < 15) && (y < 11) && (x > 3)) {
            if (
                    (matrix.getElement(x, y) == "green") && (matrix.getElement(x - 1, y + 1) == "green") &&
                            (matrix.getElement(x - 2, y + 2) == "green") && (matrix.getElement(x - 3, y + 3) == "green") &&
                            (matrix.getElement(x - 4, y + 4) == "green")
            )
                winner = "Победил зелёный";
        }
        return winner;
    }
}
