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
<script src="${pageContext.request.contextPath}/js/demo/macarons.js"></script>
<script type="text/javascript">
	$(function(){
		dataInit();
		$("#query").click(function() {
			dataInit();
		});
		$("#clear").click(function() {
			$('#formSearch #syear').val("0");
			$('#formSearch #eyear').val("0");
			dataInit();
		});
	});
	var dataInit = function (){
		var url = "${pageContext.request.contextPath}/finance/year/getyearin";
		$.post(
			url,
			{
				startyear : $('#formSearch #syear').val(),
				endyear : $('#formSearch #eyear').val()
			},
			resetBar,
			"json"
		);
		var worldMapContainer = document.getElementById('echarts-bar-chart');
		//用于使chart自适应高度和宽度,通过窗体高宽计算容器高宽
		var resizeWorldMapContainer = function () {
		    worldMapContainer.style.width = (window.innerWidth-50)+'px';
		    worldMapContainer.style.height = (window.innerHeight-20)+'px';
		};
		//设置容器高宽
		resizeWorldMapContainer();
		var barChart = echarts.init(worldMapContainer,e_macarons);
		barChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
        var moneysin=[];    //类别数组（实际用来盛放X轴坐标值）
        var dates=[];    //销量数组（实际用来盛放Y坐标值）
        var moneysout=[];    //类别数组（实际用来盛放X轴坐标值）
		function resetBar(datas){
			if(datas.rows.length>0){
				for(var i=0;i<datas.rows.length;i++){
					moneysin.push(datas.rows[i].ysumin);
					moneysout.push(datas.rows[i].ysumout);
					dates.push(datas.rows[i].sdate+"年");
				}
				$('#nodatas').css("display","none");
			}else{
				barChart.hideLoading();    //隐藏加载动画
				$('#nodatas').css("display","block");
				return;
			}
			barChart.hideLoading();    //隐藏加载动画
			var baroption = {
			        title : {
			            text: '年度收/支统计报表'
			        },
			        tooltip : {
			            trigger: 'axis'
			        },
			        legend: {
			            data:['年支出','年收入']
			        },
			        grid:{
			            height:'60%'
			            /* width : '80%' */
			        }, 
			        toolbox: {
				        show : true,
				        feature : {
				            //mark : {show: true},
				            //dataView : {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar']},
				            restore : {show: true},
				            saveAsImage : {
				            	show: true,
				            	title : '保存为图片',
				                type : 'jpeg',
				                lang : ['点击保存']}
				        },
				        backgroundColor : 'rgba(11,192,168,0.6)'
				    },
			        calculable : true,
			        noDataLoadingOption :{
			        	text: '暂无数据'
			        },
			        xAxis : [
			            {
			                type : 'category',
			                data : dates
			            }
			        ],
			        yAxis : [
			            {
			                type : 'value'
			            }
			        ],
			        series : [
			            {
			                name:'年支出',
			                type:'bar',
			                data:moneysout,
			                markPoint : {
			                    data : [
			                        {type : 'max', name: '年支出最大值'},
			                        {type : 'min', name: '年支出最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name: '年支出平均值'}
			                    ]
			                }
			            },
			            {
			                name:'年收入',
			                type:'bar',
			                data:moneysin,
			                markPoint : {
			                    data : [
			                        /* {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
			                        {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3} */
			                        {type : 'max', name: '年收入最大值'},
			                        {type : 'min', name: '年收入最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name : '年收入平均值'}
			                    ]
			                }
			            }
			        ]
			    };
			    barChart.setOption(baroption);
			    window.onresize = function () {
			        //重置容器高宽
			        resizeWorldMapContainer();
			        barChart.resize();
			    }; 
			    //window.onresize = barChart.resize;
		}
	}
</script>
</head>
<body>
	<div class="panel-body" style="padding-bottom: 0px;">
		<div class="row" id="seminar_detail">
			<div class="col-sm-12">
				<div class="ibox float-e-margins">
					<div class="ibox-title">
						<h5>柱状图</h5>
						<div class="ibox-tools">
							<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
							</a>
						</div>
					</div>
					<div class="ibox-content">
						<form id="formSearch" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">年份：</label>
								<div class="col-sm-3">
									<select class="form-control" name="syear" id="syear">
										<option value="0">----选择开始年份----</option>
										<c:forEach items="${allFall }" var="e">
											<option value="${e}">${e}年</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-3">
									<select class="form-control" name="eyear" id="eyear">
										<option value="0">----选择结束年份----</option>
										<c:forEach items="${allFall }" var="e">
											<option value="${e}">${e}年</option>
										</c:forEach>
									</select>
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
						<div style="display: none" id="nodatas">
							<br />
							<br />
							<h1 align="center">暂无该年度收支数据！</h1>
						</div>
						<div class="echarts" id="echarts-bar-chart">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	 
	<!-- 自定义js 放在开头i-box无法收缩-->
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
</body>
</html>