package com.app.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.entity.Course;
import com.google.gson.Gson;
import com.app.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UserServlet extends BaseMobileServlet {

	private static final long serialVersionUID = 5167110812308334576L;
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
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
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 【设置单文件最大值：5M】
		upload.setFileSizeMax(5 * 1024 * 1024);

		// 【设置总文件最大值： 20M】
		upload.setSizeMax(20 * 1024 * 1024);

		upload.setHeaderEncoding("utf-8");

		User user=new User();
		try {
			// 4.遍历表单项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 普通表单项
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (name.equals("username")) {
						user.setUsername(value);
					} else if (name.equals("password")) {
						user.setPassword(value);
						//course.setCalories(Integer.parseInt(value));
					} else if (name.equals("role")) {
						user.setRole(value);
					}
					else if (name.equals("oldusername")) {
						user.setoldUsername(value);
					}
					else if (name.equals("name")) {
						user.setName(value);
					}
					else if (name.equals("phone")) {
						user.setPhone(value);
					}
					else if (name.equals("weight")) {
						user.setWeight(Double.parseDouble(value));
					}

					System.out.println(name + " : " + value);
				} else {// 文件表单项
					// 文件名
					String fileName = item.getName();
					// 生成唯一文件名
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					user.setImage(fileName);
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
		}
		catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
                userDao.delete(a.getUsername());
                dependDao.deletebyuser(c.getUserId());
                coachFitnessDao.deletebyuser(c.getUserId());
                json = gson.toJson(a);


		} else {
			json = "未找到该用户";
		}
		return json;
	}
	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 【设置单文件最大值：5M】
		upload.setFileSizeMax(5 * 1024 * 1024);

		// 【设置总文件最大值： 20M】
		upload.setSizeMax(20 * 1024 * 1024);

		upload.setHeaderEncoding("utf-8");

		User a=new User();
		try {
			// 4.遍历表单项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 普通表单项
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (name.equals("username")) {
						a.setUsername(value);
					} else if (name.equals("password")) {
						a.setPassword(value);
						//course.setCalories(Integer.parseInt(value));
					} else if (name.equals("role")) {
						a.setRole(value);
					}
					else if (name.equals("oldusername")) {
						a.setoldUsername(value);
					}
					else if (name.equals("name")) {
						a.setName(value);
					}
					else if (name.equals("phone")) {
						a.setPhone(value);
					}
					else if (name.equals("weight")) {
						a.setWeight(Double.parseDouble(value));
					}

					System.out.println(name + " : " + value);
				} else {// 文件表单项
					// 文件名
					String fileName = item.getName();
					// 生成唯一文件名
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					a.setImage(fileName);
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
		}
		catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 2.创建文件上传核心类
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 【设置单文件最大值：5M】
		upload.setFileSizeMax(5 * 1024 * 1024);

		// 【设置总文件最大值： 20M】
		upload.setSizeMax(20 * 1024 * 1024);

		upload.setHeaderEncoding("utf-8");

		User a=new User();
		try {
			// 4.遍历表单项
			@SuppressWarnings("unchecked")
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 普通表单项
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (name.equals("username")) {
						a.setUsername(value);
					} else if (name.equals("password")) {
						a.setPassword(value);
						//course.setCalories(Integer.parseInt(value));
					} else if (name.equals("role")) {
						a.setRole(value);
					}
					else if (name.equals("oldusername")) {
						a.setoldUsername(value);
					}
					else if (name.equals("name")) {
						a.setName(value);
					}
					else if (name.equals("phone")) {
						a.setPhone(value);
					}
					else if (name.equals("weight")) {
						a.setWeight(Double.parseDouble(value));
					}
					else if (name.equals("data")) {
						a.setData(value);
					}

					System.out.println(name + " : " + value);
				} else {// 文件表单项
					// 文件名
					String fileName = item.getName();
					// 生成唯一文件名
					fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
					a.setImage(fileName);
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
		}
		catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		String json = "";
		//System.out.println("a.getusername="+a.getUsername());
		 if(userDao.isExist(a.getUsername()))
		{
			userDao.update3(a);
			json = gson.toJson(a);
		}
		else {
			json = "该用户未找到";
		}

		return json;
	}
	public String update22(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		User a = packageEntity3(request);
		Gson gson = new Gson();
		String json = "";
		//System.out.println("a.getusername="+a.getUsername());
		String z=request.getParameter("data");
		a.setData(z);
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
	public String found2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("username");
		User a = userDao.found3(userName);
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
	public String userlist3(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> newsList = userDao.getNewsList3(100);
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
	private User packageEntity3(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		String oldusername = request.getParameter("oldusername");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String weight2=request.getParameter("weight");
		double weight=Double.parseDouble(weight2);
		User user = new User();
		user.setoldUsername(oldusername);
		user.setRole(role);
		user.setUsername(username);
		user.setPassword(password);
		user.setPhone(phone);
		user.setName(name);
		user.setWeight(weight);
		return user;
	}
}
