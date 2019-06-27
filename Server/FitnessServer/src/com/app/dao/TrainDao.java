package com.app.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.app.utils.JdbcUtils;

public class TrainDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();
	
	public boolean addNewTrainRecord(String userId, String duration) {
		try {
			sql= "INSERT INTO training (userId, duration) VALUES (?, ?);";
			return queryRunner.update(sql, userId, duration) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
