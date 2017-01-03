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
    <link rel="shortcut icon" href="favicon.ico"> 
    <link href="${pageContext.request.contextPath}/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/plugins/steps/jquery.steps.css" rel="stylesheet">
<script type="text/javascript">
    //全局变量，用来保存选中行的数据
	    var rows = null;
        var rowList = new Array();
	    $(function () {
	    	initCls();//初始化届别班级
	    	$("[data-toggle='tooltip']").tooltip();
			
			$('#btn_srs_add').click(function () {
				$("#score_form #stuId").prop('disabled', false);
				$("#score_form #srmId").prop('disabled', false);
				$("#score_form #teacName").prop('disabled', false);
				$("#score_form #srsDate").prop('disabled', false);
				$("#btn_srsd_save").attr("onclick","addScored()");
			});
			$('#btn_srs_update').click(function () {
				if(srsRows==null){
					parent.layer.alert('请选你要修改的数据！');
					return;
				}
				//清空div里的内容
				$("#window_score #left_div").text('');
				$("#window_score #right_div").text('');
				$("#score_form #stuId").val('');
				$("#score_form #srmId").val('');
				$("#score_form #teacName").val('');
				$("#score_form #srsDate").val('');
				//设置按钮为更新事件
				$("#btn_srsd_save").attr("onclick","updateScored()");
				var url = "${pageContext.request.contextPath }/student/replyScore/getStuRepScoreD/"+srsRows.id;
				$.get(
					url,
					{
					},
					function(data){
						var left="";
						var right="";
						$.each(data,function(i){
							if(i%2!=1){
								left = left + "<div class='form-group'>";
								left = left + "<label class='col-sm-5 control-label'>"+data[i].stuReplyModelD.srmdName+"</label>";
								left = left + "<div class='col-sm-6'>";
								left = left + "<input name='srmdName'  id='"+data[i].id+"' placeholder='总分"+data[i].stuReplyModelD.srmdScore+"' type='text'  value='"+data[i].srsdScore+"' class='form-control'>";
								left = left + "</div>";
								left = left + "</div>";
							}else{
								right = right + "<div class='form-group'>";
								right = right + "<label class='col-sm-5 control-label'>"+data[i].stuReplyModelD.srmdName+"</label>";
								right = right + "<div class='col-sm-6'>";
								right = right + "<input name='srmdName'  id='"+data[i].id+"' placeholder='总分"+data[i].stuReplyModelD.srmdScore+"' type='text' value='"+data[i].srsdScore+"'  class='form-control'>";
								right = right + "</div>";
								right = right + "</div>";
							}
						});
						$("#left_div").append(left);
						$("#right_div").append(right);//显示在div中
						$("#window_score").modal('show');
						
						//设置数据
						$("#score_form #srmId").val(data[0].srs.srmId);
						$("#score_form #stuId").val(data[0].srs.stuId);
						$("#score_form #teacName").val(data[0].srs.teacName);
						$("#score_form #srsDate").val(data[0].srs.srsDate);
					},
					"json"
				);	
				$("#score_form #stuId").prop('disabled', true);
				$("#score_form #srmId").prop('disabled', true);
				$("#score_form #teacName").prop('disabled', true);
				$("#score_form #srsDate").prop('disabled', true);
				
				//刷新数据
				$('#tb_stuRepScore').bootstrapTable('refresh');
	        });
			$("#btn_query").click(function(){
				$('#tb_stuRepScore').bootstrapTable('refresh');
	        });
		});
	</script>
