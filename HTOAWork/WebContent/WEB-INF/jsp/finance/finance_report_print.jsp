<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>发起反馈</title>
<jsp:include page="../styleInclude.jsp"></jsp:include>
<script
	src="${pageContext.request.contextPath}/js/plugins/echarts/echarts-all.js"></script>
<script
	src="${pageContext.request.contextPath}/js/demo/macarons.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Export/bootstrap-table-export.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/Export/tableExport.js"></script>
<script type="text/javascript">
	//全局变量，读取当前行的数据
	var rows = null;
	$(function() {
		//1.初始化Table
		var oTable = new TableInit();
		oTable.Init();

		//2.初始化Button的点击事件
		var oButtonInit = new ButtonInit();
		oButtonInit.Init();
		getPie();
		getBarOut();
		getBarIn();
		//条件查询click事件注册
		$("#query").click(function() {
			getPie();
			getBarOut();
			getBarIn();
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
		$("#clear").click(function() {
			empId: $('#formSearch #empid').val('');
			startTime: $('#formSearch #start').val('');
			endTime: $('#formSearch #end').val('');
		getPie();
		getBarOut();
		getBarIn();
			$("#feedbackstart_Tab").bootstrapTable('refresh');
		});
	});
	var getPie = function(){
		var url = "${pageContext.request.contextPath}/finance/financeIncomeAndExpense/getSum";
		$.post(
			url,
			{
				_method : 'PUT',
				typeId : $('#formSearch #empid').val(),
				systime : $('#formSearch #start').val(),
				createTime : $('#formSearch #end').val()
			},
			resetPie,
			"text"	
		);
		var pieChart = echarts.init(document.getElementById("echarts-pie-chart"),e_macarons);
		pieChart.showLoading();
		function resetPie(data){
			var datas = JSON.parse(data);
			//饼状图
			if(datas.rows.length>0 && datas.rows.length!=2){
				$('#pienodatas').css("display","none");
			}else{
				pieChart.hideLoading();    //隐藏加载动画
				$('#pienodatas').css("display","block");
				return;
			}
			pieChart.hideLoading();    //隐藏加载动画
			var pieoption = {
				title : {
					text : '宏图总收入/总支出(单位/元)',
					//x : 'center'
				},
				tooltip : {
					trigger : 'item',
					formatter : "{a} <br/>{b} : {c} ({d}%)"
				},
				legend : {
					//orient : 'vertical',
					x : 'center',
					data : [ '总收入', '总支出']
				},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
				calculable : true,
				series : [ {
					name : '财务',
					type : 'pie',
					radius : '75%',
					center : [ '50%', '58%' ],
					data : [ {
						value : datas.rows[0].allout,
						name : '总支出'
					}, {
						value : datas.rows[0].allin,
						name : '总收入'
					} ]
				} ]
			};
			pieChart.setOption(pieoption);
			$(window).resize(pieChart.resize);
		}
	}
	var getBarOut = function(){
		var url = "${pageContext.request.contextPath}/finance/financeIncomeAndExpense/getSumOut";
		$.post(
			url,
			{
				_method : 'PUT',
				typeId : $('#formSearch #empid').val(),
				systime : $('#formSearch #start').val(),
				createTime : $('#formSearch #end').val()
			},
			resetBarOut,
			"text"	
		);
		//条形图
		var barChart = echarts.init(document.getElementById("echarts-bar-chart"),e_macarons);
		barChart.showLoading({
            text: '读取数据中...' //loading，是在读取数据的时候显示
        });    //数据加载完之前先显示一段简单的loading动画
        var types=[];    //类别数组（实际用来盛放X轴坐标值）
        var values=[];    //销量数组（实际用来盛放Y坐标值）
		function resetBarOut(data){
			//alert(data);
			var datas = JSON.parse(data);
			if(datas.rows.length>0){
				for(var i = 0; i<datas.rows.length; i++){
					types.push(datas.rows[i].typeOutName);
					values.push(datas.rows[i].typeOut);
				}
			} 
			barChart.hideLoading();    //隐藏加载动画
			barChart.setOption({        //加载数据图表
				title : {
			        text: '宏图各类支出(单位/元)'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:[ '支出']
			    },
			    grid:{
		            height:'74%'
		        },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: false},
			            dataView : {show: false, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value',
			            boundaryGap : [0, 0.01]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
			            data : types
			        }
			    ],
			    series : [
			        {
			            name:'支出',
			            type:'bar',
			            data:values
			        }
			    ]
            });
			//barChart.setOption(baroption);
			window.onresize = barChart.resize;
		}
	}
	var getBarIn = function(){
		var url2 = "${pageContext.request.contextPath}/finance/financeIncomeAndExpense/getSumIn";
		$.post(
			url2,
			{
				_method : 'PUT',
				typeId : $('#formSearch #empid').val(),
				systime : $('#formSearch #start').val(),
				createTime : $('#formSearch #end').val()
			},
			resetBarIn,
			"text"	
		);
		//条形图
		var barChart2 = echarts.init(document.getElementById("echarts-bar2-chart"),e_macarons);
		barChart2.showLoading();    //数据加载完之前先显示一段简单的loading动画
        var types2=[];    //类别数组（实际用来盛放X轴坐标值）
        var values2=[];    //销量数组（实际用来盛放Y坐标值）
		function resetBarIn(data){
			//alert(data);
			var datas = JSON.parse(data);
			if(datas.rows.length>0){
				for(var i = 0; i<datas.rows.length; i++){
					types2.push(datas.rows[i].typeInName);
					values2.push(datas.rows[i].typeIn);
				}
			} 
			barChart2.hideLoading();    //隐藏加载动画
			barChart2.setOption({        //加载数据图表
				title : {
			        text: '宏图各类收入(单位/元)'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:[ '收入']
			    },
			    grid:{
		            height:'74%'
		            /* width : '80%' */
		        },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: false},
			            dataZoom : {
			                show : false,
			                title : {
			                    dataZoom : '区域缩放',
			                    dataZoomReset : '区域缩放后退'
			                }
			            },
			            dataView : {show: false, readOnly: true},
			            magicType: {
			                show : true,
			                title : {
			                    line : '动态类型切换-折线图',
			                    bar : '动态类型切换-柱形图'
			                },
			                type : ['line', 'bar']
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value',
			            boundaryGap : [0, 0.01]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
			            data : types2
			        }
			    ],
			    series : [
			        {
			            name:'收入',
			            type:'bar',
			            //barMinHeight : 20,
			            barGap : '40%',
			            barCategoryGap : '40%',
			            data:values2
			        }
			    ]
            });
			//barChart.setOption(baroption);
			//window.onresize = barChart2.resize;
			$(window).resize(barChart2.resize);
		}
	}
	var TableInit = function() {
		var oTableInit = new Object();
		//初始化Table
		oTableInit.Init = function() {
			$('#feedbackstart_Tab').bootstrapTable({
		url : '${pageContext.request.contextPath}/finance/financeIncomeAndExpense/balancelist', //请求后台的URL（*）
		method : 'post', //请求方式（*）
		contentType : "application/x-www-form-urlencoded",
		//toolbar : '#toolbar', //工具按钮用哪个容器
		showExport : true, //是否显示导出
		//exportDataType: "selected", //basic', 'all', 'selected'.好像默认basic不写也罢
		striped : true, //是否显示行间隔色
		cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
		pagination : true, //是否显示分页（*）
		sortable : false, //是否启用排序
		sortOrder : "asc", //排序方式
		queryParams : oTableInit.queryParams,//传递参数（*）
		queryParamsType : "limit",
		sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
		pageNumber : 1, //初始化加载第一页，默认第一页
		pageSize : 7, //每页的记录行数（*）
		pageList : [ 10, 25, 50, 100, 'ALL'], //可供选择的每页的行数（*
		search : false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
		strictSearch : false,
		showColumns : true, //是否显示所有的列
		showRefresh : true, //是否显示刷新按钮
		minimumCountColumns : 2, //最少允许的列数
		clickToSelect : true, //是否启用点击选中行
		height : 450, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
		uniqueId : "id", //每一行的唯一标识，一般为主键列
		showToggle : true, //是否显示详细视图和列表视图的切换按钮
		cardView : false, //是否显示详细视图
		detailView : false, //是否显示父子表
		singleSelect : true, //设置为单选
		onClickRow : function(row, $element) {//selected
			//$element是当前tr的jquery对象
			if (rows != null) {
				rows = null;
			}
			rows = row;
		},//单击row事件
		onUncheck : function(row) {
			if (rows != null) {
				rows = null;
			}
		},
		columns : [
				{
					field : 'id',
					title : '编号',
					align : 'center',
					visible : false
				},
				{
					field : 'typeName',
					align : 'center',
					title : '收支名称',
					formatter : function(value, row, index) {
						var type = row.type;
						if (type.typename==null) {
							return '-';
						} else {
							return type.typename;
						}
					}
				},
				{
					field : 'inorout',
					title : '收支类别',
					align : 'center',
					formatter : function(value, row,
							index) {
						if (row.type.identifying == 1) {
							return '<button type="button" class="btn btn-xs btn-danger">支出</button>';
						} else {
							return '<button type="button" class="btn btn-xs btn-primary">收入</button>';
						}
					}
				},
				{
					field : 'money',
					align : 'center',
					title : '发生金额',
					formatter : function(value, row,
							index) {
						if (row.type.identifying == 1) {
							return '<button type="button" class="btn btn-xs btn-danger">'+row.money+'元</button>';
						} else {
							return '<button type="button" class="btn btn-xs btn-primary">'+row.money+'元</button>';
						}
					}
				},
				{
					field : 'emp',
					title : '申报人',
					align : 'center',
					formatter : function(value, row,
							index) {
						if (row.reportEmp == null) {
							return "无";
						} else {
							return '<button type="button" class="btn btn-xs btn-warning">'+row.reportEmp.empname+'</button>';
						}
					}
				},
				{
					field : 'systime',
					align : 'center',
					title : '时间',
					formatter : function(value, row,
							index) {
						if (value) {
							return value.substring(0,
									19);
						}
					}
				},
				{
					field : 'financeRemark',
					align : 'center',
					title : '备注',
					formatter : function(value, row,
							index) {
						if (row.financeRemark == null
								|| row.financeRemark == "") {
							return "无";
						} else {
							return '<button type="button" class="btn btn-xs btn-info">'+row.financeRemark+'</button>';
						}
					}
				} ]
			});
		};
		//得到查询的参数
		oTableInit.queryParams = function(params) {
			var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
				limit : params.limit, //页面大小
				offset : params.offset, //页码
				typeId : $('#formSearch #empid').val(),
				systime : $('#formSearch #start').val(),
				createTime : $('#formSearch #end').val()
			};
			return temp;
		};
		return oTableInit;
	}
	var ButtonInit = function() {
		var oInit = new Object();
		var postdata = {};

		oInit.Init = function() {
			//初始化页面上面的按钮事件
		};
		return oInit;
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>查询条件</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i></a>
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">类型：</label>
								<div class="col-sm-2">
									<select class="form-control" name="empid" id="empid">
										<option value=>----不选择----</option>
										<c:forEach items="${allType }" var="e">
											<option value="${e.id }">${e.typename}</option>
										</c:forEach>
									</select>
								</div>
								<label class="col-sm-1 control-label">时间：</label>
								<div class="col-sm-3">
									<input placeholder="开始日期" class="form-control layer-date"
										id="start" name="start">
								</div>
								<div class="col-sm-3">
									<input placeholder="结束日期" class="form-control layer-date"
										id="end" name="end">
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-primary" id="query">
										<span class="glyphicon glyphicon-zoom-in" aria-hidden="true"></span>查询
									</button>
								</div>
								<div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- table代码就这些，用js构建表格 -->
		<table id="feedbackstart_Tab"></table>
		<div class="row" id="seminar_detail">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>柱状图(支出)</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<div class="echarts" id="echarts-bar-chart"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>柱状图(收入)</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<div class="echarts" id="echarts-bar2-chart"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>饼状图</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<h1 align="center" style="display: none" id="pienodatas">暂无数据！</h1>
						<div class="echarts" id="echarts-pie-chart"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	 
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
	<script>
		//日期范围限制
		var start = {
			elem : '#start',
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
		var end = {
			elem : '#end',
			format : 'YYYY/MM/DD hh:mm:ss',
			//min : laydate.now(),
			max : '2099-06-16 23:59:59',
			istime : true,
			istoday : false,
			choose : function(datas) {
				start.max = datas; //结束日选好后，重置开始日的最大日期
			}
		};
		laydate(start);
		laydate(end);
	</script>
</body>
</html>