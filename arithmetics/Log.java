package arithmetics;

import java.util.Map;
import java.util.Set;

//натуральный логарифм
public class Log extends Expression{
    private final Expression argument;

    public Log(Expression expr1){
        argument = expr1;
    }

    @Override
    protected double calculate(double variableValue) {
        return Math.log(argument.calculate(variableValue));
    }

    @Override
    protected ExprType getType() {
        return ExprType.LOG;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        Expression newArg = argument.calculate(variableMap);
        if(newArg.getType() == ExprType.NUM){
            return new Num(Math.log(((Num)newArg).getNumber()));
        }else {
            return copy();
        }
    }

    @Override
    protected Expression calculate(String varName, double value) {
        Expression expr = argument.calculate(varName, value);
        if(expr.getType() == ExprType.NUM){
            return new Num(Math.log(((Num)expr).getNumber()));
        }else {
            return copy();
        }
    }

    @Override
    public Expression copy() {
        return new Log(argument.copy());
    }

    @Override
    protected Expression derivative(String derivativeVariable) {
        return new Div(
                new Num(1),
                argument.copy()
        );
    }

    @Override
    protected Expression simplify() {
        Expression simple = argument.simplify();

        if(simple.getType() == ExprType.NUM && ((Num)simple).getNumber() == 1){
            return new Num(1);
        }else{
            return new Log(simple);
        }
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument.countVariables(variables);
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.SUB){
            return false;
        }else{
            return argument.equals(((Log)expression).argument);
        }
    }

    @Override
    public String toString() {
        return "lg("+ argument.toString()+")";
    }
}
