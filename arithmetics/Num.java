package arithmetics;

import java.util.Map;
import java.util.Set;

public class Num extends Expression {
    protected final double number;

    public Num(double number){
        this.number = number;
    }

    @Override
    protected double calc(double variableValue) {
        return number;
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        return copy();
    }

    @Override
    public Expression calculate(String varName, double value) {
        return copy();
    }

    @Override
    public Expression copy() {
        return new Num(number);
    }

    @Override
    public Expression derivative(String derivativeVariable) {
        return new Num(0);
    }

    @Override
    protected void countVariables(Set<String> variables) {
        //нет переменных
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
            return this.number == ((Num) object).number;
        }
    }

    @Override
    public String toString(){
        return String.valueOf(number);
    }
}
