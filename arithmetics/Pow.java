package arithmetics;

import java.util.Map;
import java.util.Set;

//возведение в степень
public class Pow extends Expression{
    protected final Expression argument1;
    protected final Expression argument2;

    private Pow(Expression expr1, Expression expr2){
        argument1 = expr1;
        argument2 = expr2;
    }

    public static Expression create(Expression expr1, Expression expr2){ //входящие выражения копируются
        if(expr1.getClass() == Num.class && expr2.getClass() == Num.class){
            return new Num(Math.pow(((Num)expr1).number, ((Num)expr2).number));
        } else if((expr1.getClass() == Num.class && ((Num)expr1).number == 1) ||
                (expr2.getClass() == Num.class && ((Num)expr2).number == 0)){
            return new Num(1);
        }else if(expr2.getClass() == Num.class && ((Num)expr2).number == 1){
            return expr1.copy();
        }else{
            return new Pow(expr1.copy(), expr2.copy());
        }
    }

    @Override
    protected double calc(double variableValue) {
        return Math.pow(argument1.calc(variableValue), argument2.calc(variableValue));
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return Pow.create(
                argument1.calculate(variableMap),
                argument2.calculate(variableMap)
        );
    }

    @Override
    public Expression calculate(String varName, double value) {
        return Pow.create(
                argument1.calculate(varName, value),
                argument2.calculate(varName, value)
        );
    }

    @Override
    public Expression copy() {
        return Pow.create(argument1, argument2);
    }

    @Override
    public Expression derivative(String derivativeVariable) { //используется общий вид производной степенной функции
        Expression der = Mul.create(argument2, Log.create(argument1)).derivative(derivativeVariable);
        return Mul.create(
                Pow.create(argument1, argument2),
                der
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
            return argument1.equals(((Pow)object).argument1) &&
                    argument2.equals(((Pow)object).argument2);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if(argument1.getClass() == Num.class
                || argument1.getClass() == Variable.class
                || argument1.getClass() == Log.class){
            builder.append(argument1).append("^");
        }else{
            builder.append("(").append(argument1).append(")^");
        }
        if(argument2.getClass() == Num.class
                || argument2.getClass() == Variable.class
                || argument2.getClass() == Log.class){
            builder.append(argument2);
        }else{
            builder.append("(").append(argument1).append(")");
        }
        return builder.toString();
    }
}