<script>
	var srsRows = null;
	$(function () {
		//表格数据
		$('#tb_stuRepScore').bootstrapTable({
			url : '${pageContext.request.contextPath}/student/replyScore/repScoreJsonList', //请求后台的URL（*）
			method : 'post', //请求方式（*）
			contentType: "application/x-www-form-urlencoded",
			toolbar : '#toolbar_stuSrm', //工具按钮用哪个容器
			cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination : true, //是否显示分页（*）
			queryParams : queryParams,//传递参数（*）
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pageSize : 20, //每页的记录行数（*）
			pageList : [ 10, 15, 20, 30 ], //可供选择的每页的行数（*）
			clickToSelect : true, //是否启用点击选中行
			uniqueId : "id", //每一行的唯一标识，一般为主键列
			singleSelect: true,  //设置为单选
			onCheck: function(row) {
	             //$element是当前tr的jquery对象
	            if(srsRows != null){
	            	srsRows = null;
	            }
	            srsRows = row;
	        },
	        onUncheck: function(row) {
	            if(srsRows != null){
	            	srsRows = null;
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
			},{
				field : 'srmName',
				title : '答辩模板',
				formatter : function(value, row, index) {
					var stuReplyModel = row.stuReplyModel;
					if(stuReplyModel==null){
						return "-";
					}else{
						return "<b>"+stuReplyModel.srmName+"</b>";
					}
				}
			},{
				field : 'claName',
				title : '班级',
				formatter : function(value, row, index) {
					var stuReplyModel = row.stuReplyModel;
					if(stuReplyModel==null){
						return "-";
					}else{
						return stuReplyModel.cls.classname;
					}
				}
			}, {
				field : 'mainTeac',
				title : '指导老师',
				formatter : function(value, row, index) {
					var teac = row.stuReplyModel.teac;
					if(teac==null){
						return "-";
					}else{
						return teac.empname;
					}
				}
			}, {
				field : 'student.stuname',
				title : '学生'
			}, {
				field : 'teacName',
				title : '评委老师'
			}, {
				field : 'srsDate',
				title : '答辩日期'
			},{
				field : 'srsScore',
				align : 'center',
				title : '得分',
				formatter : function(value, row, index) {
					var srsScore = row.srsScore;
					var allScore = row.allScore;
					var score = Math.floor((srsScore/allScore)*100);
					var color = "";
					if(score<60){
						color = "label label-danger";
					}else if(score>=60&&score<80){
						color = "label label-warning";
					}else if(score>=80&&score<100){
						color = "label label-info";
					}else{
						color = "label label-primary";
					}
					return "<a onmouseover='getStuRepScoreD("+row.id+")' ><span class='"+color+"'>"+srsScore+"分("+score+"%)</span></a>";
				}
			},]
		});
	});
	function queryParams(params) { //配置参数
		var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
			limit : params.limit, //页面大小
			offset : params.offset, //页码
			clsId : $("#txt_search_clsId").val(),
			stuId : $("#txt_search_stuId").val(),
			srsDate : $("#txt_search_srsDate").val(),
			mbName : $("#txt_search_srmName").val(),
			empName	: $("#txt_search_empName").val(),
			pass :$("#txt_search_pass").val()
		};
		return temp;
	}
	function getStuRepScoreD(srsId){
		$('#stuRepScoreDDiv').text("");//清空div里的内容
		var url = "${pageContext.request.contextPath }/student/replyScore/getStuRepScoreD/"+srsId;
		$.get(
			url,
			{
				id:srsId
			},
			getStuRepScoreDHandle,
			"json"
		);	
	}
	function getStuRepScoreDHandle(data){
		var srsdList = data;
		var divData = "";
		var hasScore = 0;//总得分
		var allScore = 0;//总分
		for(var i=0;i<srsdList.length;i++){
			hasScore = hasScore + srsdList[i].srsdScore;
			allScore = allScore + srsdList[i].stuReplyModelD.srmdScore;
			var srmdName = srsdList[i].stuReplyModelD.srmdName;
			var score = srsdList[i].srsdScore+"/"+srsdList[i].stuReplyModelD.srmdScore+"分";
			var barLength = Math.floor(((srsdList[i].srsdScore)/(srsdList[i].stuReplyModelD.srmdScore))*100);
			//Math.floor()数字取整
			//bar的颜色
			var barstyle = "";
			if(barLength<60){
				barstyle = "progress-bar progress-bar-danger";
			}else if(barLength>=60&&barLength<80){
				barstyle = "progress-bar progress-bar-warning";
			}else if(barLength>=80&&barLength<100){
				barstyle = "progress-bar progress-bar-info";
			}else{
				barstyle = "progress-bar";
			}
			divData = divData + "<div><span>"+srmdName+"</span>";
			divData = divData + "<small class='pull-right'>"+score+"</small></div>";
			divData = divData +"<div class='progress progress-small'><div style='width:"+barLength+"%' class='"+barstyle+"'></div></div>";
		}
		var barstyle2 ="";
		var barLength2 = Math.floor((hasScore/allScore)*100);
		if(barLength2<60){
			barstyle2 = "progress-bar progress-bar-danger";
		}else if(barLength2>=60&&barLength2<80){
			barstyle2 = "progress-bar progress-bar-warning";
		}else if(barLength2>=80&&barLength2<100){
			barstyle2 = "progress-bar progress-bar-info";
		}else{
			barstyle2 = "progress-bar";
		}
		divData = divData +"<div><h5>总得分("+barLength2+"%)<small class='pull-right'>"+hasScore+"/"+allScore+"分</small></h5></div>";
		divData = divData +"<div class='progress progress-striped active'>";
		divData = divData +"<div style='width: "+barLength2+"%' class='"+barstyle2+"'> </div></div>";
		//往div里添加得分明细内容
		$('#stuRepScoreDDiv').append(divData);
	}
