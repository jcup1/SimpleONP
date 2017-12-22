package mywins.theandroiddev.com.simplerpn;

import java.util.Stack;

/**
 * Created by jakub on 16.12.17.
 */

public class Converter {

    private Stack<Character> stack;
    private StringBuilder result;
    private int i;
    private char currentChar;
    private int operatorCount;

    public String toRpn(String expression) {

        stack = new Stack<>();
        result = new StringBuilder();
        i = 0;
        operatorCount = 0;

        while (i < expression.length()) {
            currentChar = expression.charAt(i);
            if (Character.isDigit(currentChar)) {
                onDigit();
            } else if (isOperator(currentChar)) {
                onOperator();
            }//else if
            else if (isOpenBracket(currentChar)) {
                onOpenBracket();
            } else if (isClosedBracket(currentChar)) {
                onClosedBracket();
            }
            i++;
        }

        appendOperators();

        if (operatorCount == 0) return "";
        return String.valueOf(result);

    }

    private void appendOperators() {
        while (!stack.isEmpty()) {
            result.append(stack.peek());
            stack.pop();
        }
    }

    private void onDigit() {
        result.append(currentChar);
    }

    private void onClosedBracket() {

        while (!stack.isEmpty() && !isOpenBracket(stack.peek())) {
            result.append(stack.peek());
            stack.pop();
        }
        //to avoid EmptyStackException when user input strange char
        if (!stack.isEmpty())
            stack.pop();
    }

    private void onOpenBracket() {
        stack.push(currentChar);
    }

    private void onOperator() {
        operatorCount++;
        //to make space between numbers
        result.append(' ');

        while (!stack.isEmpty() && !isOpenBracket(currentChar) &&
                (getWeight(currentChar) <= getWeight(stack.peek()))) {
            result.append(stack.peek());
            stack.pop();
        }
        stack.push(currentChar);
        //to make space between isOperator nad number(only when RPN operators are not at the end of the line.)
        result.append(' ');
    }

    public boolean isOperator(char operator) {
        return (operator == '+') || (operator == '-') || (operator == '/') || (operator == '*');
    }

    private boolean isOpenBracket(char operator) {
        return (operator == '{') || (operator == '[') || (operator == '(');
    }

    private boolean isClosedBracket(char operator) {
        return (operator == '}') || (operator == ']') || (operator == ')');
    }

    private int getWeight(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            //pow
            case '^':
                return 2;
            case '{':
            case '[':
            case '(':
            case '}':
            case ']':
            case ')':
                return -1;
            default:
                return -2;
        }
    }

}
