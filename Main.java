import arithmetics.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        Arithmetics expression = Arithmetics.createExpression(new Mul(
                new Sum(
                        new Div(
                                new Variable("x"),
                                new Num(2)
                        ),
                        new Variable("x")
                ),
                new Pow(
                        new Log(new Variable("y")),
                        new Num(5))));
        System.out.println(expression);
        try {
            /*System.out.println("Считаем с аргументом 4, подразумевая, что у нас одна переменная");
            System.out.println(expression.calculateExact(4));*/
            System.out.println("Считаем с аргументами \"y\" и 4, остальные переменные остаются");
            System.out.println(expression.calculateWithVariable("y", 4));
            System.out.println("Считаем с аргументами \"x\" и 5, остальные переменные остаются");
            System.out.println(expression.calculateWithVariable("x", 5));
            System.out.println("Добавляем в Map значение переменных (\"y\", 4) и считаем");
            Map<String, Double> map = new HashMap<>();
            map.put("y", 4.0);
            System.out.println(expression.calculateWithVariables(map));
        }catch (ArithmeticException e){
            System.err.println(e.getMessage());
        }
        System.out.println("Производная по y");
        Arithmetics der = expression.derivative("y");
        der.simplify();
        der = der.calculateWithVariables(new HashMap<>());
        der.simplify();
        System.out.println(der);
    }
}
