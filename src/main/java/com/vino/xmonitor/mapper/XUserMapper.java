package com.vino.xmonitor.mapper;

import com.vino.xmonitor.bean.XUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author phantom
 * @date
 */
@Mapper
public interface XUserMapper {

    @Select("select * from x_user where id = #{id}")
    public XUser getById(@Param("id") long id);

    @Update("update x_user set password = #{password} where id = #{id}")
    public void update(XUser toBeUpdate);

    @Select("select count(1) from x_user")
    public int countUsers();
}


