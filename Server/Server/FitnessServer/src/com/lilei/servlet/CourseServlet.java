package com.lilei.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lilei.entity.NewsListForFound;
import com.lilei.entity.User;
import com.lilei.entity.Course;

public class CourseServlet extends BaseMobileServlet {

	private static final long serialVersionUID= 6369315986486005507L;

	public String found(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseName = request.getParameter("mingcheng");
		Course a = courseDao.found(courseName);
		Gson gson = new Gson();
		String json = "";
		if (a == null) {
			json = "未找到该课程";
		} else {

			json = gson.toJson(a);

		}
		return json;
	}
	public String found2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseteach = request.getParameter("jiaolian");
		Course a = courseDao.found2(courseteach);
		Gson gson = new Gson();
		String json = "";
		json = gson.toJson(a);
		return json;
	}
	public String delete(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Course a = packageEntity(request);
		Course c=courseDao.found(a.getCoursename());
		User b=userDao.exist(c.getCourseteach());
		Gson gson = new Gson();
		String json = "";
		if (courseDao.isExist(a.getCoursename())) {

			if (b==null){
				courseDao.delete(a.getCoursename());
				dependDao.deletebycourse(c.getCourseId());
				json = gson.toJson(a);}
				else
				{
					//System.out.println("11111111111    "+b.getUsername());
					userDao.statu0(b.getUsername());
					courseDao.delete(a.getCoursename());
					dependDao.deletebycourse(c.getCourseId());
					json = gson.toJson(a);
				}
			}
		 else {
			json = "未找到该课程名称";
		}
		return json;
	}

	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Course a = packageEntity(request);

		User b=userDao.exist(a.getCourseteach());
		Gson gson = new Gson();
		String json = "";
		if (courseDao.isExist(a.getCoursename())) {
			json = "该课程已经存在";
		}
		else if (b==null){
			json = "未找到该教练";
		}
		else if(b.getStatus()==1){
			json = "该教练已经教授其他课程";
		}
		else if (b.getStatus()==0) {
			a.setTeachername(b.getName());
			courseDao.add(a);
			userDao.statu1(b.getUsername());
            Course d=courseDao.found2(a.getCoursename());
			json = gson.toJson(d);
		} else {

			json = "添加失败，请稍后重试！";
		}
		return json;
	}

	public String update(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		Course a = packageEntity(request);
		User b=userDao.exist(a.getCourseteach());

		Gson gson = new Gson();
		String json = "";
		//System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+a.getoldCoursename());
		if (courseDao.isExist(a.getCoursename())&&!a.getCoursename().equals(a.getoldCoursename())) {
			json = "新修改的课程名称已存在";
		}
		else if(courseDao.isExist(a.getoldCoursename()))
		{
			Course c=courseDao.found(a.getoldCoursename());
			if (a.getCourseteach().equals(c.getCourseteach()))
			{
				a.setTeachername(c.getTeachername());
				courseDao.update(a);
				json = gson.toJson(a);
			}
			else
			{

				if (b==null){
					json = "未找到该教练";
				}
				else if(b.getStatus()==1){
					json = "该教练已经教授其他课程";
				}
				else{
					a.setTeachername(b.getName());
					userDao.statu1(b.getUsername());
					userDao.statu0(c.getCourseteach());
					courseDao.update(a);
					json = gson.toJson(a);
				}
			}

		}
		else {
			json = "该课程未找到";
		}
		return json;
	}

	public String courselist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Course> newsList = courseDao.getNewsList(100);
		Gson gson = new Gson();
		String json = gson.toJson(newsList);
		return json;
	}


	private Course packageEntity(HttpServletRequest request) {
		String oldcoursename = request.getParameter("oldmingcheng");
		String coursename = request.getParameter("mingcheng");
		String courseteach = request.getParameter("jiaolian");
		String coursedata= request.getParameter("neirong");
		Course a = new Course();
		a.setCoursename(coursename);
		a.setCourseteach(courseteach);
		a.setCoursedata(coursedata);
		a.setoldCoursename(oldcoursename);
		return a;
	}
}
