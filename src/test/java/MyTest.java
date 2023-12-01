import com.yyh.entity.Condition;
import com.yyh.entity.Expression;
import com.yyh.entity.NatureProducer;
import org.junit.Test;

import java.util.List;


public class MyTest {
    @Test
    public void test(){
        NatureProducer natureProducer = new NatureProducer();
        Condition condition = new Condition(5, 3);
        List<Expression> expressions = natureProducer.produceExpressions(condition);
        System.out.println(expressions);
    }
}
