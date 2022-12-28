import arithmetics.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        /*Expression expression =
                Mul.create(
                        Sum.create(
                                Div.create(
                                        new Variable("x"),
                                        new Num(2)
                                ),
                                new Variable("x")
                        ),
                        Pow.create(
                                Log.create(new Variable("y")),
                                new Num(5)
                        )
                )
        ;*/
        Expression expression1 = new Variable("x").divide(new Num(2)).add(new Variable("x"));
        Expression expression2 = Log.create(new Variable("y")).pow(new Num(5));
        Expression expression = expression1.multiply(expression2);
        System.out.println(expression);
        try {
            /*System.out.println("Считаем с аргументом 4, подразумевая, что у нас одна переменная");
            System.out.println(expression.calculateExact(4));*/
            System.out.println("Считаем с аргументами \"y\" и 4, остальные переменные остаются");
            System.out.println(expression.calculate("y", 4));
            System.out.println("Считаем с аргументами \"x\" и 5, остальные переменные остаются");
            System.out.println(expression.calculate("x", 5));
            System.out.println("Добавляем в Map значение переменных (\"y\", 4) и считаем");
            Map<String, Double> map = new HashMap<>();
            map.put("y", 4.0);
            System.out.println(expression.calculate(map));
        }catch (ArithmeticException e){
            System.err.println(e.getMessage());
        }
        System.out.println("Производная по y");
        Expression der = expression.derivative("y");
        System.out.println(der);
    }
}
