package com.java.springboot.interceptor;

import com.java.springboot.entity.Token;
import com.java.springboot.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

import io.jsonwebtoken.Jwts;


@Component
public class MethodInterceptor implements HandlerInterceptor {
    private TokenService tokenService;

    @Autowired
    public void setTokenService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter resultWriter = null;
        final String headerToken = httpServletRequest.getHeader("Token");
        //判断请求信息
        if (null == headerToken || headerToken.trim().equals("")) {
            resultWriter = httpServletResponse.getWriter();
            resultWriter.write("token为空,需要登录");
            resultWriter.flush();
            resultWriter.close();
            return false;
        }
        //解析Token信息
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("benjq")
                    .parseClaimsJws(headerToken)
                    .getBody();
            String tokenUserId = (String) claims.get("userid");
            int intTokenUserId = Integer.parseInt(tokenUserId);
            //resultWriter=httpServletResponse.getWriter();
            //根据客户Token查找数据库Token
            Token myToken = tokenService.findByUserId(intTokenUserId);

            /*
            //数据库没有Token记录
            if (null == myToken) {
                resultWriter = httpServletResponse.getWriter();
                resultWriter.write("token不存在,需要登录");
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            */
            /*数据库Token与客户Token比较
            if( !headerToken.equals(myToken.getToken()) ){
                resultWriter=httpServletResponse.getWriter();
                resultWriter.write("你的token修改过,需要登录");
                resultWriter.flush();
                resultWriter.close();
                return false;
            }
            */

            //判断Token过期
            Date tokenDate = (Date) claims.getExpiration();
            int overtime = (int) (new Date().getTime() - tokenDate.getTime()) / 1000;


            if (overtime > 60 * 60 * 24 * 3) {
                resultWriter = httpServletResponse.getWriter();
                resultWriter.write("token已过期,需要登录");
                resultWriter.flush();
                resultWriter.close();
                return false;
            }

        } catch (Exception e) {
            resultWriter = httpServletResponse.getWriter();
            e.printStackTrace();
            resultWriter.write("token不正确,需要登录");
            resultWriter.flush();
            resultWriter.close();
            return false;
        }

        //最后才放行
        //resultWriter.flush();
        //resultWriter.close();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

