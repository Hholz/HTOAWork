<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>员工管理</title>
    <jsp:include page="../styleInclude.jsp"></jsp:include>
    
    <script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
	    $(function () {
	 	
			//1.初始化Table
			var oTable = new TableInit();
			oTable.Init();
	
			//2.初始化Button的点击事件
			var oButtonInit = new ButtonInit();
			oButtonInit.Init();
			
			//新增按钮事件*************************
			//修改按钮事件
			$("#btn_edit").click(function(){
				//把内容放到更新的列表
				if(rows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_update #id").val(rows.id);
	            $("#window_update #empname").val(rows.empname);
	            $("#window_update #sex").val(rows.sex);
	            $("#window_update #depid").val(rows.depid);
	            $("#window_update #fingerprint").val(rows.fingerprint);
	            $("#window_update #nation").val(rows.nation);
	            $("#window_update #phone").val(rows.phone);
	            $("#window_update #birthday").val(rows.birthday);
	            $("#window_update #cardno").val(rows.cardno);
	            $("#window_update #qqcode").val(rows.qqcode);
	            $("#window_update #weixin").val(rows.weixin);
	            $("#window_update #email").val(rows.email);
	            $("#window_update #married").val(rows.married);
	            $("#window_update #address").val(rows.address);
	            $("#window_update #bank").val(rows.bank);
	            $("#window_update #banknumber").val(rows.banknumber);
	            $("#window_update #alipay").val(rows.alipay);
	            $('#window_update').modal('show');
	        });
			//删除按钮事件
			//*************************************************************************按钮事件
			$('#btn_delete').click(function () {
				if(rows==null){
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
	                	deleteStudent();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
	        });
			//条件查询click事件注册
			$("#query").click(function() {
				$("#tb_departments").bootstrapTable('refresh');
			});
		});

	    var TableInit = function() {
			var oTableInit = new Object();
			//初始化Table
			oTableInit.Init = function() {
				$('#tb_departments').bootstrapTable({
					url : '${pageContext.request.contextPath}/dailyWork/emp/0', //请求后台的URL（*）
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
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					height : 520, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect : true, //设置为单选
					onCheck: function(row) {
		                 //$element是当前tr的jquery对象
		                if(rows != null){
		                	rows = null;
		                }
		                rows = row;
		            },
		            onUncheck: function(row) {
		                if(rows != null){
		                	rows = null;
		                }
		            },//单击row事件
					columns : [ {
						checkbox : true
					}, {
						field : 'fingerprint',
						title : '员工号'
					}, {
						field : 'empname',
						title : '姓名'
					}, {
						field : 'dep.depname',
						title : '部门'
					}, {
						field : 'nation',
						title : '籍贯'
					}, {
						field : 'sex',
						title : '性别'
					}, {
						field : 'birthday',
						title : '生日'
					},{
						field : 'phone',
						title : '手机号'
					},{
						field : 'cardno',
						title : '身份证号'
					},{
						field : 'opt',
						title : '操作',
						formatter : function(value, row, index) {
							return '<a href="${pageContext.request.contextPath }/dailyWork/emp/empInfo/'+row.id+'" >其他信息</a>';
						}
					},]
				});
			};

			//得到查询的参数
			oTableInit.queryParams = function(params) {
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					empname : $("#find_empname").val(),
					depid : $("#find_depid").val(),
					nation : $("#find_nation").val(),
					sex : $("#find_sex").val(),
					birthday : $("#find_birthday").val(),
					phone : $("#find_phone").val(),
					cardno : $("#find_cardno").val()
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
		
		//新增学生，ajax提交
		function addStudent(){
			if(!validateForm($("#addForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/emp/addemp";
			$.post(
				url,
				{	
					_method:'GET',
					id:$("#window_add #id").val(),
					empname:$("#window_add #empname").val(),
	                sex:$("#window_add #sex").val(),
	                depid:$("#window_add #depid").val(),
	                fingerprint:$("#window_add #fingerprint").val(),
	                nation:$("#window_add #nation").val(),
	                birthday:$("#window_add #birthday_add").val(),
	                phone:$("#window_add #phone").val(),
	                cardno:$("#window_add #cardno").val(),
	                qqcode:$("#window_add #qqcode").val(),
	                weixin:$("#window_add #weixin").val(),
	                email:$("#window_add #email").val(),
	                married:$("#window_add #married").val(),
	                address:$("#window_add #address").val(),
	                bank:$("#window_add #bank").val(),
	                alipay:$("#window_add #alipay").val(),
	                banknumber:$("#window_add #banknumber").val()
				},
				addStudentHandle,
				"text"
			);	
			
			//用来关闭新增窗口***********
			$("#window_add").modal('hide');
			//刷新数据
			$('#tb_departments').bootstrapTable('refresh');
			//清空新增div中的数据
			$("#window_add #id").val('');
            $("#window_add #empname").val('');
            $("#window_add #fingerprint").val('');
            $("#window_add #nation").val('');
            $("#window_add #birthday_add").val('');
            $("#window_add #phone").val('');
            $("#window_add #cardno").val('');
            $("#window_add #qqcode").val('');
            $("#window_add #weixin").val('');
            $("#window_add #email").val('');
            $("#window_add #married").val('');
            $("#window_add #address").val('');
            $("#window_add #bank").val('');
            $("#window_add #banknumber").val('');
            $("#window_add #alipay").val('');
		}
		function addStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		//修改学生，ajax提交,PUT
		function updateStudent(){
			if(!validateForm($("#updateForm"))){
				return;
			}
			var id =$("#window_update #id").val(); 
			var url = "${pageContext.request.contextPath }/dailyWork/emp/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					empname:$("#window_update #empname").val(),
	                sex:$("#window_update #sex").val(),
	                depid:$("#window_update #depid").val(),
	                fingerprint:$("#window_update #fingerprint").val(),
	                nation:$("#window_update #nation").val(),
	                birthday:$("#window_update #birthday").val(),
	                phone:$("#window_update #phone").val(),
	                cardno:$("#window_update #cardno").val(),
	                qqcode:$("#window_update #qqcode").val(),
	                weixin:$("#window_update #weixin").val(),
	                email:$("#window_update #email").val(),
	                married:$("#window_update #married").val(),
	                address:$("#window_update #address").val(),
	                bank:$("#window_update #bank").val(),
	                alipay:$("#window_update #alipay").val(),
	                banknumber:$("#window_update #banknumber").val()
				},
				updateStudentHandle,
				"text"
			);	
			//用来关闭修改窗口***********
			$("#window_update").modal('hide')
		}
		function updateStudentHandle(data){
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
			$('#tb_departments').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		
		//***************************************************************************************删除
		//删除学生，ajax提交,DELETE
		function deleteStudent(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/dailyWork/emp/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteStudentHandle,
				"text"
			);	
		}
		function deleteStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_departments').bootstrapTable('refresh');
		}
	</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>查询条件</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
							<!-- <a class="close-link"> <i class="fa fa-times"></i></a> -->
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
                                <label class="col-sm-1 control-label">姓名:</label>
                                <div class="col-sm-3">
                                    <input id="find_empname" type="text" required class="form-control">
                                </div>
                           		<label class="col-sm-1 control-label">部门:</label>
	                            <div class="col-sm-3">
	                                <select id="find_depid" class="form-control">
	                                	<option value="">---------------</option>
	                                    <c:forEach items="${dep}" var="dep">
	                                    	<option value="${dep.id}">${dep.depname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                                <label class="col-sm-1 control-label">籍贯:</label>
                                <div class="col-sm-3">
                                    <input id="find_nation" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">性别:</label>
                                <div class="col-sm-3">
									<select id="find_sex" class="form-control">
										<option value="">不限</option>
	                                    <option value="男">男</option>
	                                    <option value="女">女</option>
	                                </select>
                                </div>
                            	<label class="col-sm-1 control-label">生日:</label>
	                            <div class="col-sm-4">
                                    <input placeholder="生日" class="form-control layer-date" id="find_birthday">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-1 control-label">手机号:</label>
                                <div class="col-sm-3">
                                	<input id="find_phone" type="text" required class="form-control">
                                </div>
                                <label class="col-sm-1 control-label">身份证号:</label>
                                <div class="col-sm-4">
                                	<input id="find_cardno" type="text" required class="form-control">
                                </div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">查询</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
			</button>
			<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal" >
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
			</button>
			<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
			</button>
		</div>
		
		<div class="modal inmodal fade in" id="window_update" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<form class="form-horizontal m-t" id="updateForm" novalidate="novalidate">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">修改员工信息</h4>
						</div>
	                    <div class="col-sm-6" style="background: rgb(248, 250, 251);">
	                    	<input id="id" type="hidden"/>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">姓名：</label>
	                            <div class="col-sm-8">
	                                <input id="empname" name="empname" type="text" required class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                       		<label class="col-sm-3 control-label">部门：</label>
		                        <div class="col-sm-8">
		                            <select id="depid" name="depid" required class="form-control">
		                                <c:forEach items="${dep}" var="dep">
		                                	<option value="${dep.id}">${dep.depname}</option>
		                                </c:forEach>
		                            </select>
		                        </div>
	                    	</div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">指纹：</label>
	                            <div class="col-sm-8">
	                                <input id="fingerprint" name="fingerprint" type="number" required class="form-control">
	                            </div>
                        	</div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">籍贯：</label>
	                            <div class="col-sm-8">
	                                <input id="nation" name="nation" type="text" required class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">性别：</label>
	                            <div class="col-sm-8">
	                                <select id="sex" name="sex" required class="form-control">
	                                 <option value="男">男</option>
	                                 <option value="女">女</option>
	                             </select>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                        	<label class="col-sm-3 control-label">生日：</label>
	                         <div class="col-sm-8">
	                             <input placeholder="生日" name="birthday" required class="form-control layer-date" id="birthday">
	                         </div>
	                    	</div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">手机号：</label>
	                            <div class="col-sm-8">
	                            	<input id="phone" name="phone" type="text" required class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">身份证号：</label>
	                            <div class="col-sm-8">
	                            	<input id="cardno" name="cardno" type="text" required class="form-control">
	                            </div>
	                        </div>
	                	</div>
	                    <div class="col-sm-6" style="background: rgb(248, 250, 251);">
	                    	<div class="form-group">
	                            <label class="col-sm-3 control-label">QQ：</label>
	                            <div class="col-sm-8">
	                                <input id="qqcode" name="qqcode" type="text" class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                       		<label class="col-sm-3 control-label">微信：</label>
	                         <div class="col-sm-8">
	                             <input id="weixin" name="weixin" type="text" class="form-control">
	                         </div>
	                    	</div>
	                        <div class="form-group">
	                       		<label class="col-sm-3 control-label">邮箱：</label>
	                         <div class="col-sm-8">
	                             <input id="email" name="email" type="email"  class="form-control">
	                         </div>
	                    	</div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">婚姻状况：</label>
	                            <div class="col-sm-8">
	                                <select id="married" name="married" required class="form-control">
	                                    <option value="已婚">已婚</option>
	                                    <option value="未婚">未婚</option>
	                                    <option value="离异">离异</option>
	                                    <option value="丧偶">丧偶</option>
	                                </select>
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">开户银行：</label>
	                            <div class="col-sm-8">
	                                <input id="bank" name="bank" type="text"  class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                        	<label class="col-sm-3 control-label">银行账号：</label>
	                         <div class="col-sm-8">
	                             <input id="banknumber" name="banknumber" type="text"  class="form-control">
	                         </div>
	                    	</div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">支付宝账号：</label>
	                            <div class="col-sm-8">
	                            	<input id="alipay" name="alipay" type="text" class="form-control">
	                            </div>
	                        </div>
	                        <div class="form-group">
	                            <label class="col-sm-3 control-label">家庭住址：</label>
	                            <div class="col-sm-8">
	                            	<input id="address" name="address" type="text" class="form-control">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="modal-footer">
	                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                         <button type="button" onclick="updateStudent()" class="btn btn-primary">保存</button>
	                    </div>
					</div>
				</form>
			</div>
		</div>
		
		<div class="modal inmodal fade in" id="window_add" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<form class="form-horizontal m-t" id="addForm" novalidate="novalidate">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
							</button>
							<h4 class="modal-title">添加员工信息</h4>
						</div>
                    	<div class="col-sm-6" style="background: rgb(248, 250, 251);">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-8">
                                    <input id="empname" name="empname" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">部门：</label>
	                            <div class="col-sm-8">
	                                <select id="depid" name="depid" required class="form-control">
	                                    <c:forEach items="${dep}" var="dep">
	                                    	<option value="${dep.id}">${dep.depname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                        	</div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">指纹：</label>
	                            <div class="col-sm-8">
	                                <input id="fingerprint" name="fingerprint" type="number" required class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">籍贯：</label>
                                <div class="col-sm-8">
                                    <input id="nation" name="nation" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">性别：</label>
                                <div class="col-sm-8">
                                    <select id="sex" name="sex" required class="form-control">
	                                    <option value="男">男</option>
	                                    <option value="女">女</option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">生日：</label>
	                            <div class="col-sm-8">
	                                <input placeholder="生日" name="birthday" required class="form-control layer-date" id="birthday_add">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">手机号：</label>
                                <div class="col-sm-8">
                                	<input id="phone" name="phone" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">身份证号：</label>
                                <div class="col-sm-8">
                                	<input id="cardno" name="cardno" type="text" required class="form-control">
                                </div>
                            </div>
                    	</div>
                        <div class="col-sm-6" style="background: rgb(248, 250, 251);">
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">QQ：</label>
                                <div class="col-sm-8">
                                    <input id="qqcode" name="qqcode" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">微信：</label>
	                            <div class="col-sm-8">
	                                <input id="weixin" name="weixin" type="text" class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">邮箱：</label>
	                            <div class="col-sm-8">
	                                <input id="email" name="email" type="email"  class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">婚姻状况：</label>
                                <div class="col-sm-8">
                                	<select id="married" name="married" required class="form-control">
	                                    <option value="已婚">已婚</option>
	                                    <option value="未婚">未婚</option>
	                                    <option value="离异">离异</option>
	                                    <option value="丧偶">丧偶</option>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">开户银行：</label>
                                <div class="col-sm-8">
                                    <input id="bank" name="bank" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                            	<label class="col-sm-3 control-label">银行账号：</label>
	                            <div class="col-sm-8">
	                                <input id="banknumber" name="banknumber" type="text"  class="form-control">
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">支付宝账号：</label>
                                <div class="col-sm-8">
                                	<input id="alipay" name="alipay" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">家庭住址：</label>
                                <div class="col-sm-8">
                                	<input id="address" name="address" type="text" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                             <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                             <button type="button" onclick="addStudent()" class="btn btn-primary">保存</button>
                        </div>
					</div>
				</form>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="tb_departments"></table>
	</div>
<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#find_birthday',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			/* max : '2099-06-16 23:59:59', //最大日期 */
			max : laydate.now(),
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#birthday',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			/* max : '2099-06-16 23:59:59', //最大日期 */
			max : laydate.now(),
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		laydate(start);
	</script>
	<script>
		//日期范围限制
		var start = {
			elem : '#birthday_add',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			/* max : '2099-06-16 23:59:59', //最大日期 */
			max : laydate.now(),
			istime : true,
			istoday : false,
			choose : function(datas) {
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
			}
		};
		laydate(start);
	</script>
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
	            	empname: {
	            		required:true
                    },
                    depid: {
                    	required:true,
                    },
                    nation :{
                    	required:true
                    },
                    sex:{
                    	required:true
                    },
                    birthday: {
                    	required:true
                    },
                    phone: {
                    	required:true,
	                	digits:true,
	                	maxlength:11,
	                	minlength:11
                    },
                    cardno :{
                    	required:true,
	                	digits:true,
	                	maxlength:18,
	                	minlength:18
                    },
                    fingerprint:{
                    	required:true,
	                	digits:true
                    }
	            },
	            messages: {
	            	empname: {
	            		required:icon + "请输入员工姓名"
                    },
                    depid: {
                    	required:icon + "请选择员工部门",
                    },
                    nation :{
                    	required:icon + "请填写员工籍贯"
                    },
                    sex:{
                    	required:icon + "请选择员工性别"
                    },
                    birthday: {
	                	digits: icon + "请填写员工生日"
                    },
                    phone: {
                    	required:icon + "请填写员工电话号码",
                    	digits: icon + "电话号码必须是数字",
                    	maxlength:icon + "电话号码必须是11位数",
	                	minlength:icon + "电话号码必须是11位数"
                    },
                    cardno :{
                    	required:icon + "请填写员工身份证号",
                    	digits: icon + "身份证号必须是数字",
                    	maxlength:icon + "身份证号必须是18位数",
	                	minlength:icon + "身份证号必须是18位数"
                    },
                    fingerprint:{
                    	required:icon + "请输入员工指纹"
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
</body>
</html>