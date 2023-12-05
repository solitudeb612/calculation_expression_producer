//import com.yyh.POJO.*;
//import org.junit.Test;
//
//import javax.script.ScriptEngine;
//import javax.script.ScriptEngineManager;
//import javax.script.ScriptException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static javafx.scene.input.KeyCode.O;
//
//
//public class MyTest {
//    /**
//     * 测试随机产生简单算式（无括号）
//     */
//    @Test
//    public void testProduceSimpleExpressions() throws ScriptException {
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        List<Expression> expressions = natureProducer.produceSimpleExpression(condition);
//        System.out.println(expressions);
//    }
//
//
//
//    /**
//     * 测试加法产生器
//     */
//    @Test
//    public void testProduceAddExpressions(){
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        List<Expression> expressions = natureProducer.produceAddExpressions(condition);
//        System.out.println(expressions);
//
//    }
//
//
//    /**
//     * 测试减法产生器
//     */
//    @Test
//    public void testProduceSubtractionExpressions(){
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        List<Expression> expressions = natureProducer.produceSubtractionExpressions(condition);
//        System.out.println(expressions);
//
//    }
//
//    /**
//     * 测试乘法产生器
//     */
//    @Test
//    public void testProduceMultiplicationExpressions(){
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        List<Expression> expressions = natureProducer.produceMultiplicationExpressions(condition);
//        System.out.println(expressions);
//    }
//
//    /**
//     * 测试除法产生器
//     */
//    @Test
//    public void testProduceDivisionExpressions(){
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setGenerate_expression_number(1000);
//        condition.setExpression_number(2);
//        condition.setRange(1000);
//        List<Expression> expressions = natureProducer.produceDivisionExpressions(condition);
//        System.out.println(expressions);
//    }
//
//    /**
//     * 测试生成100以内的加法
//     * @throws ScriptException
//     */
//    @Test
//    public void test100add(){
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        condition.setRange(100);
//        condition.setExpression_number(2);
//        List<Expression> expressions = natureProducer.produceAddExpressions(condition);
//        System.out.println(expressions);
//    }
//
//    @Test
//    public void test() throws ScriptException {
//        NatureProducer natureProducer = new NatureProducer();
//        Condition condition = new Condition();
//        condition.setExpression_number(3);
//        List<Expression> expressions = natureProducer.produceSimpleExpression(condition);
//    }
//
//    @Test
//    public void test1(){
//        String s = "2 + 3 * 5";
//
//        System.out.println(2 + 3 * 5);
//    }
//    @Test
//    public void test2() throws ScriptException {
//          ScriptEngineManager mgr = new ScriptEngineManager();
//          ScriptEngine engine = mgr.getEngineByName("JavaScript");
//
//          String expression = "1/3+4*(5+6)";
//          Object result = engine.eval(expression);
//
//          System.out.println(expression + " = " + result);
//    }
//    /**
//     * 测试float和BigDecimal
//     */
//    @Test
//    public void testBigDemical(){
//        //float结果都是小数
//        float a = 21f;
//        System.out.println(10 * a); //210.0
//
//        //BigDecimal结果是整数就是整数
//        BigDecimal bigDecimal = new BigDecimal("21");
//        BigDecimal result = bigDecimal.multiply(new BigDecimal("10"));
//        System.out.println(result); //210
//
//        //BigDecimal结果是小数就是小数
//        BigDecimal bigDecimal2 = new BigDecimal("21");
//        BigDecimal result2 = bigDecimal.divide(new BigDecimal("10"));
//        System.out.println(result2); // 2.1
//
//        BigDecimal big = new BigDecimal(8.8);
//        BigDecimal big1 = new BigDecimal(2);
//        System.out.println(big.divide(big1));//不精确的值
//        System.out.println(big.divide(big1).floatValue());//精确的值 4.4
//
//    }
//
//    /**
//     * 测试1+2和2+1是否相同
//     */
//    @Test
//    public void test3(){
//        NatureProducer natureProducer = new NatureProducer();
//        NatureNumberExpression natureNumberExpression1 = new NatureNumberExpression();
//        List<String> numbers1 = new ArrayList<>();
//        List<String> operations1 = new ArrayList<>();
//        numbers1.add("1");
//        numbers1.add("2");
//        operations1.add("+");
//        String a = "1+2";
//        natureNumberExpression1.setListNumbers(numbers1);
//        natureNumberExpression1.setListOperations(operations1);
//        natureNumberExpression1.setCombinedExpression(a);
//
//
//
//
//        NatureNumberExpression natureNumberExpression2 = new NatureNumberExpression();
//        List<String> numbers2 = new ArrayList<>();
//        List<String> operations2 = new ArrayList<>();
//        numbers2.add("2");
//        numbers2.add("1");
//        operations2.add("+");
//        String b = "2+1";
//        natureNumberExpression2.setListNumbers(numbers2);
//        natureNumberExpression2.setListOperations(operations2);
//        natureNumberExpression2.setCombinedExpression(b);
//
//
//        System.out.println(natureNumberExpression1);
//        System.out.println(natureNumberExpression2);
//
//        Checker.getMap().put(natureNumberExpression1,O);
//        boolean is_repeat1 = Checker.checkRepeat(natureNumberExpression2);
//        System.out.println(is_repeat1);
//
//
////        Checker.getMap().put(,O);
////        boolean is_repeat = Checker.checkRepeat(expression);
//    }
//
//    /**
//     * 测试
//     */
//    public void test4(){
//        NatureProducer natureProducer = new NatureProducer();
//    }
//}
