package com.app.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.app.utils.JdbcUtils;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月2日 下午10:04:16
 */
public class DailyCheckDao {

	private String sql = "";

	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();

	public boolean check(String userId) {
		sql = "INSERT INTO dailycheck (userId, checkDate, checkTime) VALUES (?, CURDATE(), CURRENT_TIME());";
		try {
			return queryRunner.update(sql, userId) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isChecked(String userId) {
		sql = "SELECT id FROM dailycheck WHERE userId = ? AND checkDate = CURDATE();";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), userId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int getTotalCheckedDays(String userId) {
		sql = "SELECT COUNT(*) FROM dailycheck WHERE userId = ?;";
		try {
			return queryRunner.query(sql, new ScalarHandler<Long>(), userId).intValue();
		} catch (SQLException e) {
			return 0;
		}
	}
	public boolean isExist(String userId) {
		sql = "SELECT Id FROM calories WHERE userId = ?";
		try {

			return queryRunner.query(sql, new ScalarHandler<Integer>(), userId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public int getTotalTrainingDays(String userId) {
		sql = "SELECT SUM(calories) FROM calories WHERE userId = ?;";
		try {
			int a=queryRunner.query(sql, new ScalarHandler<BigDecimal>(), userId).intValue();
			return a;
		} catch (SQLException e) {
			return 0;
		}
	}
	public int gettadayTotalTrainingDays(String userId) {
		sql = "SELECT SUM(calories) FROM calories WHERE userId = ?;";
		try {
			return queryRunner.query(sql, new ScalarHandler<BigDecimal>(), userId).intValue();
		} catch (SQLException e) {
			return 0;
		}
	}

	public String getCheckedList(String userId) {
		sql = "SELECT checkDate FROM dailycheck WHERE userId = ?;";
		try {
			return queryRunner.query(sql, new ResultSetHandler<String>() {
				@Override
				public String handle(ResultSet rs) throws SQLException {
					StringBuilder sb = new StringBuilder();
					int a=0;
					while (rs.next()) {
						a=1;
						sb.append(rs.getString(1)).append(",");
					}
					if (a==1){
						return sb.substring(0, sb.length() - 1);
					}
					else
					{
						return sb.substring(0, 0);
					}
				}
			}, userId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
