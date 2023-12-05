package com.yyh.calculation_expression_sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyh.calculation_expression_sys.entity.User;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangyunhao
 * @since 2023-07-21
 */
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);

}
