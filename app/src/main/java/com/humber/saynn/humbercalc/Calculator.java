package com.humber.saynn.humbercalc;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator {
    String resultString;
    String historyString;
    ArrayList<String> stack;
    ArrayList<String> calculationStack;
    boolean newOp;

    public Calculator() {
        resultString = "";
        historyString = "";
        stack = new ArrayList<String>();
        calculationStack = new ArrayList<String>();
        newOp = false;
    }

    public void numberClicked(String numberString) {
        String op = "";
        if (isNumber(numberString)) {
            if (!newOp) {
                if (stack.size() != 0) {
                    if (isArithmeticOperation(stack.get(stack.size() - 1))) {
                        this.resultString = numberString;
                        stack.remove(stack.size() - 1);
                    } else {
                        this.resultString += numberString;
                    }
                } else {
                    this.resultString += numberString;
                }
            } else {
                this.resultString = numberString;
                this.historyString = stack.get(0);
                newOp = false;
            }
            // If any arithmetic operation is clicked
        } else if (isArithmeticOperation(numberString)) {
            //If no other calculation is made beforehand
            Log.e("arit", "started: " + stack.toString());
            if (!this.resultString.isEmpty()) {
                stack.add(this.resultString);
                calculationStack.add(this.resultString);
                this.historyString = this.resultString;
                this.resultString = "";
                op = getArithmeticOperation(numberString);
                //If equals is clicked
                if (op.equalsIgnoreCase("equals")) {
                    calculationStack.add(stack.get(stack.size() - 1));
                    //Log.e("arit","inside Equals: " + calculationStack.toString());
                    String newStackStarted = doTheMath(calculationStack.get(0), calculationStack.get(2), calculationStack.get(1));
                    this.historyString = calculationStack.get(0) + " " + calculationStack.get(1) + " " + calculationStack.get(2) + " =";
                    calculationStack.clear();
                    stack.clear();
                    stack.add(newStackStarted);
                    newOp = true;
                    this.resultString = newStackStarted;
                } else {
                    newOp = false;
                    calculationStack.add(op);
                    stack.add(numberString);
                }
            }
        }
    }

    private String doTheMath(String no1, String no2, String op) {
        String result = "";
        double number1 = Double.valueOf(no1);
        double number2 = Double.valueOf(no2);
        double res = 0;
        DecimalFormat df = new DecimalFormat("#.####");
        if (op.equalsIgnoreCase("plus")) {
            res = number1 + number2;
            result = df.format(res);
        } else if (op.equalsIgnoreCase("multiply")) {
            res = number1 * number2;
            result = df.format(res);
        } else if (op.equalsIgnoreCase("divide")) {
            res = number1 / number2;
            result = df.format(res);
        } else if (op.equalsIgnoreCase("minus")) {
            res = number1 - number2;
            result = df.format(res);
        }
        return result;
    }

    private String getArithmeticOperation(String s) {
        String op = "";
        if (s.equalsIgnoreCase("x")) {
            op = "multiply";
        } else if (s.equalsIgnoreCase("+")) {
            op = "plus";
        } else if (s.equalsIgnoreCase("/")) {
            op = "divide";

        } else if (s.equalsIgnoreCase("-")) {
            op = "minus";
        } else if (s.equalsIgnoreCase("=")) {
            op = "equals";
        }
        return op;
    }

    private boolean isArithmeticOperation(String s) {
        ArrayList<String> arithmetics = new ArrayList<String>();
        arithmetics.add("+");
        arithmetics.add("/");
        arithmetics.add("x");
        arithmetics.add("-");
        arithmetics.add("=");
        return arithmetics.contains(s);
    }

    private boolean isNumber(String numberString) {
        char c = numberString.charAt(0);
        if (Character.isDigit(c)) {
            return true;
        }

        return false;
    }

    public String getHistoryString() {
        return this.historyString;
    }

    public String getNumberString() {
        return this.resultString;
    }

}
