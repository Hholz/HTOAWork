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
    <script>
	    var srmRows = null;
	    //家庭情况行数据
	    var srmdRows = null;
	    function test(id){
	    	$("#stuRMId").val(id);
	    	$("#window_srmd #srmId").val(id);
	    	//让模板明细表刷新
			$('#tb_stusrmd').bootstrapTable('refresh');
	    }
		$(function () {
			test(0);
			init2();
			//激活提示
			$("[data-toggle='tooltip']").tooltip();
			
			//**********************************************************答辩模板****************
			$("#btn_srm_add").click(function(){
				//生成明细表基础数据
				feedRepSet();
				//清空新增div中的数据
				$("#window_srm #id").val('');
				$("#window_srm #srmName").val('');
				$("#window_srm #clsId").val('');
				$("#window_srm #teacId").val('');
				$("#window_srm #repTime").val('');
				$("#window_srm #srmDescr").val('');
	            
	            $("#btn_srm_save").attr("onclick","addSrm()");
	        });
			//修改按钮事件
			$("#btn_srm_update").click(function(){
				if(srmRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				$("#window_srm #left_div").text('');
				$("#window_srm #right_div").text('');
				//把内容放到更新的列表
				$("#window_srm #id").val(srmRows.id);
				$("#window_srm #srmName").val(srmRows.srmName);
				$("#window_srm #clsId").val(srmRows.clsId);
				$("#window_srm #teacId").val(srmRows.teacId);
				$("#window_srm #repTime").val(srmRows.repTime);
				$("#window_srm #srmDescr").val(srmRows.srmDescr)
	            
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
				
	        });
			
			//表格数据
			$('#tb_stusrm').bootstrapTable({
				url : '${pageContext.request.contextPath}/student/reply/repModelJsonList', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
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
					field : 'number',
					title : '序号',
					formatter: function (value, row, index) { 
						return index+1; 
					} 
				}, {
					field : 'srmName',
					title : '项目名称'
				}, {
					field : 'clsId',
					title : '班级',
					formatter : function(value, row, index) {
						var cls = row.cls;
						if(cls==null){
							return "-";
						}else{
							return cls.classname;
						}
					}
				},{
					field : 'teacId',
					title : '指导老师',
					formatter : function(value, row, index) {
						var teac = row.teac;
						if(teac==null){
							return "-";
						}else{
							return teac.empname;
						}
					}
				},{
					field : 'repTime',
					title : '答辩时间'
				},{
					field : 'status',
					title : '状态',
					formatter : function(value, row, index) {
						var status = row.status;
						var text = "";
						if(status == 1){
							text = "<a onClick='changeStatus("+row.id+")' ><span class='label label-warning'>未答辩</span></a>";
							return text;
						}else if(status == 2){
							text = "<span class='label label-primary'>已答辩</span>";
							return text;
						}
					}
				},{
					field : 'id',
					title : '操作',
					align : 'center',
					formatter : function(value, row, index) {
						var id = row.id;
						return "<a onclick='test("+id+")'><i class='fa fa-file-text-o'>详情</i></a>";
					}
				},]
			});
			
			//**********************************************************模板明细****************
			
			$("#btn_srmd_add").click(function(){
				//清空新增div中的数据
				$("#window_srmd #id").val('');
				//$("#window_srmd #srmId").val('');
				$("#window_srmd #srmdName").val('');
				$("#window_srmd #srmdScore").val('');
	            
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
				$("#window_srmd #srmId").val(srmdRows.srmId);
				$("#window_srmd #srmdName").val(srmdRows.srmdName);
				$("#window_srmd #srmdScore").val(srmdRows.srmdScore);
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
				url : '${pageContext.request.contextPath}/student/reply/repModelDJsonList', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_stuSrmd', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams2,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 10, //每页的记录行数（*）
				pageList : [5, 10, 15, 20, 30], //可供选择的每页的行数（*）
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
					field : 'srmdName',
					title : '打分项名称'
				},{
					field : 'srmdScore',
					title : '打分项总分'
				},]
				
			});
			
		});
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				srmId : $("#stuRMId").val(),
				srmName : $("#txt_search_srmName").val(),
				clsId : $("#txt_search_clsId").val(),
				status : $("#txt_search_status").val()
			};
			return temp;
		}
		function queryParams2(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				srmId : $("#stuRMId").val()
			};
			return temp;
		}
		//**********************************************************模板****************
		//新增学生状态，ajax提交
		function addSrm(){
			//用来判断表单是否验证通过
			if(!validateForm($("#srmForm"))){
				return;
			}
			
			obj = $("#srmForm input[name=srmdName]");
			//var scoredList = new Array();
			var listJson = "";
			listJson = listJson	+"{'srmdNameList':[";
			for(i=0;i<obj.length;i++){
				if(obj[i].value==null||obj[i].value==''){
					return;
				}
				if(i!=obj.length-1){
					listJson = listJson	+"{'srmdName':"+obj[i].id+",'srmdScore':"+obj[i].value+"},";
				}else{
					listJson = listJson	+"{'srmdName':"+obj[i].id+",'srmdScore':"+obj[i].value+"}";
				}
			}
			listJson = listJson	+"]}";
			
			var url = "${pageContext.request.contextPath }/student/reply/addRepModel";
			$.post(
				url,
				{
					srmName:$("#window_srm #srmName").val(),
					clsId:$("#window_srm #clsId").val(),
					teacId:$("#window_srm #teacId").val(),
					repTime:$("#window_srm #repTime").val(),
					srmDescr:$("#window_srm #srmDescr").val(),
					jsonStr:listJson
				},
				addSrmHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_srm").modal('hide');
		}
		function addSrmHandle(data){
			$('#tb_stusrm').bootstrapTable('refresh');
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			
		}
		
		//新增学生状态，ajax提交
		function updateSrm(){
			//用来判断表单是否验证通过
			if(!validateForm($("#srmForm"))){
				return;
			}
			var id = srmRows.id;
			var url = "${pageContext.request.contextPath }/student/reply/repModel/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_srm #id").val(),
					srmName:$("#window_srm #srmName").val(),
					clsId:$("#window_srm #clsId").val(),
					teacId:$("#window_srm #teacId").val(),
					repTime:$("#window_srm #repTime").val(),
					srmDescr:$("#window_srm #srmDescr").val()
				},
				updateSrmHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_srm").modal('hide');
		}
		function updateSrmHandle(data){
			$('#tb_stusrm').bootstrapTable('refresh');
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
			var url = "${pageContext.request.contextPath }/student/reply/repModel/"+id;
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
			}else if(data == -1){
				parent.layer.alert('该模块已使用,无法删除！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stusrm').bootstrapTable('refresh');
		}
		
		
		//**********************************************************模板明细****************
		function addSrmd(){
			if(!validateForm2($("#srmdForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/student/reply/addRepModelD";
			$.post(
				url,
				{
					srmId:$("#window_srmd #srmId").val(),
					srmdName:$("#window_srmd #srmdName").val(),
					srmdScore:$("#window_srmd #srmdScore").val()
				},
				addSrmdHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			$("#window_srmd").modal('hide')
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
		}
		
		function updateSrmd(){
			if(!validateForm2($("#srmdForm"))){
				return;
			}
			var id = srmdRows.id;
			var url = "${pageContext.request.contextPath }/student/reply/repModelD/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_srmd #id").val(),
					srmId:$("#window_srmd #srmId").val(),
					srmdName:$("#window_srmd #srmdName").val(),
					srmdScore:$("#window_srmd #srmdScore").val()
				},
				updateSrmdHandle,
				"text"
			);	
			$("#window_srmd").modal('hide')
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
			var url = "${pageContext.request.contextPath }/student/reply/repModelD/"+id;
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
			}else if(data == -1){
				parent.layer.alert('该项已使用,无法删除！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stusrmd').bootstrapTable('refresh');
		}
		
		
		//*********************************查询里的届别班级联动
		function init2(){
  			var url="${pageContext.request.contextPath}/conn/AllFall";	
			$.post(
				url,
				{
					id:1
				},
				getFall2,
				"json"
			);	
  		}
		//界别班级
  		function getFall2(data){
  			var td="<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].level;
				td+="</option>";
			});
			$("#txt_search_fall").html(td);//显示在div中
			clsChange2();
  		}
		function clsChange2(){
			var id = $("#txt_search_fall").val();
			var url="${pageContext.request.contextPath}/conn/AllCls/"+id;	
			$.post(
				url,
				{
					id:id
				},
				getCls2,
				"json"
			);	
		}
		function getCls2(data){
			var td = "<option value=''>----</option>";
			$.each(data,function(i){
				td+="<option value='"+data[i].id+"'>";
				td+=data[i].classname;
				td+="</option>";
			});
			$("#txt_search_clsId").html(td);//显示在div中
		}
		
		//自动生成
		function feedRepSet(){
			var url = "${pageContext.request.contextPath }/student/StuRepSet/findAll";
			$.post(
				url,
				{},
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
				if(i%2!=1){
					left = left + "<div class='form-group'>";
					left = left + "<label class='col-sm-5 control-label'>"+data[i].name+"</label>";
					left = left + "<div class='col-sm-6'>";
					left = left + "<input name='srmdName'  id='"+data[i].name+"' value="+data[i].score+" type='text'  class='form-control'>";
					left = left + "</div>";
					left = left + "</div>";
				}else{
					right = right + "<div class='form-group'>";
					right = right + "<label class='col-sm-5 control-label'>"+data[i].name+"</label>";
					right = right + "<div class='col-sm-6'>";
					right = right + "<input name='srmdName'  id='"+data[i].name+"' value="+data[i].score+" type='text'  class='form-control'>";
					right = right + "</div>";
					right = right + "</div>";
				}
			});
			$("#left_div").append(left);
			$("#right_div").append(right);//显示在div中
		}
		
		//确认是否更改状态
		function changeStatus(srmId){
			parent.layer.confirm('您确定已经答辩完了吗？', {
			    btn: ['是的','取消'], //按钮
			    shade: false //不显示遮罩
			}, function(){
				changeStatus2(srmId);
			}, function(){
			    
			});
		}
		//改变状态（未答辩--》已答辩）
		function changeStatus2(srmId){
			var url = "${pageContext.request.contextPath }/student/reply/repModel/changeStatus";
			$.post(
				url,
				{
					id:srmId
				},
				function(data){
					if(data>0){
						parent.layer.alert('修改成功');
					}else{
						parent.layer.alert('修改失败');
					}
					//刷新表格
					$('#tb_stusrm').bootstrapTable('refresh');
				},
				"text"
			);	
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
                        <h5>查询</h5>
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
							<div class="form-group" style="margin-top: 15px">
								<div class="row">
									<label class="control-label col-sm-1" for="txt_search_srmName">模板名称</label>       
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_srmName"> 
									</div>
									<label class="control-label col-sm-1">选择班级</label>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_fall" id="txt_search_fall" onchange="clsChange2()" class="form-control">
		                                     <option>----</option>
		                                </select>
									</div>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_clsId" id="txt_search_clsId" class="form-control">
										
		                                </select>    
									</div>
									
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
										<a href="${pageContext.request.contextPath}/student/reply/page"><button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清空</button></a>
									</div>
								</div>
							</div>
						</form>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-8">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>项目答辩模板表</h5>
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
							<button id="btn_srm_add" type="button"  class="btn btn-w-m btn-primary" data-toggle="modal" data-toggle="modal" data-target="#window_srm">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<!-- <button id="btn_srm_update" type="button" class="btn btn-w-m btn-success" data-toggle="modal">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button> -->
							<button id="btn_srm_delete" type="button" class="btn btn-w-m btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stusrm"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>项目答辩模板明细表</h5>
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
							<button id="btn_srmd_add" type="button"  class="btn btn-sm btn-primary" data-toggle="modal" data-target="#window_srmd">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_srmd_update" type="button" class="btn btn-sm btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
							<button id="btn_srmd_delete" type="button" class="btn btn-sm btn-danger">
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stusrmd"></table>
                    </div>
                </div>
            </div>
        </div>
     </div>
     <!--项目答辩模板的弹窗  -->
		<div class="modal inmodal" id="window_srm" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">项目答辩模板</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="srmForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<div class="form-group">
                       			<label class="control-label col-sm-3">选择班级</label>
								<div class="col-sm-8">
									<select class="form-control" name="clsId" id="clsId" class="form-control">
	                                	<c:forEach items="${clsList}" var="cls">
	                                        <option value="${cls.id}">${cls.classname}</option>
	                                    </c:forEach>
	                                </select>
								</div>
							</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">项目名称：</label>
                                <div class="col-sm-8">
                                    <input id="srmName" name="srmName" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                               <label class="col-sm-3 control-label">答辩时间：</label>
                               <div class="col-sm-8">
									<input placeholder="答辩时间" class="form-control  layer-date"
										id="repTime" name="repTime">
								</div>
							</div>
                            <div class="ibox-content">
	                        	<div class="row">
		            				<div class="col-sm-6" id="left_div">
			                            
			                        </div>
			                        <div class="col-sm-6" id="right_div">
			                        
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
						<h4 class="modal-title">项目答辩模板明细</h4>
					</div>
					
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="srmdForm" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">模板名称：</label>
                                <div class="col-sm-8">
                                    <select class="form-control" disabled="disabled" name="srmId" id="srmId" class="form-control">
	                                	<c:forEach items="${srmList}" var="srm">
	                                        <option value="${srm.id}">${srm.srmName}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">打分项目名称：</label>
                                <div class="col-sm-8">
                                    <input id="srmdName" name="srmdName" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">打分项目总分：</label>
                                <div class="col-sm-8">
                                    <input id="srmdScore" name="srmdScore" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="modal-footer">
                                 <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                                 <button type="button" id="btn_srmd_save" onclick="" class="btn btn-primary">保存</button>
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
	            	srmName: "required",
	                srmDescr: "required",
	                clsId: "required",
                	teacId: "required",
	                repTime: "required"
	            },
	            messages: {
	                stuno: icon + "请输入模板名称",
	                stuname: icon + "请输入模板描述",
	                clsId: icon + "请选择一个班级",
                	teacId: icon + "请输入指导老师名字",
	                repTime: icon + "请选择答辩时间"
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		function validateForm2(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	srmId: "required",
	            	srmdName: "required",
            		srmdScore: {
                     	required:true,
 	                	digits:true
                    }
	            },
	            messages: {
	            	srmId: icon + "请选择模板",
	            	srmdName: icon + "请输入打分项名称",
	            	srmdScore: {
                    	required:icon + "请输入打分项分数",
                    	digits: icon + "分数必须是整数"
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
			elem : '#repTime',
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
</body>
</html>