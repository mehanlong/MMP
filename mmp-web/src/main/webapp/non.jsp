<%--
  Created by IntelliJ IDEA.
  User: jinkun
  Date: 2017/1/12
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>拒绝访问</title>
    <script>
        $(function(){
            var cc_days = 0.35;
            var b = cc_days.split(".");
            var x=b[0];
            var y=b[1];
            var c ='';
            var d =0;
            if(y>17&&y<33){
                y=50;
                c =x +'.' +y;
                $("#field5926").val(c);
            }
            if (y>33){
                d=x+1;
                $("#field5926").val(d);
            }
        });
    </script>
</head>
<body>
    暂时没有开发
</body>
</html>
