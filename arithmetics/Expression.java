package arithmetics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Expression {
    abstract protected double calc(double variableValue); //считаем значение, учитывая, что у нас одна переменная
    abstract public Expression calculate(Map<String, Double> variableMap); //считаем значение с несколькими переменными
    abstract public Expression calculate(String varName, double value); //считаем выражение с одной переменной, остальные считаются const
    abstract public Expression copy(); //копируем выражение
    abstract public Expression derivative(String derivativeVariable); //производная
    abstract protected void countVariables(Set<String> variables); //используется для подсчета переменных

    abstract public Expression add(Expression otherExpression);
    abstract public Expression subtract(Expression otherExpression);
    abstract public Expression multiply(Expression otherExpression);
    abstract public Expression divide(Expression otherExpression);
    abstract public Expression pow(Expression otherExpression);

    public double calculate(double variableValue){
        Set<String> varsName = countVariables();
        if(varsName.size() > 1){
            throw new ArithmeticException("Слишком много переменных");
        }else{
            return calc(variableValue);
        }
    }

    public Set<String> countVariables(){
        Set<String> varsName = new HashSet<>();
        countVariables(varsName);
        return varsName;
    }
}
