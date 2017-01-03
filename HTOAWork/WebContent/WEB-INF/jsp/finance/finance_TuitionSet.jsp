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
				$("#window_srm #fallid").val('');
				$("#window_srm #term").val('');
				$("#window_srm #edudeptid").val('');
				$("#window_srm #remark").val('');
				
				$("#window_srm #fallid").attr("disabled", false);
				$("#window_srm #term").attr("disabled", false);
				$("#window_srm #edudeptid").attr("disabled", false);
				$("#window_srm #remark").attr("disabled", false);
				feeStand();
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
				//select();
				
				$("#window_srm #id").val(srmRows.id);
				$("#window_srm #fallid").val(srmRows.fallid);
				$("#window_srm #term").val(srmRows.term);
				$("#window_srm #edudeptid").val(srmRows.edudeptid);
				$("#window_srm #remark").val(srmRows.remark);
				
				$("#window_srm #fallid").attr("disabled", true);
				$("#window_srm #term").attr("disabled", true);
				$("#window_srm #edudeptid").attr("disabled", true);
				$("#window_srm #remark").attr("disabled", true);
				feeDetail();
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
			$("#btn_query").click(function(){
				$('#tb_stusrm').bootstrapTable('refresh');
	        });
			
			$("#btn_clean").click(function(){
				$("#formSearch #fallid").val('');
				$("#formSearch #edudeptid").val('');
				$('#tb_stusrm').bootstrapTable('refresh');
	        });
			function feeDetail(){
				
				var url = "${pageContext.request.contextPath }/finance/TuitionsetDetail/findDetail";
				$.post(
					url,
					{
						tuitionid:$("#window_srm #id").val()
					},
					upd,
					"json"
				);	
			}
			function upd(data){
				//清空div里的内容
				$("#window_srm #left_div").text('');
				$("#window_srm #right_div").text('');
				var left="";
				var right="";
				$.each(data,function(i){
					//alert(i);
					if(i%2!=1){
						left = left + "<div class='form-group'>";
						left = left + "<label class='col-sm-5 control-label'>"+data[i].tuitionname+"</label>";
						left = left + "<div class='col-sm-6'>";
						left = left + "<input name='tuitionname'  id='"+data[i].tuitionname+"' value="+data[i].money+" type='text'  class='form-control'>";
						left = left + "</div>";
						left = left + "</div>";
					}else{
						right = right + "<div class='form-group'>";
						right = right + "<label class='col-sm-5 control-label'>"+data[i].tuitionname+"</label>";
						right = right + "<div class='col-sm-6'>";
						right = right + "<input name='tuitionname'  id='"+data[i].tuitionname+"' value="+data[i].money+" type='text'  class='form-control'>";
						right = right + "</div>";
						right = right + "</div>";
					}
				});
				$("#left_div").append(left);
				$("#right_div").append(right);//显示在div中
				//$("#window_srm").modal('show');
			}
			
			//表格数据
			$('#tb_stusrm').bootstrapTable({
				url : '${pageContext.request.contextPath}/finance/TuitionSet/Json', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 7, //每页的记录行数（*）
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
				},{
					field : 'number',
					title : '序号',
					formatter: function (value, row, index) { 
						return index+1; 
					}
				}, {
					field : 'id',
					title : 'ID'
				},{
					field : 'fallid',
					title : '届别',
					align : 'center',
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
					align : 'center',
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
					align : 'center',
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
					align : 'center',
					formatter : function(value, row, index) {
						var sum = row.sum;
						if(sum==null){
							return "-";
						}else{
							return sum;
						}
					}
				}, {
					field : 'status',
					title : '状态',
					align : 'center',
					formatter : function(value, row, index) {
						var statu = row.status;
						if(statu==1){
							return "<span class='label label-danger'><i class='fa fa-times'></i>未生成</span>";
						}
						if(statu==2){
							return "<span class='label label-success'><i class='fa fa-check'></i>已生成</span>";
						}
					}
				},{
					field : 'Id',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='test("+id+")'><i class='fa fa-file-text-o'>缴费明细</i></a>";
					}
				},]
			});
			
			//**********************************************************模板明细****************
			
			$("#btn_srmd_add").click(function(){
				//清空新增div中的数据
				$("#window_srmd #id").val('');
				//$("#window_srmd #tuitionid").val('');
				$("#window_srmd #tuitionid").val(srmRows.id);
				$("#window_srmd #tuitionname").val('');
				$("#window_srmd #money").val('');
	            
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
				$("#window_srmd #tuitionid").val(srmdRows.tuitionid);
				$("#window_srmd #tuitionname").val(srmdRows.tuitionname);
				$("#window_srmd #money").val(srmdRows.money);
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
				url : '${pageContext.request.contextPath}/finance/TuitionSet/DetailJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrmd', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 7, //每页的记录行数（*）
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
				},{
					field : 'number',
					title : '序号',
					formatter: function (value, row, index) { 
						return index+1; 
					}
				}, {
					field : 'id',
					title : 'ID'
				}, {
					field : 'tuitionname',
					title : '收费项'
				},{
					field : 'money',
					title : '金额'
				},]
				
			});
			
		});
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				tuitionid : $("#stuRMId").val(),
				fallid : $("#formSearch #fallid").val(),
			    edudeptid : $("#formSearch #edudeptid").val(),
			    term : $("#formSearch #term").val()
			};
			return temp;
		}
		//**********************************************************模板****************
		//新增学生状态，ajax提交
		function addSrm(){
			/* if(!validateForm($("#addForm"))){
				return;
			} */
			obj = $("#addForm input[name=feeName]");
			//var scoredList = new Array();
			var listJson = "";
			listJson = listJson	+"{'feeNameList':[";
			for(i=0;i<obj.length;i++){
				if(obj[i].value==null||obj[i].value==''){
					return;
				}
				if(i!=obj.length-1){
					listJson = listJson	+"{'feeName':"+obj[i].id+",'money':"+obj[i].value+"},";
				}else{
					listJson = listJson	+"{'feeName':"+obj[i].id+",'money':"+obj[i].value+"}";
				}
			}
			listJson = listJson	+"]}";
			alert(listJson);
			var url = "${pageContext.request.contextPath }/finance/TuitionSet/addTuitionset";
			$.post(
				url,
				{
					fallid:$("#window_srm #fallid").val(),
					term:$("#window_srm #term").val(),
					edudeptid:$("#window_srm #edudeptid").val(),
					remark:$("#window_srm #remark").val(),
					detailJson:listJson
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
			dbj = $("#addForm input[name=tuitionname]");
			//var scoredList = new Array();
			var listJsondetail = "";
			listJsondetail = listJsondetail	+"{'tuitionnameList':[";
			for(i=0;i<dbj.length;i++){
				if(dbj[i].value==null||dbj[i].value==''){
					return;
				}
				if(i!=dbj.length-1){
					listJsondetail = listJsondetail	+"{'tuitionname':"+dbj[i].id+",'money':"+dbj[i].value+"},";
				}else{
					listJsondetail = listJsondetail	+"{'tuitionname':"+dbj[i].id+",'money':"+dbj[i].value+"}";
				}
			}
			listJsondetail = listJsondetail	+"]}";
			//alert(listJsondetail);
			var url = "${pageContext.request.contextPath }/finance/TuitionsetDetail/updateTuitionsetdetail";
			$.post(
				url,
				{
					tuitionid:$("#window_srm #id").val(),
					detailJson:listJsondetail
				},
				updateSrmHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_srm").modal('hide')
			function updateSrmHandle(data){
				//从后台返回出来的int类型数据，用来判断是否新增成功
				if(data>0){
					//这是弹窗的代码
					parent.layer.alert('修改成功！');
				}else{
					parent.layer.alert('修改失败！');
				}
				//刷新数据
				$('#tb_stusrm').bootstrapTable('refresh');
				$('#tb_stusrmd').bootstrapTable('refresh');
			}
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
			var url = "${pageContext.request.contextPath }/finance/TuitionSet/"+id;
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
			var url = "${pageContext.request.contextPath }/finance/TuitionsetDetail/addTuitionsetDetail";
			$.post(
				url,
				{
					tuitionid:$("#window_srmd #tuitionid").val(),
					tuitionname:$("#window_srmd #tuitionname").val(),
					money:$("#window_srmd #money").val()
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
			var url = "${pageContext.request.contextPath }/finance/TuitionSetDetail/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_srmd #id").val(),
					tuitionid:$("#window_srmd #tuitionid").val(),
					tuitionname:$("#window_srmd #tuitionname").val(),
					money:$("#window_srmd #money").val()
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
		
		//二级联动--修改
		function select() {
			//var fallid=$("#window_srm #fallid").val();
			//var edudeptid=$("#window_srm #edudeptid").val();
			var url = "${pageContext.request.contextPath }/finance/TuitionSet/findTermName";
			$.post(url, {
				fall_id : $("#window_srm #fallid").val(),
				major_id: $("#window_srm #edudeptid").val()
			}, initterm, "text");
		}
		function initterm(data) {
			var opt = "";
			var datas = JSON.parse(data);
			var len = datas.rows.length;
			var i;
			if (len > 0) {
				for (i = 0; i < datas.rows.length; i++) {
					opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
							+ datas.rows[i].termName + '</option>';
				}
				$('#window_srm #term').empty();
				$('#window_srm #term').html(opt);
				$('#window_srm #term').trigger("chosen:updated");
				$('#window_srm #term').chosen();
			}else{
				$('#window_srm #term').empty();
			}
		}
		//二级联动查询
		function selectSearch(){
				var url = "${pageContext.request.contextPath }/finance/TuitionSet/findTermName";
				$.post(url, {
					fall_id : $("#formSearch #fallid").val(),
					major_id: $("#formSearch #edudeptid").val()
				}, initSearch, "text");
			}
			function initSearch(data) {
				var opt = "";
				var datas = JSON.parse(data);
				var len = datas.rows.length;
				var i;
				if (len > 0) {
					for (i = 0; i < datas.rows.length; i++) {
						opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
								+ datas.rows[i].termName + '</option>';
					}
					$('#formSearch #term').empty();
					$('#formSearch #term').html(opt);
					$('#formSearch #term').trigger("chosen:updated");
					$('#formSearch #term').chosen();
				}else{
					$('#formSearch #term').empty();
				}
			} 
		function feeStand(){
			
			var url = "${pageContext.request.contextPath }/finance/feeStandard/findAll";
			$.post(
				url,
				{
					id:0
				},
				del,
				"json"
			);	
		}
		function del(data){
			//清空div里的内容
			$("#window_srm #left_div").text('');
			$("#window_srm #right_div").text('');
			var left="";
			var right="";
			$.each(data,function(i){
				//alert(i);
				if(i%2!=1){
					left = left + "<div class='form-group'>";
					left = left + "<label class='col-sm-5 control-label'>"+data[i].feeName+"</label>";
					left = left + "<div class='col-sm-6'>";
					left = left + "<input name='feeName'  id='"+data[i].feeName+"' value="+data[i].money+" type='text'  class='form-control'>";
					left = left + "</div>";
					left = left + "</div>";
				}else{
					right = right + "<div class='form-group'>";
					right = right + "<label class='col-sm-5 control-label'>"+data[i].feeName+"</label>";
					right = right + "<div class='col-sm-6'>";
					right = right + "<input name='feeName'  id='"+data[i].feeName+"' value="+data[i].money+" type='text'  class='form-control'>";
					right = right + "</div>";
					right = right + "</div>";
				}
			});
			$("#left_div").append(left);
			$("#right_div").append(right);//显示在div中
			//$("#window_srm").modal('show');
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
                        <form id="formSearch" class="form-horizontal">
							<div class="form-group" style="margin-top: 15px">
								<label class="control-label col-sm-1" for="fallid">届别</label>
								<div class="col-sm-2">
									<select class="form-control" name="fallid" id="fallid">
		                              	<option value=""> -全部-</option >
		                              	<c:forEach items="${fall}" var="e">
		                                   <option value="${e.id}">${e.level}</option >
		                                </c:forEach> 
		                            </select>
								</div>
								<label class="control-label col-sm-1" for="edudeptid">班级</label>       
								<div class="col-sm-2">
									<select class="form-control" name="edudeptid" id="edudeptid">
		                              	<option value=""> -全部-</option >
		                              	<c:forEach items="${cls}" var="c">
		                                   <option value="${c.id}">${c.majorName}</option >
		                                </c:forEach> 
		                            </select>    
								</div>
								<%-- <label class="control-label col-sm-1" for="term">学期</label>
								<div class="col-sm-2">
									<select  class="form-control" name="term" id="term">
		                              	<option value="">-全部-</option >
		                              	<c:forEach items="${termslist}" var="t">
		                                   <option value="${t.id}">${t.termName}</option >
		                                </c:forEach> 
		                            </select>      
								</div> --%>
								<div class="col-sm-1">
									<button type="button" id="btn_query" class="btn btn-primary">查询</button>
								</div>
								<div class="col-sm-1">
									<button type="button" id="btn_clean" class="btn btn-primary">清除</button>
								</div>
							</div>
						</form>
                        
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-7">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学期缴费设置</h5>
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
             
            <div class="col-sm-5">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>缴费明细</h5>
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
                    	<!-- <div id="toolbar_stuSrmd" class="btn-group">
							<button id="btn_srmd_add" type="button" class="btn btn-w-m btn-primary" data-toggle="modal" data-target="#window_srmd">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_srmd_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_srmd_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div> -->
                      	<!--  table代码就这些，用js构建表格 -->
						<table id="tb_stusrmd"></table>
                    </div>
                </div>
         </div>   
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
						<h4 class="modal-title">学费设置</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="addForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">届别：</label>
                                <div class="col-sm-8">
                                    <!--input id="fallid" name="fallid" type="text"  class="form-control"-->
                                     <select class="form-control" style="width:362px;height:30px;" tabindex="2" name="fallid" id="fallid">
		                              	<option value=""> -请选择届别---</option >
		                              	<c:forEach items="${fall}" var="e">
		                                   <option value="${e.id}">${e.level}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">班级：</label>
                                <div class="col-sm-8">
                                    <!--  input id="edudeptid" name="edudeptid" type="text"  class="form-control"-->
                                    <select class="form-control" style="width:362px;height:30px;" onchange="select();" tabindex="2" name="edudeptid" id="edudeptid">
		                              	<option value=""> -请选择班级---</option >
		                              	<c:forEach items="${cls}" var="c">
		                                   <option value="${c.id}">${c.majorName}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学期：</label>
                                <div class="col-sm-8">
                                    <!-- input id="term" name="term" type="text"  class="form-control"-->
									<select name="term" id="term" class="form-control" style="width:362px;height:30px;" tabindex="2">
		                              	<option value=""> -请选择学期---</option >
		                              	<c:forEach items="${termslist}" var="t">
		                                   <option value="${t.id}">${t.termName}</option >
		                                </c:forEach> 
		                            </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                    <input id="remark" name="remark" type="text" placeholder="选填"  class="form-control">
                                </div>
                            </div>
                            <div class="ibox-content">
	                        	<div class="row">
		            				<div class="col-sm-6" id="left_div">
			                            <!-- <div class="form-group">
			                                <label class="col-sm-5 control-label">保险费：</label>
			                                <div class="col-sm-6">
			                                    <input id="latesalary" name="latesalary" type="text" required class="form-control">
			                                </div>
			                            </div>
			                            <div class="form-group">
			                                <label class="col-sm-5 control-label">教材费：</label>
			                                <div class="col-sm-6">
			                                    <input id="overtimesalary" name="overtimesalary" type="text" required class="form-control">
			                                </div>
			                            </div>
										<div class="form-group">
			                                <label class="col-sm-5 control-label">预定报名费：</label>
			                                <div class="col-sm-6">
			                                    <input id="overtimesalary" name="overtimesalary" type="text" required class="form-control">
			                                </div>
			                            </div> -->
			                        </div>
			                        <div class="col-sm-6" id="right_div">
			                        <!--<div class="form-group">
			                                <label class="col-sm-5 control-label">校服费：</label>
			                                <div class="col-sm-6">
			                                    <input id="leavesalary" name="leavesalary" type="text" required class="form-control">
			                                </div>
			                            </div>
										<div class="form-group">
			                                <label class="col-sm-5 control-label">体验费：</label>
			                                <div class="col-sm-6">
			                                    <input id="sickleavesalary" name="sickleavesalary" type="text" required class="form-control">
			                                </div>
			                            </div>
			                            <div class="form-group">
			                                <label class="col-sm-5 control-label">住宿费：</label>
			                                <div class="col-sm-6">
			                                    <input id="sickleavesalary" name="sickleavesalary" type="text" required class="form-control">
			                                </div>
			                            </div> -->
			                         </div>
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
						<h4 class="modal-title">学费明细</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<input id="tuitionid" type="hidden">
                            <!-- <div class="form-group">
                                <label class="col-sm-3 control-label">学费id：</label>
                                 <div class="col-sm-8">
                                    <input id="tuitionid" type="text"  class="form-control">
                                </div>
                            </div> -->
                            <div class="form-group">
                                <label class="col-sm-3 control-label">收费项：</label>
                                <div class="col-sm-8">
                                    <input id="tuitionname" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">金额：</label>
                                <div class="col-sm-8">
                                    <input id="money" type="text"  class="form-control">
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
	            	fallid: "required",
	            	edudeptid: "required",
	            	term: "required"
					//remark:"required"	            	
	            },
	            messages: {
	            	fallid: icon + "请选择届别",
	            	edudeptid: icon + "请选择班级",
	            	term: icon + "请选择学期"
	                //remark:icon + "请输入备注"
	            },
	            submitHandler:function(form) {
	        		//alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
	</script>
	<script type="text/javascript">

        $(function () {
        	 $('#tb_stusrmd').bootstrapTable('hideColumn', 'id');
            $('#tb_stusrm').bootstrapTable('hideColumn', 'id');
        }); 

    </script>
</body>
</html>