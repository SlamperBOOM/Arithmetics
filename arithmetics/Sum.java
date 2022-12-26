package arithmetics;

import java.util.*;

//сложение
public class Sum extends Expression{
    private final Expression argument1;
    private final Expression argument2;

    public Sum(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public Sum(List<Expression> args){
        if(args.size() == 2){
            argument1 = args.get(0);
            argument2 = args.get(1);
        }else {
            Sum subSum = new Sum(args.get(0), args.get(1));
            for (int i = 2; i < args.size() - 1; ++i) { //1+2+3+4+5 -> (((1+2)+3)+4)+5, для удобства дальнейшего счета
                subSum = new Sum(subSum, args.get(i));
            }
            argument1 = subSum;
            argument2 = args.get(args.size() - 1);
        }
    }

    @Override
    protected double calculate(double variableValue) {
        return argument1.calculate(variableValue) + argument2.calculate(variableValue);
    }

    @Override
    protected ExprType getType() {
        return ExprType.SUM;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        Expression newArg1 = argument1.calculate(variableMap);
        Expression newArg2 = argument2.calculate(variableMap);
        if(newArg1.getType() != ExprType.NUM || newArg2.getType() != ExprType.NUM){
            return new Sum(newArg1, newArg2);
        }else{
            return new Num(((Num)newArg1).getNumber() + ((Num)newArg2).getNumber() );
        }
    }

    @Override
    protected Expression calculate(String varName, double value) {
        Expression newArg1 = argument1.calculate(varName, value);
        Expression newArg2 = argument2.calculate(varName, value);
        if(newArg1.getType() != ExprType.NUM || newArg2.getType() != ExprType.NUM){
            return new Sum(newArg1, newArg2);
        }else{
            return new Num(((Num)newArg1).getNumber() + ((Num)newArg2).getNumber() );
        }
    }

    @Override
    public Expression copy() {
        Expression newArg1 = argument1.copy();
        Expression newArg2 = argument2.copy();
        return new Sum(newArg1, newArg2);
    }

    @Override
    protected Expression derivative(String derivativeVariable) {
        Expression newArg1 = argument1.derivative(derivativeVariable);
        Expression newArg2 = argument2.derivative(derivativeVariable);
        return new Sum(newArg1, newArg2);
    }

    @Override
    protected Expression simplify() {
        Expression simple1 = argument1.simplify();
        Expression simple2 = argument2.simplify();

        if(simple1.getType() == ExprType.NUM && ((Num)simple1).getNumber() == 0){
            return simple2;
        }else if(simple2.getType() == ExprType.NUM && ((Num)simple2).getNumber() == 0){
            return simple1;
        }else{
            return new Sum(simple1, simple2);
        }
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument1.countVariables(variables);
        argument2.countVariables(variables);
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.SUM){
            return false;
        }else{
            return argument1.equals(((Sum)expression).argument1) &&
                    argument2.equals(((Sum)expression).argument2);
        }
    }

    @Override
    public String toString() {
        return argument1.toString() + "+" + argument2.toString();
    }
}
