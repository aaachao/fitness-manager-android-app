/*
*  1. 教练功能--查看课程时间，查看系统通知，查看学员信息 附加： 评论功能
*  2. 学员功能--单日卡路里计算
* */
package com.app.dao;

import com.app.entity.Calories;
import com.app.entity.Notice;
import com.app.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class NoticeDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	public Notice found(int id) {
		sql = "SELECT * FROM notice WHERE id= ?";
		try {
			Notice a=queryRunner.query(sql,new BeanHandler<Notice>(Notice.class), id);
			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean update(Notice a) {
		sql = "UPDATE notice set notice=? WHERE id = ?;";
		try {
			System.out.println(a.getNotice());
			System.out.println(a.getId());
			return queryRunner.update(sql, a.getNotice(),1) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}