<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生跟踪</title>

	<jsp:include page="../styleInclude.jsp"></jsp:include>
	
	<style>
		textarea {
		  resize : none;
		}
	</style>
	
	<script type="text/javascript">
		//全局变量，用来保存选中行的数据
	    var rows = null;
	   
		$(function(){
			//激活弹框提示
			$("[data-toggle='tooltip']").tooltip();
			
			//初始化table
			var tableInit = new TableInit();
			tableInit.Init();
			//初始化button按钮事件
			var buttonInit = new ButtonInit();
			buttonInit.Init();
			
			$("#btn_add").click(function(){
				//清空新增div中的数据
				$("#addTrackStudent #trackcontent").val('');
				$("#addTrackStudent #tracktime").val('');
				$("#addTrackStudent #trackresult").val('');
				$("#save").attr("onclick","addTrackStudent()");
			});
			
			$("#btn_edit").click(function(){
				if(rows == null){
					parent.layer.alert('请选择你要修改的数据！');
					return;
				}
				$("#addTrackStudent #id").val(rows.id);
				$("#addTrackStudent #trackcontent").val(rows.trackcontent);
				$("#addTrackStudent #tracktime").val(rows.tracktime);
				$("#addTrackStudent #trackresult").val(rows.trackresult);
				$('#addTrackStudent').modal('show');
				$("#save").attr("onclick","updateTrackStudent()");
								
			});
			
			$("#btn_delete").click(function(){
				if(rows == null){
					parent.layer.alert('请选择你要删除的数据！');
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
	                	deleteTrackStudent();
	                } else {
	                    swal("已取消", "您取消了删除操作！", "error");
	                }
	            });
				
			});
			//返回
			$("#btn_back").click(function(){
				
			});
		});
		
		var TableInit = function(){
			var table = new Object();
			//初始化table
			table.Init = function(){
				$('#tb_trackStudent').bootstrapTable({
					url : '${pageContext.request.contextPath}/market/followrecord/marketrecordListJson', //请求后台的URL（*）
					method : 'post', //请求方式（*）
					contentType: "application/x-www-form-urlencoded",
					toolbar : '#toolbar', //工具按钮用哪个容器
					striped : true, //是否显示行间隔色
					cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					pagination : true, //是否显示分页（*）
					sortable : false, //是否启用排序
					sortOrder : "asc", //排序方式
					queryParams : table.queryParams,//传递参数（*）
					sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
					pageNumber : 1, //初始化加载第一页，默认第一页
					pageSize : 10, //每页的记录行数（*）
					pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
					search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
					strictSearch : false,
					searchOnEnterKey : true, //按回车搜索
					showColumns : true, //是否显示所有的列
					showRefresh : true, //是否显示刷新按钮
					minimumCountColumns : 2, //最少允许的列数
					clickToSelect : true, //是否启用点击选中行
					//height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
					uniqueId : "id", //每一行的唯一标识，一般为主键列
					showToggle : true, //是否显示详细视图和列表视图的切换按钮
					cardView : false, //是否显示详细视图
					detailView : false, //是否显示父子表
					singleSelect: true,  //设置为单选
					onCheck: function(row) {
		                if(rows != null){
		                	rows = null;
		                }
		                rows = row;
		            },
		            onUncheck: function(row) {
		                if(rows != null){
		                	rows = null;
		                }
		            },
					columns : [ {
						checkbox : true
					}, {
						field : 'name',
						title : '跟踪学生',
						formatter : function(value, row, index) {
							var marketstudent = row.marketstudent;
							if(marketstudent == null){
								return "-";
							}else{
								return marketstudent.name;
							}
						}
					}, {
						field : 'trackcontent',
						title : '洽谈内容'
					},  {
						field : 'trackresult',
						title : '跟踪情况'
					},{
						field : 'tracktime',
						title : '跟踪时间',
						formatter : function(value, row, index) {
							var tracktime = row.tracktime;
							if(tracktime == null){
								return "-";
							}else{
								return value.substring(0, 10);
							}
						}
					}, ]
				});
			};
			//得到查询的参数
			table.queryParams = function(params){
				var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					//***这里的参数传到后台，用来进行分页处理*************************
					limit : params.limit, //页面大小
					offset : params.offset, //页码
					stuId: ${stuId}
				};
				return temp;
			};
			return table;
		};
		
		var ButtonInit = function(){
			var button = new Object();
			var postdata = {};
			button.Init = function(){
				//初始化页面上的控件
			};
			return button;
		};
		
		//新增学生跟踪
		function addTrackStudent(){
			//用来判断表单是否验证通过
			if(!validateForm($("#trackstuForm"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/market/followrecord/addMarketfllow";
			$.post(
				url,
				{
					stuId:${stuId},
					trackcontent:$("#addTrackStudent #trackcontent").val(),
					tracktime:$("#addTrackStudent #tracktime").val(),
					trackresult:$("#addTrackStudent #trackresult").val()
				},
				addTrackStudentHandle,
				"text"
			);
			//关闭新增窗口
			$("#addTrackStudent").modal('hide');
		}
		function addTrackStudentHandle(data){
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
			//刷新数据
			$("#tb_trackStudent").bootstrapTable('refresh');
		}
		//修改跟踪学生
		function updateTrackStudent(){
			var id = $("#addTrackStudent #id").val();
			var url = "${pageContext.request.contextPath }/market/followrecord/marketrecord/"+id;
			$.post(
				url,
				{
					_method:"PUT",//改成PUT提交
					stuId:${stuId},
					trackcontent:$("#addTrackStudent #trackcontent").val(),
					tracktime:$("#addTrackStudent #tracktime").val(),
					trackresult:$("#addTrackStudent #trackresult").val()
				},
				
				updateTrackStudentHandle,
				"text"
			);
			//用来关闭修改窗口***********
			$("#addTrackStudent").modal('hide');
		}
		function updateTrackStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否修改成功
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
			$('#tb_trackStudent').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		//删除跟踪学生
		function deleteTrackStudent(){
			var id = rows.id;
			var url = "${pageContext.request.contextPath }/market/followrecord/marketrecord/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:rows.id  //从选中的rows中获取id
				},
				deleteTrackStudentHandle,
				"text"
			);
		}
		function deleteTrackStudentHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				swal("删除成功！", "您已经永久删除了这条信息。", "success");
			}else{
				swal("删除失败", "服务器繁忙！", "error");
			}
			//刷新表格
			$('#tb_trackStudent').bootstrapTable('refresh');
		}
	</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="col-sm-3">
			
		</div>
		<%-- <div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins" style="margin-bottom: 0px">
	                    <div class="ibox-title">
	                        <!-- <h5>查询条件</h5> -->
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content">
	                        <form id="formSearch" class="form-horizontal">
								<div class="form-group" style="margin-top: 15px">
									<label class="control-label col-sm-1" for="txt_search_studid">跟踪学生</label>       
									<div class="col-sm-3">
										<select id="txt_search_studid" class="form-control">
											<option value=""></option>
		                                	<c:forEach items="${intenstu2}" var="intenstu2">
		                                        <option value="${intenstu2.id}">${intenstu2.name}</option>
		                                    </c:forEach>
		                                </select> 
									</div>
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
										<button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button>
									</div>
								</div>
							</form>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div> --%>
		
		<div class="panel panel-default">
			<div class="row">
	            <div class="col-sm-12">
	                <div class="ibox float-e-margins">
	                    <div class="ibox-title">
	                        <h5>意向学生跟踪</h5>
	                        <div class="ibox-tools">
	                            <a class="collapse-link">
	                                <i class="fa fa-chevron-up"></i>
	                            </a>
	                            <a class="close-link">
	                                <i class="fa fa-times"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <div class="ibox-content">
	                        <div id="toolbar" class="btn-group">
								<button id="btn_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#addTrackStudent">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
								<button id="btn_edit" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
								</button>
								<button id="btn_delete" type="button" class="btn btn-w-m btn-danger">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
								</button>
								<a href="${pageContext.request.contextPath}/market/intention/page">
									<button type="button" style="margin-left: 50px" id="btn_back" class="btn btn-primary">
									返回上一页面</button>
								</a>
							</div>
							
							<div class="modal inmodal" id="addTrackStudent" tabindex="-1" role="dialog" aria-hidden="true">
					            <div class="modal-dialog">
					                <div class="modal-content animated bounceInRight">
					                    <div class="modal-header">
					                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
					                        </button>
					                        <h4 class="modal-title">跟踪学生情况</h4>
					                        <small class="font-bold"></small>
					                     </div>
					                       <div class="modal-body">
					                       	<form class="form-horizontal m-t" id="trackstuForm" novalidate="novalidate">
												<input id="id" name="id" type="hidden">
												<input id="stuId" type="hidden" value="${studid}">
					                           	<div class="form-group">
					                                <label class="col-sm-3 control-label">洽谈内容：</label>
					                                <div class="col-sm-8">
					                                    <textarea rows="3" id="trackcontent" name="trackcontent" class="form-control"></textarea>
					                                </div>
					                            </div>
					                            <div class="form-group">
					                                <label class="col-sm-3 control-label">跟踪方式：</label>
					                                <div class="col-sm-4">
					                                	<select class="form-control" name="trackresult" id="trackresult" class="form-control">
						                                	<option value="QQ">QQ</option>
						                                	<option value="微信">微信</option>
						                                	<option value="手机">手机</option>
						                                	<option value="面谈">面谈</option>
						                                	<option value="面谈">其他</option>
						                                </select>
					                                </div>
					                            </div>
											</form>
					                       </div>
					                       <div class="modal-footer">
					                           <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
											   <button type="button" id="save" onclick="" class="btn btn-primary">保存</button>
					                       </div>
					                    </div>
					                </div>
					            </div>
							
							<!-- table代码就这些，用js构建表格 -->
							<table id="tb_trackStudent"></table>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		
	</div>
	
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	
	<script>
		//日期范围限制
		var tracktime = {
			elem : '#tracktime',
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
		laydate(tracktime);
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
	            	trackcontent: {
	                	required:true,
	                	maxlength:100
	                },
	                tracktime: {
	                	required:true
                    },
                    trackresult: {
	                	required:true,
	                	maxlength:100
	                }
	            },
	            messages: {
	            	trackcontent :{
                    	required:icon + "请输入洽谈内容",
                    	maxlength:icon + "字数超出范围（最多100字）"
                    },
                    tracktime :{
                    	required:icon + "请输入跟踪时间"
                    },
                    trackresult :{
                    	required:icon + "请输入跟踪情况",
                    	maxlength:icon + "字数超出范围（最多100字）"
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