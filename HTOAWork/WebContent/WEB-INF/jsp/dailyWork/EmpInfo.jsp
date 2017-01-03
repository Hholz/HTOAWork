<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>员工家庭情况</title>
    <jsp:include page="../styleInclude.jsp"></jsp:include>
    
    <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var fam_rows = null;
	    var edu_rows = null;
	    var work_rows = null;
	    //后台传出来的员工id
	    var All_empId = ${empId};
	    $(function () {
	 	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//*******家庭信息按钮事件*************************
			$("#btn_fam_add").click(function(){
	            $("#window_fam_add #empid").val(All_empId);
	            $("#window_fam_add #contactname").val('');
	            $("#window_fam_add #relationship").val('');
	            $("#window_fam_add #phone").val('');
	            $("#window_fam_add #familyRemark").val('');
	            $('#window_fam_add').modal('show');
	        });
			//修改按钮事件
			$("#btn_fam_edit").click(function(){
				//把内容放到更新的列表
				if(fam_rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_fam_up #id").val(fam_rows.id);
	            $("#window_fam_up #empid").val(fam_rows.empid);
	            $("#window_fam_up #contactname").val(fam_rows.contactname);
	            $("#window_fam_up #relationship").val(fam_rows.relationship);
	            $("#window_fam_up #phone").val(fam_rows.phone);
	            $("#window_fam_up #familyRemark").val(fam_rows.familyRemark);
	            $('#window_fam_up').modal('show');
	        });
			//*************************************************************************按钮事件
			//新增按钮事件*************************
			$('#btn_fam_delete').click(function () {
				if(fam_rows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
	            swal({
	                    title: "您确定要删除这条信息吗",
	                    text: "删除后将无法恢复，请谨慎操作！",
	                    type: "warning",
	                    showCancelButton: true,
	                    confirmButtonColor: "#DD6B55",
	                    confirmButtonText: "是的，我要删除！",
	                    cancelButtonText: "让我再考虑一下…",
	                    closeOnConfirm: false,
	                    closeOnCancel: false
	            },
	            function (isConfirm) {
	                if (isConfirm) {
	                	//调用删除函数
	                	delEmpFamily();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	        });
			//*************************教育情况************************************************
			//修改按钮事件
			$("#btn_edu_add").click(function(){
				$("#window_edu_add #empid").val(All_empId);
				$("#window_edu_add #collegename").val('');
				$("#window_edu_add #degree").val('');
				$("#window_edu_add #startdate").val('');
				$("#window_edu_add #enddate").val('');
				$("#window_edu_add #eduRemark").val('');
				$('#window_edu_add').modal('show');
	        });
			$("#btn_edu_edit").click(function() {
				if(edu_rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_edu_up #id").val(edu_rows.id);
				$("#window_edu_up #empid").val(edu_rows.empid);
				$("#window_edu_up #collegename").val(edu_rows.collegename);
				$("#window_edu_up #degree").val(edu_rows.degree);
				$("#window_edu_up #startdate").val(edu_rows.startdate);
				$("#window_edu_up #enddate").val(edu_rows.enddate);
				$("#window_edu_up #eduRemark").val(edu_rows.eduRemark);
				$('#window_edu_up').modal('show');
			});
			//删除按钮事件
			$('#btn_edu_delete').click(function() {
				if(edu_rows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				swal({
					title : "您确定要删除这条信息吗",
					text : "删除后将无法恢复，请谨慎操作！",
					type : "warning",
					showCancelButton : true,
					confirmButtonColor : "#DD6B55",
					confirmButtonText : "是的，我要删除！",
					cancelButtonText : "让我再考虑一下…",
					closeOnConfirm : false,
					closeOnCancel : false
				}, function(isConfirm) {
					if (isConfirm) {
						//调用删除函数
						delEmpEduction();
					} else {
						swal("已取消", "您取消了删除操作！", "error");
					}
				});
			});
			//*************************工作经历************************************************
			$("#btn_work_add").click(function(){
	            $("#window_work_add #empid").val(All_empId);
	            $("#window_work_add #companyname").val('');
	            $("#window_work_add #degree").val('');
	            $("#window_work_add #startdate_work_up").val('');
	            $("#window_work_add #enddate_work_up").val('');
	            $("#window_work_add #reason").val('');
	            $("#window_work_add #worksRemark").val('');
	            $('#window_work_add').modal('show');
	        });
			$("#btn_work_edit").click(function(){
				//把内容放到更新的列表
				if(work_rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_work_update #id").val(work_rows.id);
	            $("#window_work_update #empid").val(work_rows.empid);
	            $("#window_work_update #companyname").val(work_rows.companyname);
	            $("#window_work_update #degree").val(work_rows.degree);
	            $("#window_work_update #startdate_work_up").val(work_rows.startdate);
	            $("#window_work_update #enddate_work_up").val(work_rows.enddate);
	            $("#window_work_update #reason").val(work_rows.reason);
	            $("#window_work_update #worksRemark").val(work_rows.worksRemark);
	            $('#window_work_update').modal('show');
	        });
			//删除按钮事件
			$('#btn_work_delete').click(function () {
				if(work_rows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
	            swal({
	                    title: "您确定要删除这条信息吗",
	                    text: "删除后将无法恢复，请谨慎操作！",
	                    type: "warning",
	                    showCancelButton: true,
	                    confirmButtonColor: "#DD6B55",
	                    confirmButtonText: "是的，我要删除！",
	                    cancelButtonText: "让我再考虑一下…",
	                    closeOnConfirm: false,
	                    closeOnCancel: false
	            },
	            function (isConfirm) {
	                if (isConfirm) {
	                	//调用删除函数
	                	delEmpWork();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	        });
			
			//教育表
			$('#tb_eduction').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/edu/0', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar_edu', //工具按钮用哪个容器
				//striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : EduParams,//传递参数（*）
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
				//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(edu_rows != null){
	                	edu_rows = null;
	                }
	                edu_rows = row;
	            },
	            onUncheck: function(row) {
	                if(edu_rows != null){
	                	edu_rows = null;
	                }
	            },
				columns : [ {
					checkbox : true
				}, {
					field : 'id',
					title : 'ID',
					align : 'center',
					valign : 'middle',
					visible:false
				}, {
					field : 'emp.empname',
					title : '员工',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'collegename',
					title : '学校',
					align : 'center',
					valign : 'middle',
				}, {
					field : 'degree',
					title : '学历',
					align : 'center',
					valign : 'middle'
				}, {
					field : 'startdate',
					title : '入校时间',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 10);
						}
					}
				}, {
					field : 'enddate',
					title : '毕业时间',
					align : 'center',
					valign : 'middle',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 10);
						}
					}
				}, ]
			});
			function EduParams(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码  
					empid : All_empId,
				};
				return temp;
			};
			
			//工作经历表
			$('#tb_work').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/works/0', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType : "application/x-www-form-urlencoded",
				toolbar : '#toolbar_work', //工具按钮用哪个容器
				//striped : true, //是否显示行间隔色
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				sortable : false, //是否启用排序
				sortOrder : "asc", //排序方式
				queryParams : WorkParams,//传递参数（*）
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
				//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				showToggle : true, //是否显示详细视图和列表视图的切换按钮
				cardView : false, //是否显示详细视图
				detailView : false, //是否显示父子表
				singleSelect : true, //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(work_rows != null){
	                	work_rows = null;
	                }
	                work_rows = row;
	            },
	            onUncheck: function(row) {
	                if(work_rows != null){
	                	work_rows = null;
	                }
	            },//单击row事件
				columns : [ {
					checkbox : true
				}, {
					field : 'id',
					title : 'ID',
					visible:false
				}, {
					field : 'emp.empname',
					title : '员工'
				}, {
					field : 'companyname',
					title : '公司名称'
				}, {
					field : 'degree',
					title : '岗位'
				}, {
					field : 'startdate',
					title : '入职时间',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 10);
						}
					}
				}, {
					field : 'enddate',
					title : '离职时间',
					formatter : function(value, row, index) {
						if (value) {
							return value.substring(0, 10);
						}
					}
				},{
					field : 'reason',
					title : '离职原因'
				},{
					field : 'worksRemark',
					title : '说明'
				},]
			});
			function WorkParams(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					empid : All_empId,
				};
				return temp;
			};
		});

	    var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_family').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/family/0', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType : "application/x-www-form-urlencoded",
					toolbar : '#toolbar_family', //工具按钮用哪个容器
					//striped : true, //是否显示行间隔色
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
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect : true, //设置为单选
					onCheck: function(row) {
		                 //$element是当前tr的jquery对象
		                if(fam_rows != null){
		                	fam_rows = null;
		                }
		                fam_rows = row;
		            },
		            onUncheck: function(row) {
		                if(fam_rows != null){
		                	fam_rows = null;
		                }
		            },//单击row事件
					columns : [ {
						checkbox : true
					}, {
						field : 'id',
						title : 'ID',
						visible:false
					},  {
						field : 'emp.empname',
						title : '员工'
					}, {
						field : 'contactname',
						title : '姓名'
					}, {
						field : 'relationship',
						title : '关系'
					}, {
						field : 'phone',
						title : '电话号码'
					},]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					empid : All_empId,
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
		//**************************家庭信息************************************8
		//新增，ajax提交
		function addEmpFamily(){
			if(!validateFamForm($("#addFamForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/family/add";
			$.post(
				url,
				{	
					_method:'PUT',
					empid:$("#window_fam_add #empid").val(),
					contactname:$("#window_fam_add #contactname").val(),
					relationship:$("#window_fam_add #relationship").val(),
					phone:$("#window_fam_add #phone").val(),
					familyRemark:$("#window_fam_add #familyRemark").val()
				},
				function(data){
					//从后台返回出来的int类型数据，用来判断是否新增成功
					if(data>0){
						//这是弹窗的代码
						swal({
		                    title: "成功",
		                    text: "你已经完成添加操作",
		                    type: "success"
		                });
					}else if(data==-1){
						swal("添加失败", "已添加该员工的父亲或母亲", "error");
					}else{
						swal("添加失败", "请检查你输入的数据！", "error");
					}
					$('#tb_family').bootstrapTable('refresh');
					//把保存选中行的rows变量清零，很重要，防止缓存
					fam_rows = null;
				},
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_fam_add").modal('hide');
			//清空新增div中的数据
            $("#window_fam_add #contactname").val('');
            $("#window_fam_add #relationship").val('');
            $("#window_fam_add #phone").val('');
            $("#window_fam_add #familyRemark").val('');
		}
		
		//修改，ajax提交,PUT
		function upEmpFamily(){
			if(!validateFamForm($("#upFamForm"))){
				return;
			}
			var id =$("#window_fam_up #id").val(); 
			var url = "${pageContext.request.contextPath }/dailyWork/family/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					empid:$("#window_fam_up #empid").val(),
					contactname:$("#window_fam_up #contactname").val(),
					relationship:$("#window_fam_up #relationship").val(),
					phone:$("#window_fam_up #phone").val(),
					familyRemark:$("#window_fam_up #familyRemark").val(),
				},
				function(data){
					//从后台返回出来的int类型数据，用来判断是否新增成功
					if(data>0){
						swal({
		                    title: "成功",
		                    text: "你已经完成修改操作",
		                    type: "success"
		                });
					}else{
						swal("修改失败", "请检查你输入的数据！", "error");
					}
					//刷新表格
					$('#tb_family').bootstrapTable('refresh');
					//把保存选中行的rows变量清零，很重要，防止缓存
					fam_rows = null;
				},
				"text"
			);	
			//用来关闭修改窗口***********
			$("#window_fam_up").modal('hide')
		}
		//删除，ajax提交,DELETE
		function delEmpFamily(){
			var id = fam_rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/family/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:fam_rows.id  //从选中的rows中获取id
				},
				function(data){
					//从后台返回出来的int类型数据，用来判断是否新增成功
					if(data>0){
						swal("删除成功！", "您已经永久删除了这条信息。", "success");
					}else{
						swal("删除失败", "服务器繁忙！", "error");
					}
					//刷新表格
					$('#tb_family').bootstrapTable('refresh');
				},
				"text"
			);	
		}
		
		//*******************教育情况*********************************************************删除
		function addEmpEduction() {
			if(!validateEduForm($("#addEduForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/edu/add";
			$.post(
				url, {
					empid : $("#window_edu_add #empid").val(),
					collegename : $("#window_edu_add #collegename").val(),
					degree : $("#window_edu_add #degree").val(),
					startdate : $("#window_edu_add #startdate_add").val(),
					enddate : $("#window_edu_add #enddate_add").val(),
					eduRemark : $("#window_edu_add #eduRemark").val(),
				}, 
				function(data) {
					//从后台返回出来的int类型数据，用来判断是否新增成功
					if (data > 0) {
						swal({
							title : "成功",
							text : "你已经完成添加操作",
							type : "success"
						});
					} else {
						swal("添加失败", "请检查你输入的数据！", "error");
					}
					$('#tb_eduction').bootstrapTable('refresh');
				}, 
				"text"
		    );

			//用来关闭新增窗口***********
			$("#window_edu_add").modal('hide')
			//清空新增div中的数据
			$("#window_edu_add #collegename").val('');
			$("#window_edu_add #enddate").val('');
			$("#window_edu_add #startdate").val('');
			$("#window_edu_add #eduRemark").val('');
		}
		//修改学生，ajax提交
		function upEmpEduction() {
			if(!validateEduForm($("#upEduForm"))){
				return;
			}
			var id = $("#window_edu_up #id").val();
			var url = "${pageContext.request.contextPath }/dailyWork/edu/" + id;
			$.post(url, {
				_method : 'PUT',//改成PUT提交
				empid : $("#window_edu_up #empid").val(),
				collegename : $("#window_edu_up #collegename").val(),
				degree : $("#window_edu_up #degree").val(),
				startdate : $("#window_edu_up #startdate").val(),
				enddate : $("#window_edu_up #enddate").val(),
				eduRemark : $("#window_edu_up #eduRemark").val(),
			}, upEmpEductionHandle, "text");
			//用来关闭修改窗口***********
			$("#window_edu_up").modal('hide');
		}
		function upEmpEductionHandle(data) {
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if (data > 0) {
				swal({
					title : "成功",
					text : "你已经完成修改操作",
					type : "success"
				});
			} else {
				swal("修改失败", "请检查你输入的数据！", "error");
			}
			//刷新表格
			$('#tb_eduction').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			edu_rows = null;
		}
		//删除学生，ajax提交
		function delEmpEduction() {
			var id = edu_rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/edu/"
					+ id;
			$.post(url, {
				_method : 'DELETE',//改成PUT提交
				id : edu_rows.id
			//从选中的rows中获取id
			}, delEmpEductionHandle, "text");
		}
		function delEmpEductionHandle(data) {
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if (data > 0) {
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			} else {
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_eduction').bootstrapTable('refresh');
		}
		
		//*************工作经历***************************************************************删除
		//新增，ajax提交
		function addEmpWork(){
			if(!validateWorkForm($("#addWorkForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/works/add";
			$.post(
				url,
				{	
					_method:'PUT',
					empid:$("#window_work_add #empid").val(),
					companyname:$("#window_work_add #companyname").val(),
					degree:$("#window_work_add #degree").val(),
					startdate:$("#window_work_add #startdate_work_add").val(),
					enddate:$("#window_work_add #enddate_work_add").val(),
					reason:$("#window_work_add #reason").val(),
					worksRemark:$("#window_work_add #worksRemark").val()
				},
				function(data){
					if(data>0){
						swal({
		                    title: "成功",
		                    text: "你已经完成添加操作",
		                    type: "success"
		                });
					}else{
						swal("添加失败", "请检查你输入的数据！", "error");
					}
					$('#tb_work').bootstrapTable('refresh');
					work_rows = null;
				},
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_work_add").modal('hide');
			//清空新增div中的数据
            $("#window_work_add #companyname").val('');
            $("#window_work_add #degree").val('');
            $("#window_work_add #startdate_work_add").val('');
            $("#window_work_add #enddate_work_add").val('');
            $("#window_work_add #reason").val('');
            $("#window_work_add #worksRemark").val('');
		}
		
		
		//修改，ajax提交,PUT
		function upEmpWork(){
			if(!validateWorkForm($("#upWorkForm"))){
				return;
			}
			var id =$("#window_work_update #id").val(); 
			var url = "${pageContext.request.contextPath }/dailyWork/works/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					empid:$("#window_work_update #empid").val(),
					companyname:$("#window_work_update #companyname").val(),
					degree:$("#window_work_update #degree").val(),
					startdate:$("#window_work_update #startdate_work_up").val(),
					enddate:$("#window_work_update #enddate_work_up").val(),
					reason:$("#window_work_update #reason").val(),
					worksRemark:$("#window_work_update #worksRemark").val()
				},
				function(data){
					if(data>0){
						swal({
		                    title: "成功",
		                    text: "你已经完成修改操作",
		                    type: "success"
		                });
					}else{
						swal("修改失败", "请检查你输入的数据！", "error");
					}
					$('#tb_work').bootstrapTable('refresh');
				},
				"text"
			);	
			work_rows = null;
			//用来关闭修改窗口***********
			$("#window_work_update").modal('hide')
		}
		
		//删除，ajax提交,DELETE
		function delEmpWork(){
			var id = work_rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/works/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:work_rows.id  //从选中的rows中获取id
				},
				function(data){
					if(data>0){
						swal("删除成功！", "您已经永久删除了这条信息。", "success");
					}else{
						swal("删除失败", "服务器繁忙！", "error");
					}
					$('#tb_work').bootstrapTable('refresh');
				},
				"text"
			);	
			work_rows = null;
		}
	</script>
</head>
<body class="gray-bg">
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <a href="${pageContext.request.contextPath}/dailyWork/emp/emplist">
							<button class="btn btn-warning " type="button"><i class="fa fa-mail-reply-all"></i>返回上一级
                       		</button>
                        </a>
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
                        <p>员工的工作经历、教育情况、家庭成员</p>
                    </div>
                </div>
            </div>
        </div>
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>工作经历</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar_work" class="btn-group">
							<button id="btn_work_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_work_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" >
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_work_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<table id="tb_work"></table>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>教育情况</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar_edu" class="btn-group">
							<button id="btn_edu_add" type="button" class="btn btn-w-m btn-primary"
								data-toggle="modal">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_edu_edit" type="button" class="btn btn-w-m btn-success"
								data-toggle="modal" >
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_edu_delete" type="button"
								class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_eduction"></table>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>家庭信息</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<div id="toolbar_family" class="btn-group">
							<button id="btn_fam_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_fam_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" >
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_fam_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
						<!-- table代码就这些，用js构建表格 -->
						<table id="tb_family"></table>
					</div>
				</div>
			</div>
		</div>
		<!-- 家庭情况更改弹窗 -->				
		<div class="modal inmodal" id="window_fam_up" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改员工家庭信息</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="upFamForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid"  name="empid" required class="form-control" disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*姓名：</label>
	                            <div class="col-sm-8">
	                               <input id="contactname" name="contactname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*关系：</label>
                                <div class="col-sm-8">
                                    <input id="relationship" name="relationship" type="text" required class="form-control" disabled="disabled">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*电话号码：</label>
                                <div class="col-sm-8">
                                    <input id="phone" name="phone" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">说明：</label>
	                            <div class="col-sm-8">
                                    <input type="text" class="form-control" id="familyRemark">
                                </div>
                        	</div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="upEmpFamily()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!-- 家庭考勤新增弹窗 -->
		<div class="modal inmodal" id="window_fam_add" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加员工家庭景信息</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addFamForm" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*姓名：</label>
	                            <div class="col-sm-8">
	                               <input id="contactname" name="contactname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*关系：</label>
                                <div class="col-sm-8">
                                	<select id="relationship" name="relationship" class="form-control">
										<option value="父亲">父亲</option>
										<option value="母亲">母亲</option>
										<option value="其他">其他</option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*电话号码：</label>
                                <div class="col-sm-8">
                                    <input id="phone" name="phone" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">说明：</label>
	                            <div class="col-sm-8">
                                    <input type="text" class="form-control" id="familyRemark">
                                </div>
                        	</div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="addEmpFamily()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!-- 教育情况修改弹窗 -->
		<div class="modal inmodal" id="window_edu_up" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改员工教育背景</h4>
					</div>
					<form class="form-horizontal m-t" id="upEduForm" novalidate="novalidate">
						<input id="id" type="hidden">
						<div class="form-group">
							<label class="col-sm-3 control-label">*员工：</label>
                               <div class="col-sm-8">
                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
                                    <c:forEach items="${emp}" var="emp">
                                    	<option value="${emp.id}">${emp.empname}</option>
                                    </c:forEach>
                                </select>
                            </div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*学校：</label>
                            <div class="col-sm-8">
                                <input id="collegename" name="collegename" type="text" required class="form-control">
                            </div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*学历：</label>
                               <div class="col-sm-8">
                                   <select id="degree" name="degree" class="form-control">
                                    <option value="初中">初中</option>
                                    <option value="高中">高中</option>
                                    <option value="中专">中专</option>
                                    <option value="大专">大专</option>
                                    <option value="本科">本科</option>
                                    <option value="博士及博士以上">博士及博士以上</option>
                                </select>
                               </div>
						</div>
						<div class="form-group">
                               <label class="col-sm-3 control-label">*入校时间：</label>
                               <div class="col-sm-8">
								<input placeholder="入校时间" required class="form-control layer-date"
									id="startdate" name="startdate">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*毕业时间：</label>
							<div class="col-sm-8">
								<input placeholder="毕业时间" required class="form-control layer-date"
									id="enddate" name="enddate">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">说明:</label>
                            <div class="col-sm-8">
                                   <input id="eduRemark" type="text" class="form-control">
                               </div>
                              </div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white"
								data-dismiss="modal">关闭</button>
							<button type="button" onclick="upEmpEduction()"
								class="btn btn-primary">保存</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!-- 教育情况新增弹窗 -->
		<div class="modal inmodal" id="window_edu_add" tabindex="-1"
			role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加员工教育背景</h4>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addEduForm"
							novalidate="novalidate">
							<div class="form-group">
								<label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">*学校：</label>
	                            <div class="col-sm-8">
	                                <input id="collegename" name="collegename" type="text" required class="form-control">
	                            </div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">*学历：</label>
                                <div class="col-sm-8">
                                	<select id="degree" name="degree" required class="form-control">
	                                    <option value="初中">初中</option>
	                                    <option value="高中">高中</option>
	                                    <option value="中专">中专</option>
	                                    <option value="大专">大专</option>
	                                    <option value="本科">本科</option>
	                                    <option value="博士及博士以上">博士及博士以上</option>
	                                </select>
                                </div>
							</div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">*入校时间：</label>
                                <div class="col-sm-8">
									<input placeholder="入校时间" required class="form-control layer-date"
										id="startdate_add" name="startdate">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">*毕业时间：</label>
								<div class="col-sm-8">
									<input placeholder="毕业时间" required class="form-control layer-date"
										id="enddate_add" name="enddate">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">说明:</label>
	                            <div class="col-sm-8">
                                    <input id="eduRemark" type="text" class="form-control">
                                </div>
                               </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="addEmpEduction()" class="btn btn-primary">保存</button>
                            </div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- 工作经历修改弹窗 -->
		<div class="modal inmodal" id="window_work_update" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">修改员工工作经历</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="upWorkForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" class="form-control" required disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*公司：</label>
	                            <div class="col-sm-8">
	                               <input id="companyname" name="companyname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*岗位：</label>
                                <div class="col-sm-8">
                                    <input id="degree" name="degree" type="text" required class="form-control">
                                </div>
                            </div>
								<div class="form-group">
	                                <label class="col-sm-3 control-label">*入职时间：</label>
	                                <div class="col-sm-8">
										<input placeholder="入职时间" class="form-control layer-date"
											id="startdate_work_up" name="startdate_work_up">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*离职时间：</label>
									<div class="col-sm-8">
										<input placeholder="离职时间" class="form-control layer-date"
											id="enddate_work_up" name="enddate_work_up">
									</div>
								</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*离职原因：</label>
                                <div class="col-sm-8">
                                	<input id="reason" name="reason" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                	<input id="worksRemark" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="upEmpWork()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!-- 工作经历新增弹窗 -->
		<div class="modal inmodal" id="window_work_add" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">添加员工工作经历</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addWorkForm" novalidate="novalidate">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*员工：</label>
                                <div class="col-sm-8">
	                                <select id="empid" name="empid" required class="form-control" disabled="disabled">
	                                    <c:forEach items="${emp}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">*公司：</label>
	                            <div class="col-sm-8">
	                               <input id="companyname" name="companyname" type="text" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*岗位：</label>
                                <div class="col-sm-8">
                                    <input id="degree" name="degree" type="text" required class="form-control">
                                </div>
                            </div>
								<div class="form-group">
	                                <label class="col-sm-3 control-label">*入职时间：</label>
	                                <div class="col-sm-8">
										<input placeholder="入职时间" class="form-control layer-date"
											id="startdate_work_add" name="startdate_work_add">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">*离职时间：</label>
									<div class="col-sm-8">
										<input placeholder="离职时间" class="form-control layer-date"
											id="enddate_work_add" name="enddate_work_add">
									</div>
								</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">*离职原因：</label>
                                <div class="col-sm-8">
                                	<input id="reason" name='reason' type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                	<input id="worksRemark" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" onclick="addEmpWork()" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
	</div>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
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
		function validateFamForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	empid :{
                    	required:true
                    },
                    contactname:{
                    	required:true
                    },
                    relationship:{
                    	required:true
                    },
                    phone:{
                    	required:true,
	                	digits:true,
	                	maxlength:11,
	                	minlength:11
                    }
	            },
	            messages: {
	            	empid :{
                    	required:icon + "请选择员工"
                    },
                    contactname:{
                    	required:icon + "请填写姓名"
                    },
                    relationship:{
                    	required:icon + "请填写关系"
                    },
                    phone:{
                    	required:icon + "请填写电话号码",
                    	digits: icon + "电话号码必须是数字",
                    	maxlength:icon + "电话号码必须是11位数",
	                	minlength:icon + "电话号码必须是11位数"
                    }
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		function validateEduForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	empid :{
                    	required:true
                    },
                    collegename:{
                    	required:true
                    },
                    degree :{
                    	required:true
                    },
                    startdate :{
                    	required:true
                    },
                    enddate :{
                    	required:true
                    }
	            	
	            },
	            messages: {
	            	empid :{
                    	required:icon + "请选择员工"
                    },
                    collegename:{
                    	required:icon + "请填写学校名称"
                    },
                    degree :{
                    	required:icon + "请填写学历"
                    },
                    startdate :{
                    	required:icon + "请填写入校时间"
                    },
                    enddate :{
                    	required:icon + "请填写毕业时间"
                    }
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		function validateWorkForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	empid :{
                    	required:true
                    },
	            	companyname :{
                    	required:true
                    },
                    degree:{
                    	required:true
                    },
                    startdate :{
                    	required:true
                    },
                    enddate :{
                    	required:true
                    },
                    reason :{
                    	required:true
                    }
	            },
	            messages: {
	            	empid :{
                    	required:icon + "请选择员工"
                    },
	            	companyname :{
                    	required:icon + "请填写公司名称"
                    },
                    degree:{
                    	required:icon + "请填写岗位"
                    },
                    startdate :{
                    	required:icon + "请填写入职时间"
                    },
                    enddate :{
                    	required:icon + "请填写离职时间"
                    },
                    reason :{
                    	required:icon + "请填写离职原因"
                    }
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#startdate_add',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#enddate_add',
			format : 'YYYY/MM/DD',
			//min : laydate.now(),
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#startdate',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		var end = {
			elem : '#enddate',
			format : 'YYYY/MM/DD',
			//min : laydate.now(),
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);
	</script>
	
	<script>
		//日期范围限制
		var start = {
			elem : '#startdate_work_add',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false
		};
		var end = {
			elem : '#enddate_work_add',
			format : 'YYYY/MM/DD',
			//min : laydate.now(),
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false
		};
		laydate(start);
		laydate(end);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#startdate_work_up',
			format : 'YYYY/MM/DD',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
			istime : true,
			istoday : false
		};
		var end = {
			elem : '#enddate_work_up',
			format : 'YYYY/MM/DD',
			//min : laydate.now(),
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false
		};
		laydate(start);
		laydate(end);
	</script>
</body>
</html>