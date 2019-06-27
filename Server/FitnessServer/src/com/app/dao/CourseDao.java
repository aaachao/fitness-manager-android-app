package com.app.dao;

import com.app.entity.Course;
import com.app.utils.JdbcUtils;
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
		sql = "SELECT * FROM course WHERE courseName = ? AND status = ?";
		try {

            Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseName,0);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Course foundsike(String courseName,String coachId) {
		sql = "SELECT * FROM course WHERE courseName = ? AND coachId = ? AND status = ?";
		try {

			Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseName,coachId,1);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public Course found2(String courseteach) {
		sql = "SELECT * FROM course WHERE courseteach = ? AND status = ?";
		try {

			Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseteach,0);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public Course found3(int courseId) {
        sql = "SELECT * FROM course WHERE courseId = ? ";
        try {

            Course a=queryRunner.query(sql, new BeanHandler<Course>(Course.class), courseId);

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
    public boolean isExistsike(String courseName,String coachId) {
        sql = "SELECT courseId FROM course WHERE courseName = ? AND coachId = ? AND status = ?";
        try {
            return queryRunner.query(sql, new ScalarHandler<Integer>(), courseName,coachId,1) != null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public boolean add(Course a) {
		sql = "INSERT INTO course ( courseName, coursedata,image,calories) VALUES ( ?, ?, ?,?);";
		try {
            return queryRunner.update(sql, a.getCoursename(), a.getCoursedata() ,a.getImage(),a.getCalories())>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean addsike(Course a) {
		sql = "INSERT INTO course ( courseName, coachId, coursedata,status,image,calories) VALUES ( ?, ?, ?, ?, ?,?);";
		try {
			return queryRunner.update(sql, a.getCoursename(), a.getCoachId(), a.getCoursedata() ,1,a.getImage(),a.getCalories())>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean update(Course a) {
		sql = "UPDATE course set courseName = ?,  coursedata = ?  ,image=?,calories=? WHERE courseName = ? AND status=?;";
		try {
			return queryRunner.update(sql, a.getCoursename(), a.getCoursedata(),a.getImage(),a.getCalories(),a.getoldCoursename(),0) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public boolean updatesike(Course a,String coachId) {
        sql = "UPDATE course set courseName = ?,  coursedata = ? ,image=?,calories=? WHERE courseName = ? AND coachId = ? AND status=?;";
        try {
            return queryRunner.update(sql, a.getCoursename(),a.getCoursedata(),a.getImage(),a.getCalories(),a.getoldCoursename(),coachId,1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public boolean update2(Course a) {
		sql = "UPDATE course set courseName = ?, coursedata = ? ,calories=? WHERE courseName = ?;";
		try {
			return queryRunner.update(sql, a.getCoursename(),a.getCoursedata(),a.getCalories(),a.getoldCoursename()) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public boolean updatesike2(Course a,String coachId) {
        sql = "UPDATE course set courseName = ?,  coursedata = ?  ,calories=? WHERE courseName = ? AND coachId = ? AND status=?;";
        try {
            return queryRunner.update(sql, a.getCoursename(),a.getCoursedata(),a.getCalories(),a.getoldCoursename(),coachId,1) > 0;
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
    public boolean delete2(String a) {
        sql = "DELETE  FROM course WHERE courseId = ?;";
        try {
            return queryRunner.update(sql,a) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deletesike(String a,String coachId) {
        sql = "DELETE  FROM course WHERE courseName = ? AND coachId = ? AND status = ?;";
        try {
            return queryRunner.update(sql,a,coachId,1) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Course> getNewsList(int rows) {
        sql = "SELECT * FROM course WHERE status = ? ORDER BY courseId DESC LIMIT 0, ?;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), 0,rows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public List<Course> getsikeList(String coachId,int rows) {
		sql = "SELECT * FROM course WHERE coachId = ? AND status = ? ORDER BY courseId DESC LIMIT 0, ?;";
		try {
			return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), coachId,1,rows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public List<Course> getmyList(int userId) {
        sql = "SELECT c.* FROM depend b,course c WHERE  b.courseId=c.courseId AND b.userId = ? AND c.status = ? ORDER BY courseID ;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), userId,0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Course> getmyList22(int userId) {
        sql = "SELECT c.* FROM depend b,course c WHERE  b.courseId=c.courseId AND b.userId = ? ORDER BY courseID ;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Course> getmyList2(int userId) {
        sql = "SELECT c.* FROM coachfitness b,course c WHERE  b.fitnessId=c.courseId AND b.coachId = ? AND c.status = ? ORDER BY courseID ;";
        try {
            return queryRunner.query(sql, new BeanListHandler<Course>(Course.class), userId,0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}