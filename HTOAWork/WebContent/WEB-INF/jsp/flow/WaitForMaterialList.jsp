<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>用品入库</title>

<script type="text/javascript">
	//全局变量，用来保存选中行的数据
	var rows = null;
	$(function() {

		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();

		
		//修改按钮事件
		//删除按钮事件
		//*************************************************************************按钮事件
	});

	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_departments')
					.bootstrapTable(
							{
								url : '${pageContext.request.contextPath}/flow/waitformaterial/0', //请求后台的URL（*）
								method : 'post', //请求方式（*）
								contentType : "application/x-www-form-urlencoded",
								toolbar : '#toolbar', //工具按钮用哪个容器
								striped : true, //是否显示行间隔色
								cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
								pagination : true, //是否显示分页（*）
								sortable : false, //是否启用排序
								sortOrder : "asc", //排序方式
								queryParams : oTableInit.queryParams,//传递参数（*）
								sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
								pageNumber : 1, //初始化加载第一页，默认第一页
								pageSize : 5, //每页的记录行数（*）
								pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
								search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
								strictSearch : false,
								showColumns : true, //是否显示所有的列
								showRefresh : true, //是否显示刷新按钮
								minimumCountColumns : 2, //最少允许的列数
								clickToSelect : true, //是否启用点击选中行
								height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
								uniqueId : "id", //每一行的唯一标识，一般为主键列
								showToggle : true, //是否显示详细视图和列表视图的切换按钮
								cardView : false, //是否显示详细视图
								detailView : false, //是否显示父子表
								singleSelect : true, //设置为单选
								onClickRow: function(row, $element) {//selected
					                 //$element是当前tr的jquery对象
					                 if(rows!=null){
					                	 rows = null;
					                 }
					                 rows = row;
					            },//单击row事件
								columns : [ {
									field : 'id',
									align : 'center',
									title : 'ID',
									visible : false
								}, {
									field : 'empname',
									align : 'center',
									title : '申请人'
								}, {
									field : 'materialname',
									align : 'center',
									title : '用品名称'
								},{
									field : 'count',
									align : 'center',
									title : '数量'
								}, {
									field : 'applystatus',
									align : 'center',
									title : '状态',
									formatter : function(value, row, index) {
										var id = row.applystatus;
										var a = row.id;
										var name = "";
										if (id == 0) {
											name = '<button id="btn_add" type="button" class="btn btn-w-m btn-primary" onclick="updatedit(';
											name +=a+');"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span>入库</button>&nbsp;&nbsp';
											return name;
										} else if (id == 1) {
											name = "已入库";
											return name;
										}
									}
								},  ]
							});
		};

		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码  
			};
			return temp;
		};
		return oTableInit;
	};

	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};

		return oInit;
	};
	function updatedit(id){
		/* if(rows==null){
			parent.layer.alert('请选要入库的用品！');
			return;
		} */
		
	/* 	var waitstatus = rows.applystatus;
		if(waitstatus == 1){
			parent.layer.alert('此用品已入库！');
			return;
		} */
		var materialid =rows.materialid;
		var count = rows.count;
		parent.layer.confirm('确定要入库吗??',{
			btn: ['同意','取消'],
			shade: false
		},function(){//同意
			var url = "${pageContext.request.contextPath }/flow/waitformaterial/"+id;
			$.post(url, {
				_method : 'PUT',
				id : id,
				applystatus : 1,
				materialid : materialid,
				count : count
			}, yesholiday, "text");
		},function(){//取消
			
		});
		
	}

	function yesholiday() {
		parent.layer.alert('已入库');
		//从后台返回出来的int类型数据，用来判断是否新增成功
		location.href = "${pageContext.request.contextPath }/flow/waitformaterial/waitformateriallist";
	}
	//********************************************************删除
	//删除学生，ajax提交
	function deleteStudent() {
		if(rows==null){
			parent.layer.alert('请选要删除的用品！');
			return;
		}
		
		var waitstatus = rows.applystatus;
		if(waitstatus == 0){
			parent.layer.alert('此用品还未入库！');
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/flow/waitformaterial/"+id;
				+ id;
		$.post(url, {
			_method : 'DELETE',//改成PUT提交
		}, deleteStudentHandle, "text");
	}
	function deleteStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
	function upset() {
		if(rows==null){
			parent.layer.alert('请选你要提交的数据！');
			return;
		}
		var upset = rows.upset;
		if(upset == 1){
			alert("此申请已提交!");
			return;
		}
		var id = rows.id;
		var url = "${pageContext.request.contextPath }/dailyWork/ApplyMaterial/upset";
		
		$.post(url, {
			id : rows.id
		//从选中的rows中获取id
		}, setmessage, "text");
	}
	function setmessage(data){
		if (data > 0) {
			swal("提交成功！", "正在等待上级部门的审批。", "success");
		} else {
			swal("提交失败", "服务器繁忙！", "error");
		}
		rows = null;
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
	function deleteStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal("删除成功！", "您已经永久删除了这条信息。", "success");
		} else {
			swal("删除失败", "服务器繁忙！", "error");
		}
		//刷新表格
		$('#tb_departments').bootstrapTable('refresh');
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
	<div id="toolbar" class="btn-group">
		<button id="btn_add" type="button" onclick="updatedit();"
			class="btn btn-w-m btn-primary" data-toggle="modal">
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>入库
		</button>
		<button id="btn_delete" onclick="deleteStudent();" type="button" class="btn btn-w-m btn-danger">
			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
		</button>
	</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
		 
	</div>
	<script type="text/javascript">
			//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
			//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
			//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
			$.validator.setDefaults({
				highlight : function(element) {
					$(element).closest('.form-group')
							.removeClass('has-success').addClass('has-error');
				},
				success : function(element) {
					element.closest('.form-group').removeClass('has-error')
							.addClass('has-success');
				},
				errorElement : "span",
				errorPlacement : function(error, element) {
					if (element.is(":radio") || element.is(":checkbox")) {
						error.appendTo(element.parent().parent().parent());
					} else {
						error.appendTo(element.parent());
					}
				},
				errorClass : "help-block m-b-none",
				validClass : "help-block m-b-none"

			});
			//调用表单验证的方法，在addStudent()里调用，出入form对象
			//***input控件要设置name属性***
			function validateForm(formdata) {
				var icon = "<i class='fa fa-times-circle'></i> ";
				var validator = formdata.validate({
					rules: {
	                    price: {
	                    	required:true,
		                	digits:true
	                    },
		            	
		            },
		            messages: {
		            	materialname: icon + "请输入用品名称",
		            	unit: icon + "请输入用品单位,如盒,瓶等等",
		            	price: {
		            		required:icon + "请输入用品价格",
		                	digits: icon + "价格只能是数字"
	                    },
	                    style :{
	                    	required:icon + "请输入用品规格"
	                    },
	                    counts:{
	                    	required:icon + "请输入用品数量"
	                    },
	                    applymaterialRemark:{
	                    	required:icon + "请输入申购理由"
	                    }
		            },
				});
				//返回表单验证是否通过，true false
				return validator.form();
			}
		</script>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>