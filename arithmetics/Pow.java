package arithmetics;

import java.util.Map;
import java.util.Set;

//возведение в степень
public class Pow extends Expression{
    private final Expression argument1;
    private final Expression argument2;

    public Pow(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    @Override
    protected double calculate(double variableValue) {
        return Math.pow(argument1.calculate(variableValue), argument2.calculate(variableValue));
    }

    @Override
    protected ExprType getType() {
        return ExprType.POW;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        Expression expr1 = argument1.calculate(variableMap);
        Expression expr2 = argument2.calculate(variableMap);
        if(expr1.getType() != ExprType.NUM || expr2.getType() != ExprType.NUM){
            return new Pow(expr1, expr2);
        }else{
            return new Num(Math.pow(((Num)expr1).getNumber(), ((Num)expr2).getNumber()));
        }
    }

    @Override
    protected Expression calculate(String varName, double value) {
        Expression expr1 = argument1.calculate(varName, value);
        Expression expr2 = argument2.calculate(varName, value);
        if(expr1.getType() != ExprType.NUM || expr2.getType() != ExprType.NUM){
            return new Pow(expr1, expr2);
        }else{
            return new Num(Math.pow(((Num)expr1).getNumber(), ((Num)expr2).getNumber()));
        }
    }

    @Override
    public Expression copy() {
        return new Pow(argument1.copy(), argument2.copy());
    }

    @Override
    protected Expression derivative(String derivativeVariable) { //используется общий вид производной степенной функции
        Expression der = new Mul(argument2.copy(), new Log(argument1.copy())).derivative(derivativeVariable);
        return new Mul(
                new Pow(argument1.copy(), argument2.copy()),
                der
        );
    }

    @Override
    protected Expression simplify() {
        Expression simple1 = argument1.simplify();
        Expression simple2 = argument2.simplify();

        if((simple1.getType() == ExprType.NUM && ((Num)simple1).getNumber() == 1) ||
                (simple2.getType() == ExprType.NUM && ((Num)simple2).getNumber() == 0)){
            return new Num(1);
        }else if(simple2.getType() == ExprType.NUM && ((Num)simple2).getNumber() == 1){
            return simple1;
        }else{
            return new Pow(simple1, simple2);
        }
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument1.countVariables(variables);
        argument2.countVariables(variables);
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.SUB){
            return false;
        }else{
            return argument1.equals(((Pow)expression).argument1) &&
                    argument2.equals(((Pow)expression).argument2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(argument1.getType() == ExprType.NUM
                || argument1.getType() == ExprType.VARIABLE
                || argument1.getType() == ExprType.LOG){
            builder.append(argument1).append("^");
        }else{
            builder.append("(").append(argument1).append(")^");
        }
        if(argument2.getType() == ExprType.NUM
                || argument2.getType() == ExprType.VARIABLE
                || argument2.getType() == ExprType.LOG){
            builder.append(argument2);
        }else{
            builder.append("(").append(argument1).append(")");
        }
        return builder.toString();
    }
}
