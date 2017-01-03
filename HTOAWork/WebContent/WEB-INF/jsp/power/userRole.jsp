<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
	<link href="${pageContext.request.contextPath}/css/plugins/toastr/toastr.min.css" rel="stylesheet">
    <script>
	    var userRow = null;
	    var addRow = null;
	    var delRow = null;
	    $(function(){
	    	//激活提示
			$("[data-toggle='tooltip']").tooltip();
	    	$("#btn_user_add").click(function(){
	    		if(userRow==null){
					parent.layer.alert('请选择用户');
					return;
				}
	    		var url = "";
	    		if(userRow!=null){
	    			url = '${pageContext.request.contextPath}/power/roleListJson/' + userRow.id+'/not';
	    		}else{
	    			url = '${pageContext.request.contextPath}/power/roleListJson';
	    		}
	    		$('#tb_role').bootstrapTable('refresh', { url:url}); 
	    		$("#btn_role_save").attr("onclick","addRole()");
	    		$("#window_role").modal('show');
	        });
	    	$("#btn_user_delete").click(function(){
	    		if(userRow==null){
					parent.layer.alert('请选择用户');
					return;
				}
	    		var url = "";
	    		if(userRow!=null){
	    			url = '${pageContext.request.contextPath}/power/roleListJson/' + userRow.id;
	    		}else{
	    			url = '';
	    		}
	    		$("#btn_role_save2").attr("onclick","delRole()");
	    		$('#tb_role2').bootstrapTable('refresh', { url:url}); 
	    		$("#window_role2").modal('show');
	        });
			$('#tb_user').bootstrapTable({
				url : '${pageContext.request.contextPath}/user/userListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_user', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(userRow != null){
	                	userRow = null;
	                }
	                userRow = row;
	            },
	            onUncheck: function(row) {
	                if(userRow != null){
	                	userRow = null;
	                }
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '用户名'
				}, {
					field : 'phone',
					title : '电话'
				},{
					field : 'pwd',
					title : '密码',
					formatter : function(value, row, index) {
						var pwd = row.pwd;
						var text = "";
						if(pwd==null){
							return "-";
						}else{
							for(var i=0;i<pwd.length;i++){
								text = text +"*";
							}
							return text;
						}
					}
				},{
					field : 'rolename',
					title : '角色',
					formatter : function(value, row, index) {
						var roleList = row.roleList;
						var text = "";
						if(roleList==null){
							return "-";
						}else{
							$.each(roleList,function(i){
								if(i!=(roleList.length-1)){
									text = text +roleList[i].name + ' , ';
								}else{
									text = text +roleList[i].name;
								}
							});
							return text;
						}
					}
				},{
					field : 'id',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='respwd("+id+")'>密码初始化</a>";
					}
				},]
			});
			
			
			//表格数据
			$('#tb_role').bootstrapTable({
				url : '${pageContext.request.contextPath}/power/roleListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_role', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(addRow != null){
	                	addRow = null;
	                }
	                addRow = row;
	            },
	            onUncheck: function(row) {
	                if(addRow != null){
	                	addRow = null;
	                }
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '角色名'
				}, {
					field : 'roleDesc',
					title : '描述'
				},]
			});
			
			$('#tb_role2').bootstrapTable({
				url : '', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(delRow != null){
	                	delRow = null;
	                }
	                delRow = row;
	            },
	            onUncheck: function(row) {
	                if(delRow != null){
	                	delRow = null;
	                }
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '角色名'
				}, {
					field : 'roleDesc',
					title : '描述'
				},]
			});
	    });
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
			};
			return temp;
		}
		function addRole(){
			if(addRow == null){
				return;
			}
			var url = "${pageContext.request.contextPath }/user/addRole/"+userRow.id+"/"+addRow.id;
			$.post(
				url,
				{
					
				},
				function(data){
					if(data>0){
						//这是弹窗的代码
						parent.layer.alert('添加成功！');
					}else{
						parent.layer.alert('添加失败！');
					}
					//刷新数据
					$('#tb_user').bootstrapTable('refresh');
				},
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_role").modal('hide');
		}
		function delRole(){
			if(delRow == null){
				return;
			}
			var url = "${pageContext.request.contextPath }/user/delRole/"+userRow.id+"/"+delRow.id;
			$.post(
				url,
				{
					
				},
				function(data){
					if(data>0){
						//这是弹窗的代码
						parent.layer.alert('删除成功！');
					}else{
						parent.layer.alert('删除失败！');
					}
					//刷新数据
					$('#tb_user').bootstrapTable('refresh');
				},
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_role2").modal('hide');
		}
		//初始化用户密码
		function respwd(id){
			var url = "${pageContext.request.contextPath }/user/respwd/"+id;
			$.post(
				url,
				{
					
				},
				function(data){
					if(data>0){
						//这是弹窗的代码
						parent.layer.alert('初始化成功，默认密码是123456');
					}else{
						parent.layer.alert('初始化失败！');
					}
				},
				"text"
			);	
		}
    </script>
</head>
<body>
	<body class="gray-bg">
     <div class="panel-body" style="padding-bottom: 0px;">
     	<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5><span class="glyphicon glyphicon-question-sign"></span>  帮助</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <p>系统用户表</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>用户表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                    	<div id="toolbar_user" class="btn-group">
							<button id="btn_user_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增角色
							</button>
							<button id="btn_user_delete" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>删除角色
							</button>
							<!-- <button id="btn_role_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button> -->
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_user"></table>
					</div>
                </div>
            </div>
        </div>
     </div>
     
     <!-- 弹窗div -->
        <div class="modal inmodal animated rollIn" id="window_role" tabindex="-1" role="dialog" aria-hidden="true" >
             <div class="modal-dialog">
                 <div class="modal-content">
                     <div class="modal-header">
                         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                         <h4 class="modal-title">新增角色</h4>
                     </div>
                     <div class="modal-body">
                         <form class="form-horizontal m-t" id="addcond" novalidate="novalidate">
                         	<table id="tb_role"></table>
                        </form>
                     </div>
                     <div class="modal-footer">
                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                         <button type="button" id="btn_role_save" onclick   class="btn btn-primary">保存</button>
                     </div>
                 </div>
             </div>
         </div>
         <div class="modal inmodal animated rollIn" id="window_role2" tabindex="-1" role="dialog" aria-hidden="true" >
             <div class="modal-dialog">
                 <div class="modal-content">
                     <div class="modal-header">
                         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                         <h4 class="modal-title">删除角色</h4>
                     </div>
                     <div class="modal-body">
                         <form class="form-horizontal m-t" id="addcond" novalidate="novalidate">
                         	<table id="tb_role2"></table>
                        </form>
                     </div>
                     <div class="modal-footer">
                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                         <button type="button" id="btn_role_save2" onclick   class="btn btn-primary">保存</button>
                     </div>
                 </div>
             </div>
         </div>
     <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<!-- Toastr script -->
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript">
		//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
		//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
		//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
	    $.validator.setDefaults({
	        highlight: function (element) {
	            $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	        },
	        success: function (element) {
	            element.closest('.form-group').removeClass('has-error').addClass('has-success');
	        },
	        errorElement: "span",
	        errorPlacement: function (error, element) {
	            if (element.is(":radio") || element.is(":checkbox")) {
	                error.appendTo(element.parent().parent().parent());
	            } else {
	                error.appendTo(element.parent());
	            }
	        },
	        errorClass: "help-block m-b-none",
	        validClass: "help-block m-b-none"
	
	
	    });
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	name: "required",
	            	roleDesc: "required"
	            },
	            messages: {
	            	name: icon + "请输角色名称",
	            	roleDesc: icon + "请输角色描述"
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	</script>
</body>
</html>