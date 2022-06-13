package com.pandatom.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcly
 * @since 2022-05-04
 */
@TableName("tb_record")
public class Record extends Model<Record> {

	private static final long serialVersionUID = 1L;

	private String id;
	private String username;
	@TableField("login_time")
	private String loginTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Record{" +
		", id=" + id +
		", username=" + username +
		", loginTime=" + loginTime +
		"}";
	}
}