</script>
<script type="text/javascript">
	function addScore(){
		$("#score_form #stuName").val('');
		$("#score_form #srmId").val('');
		$("#score_form #teacName").val('');
		$("#score_form #srsDate").val('');
	}
	//定义一个对象用来封装，传到后台
	function scoreObj(srmdId,score){
		this.srmdId = srmdId;
		this.score =score;
	}
	//把数据保存到后台数据库
	function addScored(){
		if(!validateForm2($("#score_form"))){
			return;
		}
		obj = $("#score_form input[name=srmdName]");
		//var scoredList = new Array();
		var listJson = "";
		listJson = listJson	+"{'scoredList':[";
		for(i=0;i<obj.length;i++){
			if(obj[i].value==null||obj[i].value==''){
				return;
			}
			if(i!=obj.length-1){
				listJson = listJson	+"{'srmdId':"+obj[i].id+",'score':"+obj[i].value+"},";
			}else{
				listJson = listJson	+"{'srmdId':"+obj[i].id+",'score':"+obj[i].value+"}";
			}
		}
		listJson = listJson	+"]}";
		//alert(listJson);
		$("#window_score").modal('hide');
		var url = "${pageContext.request.contextPath }/student/replyScore/add";
		$.post(
			url,
			{
				srmId:$("#score_form #srmId").val(),
				stuId:$("#score_form #stuId").val(),
				teacName:$("#score_form #teacName").val(),
				srsDate:$("#score_form #srsDate").val(),
				scoredListJson:listJson
			},
			addScoredDel,
			"text"
		);	
	}
	function addScoredDel(data){
		swal({
            title: "成功",
            text: "你已经完成添加操作",
            type: "success"
        });
		$('#tb_stuRepScore').bootstrapTable('refresh');
	}
	
	//把数据保存到后台数据库
	function updateScored(){
		if(!validateForm2($("#score_form"))){
			return;
		}
		obj = $("#score_form input[name=srmdName]");
		//var scoredList = new Array();
		var listJson = "";
		listJson = listJson	+"{'scoredList':[";
		for(i=0;i<obj.length;i++){
			if(obj[i].value==null||obj[i].value==''){
				return;
			}
			if(i!=obj.length-1){
				listJson = listJson	+"{'srsdId':"+obj[i].id+",'score':"+obj[i].value+"},";
			}else{
				listJson = listJson	+"{'srsdId':"+obj[i].id+",'score':"+obj[i].value+"}";
			}
		}
		listJson = listJson	+"]}";
		//alert(listJson);
		$("#window_score").modal('hide');
		var url = "${pageContext.request.contextPath }/student/replyScore/update";
		$.post(
			url,
			{
				scoredListJson:listJson
			},
			function(data){
				swal({
                    title: "成功",
                    text: "你已经完成添加操作",
                    type: "success"
                });
				$('#tb_stuRepScore').bootstrapTable('refresh');
			},
			"text"
		);	
	}
	//通过答辩模板来获取要答辩的学生
	function srmChange(){
		start_reply();//生成打分项
		var id = $("#window_score #srmId").val();
		var url="${pageContext.request.contextPath}/conn/stuBySrmId/"+id;	
		$.post(
			url,
			{
				id:id
			},
			getStu,
			"json"
		);	
	}
	function getStu(data){
		var td = "<option value=''>----</option>";
		$.each(data,function(i){
			td+="<option value='"+data[i].id+"'>";
			td+=data[i].stuname;
			td+="</option>";
		});
		$("#window_score #stuId").html(td);//显示在div中
	}
	
	//通过答辩模板id去后台取数据
	function start_reply(){
		var srmId = $("#score_form #srmId").val();
		var url = "${pageContext.request.contextPath }/student/reply/repModelDList/"+srmId;
		$.post(
			url,
			{
				srmId:$("#score_form #srmId").val()
			},
			del,
			"json"
		);	
	}
	function del(data){
		//清空div里的内容
		$("#window_score #left_div").text('');
		$("#window_score #right_div").text('');
		var left="";
		var right="";
		$.each(data,function(i){
			if(i%2!=1){
				left = left + "<div class='form-group'>";
				left = left + "<label class='col-sm-5 control-label'>"+data[i].srmdName+"</label>";
				left = left + "<div class='col-sm-6'>";
				left = left + "<input name='srmdName'  id='"+data[i].id+"' placeholder='总分"+data[i].srmdScore+"' type='text'  class='form-control'>";
				left = left + "</div>";
				left = left + "</div>";
			}else{
				right = right + "<div class='form-group'>";
				right = right + "<label class='col-sm-5 control-label'>"+data[i].srmdName+"</label>";
				right = right + "<div class='col-sm-6'>";
				right = right + "<input name='srmdName'  id='"+data[i].id+"' placeholder='总分"+data[i].srmdScore+"' type='text'  class='form-control'>";
				right = right + "</div>";
				right = right + "</div>";
			}
		});
		$("#left_div").append(left);
		$("#right_div").append(right);//显示在div中
	}
	//*********************************查询里的届别班级联动
	function initCls(){
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
		$("#txt_search_clsId").html(td);//显示在div中
	}
	
	function studentChange(){
		var id = $("#txt_search_clsId").val();
		var url="${pageContext.request.contextPath}/conn/stuByClsId/"+id;	
		$.post(
			url,
			{},
			function(data){
				var td = "<option value=''>----</option>";
				$.each(data,function(i){
					td+="<option value='"+data[i].id+"'>";
					td+=data[i].stuname;
					td+="</option>";
				});
				$("#txt_search_stuId").html(td);//显示在div中
			},
			"json"
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
                        <h5>查询条件</h5>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a  class="dropdown-toggle" data-toggle="dropdown" href="tabs_panels.html#">
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
                        <form id="formSearch" class="form-horizontal">
							<div class="form-group" style="margin-top: 15px">
								<div class="row">
									<label class="control-label col-sm-1" for="txt_search_srmName">模板名称</label>       
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_srmName"> 
									</div>
									<label class="control-label col-sm-1">选择班级</label>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_fall" id="txt_search_fall" onchange="clsChange()" class="form-control">
		                                     <option>----</option>
		                                </select>
									</div>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_clsId" id="txt_search_clsId" onchange="studentChange()" class="form-control">
										
		                                </select>    
									</div>
									<label class="control-label col-sm-1">选择学生</label>
									<div class="col-sm-2">
										<select class="form-control" name="txt_search_clsId" id="txt_search_stuId" class="form-control">
										
		                                </select>    
									</div>
								</div>
								<div class="row">
									<label class="control-label col-sm-1" for="txt_search_empName">指导老师</label>       
									<div class="col-sm-2">
										<input type="text" class="form-control" id="txt_search_empName"> 
									</div>
									<label class="control-label col-sm-1">得分情况</label>
									<div class="col-sm-1">
										<select class="form-control" name="txt_search_pass" id="txt_search_pass" onchange="clsChange2()" class="form-control">
		                                     <option value="0">----</option>
		                                     <option value="1" >不及格</option>
		                                     <option value="2">及格</option>
		                                     <option value="3" >优秀</option>
		                                     <option value="4">满分</option>
		                                </select>
									</div>
									<label class="col-sm-1 control-label">答辩时间</label>
	                                <div class="col-sm-2">
										<input placeholder="精确到月" class="form-control layer-date" id="txt_search_srsDate" name="txt_search_srsDate">
									</div>
									<div class="col-sm-3">
										<button type="button" style="margin-left: 50px" id="btn_query" class="btn btn-primary">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询</button>
										<a href="${pageContext.request.contextPath}/student/replyScore/page"><button type="button" style="margin-left: 50px" id="btn_clean" class="btn btn-primary">
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
                        <h5>项目答辩得分表</h5>
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
                    	<div id="toolbar_stuRepScore" class="btn-group">
							<button id="btn_srs_add" onclick="addScore()" type="button" class="btn btn-w-m btn-primary" data-toggle="modal"  data-target="#window_score">
								<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
							</button>
							<button id="btn_srs_update" type="button" class="btn btn-w-m btn-success">
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
							</button>
						</div>
                      	<!-- table代码就这些，用js构建表格 -->
						<table id="tb_stuRepScore"></table>
					</div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>得分明细</h5>
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
                    	<!-- 放得分明细进度条 -->
	                    <div id="stuRepScoreDDiv">
	                    	
	                    </div>
	                </div>
                 </div>
             </div>
         </div>
         <!-- 准备开始录入 -->
         <div class="modal inmodal" id="window_score" tabindex="-1" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content animated bounceInRight">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">关闭</span>
						</button>
						<h4 class="modal-title">录入答辩成绩</h4>
					</div>
                    <div class="ibox-content">
                        <form class="form-horizontal m-t" id="score_form" novalidate="novalidate" >
                        	<input id="id" type="hidden">
                        	<div class="form-group">
                                <label class="col-sm-3 control-label">选择模板：</label>
                                <div class="col-sm-8">
                                    <select class="form-control"  name="srmId" id="srmId" class="form-control" onchange="srmChange()">
	                                	<c:forEach items="${srmList}" var="srm">
	                                        <option value="${srm.id}">${srm.srmName}<br>(${srm.cls.classname})</option>
	                                    </c:forEach>
	                                </select>
                                </div>
                            </div>
                        	<div class="form-group">
                        		<label class="control-label col-sm-3">学生姓名：</label>
								<div class="col-sm-8">
									<select class="form-control" name="stuId" id="stuId"  class="form-control">
	                                    
	                                </select>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">评委老师：</label>
                                <div class="col-sm-8">
                                    <input id="teacName" name="teacName" type="text"  class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">答辩日期：</label>
                                <div class="col-sm-8">
									<input placeholder="答辩日期" class="form-control layer-date"
										id="srsDate" name="srsDate">
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
                                 <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                                 <button type="button" id="btn_srsd_save"  class="btn btn-primary" >完成录入</button>
                            </div>
                        </form>
                    </div>
				</div>
			</div>
		</div>
     <!-- 自定义js -->
    <script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#srsDate',
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
	            	stuName: "required",
	            	teacName: "required",
	            	srmId: "required",
	            	stuId: "required",
	            	srsDate: "required"
	            },
	            messages: {
	            	stuName: icon + "请选择学生",
	            	teacName: icon + "请输入老师姓名",
	            	srmId: icon + "请选择答辩模板",
	            	stuId: icon + "请选择一位学生",
	            	srsDate: icon + "请选择答辩日期"
	            },
	            submitHandler:function(form) {
	        		alert("表单验证成功，不提交"+validator.form());
	        	}
	        });
	        //返回表单验证是否通过，true false
	        return validator.form();
		}
		//***input控件要设置name属性***
		function validateForm2(formdata){
			var icon = "<i class='fa fa-times-circle'></i> ";
	        var validator = formdata.validate({
	            rules: {
	            	 srmName: {
	                    	required:true,
		                	digits:true
	                 }
	            },
	            messages: {
	            	srmName: {
                    	required:icon + "请输入分数",
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
			elem : '#txt_search_srsDate',
			format : 'YYYY-MM',
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