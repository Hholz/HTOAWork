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
		var myDate = new Date();
		var myYear = myDate.getFullYear(); //获取完整的年份(4位,1970-????)
		//syear : $('#formSearch #syear').val(myYear);
		dataInit();
		$("#query").click(function() {
			dataInit();
		});
	});
	var dataInit = function (){
		var url = "${pageContext.request.contextPath}/market/report/getmonth";
		$.post(
			url,
			{
				syear : $('#formSearch #syear').val()
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
        var intention=[];    //类别数组（实际用来盛放X轴坐标值）
        var dates=[];    //销量数组（实际用来盛放Y坐标值）
        var success=[];    //类别数组（实际用来盛放X轴坐标值）就读状态
        var isTest=[];    //类别数组（实际用来盛放X轴坐标值）已试学状态
        var isLeave=[];    //类别数组（实际用来盛放X轴坐标值）已离开状态
		function resetBar(datas){
        	var imsc = datas.rows.imsc;
        	var smsc = datas.rows.smsc;
        	var tmsc = datas.rows.tmsc;
        	var lmsc = datas.rows.lmsc;
			if(datas.total>0){
				intention.push(imsc.january);
				intention.push(imsc.february);
				intention.push(imsc.march);
				intention.push(imsc.april);
				intention.push(imsc.may);
				intention.push(imsc.june);
				intention.push(imsc.july);
				intention.push(imsc.august);
				intention.push(imsc.september);
				intention.push(imsc.october);
				intention.push(imsc.november);
				intention.push(imsc.december);
				
				success.push(smsc.january);
				success.push(smsc.february);
				success.push(smsc.march);
				success.push(smsc.april);
				success.push(smsc.may);
				success.push(smsc.june);
				success.push(smsc.july);
				success.push(smsc.august);
				success.push(smsc.september);
				success.push(smsc.october);
				success.push(smsc.november);
				success.push(smsc.december);
				
				isTest.push(tmsc.january);
				isTest.push(tmsc.february);
				isTest.push(tmsc.march);
				isTest.push(tmsc.april);
				isTest.push(tmsc.may);
				isTest.push(tmsc.june);
				isTest.push(tmsc.july);
				isTest.push(tmsc.august);
				isTest.push(tmsc.september);
				isTest.push(tmsc.october);
				isTest.push(tmsc.november);
				isTest.push(tmsc.december);
				
				isLeave.push(lmsc.january);
				isLeave.push(lmsc.february);
				isLeave.push(lmsc.march);
				isLeave.push(lmsc.april);
				isLeave.push(lmsc.may);
				isLeave.push(lmsc.june);
				isLeave.push(lmsc.july);
				isLeave.push(lmsc.august);
				isLeave.push(lmsc.september);
				isLeave.push(lmsc.october);
				isLeave.push(lmsc.november);
				isLeave.push(lmsc.december);
				
				dates.push('一月');
				dates.push('二月');
				dates.push('三月');
				dates.push('四月');
				dates.push('五月');
				dates.push('六月');
				dates.push('七月');
				dates.push('八月');
				dates.push('九月');
				dates.push('十月');
				dates.push('十一月');
				dates.push('十二月');
				
				$('#nodatas').css("display","none");
			}else if(datas.total==0){
				barChart.hideLoading();    //隐藏加载动画
				$('#nodatas').css("display","block");
				return;
			}
			barChart.hideLoading();    //隐藏加载动画
			var baroption = {
			        title : {
			            text: '意向学生统计报表'
			        },
			        tooltip : {
			            trigger: 'axis'
			        },
			        legend: {
			            data:['意向','试学','就读','离开']
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
			                name:'意向',
			                type:'bar',
			                data:intention,
			                markPoint : {
			                    data : [
			                        {type : 'max', name: '最大值'},
			                        {type : 'min', name: '最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name: '平均值'}
			                    ]
			                }
			            },
			            {
			                name:'试学',
			                type:'bar',
			                data:isTest,
			                markPoint : {
			                    data : [
			                        {type : 'max', name: '最大值'},
			                        {type : 'min', name: '最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name: '平均值'}
			                    ]
			                }
			            },
			            {
			                name:'就读',
			                type:'bar',
			                data:success,
			                markPoint : {
			                    data : [
			                        {type : 'max', name: '最大值'},
			                        {type : 'min', name: '最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name: '平均值'}
			                    ]
			                }
			            },
			            {
			                name:'离开',
			                type:'bar',
			                data:isLeave,
			                markPoint : {
			                    data : [
			                        {type : 'max', name: '最大值'},
			                        {type : 'min', name: '最小值'}
			                    ]
			                },
			                markLine : {
			                    data : [
			                        {type : 'average', name: '平均值'}
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
								<!-- <div class="col-sm-1">
									<button type="button" class="btn btn-success" id="clear">
										<span class="glyphicon glyphicon-zoom-out" aria-hidden="true"></span>清除
									</button>
								</div> -->
							</div>
						</form>
						<div style="display: none" id="nodatas">
							<br />
							<br />
							<h1 align="center">暂无该年的月度收支数据！</h1>
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