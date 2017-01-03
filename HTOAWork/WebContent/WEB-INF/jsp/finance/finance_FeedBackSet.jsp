<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
    <script>
	    var srmRows = null;
	    //家庭情况行数据
	    var srmdRows = null;
	    function test(id){
	    	$("#stuRMId").val(id);
	    	//让模板明细表刷新
			$('#tb_stusrmd').bootstrapTable('refresh');
	    }
		$(function () {
			//激活提示
			$("[data-toggle='tooltip']").tooltip();
			
			//**********************************************************答辩模板****************
			$("#btn_srm_add").click(function(){
				//清空新增div中的数据
				$("#window_srm #id").val('');
				$("#window_srm #name").val('');
				$("#window_srm #score").val('');
				$("#window_srm #remark").val('');
				$('#window_srm').modal('show');
	            $("#btn_srm_save").attr("onclick","addSrm()");
	        });
			//修改按钮事件
			$("#btn_srm_update").click(function(){
				if(srmRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_srm #id").val(srmRows.id);
				$("#window_srm #name").val(srmRows.name);
				$("#window_srm #score").val(srmRows.score);
				$("#window_srm #remark").val(srmRows.remark)
	            
				$('#window_srm').modal('show');
	            $("#btn_srm_save").attr("onclick","updateSrm()");
	        });
			$("#btn_srm_delete").click(function(){
				if(srmRows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteSrm();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_stusrm').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/FeedBackSet/financefeedbacklistJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
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
	                if(srmRows != null){
	                	srmRows = null;
	                }
	                srmRows = row;
	            },
	            onUncheck: function(row) {
	                if(srmRows != null){
	                	srmRows = null;
	                }
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '模板名称'
				}, {
					field : 'score',
					title : '分数'
				}, {
					field : 'createTime',
					title : '创建时间'
				}, {
					field : 'updateTime',
					title : '修改时间'
				}, {
					field : 'remark',
					title : '描述'
				},]
			});
			
			//**********************************************************模板明细****************
			
			$("#btn_srmd_add").click(function(){
				//清空新增div中的数据
				$("#window_srmd #id").val('');
				$("#window_srmd #feedbacksetid").val('');
				$("#window_srmd #detailname").val('');
				$("#window_srmd #detailscore").val('');
	            
	            $("#btn_srmd_save").attr("onclick","addSrmd()");
	        });
			//修改按钮事件
			$("#btn_srmd_update").click(function(){
				if(srmdRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//把内容放到更新的列表
				$("#window_srmd #id").val(srmdRows.id);
				$("#window_srmd #feedbacksetid").val(srmdRows.feedbacksetid);
				$("#window_srmd #detailname").val(srmdRows.detailname);
				$("#window_srmd #detailscore").val(srmdRows.detailscore);
				//调用静态窗口
				$('#window_srmd').modal('show');
	            $("#btn_srmd_save").attr("onclick","updateSrmd()");
	        });
			$("#btn_srmd_delete").click(function(){
				if(srmdRows==null){
					parent.layer.alert('请选你要删除的数据！');
					return;
				}
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteSrmd();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_stusrmd').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/financeFeedBackDetail/financeFeedBackDetailJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrmd', //工具按钮用哪个容器
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
	                if(srmdRows != null){
	                	srmdRows = null;
	                }
	                srmdRows = row;
	            },//单击row事件
	            onUncheck: function(row) {
	                srmdRows = null;
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				}, {
					field : 'name',
					title : '模板id',
					formatter : function(value, row, index) {
						var finaceFeedbackset = row.finaceFeedbackset;
						if(finaceFeedbackset==null){
							return "-";
						}else{
							return finaceFeedbackset.name;
						}
					}
				}, {
					field : 'detailname',
					title : '打分项名称'
				},{
					field : 'detailscore',
					title : '打分项总分'
				},]
				
			});
			
		});
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				feedbacksetid : $("#stuRMId").val()
			};
			return temp;
		}
		//**********************************************************模板****************
		//新增学生状态，ajax提交
		function addSrm(){
			if(!validateForm($("#addForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/finance/FeedBackSet/addfinancefeedback";
			$.post(
				url,
				{
					name:$("#window_srm #name").val(),
					score:$("#window_srm #score").val(),
					remark:$("#window_srm #remark").val()
				},
				addSrmHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_srm").modal('hide')
		}
		function addSrmHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_stusrm').bootstrapTable('refresh');
		}
		
		//新增学生状态，ajax提交
		function updateSrm(){
			var id = srmRows.id;
			var url = "${pageContext.request.contextPath }/finance/FeedBackSet/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_srm #id").val(),
					name:$("#window_srm #name").val(),
					score:$("#window_srm #score").val(),
					remark:$("#window_srm #remark").val()
				},
				updateSrmHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
		}
		function updateSrmHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_stusrm').bootstrapTable('refresh');
		}
		
		function deleteSrm(){
			var id = srmRows.id;
			var url = "${pageContext.request.contextPath }/finance/FeedBackSet/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:srmRows.id  //从选中的rows中获取id
				},
				deleteSrmHandle,
				"text"
			);	
		}
		function deleteSrmHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stusrm').bootstrapTable('refresh');
		}
		
		
		//**********************************************************模板明细****************
		function addSrmd(){
			var url = "${pageContext.request.contextPath }/finance/financeFeedBackDetail/add";
			$.post(
				url,
				{
					feedbacksetid:$("#window_srmd #feedbacksetid").val(),
					detailname:$("#window_srmd #detailname").val(),
					detailscore:$("#window_srmd #detailscore").val()
				},
				addSrmdHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
		}
		function addSrmdHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_stusrmd').bootstrapTable('refresh');
			$('#tb_stusrm').bootstrapTable('refresh');
		}
		
		function updateSrmd(){
			var id = srmdRows.id;
			var url = "${pageContext.request.contextPath }/finance/financeFeedBackDetail/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_srmd #id").val(),
					feedbacksetid:$("#window_srmd #feedbacksetid").val(),
					detailname:$("#window_srmd #detailname").val(),
					detailscore:$("#window_srmd #detailscore").val()
				},
				updateSrmdHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
		}
		function updateSrmdHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_stusrmd').bootstrapTable('refresh');
		}
		
		function deleteSrmd(){
			var id = srmdRows.id;
			var url = "${pageContext.request.contextPath }/finance/financeFeedBackDetail/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:srmdRows.id  //从选中的rows中获取id
				},
				deleteSrmdHandle,
				"text"
			);	
		}
		function deleteSrmdHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stusrmd').bootstrapTable('refresh');
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
                        <h5>查询条件</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="tabs_panels.html#">选项1</a>
                                </li>
                                <li><a href="tabs_panels.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <p>这里写查询条件</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>反馈模板表</h5>
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
                    	<div id="toolbar_stuSrm" class="btn-group">
							<button id="btn_srm_add" type="button" class="btn btn-w-m btn-primary">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_srm_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_srm_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stusrm"></table>
					</div>
                </div>
            </div>
            <!--  
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>反馈模板明细表</h5>
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
                    	<div id="toolbar_stuSrmd" class="btn-group">
							<button id="btn_srmd_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_srmd">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_srmd_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_srmd_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	table代码就这些，用js构建表格 
						<table id="tb_stusrmd"></table>
                    </div>
                </div>
            -->
        </div>
    </div>
     <!--教育情况修改的弹窗  -->
		<div class="modal inmodal" id="window_srm" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">反馈模板</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板名称：</label>
                                <div class="col-sm-8">
                                    <input id="name" name="name" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分数：</label>
                                <div class="col-sm-8">
                                    <input id="score" name="score" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">备注：</label>
                                <div class="col-sm-8">
                                    <input id="remark" name="remark" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_srm_save" onclick="" class="btn btn-primary">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
		<!--家庭情况修改的弹窗  -->
		<div class="modal inmodal" id="window_srmd" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<input type="hidden" id="stuRMId"> 
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">模板明细</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板id：</label>
                                <div class="col-sm-8">
                                    <select id="feedbacksetid" name="feedbacksetid" class="form-control">
	                                	<option value="">---选择模板---</option>
	                                	<c:forEach items="${financeFeedBackList}" var="s">
	                                        <option value="${s.id}">${s.name}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">打分项目名称：</label>
                                <div class="col-sm-8">
                                    <input id="detailname" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">打分项目总分：</label>
                                <div class="col-sm-8">
                                    <input id="detailscore" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_srmd_save" onclick="" class="btn btn-primary" data-dismiss="modal">保存</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
     <!-- 自定义js -->
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
		function validateForm(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	name: "required",
	                score: "required",
					remark:"required"	            	
	            },
	            messages: {
	            	name: icon + "请输入模板名称",
	            	score: icon + "请输入分数",
	                remark:icon + "请输入备注"
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