package com.pandatom.example.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@TableName("tb_users")
@Data
public class Users extends Model<Users> {

	private static final long serialVersionUID = 1L;

	@TableId(value="user_id", type= IdType.AUTO)
	private Integer userId;
	private String username;
	private String password;
//	private String email;
	@TableField("password_salt")
	private String passwordSalt;
	@TableField(exist = false)
	private String taken;
	@TableField(exist = false)
	private boolean readme;



}
