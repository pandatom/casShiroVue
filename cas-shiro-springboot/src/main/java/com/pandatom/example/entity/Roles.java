package com.pandatom.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;


/**
 * <p>
 * 
 * </p>
 *
 * @author hcly
 * @since 2022-03-28
 */
@TableName("tb_roles")
public class Roles extends Model<Roles> {

	private static final long serialVersionUID = 1L;

	@TableId(value="role_id", type= IdType.AUTO)
	private Integer roleId;
	@TableField("role_name")
	private String roleName;


	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

//	@Override
//	protected Serializable pkVal() {
//		return this.roleId;
//	}

	@Override
	public String toString() {
		return "Roles{" +
		", roleId=" + roleId +
		", roleName=" + roleName +
		"}";
	}
}
