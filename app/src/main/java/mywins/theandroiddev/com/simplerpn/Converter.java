package mywins.theandroiddev.com.simplerpn;

import android.text.TextUtils;

import java.util.Stack;

/**
 * Created by jakub on 16.12.17.
 */

public class Converter {

    public String toRpn(String expression) {

        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();
        int operatorCount = 0;

        int i = 0;
        while (i < expression.length()) {
            if (Character.isDigit(expression.charAt(i))) {
                result.append(expression.charAt(i));
            } else if (isOperator(expression.charAt(i))) {
                //to make space between numbers
                operatorCount++;
                result.append(' ');

                while (!stack.isEmpty() && !isOpenBracket(expression.charAt(i)) &&
                        (getWeight(expression.charAt(i)) <= getWeight(stack.peek()))) {
                    result.append(stack.peek());
                    stack.pop();
                }//while
                stack.push(expression.charAt(i));
                //to make space between isOperator nad number(only when RPN operators are not at the end of the line.)
                result.append(' ');
            }//else if
            else if (isOpenBracket(expression.charAt(i))) {
                stack.push(expression.charAt(i));
            } else if (isClosedBracket(expression.charAt(i))) {
                while (!stack.isEmpty() && !isOpenBracket(stack.peek())) {
                    result.append(stack.peek());
                    stack.pop();
                }
                //to avoid EmptyStackException when user input strange char
                if (!stack.isEmpty())
                    stack.pop();
            }
            i++;
        }

        //get operators
        while (!stack.isEmpty()) {
            result.append(stack.peek());
            stack.pop();
        }

        if (TextUtils.isEmpty(result)) return "Letters are not allowed";
        else if (operatorCount == 0) return "";
        else return String.valueOf(result);
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
