<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	var rows = null;
	$(function () {
		
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//条件查询click事件注册
		$("#query").click(function() {
			$("#tb_stusrm").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			$("#majorid").val(0);
			$("#fallid").val(0);
			$("#term_Name").val('');
		});
	})
	
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#tb_stusrm').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/Shouldcharge/Json', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
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
	            },
	            columns : [ {
					checkbox : true,
				}, {
					field : 'id',
					title : '编号'
				},{
					field : 'fallid',
					title : '届别',
					formatter : function(value, row, index) {
						var fall = row.fall;
						if(fall==null){
							return "-";
						}else{
							return fall.level;
						}
					}
				},{
					field : 'majorName',
					title : '专业',
					formatter : function(value, row, index) {
						var major = row.major;
						if(major==null){
							return "-";
						}else{
							return major.majorName;
						}
					}
				}, {
					field : 'termName',
					title : '学期',
					formatter : function(value, row, index) {
						var termname = row.termname;
						if(termname==null){
							return "-";
						}else{
							return termname.termName;
						}
					}
				}, {
					field : 'sum',
					title : '总金额',
					formatter : function(value, row, index) {
						var sum = row.sum;
						if(sum==null){
							return "-";
						}else{
							return sum;
						}
					}
				},{
					field : 'id',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						return "<a onclick='select()'><i class='fa fa-file-text-o'>生成应缴费</i></a>";
					}
				},]
	            
			});

		};
		//得到查询的参数
		queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				//***这里的参数传到后台，用来进行分页处理*************************
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				fallid : $("#fallid").val(),
				term_Name : $("#term_Name").val(),
				edudeptid : $("#majorid").val()
			};
			return temp;
		};
		return oTableInit;
	}
	
	function select(){
			if(rows != null ){
				alert("fallid"+rows.fallid+"majorid="+rows.major.id+"  sum="+rows.sum+"   termid="+rows.termname.id);
				var url = "${pageContext.request.contextPath }/finance/Shouldcharge/addTuition";
				$.post(
					url,
					{
						_method:'POST',//改成PUT提交
						id: rows.id,
						fallid: rows.fallid,
						edudeptid: rows.major.id,
						sum: rows.sum,
						term: rows.termname.id
					},
					selectHandle,
					"text"
				);	
			}else{
				parent.layer.alert('请选择需要生产缴费记录的班级！');
			}
			
			//用来关闭新增窗口***********
		}
		
		function selectHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('生成收费记录成功！');
			}else if(data<0){
				parent.layer.alert('该专业还没有学生，可能还未开班！');
			}else{
				parent.layer.alert('未生成，服务器没有响应！');
			}
			//刷新表格
			$('#tb_stusrm').bootstrapTable('refresh');
		}
</script>
</head>
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
                       <form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">届别：</label>
								<div class="col-sm-2">
									<select class="form-control" name="fallid" id="fallid">
										<option value="0">---不选择---</option>
										<c:forEach items="${fallList}" var="f">
											<option value="${f.id }">${f.level}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">专业：</label>
								<div class="col-sm-2">
									<select class="form-control" name="majorid" id="majorid">
										<option value="0">---不选择---</option>
										<c:forEach items="${majorlist }" var="e">
											<option value="${e.id }">${e.majorName}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">学期：</label>
								<div class="col-sm-2">
									<select class="form-control" name="term_Name" id="term_Name">
										<option value="">---不选择---</option>
										<option value=第一学期>第一学期</option>
				          				<option value=第二学期>第二学期</option>
				          				<option value=第三学期>第三学期</option>
				          				<option value=第四学期>第四学期</option>
				          				<option value=第五学期>第五学期</option>
				          				<option value=第六学期>第六学期</option>
									</select>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query"><span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear"><span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除</button>
								</div>
							</div>
						</form>
                   </div>
               </div>
           </div>
     </div>
           <div class="ibox float-e-margins">
                 <div class="ibox-title">
                     <h5>待生成学费数据</h5>
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
           			<table id="tb_stusrm"></table>
				</div>
          </div>
      </div>
</body>
</html>