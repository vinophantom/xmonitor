package com.vino.xmonitor.mapper;

import com.vino.xmonitor.bean.XUser;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    /**
     * 根据id获取用户实例
     * @param id
     * @return
     */
    @Select("select * from X_USERS where ID=#{id}")
    public XUser getById (@Param("id")int id);


    /**
     * 更新用户
     * @param xUser
     */
    @Update("update X_USERS set name=#{name},age=#{age} where id=#{id}")
    public void update (XUser xUser);


    /**
     * 插入用户
     * @param user
     */
    @Insert("insert into X_USERS(name,age,address,salt,password) values(#{name},#{age},#{address},#{salt},#{password})")
    public void insert(XUser user);


}
