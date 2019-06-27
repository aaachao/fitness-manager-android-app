package com.app.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.app.entity.Comments;
import com.app.entity.News;
import com.app.entity.NewsDetail;
import com.app.entity.NewsListForFound;


public class NewsServlet extends BaseMobileServlet {

	private static final long serialVersionUID = -6810618231374558214L;

	public String releaseNewsWithImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1.创建工厂类
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 【设置单文件最大值：5M】
		upload.setFileSizeMax(5 * 1024 * 1024);

		// 【设置总文件最大值： 20M】
		upload.setSizeMax(20 * 1024 * 1024);

		upload.setHeaderEncoding("utf-8");

		News news = new News();

		try {
			// 4.遍历表单项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 普通表单项
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (name.equals("userId")) {
						news.setUserId(Integer.parseInt(value));
					} else if (name.equals("title")) {
						news.setTitle(value);
					} else if (name.equals("content")) {
						news.setContent(value);
					}
					System.out.println(name + " : " + value);
				} else {// 文件表单项
					// 文件名
					String fileName = item.getName();
					// 生成唯一文件名
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					news.setImage(fileName);

					// 获取上传路径：项目目录下的upload文件夹(先创建upload文件夹)
					String basePath = "C:\\Users\\C\\Desktop\\file";

					// 创建文件对象
					File file = new File(basePath, fileName);

					// 写文件（保存）
					item.write(file);

					// 删除临时文件
					item.delete();
				}
			}

			if (newsDao.releaseNews(news)) {
				return "success";
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	public String releaseNewsWithoutImage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String userId = request.getParameter("userId");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			News news = new News();
			news.setContent(content);
			news.setTitle(title);
			news.setUserId(Integer.parseInt(userId));
			if (newsDao.releaseNews(news)) {
				return "success";
			}
		return "error";
	}
	
	public String getNewsList(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<NewsListForFound> newsList = newsDao.getNewsList(100);
		for(int i=0;i<newsList.size();i++)
		{
			newsList.get(i).setName(userDao.found(newsList.get(i).getUsername()).getName());
			newsList.get(i).setImage(userDao.found(newsList.get(i).getUsername()).getImage());
		}
		Gson gson = new Gson();
		String json = gson.toJson(newsList);
		return json;
	}
    public String delete(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        String newsId = request.getParameter("newsId");

        Gson gson = new Gson();
        String json = "";
        if (newsDao.delete(newsId))
        {
            System.out.println(newsId);
            json = "删除成功";

        } else {
            System.out.println("失败");
            json = "删除失败";
        }
        return json;
    }
	
	public String getNewsDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String newsId = request.getParameter("newsId");
		NewsDetail newsDetail = newsDao.getNewsDetail(newsId);
		newsDetail.setName(userDao.found(newsDetail.getUsername()).getName());
		if (newsDetail != null) {
			List<Comments> comments = commentsDao.getCommentsByNewsId(newsId);
            for(int i=0;i<comments.size();i++)
            {
                comments.get(i).setName(userDao.found(comments.get(i).getUsername()).getName());
            }
			newsDetail.setComments(comments);
			Gson gson = new Gson();
			String json = gson.toJson(newsDetail);
			return json;
		} else {
			return ERROR;
		}
	}
}
