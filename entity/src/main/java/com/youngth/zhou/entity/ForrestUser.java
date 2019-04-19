package com.youngth.zhou.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author YoungTH
 * @since 2019-04-18
 */
@TableName("FORREST_USER")
public class ForrestUser extends Model<ForrestUser> {

    private static final long serialVersionUID = 1L;

    @TableId("USERID")
    private String userid;
    @TableField("USERNAME")
    private String username;
    @TableField("PASSWORD")
    private String password;


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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
    protected Serializable pkVal() {
        return this.userid;
    }

    @Override
    public String toString() {
        return "ForrestUser{" +
        ", userid=" + userid +
        ", username=" + username +
        ", password=" + password +
        "}";
    }
}
