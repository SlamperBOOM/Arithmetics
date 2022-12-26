package arithmetics;

import java.util.Map;
import java.util.Set;

public class Variable extends Expression{
    private final String varName;

    public Variable(String name){
        varName = name;
    }

    @Override
    protected double calculate(double variableValue) {
        return variableValue;
    }

    @Override
    protected ExprType getType() {
        return ExprType.VARIABLE;
    }

    @Override
    protected Expression calculate(Map<String, Double> variableMap) {
        if(variableMap.containsKey(varName)){
            return new Num(variableMap.get(varName));
        }else {
            return copy();
        }
    }

    @Override
    protected Expression calculate(String varName, double value) {
        if(varName.equals(this.varName)){
            return new Num(value);
        }else{
            return copy();
        }
    }

    @Override
    public Expression copy() {
        return new Variable(varName);
    }

    @Override
    protected Expression derivative(String derivativeVariable) {
        if(this.varName.equals(derivativeVariable)){
            return new Num(1);
        }else{
            return new Num(0);
        }
    }

    @Override
    protected Expression simplify() {
        return this;
    }

    @Override
    protected void countVariables(Set<String> variables) {
        variables.add(varName);
    }

    @Override
    protected boolean equals(Expression expression) {
        if(expression.getType() != ExprType.VARIABLE){
            return false;
        }else {
            return this.varName.equals(((Variable)expression).varName);
        }
    }

    @Override
    public String toString(){
        return varName;
    }
}
