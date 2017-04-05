<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dailycontract" fit="true">
    <table id="dctbs" class="tabclass" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>合同状态：</td>
            <td><select class="easyui-combobox" id="dcstatus" name="status" style="width:100px;" onchange='getSubmitSatus()'>
            </select></td>
            <td>合同编号：</td>
            <td><input  id="dcpayment_code" name="payment_code"  value=""  style="width: auto;"></td>

            <td></td><td></td>
            <td></td><td></td>
        </tr>
        <tr>
            <%--<td>申请人：</td><td><input  id="dclastname" name="lastname"  value=""  style="width: 100px;"></td>--%>
            <%--<td>申请部门：</td><td><input  id="dcdepartmentname" name="departmentname"  value=""  style="width: 100px;"></td>--%>
            <td>合同名称：</td>
            <td><input  id="dcrequestname" name="requestname"  value=""  style="width: auto;"></td>
            <td>付款单位：</td>
            <td><input  id="dcvendor" name="vendor"  value=""  style="width: auto;"></td>
            <td>支付日期：</td>
            <td>
                <input id="dcstartdate" type="text" class="easyui-datebox" value="">
                -
                <input id="dcenddate" type="text" class="easyui-datebox" value="">
            </td>
            <td></td><td></td>

        </tr>
        <tr>
            <td><button id="dcbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="dcsearch()">查询</button></td>
            <td><button id="dcpay" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="dcpay()">付款</button></td>
        </tr>
    </table>
    <br>

    <table id="dcdg" style="width:auto;height:auto"></table>
</div>
<div id = "dcdia"></div>
<script type="text/javascript" charset="utf-8">
    var dcsubmitdata;
    var dcbankvendor;
    function dcsearch(){
        var dcstatus = $("#dcstatus").val();
        var dcpayment_code = $("#dcpayment_code").val();
        var dcrequestname = $("#dcrequestname").val();
        var dclastname = $("#dclastname").val();
        var dcdepartmentname = $("#dcdepartmentname").val();
        var dcstartdate = $("#dcstartdate").val();
        var dcenddate = $("#dcenddate").val();
        var dcvendor = $("#dcvendor").val();
        $('#dcdg').datagrid('clearSelections');
        $('#dcdg').datagrid('load',{
            "vendor": dcvendor,
            "status": dcstatus,
            "payment_code": dcpayment_code,
            "requestname": dcrequestname,
            "lastname": dclastname,
            "departmentname": dcdepartmentname,
            "startdate": dcstartdate,
            "enddate": dcenddate,
        });
    }


    function dcpay(){
        var dcids = [];
        var dcvendors = [];
        var dcrows = $('#dcdg').datagrid('getSelections');
        for(var i=0; i<dcrows.length; i++){
            var dcrow = dcrows[i];
            dcids.push(dcrow.id);
            dcvendors.push(dcrow.vendor);
        }
        if (dcrows == null || dcrows == ''){
            $.messager.alert('提示','请至少选择一条付款信息');
            return;
        }
        dcbankvendor = dcrows[0].vendor;
        $.ajax({
            url:"/payregister/getids",
            type:"POST",
            dateType:"JSON",
            data:{"ids":dcids.toString(),"vendors":dcvendors.toString()},
            async:false,
            success:function(data){
                if (data.code == 200){
                    $('#dcdia').dialog({
                        title: "付款",
                        width: 600,
                        height: 400,
                        closed: false,
                        cache: false,
                        href: '/jsp/DCpayselect.jsp',
                        modal: true ,
                    });
                } else {
                    $.messager.alert('提示',data.msg);
                }

            }
        });
    }

    function getSubmitSatus(){
        $.ajax({
            type: "GET",
            async:false,
            url: "/payregister/selectSubmitStatus",
            success: function (data) {
                dcsubmitdata = data.data;
                if (data.code == 200) {
                    var optionlist = "<option value=\'\'>全部</option>";
                    //动态添加1级菜单按钮
                    for (var i = 0; i < data.data.length; i++) {
                        if (data.data[i].selectname == '审单通过'){
                            optionlist+= "<option value=\""+data.data[i].selectvalue+"\" selected=\"selected\">";
                            optionlist+= data.data[i].selectname;
                            optionlist+= "</option>";
                        } else {
                            optionlist+= "<option value=\""+data.data[i].selectvalue+"\">";
                            optionlist+= data.data[i].selectname;
                            optionlist+= "</option>";
                        }
                    }
                    $('#dcstatus').append(optionlist);
                }
            }
        });
    }
    $(function(){
        getSubmitSatus();

        $('#dcdg').datagrid({
            url:'/payregister/queryContract/0/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            queryParams:{status:$("#dcstatus").val()},
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
                {field:"lastname",title:'申请人',width:100,align:'center',hidden:"true"},
                {field:"departmentname",title:'申请部门',width:100,align:'center',hidden:"true"},
                {field:"bank",title:'收款银行',width:100,align:'center',hidden:"true"},

                {field:"vendor",title:'付款单位',width:100,align:'center'},
                {field:"requestname",title:'合同名称',width:100,align:'center'},
                {field:"payment_code",title:'合同编号',width:100,align:'center'},
                {field:"status",title:'合同状态',width:100,align:'center',
                    formatter:function(value){
                        for (var i=0;i<dcsubmitdata.length;i++){
                            if( value == dcsubmitdata[i].selectvalue){
                                return dcsubmitdata[i].selectname;
                            }
                        }
                    }},
                {field:"revendor",title:'收款单位',width:100,align:'center'},
                {field:"reaccount",title:'收款账号',width:100,align:'center'},
                {field:"payer_bank",title:'付款银行',width:100,align:'center'},
                {field:"payer_bank_account",title:'付款账号',width:100,align:'center'},
                {field:"amount",title:'付款金额',width:100,align:'center'},
                {field:"pay_date",title:'付款日期',width:100,align:'center'}

            ]],
        });
    });
</script>