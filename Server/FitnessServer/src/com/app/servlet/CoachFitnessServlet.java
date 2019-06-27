package com.app.servlet;

import com.google.gson.Gson;
import com.app.entity.Course;
import com.app.entity.CoachFitness;
import com.app.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CoachFitnessServlet extends BaseMobileServlet {

	private static final long serialVersionUID= -318394635533549355L;

	public String add(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		CoachFitness a = packageEntity(request);
		Gson gson = new Gson();
		String json = "";
		if (coachFitnessDao.isExist(a.getCoachId(),a.getFitnessId())) {
			json = "您已经选过该项目了";
		} else if (	coachFitnessDao.add(a)) {
			json = gson.toJson(a);
		} else {
			json = "添加失败，请稍后重试！";
		}
		return json;
	}
    public String add2(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

	    CoachFitness a=packageEntity(request);
        Gson gson = new Gson();
        String json = "";
        if (coachFitnessDao.isExist(a.getCoachId(),a.getFitnessId())) {
            json = "该课程的学习者已有该用户";
        } else if (	coachFitnessDao.add(a) ){
            json = gson.toJson(a);
        } else {
            json = "添加失败，请稍后重试！";
        }
        return json;
    }

    public String delete(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        CoachFitness a = packageEntity(request);
        Gson gson = new Gson();
        String json = "";
        if (coachFitnessDao.isExist(a.getCoachId(),a.getFitnessId())) {
            coachFitnessDao.delete(a.getCoachId(),a.getFitnessId());
            dependDao.delete2(a.getCoachId(),a.getFitnessId());
            json = gson.toJson(a);
        } else {
            json = "未找到该项目名称";
        }
        return json;
    }



    public String mydepend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CoachFitness a = packageEntity2(request);
        Gson gson = new Gson();
        List<Course> newsList = courseDao.getmyList2(a.getCoachId());
        String json = gson.toJson(newsList);
        return json;
    }
    public String coursedepend(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CoachFitness a = packageEntity3(request);
        Gson gson = new Gson();
        List<User> newsList = userDao.getmyList2(a.getFitnessId());
        String json = gson.toJson(newsList);
        return json;
    }
	private CoachFitness packageEntity(HttpServletRequest request) {
		String userid2 = request.getParameter("userId");
		String courseid2=request.getParameter("fitnessId");
		int userid;
		int courseid;
        //System.out.println("userid2="+userid2);
		userid=Integer.parseInt(userid2);
		courseid=Integer.parseInt(courseid2);
       // System.out.println("userid="+userid);
		CoachFitness a = new CoachFitness();
		a.setFitnessId(courseid);
		a.setCoachId(userid);

		//System.out.println("a.getcoachid="+a.getCoachId());
		return a;
	}
    private CoachFitness packageEntity2(HttpServletRequest request) {
        String userid2 = request.getParameter("userId");
        int userid;
        userid=Integer.parseInt(userid2);
        CoachFitness a = new CoachFitness();
        a.setCoachId(userid);
        return a;
    }
    private CoachFitness packageEntity3(HttpServletRequest request) {
        String courseid2 = request.getParameter("fitnessId");
        int courseid;
        courseid=Integer.parseInt(courseid2);
        CoachFitness a = new CoachFitness();
        a.setFitnessId(courseid);
        return a;
    }

}
