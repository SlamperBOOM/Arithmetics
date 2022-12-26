package arithmetics;

import java.util.Map;
import java.util.Set;

public class Num extends Expression {
    private final double number;

    public Num(double number){
        this.number = number;
    }

    protected double getNumber(){
        return number;
    }

    @Override
    protected double calculate(double variableValue) {
        return number;
    }

    @Override
    protected ExprType getType() {
        return ExprType.NUM;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        return copy();
    }

    @Override
    protected Expression calculate(String varName, double value) {
        return copy();
    }

    @Override
    public Expression copy() {
        return new Num(number);
    }

    @Override
    protected Expression derivative(String derivativeVariable) {
        return new Num(0);
    }

    @Override
    protected Expression simplify() {
        return this;
    }

    @Override
    protected void countVariables(Set<String> variables) {
        //нет переменных
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.NUM){
            return false;
        }else {
            return this.number == ((Num) expression).number;
        }
    }

    @Override
    public String toString(){
        return String.valueOf(number);
    }
}
