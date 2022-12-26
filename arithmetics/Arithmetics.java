package arithmetics;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//Класс хранит в себе выражение и работает с ним целиком.
//Создан для удобства работы с выражениями и для логического разделения операций
public class Arithmetics {
    private Set<String> variableSet = new HashSet<>();
    private Expression expression; //здесь только набор операций, чисел и переменных

    public static Arithmetics createExpression(Expression expr){ // берем уже созданное выражение
        Arithmetics newExpr = new Arithmetics();
        newExpr.expression = expr;
        newExpr.addVariables();
        return newExpr;
    }

    private void addVariables(){ // используется для добавления имеющихся переменных, если выражение добавлялось целиком
        expression.countVariables(variableSet);
    }

    public static Arithmetics parseExpression(String expr){ //парсим выражение из строки
        Arithmetics newExpr = new Arithmetics();
        newExpr.expression = parseExpr(expr);
        return null;
    }

    private static Expression parseExpr(String expr){
        //ищем подвыражения в скобках
        int firstBracketPos = expr.indexOf('(');
        int lastBracketPos = expr.lastIndexOf(')');
        Expression subExpr = null;
        if(firstBracketPos != -1){
            subExpr = parseExpr(expr.substring(firstBracketPos+1, lastBracketPos));
        }
        //ищем возведение в степень

        return null;
    }

    public Arithmetics cloneExpression(){ //копирование
        Arithmetics newExpr = new Arithmetics();
        newExpr.expression = this.expression.copy();
        newExpr.variableSet = new HashSet<>();
        newExpr.variableSet.addAll(this.variableSet);
        return newExpr;
    }

    public void simplify(){ //простые упрощения
        expression = expression.simplify();
    }

    public Arithmetics derivative(String derivativeVariable){ //считаем производную по переменной
        return Arithmetics.createExpression(expression.derivative(derivativeVariable));
    }

    public double calculateExact(double value){ //считаем значение, учитывая, что у нас одна переменная
        if(variableSet.size() > 1){
            throw new ArithmeticException("Слишком много переменных");
        }else {
            return expression.calculate(value);
        }
    }

    public Arithmetics calculateWithVariable(String varName, double value){ //считаем новое выражение, используя значение нескольких переменных
        Arithmetics newExpr = this.cloneExpression();
        newExpr.expression = newExpr.expression.calculate(varName, value);
        return newExpr;
    }

    public Arithmetics calculateWithVariables(Map<String, Double> variableMap){ //считаем новое выражение, используя значения нескольких переменных
        Arithmetics newExpr = this.cloneExpression();
        newExpr.expression = newExpr.expression.calculate(variableMap);
        return newExpr;
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
