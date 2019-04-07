package com.vino.xmonitor.vo;



import javax.validation.constraints.NotNull;

/**
 *
 * @author phantom
 * @date 2019/01/22
 */
public class LoginVo {

    @NotNull
    //@IsMobile  //因为框架没有校验手机格式注解，所以自己定义
    private String username;

    @NotNull
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

