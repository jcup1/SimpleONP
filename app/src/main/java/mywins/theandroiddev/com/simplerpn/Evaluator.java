package mywins.theandroiddev.com.simplerpn;

/**
 * Created by jakub on 22.12.17.
 */

public class Evaluator {

    private int position;
    private int character;
    private String expression;
    private double number;
    private int startPos;

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

        reset();

        checkOperate();

        return number;
    }

    private void reset() {
        number = 0;
        startPos = this.position;
    }

    private void checkOperate() {

        if (operate('(')) {
            onOperateOpenBracket();
        } else if (Character.isDigit(character)) {
            onCharacterIsDigit();
        } else if (Character.isLetter(character)) {
            onCharacterIsLetter();
        }
        if (operate('^')) {
            onOperatePow();
        }
    }

    private void onOperatePow() {
        number = Math.pow(number, parseFactor());
    }

    private void onOperateOpenBracket() {
        number = parseExpression();
        operate(')');
    }

    private void onCharacterIsLetter() {
        while (Character.isLetter(character)) {
            nextChar();
        }
        number = parseFactor();
    }

    private void onCharacterIsDigit() {
        while (Character.isDigit(character)) {
            nextChar();
        }
        number = findParsedNumber();
    }

    private double findParsedNumber() {
        return Double.parseDouble(expression.substring(startPos, this.position));
    }
}
