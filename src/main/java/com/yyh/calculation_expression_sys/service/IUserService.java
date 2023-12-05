package com.yyh.calculation_expression_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yyh.calculation_expression_sys.entity.User;


import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangyunhao
 * @since 2023-07-21
 */
public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    void logout(String token);


    boolean existsByUsername(String username, String phone, String email);
}
