package mywins.theandroiddev.com.simpleonp;

import android.text.TextUtils;

import java.util.Stack;

/**
 * Created by jakub on 16.12.17.
 */

class Converter {

    public String toInfix(String postfix) {

        //TODO conversion
        return "Postfix";
    }

    public String toONP(String s) {

        StringBuilder res = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        int i = 0;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i))) {
                res.append(s.charAt(i));
            } else if (operator(s.charAt(i))) {
                //to make space between numbers
                res.append(' ');

                while (!stack.isEmpty() && !openBrackets(s.charAt(i)) &&
                        (getWeight(s.charAt(i)) <= getWeight(stack.peek()))) {
                    res.append(stack.peek());
                    stack.pop();
                }//while
                stack.push(s.charAt(i));
                //to make space between operator nad number(only when ONP operators are not at the end of the line.)
                res.append(' ');
            }//else if
            else if (openBrackets(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else if (closeBrackets(s.charAt(i))) {
                while (!stack.isEmpty() && !openBrackets(stack.peek())) {
                    res.append(stack.peek());
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
            res.append(stack.peek());
            stack.pop();
        }

        return TextUtils.isEmpty(res) ? "Letters are not allowed" : String.valueOf(res);
    }

    private boolean operator(char o) {
        return (o == '+') || (o == '-') || (o == '/') || (o == '*');
    }

    private boolean openBrackets(char o) {
        return (o == '{') || (o == '[') || (o == '(');
    }

    private boolean closeBrackets(char o) {
        return (o == '}') || (o == ']') || (o == ')');
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
