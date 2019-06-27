package com.lilei.dao;

import java.sql.SQLException;
import java.util.List;

import com.lilei.entity.Course;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lilei.entity.User;
import com.lilei.utils.JdbcUtils;

public class UserDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	
	public User login(String username, String password) {
		sql = "SELECT * FROM `user` WHERE username = ? AND `password` = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<User>(User.class), username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User getRole(String username,String password) {
		sql = "SELECT role FROM `user` WHERE username = ? AND `password` = ?";
		try {
		    User role=queryRunner.query(sql, new BeanHandler<User>(User.class),username,password);
            //System.out.println("SQLSQLqwertyuiop哇哇哇哇哇哇哇哇哇哇"+sql);
            //System.out.println("ROLEROLEqwertyuiop哇哇哇哇哇哇哇哇哇哇"+role);
			return role;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isExist(String username) {
		sql = "SELECT userId FROM user WHERE username = ?";
		try {

			return queryRunner.query(sql, new ScalarHandler<Integer>(), username) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public User exist(String name) {
		sql = "SELECT * FROM user WHERE username = ? AND role=?";
		try {
			 User a=queryRunner.query(sql, new BeanHandler<User>(User.class), name,"teacher");
			 return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public User found(String username) {
		sql = "SELECT * FROM user WHERE userName = ?";
		try {

			User a=queryRunner.query(sql, new BeanHandler<User>(User.class), username);

			return a;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add(User a) {
		sql = "INSERT INTO user (userName, password, phone,role,name) VALUES ( ?, ?, ?,?, ?);";
		try {
			return queryRunner.update(sql, a.getUsername(), a.getPassword(),a.getPhone(), a.getRole(),a.getName() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean update(User a) {
	    //System.out.println(a.getUsername()+a.getPassword());
		sql = "UPDATE user set username = ?, password = ? WHERE username = ?;";
		try {
			return queryRunner.update(sql, a.getUsername(), a.getPassword(),a.getoldUsername()) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    public boolean update2(User a) {
        //System.out.println(a.getUsername()+a.getPassword());
        sql = "UPDATE user set password = ?  ,name = ?,  phone = ? WHERE username = ?;";
        try {
            return queryRunner.update(sql, a.getPassword(),a.getName(),a.getPhone(),a.getUsername()) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean statu0(String a) {
        //System.out.println(a.getUsername()+a.getPassword());
        sql = "UPDATE user set status = ? WHERE username = ?;";
        try {
            return queryRunner.update(sql, 0,a) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean statu1(String a) {
        //System.out.println(a.getUsername()+a.getPassword());
        sql = "UPDATE user set status = ? WHERE username = ?;";
        try {
            return queryRunner.update(sql, 1,a) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	public boolean delete(String a) {
		sql = "DELETE  FROM user WHERE username = ?;";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<User> getNewsList(int rows) {
		sql = "SELECT * FROM user ORDER BY userId DESC LIMIT 0, ?;";
		try {
			return queryRunner.query(sql, new BeanListHandler<User>(User.class), rows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean register(User user) {
		sql = "INSERT INTO user (username, `password`, name, phone) VALUES (?, ?, ?, ?);";
		try {
			return queryRunner.update(sql, user.getUsername(), user.getPassword(),  user.getName(), user.getPhone()) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public List<User> getmyList(int courseId) {
		//System.out.println("courseId="+courseId);
		sql = "SELECT c.*, status FROM user c,depend b WHERE  b.userId=c.userId AND b.courseId = ? ORDER BY userID ;";
		try {
			return queryRunner.query(sql, new BeanListHandler<User>(User.class), courseId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
//	public List<User> getuserList(int courseId) {
//		//System.out.println("courseId="+courseId);
//		sql = "SELECT c.userId, username, password, sex, height, weight, role,registerTime, status FROM user c,depend b WHERE b.userId=c.userId AND b.courseId = ? AND c.role=? ORDER BY userID ;";
//		try {
//			return queryRunner.query(sql, new BeanListHandler<User>(User.class), courseId,"user");
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	public List<User> getNewsList2(int rows) {
		sql = "SELECT * FROM user WHERE role=? ORDER BY userId DESC LIMIT 0, ?;";
		try {
			return queryRunner.query(sql, new BeanListHandler<User>(User.class),"user", rows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}