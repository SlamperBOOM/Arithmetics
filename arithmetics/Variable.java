package arithmetics;

import java.util.Map;
import java.util.Set;

public class Variable extends Expression{
    protected final String varName;

    public Variable(String name){
        varName = name;
    }

    @Override
    protected double calc(double variableValue) {
        return variableValue;
    }

    @Override
    public Expression calculate(Map<String, Double> variableMap) {
        if(variableMap.containsKey(varName)){
            return new Num(variableMap.get(varName));
        }else {
            return copy();
        }
    }

    @Override
    public Expression calculate(String varName, double value) {
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
    public Expression derivative(String derivativeVariable) {
        if(this.varName.equals(derivativeVariable)){
            return new Num(1);
        }else{
            return new Num(0);
        }
    }

    @Override
    protected void countVariables(Set<String> variables) {
        variables.add(varName);
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
        }else {
            return this.varName.equals(((Variable)object).varName);
        }
    }

    @Override
    public String toString(){
        return varName;
    }
}
