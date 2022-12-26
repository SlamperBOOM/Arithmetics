package arithmetics;

import java.util.*;

//умножение
public class Mul extends Expression{
    private final Expression argument1;
    private final Expression argument2;

    public Mul(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public Mul(List<Expression> args){
        if(args.size() == 2){
            argument1 = args.get(0);
            argument2 = args.get(1);
        }else {
            Mul subMul = new Mul(args.get(0), args.get(1));
            for (int i = 2; i < args.size() - 1; ++i) { //1*2*3*4*5 -> (((1*2)*3)*4)*5, для удобства дальнейшего счета
                subMul = new Mul(subMul, args.get(i));
            }
            argument1 = subMul;
            argument2 = args.get(args.size() - 1);
        }
    }

    @Override
    protected double calculate(double variableValue) {
        return argument1.calculate(variableValue) * argument2.calculate(variableValue);
    }

    @Override
    protected ExprType getType() {
        return ExprType.MUL;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        Expression newArg1 = argument1.calculate(variableMap);
        Expression newArg2 = argument2.calculate(variableMap);
        if(newArg1.getType() != ExprType.NUM || newArg2.getType() != ExprType.NUM){
            return new Mul(newArg1, newArg2);
        }else{
            return new Num(((Num)newArg1).getNumber() * ((Num)newArg2).getNumber() );
        }
    }

    @Override
    protected Expression calculate(String varName, double value) {
        Expression newArg1 = argument1.calculate(varName, value);
        Expression newArg2 = argument2.calculate(varName, value);
        if(newArg1.getType() != ExprType.NUM || newArg2.getType() != ExprType.NUM){
            return new Mul(newArg1, newArg2);
        }else{
            return new Num(((Num)newArg1).getNumber() * ((Num)newArg2).getNumber() );
        }
    }

    @Override
    public Expression copy() {
        Expression newArg1 = argument1.copy();
        Expression newArg2 = argument2.copy();
        return new Mul(newArg1, newArg2);
    }

    @Override
    protected Expression derivative(String derivativeVariable) {
        Expression newArg1 = argument1.derivative(derivativeVariable);
        Expression newArg2 = argument2.derivative(derivativeVariable);
        return new Sum(
                new Mul(newArg1, argument2.copy()),
                new Mul(argument1.copy(), newArg2)
        );
    }

    @Override
    protected Expression simplify() {
        Expression simple1 = argument1.simplify();
        Expression simple2 = argument2.simplify();

        if((simple1.getType() == ExprType.NUM && ((Num)simple1).getNumber() == 0)
                || (simple2.getType() == ExprType.NUM && ((Num)simple2).getNumber() == 0)){
            return new Num(0);
        } else if(simple1.getType() == ExprType.NUM && ((Num)simple1).getNumber() == 1){
            return simple2;
        }else if(simple2.getType() == ExprType.NUM && ((Num)simple2).getNumber() == 1){
            return simple1;
        }else{
            return new Mul(simple1, simple2);
        }
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument1.countVariables(variables);
        argument2.countVariables(variables);
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.MUL){
            return false;
        }else{
            return argument1.equals(((Mul)expression).argument1) &&
                    argument2.equals(((Mul)expression).argument2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(argument1.getType() == ExprType.NUM || argument1.getType() == ExprType.MUL || argument1.getType() == ExprType.DIV){
            builder.append(argument1).append("*");
        }else{
            builder.append("(").append(argument1).append(")*");
        }
        if(argument2.getType() == ExprType.NUM || argument2.getType() == ExprType.MUL || argument2.getType() == ExprType.DIV){
            builder.append(argument2);
        }else{
            builder.append("(").append(argument2).append(")");
        }
        return builder.toString();
    }
}
