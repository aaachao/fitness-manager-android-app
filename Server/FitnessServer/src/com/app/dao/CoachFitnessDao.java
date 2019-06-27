package com.app.dao;

import com.app.entity.CoachFitness;
import com.app.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

public class CoachFitnessDao {
	/** sql语句 */
	private String sql = "";
	
	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();


	
	public boolean isExist(int coachId,int fitnessId) {
		sql = "SELECT ID FROM coachfitness WHERE coachId = ? AND fitnessId = ?";
		try {
			return queryRunner.query(sql, new ScalarHandler<Integer>(), coachId,fitnessId) != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean add(CoachFitness a) {
		sql = "INSERT INTO coachfitness ( ID, coachId, fitnessId) VALUES ( ?, ?, ?);";
		try {
			return queryRunner.update(sql, a.getId(), a.getCoachId(), a.getFitnessId() )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean addsike(String coachId,String fitnessId) {
		sql = "INSERT INTO coachfitness (  coachId, fitnessId) VALUES (  ?, ?);";
		try {
			return queryRunner.update(sql, coachId,fitnessId )>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete(int a,int b) {
		sql = "DELETE  FROM coachfitness WHERE coachId = ? AND fitnessId=?";
		try {
			return queryRunner.update(sql,a,b) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebyuser(int a) {
		sql = "DELETE  FROM coachfitness WHERE coachId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean deletebycourse(int a) {
		sql = "DELETE  FROM coachfitness WHERE fitnessId = ?";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}