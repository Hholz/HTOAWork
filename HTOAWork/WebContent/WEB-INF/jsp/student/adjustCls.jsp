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
$(function(){
	init();
});
function init(){
	//alert(555);
		var url="${pageContext.request.contextPath}/conn/AllFall";	
	$.post(
		url,
		{
			id:1
		},
		getFall,
		"json"
	);	
}

//界别班级
	function getFall(data){
		var td="<option value=''>----</option>";
	$.each(data,function(i){
		td+="<option value='"+data[i].id+"'>";
		td+=data[i].level;
		td+="</option>";
	});
	$("#txt_search_fall").html(td);//显示在div中
	clsChange();
	}
function clsChange(){
	var id = $("#txt_search_fall").val();
	var url="${pageContext.request.contextPath}/conn/AllCls/"+id;	
	$.post(
		url,
		{
			id:id
		},
		getCls,
		"json"
	);	
}
function getCls(data){
	var td = "<option value=''>----</option>";
	$.each(data,function(i){
		td+="<option value='"+data[i].id+"'>";
		td+=data[i].classname;
		td+="</option>";
	});
	$("#toclsid").html(td);//显示在div中
}
</script>
</head>
<body>
	<div class="panel panel-info">
       <div class="panel-heading">
          	 <h2>申请分班</h2>
          	 <div class="alert alert-info">
           		<form id="holiday_form" method="post" action="${pageContext.request.contextPath}/student/adjustCls/add">
				<div class="form-horizontal m-t">
					<div class="row">
						<div class="col-sm-4">
							<div class="form-group">
		                        <label class="col-sm-3 control-label">理由：</label>
		                        <div class="col-sm-7">
		                        	<input id="reason" name="reason" required="" type="text"  class="form-control">
		                        </div>
		                    </div>
						</div>
						<div class="col-sm-6">
							<label class="control-label col-sm-2">更换班级</label>
							<div class="col-sm-5">
								<select class="form-control" required="" name="txt_search_fall" id="txt_search_fall" onchange="clsChange()" class="form-control">
                                     <option>----</option>
                                </select>
							</div>
							<div class="col-sm-5">
								<select class="form-control" required="" name="toclsid" id="toclsid" class="form-control">
								
                                </select>    
							</div>
						</div>
						<div class="col-sm-2">
							<button type="submit" id="save"  class="btn btn-primary">提交请假申请</button>
						</div>
					</div>
				</div>
				</form>
	      	 </div>
       </div>
       <div class="panel-body ">
       		<div class="panel-body well">
              	<c:forEach items="${ajcList}" var="ajc">
	                    <div class="alert alert-info">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-12">
				                                                                                理由：${ajc.reason }&nbsp;&nbsp; &nbsp;&nbsp;  
				                                                                        申请时间：${ajc.createTime }&nbsp;&nbsp; &nbsp;&nbsp;   
				                                                                    调换到班级：${ajc.tocls.classname }&nbsp;&nbsp;    &nbsp;&nbsp;                                         
				                   </div>
				                   <div class="col-sm-12">
				                     <br>                                                
				                   </div>
				                   <div class="col-sm-12">
			                   			<span <c:if test="${ajc.acStatus==0}">class="badge badge-info"</c:if>>
			                   				等待自己班主任处理
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==1}"> class="badge badge-info" </c:if>>
			                   				班主任通过
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==2}"> class="badge badge-info" </c:if>>
			                   				班主任拒绝
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==3}"> class="badge badge-info" </c:if>>
			                   				另外班主任通过
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${ajc.acStatus==4}"> class="badge badge-info" </c:if>>
			                   				另外班主任拒绝
			                   			</span>
				                   </div>
				                </div>
				           </div>
	                    </div>                     
	            </c:forEach>
	         	<div class="clearfix"></div>
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
	            	reason: "required",
	            },
	            messages: {
	            	reason: icon + "请填写申请理由",
	            },
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
	   
	</script>
</body>
</html>