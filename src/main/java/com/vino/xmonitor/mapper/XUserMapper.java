package com.vino.xmonitor.mapper;

import com.vino.xmonitor.bean.XUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by jiangyunxiong on 2018/5/21.
 */
@Mapper
public interface XUserMapper {

    @Select("select * from sk_user where id = #{id}")
    public XUser getById(@Param("id") long id);

    @Update("update sk_user set password = #{password} where id = #{id}")
    public void update(XUser toBeUpdate);
}


