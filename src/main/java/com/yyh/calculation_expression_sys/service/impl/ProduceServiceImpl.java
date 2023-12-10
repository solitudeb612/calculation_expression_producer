package com.yyh.calculation_expression_sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yyh.POJO.Condition;
import com.yyh.POJO.Expression;
import com.yyh.POJO.NatureProducer;
import com.yyh.calculation_expression_sys.entity.Practice;
import com.yyh.calculation_expression_sys.entity.vo.NoAnswerExpressionVO;
import com.yyh.calculation_expression_sys.mapper.ProduceMapper;
import com.yyh.calculation_expression_sys.service.ProduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class ProduceServiceImpl extends ServiceImpl<ProduceMapper, Practice> implements ProduceService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    NatureProducer natureProducer;
//    @Autowired
//    ProduceMapper produceMapper; 这里用this.BaseMapper 就不用注入Mapper了，如果需要注入，请在ProduceMapper加上@Mapper注解

    @Override
    public Map<String, Object> redisSavePractice(Practice practice, String token){

        // 确保表达式列表不为空并且所有表达式都有唯一的id
        if (practice.getExpressions() == null || practice.getExpressions().isEmpty()) {
            throw new IllegalArgumentException("Expressions list is null or empty.");
        }

        // 使用用户登录的token作为本次练习的token
        String key = "practice:" + token;

        // 存储到Redis,并设置过期时间,例如30分钟
        long timeout = 30; // 过期时间长度
        TimeUnit timeUnit = TimeUnit.MINUTES; // 过期时间单位

        redisTemplate.opsForValue().set(key, practice, timeout, timeUnit);

        // 创建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("key", key);
        result.put("practice", practice); // 可选，根据需要返回序列化前的数据
        return result;
    }

    @Override
    public Practice judgePractice(Practice practice, String token) {
        // 如果提交的表达式列表为空，返回空列表或错误
        if (practice.getExpressions() == null || practice.getExpressions().isEmpty()) {
            throw new IllegalArgumentException("Submitted expressions list is null or empty.");
        }

        // 构造在Redis中存储表达式的key
        String key = "practice:" + token;

        // 从Redis获取正确答案的表达式列表

        Object obj = redisTemplate.opsForValue().get(key);
        System.out.println();
        Practice practice_answer = JSON.parseObject(JSON.toJSONString(obj), Practice.class);//1.将obj转换成json字符串，2.将json字符串转换成User对象
        System.out.println(practice_answer);
        // 判断正确答案的表达式列表是否为空
        if (practice_answer.getExpressions() == null || practice_answer.getExpressions().isEmpty()) {
            throw new IllegalArgumentException("No expressions found for the token: " + token);
        }

        // 创建一个正确答案的map（id为key,expression的答案为value）
        Map<String, Expression> correctExpressionMap = new HashMap<>();
        for (Expression expression : practice_answer.getExpressions()) {
            correctExpressionMap.put(String.valueOf(expression.getId()) , expression);
        }

        // 遍历提交的表达式列表，判断对错
        for (Expression submittedExpression : practice.getExpressions()) {
            // 检查提交的表达式的ID是否存在于正确答案的map中
            Expression correctExpression = correctExpressionMap.get(String.valueOf(submittedExpression.getId()));

            if (correctExpression != null) {
                // 如果存在，比较答案，并设置is_correct属性
                boolean isCorrect = correctExpression.getAnswer().equals(submittedExpression.getAnswer());
                submittedExpression.setIsCorrect(isCorrect);
            } else {
                // 如果不存在，设置为不正确
                submittedExpression.setIsCorrect(false);
            }
        }

        // 返回判断后的表达式列表
        return practice;
    }



    @Override
    public Practice producePractice(Condition condition, String token) {
        List<Expression> expressions = null;
        Practice practice = new Practice();
        String practice_title = null;
        if(condition.getOperation().stream().allMatch(s -> s.equals("+"))){
            expressions = natureProducer.produceAddExpressions(condition);
            practice_title = "加法练习";
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("-"))){
            expressions = natureProducer.produceSubtractionExpressions(condition);
            practice_title = "减法练习";
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("*"))){
            expressions = natureProducer.produceMultiplicationExpressions(condition);
            practice_title = "乘法练习";
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("/"))){
            expressions = natureProducer.produceDivisionExpressions(condition);
            practice_title = "除法练习";
        }

        int range = condition.getRange();
        practice_title = range + "以内的" + practice_title;
        practice.setPracticeTitle(practice_title);
        practice.setExpressions(expressions);
        practice.setStartTime(new Date());
        return practice;
    }

    @Override
    public Boolean dbSavePractice(Practice practice) {
        boolean flag = this.baseMapper.dbSavePractice(practice);
        return null;
    }

    @Override
    public List<Expression> offAnswer(Practice practice) {
        for (Expression expression : practice.getExpressions()) {
            expression.setAnswer(null);
        }
        return practice.getExpressions();
    }

//    @Override
//    public List<NoAnswerExpressionVO> transfer(List<Expression> expressionList) {
//        List<NoAnswerExpressionVO> noAnswerExpressionVOList = new ArrayList<>();
//        NoAnswerExpressionVO noAnswerExpressionVO = new NoAnswerExpressionVO();
//        for (Expression expression : expressionList) {
//            noAnswerExpressionVO.setExpressionId(expression.getId());
//            noAnswerExpressionVO.setExpressionContent((String) expression.getAnswer());
//            noAnswerExpressionVOList.add(noAnswerExpressionVO);
//        }
//        return noAnswerExpressionVOList;
//    }

}
