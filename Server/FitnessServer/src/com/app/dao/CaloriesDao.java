/*
*  1. 教练功能--查看课程时间，查看系统通知，查看学员信息 附加： 评论功能
*  2. 学员功能--单日卡路里计算
* */
package com.app.dao;

import com.app.entity.Calories;
import com.app.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CaloriesDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	public Calories found(int userId,int courseId) {
		sql = "SELECT * FROM calories WHERE userId = ? AND courseId = ?";
		try {
			Calories a=queryRunner.query(sql,new BeanHandler<Calories>(Calories.class), userId,courseId);
			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public boolean isExist(int userId,int courseId) {
		sql = "SELECT Id FROM calories WHERE userId = ? AND courseId = ?";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), userId,courseId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add(Calories a) {
		sql = "INSERT INTO calories ( userId, courseId,coachId,calories) VALUES ( ?,?, ?, ?);";
		try {
			return queryRunner.update(sql, a.getUserId(), a.getCourseId(),a.getCoachId(),a.getCalories() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add2(Calories a) {
		sql = "INSERT INTO calories ( userId, courseId, calories) VALUES (  ?, ?,?);";
		try {
			return queryRunner.update(sql, a.getUserId(), a.getCourseId(),a.getCalories() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete(int a,int b) {
		sql = "DELETE  FROM calories WHERE userId = ? AND courseId=?";
		try {
			return queryRunner.update(sql,a,b) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete2(int a,int b) {
		sql = "DELETE  FROM calories WHERE coachId = ? AND courseId=?";
		try {
			return queryRunner.update(sql,a,b) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebyuser(int a) {
		sql = "DELETE  FROM calories WHERE userId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebycourse(int a) {
		sql = "DELETE  FROM calories WHERE courseId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Calories> getmyList(int userId) {
		sql = "SELECT * FROM calories WHERE  userId = ? ORDER BY Id DESC;";
		try {
			return queryRunner.query(sql, new BeanListHandler<Calories>(Calories.class), userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Calories> getmyList2(int userId,int courseId,int coachId) {
		sql = "SELECT * FROM calories WHERE  userId = ? AND courseId=? AND coachId=? ORDER BY Id DESC;";
		try {
			return queryRunner.query(sql, new BeanListHandler<Calories>(Calories.class), userId,courseId,coachId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}