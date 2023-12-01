package com.yyh.entity;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.scene.input.KeyCode.O;

@Service
public class NatureProducer implements Producer {
    @Override
    public List<Expression> produceExpressions(Condition condition) {
        List<Expression> expressions = new ArrayList<>();
        for (int i = 0; i < condition.getGenerate_expression_number(); i++) {
            // 生成表达式并添加到列表中
            Expression expression = new NatureNumberExpression();
            Random random = new Random();
            List<Long> numbers = new ArrayList<>();
            for (int j = 0; j < condition.getExpression_number(); j++){
                long randomNumber = random.nextInt(condition.getRange()+1);  // 生成0到range之间的随机数
                numbers.add(randomNumber);  // 将随机数添加到numbers中
            }
            expression.setListNumbers(numbers);
            // 随机选择一个操作符
            String[] operations = condition.getOperation().toArray(new String[0]);
            String operation = operations[random.nextInt(operations.length)];
            List<String> operationList = new ArrayList<>();
            operationList.add(operation);
            expression.setListOperations(operationList);
            // 计算表达式的答案
            String answer = calculateAnswer(numbers, operationList);
            expression.setAnswer(answer);
            Checker.getMap().put(expression,O);
            boolean is_repeat = Checker.checkRepeat(expression);
            if(is_repeat){
                expressions.add(expression);
            }
        }
        return expressions;
    }

    public List<Expression> produceAddExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.ADDITION);
        condition.setOperation(operation);
        return produceExpressions(condition);
    }
    public List<Expression> produceSubtractionExpressions(Condition condition){
        ArrayList<String> operation = new ArrayList<>();
        operation.add(Operation.SUBTRACTION);
        condition.setOperation(operation);
        return produceExpressions(condition);
    }



    public String calculateAnswer(List<Long> listNumbers, List<String> listOperations){
        long result = listNumbers.get(0);
        for (int i = 0; i < listOperations.size(); i++) {
            String operation = listOperations.get(i);
            long nextNumber = listNumbers.get(i + 1);
            result = calculate(result, nextNumber, operation);
        }
        return String.valueOf(result);
    }

    private long calculate(long leftNumber, long rightNumber, String operation) {
        long answer = 0;
        switch (operation) {
            case "+":
                answer = leftNumber + rightNumber;
                break;
            case "-":
                answer = leftNumber - rightNumber;
                break;
            case "*":
                answer = leftNumber * rightNumber;
                break;
            case "/":
                if (rightNumber != 0) {
                    answer = leftNumber / rightNumber;
                } else {
                    // 处理除数为0的情况
                    answer = 0;
                }
                break;
        }
        return answer;
    }


}


