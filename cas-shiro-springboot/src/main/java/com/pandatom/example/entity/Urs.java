package com.pandatom.example.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hcly
 * @since 2022-03-29
 */
@TableName("tb_urs")
public class Urs extends Model<Urs> {

	private static final long serialVersionUID = 1L;

	private Integer uid;
	private Integer rid;


	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}


	@Override
	public String toString() {
		return "Urs{" +
		", uid=" + uid +
		", rid=" + rid +
		"}";
	}
}
