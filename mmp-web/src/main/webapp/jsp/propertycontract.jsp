<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="propertycontract" fit="true">
    <table id="dctbs" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>合同状态：</td>
            <td><select class="easyui-combobox" id="pcstatus" name="status" style="width:100px;" onchange='getSubmitSatus()'>
            </select></td>
            <td>合同编号：</td>
            <td><input  id="pccode" name="code"  value=""  style="width: 100px;"></td>
            <td>合同名称：</td>
            <td><input  id="pcrequestname" name="requestname"  value=""  style="width: auto;"></td>
        </tr>
        <tr>
            <td>申请人：</td><td><input  id="pclastname" name="lastname"  value=""  style="width: 100px;"></td>
            <td>申请部门：</td><td><input  id="pcdepartmentname" name="departmentname"  value=""  style="width: 100px;"></td>
            <td>支付日期：</td>
            <td>
                <input id="pcstartdate" type="text" class="easyui-datebox" value="">
                -
                <input id="pcenddate" type="text" class="easyui-datebox" value="">
            </td>

        </tr>
        <tr>
            <td><button id="pcbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="pcsearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="pcdg" style="width:auto;height:auto"></table>
</div>
<div id = "pcdia"></div>
<script type="text/javascript" charset="utf-8">
    var pcsubmitdata;
    var pcbankvendor;
    function pcsearch(){
        var pcstatus = $("#pcstatus").val();
        var pccode = $("#pccode").val();
        var pcrequestname = $("#pcrequestname").val();
        var pclastname = $("#pclastname").val();
        var pcdepartmentname = $("#pcdepartmentname").val();
        var pcstartdate = $("#pcstartdate").val();
        var pcenddate = $("#pcenddate").val();
        $('#pcdg').datagrid({url:'/payregister/queryContract/1/'+new Date()});
        $('#pcdg').datagrid('load',{
            "status": pcstatus,
            "code": pccode,
            "requestname": pcrequestname,
            "lastname": pclastname,
            "departmentname": pcdepartmentname,
            "startdate": pcstartdate,
            "enddate": pcenddate
        });
        $('#pcdg').datagrid('reload')
    }

    function getSubmitSatus(){
        $.ajax({
            type: "GET",
            async:false,
            url: "/payregister/selectSubmitStatus",
            success: function (data) {
                pcsubmitdata = data.data;
                if (data.code == 200) {
                    var optionlist = "<option value=\'\'>全部</option>";
                    //动态添加1级菜单按钮
                    for (var i = 0; i < data.data.length; i++) {
                        optionlist+= "<option value=\""+data.data[i].selectvalue+"\">";
                        optionlist+= data.data[i].selectname;
                        optionlist+= "</option>";
                    }
                    $('#pcstatus').append(optionlist);
                }
            }
        });
    }
    $(function(){
        getSubmitSatus();

        $('#pcdg').datagrid({
            url:'/payregister/queryContract/1/'+new Date(),
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
                {field:"requestname",title:'合同名称',width:100,align:'center'},
                {field:"code",title:'合同编号',width:100,align:'center'},
                {field:"status",title:'合同状态',width:100,align:'center',
                    formatter:function(value){
                        for (var i=0;i<pcsubmitdata.length;i++){
                            if( value == pcsubmitdata[i].selectvalue){
                                return pcsubmitdata[i].selectname;
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
                {field:"amount",title:'付款金额',width:100,align:'center'}

            ]],
            toolbar: [{
                text:'付款',
                iconCls: 'icon-edit',
                handler: function(){
                    var pcids = [];
                    var pcvendors = [];
                    var pcrows = $('#pcdg').datagrid('getSelections');
                    for(var i=0; i<pcrows.length; i++){
                        var pcrow = pcrows[i];
                        pcids.push(pcrow.id);
                        pcvendors.push(pcrow.vendor);
                    }
                    if (pcrows == null || pcrows == ''){
                        alert('请至少选择一条付款信息');
                        return;
                    }
                    pcbankvendor = pcrows[0].vendor;
                    $.ajax({
                        url:"/payregister/getids",
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":pcids.toString(),"vendors":pcvendors.toString()},
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#pcdia').dialog({
                                    title: "付款",
                                    width: 600,
                                    height: 400,
                                    closed: false,
                                    cache: false,
                                    href: '/jsp/PCpayselect.jsp',
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