package com.lilei.dao;

import com.lilei.entity.Course;
import com.lilei.entity.Depend;
import com.lilei.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class DependDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();


	
	public boolean isExist(int userId,int courseId) {
		sql = "SELECT dependId FROM depend WHERE userId = ? AND courseId = ?";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), userId,courseId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add(Depend a) {
		sql = "INSERT INTO depend ( dependId, userId, courseId) VALUES ( ?, ?, ?);";
		try {
			return queryRunner.update(sql, a.getDependId(), a.getUserId(), a.getCourseId() )>0;
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