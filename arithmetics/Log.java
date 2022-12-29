package arithmetics;

import java.util.Map;
import java.util.Set;

//натуральный логарифм
public class Log extends Expression{
    private final Expression argument;

    private Log(Expression expr1){
        argument = expr1;
    }

    public static Expression create(Expression expr){
        if(expr.getClass() == Num.class && ((Num)expr).number == 1){
            return new Num(0);
        } else if(expr.getClass() == Num.class){
            return new Num(Math.log(((Num)expr).number));
        }else{
            return new Log(expr.copy());
        }
    }

    @Override
    public double calc(double variableValue) {
        return Math.log(argument.calc(variableValue));
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return Log.create(argument.calculate(variableMap));
    }

    @Override
    public Expression calculate(String varName, double value) {
        return Log.create(argument.calculate(varName, value));
    }

    @Override
    public Expression copy() {
        return Log.create(argument);
    }

    @Override
    public Expression derivative(String derivativeVariable) {
        return Div.create(
                argument.derivative(derivativeVariable),
                argument
        );
    }

    @Override
    protected void countVariables(Set<String> variables) {
        argument.countVariables(variables);
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
            return argument.equals(((Log)object).argument);
        }
    }

    @Override
    public String toString() {
        return "lg("+ argument.toString()+")";
    }
}
