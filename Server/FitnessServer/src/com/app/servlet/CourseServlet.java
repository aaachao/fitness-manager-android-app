package com.app.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.app.entity.Course;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class CourseServlet extends BaseMobileServlet {

	private static final long serialVersionUID= 6369315986486005507L;

	public String found(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseName = request.getParameter("mingcheng");
		Course a = courseDao.found(courseName);
		Gson gson = new Gson();
		String json = "";
		if (a == null) {
			json = "未找到该项目";
		} else {

			json = gson.toJson(a);

		}
		return json;
	}
    public String foundsike(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String courseName = request.getParameter("mingcheng");
        String coachId=request.getParameter("coachId");
        Course a = courseDao.foundsike(courseName,coachId);
        Gson gson = new Gson();
        String json = "";
        if (a == null) {
            json = "未找到";
        } else {

            json = gson.toJson(a);

        }
        return json;
    }
	/*public String found2(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String courseteach = request.getParameter("jiaolian");
		Course a = courseDao.found2(courseteach);
		Gson gson = new Gson();
		String json = "";
		json = gson.toJson(a);
		return json;
	}*/
    public String deletesike(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        Course a = packageEntity2(request);
        Course c=courseDao.found(a.getCoursename());
        String coachId=request.getParameter("coachId");
        Gson gson = new Gson();
        String json = "";
        if (courseDao.isExistsike(a.getCoursename(),coachId)) {
            courseDao.deletesike(a.getCoursename(),coachId);
            //dependDao.deletebycourse(c.getCourseId());
            //coachFitnessDao.deletebycourse(c.getCourseId());
            json = gson.toJson(a);
        }
        else {
            json = "未找到";
        }
        return json;
    }
	public String delete(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Course a = packageEntity2(request);
		Course c=courseDao.found(a.getCoursename());
		Gson gson = new Gson();
		String json = "";
		if (courseDao.isExist(a.getCoursename())) {
				courseDao.delete(a.getCoursename());
				dependDao.deletebycourse(c.getCourseId());
				coachFitnessDao.deletebycourse(c.getCourseId());
				json = gson.toJson(a);
			}
		 else {
			json = "未找到该项目";
		}
		return json;
	}
    public String delete2(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        String courseId = request.getParameter("mingcheng");
        Gson gson = new Gson();
        String json = "";
            if(courseDao.delete2(courseId))
            {
                json = "删除成功";
            }
            else
            {
                json = "删除失败";
            }
        return json;
    }
    public String addsike(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 2.创建文件上传核心类
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 【设置单文件最大值：5M】
        upload.setFileSizeMax(5 * 1024 * 1024);

        // 【设置总文件最大值： 20M】
        upload.setSizeMax(20 * 1024 * 1024);

        upload.setHeaderEncoding("utf-8");

        Course course=new Course();
        try {
            // 4.遍历表单项
            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    if (name.equals("mingcheng")) {
                        course.setCoursename(value);
                    } else if (name.equals("kaluli")) {
                        course.setCalories(Integer.parseInt(value));
                    } else if (name.equals("neirong")) {
                        course.setCoursedata(value);
                    }else if (name.equals("coachId")) {
                        course.setCoachId(Integer.parseInt(value));
                    }
                    //System.out.println(name + " : " + value);
                } else {// 文件表单项
                    // 文件名
                    String fileName = item.getName();
                    // 生成唯一文件名
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                    course.setImage(fileName);
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
        Course a = course;
        //User b=userDao.exist(a.getCourseteach());


        Gson gson = new Gson();
        String json = "";
        if (courseDao.isExist(a.getCoursename())) {
            json = "该项目已经存在";

        }
        else {
            courseDao.addsike(a);

            Course z=courseDao.foundsike(a.getCoursename(),a.getCoachId()+"");

            coachFitnessDao.addsike(z.getCoachId()+"",z.getCourseId()+"");
            json = gson.toJson(a);
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

        Course course=new Course();
        try {
            // 4.遍历表单项
            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    if (name.equals("mingcheng")) {
                        course.setCoursename(value);
                    } else if (name.equals("kaluli")) {
                        course.setCalories(Integer.parseInt(value));
                    } else if (name.equals("neirong")) {
                        course.setCoursedata(value);
                    }
                    System.out.println(name + " : " + value);
                } else {// 文件表单项
                    // 文件名
                    String fileName = item.getName();
                    // 生成唯一文件名
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                    course.setImage(fileName);
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
		Course a = course;
		//User b=userDao.exist(a.getCourseteach());


		Gson gson = new Gson();
		String json = "";
		if (courseDao.isExist(a.getCoursename())) {
			json = "该项目已经存在";

		}
		else {
			courseDao.add(a);
			json = gson.toJson(a);
		}


        return json;
	}

    public String updatewithoutpic(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        Course a = packageEntity(request);

        Gson gson = new Gson();
        String json = "";
        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+a.getoldCoursename());
        if (courseDao.isExist(a.getCoursename())&&!a.getCoursename().equals(a.getoldCoursename())) {
            json = "新修改的项目名称已存在";
        }
        else if(courseDao.isExist(a.getoldCoursename()))
        {
            courseDao.update2(a);
            json = gson.toJson(a);
        }
        else {
            json = "该项目未找到";
        }
        return json;
    }
    public String updatesikewithoutpic(HttpServletRequest request,
                                   HttpServletResponse response) throws ServletException, IOException {
        Course a = packageEntity(request);
        String coachId=request.getParameter("coachId");
        Gson gson = new Gson();
        String json = "";
        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+a.getoldCoursename());
        if (courseDao.isExist(a.getCoursename())&&!a.getCoursename().equals(a.getoldCoursename())) {
            json = "新修改的项目名称已存在";
        }
        else if(courseDao.isExist(a.getoldCoursename()))
        {
            courseDao.updatesike2(a,coachId);
            json = gson.toJson(a);
        }
        else {
            json = "该项目未找到";
        }
        return json;
    }
    public String updatesike(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
	    DiskFileItemFactory factory = new DiskFileItemFactory();

        // 2.创建文件上传核心类
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 【设置单文件最大值：5M】
        upload.setFileSizeMax(5 * 1024 * 1024);

        // 【设置总文件最大值： 20M】
        upload.setSizeMax(20 * 1024 * 1024);

        upload.setHeaderEncoding("utf-8");

        Course course=new Course();
        try {
            // 4.遍历表单项
            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    if (name.equals("mingcheng")) {
                        course.setCoursename(value);
                    } else if (name.equals("kaluli")) {
                        course.setCalories(Integer.parseInt(value));
                    } else if (name.equals("neirong")) {
                        course.setCoursedata(value);
                    }
                    else if (name.equals("oldmingcheng")) {
                        course.setoldCoursename(value);
                    }
                    System.out.println(name + " : " + value);
                } else {// 文件表单项
                    // 文件名
                    String fileName = item.getName();
                    // 生成唯一文件名
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                    course.setImage(fileName);
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
        Course a = course;
        String coachId=request.getParameter("coachId");
        Gson gson = new Gson();
        String json = "";
        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+a.getoldCoursename());
        if (courseDao.isExist(a.getCoursename())&&!a.getCoursename().equals(a.getoldCoursename())) {
            json = "新修改的项目名称已存在";
        }
        else if(courseDao.isExist(a.getoldCoursename()))
        {
            courseDao.updatesike(a,coachId);
            json = gson.toJson(a);
        }
        else {
            json = "该项目未找到";
        }
        return json;
    }
	public String update(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // 2.创建文件上传核心类
        ServletFileUpload upload = new ServletFileUpload(factory);

        // 【设置单文件最大值：5M】
        upload.setFileSizeMax(5 * 1024 * 1024);

        // 【设置总文件最大值： 20M】
        upload.setSizeMax(20 * 1024 * 1024);

        upload.setHeaderEncoding("utf-8");

        Course course=new Course();
        try {
            // 4.遍历表单项
            @SuppressWarnings("unchecked")
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                // 普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    if (name.equals("mingcheng")) {
                        course.setCoursename(value);
                    } else if (name.equals("kaluli")) {
                        course.setCalories(Integer.parseInt(value));
                    } else if (name.equals("neirong")) {
                        course.setCoursedata(value);
                    }
                    else if (name.equals("oldmingcheng")) {
                        course.setoldCoursename(value);
                    }
                    System.out.println(name + " : " + value);
                } else {// 文件表单项
                    // 文件名
                    String fileName = item.getName();
                    // 生成唯一文件名
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
                    course.setImage(fileName);
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
		Course a = course;

		Gson gson = new Gson();
		String json = "";
		//System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇"+a.getoldCoursename());
		if (courseDao.isExist(a.getCoursename())&&!a.getCoursename().equals(a.getoldCoursename())) {
			json = "新修改的项目名称已存在";
		}
		else if(courseDao.isExist(a.getoldCoursename()))
		{
			courseDao.update(a);
			json = gson.toJson(a);
		}
		else {
			json = "该项目未找到";
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
    public String sikelist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String coachId = request.getParameter("coachId");
        List<Course> newsList = courseDao.getsikeList(coachId,100);
        Gson gson = new Gson();
        String json = gson.toJson(newsList);
        return json;
    }

	private Course packageEntity(HttpServletRequest request) {
		String oldcoursename = request.getParameter("oldmingcheng");
		String coursename = request.getParameter("mingcheng");
		String courseteach = request.getParameter("jiaolian");
		String coursedata= request.getParameter("neirong");
        String kalulia= request.getParameter("kaluli");
        int kaluli=0;
        kaluli=Integer.parseInt(kalulia);
		Course a = new Course();
		a.setCoursename(coursename);
		a.setCourseteach(courseteach);
		a.setCoursedata(coursedata);
		a.setoldCoursename(oldcoursename);
		a.setCalories(kaluli);
		return a;
	}
    private Course packageEntity2(HttpServletRequest request) {
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
