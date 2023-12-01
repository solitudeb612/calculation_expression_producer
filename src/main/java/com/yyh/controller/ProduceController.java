package com.yyh.controller;

import com.yyh.entity.Condition;
import com.yyh.entity.Expression;
import com.yyh.entity.NatureProducer;
import com.yyh.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produce")
public class ProduceController {
    @Autowired
    NatureProducer natureProducer;


    @RequestMapping("/practice")
    public Result producePractice(@RequestBody Condition condition){
        List<Expression> expressions = natureProducer.produceExpressions(condition);
        return new Result(true, "操作成功", expressions);
    }

}
