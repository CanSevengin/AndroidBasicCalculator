package com.humber.saynn.humbercalc;

import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Calculator {
    String resultString;
    String historyString;
    ArrayList<String> stack;

    public Calculator(){
        resultString = "";
        historyString = "";
        stack = new ArrayList<String>();
    }

    public void numberClicked(String numberString){
        if(isNumber(numberString)){
            if(stack.size()!=0){
                if(isArithmeticOperation(stack.get(stack.size()-1))){
                    String op = getArithmeticOperation(stack.get(stack.size()-1));
                    stack.remove(stack.size()-1);
                    String result = doTheMath(stack.get(stack.size()-1),numberString,op);
                    stack.remove(stack.size()-1);
                    stack.add(result);
                    Log.e("arit",stack.toString());
                    this.resultString = result;
                }else{
                    stack.add(numberString);
                }
            }else{
                stack.add(numberString);
                this.resultString += numberString;
            }
        }else if(isArithmeticOperation(numberString)){
            stack.add(numberString);
            this.resultString += numberString;
        }


    }

    private String doTheMath(String no1, String no2, String op){
        String result="";
        int number1 = Integer.valueOf(no1);
        int number2 = Integer.valueOf(no2);
        int res = 0;
        Log.e("Arithmetic",number1+"");
        if(op.equalsIgnoreCase("plus")){
             res = number1 + number2;
             result = res+"";
        }else if(op.equalsIgnoreCase("multiply")){
            res = number1 * number2;
            result = res+"";
        }else if(op.equalsIgnoreCase("divide")){
            res = number1 / number2;
            result = res+"";
        }else if(op.equalsIgnoreCase("minus")){
            res = number1 - number2;
            result = res+"";
        }
        return result;
    }

    private String getArithmeticOperation(String s) {
        String op = "";
        if(s.equalsIgnoreCase("x")){
            op = "multiply";
        }else if(s.equalsIgnoreCase("+")){
            op = "plus";
        }else if(s.equalsIgnoreCase("/")){
            op = "divide";

        }else if(s.equalsIgnoreCase("-")){
            op = "minus";
        }
        return op;
    }

    private boolean isArithmeticOperation(String s) {
        ArrayList<String> arithmetics = new ArrayList<String>();
        arithmetics.add("+");
        arithmetics.add("/");
        arithmetics.add("x");
        arithmetics.add("-");
        return arithmetics.contains(s);
    }

    private boolean isNumber(String numberString) {
        char c = numberString.charAt(0);
        if(Character.isDigit(c)){
            return true;
        }

        return false;
    }

    public String getHistoryString() {
        return this.historyString;
    }

    public String getNumberString(){
        return this.resultString;
    }

}
