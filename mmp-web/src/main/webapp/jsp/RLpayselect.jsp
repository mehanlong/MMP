<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="rlbankselect" fit="true">
    <table id="rltbsbank" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>账号：</td><td><input  id="rlbkaccount" name="account"  value=""  style="width: 100px;"></td>
            <td>银行名称：</td><td><input  id="rlbkbank" name="bank"  value=""  style="width: 100px;"></td>
            <%--<td>付款单位：</td><td><input id="vendor" name="vendor" value="" style="width: 100px;"></td>--%>

        </tr>
        <tr>
            <td><button id="rlbkbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="rlbksearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="rlbkdgbank" style="width:auto;height:auto"></table>
</div>
<script type="text/javascript" charset="utf-8">
    function rlbksearch(){
        var rlbkaccount = $("#rlbkaccount").val();
        var rlbkbank = $("#rlbkbank").val();
//        var vendor = $("#vendor").val();
        $('#rlbkdgbank').datagrid({url:'/payregister/queryBankOption/'+new Date()});
        $('#rlbkdgbank').datagrid('load',{
            "account": rlbkaccount,
            "bank": rlbkbank,
            "vendor": rlbankvendor
        });
    }

    $(function(){

        $('#rlbkdgbank').datagrid({
            url:'/payregister/queryBankOption/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            queryParams:{vendor:rlbankvendor},
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
                    var rlbkids = [];
                    var rlbkrows = $('#rldg').datagrid('getSelections');
                    for(var i=0; i<rlbkrows.length; i++){
                        var rlbkrow = rlbkrows[i];
                        rlbkids.push(rlbkrow.id);
                    }
                    var rlbkbanks = $('#rlbkdgbank').datagrid('getSelected');
                    $.ajax({
                        url:"/payregister/changebank/3/"+new Date(),
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":rlbkids.toString(),
                            "bankid":rlbkbanks.bank_id,
                            "account":rlbkbanks.account,
                            "bank":rlbkbanks.bank
                        },
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#rldia').dialog('close');
                                $('#rldg').datagrid('reload');
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