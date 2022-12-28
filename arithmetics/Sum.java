package arithmetics;

import java.util.*;

//сложение
public class Sum extends Expression{
    protected final Expression argument1;
    protected final Expression argument2;

    private Sum(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public static Expression create(Expression expr1, Expression expr2){ //входящие выражения копируются
        if((expr1).getClass() == Num.class && expr2.getClass() == Num.class){
            return new Num(((Num)expr1).number+((Num)expr2).number);
        }else if((expr1).getClass() == Num.class && ((Num)expr1).number == 0){
            return expr2.copy();
        }else if(expr2.getClass() == Num.class && ((Num)expr2).number == 0){
            return expr1.copy();
        }else{
            if(expr1.getClass() == Mul.class && expr2.getClass() == Mul.class){
                Expression arg1_1 = ((Mul) expr1).argument1;
                Expression arg1_2 = ((Mul) expr1).argument2;
                Expression arg2_1 = ((Mul) expr2).argument1;
                Expression arg2_2 = ((Mul) expr2).argument2;

                if(arg1_1.equals(arg2_1)){
                    return Mul.create(arg1_1, Sum.create(arg1_2, arg2_2));
                }else if(arg1_1.equals(arg2_2)){
                    return Mul.create(arg1_1, Sum.create(arg1_2, arg2_1));
                }else if(arg1_2.equals(arg2_1)){
                    return Mul.create(arg1_2, Sum.create(arg1_1, arg2_2));
                }else if(arg1_2.equals(arg2_2)){
                    return Mul.create(arg1_2, Sum.create(arg1_1, arg2_1));
                }else{
                    return new Sum(expr1.copy(), expr2.copy());
                }
            }
            else{
                return new Sum(expr1.copy(), expr2.copy());
            }
        }
    }

    public static Expression create(List<Expression> args){
        if(args.size() == 2){
            return Sum.create(args.get(0), args.get(1));
        }else {
            Expression subSum = Sum.create(args.get(0), args.get(1));
            for (int i = 2; i < args.size() - 1; ++i) { //1+2+3+4+5 -> (((1+2)+3)+4)+5, для удобства дальнейшего счета
                subSum = Sum.create(subSum, args.get(i));
            }
            return Sum.create(subSum, args.get(args.size() - 1));
        }
    }

    @Override
    protected double calc(double variableValue) {
        return argument1.calc(variableValue) + argument2.calc(variableValue);
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return Sum.create(
                argument1.calculate(variableMap),
                argument2.calculate(variableMap)
        );
    }

    @Override
    public Expression calculate(String varName, double value) {
        return Sum.create(
                argument1.calculate(varName, value),
                argument2.calculate(varName, value)
        );
    }

    @Override
    public Expression copy() {
        return Sum.create(argument1, argument2);
    }

    @Override
    public Expression derivative(String derivativeVariable) {
        return Sum.create(
                argument1.derivative(derivativeVariable),
                argument2.derivative(derivativeVariable)
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
            return argument1.equals(((Sum)object).argument1) &&
                    argument2.equals(((Sum)object).argument2);
        }
    }

    @Override
    public String toString() {
        return argument1.toString() + "+" + argument2.toString();
    }
}
