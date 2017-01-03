<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<title>BootStrap Table使用</title>

<jsp:include page="../styleInclude.jsp"></jsp:include>
<script type="text/javascript">
	//点击弹意见反馈窗
	function myoption(){
		selectSort();
		$('#window_option').modal('show');
	}
	//查询意见类别
	function selectSort(){
		var url = "${pageContext.request.contextPath }/student/studentOption/findAllClass";
		$.post(
			url,
			del,
			"json"
		);	
	}
	function del(data){
		//清空div里的内容
		//<label class="control-label"><input type="radio"  name="sortname"  value="" >&nbsp;&nbsp;生活</label>
		$("#window_option #left_div").text('');
		
		var left="";
		$.each(data,function(i){
			//alert(i);
			left = left + "&nbsp;&nbsp;&nbsp;&nbsp;<label class='control-label'>";
			left = left + "<input type='radio' name='titleclassid' id='"+data[i].sortname+"' value="+data[i].sortname+" >&nbsp;&nbsp;"+data[i].sortname;
			let =left + "</label>"
		});
		$("#left_div").append(left);
		//$("#window_srm").modal('show');
	}
	
	function addOption(){
		var url = "${pageContext.request.contextPath }/student/studentOption/addoption";
		$.post(
			url,
			{
				title:$("#window_option #title").val(),
				titleclassid:$('#window_option input[name=titleclassid]:checked').val(),
				content:$("#window_option #content").val(),
				ishidename:$("input:checkbox[name='ishidename']:checked").val()
			},
			addData,
			"json"
		);	
		$("#window_option").modal('hide');
	}
	function addData(data){
		//从后台返回出来的int类型数据，用来判断是否新增成功
 		if(data>0){
 			swal({
                 title: "成功",
                 text: "你已经成功提交意见",
                 type: "success"
             });
 			//刷新
 			window.location.href='${pageContext.request.contextPath}/student/studentOption/studentOption'; 
 		}else{
 			swal("提交失败", "请检查你输入的数据！", "error");
 		}
	}
</script>   
</head>
<body>
	<div class="col-sm-12">
         <div class="panel-body">
         	  <div class="alert alert-warning">
             	  <div class="input-group">
                       <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                		我的意见
                       <span class="input-group-btn">
                           <button type="button" data-toggle="modal" class="btn btn-outline btn-success"  onclick="myoption();"><span class="glyphicon glyphicon-hand-up" aria-hidden="true"></span>&nbsp;&nbsp;&nbsp;我要反馈</button>
                       </span>
                  </div>
              </div>
         </div>
     </div>
     <div class="row">
         <div class="col-sm-12">
			             <ul id="myTab" class="nav nav-tabs">
			              <li>
			              	<a class="active" href="${pageContext.request.contextPath }/student/studentOption/studentOption">全部</a>
			               </li>
			              		<!-- 遍历选项卡标题 -->
			               <c:forEach items="${stuSort }" var="sort">
			                  <li><a href="${pageContext.request.contextPath }/student/studentOption/select/${sort.id }">${sort.sortname }</a>
			                  </li>
			                  
			               </c:forEach>
			             </ul>
			             <div id="myTabContent" class="tab-content">
			                 <div class="tab-pane fade in active">
			                     <div class="">
			                      <div class="wrapper wrapper-content animated fadeInUp">
			                <ul class="notes">
			                  <c:forEach items="${optionlist }" var="opt">
			                    <li>
			                        <div>
			                            <small>
				                            <fmt:formatDate type="date" value="${opt.createtime }" dateStyle="full"/>
				                            <fmt:formatDate value="${opt.createtime }" type="time" timeStyle="short"/>
			                            </small>
			                            <h4>${opt.title }</h4>
			                            <p>${opt.content }</p>
			                            <a href="${pageContext.request.contextPath }/student/studentOption/${opt.id}"><i class="fa fa-trash-o "></i></a>
			                        </div>
			                    </li>
			                  </c:forEach>
			                 </ul>
			             </div>
                     </div>
                 </div>
             </div>
         </div>
    </div>
   <!-- 弹窗 -->
	<div class="modal inmodal" id="window_option" tabindex="-1" role="dialog"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">我的意见</h4>
					</div>
					<div class="ibox-content">
						<form class="form-horizontal m-t" id="addForm"
							novalidate="novalidate">
							<div class="form-group">
                                <label class="col-sm-3 control-label">意见标题：</label>
                                <div class="col-sm-8">
                                     <input id="title" type="text" required class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">意见类别：</label>
                                <div class="col-sm-8" id="left_div">
                                	<!--label class="control-label"><input type="radio"  name="sortname"  value="" >&nbsp;&nbsp;生活</label>
                                	<label class="control-label"><input type="radio"  name="sortname"  value="" >&nbsp;&nbsp;学习</label-->
                                </div>
                            </div>
							<div class="form-group">
                                <label class="col-sm-3 control-label">意见内容：</label>
                                <div class="col-sm-8">
                                   <textarea id="content" name="content" placeholder="建议输入120个字以内" class="form-control" required="" aria-required="true"></textarea>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                 <label class="col-sm-3 control-label">是否匿名：</label>
                                 <div class="col-sm-8">
                                 	<input type="checkbox" id="ishidename" name="ishidename" value="0"><i></i>&nbsp;&nbsp;&nbsp;匿名</label>
                                 </div>
                            </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-white" id="btn_cancel" data-dismiss="modal">关闭</button>
								<button type="button" onclick="addOption()" class="btn btn-primary">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		

</body>
</html>