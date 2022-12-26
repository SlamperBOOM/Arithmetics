package arithmetics;

import java.util.Map;
import java.util.Set;

public abstract class Expression {
    abstract protected double calculate(double variableValue); //считаем значение, учитывая, что у нас одна переменная
    abstract protected ExprType getType();
    abstract protected Expression calculate(Map<String, Double> variableMap); //считаем значение с несколькими переменными
    abstract protected Expression calculate(String varName, double value); //считаем выражение с одной переменной, остальные считаются const
    abstract public Expression copy(); //копируем выражение
    abstract protected Expression derivative(String derivativeVariable); //производная
    abstract protected Expression simplify(); //упрощение
    abstract protected void countVariables(Set<String> variables); //используется для подсчета переменных
    abstract protected boolean equals(Expression expression);
}
