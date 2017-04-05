<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@include file="common/tag.jsp" %>
<html>
<head>
    <title>首页</title>
    <%@include file="common/header.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/index.css"/>
    <script>
        $(function () {
            //加载当前用户的1级菜单
            $.ajax({
                type: "GET",
                url: "/test/getTopMenu",
                success: function (data) {
                    if (data.code == 200) {
                        $("#ddd").append("<img src=\"/images/BacoError_wev8.gif\" align=\"absmiddle\">");
                        var menulist = "";
                        //动态添加1级菜单按钮
                        for (var i = 0; i < data.data.length; i++) {
                            menulist+= "<ul id=\"menu"+data.data[i].id+"\">";
                            menulist+= getChildMenu(data.data[i].id);
                            menulist+= "</ul>";
                            if (i==0){
                                $("#mainmenu").accordion("add",{
                                    title: data.data[i].description,
                                    content:menulist,
                                    selected:true
                                })
                            } else {
                                $("#mainmenu").accordion("add",{
                                    title: data.data[i].description,
                                    content:menulist,
                                    selected:false
                                })
                            }
                            menulist="";
                        }
                    }
                }
            });
            //退出登录
            $("#logout").linkbutton({
                onClick: function () {
                    $.ajax({
                        type: "GET",
                        url: "/logout",
                        async: false,
                        success: function (data) {
                            if (data.code == 200) {
                                window.location.href = "/login.jsp";
                            }
                        }
                    });
                }
            });


        });

        function getChildMenu(id){
            var childlist = "";
            $.ajax({
                type:"GET",
                url:"/test/getChiledMenu/"+id,
                async: false,
                success:function(data){
                    if (data.code == 200){
                        for (var i = 0; i < data.data.length; i++) {
                            childlist += "<li style=\"padding:10px\">"+
                                    "<span>" +
                                    "<a href=\"javascript:addTab(\'"+data.data[i].url+"\',\'"+id+"\',\'"+data.data[i].description+  "\');\" target=\"mainFrame\" class=\"whitelink\">"+data.data[i].description+"</a>" +
                                    "</span></li>";
                        }

                    }
                }
            });
            return childlist;
        }

        function addTab(path,id,title){
            var obj=null;
            $.ajax({
                url:path,
                type:"POST",
                dateType:"JSON",
                async:false,
                success:function(data){
                    if ($('#tabs').tabs('exists',title)){
                        $('#tabs').tabs('select',title);
//                        var currentTab = $('#tabs').tabs('getSelected');
//                        RefreshTab(currentTab);
                    } else{
                        $('#tabs').tabs('add',{
                            id:id,
                            href:data,
                            title:title,
                            closable:true
                        });
                    }
                }
            });
        }

        function RefreshTab(currentTab) {
                    var url = $(currentTab.panel('options')).attr('href');
                    $('#tabs').tabs('update', {
                             tab: currentTab,
                                 options: {
                                 href: url
                             }
                    });
                    currentTab.panel('refresh');
               }
    </script>
</head>
<body class="easyui-layout">
<%--<div data-options="region:'north',title:''" style="height: 40px;">--%>
    <%--<div class="headerTop">--%>
        <%--<!-- 一级菜单 -->--%>
        <%--<div id="topMenu" style="float: left; margin-top: 5px;"></div>--%>

        <%--<!-- 用户信息 -->--%>
        <%--&lt;%&ndash;<span style="float: right;margin-right: 10px;text-align: center">&ndash;%&gt;--%>
            <%--&lt;%&ndash;<a href="#" id="logout" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">退出系统</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</span>&ndash;%&gt;--%>
    <%--</div>--%>
<%--</div>--%>
<div data-options="region:'west',title:'菜单',split:false" style="width:180px;">
    <div id="mainmenu" class="easyui-accordion" fit="true" border="false">

    </div>
</div>
<div data-options="region:'center',title:''">
    <div id="tabs" class="easyui-tabs" style="width: 100%;" border="false">

    </div>
</div>
</body>
</html>
