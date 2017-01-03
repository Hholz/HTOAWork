<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>学生相关设置</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<%-- <jsp:include page="../styleInclude.jsp"></jsp:include> --%>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
	<script type="text/javascript">
		//防止多量添加
		var temp = null;
		//学生状态table选中行的数据
		var stuStaRows = null;
		//户口类别table选中行的数据
		var resRows = null;
		//专业类别table选中行的数据
		var majorRows = null;
		$(function () {
			//激活提示
			$("[data-toggle='tooltip']").tooltip();
			
			$("#btn_ress_add").click(function(){
				parent.layer.alert('内容');
				if(temp==null){
					temp = 1;
					var data = "户口类别：<br><input id='classid' class='btn-xs col-sm-7'  type='text' >&nbsp;<button  type='button' class=' badge badge-primary btn btn-primary btn-xs' >添加</button> "
					$("#li_res_add").append(data);
				}
			});
			
			$("#btn_stuSta_add").click(function(){
				//清空新增div中的数据
				$("#window_stuSta #id").val('');
				$("#window_stuSta #stu").val('');
				$("#window_stuSta #descr").val('');
	            
	            //修改按钮的点击事件
	            $("#btn_stuSta").attr("onclick","addStuSta()");
	        });
			//修改按钮事件
			$("#btn_stuSta_update").click(function(){
				//把内容放到更新的列表
				$("#window_stuSta #id").val(stuStaRows.id);
				$("#window_stuSta #stu").val(stuStaRows.stu);
				$("#window_stuSta #descr").val(stuStaRows.descr);
	            
                
                $("#btn_stuSta").attr("onclick","updateStuSta()");
	        });
			//修改按钮事件
			$("#btn_stuSta_delete").click(function(){
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteStuSta();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_stuSta').bootstrapTable({
				url : '${pageContext.request.contextPath}/sysSet/stuStaListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onClickRow: function(row, $element) {
	                 //$element是当前tr的jquery对象
	                if(stuStaRows != null){
	                	stuStaRows = null;
	                }
	                stuStaRows = row;
	            },//单击row事件
				columns : [ {
					field : 'id',
					title : '编号'
				}, {
					field : 'stu',
					title : '状态名'
				}, {
					field : 'descr',
					title : '描述'
				},]
			});
			
			//********************************************************************************户口类型*************
			$("#btn_res_add").click(function(){
				//清空新增div中的数据
				$("#window_res #id").val('');
				$("#window_res #residence").val('');
				$("#window_res #descr").val('');
	            
	            //修改按钮的点击事件
	            $("#btn_res").attr("onclick","addResidence()");
	        });
			//修改按钮事件
			$("#btn_res_update").click(function(){
				//把内容放到更新的列表
				$("#window_res #id").val(resRows.id);
				$("#window_res #residence").val(resRows.residence);
				$("#window_res #descr").val(resRows.descr);
	            
                
                $("#btn_res").attr("onclick","updateResidence()");
	        });
			//修改按钮事件
			$("#btn_res_delete").click(function(){
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteResidence();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_residence').bootstrapTable({
				//classes:'table-no-bordered',
				url : '${pageContext.request.contextPath}/sysSet/residenceListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_res', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onClickRow: function(row, $element) {
	                 //$element是当前tr的jquery对象
	                if(resRows != null){
	                	resRows = null;
	                }
	                resRows = row;
	            },//单击row事件
				columns : [{
					field : 'id',
					title : '编号'
				}, {
					field : 'residence',
					title : '类型名称'
				}, {
					field : 'descr',
					title : '描述'
				},]
			});
			
			//********************************************************************************专业类别*************
			$("#btn_major_add").click(function(){
				//清空新增div中的数据
				$("#window_major #id").val('');
				$("#window_major #name").val('');
				$("#window_major #majorDesc").val('');
	            
	            //修改按钮的点击事件
	            $("#btn_major").attr("onclick","addMajor()");
	        });
			//修改按钮事件
			$("#btn_major_update").click(function(){
				
				//把内容放到更新的列表
				$("#window_major #id").val(majorRows.id);
				$("#window_major #name").val(majorRows.attence);
				$("#window_major #majorDesc").val(majorRows.remark);
	            
                
                $("#btn_major").attr("onclick","updateMajor()");
	        });
			//修改按钮事件
			$("#btn_major_delete").click(function(){
				parent.layer.confirm('您确定删除该信息吗？', {
				    btn: ['是的','取消'], //按钮
				    shade: false //不显示遮罩
				}, function(){
					deleteMajor();
					//parent.layer.msg('正在删除');
				}, function(){
				    
				});
				
	        });
			
			//表格数据
			$('#tb_major').bootstrapTable({
				url : '${pageContext.request.contextPath}/sysSet/condListJson', //请求后台的URL（*）
				method : 'post', //请求方式（*）
				contentType: "application/x-www-form-urlencoded",
				toolbar : '#toolbar_major', //工具按钮用哪个容器
				cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				pagination : true, //是否显示分页（*）
				queryParams : queryParams,//传递参数（*）
				sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
				pageSize : 5, //每页的记录行数（*）
				pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
				clickToSelect : true, //是否启用点击选中行
				uniqueId : "id", //每一行的唯一标识，一般为主键列
				singleSelect: true,  //设置为单选
				onClickRow: function(row, $element) {
	                 //$element是当前tr的jquery对象
	                if(majorRows != null){
	                	majorRows = null;
	                }
	                majorRows = row;
	            },//单击row事件
				columns : [ {
					field : 'id',
					title : '编号'
				}, {
					field : 'attence',
					title : '情况'
				}, {
					field : 'remark',
					title : '描述'
				},]
			});
		});
		 
		function queryParams(params) { //配置参数
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
			};
			return temp;
		}
		//新增学生状态，ajax提交
		function addStuSta(){
			var url = "${pageContext.request.contextPath }/sysSet/addStuSta";
			$.post(
				url,
				{
					stu:$("#window_stuSta #stu").val(),
					descr:$("#window_stuSta #descr").val()
				},
				addStuStaHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
		}
		function addStuStaHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_student').bootstrapTable('refresh');
		}
		
		//新增学生状态，ajax提交
		function updateStuSta(){
			var id = stuStaRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/stuSta/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_stuSta #id").val(),
					stu:$("#window_stuSta #stu").val(),
					descr:$("#window_stuSta #descr").val()
				},
				updateStuStaHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
			//刷新数据
			$('#tb_stuSta').bootstrapTable('refresh');
		}
		function updateStuStaHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_stuSta').bootstrapTable('refresh');
		}
		
		function deleteStuSta(){
			var id = stuStaRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/stuSta/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:stuStaRows.id,//从选中的rows中获取id
					status:0
				},
				deleteStuStaHandle,
				"text"
			);	
		}
		function deleteStuStaHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_stuSta').bootstrapTable('refresh');
		}
		
		
		//*****************************************************************户口类型******************************
		 
		//新增学生状态，ajax提交
		function addResidence(){
			var url = "${pageContext.request.contextPath }/sysSet/addResidence";
			$.post(
				url,
				{
					stu:$("#window_res #residence").val(),
					descr:$("#window_res #descr").val()
				},
				addResidenceHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
		}
		function addResidenceHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_residence').bootstrapTable('refresh');
		}
		
		//新增学生状态，ajax提交
		function updateResidence(){
			var id = resRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/residence/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_res #id").val(),
					residence:$("#window_res #residence").val(),
					descr:$("#window_res #descr").val()
				},
				updateResidenceHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
			//刷新数据
			$('#tb_residence').bootstrapTable('refresh');
		}
		function updateResidenceHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_residence').bootstrapTable('refresh');
		}
		
		function deleteResidence(){
			var id = resRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/residence/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:resRows.id  //从选中的rows中获取id
				},
				deleteResidenceHandle,
				"text"
			);	
		}
		function deleteResidenceHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_residence').bootstrapTable('refresh');
		}
		
		//*****************************************************************专业类别******************************
		 
		//新增专业类别，ajax提交
		function addMajor(){
			
			if(!validateForm($("#addcond"))){
				return;
			}
			
			var url = "${pageContext.request.contextPath }/sysSet/addcond";
			$.post(
				url,
				{
					attence:$("#window_major #name").val(),
					remark:$("#window_major #majorDesc").val()
				},
				addMajorHandle,
				"text"
			);	
		}
		function addMajorHandle(data){
			if(data>0){
				parent.layer.alert('添加成功！');
			}else{
				parent.layer.alert('添加失败！');
			}
			//刷新数据
			$('#tb_major').bootstrapTable('refresh');
		}
		
		//修改专业类别，ajax提交
		function updateMajor(){
			var id = majorRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/cond/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					id:$("#window_major #id").val(),
					attence:$("#window_major #name").val(),
					remark:$("#window_major #majorDesc").val()
				},
				updateMajorHandle,
				"text"
			);	
			//用来关闭新增窗口***********
			//$("#addStudent").modal('hide')
			//刷新数据
			$('#tb_residence').bootstrapTable('refresh');
		}
		function updateMajorHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				//这是弹窗的代码
				parent.layer.alert('修改成功！');
			}else{
				parent.layer.alert('修改失败！');
			}
			//刷新表格
			$('#tb_major').bootstrapTable('refresh');
		}
		
		function deleteMajor(){
			var id = majorRows.id;
			var url = "${pageContext.request.contextPath }/sysSet/cond/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
					id:majorRows.id , //从选中的rows中获取id
					status:0
				},
				deleteMajorHandle,
				"text"
			);	
		}
		function deleteMajorHandle(data){
			//从后台返回出来的int类型数据，用来判断是否新增成功
			if(data>0){
				parent.layer.alert('删除成功！');
			}else{
				parent.layer.alert('删除失败！');
			}
			//刷新表格
			$('#tb_major').bootstrapTable('refresh');
		}
	</script>
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">
        <div class="row">
			<!-- <div class="col-sm-3">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>分组列表</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link" id="btn_ress_add" data-toggle="tooltip" data-placement="top" title="" data-original-title="添加类别" href="javascript:void(0);">
                                <i class="fa fa-check-circle-o"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="修改类别">
                                <i class="fa fa-exchange"></i>
                            </a>
                            <a class="close-link" data-toggle="tooltip" data-placement="bottom" title="" data-original-title="删除类别">
                                <i class="fa fa-scissors"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content no-padding">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <span class="badge badge-primary">农村户口</span>农村户口
                            </li>
                            <li class="list-group-item ">
                                <span class="badge badge-info">城市户口</span>城市户口
                            </li>
                            <li class="list-group-item" id="li_res_add" >
                                                                                                                 
                            </li>
                            <li class="list-group-item" id="li_res_update">
                                  	 原名：<br><input id="classid" class="btn-xs col-sm-7"  type="text" />&nbsp;
                                  	<br><br>&nbsp;修改成：<br><input id="classid" class="btn-xs col-sm-7"  type="text" >&nbsp;
                                  	<button  type="button" class=" badge badge-warning btn btn-warning btn-xs" >修改</button>                                                          
                            </li>
                            <li class="list-group-item" id="li_res_delete">
                                  	 类型名称：<br><input id="classid" class="btn-xs col-sm-7"  type="text" />&nbsp;
                                  	<button  type="button" class=" badge badge-danger btn btn-danger btn-xs" >删除</button>                                                          
                            </li>
                        </ul>
                    </div>
                </div>
            </div> -->
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学生考勤情况类别</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
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
                        <!-- <div class="btn-group" id="toolbar_major">
                                <button class="btn btn-white glyphicon glyphicon-plus" id="btn_major_add" type="button" data-toggle="modal" data-target="#window_major"></button>
                                <button class="btn btn-white glyphicon glyphicon-pencil" id="btn_major_update" type="button" data-toggle="modal" data-target="#window_major"></button>
                                <button class="btn btn-white glyphicon glyphicon glyphicon-minus" id="btn_major_delete" type="button"></button>
                        </div> -->
                        <table class="table" id="tb_major"></table>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>户口类别</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
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
                        <!-- <div class="btn-group" id="toolbar_res">
                                <button class="btn btn-white glyphicon glyphicon-plus" id="btn_res_add" type="button" data-toggle="modal" data-target="#window_res"></button>
                                <button class="btn btn-white glyphicon glyphicon-pencil" id="btn_res_update" type="button" data-toggle="modal" data-target="#window_res"></button>
                                <button class="btn btn-white glyphicon glyphicon glyphicon-minus" id="btn_res_delete" type="button"></button>
                        </div> -->
                        <table class="table" id="tb_residence"></table>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>学生状态</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="typography.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                   <div class="ibox-content">
                   		<!-- <div class="btn-group" id="toolbar">
                                <button class="btn btn-white glyphicon glyphicon-plus" id="btn_stuSta_add" type="button" data-toggle="modal" data-target="#window_stuSta"></button>
                                <button class="btn btn-white glyphicon glyphicon-pencil" id="btn_stuSta_update" type="button" data-toggle="modal" data-target="#window_stuSta"></button>
                                <button class="btn btn-white glyphicon glyphicon glyphicon-minus" id="btn_stuSta_delete" type="button"></button>
                        </div> -->
                        <table class="table" id="tb_stuSta"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 弹窗div -->
        <div class="modal inmodal animated fadeInRight" id="window_stuSta" tabindex="-1" role="dialog" aria-hidden="true" >
             <div class="modal-dialog modal-sm">
                 <div class="modal-content">
                     <div class="modal-header">
                         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                         <h4 class="modal-title">学生状态编辑</h4>
                     </div>
                     <div class="modal-body">
                         <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate">
                         	<input id="id" type="hidden">
                         	<label>状态名：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="stu" type="text"  class="form-control">
                                </div>
                            </div>
                            <label>描述：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="descr" type="text"  class="form-control">
                                </div>
                            </div>
                        </form>
                     </div>
                     <div class="modal-footer">
                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                         <button type="button" id="btn_stuSta" onclick data-dismiss="modal"  class="btn btn-primary">保存</button>
                     </div>
                 </div>
             </div>
         </div>
    <!-- 弹窗div -->
        <div class="modal inmodal animated rollIn" id="window_res" tabindex="-1" role="dialog" aria-hidden="true" >
             <div class="modal-dialog modal-sm">
                 <div class="modal-content">
                     <div class="modal-header">
                         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                         <h4 class="modal-title">户口类别编辑</h4>
                     </div>
                     <div class="modal-body">
                         <form class="form-horizontal m-t" id="commentForm" novalidate="novalidate">
                         	<input id="id" type="hidden">
                         	<label>户口类别：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="residence" type="text"  class="form-control">
                                </div>
                            </div>
                            <label>类别描述：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="descr" type="text"  class="form-control">
                                </div>
                            </div>
                        </form>
                     </div>
                     <div class="modal-footer">
                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                         <button type="button" id="btn_res" onclick data-dismiss="modal"  class="btn btn-primary">保存</button>
                     </div>
                 </div>
             </div>
         </div>
         
       <!-- 弹窗div -->
        <div class="modal inmodal animated rollIn" id="window_major" tabindex="-1" role="dialog" aria-hidden="true" >
             <div class="modal-dialog modal-sm">
                 <div class="modal-content">
                     <div class="modal-header">
                         <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                         <h4 class="modal-title">考勤情况编辑</h4>
                     </div>
                     <div class="modal-body">
                         <form class="form-horizontal m-t" id="addcond" novalidate="novalidate">
                         	<input id="id" type="hidden">
                         	<label>考勤类别：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="name" name="name" type="text"  class="form-control">
                                </div>
                            </div>
                            <label>类别描述：</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <input id="majorDesc" type="text"  class="form-control">
                                </div>
                            </div>
                        </form>
                     </div>
                     <div class="modal-footer">
                         <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                         <button type="button" id="btn_major" onclick data-dismiss="modal"  class="btn btn-primary">保存</button>
                     </div>
                 </div>
             </div>
         </div>

    <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript">
		
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata){
			var icon = "<i class='glyphicon glyphicon-minus-sign'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	name :{
                    	required:true
                    }
                    
	            },
	            messages: {
	            	name :{
                    	required:icon + "请输入状态名称"
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