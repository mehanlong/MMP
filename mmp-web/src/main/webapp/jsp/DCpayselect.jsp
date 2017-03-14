<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dcbankselect" fit="true">
    <table id="dctbsbank" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>账号：</td><td><input  id="dcbkaccount" name="account"  value=""  style="width: 100px;"></td>
            <td>银行名称：</td><td><input  id="dcbkbank" name="bank"  value=""  style="width: 100px;"></td>

        </tr>
        <tr>
            <td><button id="dcbkbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="dcbksearch()">查询</button></td>
        </tr>
    </table>
    <br>

    <table id="dcbkdgbank" style="width:auto;height:auto"></table>
</div>
<script type="text/javascript" charset="utf-8">
    function dcbksearch(){
        $('#dcbkdgbank').datagrid({url:'/payregister/queryBankOption/'+new Date()});
        $('#dcbkdgbank').datagrid('load',{
            "account": $("#dcbkaccount").val(),
            "bank": $("#dcbkbank").val(),
            "vendor": dcbankvendor
        });
    }

    $(function(){
        $('#dcbkdgbank').datagrid({
            url:'/payregister/queryBankOption/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            queryParams:{vendor:dcbankvendor},
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
                    var dcbkids = [];
                    var dcbkrows = $('#dcdg').datagrid('getSelections');
                    for(var i=0; i<dcbkrows.length; i++){
                        var dcbkrow = dcbkrows[i];
                        dcbkids.push(dcbkrow.id);
                    }
                    var dcbkbanks = $('#dcbkdgbank').datagrid('getSelected');
                    $.ajax({
                        url:"/payregister/changebank/0/"+new Date(),
                        type:"POST",
                        dateType:"JSON",
                        data:{"ids":dcbkids.toString(),
                            "bankid":dcbkbanks.bank_id,
                            "account":dcbkbanks.account,
                            "bank":dcbkbanks.bank
                        },
                        async:false,
                        success:function(data){
                            if (data.code == 200){
                                $('#dcdia').dialog('close');
                                $('#dcdg').datagrid('reload');
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