package arithmetics;

import java.util.*;

//умножение
public class Mul extends Expression{
    protected final Expression argument1;
    protected final Expression argument2;

    private Mul(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public static Expression create(Expression expr1, Expression expr2){ //входящие выражения копируются
        if(expr1.getClass() == Num.class && expr2.getClass() == Num.class){
            return new Num(((Num) expr1).number * ((Num) expr2).number);
        } else if((expr1.getClass() == Num.class && ((Num)expr1).number == 0)
                || (expr2.getClass() == Num.class && ((Num)expr2).number == 0)){
            return new Num(0);
        } else if(expr1.getClass() == Num.class && ((Num)expr1).number == 1){
            return expr2.copy();
        }else if(expr2.getClass() == Num.class && ((Num)expr2).number == 1){
            return expr1.copy();
        }else{
            return new Mul(expr1.copy(), expr2.copy());
        }
    }

    public static Expression create(List<Expression> args){
        if(args.size() == 2){
            return Mul.create(args.get(0), args.get(1));
        }else {
            Expression subMul = Mul.create(args.get(0), args.get(1));
            for (int i = 2; i < args.size() - 1; ++i) { //1*2*3*4*5 -> (((1*2)*3)*4)*5, для удобства дальнейшего счета
                subMul = Mul.create(subMul, args.get(i));
            }
            return Mul.create(subMul, args.get(args.size() - 1));
        }
    }

    @Override
    protected double calc(double variableValue) {
        return argument1.calc(variableValue) * argument2.calc(variableValue);
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return Mul.create(
                argument1.calculate(variableMap),
                argument2.calculate(variableMap)
        );
    }

    @Override
    public Expression calculate(String varName, double value) {
        return Mul.create(
                argument1.calculate(varName, value),
                argument2.calculate(varName, value));
    }

    @Override
    public Expression copy() {
        return Mul.create(argument1, argument2);
    }

    @Override
    public Expression derivative(String derivativeVariable) {
        return Sum.create(
                Mul.create(
                        argument1.derivative(derivativeVariable),
                        argument2
                ),
                Mul.create(
                        argument1,
                        argument2.derivative(derivativeVariable)
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
        if(this.getClass() != object.getClass()){
            return false;
        }else{
            return argument1.equals(((Mul)object).argument1) &&
                    argument2.equals(((Mul)object).argument2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(argument1.getClass() == Num.class || argument1.getClass() == Mul.class || argument1.getClass() == Div.class){
            builder.append(argument1).append("*");
        }else{
            builder.append("(").append(argument1).append(")*");
        }
        if(argument2.getClass() == Num.class || argument2.getClass() == Mul.class || argument2.getClass() == Div.class){
            builder.append(argument2);
        }else{
            builder.append("(").append(argument2).append(")");
        }
        return builder.toString();
    }
}
