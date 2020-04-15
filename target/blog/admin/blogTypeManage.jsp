<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客类别管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript">

	var url;

	function deleteBlogType(){
		var selectedRows=$("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			 $.messager.alert("系统提示","请选择要删除的数据！");
			 return;
		 }
		 var strIds=[];
		 for(var i=0;i<selectedRows.length;i++){
			 strIds.push(selectedRows[i].id);
		 }
		 //将数组加入“，”，分割成字符串，传给后台
		 var ids=strIds.join(",");
		 $.messager.confirm("系统提示","您确定要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
				if(r){
					$.post("${pageContext.request.contextPath}/admin/blogType/delete.do",{ids:ids},function(result){
						if(result.success){
							if(result.exist){
								 $.messager.alert("系统提示","改类别下有博客，无法删除！");
							}else{
								 $.messager.alert("系统提示","数据已成功删除！");								
							}
							 $("#dg").datagrid("reload");
						}else{
							$.messager.alert("系统提示","数据删除失败！");
						}
					},"json");
				} 
	   });
	}


	//点击添加按钮让其弹出添加diolog
	function openBlogTypeAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加博客类别信息");
		url="${pageContext.request.contextPath}/admin/blogType/save.do";
	}
	
	function openBlogTypeModifyDialog(){
		var selectedRows=$("#dg").datagrid("getSelections");
		 if(selectedRows.length!=1){
			 $.messager.alert("系统提示","请选择一条要编辑的数据！");
			 return;
		 }
		 var row=selectedRows[0];
		 $("#dlg").dialog("open").dialog("setTitle","编辑博客类别信息");
		 $("#fm").form("load",row);
		 url="${pageContext.request.contextPath}/admin/blogType/save.do?id="+row.id;
	 }

	// 点击添加diaolog的保存按钮会触发该函数，保存类别博客信息
	//这里是easy-ui形式的ajax提交form表单。
	function saveBlogType(){
		 $("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");//这一句自动验证表单中是否有未提交项，如果有，则不继续下去
			},
			success:function(result){
				//当后台传入的是json字符串，需要用javascript处理方式： eval（“（”+result+“）”），可以将json字符串转变成json对象
				var result=eval('('+result+')');
				if(result.success){
					$.messager.alert("系统提示","保存成功！");
					resetValue();
					$("#dlg").dialog("close");
					//关键点！！！保存后刷新显示BT的界面
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示","保存失败！");
					return;
				}
			}
		 });
	 }


	// 重置添加dialog
	function resetValue(){
		 $("#typeName").val("");
		 $("#orderNo").val("");
	 }


	//关闭添加dialog
	 function closeBlogTypeDialog(){
		 $("#dlg").dialog("close");
		 resetValue();
	 }
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="博客类别管理" class="easyui-datagrid"
   fitColumns="true" pagination="true" rownumbers="true"
   url="${pageContext.request.contextPath}/admin/blogType/list.do" fit="true" toolbar="#tb">
   <thead>
   	<tr>
   		<th field="cb" checkbox="true" align="center"></th>
   		<th field="id" width="20" align="center">编号</th>
   		<th field="typeName" width="100" align="center">博客类型名称</th>
   		<th field="orderNo" width="100" align="center">排序序号</th>
   	</tr>
   </thead>
 </table>
 <div id="tb">
 	<div>
 	    <a href="javascript:openBlogTypeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
 		<a href="javascript:openBlogTypeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
 		<a href="javascript:deleteBlogType()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
 	</div>
 </div>

 
 <div id="dlg" class="easyui-dialog" style="width:500px;height:180px;padding: 10px 20px"
   closed="true" buttons="#dlg-buttons">
   
   <form id="fm" method="post">
   	<table cellspacing="8px">
   		<tr>
   			<td>博客类别名称：</td>
   			<td><input type="text" id="typeName" name="typeName" class="easyui-validatebox" required="true"/></td>
   		</tr>
   		<tr>
   			<td>博客类别排序：</td>
   			<td><input type="text" id="orderNo" name="orderNo" class="easyui-numberbox" required="true" style="width: 60px"/>&nbsp;(类别根据排序序号从小到大排序)</td>
   		</tr>
   	</table>
   </form>
 </div>



//上面的dlg的两个按钮
 <div id="dlg-buttons">
 	<a href="javascript:saveBlogType()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
 	<a href="javascript:closeBlogTypeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
 </div>
</body>
</html>