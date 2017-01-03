<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
    <script>
	    var eduRows = null;
	    //家庭情况行数据
	    var famRows = null;
		$(function () {
			//激活提示
			$("[data-toggle='tooltip']").tooltip();
			
			//**********************************************************教育情况****************
			$("#btn_edu_add").click(function(){
				//清空新增div中的数据
				$("#window_edu #id").val('');
				$("#window_edu #school").val('');
				$("#window_edu #begindate").val('');
				$("#window_edu #enddate").val('');
	            
				$('#window_edu').modal('show');
	            $("#btn_edu_save").attr("onclick","addEdu()");
	        });
			//修改按钮事件
			$("#btn_edu_update").click(function(){
				if(eduRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_edu #id").val(eduRows.id);
				$("#window_edu #school").val(eduRows.school);
				$("#window_edu #begindate").val(eduRows.begindate);
				$("#window_edu #enddate").val(eduRows.enddate);
	            
				$('#window_edu').modal('show');
	            $("#btn_edu_save").attr("onclick","updateEdu()");
	        });
			$("#btn_edu_delete").click(function(){
				if(eduRows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteEdu();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_stuedu').bootstrapTable({
				url : '${pageContext.request.contextPath}/student/edufam/stuEduListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuEdu', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(eduRows != null){
	                	eduRows = null;
	                }
	                eduRows = row;
	            },
	            onUncheck: function(row) {
	                if(eduRows != null){
	                	eduRows = null;
	                }
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'stuname',
					title : '学生',
					formatter : function(value, row, index) {
						var student = row.student;
						if(student==null){
							return "-";
						}else{
							return student.stuname;
						}
					}
				}, {
					field : 'school',
					title : '学校名称'
				}, {
					field : 'begindate',
					title : '开始时间'
				}, {
					field : 'enddate',
					title : '结束时间'
				},]
			});
			
			//**********************************************************家庭情况****************
			
			$("#btn_fam_add").click(function(){
				//清空新增div中的数据
				$("#window_fam #id").val('');
				$("#window_fam #familyname").val('');
				$("#window_fam #relation").val('');
				$("#window_fam #familyhone").val('');
	            
	            $("#btn_fam_save").attr("onclick","addFam()");
	        });
			//修改按钮事件
			$("#btn_fam_update").click(function(){
				if(famRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_fam #id").val(famRows.id);
				$("#window_fam #familyname").val(famRows.familyname);
				$("#window_fam #relation").val(famRows.relation);
				$("#window_fam #familyhone").val(famRows.familyhone);
				//调用静态窗口
				$('#window_fam').modal('show');
	            $("#btn_fam_save").attr("onclick","updateFam()");
	        });
			$("#btn_fam_delete").click(function(){
				if(famRows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteFam();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_stufam').bootstrapTable({
				url : '${pageContext.request.contextPath}/student/edufam/stuFamListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuFam', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onCheck: function(row) {
	                 //$element是当前tr的jquery对象
	                if(famRows != null){
	                	famRows = null;
	                }
	                famRows = row;
	            },//单击row事件
	            onUncheck: function(row) {
	                famRows = null;
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'stuname',
					title : '学生',
					formatter : function(value, row, index) {
						var student = row.student;
						if(student==null){
							return "-";
						}else{
							return student.stuname;
						}
					}
				}, {
					field : 'familyname',
					title : '家长名称'
				},{
					field : 'relation',
					title : '亲属关系'
				}, {
					field : 'familyhone',
					title : '家长电话'
				},]
				
			});
			
		});
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				studentId : ${studentid}
			};
			return temp;
		}
		//**********************************************************教育情况****************
		//新增学生状态，ajax提交
		function addEdu(){
			if(!validateEduForm($("#eduForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/student/edufam/addStuEdu";
			$.post(
				url,
				{
					studentId:$("#window_edu #studentId").val(),
					school:$("#window_edu #school").val(),
					bdate:$("#window_edu #begindate").val(),
					edate:$("#window_edu #enddate").val()
				},
				addEduHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_edu").modal('hide')
		}
		function addEduHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_stuedu').bootstrapTable('refresh');
		}
		
		//新增学生状态，ajax提交
		function updateEdu(){
			if(!validateEduForm($("#eduForm"))){
				return;
			}
			var id = eduRows.id;
			var url = "${pageContext.request.contextPath }/student/edufam/edu/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_edu #id").val(),
					studentId:$("#window_edu #studentId").val(),
					school:$("#window_edu #school").val(),
					bdate:$("#window_edu #begindate").val(),
					edate:$("#window_edu #enddate").val()
				},
				updateEduHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_edu").modal('hide')
		}
		function updateEduHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_stuedu').bootstrapTable('refresh');
		}
		
		function deleteEdu(){
			var id = eduRows.id;
			var url = "${pageContext.request.contextPath }/student/edufam/edu/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:eduRows.id  //从选中的rows中获取id
				},
				deleteEduHandle,
				"text"
			);	
		}
		function deleteEduHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stuedu').bootstrapTable('refresh');
		}
		
		
		//**********************************************************家庭情况****************
		function addFam(){
			if(!validateFamForm($("#famForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/student/edufam/addStuFam";
			$.post(
				url,
				{
					studentId:$("#window_fam #studentId").val(),
					familyname:$("#window_fam #familyname").val(),
					relation:$("#window_fam #relation").val(),
					familyhone:$("#window_fam #familyhone").val()
				},
				addFamHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_fam").modal('hide')
		}
		function addFamHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_stufam').bootstrapTable('refresh');
		}
		
		function updateFam(){
			if(!validateFamForm($("#famForm"))){
				return;
			}
			var id = famRows.id;
			var url = "${pageContext.request.contextPath }/student/edufam/fam/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_fam #id").val(),
					studentId:$("#window_fam #studentId").val(),
					familyname:$("#window_fam #familyname").val(),
					relation:$("#window_fam #relation").val(),
					familyhone:$("#window_fam #familyhone").val()
				},
				updateFamHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_fam").modal('hide')
		}
		function updateFamHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_stufam').bootstrapTable('refresh');
		}
		
		function deleteFam(){
			var id = famRows.id;
			var url = "${pageContext.request.contextPath }/student/edufam/fam/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:famRows.id  //从选中的rows中获取id
				},
				deleteFamHandle,
				"text"
			);	
		}
		function deleteFamHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stufam').bootstrapTable('refresh');
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
                        <a href="${pageContext.request.contextPath}/student/stuList">
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
                        <p>显示该学生的过往教育经历， 家庭成员信息</p>
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
                    	<div id="toolbar_stuEdu" class="btn-group">
							<button id="btn_edu_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_edu_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_edu_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stuedu"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>家庭成员</h5>
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
                    	<div id="toolbar_stuFam" class="btn-group">
							<button id="btn_fam_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_fam">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_fam_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_fam_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stufam"></table>
                    </div>
                </div>
            </div>
        </div>
     </div>
     <!--教育情况修改的弹窗  -->
		<div class="modal inmodal" id="window_edu" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">学生教育情况</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="eduForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<input id="studentId" type="hidden" value="${studentid}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学校名称：</label>
                                <div class="col-sm-8">
                                    <input id="school" name="school" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">开始时间：</label>
                                <div class="col-sm-8">
									<input placeholder="入学时间" class="form-control  layer-date"
										id="begindate" name="begindate">
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">结束时间：</label>
                                <div class="col-sm-8">
									<input placeholder="毕业时间" class="form-control  layer-date"
										id="enddate" name="enddate">
								</div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_edu_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!--家庭情况修改的弹窗  -->
		<div class="modal inmodal" id="window_fam" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">学生家庭情况</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="famForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <input id="studentId" type="hidden" value="${studentid}">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">家长名称：</label>
                                <div class="col-sm-8">
                                    <input id="familyname" name="familyname" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">与学生关系：</label>
                                <div class="col-sm-8">
                                    <input id="relation" name="relation" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">家长电话：</label>
                                <div class="col-sm-8">
                                    <input id="familyhone" name="familyhone" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_fam_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
     <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#enddate',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
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
			elem : '#begindate',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(), //设定最小日期为当前日期
			max : '2099-06-16 23:59:59', //最大日期
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
		function validateEduForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	school: "required",
	            	begindate: {
                     	required:true,
                     	date:true
                    },
                    enddate: {
                     	required:true,
                     	date:true
                    }
	            },
	            messages: {
	            	school: icon + "请输入学校名称",
	            	begindate: {
                    	required:icon + "请输入开学时间",
                    	date: icon + "请输入正确的日期"
                    },
                    enddate: {
                    	required:icon + "请输入毕业时间",
                    	date: icon + "请输入正确的日期"
                    }
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		function validateFamForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	familyname: "required",
	            	relation: "required",
	            	familyhone: {
                     	required:true,
 	                	digits:true
                    }
	            },
	            messages: {
	            	familyname: icon + "请输入家长姓名",
	            	relation: icon + "请输入与学生的关系",
	            	familyhone: {
                    	required:icon + "请输入家长电话号码",
                    	digits: icon + "电话号码错误"
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