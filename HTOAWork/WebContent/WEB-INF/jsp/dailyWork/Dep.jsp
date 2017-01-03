<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>
	<jsp:include page="../styleInclude.jsp"></jsp:include>
    <link href="${pageContext.request.contextPath}/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/plugins/treeview/bootstrap-treeview.js"></script>
    
    <script type="text/javascript">
	    $(function () {
	    	var json=${deptree };
	    	$('#treeview11').treeview({
	            data: json,
	            onNodeSelected: function (event, node) {
	                $("#id").val(node.id);
	                $("#parentid").val(node.parent);
	                $("#depname").val(node.text);
	                $("#chairman").val(node.chairman);
	                $("#depRemark").val(node.depRemark);
	            }
	        });
	    });
	    //添加部门
	    function addDep() {
	    	if(!validateForm($("#depForm"))){
				return;
			}
	    	var url = "${pageContext.request.contextPath }/dailyWork/dep/add";
			$.post(
				url,
				{	
					_method:'GET',
					parentid:$("#parentid").val(),
					depname:$("#depname").val(),
					chairman:$("#chairman").val(),
					depRemark:$("#depRemark").val(),
				},
				addDepHandle,
				"text"
			);	
	    }
	    function addDepHandle(data){
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
			}else{
				swal("添加失败", "请检查你输入的数据！", "error");
			}
			window.setTimeout(function(){ 
				location.replace(location.href);
			},1500); 
		}
	    //修改部门
	    function updateDep() {
	    	var id =$("#id").val();
	    	if(id==null || id=='' || id==0){
				parent.layer.alert('请选你要修改的数据！');
				return;
			}
	    	if(!validateForm($("#depForm"))){
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/dep/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
					parentid:$("#parentid").val(),
					depname:$("#depname").val(),
					chairman:$("#chairman").val(),
					depRemark:$("#depRemark").val(),
				},
				updateDepHandle,
				"text"
			);
	    }
	    function updateDepHandle(data){
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成修改操作",
                    type: "success"
                });
			}else if(data=-1){
				swal("修改失败", "父部门不能修改为该部门的子部门！", "error");
			}else{
				swal("修改失败", "请检查你输入的数据！", "error");
			}
			window.setTimeout(function(){ 
				location.replace(location.href);
			},1500); 
		}
	    //删除部门
	    function delDep() {
	    	var id = $("#id").val();
	    	if(id==null || id=='' || id==0){
				parent.layer.alert('请选你要删除的数据！');
				return;
			}
			var url = "${pageContext.request.contextPath }/dailyWork/dep/"+id;
			$.post(
				url,
				{
					_method:'DELETE',//改成PUT提交
				},
				delDepHandle,
				"text"
			);	
	    }
	    function delDepHandle(data){
			if(data>0){
				swal({
                    title: "成功",
                    text: "你已经完成删除操作",
                    type: "success"
                });
			}else if(data==-1){
				swal("删除失败", "该部门有子部门", "error");
			}else if(data==-2){
				swal("删除失败", "该部门有员工", "error");
			}
			window.setTimeout(function(){ 
				location.replace(location.href);
			},1500); 
		}
	    function findemp(){
	    	var id = $("#parentid").val();
			var url = "${pageContext.request.contextPath }/dailyWork/findemp/"+id;
			$.post(
				url,
				{
					_method:'PUT',//改成PUT提交
				},
				getemp,
				"text"
			);
	    }
	    function getemp(data){
	    	var opt = "";
			var datas = JSON.parse(data);
			var len = datas.rows.length;
			var i;
			if (len > 0) {
				for (i = 0; i<datas.rows.length; i++) {
					opt += '<option value="'+datas.rows[i].id+'">'
							+ datas.rows[i].empname + '</option>';
				}
			}
			$('#chairman').empty();
			$('#chairman').html(opt);
			$('#chairman').trigger("chosen:updated"); 
			$('#chairman').chosen();
	    }
    </script>
</head>
<body class="gray-bg">
	 <div class="row wrapper wrapper-content animated fadeInRight">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>部门管理</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="col-sm-6">
                        <div id="treeview11" class="test"></div>
                    </div>
                    <div class="col-sm-6">
                    	<form class="form-horizontal m-t" id="depForm" novalidate="novalidate">
                            <div class="form-group">
                            	<input id="id" type="hidden">
                                <label class="col-sm-3 control-label">部门名称：</label>
                                <div class="col-sm-8">
                                    <input id="depname" name="depname" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                           		<label class="col-sm-3 control-label">父部门：</label>
	                            <div class="col-sm-8">
	                                <select id="parentid" name="parentid" class="form-control" onchange="findemp();">
	                                	<option value="0">无</option>
	                                    <c:forEach items="${deplist}" var="dep">
	                                    	<option value="${dep.id}">${dep.depname}</option>
	                                    </c:forEach>
	                                </select>
	                            </div>
                        	</div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">部门负责人：</label>
                                <div class="col-sm-8">
                                    <select id="chairman" name="chairman" class="form-control">
                                    	<option value="无">无</option>
	                                    <c:forEach items="${emplist}" var="emp">
	                                    	<option value="${emp.id}">${emp.empname}</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">说明：</label>
                                <div class="col-sm-8">
                                    <input id="depRemark" type="text" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <button type="button" onclick="addDep()" class="btn btn-primary">新增</button>
                                <button type="button" onclick="updateDep()" class="btn btn-primary">修改</button>
                            	<button type="button" onclick="delDep()" class="btn btn-white">删除</button>
                            </div>
                        </form>
                    </div>
                    <div class="clearfix"></div>
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
	            	depname :{
                    	required:true
                    }
	            },
	            messages: {
	            	depname :{
                    	required:icon + "请填写部门名称"
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