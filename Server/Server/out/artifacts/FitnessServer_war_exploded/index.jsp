
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ page import="java.sql.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    <title>My JSP 'testconnection.jsp' starting page</title>
</head>

<body>
<%
    //student为数据库名字，加上useSSL=true不然报错
    String url="jdbc:mysql://localhost:3306/fitness_mysql?useSSL=true";
    String useName="root";
    String password="1234";
    Connection conn=null;
    try{
        Class.forName("com.mysql.jdbc.Driver");
    }
    catch(ClassNotFoundException e){
        out.print("加载驱动器类出现异常");
    }
    try{
        conn=null;
        conn=DriverManager.getConnection(url,useName,password);
    }
    catch(SQLException e){
        out.print("连接数据库的过程中出现SQL异常");
    }
    if(conn==null){
        out.print("连接数据库失败");
    }
    else{
        out.print("连接数据库成功 服务器正在运行");
    }


%>
</body>
</html>