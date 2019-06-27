package com.lilei.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.lilei.entity.Course;
import com.lilei.entity.User;

public class UserServlet extends BaseMobileServlet {

	private static final long serialVersionUID = 5167110812308334576L;
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+username);

		User role =userDao.getRole(username,password);
		User user = userDao.login(username, password);
		Gson gson = new Gson();
		String json = "";
		if (user == null) {
			json = ERROR;
		} else {
			json = gson.toJson(user);
		}
		return json;
	}

	public String register(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = packageEntity(request);
		Gson gson = new Gson();
		String json = "";

		if (userDao.isExist(user.getUsername())) {
			json = "该用户已经存在";
		} else if (userDao.register(user)) {
			json = gson.toJson(user);
		} else {
			json = "注册失败，请稍后重试！";
		}
		return json;
	}
	

	public String delete(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		User a = packageEntity2(request);
		Gson gson = new Gson();
		String json = "";
		User c=userDao.found(a.getUsername());
		if (userDao.isExist(a.getUsername()))
		{
			//System.out.println("哈哈哈哈哈哈哈哈哈哈"+a.getUsername());
            if (c.getRole().equals("teacher")&&c.getStatus()==1)
            {
                json = "该教练还有课程任务，请先删除其课程";
            }
            else{
                userDao.delete(a.getUsername());
                dependDao.deletebyuser(c.getUserId());
                json = gson.toJson(a);
            }

		} else {
			json = "未找到该用户";
		}
		return json;
	}
	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		User a = packageEntity2(request);
		Gson gson = new Gson();
		String json = "";

		if (userDao.isExist(a.getUsername())) {
			json = "该用户账号已经存在";
		} else if (	userDao.add(a)) {

			json = gson.toJson(a);
		} else {

			json = "添加失败，请稍后重试！";
		}
		return json;
	}
	public String update2(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		User a = packageEntity2(request);
		Gson gson = new Gson();
		String json = "";
		//System.out.println("a.getusername="+a.getUsername());
		 if(userDao.isExist(a.getUsername()))
		{
			userDao.update2(a);
			json = gson.toJson(a);
		}
		else {
			json = "该用户未找到";
		}

		return json;
	}
	public String found(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		User a = userDao.found(userName);
		Gson gson = new Gson();
		String json = "";
		if (a == null) {
			json = "未找到该用户";
		} else {

			json = gson.toJson(a);

		}
		return json;
	}
	public String userlist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> newsList = userDao.getNewsList(100);
		Gson gson = new Gson();
		String json = gson.toJson(newsList);
		return json;
	}
	public String userlist2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> newsList = userDao.getNewsList2(100);
		Gson gson = new Gson();
		String json = gson.toJson(newsList);
		return json;
	}
	
	private User packageEntity(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		String oldusername = request.getParameter("oldusername");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		User user = new User();
		user.setoldUsername(oldusername);
		user.setRole(role);
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setName(name);
		return user;
	}
	private User packageEntity2(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		String oldusername = request.getParameter("oldusername");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
		User user = new User();
		user.setoldUsername(oldusername);
		user.setRole(role);
		user.setUsername(username);
		user.setPassword(password);
        user.setPhone(phone);
        user.setName(name);
		return user;
	}
}
