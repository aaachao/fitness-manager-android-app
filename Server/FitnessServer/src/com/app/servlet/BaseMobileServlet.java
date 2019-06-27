package com.app.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.*;

public class BaseMobileServlet extends HttpServlet {
	
	private static final long serialVersionUID = -6970143137677892748L;
	String ERROR = "{'info':'error'}";

	// 统一创建dao，由子类调用
	UserDao userDao = new UserDao();
	NoticeDao noticeDao=new NoticeDao();
	CaloriesDao caloriesDao=new CaloriesDao();
	CourseDao courseDao=new CourseDao();
    DependDao dependDao=new DependDao();
	DailyCheckDao dailyCheckDao = new DailyCheckDao();
	CommentsDao commentsDao = new CommentsDao();
	FavorsDao favorsDao = new FavorsDao();
	NewsDao newsDao = new NewsDao();
	TrainDao trainDao = new TrainDao();
	CoachFitnessDao coachFitnessDao = new CoachFitnessDao();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// 设置编码

            //System.out.println("哈哈哈哈哈哈哈哈哈哈"+request);
			request.setCharacterEncoding("UTF-8");

			response.setContentType("text/html;charset=utf-8");

			// 操作类型
			String methodName=request.getParameter("method");

            //System.out.println("哈哈哈哈哈哈哈哈哈哈"+methodName);


			// 调用的子类
			Class clazz = this.getClass();


			// 通过反射执行方法
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 获取返回值
			Object returnValue = method.invoke(this, request, response);
			// 相应请求
			response.getWriter().write(returnValue.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(ERROR);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
