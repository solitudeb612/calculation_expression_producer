package com.yyh.calculation_expression_sys.controller;

import com.yyh.POJO.Condition;
import com.yyh.POJO.Expression;
import com.yyh.calculation_expression_sys.entity.Practice;
import com.yyh.calculation_expression_sys.service.ProduceService;
import com.yyh.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produce")
@CrossOrigin//跨域
public class ProduceController {

    @Autowired
    ProduceService produceService;


    @PostMapping("/practice")
    public Result producePractice(@RequestBody Condition condition, @RequestParam("token") String token){
        //根据条件生成题目和答案
        Practice practice = produceService.producePractice(condition, token);
        //缓存题目信息，方便批改
        produceService.redisSavePractice(practice, token);
        //把答案置为null传给前端
        List<Expression> expressions = produceService.offAnswer(practice);
        practice.setExpressions(expressions);
        return new Result(20000, "操作成功", practice);
    }

    @PostMapping("/judge")
    public Result judgePractice(@RequestBody Practice practice, @RequestParam("token") String token){
        Practice judgePractice = produceService.judgePractice(practice, token);

        Boolean flag = produceService.dbSavePractice(judgePractice);
        if(judgePractice == null || !flag){
            return new Result(50000, "服务器异常，批改习题中", null);
        }else{
            return new Result(20000, "批改成功", judgePractice);
        }
    }
}
