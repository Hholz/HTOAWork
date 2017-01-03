<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script>
	function getStudentJob(stuId,currStuName){
		//标题内容
		$("#currStuName").text(">"+currStuName);
		
		$("#stuJobForm #id").val('');
		$("#stuJobForm #studentId").val(stuId);//给form在点击时设置学生的id，新增的时候用
		$('#stuJobForm #company').val('');
		$("#stuJobForm #addr").val('');
		$("#stuJobForm #saraly").val('');
		$('#stuJobForm #welfare').val('');
		$("#stuJobForm #phone").val('');
		var url = "${pageContext.request.contextPath }/student/stujob/"+stuId;
		$.get(
			url,
			{
				id:stuId
			},
			getStudentJobHandle,
			"json"
		);	
	}
	function getStudentJobHandle(data){
		var stuJob = data;
		if(stuJob!=null){
			$("#stuJobForm #id").val(data.id);
			$("#stuJobForm #studentId").val(data.studentId);
			$('#stuJobForm #company').val(data.company);
			$("#stuJobForm #addr").val(data.addr);
			$("#stuJobForm #saraly").val(data.saraly);
			$('#stuJobForm #welfare').val(data.welfare);
			$("#stuJobForm #phone").val(data.phone);
		}
		//验证表单
		validateForm($("#stuJobForm"));
		
	}
	function EditStudentJob(){
		//用来判断表单是否验证通过
		if(!validateForm($("#stuJobForm"))){
			return;
		}
		var url = "${pageContext.request.contextPath }/student/stujob/addOrUp";
		$.post(
			url,
			{
				id:$("#stuJobForm #id").val(),
				studentId:$("#stuJobForm #studentId").val(),
				company:$('#stuJobForm #company').val(),
				addr:$("#stuJobForm #addr").val(),
				saraly:$("#stuJobForm #saraly").val(),
				welfare:$('#stuJobForm #welfare').val(),
				phone:$("#stuJobForm #phone").val()
			},
			EditStudentJobHandle,
			"text"
		);	
	}
	function EditStudentJobHandle(data){
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if(data>0){
			//这是弹窗的代码
			swal({
	            title: "成功",
	            text: "操作成功",
	            type: "success"
	        });
		}else{
			swal("操作失败", "请检查你输入的数据！", "error");
		}
	}
</script>
</head>
<body>
	<div class="panel panel-info">
       <div class="panel-heading">
          	 <h2>&nbsp;${stuCls.studentfall.level }${stuCls.classname }<span id="currStuName"></span></h2>
          	 <div class="alert alert-info">
	          	班级人数： ${stuCls.count }
	      	 </div>
       </div>
       <div class="panel-body ">
       		<div class="panel-body well">
               <ul class="tag-list" style="padding: 0">
               	 <c:forEach items="${stuList}" var="stu">
               	 	<li><a href="javascript:void(0)" onclick="getStudentJob('${stu.id}','${stu.stuname}')" class="btn btn-success btn-outline"><i class="fa fa-qq"> </i> ${stu.stuname }</a></li>
               	 </c:forEach>
	         	</ul>
	         <div class="clearfix"></div>
           </div>
           <div class="col-sm-12">
           		<form id="stuJobForm">
	           		<input type="hidden" id="id"> 
	           		<input type="hidden" id="studentId"> 
				<div class="form-horizontal m-t">
					<div class="form-group">
                        <label class="col-sm-3 control-label">公司名称：</label>
                        <div class="col-sm-7">
                        	<input id="company" name="company" type="text"  class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">公司地址：</label>
                        <div class="col-sm-7">
                        	<input id="addr" name="addr" type="text"  class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">工资待遇：</label>
                        <div class="col-sm-7">
                        	<input id="saraly" name="saraly" type="text"  class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">福利待遇：</label>
                        <div class="col-sm-7">
                        	<input id="welfare" name="welfare" type="text"  class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">公司电话：</label>
                        <div class="col-sm-7">
                        	<input id="phone" name="phone" type="text"  class="form-control">
                        </div>
                    </div>
                    <div class="modal-footer">
						<button type="button" id="save" onclick="EditStudentJob()" class="btn btn-primary">保存</button>
					</div>
				</div>
				</form>
			</div>
       </div>
   </div>
   <!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script type="text/javascript">
	//表单验证
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
	            	company: "required",
	            	addr: "required",
	            	welfare:{
	            		maxlength:30
	            	},
	            	saraly: {
	                	digits:true
                    },
                    phone:{
                    	digits:true
                    }
	            	
	            },
	            messages: {
	            	company: icon + "请输入公司名称",
	            	addr: icon + "请输入公司地址",
	            	welfare:{
	            		maxlength:icon + "超过字数限制"
	            	},
	            	saraly: {
	                	digits: icon + "薪水必须是整数"
                    },
                    phone: {
                    	digits: icon + "电话号码必须是数字"
                    }
	            },
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
	</script>
</body>
</html>