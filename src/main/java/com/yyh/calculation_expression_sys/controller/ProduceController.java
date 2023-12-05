package com.yyh.calculation_expression_sys.controller;

import com.yyh.POJO.Condition;
import com.yyh.POJO.Expression;
import com.yyh.POJO.NatureProducer;
//import com.yyh.calculation_expression_sys.service.ProduceService;
import com.yyh.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produce")
public class ProduceController {
    @Autowired
    NatureProducer natureProducer;
//    @Autowired
//    ProduceService produceService;


    @PostMapping("/practice")
    public Result producePractice(@RequestBody Condition condition){
        List<Expression> expressions = null;
        if(condition.getOperation().stream().allMatch(s -> s.equals("+"))){
            System.out.println("hhh");
            expressions = natureProducer.produceAddExpressions(condition);
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("-"))){
            expressions = natureProducer.produceSubtractionExpressions(condition);
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("*"))){
            expressions = natureProducer.produceMultiplicationExpressions(condition);
        }
        else if(condition.getOperation().stream().allMatch(s -> s.equals("/"))){
            expressions = natureProducer.produceDivisionExpressions(condition);
        }

        return new Result(20000, "操作成功", expressions);
    }

//    @PostMapping("/judge")
//    public Result judgePractice(@RequestBody Expression expression){
//
//    }
}
