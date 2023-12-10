package com.yyh.calculation_expression_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyh.POJO.Condition;
import com.yyh.POJO.Expression;
import com.yyh.calculation_expression_sys.entity.Practice;
import com.yyh.calculation_expression_sys.entity.vo.NoAnswerExpressionVO;

import java.util.List;
import java.util.Map;

public interface ProduceService extends IService<Practice> {
    Map<String, Object> redisSavePractice(Practice practice, String token);

    Practice judgePractice(Practice practice, String token);
    Practice producePractice(Condition condition, String token);
    Boolean dbSavePractice(Practice practice);
//    List<NoAnswerExpressionVO> transfer(List<Expression> expressionList);
    List<Expression> offAnswer(Practice practice);
}
