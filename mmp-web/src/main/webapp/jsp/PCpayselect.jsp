<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="pcbankselect" fit="true">
    <table id="pctbsbank" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>账号：</td><td><input  id="pcbkaccount" name="account"  value=""  style="width: 100px;"></td>
            <td>银行名称：</td><td><input  id="pcbkbank" name="bank"  value=""  style="width: 100px;"></td>
            <%--<td>付款单位：</td><td><input id="vendor" name="vendor" value="" style="width: 100px;"></td>--%>

        </tr>
        <tr>
            <td><button id="pcbkbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="pcbksearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="pcbkdgbank" style="width:auto;height:auto"></table>
</div>
<script type="text/javascript" charset="utf-8">
    function pcbksearch(){
        var pcbkaccount = $("#pcbkaccount").val();
        var pcbkbank = $("#pcbkbank").val();
//        var vendor = $("#vendor").val();
        $('#pcbkdgbank').datagrid({url:'/payregister/queryBankOption/'+new Date()});
        $('#pcbkdgbank').datagrid('load',{
            "account": pcbkaccount,
            "bank": pcbkbank,
            "vendor": pcbankvendor
        });
    }

    $(function(){

        $('#pcbkdgbank').datagrid({
            url:'/payregister/queryBankOption/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            queryParams:{vendor:pcbankvendor},
            pageSize:10,
            pageList:[10,20,30],
            fitColums:true,
            nowrap:true,
            singleSelect:true,
            columns:[[
                {field:"bank_id",title:'编号',width:100,align:'center',hidden:"true"},
                {field:"account",title:'账号',width:150,align:'center'},
                {field:"bank",title:'银行',width:150,align:'center'},
                {field:"vendor",title:'付款单位',width:150,align:'center'}

            ]],
            toolbar: [{
                text:'选择',
                iconCls: 'icon-edit',
                handler: function(){
                    var pcbkids = [];
                    var pcbkrows = $('#pcdg').datagrid('getSelections');
                    for(var i=0; i<pcbkrows.length; i++){
                        var pcbkrow = pcbkrows[i];
                        pcbkids.push(pcbkrow.id);
                    }
                    var pcbkbanks = $('#pcbkdgbank').datagrid('getSelected');
                    $.ajax({
                        url:"/payregister/changebank/1/"+new Date(),
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":pcbkids.toString(),
                            "bankid":pcbkbanks.bank_id,
                            "account":pcbkbanks.account,
                            "bank":pcbkbanks.bank
                        },
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#pcdia').dialog('close');
                                $('#pcdg').datagrid('reload');
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