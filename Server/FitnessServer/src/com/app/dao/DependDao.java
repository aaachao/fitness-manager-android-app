package com.app.dao;

import com.app.entity.Depend;
import com.app.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class DependDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	public Depend found(int userId,int courseId) {
		sql = "SELECT * FROM depend WHERE userId = ? AND courseId = ?";
		try {
			Depend a=queryRunner.query(sql,new BeanHandler<Depend>(Depend.class), userId,courseId);
			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	
	public boolean isExist(int userId,int courseId) {
		sql = "SELECT dependId FROM depend WHERE userId = ? AND courseId = ?";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), userId,courseId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add(Depend a) {
		sql = "INSERT INTO depend ( userId, courseId) VALUES (  ?, ?);";
		try {
			return queryRunner.update(sql, a.getUserId(), a.getCourseId() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add2(Depend a) {
		sql = "INSERT INTO depend ( userId, courseId, coachId) VALUES (  ?, ?,?);";
		try {
			return queryRunner.update(sql, a.getUserId(), a.getCourseId(),a.getCoachId() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete(int a,int b) {
		sql = "DELETE  FROM depend WHERE userId = ? AND courseId=?";
		try {
			return queryRunner.update(sql,a,b) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete2(int a,int b) {
		sql = "DELETE  FROM depend WHERE coachId = ? AND courseId=?";
		try {
			return queryRunner.update(sql,a,b) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebyuser(int a) {
		sql = "DELETE  FROM depend WHERE userId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebycourse(int a) {
		sql = "DELETE  FROM depend WHERE courseId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}