package com.yyh.POJO;

import com.yyh.constants.Operation;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO 优化栈内存变量
@Service
public class NatureProducer implements Producer {
    /**
     * 随机简单运算式产生器（算式中无括号）
     * @param condition
     * @return
     */
    @Override
    public List<Expression> produceSimpleExpression(Condition condition) {
        List<Expression> expressions = new ArrayList<>();
        Random random = new Random();
        String combinedExpression;
        String answer;
        boolean is_decimal;
        BigDecimal result;
        int id;

        while (expressions.size() < condition.getGenerate_expression_number()) {
            // 生成表达式并添加到列表中
            Expression expression = new NatureNumberExpression();
            List<String> numbers = new ArrayList<>();
            List<String> operationList = new ArrayList<>();

            for (int j = 0; j < condition.getExpression_number(); j++) {
                long randomNumber = random.nextInt(condition.getRange() + 1);  // 生成0到range之间的随机数
                numbers.add(String.valueOf(randomNumber));  // 将随机数添加到numbers中
            }
            expression.setListNumbers(numbers);

            // 随机选择操作符
            for (int k = 0; k < condition.getExpression_number() - 1; k++) {
                String[] operations = condition.getOperation().toArray(new String[0]);
                String operation = operations[random.nextInt(operations.length)];
                operationList.add(operation);
            }
            expression.setListOperations(operationList);


            try {
                combinedExpression = combinedExpression(expression);
                answer = calculate(combinedExpression);
                expression.setCombinedExpression(combinedExpression);
                expression.setAnswer(answer);

            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }

            // 查重，如果不重复则添加到列表中
            boolean is_repeat = Checker.checkRepeat(expression);
            Checker.getMap().put(expression, 1); // 假设1是一个特定的值，标记expression存在
//            if (!is_repeat) {
//                expressions.add(expression);
//            } else {
//                // 如果发现重复，从映射中移除这个表达式
//                Checker.getMap().remove(expression);
//            }


            if(!is_repeat){
                try {
                    result = new BigDecimal(answer); // 使用字符串构造函数创建BigDecimal对象
                    is_decimal = Checker.checkResultPrecision(result); // 调用checkResultPrecision方法
                    if (!is_decimal){
                        id = expressions.size() + 1;
                        expression.setId(id);
                        expressions.add(expression);

                    }
                } catch (NumberFormatException e) {

                    e.printStackTrace();
                    is_decimal = false;
                }
            }

        }
        return expressions;
    }


    /**
     * 加法运算式产生器
     * @param condition
     * @return
     */
    public List<Expression> produceAddExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.ADDITION);
        condition.setOperation(operation);
        return produceSimpleExpression(condition);
    }

    /**
     * 减法运算式产生器
     * @param condition
     * @return
     */
    public List<Expression> produceSubtractionExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.SUBTRACTION);
        condition.setOperation(operation);
        return produceSimpleExpression(condition);
    }


    /**
     * 乘法运算式生成器
     * @param condition
     * @return
     */
    public List<Expression> produceMultiplicationExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.MULTIPLICATION);
        condition.setOperation(operation);
        return produceSimpleExpression(condition);
    }

    /**
     * 除法运算式生成器
     * @param condition
     * @return
     */
    public List<Expression> produceDivisionExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.DIVISION);
        condition.setOperation(operation);
        return produceSimpleExpression(condition);
    }

    /**
     * 从左到右计算，不考虑优先级
     * @param listNumbers
     * @param listOperations
     * @param precision
     * @return
     */
    @Deprecated
    public String calculateAnswer(List<String> listNumbers, List<String> listOperations, int precision) {
        BigDecimal result = new BigDecimal(listNumbers.get(0));
        for (int i = 0; i < listOperations.size(); i++) {
            String operation = listOperations.get(i);
            BigDecimal nextNumber = new BigDecimal(listNumbers.get(i + 1));
            result = calculate(result, nextNumber, operation, precision);
        }
        return result.toString();
    }

    /**
     * 计算两个数的运算结果
     * @param leftNumber 左操作数
     * @param rightNumber 右操作数
     * @param operation 运算符
     * @param precision 计算结果的精度
     * @return
     */
    public BigDecimal calculate(BigDecimal leftNumber, BigDecimal rightNumber, String operation, int precision) {
        BigDecimal answer = BigDecimal.ZERO;
        switch(operation){
            case "+":
                answer = leftNumber.add(rightNumber);
                break;
            case "-":
                answer = leftNumber.subtract(rightNumber);
                break;
            case "*":
                answer = leftNumber.multiply(rightNumber);
                break;
            case "/":
                if(!rightNumber.equals(BigDecimal.ZERO)){
                    answer = leftNumber.divide(rightNumber, precision, BigDecimal.ROUND_HALF_UP);
                }else{
                    //处理除数为0的情况
                    answer = BigDecimal.ZERO;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operation: "+ operation);
        }
        return answer;
    }



    public String calculate(String cominedExpression) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        Object result = engine.eval(cominedExpression.toString());
        return String.valueOf(result); // 使用 String.valueOf() 安全转换为 String
    }

    public List<String> combinedExpressions(List<Expression> expressionsList) throws ScriptException {
        List<String> combinedExpressions = new ArrayList<>();
        for(Expression expression : expressionsList){
            StringBuilder expressionBuilder = new StringBuilder();
            List<String> numbers = expression.getListNumbers();
            List<String> operations = expression.getListOperations();
            for(int i = 0; i < numbers.size(); i++){
                expressionBuilder.append(numbers.get(i));
                if(i < operations.size()){
                    expressionBuilder.append(" ").append(operations.get(i)).append(" ");
                }
            }
            combinedExpressions.add(expressionBuilder.toString());
        }
        return combinedExpressions;
    }

    public String combinedExpression(Expression expression) throws ScriptException {
        StringBuilder expressionBuilder = new StringBuilder();
        List<String> numbers = expression.getListNumbers();
        List<String> operations = expression.getListOperations();
        for(int i = 0; i < numbers.size(); i++){
            expressionBuilder.append(numbers.get(i));
            if(i < operations.size()){
                expressionBuilder.append(" ").append(operations.get(i)).append(" ");
            }
        }
        return String.valueOf(expressionBuilder);
    }


}


