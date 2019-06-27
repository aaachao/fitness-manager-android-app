package com.lilei.dao;

import com.lilei.entity.Course;
import com.lilei.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class CourseDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();

	public Course found(String courseName) {
		sql = "SELECT * FROM course WHERE courseName = ?";
		try {

            Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseName);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Course found2(String courseteach) {
		sql = "SELECT * FROM course WHERE courseteach = ?";
		try {

			Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseteach);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isExist(String courseName) {
		sql = "SELECT courseId FROM course WHERE courseName = ?";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), courseName) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public boolean add(Course a) {
		sql = "INSERT INTO course ( courseName, courseteach, coursedata,teachername) VALUES ( ?, ?, ?,?);";
		try {
            return queryRunner.update(sql, a.getCoursename(), a.getCourseteach(), a.getCoursedata() ,a.getTeachername())>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean update(Course a) {
		sql = "UPDATE course set courseName = ?, courseteach = ?, coursedata = ? ,teachername=? WHERE courseName = ?;";
		try {
			return queryRunner.update(sql, a.getCoursename(), a.getCourseteach(),a.getCoursedata(),a.getTeachername(),a.getoldCoursename()) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public boolean delete(String a) {
        sql = "DELETE  FROM course WHERE courseName = ?;";
        try {
            return queryRunner.update(sql,a) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Course> getNewsList(int rows) {
        sql = "SELECT * FROM course ORDER BY courseId DESC LIMIT 0, ?;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Course> getmyList(int userId) {
        sql = "SELECT c.* FROM depend b,course c WHERE  b.courseId=c.courseId AND b.userId = ? ORDER BY courseID ;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}