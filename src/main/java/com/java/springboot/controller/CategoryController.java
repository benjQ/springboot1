package com.java.springboot.controller;

import java.util.Date;
import java.util.List;
import com.java.springboot.entity.ResultMsg;
import com.java.springboot.entity.Token;
import com.java.springboot.entity.User;
import com.java.springboot.service.CategoryService;
import com.java.springboot.service.TokenService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.java.springboot.entity.Category;




@Transactional(rollbackFor = Exception.class)
@RestController
public class CategoryController {
    //Autowired自动装配，消除getter和setter方法
    private CategoryService categoryService;
    private TokenService tokenService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = "/addCategory", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultMsg addCategory(@RequestBody Category category) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        categoryService.insert(category);
        //int i=12/0;
        resultMsg.setStatusCode(0);
        resultMsg.setMsg("success");
        return resultMsg;
    }


    @GetMapping(value = "/deleteCategory", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultMsg deleteCategory(@RequestParam int id) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        categoryService.delete(id);
        //int i=12/0;
        resultMsg.setStatusCode(0);
        resultMsg.setMsg("success");
        return resultMsg;
    }

    @PostMapping(value = "/updateCategory", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultMsg updateCategory(@RequestBody Category category) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        categoryService.update(category);
        //int i=12/0;
        resultMsg.setStatusCode(0);
        resultMsg.setMsg("success");
        return resultMsg;
    }

    @GetMapping(value = {"/editCategory"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultMsg editCategoryById(int id) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        Category item = categoryService.findById(id);
        //int i=12/0;
        resultMsg.setStatusCode(0);
        resultMsg.setMsg("success");
        resultMsg.setData(item);
        return resultMsg;
    }

    @GetMapping(value = "/listCategory")
    public ResultMsg listCategory() throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        List<Category> all = categoryService.findAll();
        resultMsg.setStatusCode(0);
        resultMsg.setMsg("success");
        resultMsg.setData(all);
        return resultMsg;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultMsg login(@RequestBody User user) throws Exception {
        ResultMsg resultMsg = new ResultMsg();
        //根据用户名来查找数据库中的user对象
        User userConfirm = categoryService.login(user);
        //判断用户信息为空
        if ("".equals(user.getUsername()) || "".equals(user.getPassword())) {
            resultMsg.setMsg("username/password is null");
            resultMsg.setStatusCode(400);
            return resultMsg;
        }
        //判断用户不存在
        if (userConfirm == null) {
            resultMsg.setMsg("user is not existed");
            resultMsg.setStatusCode(400);
            return resultMsg;
        }
        //判断密码错误
        if (!user.getPassword().equals(userConfirm.getPassword())) {
            resultMsg.setMsg("password is not correct");
            resultMsg.setStatusCode(400);
            return resultMsg;
        }
        //根据数据库的用户信息查询Token
        Token token = tokenService.findByUserId(userConfirm.getUserid());

        //为生成Token准备
        String tokenStr;
        Date date = new Date();
        int currentTime = (int) (date.getTime() / 1000);
        //生成Token
        tokenStr = creatToken(userConfirm, date);
        if (null == token) {
            //第一次登陆
            token = new Token();
            token.setToken(tokenStr);
            token.setBuildtime(currentTime);
            token.setUserid(userConfirm.getUserid());
            tokenService.addToken(token);
        } else {
            //登陆就更新Token信息
            tokenStr = creatToken(userConfirm, date);
            token.setToken(tokenStr);
            token.setBuildtime(currentTime);
            tokenService.updateToken(token);
        }
        //返回Token信息给客户端
        resultMsg.setMsg("success");
        resultMsg.setToken(tokenStr);
        //int i=12/0;

        return resultMsg;
    }

    //生成Token信息方法（根据有效的用户信息）
    private String creatToken(User user, Date date) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT") // 设置header
                .setHeaderParam("alg", "HS256")
                .setIssuedAt(date) // 设置签发时间
                .setExpiration(new Date(date.getTime() + 1000 * 60 * 60 * 24 * 3))//设置过期时间
                .claim("userid", String.valueOf(user.getUserid())) // 设置内容
                .setIssuer("root")// 设置签发人
                .signWith(signatureAlgorithm, "benjq"); // 签名算法和key
        String jwt = builder.compact();//压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
        return jwt;
    }


}