<%@ page contentType="text/html;charset=utf-8"%>

<%@ page import="java.sql.*"%>

<html>

<body>

<%

    Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    out.println("成功加载MySQL驱动程序");

    String url ="jdbc:mysql://localhost:3306/fitness_mysql?"+"user=root2&password=1234&useUnicode=true&characterEncoding=utf-8";

    Connection conn= DriverManager.getConnection(url);

    Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

    String sql;
    sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
    int result = stmt.executeUpdate(sql);
    if (result != -1) {
        System.out.println("创建数据表成功");
        sql = "insert into student(NO,name) values('2012001','陶伟基')";
        result = stmt.executeUpdate(sql);
        sql = "insert into student(NO,name) values('2012002','周小俊')";
        result = stmt.executeUpdate(sql);
        sql = "select * from student";
        ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
        System.out.println("学号\t姓名");
        while (rs.next()) {
            System.out
                    .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
        }
    }




%>

</body>

</html>


</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'index.jsp' starting page</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
</head>

<body>
This is my JSP page. <br>
</body>
</html>