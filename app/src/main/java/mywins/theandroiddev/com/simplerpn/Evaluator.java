package mywins.theandroiddev.com.simplerpn;

/**
 * Created by jakub on 22.12.17.
 */

public class Evaluator {

    private int position;
    private int character;
    private String expression;

    public double evaluate(final String expression) {
        position = -1;
        this.expression = expression;
        return parse();
    }

    private void nextChar() {
        if (++position < expression.length()) {
            character = expression.charAt(position);
        } else {
            character = -1;
        }
    }

    private boolean operate(double operationCharacter) {
        //handle white spaces
        while (character == ' ') {
            nextChar();
        }
        if (character == operationCharacter) {
            nextChar();
            return true;
        }
        return false;
    }

    private double parse() {
        nextChar();
        return parseExpression();
    }

    private double parseExpression() {
        double number;

        number = parseTerm();
        while (true) {
            if (operate('+')) {
                number += parseTerm();
            } else if (operate('-')) {
                number -= parseTerm();
            } else return number;
        }
    }

    //I could use other multiply and divide symbols and convert them
    //but I don't think that it's really important in this project
    private double parseTerm() {
        double number;

        number = parseFactor();

        while (true) {
            if (operate('*')) {
                number *= parseFactor();
            } else if (operate('/')) {
                number /= parseFactor();
            } else return number;
        }
    }

    private double parseFactor() {
        if (operate('+')) {
            return parseFactor();
        }
        if (operate('-')) {
            return -parseFactor();
        }

        double number;
        number = 0;
        int startPos = this.position;
        if (operate('(')) {
            number = parseExpression();
            operate(')');
        } else if (Character.isDigit(character)) {
            while (Character.isDigit(character)) {
                nextChar();
            }
            number = Double.parseDouble(expression.substring(startPos, this.position));
        } else if (Character.isLetter(character)) {
            while (Character.isLetter(character)) {
                nextChar();
            }
            number = parseFactor();
        }
        //no double and '.' isOperator
        if (operate('^')) {
            number = Math.pow(number, parseFactor());
        }

        return number;
    }
}
