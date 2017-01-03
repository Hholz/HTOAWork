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
	<script type="text/javascript">
		$(function(){
			$("#btn_role_add").click(function(){
				//清空新增div中的数据
				$("#window_role #id").val('');
				$("#window_role #name").val('');
				$("#window_role #roleDesc").val('');
	            
	            $("#btn_role_save").attr("onclick","addRole()");
	        });
			//修改按钮事件
			$("#btn_role_update").click(function(){
				if(roleRow==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_role #id").val(roleRow.id);
				$("#window_role #name").val(roleRow.name);
				$("#window_role #roleDesc").val(roleRow.roleDesc);
	            
				$('#window_role').modal('show');
	            $("#btn_role_save").attr("onclick","updateRole()");
	        });
			$("#btn_role_delete").click(function(){
				if(roleRow==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteRole();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
		});
		
		function addRole(){
			if(!validateForm($("#roleForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/power/role/add";
			$.post(
				url,
				{
					id:$("#window_role #id").val(),
					name:$("#window_role #name").val(),
					roleDesc:$("#window_role #roleDesc").val()
				},
				function(data){
					if(data>0){
						//这是弹窗的代码
						parent.layer.alert('添加成功！');
					}else{
						parent.layer.alert('添加失败！');
					}
					//刷新数据
					$('#tb_role').bootstrapTable('refresh');
				},
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_role").modal('hide');
		}
		function updateRole(){
			if(!validateForm($("#roleForm"))){
				return;
			}
			var id = roleRow.id;
			var url = "${pageContext.request.contextPath }/power/role/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_role #id").val(),
					name:$("#window_role #name").val(),
					roleDesc:$("#window_role #roleDesc").val()
				},
				function(data){
					if(data>0){
						//这是弹窗的代码
						parent.layer.alert('修改成功！');
					}else{
						parent.layer.alert('修改失败！');
					}
					//刷新数据
					$('#tb_role').bootstrapTable('refresh');
				},
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_role").modal('hide');
		}
		function deleteRole(){
			var id = roleRow.id;
			var url = "${pageContext.request.contextPath }/power/role/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:roleRow.id  //从选中的rows中获取id
				},
				function(data){
					if(data>0){
						parent.layer.alert('删除成功！');
					}else{
						parent.layer.alert('删除失败！');
					}
					//刷新表格
					$('#tb_role').bootstrapTable('refresh');
				},
				"text"
			);	
		}
	</script>
    <script>
	    var roleRow = null;
	    var authList = null;
	    var roleId = null;
	    function authHas(id){
	    	roleId = id;
	    	var url = "${pageContext.request.contextPath }/power/authListByRoleId/"+id;
			$.post(
				url,
				{
					id:id
				},
				authHasHandle,
				"json"
			);	
	    }
	    function authHasHandle(data){
	    	authList = data;
	    	//表格刷新
	    	$('#tb_auth').bootstrapTable('refresh');
	    }
	    
	    function addPower(roleId,authId){
	    	var url = "${pageContext.request.contextPath }/power/addPower";
			$.post(
				url,
				{
					roleId:roleId,
					authId:authId
				},
				function(data){
					if(data>=1){
						toastr.success('新增权限', '新增成功');
					}else{
						toastr.error('新增权限', '新增失败');
					}
				},
				"text"
			);	
	    }
	    function delPower(roleId,authId){
	    	var url = "${pageContext.request.contextPath }/power/delPower/";
			$.post(
				url,
				{
					roleId:roleId,
					authId:authId
				},
				function(data){
					if(data>=1){
						toastr.info('删除权限', '删除成功');
					}else{
						toastr.error('删除权限', '删除失败');
					}
				},
				"json"
			);	
	    }
	    $(function () {
	    	//提示窗口属性
	    	toastr.options = {
	    			  "closeButton": true,
	    			  "debug": false,
	    			  "progressBar": true,
	    			  "positionClass": "toast-top-right",
	    			  "onclick": null,
	    			  "showDuration": "400",
	    			  "hideDuration": "1000",
	    			  "timeOut": "7000",
	    			  "extendedTimeOut": "1000",
	    			  "showEasing": "swing",
	    			  "hideEasing": "linear",
	    			  "showMethod": "fadeIn",
	    			  "hideMethod": "fadeOut"
	    			}
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
	                if(roleRow != null){
	                	roleRow = null;
	                }
	                roleRow = row;
	            },
	            onUncheck: function(row) {
	                if(roleRow != null){
	                	roleRow = null;
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
				},{
					field : 'id',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='authHas("+id+")'><i class='fa fa-file-text-o'>权限设置</i></a>";
					}
				},]
			});
			
			
			//表格数据
			$('#tb_auth').bootstrapTable({
				url : '${pageContext.request.contextPath}/power/authTypeListJson', //请求后台的URL（*）
				detailView: true,//父子表
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
				onCheck: function(row) {
	              
	            },//单击row事件
	            onUncheck: function(row) {
	               
	            },
	            columns : [{
					field : 'id',
					title : '编号'
				}, {
					field : 'satName',
					title : '权限类别'
				}, {
					field : 'satDesc',
					title : '描述'
				},],
				//注册加载子表的事件。注意下这里的三个参数！
                onExpandRow: function (index, row, $detail) {
                    InitSubTable(index, row, $detail);
                }
			});
		});
	    //初始化子表格(无线循环)
	    function InitSubTable(index, row, $detail) {
	        var typeId = row.id;
	        var cur_table = $detail.html('<table></table>').find('table');
	        $(cur_table).bootstrapTable({
	        	classes:'table-no-bordered',
	            url : '${pageContext.request.contextPath}/power/authListJson/'+typeId, //请求后台的URL（*）
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
				onCheckAll: function(rows) {
					if(roleId==null){
			            alert("请新增角色后进行"); 
					}else{
						
					}
	            },
	            onUncheckAll: function(rows) {
	            	if(roleId==null){
			            alert("请新增角色后进行"); 
			            return false;
					}
	            },
	            onCheck: function(row) {
	            	if(roleId==null){
			            alert("请新增角色后进行"); 
					}else{
						addPower(roleId,row.id);
					}
	            },
	            onUncheck: function(row) {
	            	if(roleId==null){
			            alert("请新增角色后进行"); 
					}else{
						delPower(roleId,row.id);
					}
	            },
	            columns : [ {
	            	 field: 'state',
	                 checkbox: true,
	                 formatter:stateFormatter
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '权限名'
				}, {
					field : 'authDesc',
					title : '描述'
				},]
	        });
	    };
	    function stateFormatter(value, row, index) {
	    	if(authList==null){
	    		return value;
	    	}
	    	for(var i=0;i<authList.length;i++){
	    		if(row.id==authList[i].id){
	    			return {checked: true};
	    		}
	    	}
	    	return value;
	    }
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
			};
			return temp;
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
                        <p>用户权限</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>角色表</h5>
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
                    	<div id="toolbar_role" class="btn-group">
							<button id="btn_role_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-toggle="modal" data-target="#window_role">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_role_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<!-- <button id="btn_role_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button> -->
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_role"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>权限表</h5>
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
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_auth"></table>
                    </div>
                </div>
            </div>
        </div>
     </div>
     <!--教育情况修改的弹窗  -->
		<div class="modal inmodal" id="window_role" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">角色编辑</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="roleForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色名称：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">角色描述：</label>
                                <div class="col-sm-8">
                                    <input id="roleDesc" name="roleDesc" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_role_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
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