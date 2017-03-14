<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="receiveloan" fit="true">
    <table id="rltbs" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>借款状态：</td>
            <td><select class="easyui-combobox" id="rlstatus" name="status" style="width:100px;" onchange='getSubmitSatus()'>
            </select></td>
            <td>借款编号：</td>
            <td><input  id="rlcode" name="code"  value=""  style="width: 100px;"></td>
            <td>借款名称：</td>
            <td><input  id="rlrequestname" name="requestname"  value=""  style="width: auto;"></td>
        </tr>
        <tr>
            <td>申请人：</td><td><input  id="rllastname" name="lastname"  value=""  style="width: 100px;"></td>
            <td>申请部门：</td><td><input  id="rldepartmentname" name="departmentname"  value=""  style="width: 100px;"></td>
            <td>支付日期：</td>
            <td>
                <input id="rlstartdate" type="text" class="easyui-datebox" value="">
                -
                <input id="rlenddate" type="text" class="easyui-datebox" value="">
            </td>

        </tr>
        <tr>
            <td><button id="rlbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="rlsearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="rldg" style="width:auto;height:auto"></table>
</div>
<div id = "rldia"></div>
<script type="text/javascript" charset="utf-8">
    var rlsubmitdata;
    var rlbankvendor;
    function rlsearch(){
        var rlstatus = $("#rlstatus").val();
        var rlcode = $("#rlcode").val();
        var rlrequestname = $("#rlrequestname").val();
        var rllastname = $("#rllastname").val();
        var rldepartmentname = $("#rldepartmentname").val();
        var rlstartdate = $("#rlstartdate").val();
        var rlenddate = $("#rlenddate").val();
        $('#rldg').datagrid({url:'/payregister/queryReceiveLoan/'+new Date()});
        $('#rldg').datagrid('load',{
            "status": rlstatus,
            "code": rlcode,
            "requestname": rlrequestname,
            "lastname": rllastname,
            "departmentname": rldepartmentname,
            "startdate": rlstartdate,
            "enddate": rlenddate
        });
        $('#rldg').datagrid('reload')
    }

    function getSubmitSatus(){
        $.ajax({
            type: "GET",
            async:false,
            url: "/payregister/selectSubmitStatus",
            success: function (data) {
                rlsubmitdata = data.data;
                if (data.code == 200) {
                    var optionlist = "<option value=\'\'>全部</option>";
                    //动态添加1级菜单按钮
                    for (var i = 0; i < data.data.length; i++) {
                        optionlist+= "<option value=\""+data.data[i].selectvalue+"\">";
                        optionlist+= data.data[i].selectname;
                        optionlist+= "</option>";
                    }
                    $('#rlstatus').append(optionlist);
                }
            }
        });
    }
    $(function(){
        getSubmitSatus();

        $('#rldg').datagrid({
            url:'/payregister/queryReceiveLoan/'+new Date(),
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
                {field:"requestname",title:'借款名称',width:100,align:'center'},
                {field:"code",title:'借款编号',width:100,align:'center'},
                {field:"status",title:'借款状态',width:100,align:'center',
                    formatter:function(value){
                        for (var i=0;i<rlsubmitdata.length;i++){
                            if( value == rlsubmitdata[i].selectvalue){
                                return rlsubmitdata[i].selectname;
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
                {field:"loan_amount",title:'付款金额',width:100,align:'center'}

            ]],
            toolbar: [{
                text:'付款',
                iconCls: 'icon-edit',
                handler: function(){
                    var rlids = [];
                    var rlvendors = [];
                    var rlrows = $('#rldg').datagrid('getSelections');
                    for(var i=0; i<rlrows.length; i++){
                        var rlrow = rlrows[i];
                        rlids.push(rlrow.id);
                        rlvendors.push(rlrow.vendor);
                    }
                    if (rlrows == null || rlrows == ''){
                        alert('请至少选择一条付款信息');
                        return;
                    }
                    rlbankvendor = rlrows[0].vendor;
                    $.ajax({
                        url:"/payregister/getids",
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":rlids.toString(),"vendors":rlvendors.toString()},
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#rldia').dialog({
                                    title: "付款",
                                    width: 600,
                                    height: 400,
                                    closed: false,
                                    cache: false,
                                    href: '/jsp/RLpayselect.jsp',
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