<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dailysubmit" fit="true">
    <table id="dstbs" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>报销状态：</td>
            <td><select class="easyui-combobox" id="dsstatus" name="status" style="width:100px;" onchange='getSubmitSatus()'>
            </select></td>
            <td>报销编号：</td>
            <td><input  id="dscode" name="code"  value=""  style="width: 100px;"></td>
            <td>报销名称：</td>
            <td><input  id="dsrequestname" name="requestname"  value=""  style="width: auto;"></td>
        </tr>
        <tr>
            <td>申请人：</td><td><input  id="dslastname" name="lastname"  value=""  style="width: 100px;"></td>
            <td>申请部门：</td><td><input  id="dsdepartmentname" name="departmentname"  value=""  style="width: 100px;"></td>
            <td>支付日期：</td>
            <td>
                <input id="dsstartdate" type="text" class="easyui-datebox" value="">
                -
                <input id="dsenddate" type="text" class="easyui-datebox" value="">
            </td>

        </tr>
        <tr>
            <td><button id="dsbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="dssearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="dsdg" style="width:auto;height:auto"></table>
</div>
<div id = "dsdia"></div>
<script type="text/javascript" charset="utf-8">
    var dssubmitdata;
    var dsbankvendor;
    function dssearch(){
        var dsstatus = $("#dsstatus").val();
        var dscode = $("#dscode").val();
        var dsrequestname = $("#dsrequestname").val();
        var dslastname = $("#dslastname").val();
        var dsdepartmentname = $("#dsdepartmentname").val();
        var dsstartdate = $("#dsstartdate").val();
        var dsenddate = $("#dsenddate").val();
        $('#dsdg').datagrid({url:'/payregister/queryDailySubmit/'+new Date()});
        $('#dsdg').datagrid('load',{
            "status": dsstatus,
            "code": dscode,
            "requestname": dsrequestname,
            "lastname": dslastname,
            "departmentname": dsdepartmentname,
            "startdate": dsstartdate,
            "enddate": dsenddate
        });
        $('#dsdg').datagrid('reload')
    }

    function getSubmitSatus(){
        $.ajax({
            type: "GET",
            async:false,
            url: "/payregister/selectSubmitStatus",
            success: function (data) {
                dssubmitdata = data.data;
                if (data.code == 200) {
                    var optionlist = "<option value=\'\'>全部</option>";
                    //动态添加1级菜单按钮
                    for (var i = 0; i < data.data.length; i++) {
                        optionlist+= "<option value=\""+data.data[i].selectvalue+"\">";
                        optionlist+= data.data[i].selectname;
                        optionlist+= "</option>";
                    }
                    $('#dsstatus').append(optionlist);
                }
            }
        });
    }
    $(function(){
        getSubmitSatus();

        $('#dsdg').datagrid({
            url:'/payregister/queryDailySubmit/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            pageSize:20,
            pageList:[10,20,30],
            fitColums:true,
            nowrap:true,
            frozenColumns:[[                //冻结列特性 ,不要与fitColumns 特性一起使用
                {                           //如果需要多选，需要禁止单选模式
                    field:'ck' ,
                    width:50 ,
                    checkbox: true
                }
            ]],
            columns:[[
                {field:"id",title:'编号',width:50,align:'center',hidden:"true"},
                {field:"requestname",title:'报销名称',width:100,align:'center'},
                {field:"code",title:'报销编号',width:100,align:'center'},
                {field:"status",title:'报销状态',width:100,align:'center',
                    formatter:function(value){
                        for (var i=0;i<dssubmitdata.length;i++){
                            if( value == dssubmitdata[i].selectvalue){
                                return dssubmitdata[i].selectname;
                            }
                        }
                    }},
                {field:"lastname",title:'申请人',width:100,align:'center'},
                {field:"departmentname",title:'申请部门',width:100,align:'center'},
                {field:"payer_bank",title:'付款银行',width:100,align:'center'},
                {field:"bank",title:'收款银行',width:100,align:'center'},
                {field:"payer_account",title:'付款账号',width:100,align:'center'},
                {field:"vendor",title:'付款单位',width:100,align:'center'},
                {field:"pay_date",title:'付款日期',width:100,align:'center'} ,
                {field:"pay_amount",title:'付款金额',width:100,align:'center'}

            ]],
            toolbar: [{
                text:'付款',
                iconCls: 'icon-edit',
                handler: function(){
                    var dsids = [];
                    var dsvendors = [];
                    var rows = $('#dsdg').datagrid('getSelections');
                    for(var i=0; i<rows.length; i++){
                        var row = rows[i];
                        dsids.push(row.id);
                        dsvendors.push(row.vendor);
                    }
                    if (rows == null || rows == ''){
                        alert('请至少选择一条付款信息');
                        return;
                    }
                    dsbankvendor = rows[0].vendor;
                    $.ajax({
                        url:"/payregister/getids",
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":dsids.toString(),"vendors":dsvendors.toString()},
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#dsdia').dialog({
                                    title: "付款",
                                    width: 600,
                                    height: 400,
                                    closed: false,
                                    cache: false,
                                    href: '/jsp/DSpayselect.jsp',
                                    modal: true ,
                                });
                            } else {
                                alert(data.msg);
                            }

                        }
                    });


                }
            }],
        });
    });
</script>