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
		
		
		
	</script>
    <script>
	    var atRow = null;
	    var authList = null;
	    var roleId = null;
	    var rows = null;
	    var arr = new Array();
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
	    
	    //条件查询click事件注册
		function select() {
			$("#tb_auth").bootstrapTable('refresh');
		};
		//清空条件
		function cleantable() {
			
			$("#holidaystat").val("");
			$("#start").val("");
			$("#end").val("");
			$("#empname").val("");
			$("#tb_auth").bootstrapTable('refresh');
		};
	    
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
	    
	    function clickrow(){
	    	var ts = '';
	    	var id = rows.id;
	    	ts = "当前选中编号为:"+id;
	    	//alert(ts);
	    	$("#tsText").html(ts);
	    }
	    
	    function updatedits(){
	   
	    	$('#window_update #id ').val(rows.id);
			$('#window_update #attenstatus ').val(rows.attenstatus);
			$('#window_update #remark ').val(rows.remark);
			$('#window_update #late ').val(rows.late);
			$('#window_update #absent ').val(rows.absent);
			$('#window_update #leave ').val(rows.leave);
			
	    }
	    
	    function btnaddpd(){
	    	var si =  $("#panduan #sizes").val();
			if(si==1){
				swal({
	                   title: "该表已有全部人员上月考勤统计,确定覆盖？",
	                   text: "覆盖后将无法显示，请谨慎操作！",
	                   type: "warning",
	                   showCancelButton: true,
	                   confirmButtonColor: "#4876FF",
	                   confirmButtonText: "是的，我要覆盖！",
	                   cancelButtonText: "让我再考虑一下…",
	                   cancelButtonColor: "#00FFFF",
	                   closeOnConfirm: false,
	                   closeOnCancel: false
	           },
	           function (isConfirm) {
	               if (isConfirm) {
	               	//调用
	               	del_add();
	               } else {
	                   swal("已取消", "您取消了该操作！", "error");
	               }
	           });
	    	}else {
	    		btnadd();
	    	}
	    	
	    }
	    
	    function del_add(){
	    	var url = "${pageContext.request.contextPath }/dailyWork/deletelist";
	    	
	    	$.post(
				url,
				{
					workday : $("#panduan #ati").val()
				},
				function(data){
					if(data>=1){
						swal({
							title : "成功",
							text : "你已经完成改操作！",
							type : "success"
						});
					}else{
						swal("添加失败", "请检查你的数据..！", "error");
					}
				},
				"text"
			);	
	    }
	    
	    function btnadd(){
	    	
	    	var url = "${pageContext.request.contextPath }/dailyWork/addAttenstatis";//addAttenstatis-AttenstatisList/add
			$.post(
				url,
				{
					status : 1
				},
				function(data){
					if(data>=1){
						swal({
							title : "成功",
							text : "你已经完成改操作！",
							type : "success"
						});
					}else{
						swal("添加失败", "请检查你的数据..！", "error");
					}
				},
				"text"
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
			$('#tb_auth').bootstrapTable({
				url : '${pageContext.request.contextPath}/dailyWork/AttenstatisList', //请求后台的URL（*）
				detailView: true,//父子表
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParam,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, //每页的记录行数（*）
				showRefresh : true, //是否显示刷新按钮
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect : true, //设置为单选
				onClickRow: function(row,$element) {
					if(rows != null){
	                	rows = null;
	                }
	                rows = row;
	                
	                clickrow();
	            },//单击row事件
	            onUncheck: function(row) {
	            	 if(rows != null){
		                rows = null;
		             }
	            },
	            columns : [{
					field : 'id',
					align : 'center',
					visible : false,
					title : '编号'
				}, {
					field : 'finger',
					align : 'center',
					title : '员工号'
				}, {
					field : 'empname',
					align : 'center',
					title : '员工姓名'
				}, {
					field : 'depid',
					align : 'center',
					title : '部门',
					formatter : function(value, row, index) {
						var dep = row.dep;
						if(dep==null){
							return "-";
						}else {
							return dep.depname;
						}
					}
				}, {
					field : 'workday',
					align : 'center',
					title : '时期'
				}, {
					field : 'absent',
					align : 'center',
					title : '旷工 /天'
				}, {
					field : 'leave',
					align : 'center',
					title : '请假 /天'
				}, {
					field : 'late',
					align : 'center',
					title : '迟到 /min'
				},{	
					field : 'attenstatus',
					align : 'center',
					title : '考勤状态',
					formatter : function(value, row, index) {
						var stat = row.attenstatus;
						if(stat == 0){
							return "<span class='btn btn-warning btn-xs dropdown-toggle'>旷工</span>";
						}else if(stat == 2){
							return "<span class='btn btn-default btn-xs dropdown-toggle'>请假</span>";
						}else if(stat == 4){
							return "<span class='btn btn-info btn-xs dropdown-toggle'>迟到</span>";
						}else if(stat == 5){
							return "<span class='btn btn-danger btn-xs dropdown-toggle'>异常</span>";
						}else if(stat == 6){
							return "<span class='btn btn-success btn-xs dropdown-toggle'>正常</span>";
						}else if(stat == 1){
							return "<span class='btn btn-warning btn-xs dropdown-toggle'>旷工半天</span>";
						}else if(stat == 3){
							return "<span class='btn btn-default btn-xs dropdown-toggle'>请假半天</span>";
						}else if(stat == 7){
							return "<span class='btn btn-warning btn-xs dropdown-toggle'>忘打卡一次</span>";
						}else if(stat == 8){
							return "<span class='btn btn-warning btn-xs dropdown-toggle'>忘打卡两次</span>";
						}else{
							return "-";
						}
					}
				},{	
					field : 'remark',
					align : 'center',
					title : '简叙'
				},{
					field : '',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						//rows = row;
						return "<button onclick='updatedits()' class='btn btn-primary btn-xs dropdown-toggle'  data-toggle='modal' data-target='#window_update'><i class='glyphicon glyphicon-pencil'></i>修改</button>";
					}
				}],
				//注册加载子表的事件。注意下这里的三个参数！
                onExpandRow: function (index, row, $detail) {
                	rows = row;
                    InitSubTable(index, row, $detail);
                }
			});
		});
	    //初始化子表格(无线循环)
	    function InitSubTable(index, row, $detail) {
	        var typeId = row.id;
	        var time = row.workday;
	        var finger = row.finger;
	        var cur_table = $detail.html('<table></table>').find('table');
	        $(cur_table).bootstrapTable({
	            url : '${pageContext.request.contextPath}/dailyWork/AttenstatiszbList?fingerprint='+finger+'&workday='+time, //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : childParam,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect : true, //设置为单选
	            onCheck: function(row) {
	            	if(atRow != null){
	            		atRow = null;
	                }
	            	atRow = row;
	            },
	            onUncheck: function(row) {
	            	if(atRow != null){
	            		atRow = null;
		             }
	            },
	            columns : [ {
	            	 field: 'state',
	                 checkbox: true,
	                 formatter:stateFormatter
				}, {
					field : 'empname',
					align : 'center',
					title : '员工姓名'
				}, {
					field : 'workday',
					align : 'center',
					title : '打卡时间'
				}, {
					field : 'status',
					align : 'center',
					title : '考勤状态',
					formatter : function(value, row, index) {
						var stat = row.status;
						if(stat == 0){
							return "节假日";
						}else if(stat == 1){
							return "未打卡";
						}else if(stat == 2){
							return "迟到";
						}else if(stat == 3){
							return "无效";
						}else if(stat == 4){
							return "签到";
						}else{
							return "-";
						}
					}
				}, {
					field : 'type',
					align : 'center',
					title : '时段',
					formatter : function(value, row, index) {
						var type = row.type;
						var a = 'a';
						var p='p';
						if(type==a){
							return "上午";
						}else if(type==p){
							return "下午";
						}else {
							return "-";
						}
					}
				}]
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
		function queryParam(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				attenstatus : $('#formSearch #holidaystat').val() ,
				workday : $('#formSearch #start').val(),
				endday : $('#formSearch #end').val(),
				empname :$('#formSearch #empname').val()
			};
			return temp;
		}
		
		function childParam(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset //页码
			
			};
			return temp;
		}
		
		//修改考勤，ajax提交
		function updateStart() {
			
			if(!validateForm($("#updateholidays"))){
				return;
			} 
			
			var id = $("#window_update #id").val();
		
			var url = "${pageContext.request.contextPath }/dailyWork/"+ id;
			$.post(url, {
				_method : 'PUT',//改成PUT提交
				attenstatus : $('#window_update #attenstatus').val(),
				remark : $('#window_update #remark').val(),
			//	late : $('#window_update #late').val(),
			//	absent : $('#window_update #absent').val(),
			//	leave : $('#window_update #leave').val()
				
			}, updateStartHandle, "text");
			$("#window_update").modal('hide')
			
		}
		function updateStartHandle(data) {
			//从后台返回出来的int类型数据，用来判断是否新增成功
			
			if(data<0){
				swal("修改失败", "请检查输入的数据！", "error");
			}else{
				swal({
					title : "成功",
					text : "你已经完成数据的修改！",
					type : "success"
				});
			}
			//刷新表格
			$('#tb_auth').bootstrapTable('refresh');
			//把保存选中行的rows变量清零，很重要，防止缓存
			rows = null;
		}
		
		function selectstuatt(){
			var k = $('#window_update #attenstatus ').val();
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
                        <h5><span class="glyphicon glyphicon-question-sign"></span>条件查询</h5>
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
                        <form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="control-label col-sm-1" style="margin-left: 30px">时间：</label>
								<div class="col-sm-2">
									<input placeholder="开始日期"  class="form-control layer-date"
										id="start" name="start"/>
								</div>
								<div class="col-sm-2">
									<input placeholder="结束日期" class="form-control layer-date"
										id="end" name="end"/>
								</div>
								<label class="control-label col-sm-1" >状态：</label>
								<div class="col-sm-2">
									<select class="chosen-select" style="width:120px;height:33px;" tabindex="2" name="holidaystat" id="holidaystat">
		                              	<option value=""> --请选择--</option >
		                              	<option value="4">迟到</option >
		                              	<option value="2">请假</option >
		                              	<option value="6">正常</option >
		                              	<option value="0">旷工</option >
		                              	<option value="5">异常</option >
		                              	<option value="1">旷工半天</option >
		                              	<option value="3">请假半天</option >
		                            </select>
								</div>
								<label class="control-label col-sm-1">员工：</label>
								<div class="col-sm-2">
									<select class="chosen-select" style="width:120px;height:33px;" tabindex="2" name="empname" id="empname">
										<option value=""> -请选择---</option >
		                              	<c:forEach items="${empList}" var="e">
		                                   <option value="${e.empname}">${e.empname}</option >
		                                </c:forEach>
									</select>
								</div>
							
							</div>
							<div class="form-group">
								<div class="col-sm-3" style="margin-left: 450px">
									<button type="button" class="btn btn-primary" id="query" onclick="select()">查询</button>
									<button type="button" id="clean" class="btn btn-primary" onclick="cleantable()">清空</button>
								</div>
							</div>
						</form>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="panduan">
			<div>
				<input type="hidden" name="sizes" id="sizes" value="${sizes}"/>
				<input type="hidden" name="ati" id="ati" value="${ati}"/>
			</div>
		</div>
        
        <div id="toolbar" class="btn-group">
			<button id="btn_add" type="button" class="btn btn-w-m btn-primary"
				 onclick="btnaddpd()" ><!-- onclick="addStart()" -->
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>生成考勤统计
			</button>
			<div  id="tsText" style="color:#00f"></div>
		</div>
		<div >每次审核时要记住先点击所需修改数据的行哦！</div>
		<div class="modal inmodal" id="window_update" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">考勤统计修改表</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="updateholidays" name="updateholidays"
							novalidate="novalidate">
							<input type="hidden" name="id" id="id" />  
							
							<div class="form-group">
								
								<label class="col-sm-2 control-label" style="margin-left: 130px">考勤状态：</label>
								<div class="col-sm-4">
									<select class="chosen-select" onchange="selectstuatt();" style="width:150px;height:30px;" tabindex="2" name="attenstatus" id="attenstatus">
		                              	<option value=""> -请选择---</option >
		                              	<option value="4">迟到</option >
		                              	<option value="2">请假</option >
		                              	<option value="6">正常</option >
		                              	<option value="0">旷工</option >
		                              	<option value="5">异常</option >
		                              	<option value="1">旷工半天</option >
		                              	<option value="3">请假半天</option >
		                            </select>
								</div>
							</div>
							
							<div class="form-group" >
								<label class="col-sm-3 control-label">简叙：</label>
								<div class="col-sm-6">
									<textarea class="form-control" placeholder="备注：" style="width:290px;" 
										style="resize: none" id="remark"></textarea>
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
								<button type="button" onclick="updateStart()"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
        <div class="ibox-content">
          	<!-- table代码就这些，用js构建表格 -->
			<table id="tb_auth"></table>
        </div>
        
    </div> 
     
     <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<!-- Toastr script -->
    <script src="${pageContext.request.contextPath}/js/plugins/toastr/toastr.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
    <script>
	//日期范围限制
	var start = {
		elem : '#start',
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
	var end = {
		elem : '#end',
		format : 'YYYY/MM/DD hh:mm:ss',
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
 <script type="text/javascript">
	    
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	attenstatus:{
                    	required:true
                    },
	        		late:{
	        			
	        			number:true
	        		},
	        		absent:{
	        			
	        			number:true
	        		},
	        		leave:{
	        			
	        			number:true
	        		}
	        		
	            },
	            messages: {
	            	attenstatus:{
		                	required:icon + "请选择好状态"
		                },
	                late:{
		        			number:icon + "请输入合理的迟到时间"
	        			},
	        		absent:{
	        			    number:icon + "请输入合理的旷工时间"
	        			},
	        		leave:{
	        				number:icon + "请输入合理的请假时间"
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