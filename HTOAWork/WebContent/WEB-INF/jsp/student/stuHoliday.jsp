<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
</head>
<body>
	<div class="panel panel-info">
       <div class="panel-heading">
          	 <h2>填写请假单</h2>
          	 <div class="alert alert-info">
           		<form id="holiday_form" method="post" action="${pageContext.request.contextPath}/stuHoliday/add">
				<div class="form-horizontal m-t">
					<div class="row">
						<div class="col-sm-5">
							<div class="form-group">
		                        <label class="col-sm-3 control-label">请假原因：</label>
		                        <div class="col-sm-7">
		                        	<input id="reason" required="" name="reason" type="text"  class="form-control">
		                        </div>
		                    </div>
						</div>
						<div class="col-sm-5">
							<div class="form-group">
		                        <label class="col-sm-3 control-label">时间：</label>
		                        <div class="col-sm-4">
									<input placeholder="开始时间" required="" class="form-control  layer-date"
										id="stdate" name="stdate">
								</div>
		                        <div class="col-sm-4">
									<input placeholder="结束时间" required="" class="form-control  layer-date"
										id="endate" name="endate">
								</div>
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
              <c:forEach items="${holList}" var="hol">
	                    <div class="alert alert-info">
	                 	   <div class="row">
	                            <div class="col-sm-12">
				                   <div class="col-sm-12">
				                                                                                理由：${hol.reason }&nbsp;&nbsp; &nbsp;&nbsp;  
				                                                                        开始时间：${hol.stdate }&nbsp;&nbsp; &nbsp;&nbsp;
				                                                                         结束时间：${hol.endate }&nbsp;&nbsp; &nbsp;&nbsp;      
				                   </div>
				                   <div class="col-sm-12">
				                     <br>                                                
				                   </div>
				                   <div class="col-sm-12">
			                   			<span <c:if test="${hol.status==0}">class="badge badge-info"</c:if>>
			                   				等待班主任处理
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${hol.status==1}"> class="badge badge-info" </c:if>>
			                   				班主任通过
			                   			</span>
			                   			&nbsp;&nbsp;
			                   			<span <c:if test="${hol.status==2}"> class="badge badge-info" </c:if>>
			                   				班主任拒绝
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
	<script>
		//日期范围限制
		var start = {
			elem : '#stdate',
			format : 'YYYY/MM/DD hh:mm:ss',
			min : laydate.now(), //设定最小日期为当前日期
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
	<script>
		//日期范围限制
		var start = {
			elem : '#endate',
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