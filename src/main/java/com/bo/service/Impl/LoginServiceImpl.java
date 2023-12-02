package com.bo.service.Impl;

import com.bo.domain.LoginUser;
import com.bo.domain.ResponseResult;
import com.bo.domain.User;
import com.bo.service.LoginService;
import com.bo.util.JwtUtil;
import com.bo.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager的authenticate方法进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(),
                user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }
        //如果认证通过使用userid生成一个jwt 把jwt存入responseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        //把完整的用户信息存入redis userid作为key
        redisCache.setCacheObject("login:" + userId, loginUser);

        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的userid
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //redis中删除该用户的值
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }

}
