package com.vino.xmonitor.service;

import com.vino.xmonitor.bean.XUser;
import com.vino.xmonitor.exception.GlobalException;
import com.vino.xmonitor.mapper.XUserMapper;
import com.vino.xmonitor.result.CodeMsg;
import com.vino.xmonitor.utils.MD5Util;
import com.vino.xmonitor.utils.UUIDUtil;
import com.vino.xmonitor.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author phantom
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class UserService {

    private final XUserMapper userMapper;


    private static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    public UserService(XUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int countUsers() {
        return userMapper.countUsers();
    }

    private XUser getById(long id) {

        //TODO 取数据库
        XUser user = userMapper.getById(id);
        //TODO 再存入缓存

        return user;
    }

    /**
     * 典型缓存同步场景：更新密码
     */
    public boolean updatePassword(String token, long id, String formPass) {
        //取user
        XUser user = getById(id);
        //更新数据库
        XUser toBeUpdate = new XUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        userMapper.update(toBeUpdate);
        //更新缓存：先删除再插入
        user.setPassword(toBeUpdate.getPassword());
        return true;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        XUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成唯一id作为token
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * 将token做为key，用户信息做为value 存入redis模拟session
     * 同时将token存入cookie，保存登录状态
     */
    private void addCookie(HttpServletResponse response, String token, XUser user) {
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(3600*24 *2);
        // 设置为网站根目录
        cookie.setPath("/");
        response.addCookie(cookie);
    }



}
