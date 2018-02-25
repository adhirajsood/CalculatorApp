package com.sood.calculator;

import java.util.ArrayList;
import java.util.Stack;


public class CalculatorMain
{
    ArrayList<Character> operators;
    public CalculatorMain()
    {
        operators=new ArrayList<>();
        operators.add('^');
        operators.add('%');
        operators.add('/');
        operators.add('*');
        operators.add('+');
        operators.add('-');
    }
    
    
    public String createStringPostFix(String inputP)//this will return the postfix expression of given input
    {
        String temp="";
        ArrayList<Character> al=new ArrayList<>();
        al.add('(');
        char ch;
        inputP=inputP+")";
        for(int i=0;i<inputP.length();i++)
        {
            ch=inputP.charAt(i);
           if(ch=='(')
               al.add('(');
           else if(operators.contains(ch))
           {
               al.add(ch);
               temp=temp+pop(al,ch);
           }    
           else if(ch==')')
               temp=temp+pop(al);
           else
           {
               while(ch!='(' && ch!=')' && !operators.contains(ch))
               {
                   temp=temp+ch;
                   i=i+1;
                   ch=inputP.charAt(i);
               }
               temp=temp+" ";
               i=i-1;
           }
           
        }
        return temp;
    }

    
    
    String pop(ArrayList<Character> al,char ch)// pop all the operators of higher precedence than ch and add them to 
                                    //temp
    {
        String temp="";
        for (int i = al.size()-1;  al.get(i)!='(' && i>=0;   i--) 
        {
            if(operators.indexOf(al.get(i))<operators.indexOf(ch))
            {
                temp=temp+al.get(i);
                al.remove(i);
            }    
        }
        return temp;
    }
    
    
    String pop(ArrayList<Character> al)// pop all operators until '(' reached and add them to temp
    {
        String temp="";
        for (int i = al.size()-1;  al.get(i)!='(' && i>=0;   i--) 
        {
                temp=temp+al.get(i);
                al.remove(i);
        }
        al.remove(al.size()-1);
        return temp;
    }
    
    
    public float evaluateResult(String inputP)//this will return the final result after evaluating postfix expression
    {
        Stack<Float> stack=new Stack<>();
        char ch;
        float inputA;
        float inputB;
        for (int i = 0; i < inputP.length(); i++)
        {
            ch=inputP.charAt(i);
            
            if(operators.contains(ch))
            {
                inputB=stack.pop();
                inputA=stack.pop();
                switch(ch)
                {
                    case '^' :
                        stack.push((float)Math.pow(inputA,inputB));
                        break;
                    case '%' :
                        stack.push(inputA%inputB);
                        break;
                    case '/' :
                        stack.push(inputA/inputB);
                        break;
                    case '*' :
                        stack.push(inputA*inputB);
                        break;
                    case '+' :
                        stack.push(inputA+inputB);
                        break;
                    case '-' :
                        stack.push(inputA-inputB);
                        break;
                }
            }
            
            
            else
            {
                String temp="";
                while(!operators.contains(ch) && i<inputP.length() && ch!=' ')
                {
                    temp=temp+ch;
                    i=i+1;
                    ch=inputP.charAt(i);
                }
                stack.push((Float.parseFloat(temp)));
            }
                
        }
        return stack.pop();
    }

}
