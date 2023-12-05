package com.yyh.calculation_expression_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yyh.calculation_expression_sys.entity.User;
import com.yyh.calculation_expression_sys.service.IUserService;
import com.yyh.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController  //这里加@RestController,,如果加Controller,返回的必须是视图
@RequestMapping("/user")
@CrossOrigin//跨域
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")  //@RequestBody注解告诉Spring将HTTP请求体中的JSON数据转换为Java对象
    public Result<Map<String,Object>> login(@RequestBody User user){ //加上@RequestBody,前端传过来的是json格式的数据，后端要用@RequestBody接收
        Map<String, Object> data = userService.login(user);
        //登录成功，返回token
        if(data != null){
            return Result.success("登录成功", data);
        }
        return Result.fail("登录失败");
    }



    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(String token){
        Map<String, Object> data = userService.getUserInfo(token);
        if (data != null) {
            return Result.success("获取用户信息成功", data);
        }
        return Result.success("获取用户信息成功", data);
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){//这里的token是前端传过来的,是前端拦截器设置到请求头中的
        userService.logout(token);
        return Result.success("退出成功");
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username", required = false) String username,
                                                   @RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "pageNo") Long pageNo,
                                                   @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username), User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone,phone);
        //降序
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page, wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total", page.getTotal());
        data.put("rows", page.getRecords());

        return Result.success(data);

    }



    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Long id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result<?> addUser(@RequestBody User user){
        // 在添加用户之前检查用户名是否已存在
        if (userService.existsByUsername(user.getUsername(), user.getPhone(), user.getEmail())) {
            return Result.fail("该用户名或邮箱或手机号已经存在");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return Result.success("注册用户成功");
    }

}