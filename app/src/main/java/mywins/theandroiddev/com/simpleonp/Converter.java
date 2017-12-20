package mywins.theandroiddev.com.simpleonp;

import android.text.TextUtils;

import java.util.Stack;

/**
 * Created by jakub on 16.12.17.
 */

public class Converter {

    //not used
    public String toInfix(String s) {

        return "Infix";
    }

    public String toONP(String s) {

        Stack<Character> stack = new Stack<>();
        StringBuilder res = new StringBuilder();
        int operatorCount = 0;

        int i = 0;
        while (i < s.length()) {
            if (Character.isDigit(s.charAt(i))) {
                res.append(s.charAt(i));
            } else if (operator(s.charAt(i))) {
                //to make space between numbers
                operatorCount++;
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

        if (TextUtils.isEmpty(res)) return "Letters are not allowed";
        else if (operatorCount == 0) return "";
        else return String.valueOf(res);
    }

    public boolean operator(char o) {
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

    public double evaluate(final String str) {
        return new Object() {
            int position = -1;
            int character;

            void nextChar() {
                if (++position < str.length()) character = str.charAt(position);
                else character = -1;
            }

            boolean operate(double c) {
                //handle white spaces
                while (character == ' ') nextChar();
                if (character == c) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                return parseExpression();
            }


            double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (operate('+')) {
                        x += parseTerm();
                    } else if (operate('-')) {
                        x -= parseTerm();
                    } else return x;
                }
            }

            //I could use other multiply and divide symbols and convert them
            //but I don't think that it's really important in this project
            double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (operate('*')) {
                        x *= parseFactor();
                    } else if (operate('/')) {
                        x /= parseFactor();
                    } else return x;
                }
            }

            double parseFactor() {
                if (operate('+')) return parseFactor();
                if (operate('-')) return -parseFactor();

                double x = 0;
                int startPos = this.position;
                if (operate('(')) {
                    x = parseExpression();
                    operate(')');
                } else if (Character.isDigit(character)) {
                    while (Character.isDigit(character)) nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.position));
                } else if (Character.isLetter(character)) {
                    while (Character.isLetter(character)) nextChar();
                    x = parseFactor();
                }
                //no double and '.' operator
                if (operate('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }
}
