package com.app.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.app.entity.News;
import com.app.entity.NewsDetail;
import com.app.entity.NewsListForFound;
import com.app.utils.JdbcUtils;

/**
 * 
 * 
 * @author djzhao
 * @time 2017年5月5日 下午7:56:57
 */
public class NewsDao {

	/** sql语句 */
	private String sql = "";

	/** QueryRunner */
	private QueryRunner queryRunner = JdbcUtils.getQueryRunnner();

	public boolean releaseNews(News news) {
		sql = "INSERT INTO news (userId, title, content, image) VALUES (?, ?, ?, ?);";
		try {
			return queryRunner.update(sql, news.getUserId(), news.getTitle(), news.getContent(), news.getImage()) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<NewsListForFound> getNewsList(int rows) {
		sql = "SELECT newsId, title,  username FROM news a, user b WHERE a.userId = b.userId ORDER BY releaseTime DESC LIMIT 0, ?;";
		try {
			return queryRunner.query(sql, new BeanListHandler<NewsListForFound>(NewsListForFound.class), rows);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean delete(String a) {
		sql = "DELETE  FROM news WHERE newsId = ?;";
		try {
			return queryRunner.update(sql,a) > 0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public NewsDetail getNewsDetail(String newsId) {
		sql = "SELECT newsId, username, title, content, a.image, releaseTime FROM news a, user b WHERE a.userId = b.userId AND newsId = ?;";
		try {
			return queryRunner.query(sql, new BeanHandler<NewsDetail>(NewsDetail.class), newsId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


}
