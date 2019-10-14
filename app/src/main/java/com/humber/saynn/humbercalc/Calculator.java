package com.humber.saynn.humbercalc;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Calculator {
    String resultString;
    String historyString;
    ArrayList<String> stack;
    ArrayList<String> calculationStack;
    boolean newOp;
    DecimalFormat df;
    boolean percentage;
    String memory;

    public Calculator() {
        resultString = "";
        historyString = "";
        stack = new ArrayList<String>();
        calculationStack = new ArrayList<String>();
        newOp = false;
        df = new DecimalFormat("#.#######");
        percentage = false;
        memory = "0";
    }

    public void numberClicked(String numberString) {
        String op = "";
        // If the button clicked is a number
        if (isNumber(numberString)) {
            //If this is not a start to a new operation
            if (!newOp) {
                if (stack.size() != 0) {
                    //If there is an arithmetic operation button is clicked beforehand
                    if (isArithmeticOperation(stack.get(stack.size() - 1))) {
                        this.resultString = numberString;
                        stack.remove(stack.size() - 1);
                    } else { //regular entry, concatenate with the existing string
                        this.resultString += numberString;
                    }
                } else { // The very first entry
                    this.resultString += numberString;
                }
            } else {
                this.resultString = numberString;
                if (stack.size() != 0) {
                    this.historyString = stack.get(0);
                }
                newOp = false;
            }
            // If any arithmetic operation is clicked
        } else if (isArithmeticOperation(numberString)) {
            //If no other calculation is made beforehand
            op = getArithmeticOperation(numberString);
            //CALL ARITHMETIC
            doTheArithmetic(numberString, op);
            //If functions are clicked
            //CALL FUNCTION
        } else if (isFunctionalButton(numberString)) {
            String fnctn = getFunctionalButton(numberString);
            doTheFunction(fnctn);
        }
    }

    private void doTheFunction(String fnctn) {
        if (fnctn.equalsIgnoreCase(".")) {
            if (!this.resultString.contains(".")) {
                this.resultString += fnctn;
            }
        } else if (fnctn.equalsIgnoreCase("delete")) {
            this.resultString = "";
            this.historyString = "";
            stack.clear();
            calculationStack.clear();
            newOp = true;
        } else if (fnctn.equalsIgnoreCase("change")) {
            if (!this.resultString.isEmpty()) {
                if (Double.valueOf(this.resultString) > 0) {
                    this.resultString = "-" + this.resultString;
                } else {
                    this.resultString = Math.abs(Double.valueOf(this.resultString)) + "";
                }
            }
            //CALLED IF PERCENTAGE IS CLICKED
        } else if (fnctn.equalsIgnoreCase("percent")) {
            percentage = true;
            Double currentValue = Double.valueOf(this.resultString);
            String percentValue = df.format(currentValue / 100);
            if (!this.resultString.contains("%")) {
                this.resultString += "%";
            }
            if (calculationStack.size() == 2) {
                this.historyString = calculationStack.get(0) + " " + calculationStack.get(1) + " " + this.resultString;
                String secondaryResult = doTheMath(calculationStack.get(0), percentValue, calculationStack.get(1));
                this.resultString = secondaryResult;
                newOp = true;
                calculationStack.clear();
                stack.clear();
            } else {
                this.resultString = percentValue;
                percentage = false;
            }
        } else if (fnctn.equalsIgnoreCase("mr")) {
            this.resultString = memory;

        } else if (fnctn.equalsIgnoreCase("mc")) {
            this.historyString = "Memory cleared. Memory: 0";
            this.memory = "0";
        }
    }

    private void doTheArithmetic(String numberString, String op) {
        //Log.e("arit", "Arithmetic, OP: " + op);


        if (!this.resultString.isEmpty()) {         //Entry is correct. There are numbers to work with.
            if (!op.equalsIgnoreCase("mplus") && !op.equalsIgnoreCase("mminus")) {
                stack.add(this.resultString);
                calculationStack.add(this.resultString);
                this.historyString = this.resultString;
                this.resultString = "";
                //If equals is clicked
                if (op.equalsIgnoreCase("equals")) {
                    //Log.e("arit","Stack: " + stack.toString() + ", Calc: " + calculationStack.toString());
                    if(calculationStack.size()<3){
                        calculationStack.clear();
                        stack.clear();
                        this.historyString = "Wrong operation.";
                        this.resultString = "";
                        return;
                    }
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
                    //Log.e("arit", calculationStack.toString());
                    if (calculationStack.size() >= 3) {
                        this.resultString = doTheMath(calculationStack.get(0), calculationStack.get(2), calculationStack.get(1));
                        this.historyString = calculationStack.get(0) + " " + calculationStack.get(1) + " " + calculationStack.get(2) + " =";
                        calculationStack.clear();
                        stack.clear();
                        stack.add(this.resultString);
                        calculationStack.add(this.resultString);
                        newOp = true;
                    }
                    newOp = false;
                    calculationStack.add(op);
                    stack.add(numberString);
                }
            } else { //If memory function
                doTheMath(memory, this.resultString, op);
            }
        } else { //If the entry is incorrect ,i.e, nothing to work with
            if (stack.isEmpty()) {
                this.resultString = "";
                newOp = true;
            }
        }
    }

    // Calculating
    private String doTheMath(String no1, String no2, String op) {
        String result = "";
        double number1 = Double.valueOf(no1);
        double number2 = Double.valueOf(no2);
        double res = 0;
        if (op.equalsIgnoreCase("plus")) {
            if (percentage) {
                res = number1 + number2 * number1;
                percentage = false;
            } else {
                res = number1 + number2;
            }
            result = df.format(res);
        } else if (op.equalsIgnoreCase("multiply")) {
            res = number1 * number2;
            if (percentage) percentage = false;
            result = df.format(res);
        } else if (op.equalsIgnoreCase("divide")) {
            if (number2 != 0) {
                res = number1 / number2;
                if (percentage) percentage = false;
                result = df.format(res);
            }
        } else if (op.equalsIgnoreCase("minus")) {
            if (percentage) {
                res = number1 - number2 * number1;
                percentage = false;
            } else {
                res = number1 - number2;
            }
            result = df.format(res);
        } else if (op.equalsIgnoreCase("mplus")) {
            if (!memory.isEmpty()) {
                res = number1 + number2;
                this.memory = df.format(res);
                result = df.format(res);
                this.historyString = this.resultString + " M+ ";
                this.resultString = "";
                newOp = true;
            }
        } else if (op.equalsIgnoreCase("mminus")) {
            if (!memory.isEmpty()) {
                res = number1 - number2;
                this.memory = df.format(res);
                result = df.format(res);
                this.historyString = this.resultString + " M- ";
                this.resultString = "";
                newOp = true;
            }
        } else if (op.equalsIgnoreCase("power")) {
            res = Math.pow(number1, number2);
            result = df.format(res);
        }
        return result;
    }

    // Long version of enuming, identifies the functional buttons
    private boolean isFunctionalButton(String numberString) {
        ArrayList<String> functionals = new ArrayList<String>();
        functionals.add("+-");
        functionals.add("C");
        functionals.add("%");
        functionals.add(".");
        functionals.add("MR");
        functionals.add("MC");
        return functionals.contains(numberString);
    }

    // Long version of enuming, identifies the arithmetical buttons
    private boolean isArithmeticOperation(String s) {
        ArrayList<String> arithmetics = new ArrayList<String>();
        arithmetics.add("+");
        arithmetics.add("/");
        arithmetics.add("x");
        arithmetics.add("-");
        arithmetics.add("=");
        arithmetics.add("M+");
        arithmetics.add("M-");
        arithmetics.add("\u2093\u02b8");
        return arithmetics.contains(s);
    }

    //Checks if the button entered is number
    private boolean isNumber(String numberString) {
        char c = numberString.charAt(0);
        return Character.isDigit(c);
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
        } else if (s.equalsIgnoreCase("M+")) {
            op = "mplus";
        } else if (s.equalsIgnoreCase("M-")) {
            op = "mminus";
        } else if (s.equalsIgnoreCase("\u2093\u02b8")) {
            op = "power";
        }
        return op;
    }

    private String getFunctionalButton(String s) {
        String fn = "";
        if (s.equalsIgnoreCase("+-")) {
            fn = "change";
        } else if (s.equalsIgnoreCase("%")) {
            fn = "percent";
        } else if (s.equalsIgnoreCase(".")) {
            fn = ".";
        } else if (s.equalsIgnoreCase("C")) {
            fn = "delete";
        } else if (s.equalsIgnoreCase("MR")) {
            fn = "mr";
        } else if (s.equalsIgnoreCase("MC")) {
            fn = "mc";
        }
        return fn;
    }

    public String getHistoryString() {
        return this.historyString;
    }

    public String getNumberString() {
        return this.resultString;
    }

}
