package com.app.servlet;

import com.app.entity.Calories;
import com.app.entity.Course;
import com.app.entity.Notice;
import com.app.entity.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class NoticeServlet extends BaseMobileServlet {

	private static final long serialVersionUID= -318394635533549355L;

	public String update(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {

		Notice a = packageEntity(request);
		Gson gson = new Gson();
		String json = "";

		if (noticeDao.update(a)){

			return "success";
		}
		else{
			return "error";
		}
	}
    public String found(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";
        Notice a;
        int b=1;
        a=noticeDao.found(b);
        json = gson.toJson(a);

        return json;
    }

	private Notice packageEntity(HttpServletRequest request) {
		String content = request.getParameter("content");
		Notice a = new Notice();
		a.setNotice(content);
		return a;
	}

}
