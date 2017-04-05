<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dsbankselect" fit="true">
    <table id="dstbsbank" class="tabclass" style="width: auto; height: auto; " cellspacing="0" border="0">
        <tr>
            <td>账号：</td><td><input  id="dsbkaccount" name="account"  value=""  style="width: 100px;"></td>
            <td>银行名称：</td><td><input  id="dsbkbank" name="bank"  value=""  style="width: 100px;"></td>
            <%--<td>付款单位：</td><td><input id="vendor" name="vendor" value="" style="width: 100px;"></td>--%>

        </tr>
        <tr>
            <td><button id="dsbkbuts" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="dsbksearch()">查询</button></td>
            <td><button id="dsbksel" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="dsbksel()">确定</button></td>
        </tr>
    </table>
    <br>

    <table id="dsbkdgbank" style="width:auto;height:auto"></table>
</div>
<script type="text/javascript" charset="utf-8">
    function dsbksearch(){
        var dsbkaccount = $("#dsbkaccount").val();
        var dsbkbank = $("#dsbkbank").val();
//        var dsbkvendor = $("#dsbkvendor").val();
//        $('#dsbkdgbank').datagrid({url:'/payregister/queryBankOption/'+new Date()});
        $('#dsbkdgbank').datagrid('clearSelections');
        $('#dsbkdgbank').datagrid('load',{
            "account": dsbkaccount,
            "bank": dsbkbank,
            "vendor": dsbankvendor
        });
    }
    function dsbksel(){
        var dsbkids = [];
        var dsbkrows = $('#dsdg').datagrid('getSelections');
        for(var i=0; i<dsbkrows.length; i++){
            var dsbkrow = dsbkrows[i];
            dsbkids.push(dsbkrow.id);
        }
        var dsbkbanks = $('#dsbkdgbank').datagrid('getSelected');
        $.ajax({
            url:"/payregister/changebank/2/"+new Date(),
            type:"POST",
            dateType:"JSON",
            data:{"ids":dsbkids.toString(),
                "bankid":dsbkbanks.bank_id,
                "account":dsbkbanks.account,
                "bank":dsbkbanks.bank
            },
            async:false,
            success:function(data){
                if (data.code == 200){
                    $('#dsdg').datagrid('clearSelections');
                    $('#dsdia').dialog('close');
                    $('#dsdg').datagrid('reload');
                } else {
                    $.messager.alert('提示',data.msg);
                }

            }
        });
    }

    $(function(){

        $('#dsbkdgbank').datagrid({
            url:'/payregister/queryBankOption/'+new Date(),
            idField: 'id',    //只要创建数据表格 就必须要加 ifField
            border:false,
            pagination:true,
            striped: true ,                 //隔行变色特性
            rownumbers:true,                //显示行号
            queryParams:{vendor:dsbankvendor},
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
//            toolbar: [{
//                text:'选择',
//                iconCls: 'icon-edit',
//                handler: function(){
//                    var dsbkids = [];
//                    var dsbkrows = $('#dsdg').datagrid('getSelections');
//                    for(var i=0; i<dsbkrows.length; i++){
//                        var dsbkrow = dsbkrows[i];
//                        dsbkids.push(dsbkrow.id);
//                    }
//                    var dsbkbanks = $('#dsbkdgbank').datagrid('getSelected');
//                    $.ajax({
//                        url:"/payregister/changebank/2/"+new Date(),
//                        type:"POST",
//                        dateType:"JSON",
//                        data:{"ids":dsbkids.toString(),
//                            "bankid":dsbkbanks.bank_id,
//                            "account":dsbkbanks.account,
//                            "bank":dsbkbanks.bank
//                        },
//                        async:false,
//                        success:function(data){
//                            if (data.code == 200){
//                                $('#dsdia').dialog('close');
//                                $('#dsdg').datagrid('reload');
//                            } else {
//                                alert(data.msg);
//                            }
//
//                        }
//                    });
//
//                }
//            }],
        });
    });
</script>