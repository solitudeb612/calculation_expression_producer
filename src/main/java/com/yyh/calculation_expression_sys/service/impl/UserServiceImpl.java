package com.yyh.calculation_expression_sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yyh.calculation_expression_sys.entity.User;
import com.yyh.calculation_expression_sys.mapper.UserMapper;
import com.yyh.calculation_expression_sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangyunhao
 * @since 2023-07-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername()); //eq->equal,    LambdaQueryWrapper用get函数，而不是直接写属性值（这样会耦合死），属性名修改这里的形参也要改，不优雅
        User loginUser = this.baseMapper.selectOne(wrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword())){
            // 暂时用UUID, 终极方案是jwt
            String key = "user:" + UUID.randomUUID();

            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,1440, TimeUnit.MINUTES);

            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);

            return data;
        }
        return null;
 }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token从redis中获取用户信息
        Object obj = redisTemplate.opsForValue().get(token);//这里的token是前端传过来的，是一个字符串，不是redis中的key
        if (obj != null) {
            User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);//1.将obj转换成json字符串，2.将json字符串转换成User对象
            Map<String, Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            data.put("avatar",loginUser.getAvatar());
            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public boolean existsByUsername(String username, String phone, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 构建查询逻辑，判断用户名、手机或邮箱是否已存在
        queryWrapper.eq("username", username).or().eq("phone", phone).or().eq("email", email);

        // 使用MyBatis-Plus提供的selectCount方法来统计匹配的记录数
        Long count = this.baseMapper.selectCount(queryWrapper);

        // 如果count大于0，表示存在匹配的用户
        return count > 0;
    }


}
