package arithmetics;

import java.util.*;

//деление
public class Div extends Expression{
    protected final Expression argument1;
    protected final Expression argument2;

    private Div(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public static Expression create(Expression expr1, Expression expr2){ //входящие выражения копируются
        if((expr1.getClass() == Num.class && ((Num)expr1).number == 0) &&
                (expr2.getClass() == Num.class && ((Num)expr2).number != 0)){
            return new Num(0);
        } else if(expr1.getClass() == Num.class && expr2.getClass() == Num.class &&
                ((Num)expr2).number == 0){
            throw new ArithmeticException("Деление на 0");
        }else if(expr1.getClass() == Num.class && expr2.getClass() == Num.class){
            return new Num(((Num)expr1).number / ((Num)expr2).number);
        }
        else if(expr2.getClass() == Num.class && ((Num)expr2).number == 1){
            return expr1.copy();
        }else{
            return new Div(expr1.copy(), expr2.copy());
        }
    }

    @Override
    public double calc(double variableValue) {
        double arg2 = argument2.calc(variableValue);
        if(arg2 == 0){
            throw new ArithmeticException("Деление на 0");
        }
        return argument1.calc(variableValue) / arg2;
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return Div.create(
                argument1.calculate(variableMap),
                argument2.calculate(variableMap)
        );
    }

    @Override
    public Expression calculate(String varName, double value) {
        return Div.create(
                argument1.calculate(varName, value),
                argument2.calculate(varName, value)
        );
    }

    @Override
    public Expression copy() {
        return Div.create(argument1, argument2);
    }

    @Override
    public Expression derivative(String derivativeVariable) {
        return Div.create(
                Sub.create(
                        Mul.create(
                                argument1.derivative(derivativeVariable),
                                argument2
                        ),
                        Mul.create(
                                argument1,
                                argument2.derivative(derivativeVariable)
                        )
                ),
                Pow.create(
                        argument2,
                        new Num(2)
                )
        );
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument1.countVariables(variables);
        argument2.countVariables(variables);
    }

    @Override
    public Expression add(Expression otherExpression) {
        return Sum.create(this, otherExpression);
    }

    @Override
    public Expression subtract(Expression otherExpression) {
        return Sub.create(this, otherExpression);
    }

    @Override
    public Expression multiply(Expression otherExpression) {
        return Mul.create(this, otherExpression);
    }

    @Override
    public Expression divide(Expression otherExpression) {
        return Div.create(this, otherExpression);
    }

    @Override
    public Expression pow(Expression otherExpression) {
        return Pow.create(this, otherExpression);
    }

    @Override
    public boolean equals(Object object) {
        if(object.getClass() != this.getClass()){
            return false;
        }else{
            return argument1.equals(((Div)object).argument1) &&
                    argument2.equals(((Div)object).argument2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(argument1.getClass() == Num.class
                || argument1.getClass() == Variable.class
                || argument1.getClass() == Mul.class
                || argument1.getClass() == Div.class){
            builder.append(argument1).append("/");
        }else{
            builder.append("(").append(argument1).append(")/");
        }
        if(argument2.getClass() == Num.class
                || argument2.getClass() == Variable.class
                || argument2.getClass() == Mul.class
                || argument2.getClass() == Div.class){
            builder.append(argument2);
        }else{
            builder.append("(").append(argument2).append(")");
        }
        return builder.toString();
    }
}
