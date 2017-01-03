<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width" />
<jsp:include page="../styleInclude.jsp"></jsp:include>
<title>办公用品审领</title>

<script type="text/javascript">
	//全局变量，用来保存选中行的数据
	var rows = null;
	$(function() {
		$('#window_add').modal('show');
	});
	//新增学生，ajax提交
	function addStudent(id) {
		if (!validateForm($("#commentForm"))) {
			return;
		}
		//parent.layer.alert('添加成功');
		//return;
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/add";
		$.post(url, {
			materialid : $("#window_add #mid").val(),
			counts : $("#window_add #counts").val(),
			receiveremark : $("#window_add #remark").val(),
			flowmodeltypeid :  $("#window_add #flowmodeltypeid").val(),
			upset : id
		}, addStudentHandle, "text");

		//清空新增div中的数据
		
	}
	function addStudentHandle(data) {
		//从后台返回出来的int类型数据，用来判断是否新增成功
		if (data > 0) {
			swal({
				title : "成功",
				text : "你已经完成添加操作",
				type : "success"
			},function() {
				window.location.href="${pageContext.request.contextPath }/flow/myFlowList";;
			});
			//window.location.href=window.location.href; 
		} else {
			swal("添加失败", "请检查你输入的数据！", "error");
		}
		
	}
	function selecttype() {
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/findmaterial";
		$.post(url, {
			materialtypeid : $("#window_add #materialtypeid").val(),
		}, initemp, "text");
	} 
	function initemp(data) {
		var opt = "";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i < datas.rows.length; i++) {
				opt += '<option title="'+"此物品还需"+datas.rows[i].lessnumber+'" value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].materialname + '</option>';
			}
			$('#window_add #materialid').empty();
			$('#window_add #materialid').html(opt);
			$('#window_add #materialid').trigger("chosen:updated");
			selectmaterial();
		}else{
			opt += '<option >'+ "-----" + '</option>';
			$('#window_add #materialid').empty();
			$('#window_add #materialid').html(opt);
		}
		
	}
	function selectmaterial() {
		var url = "${pageContext.request.contextPath }/flow/ReceviceMaterial/findmaterialdetail";
		$.post(url, {
			id : $("#window_add #materialid").val(),
		}, mess, "json");
	}
	function mess(data) {
		var opt = "";				
		opt= data.rows.counts+data.rows.unit;
		$("#window_add #kucun").val(opt);
		$("#window_add #mid").val(data.rows.id);
		$("#window_add #counts").attr('max',data.rows.counts);
		$("#window_add #counts").attr('min',1);
	} 
	
</script>
</head>
<body>
	<div class="modal inmodal" id="window_add" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content animated bounceInRight">
				<div class="modal-header">
					<h4 class="modal-title">物品申领</h4>
				</div>
				<div class="ibox-content">
					<form class="form-horizontal m-t" id="commentForm"
						novalidate="novalidate">
						<div class="form-group">
							<label class="col-sm-4 control-label">申购类型：</label>
							<div class="col-sm-4">
								<select id="flowmodeltypeid" class="form-control" required
									name="flowmodeltypeid">
									<option value="">-----</option>
									<c:forEach items="${flowmodeltypelist}" var="cla">
										<option value="${cla.id}">${cla.flowmodeltype}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-4 control-label">物品类别：</label>
							<div class="col-sm-4">
								<select id="materialtypeid" class="form-control" required
									name="materialtypeid" onchange="selecttype();">
									<option value="">-----</option>
									<c:forEach items="${mlist}" var="cla">
										<option value="${cla.id}">${cla.materialtypename}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">物品：</label>
							<input type="hidden" id="mid" />
							<div class="col-sm-4">
								<select id="materialid" title="" required class="form-control"
									name="materialid" onchange="selectmaterial();">
									<option value="">-----</option>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">此物品的库存为:</label>
							<div class="col-sm-4">
								<input id="kucun" class="form-control" disabled="disabled" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">申领数量：</label>
							<div class="col-sm-4">
								<input id="counts" type="number" name="counts" required class="form-control">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label">备注：</label>
							<div class="col-sm-6">
								<textarea id="remark" name="remark" rows="4"
									class="form-control"></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-white" onclick="addStudent(0)" id="save">保存</button>
							<button type="button" onclick="addStudent(1)" id="upset" class="btn btn-primary">提交</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//已经把文档下到本地，访问地址：http://localhost:8080/HTOAWork/Demo/validateDemo/jQueryValidate.html
		//详情参考：http://www.runoob.com/jquery/jquery-plugin-validate.html
		//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
		$.validator.setDefaults({
			highlight : function(element) {
				$(element).closest('.form-group').removeClass('has-success')
						.addClass('has-error');
			},
			success : function(element) {
				element.closest('.form-group').removeClass('has-error')
						.addClass('has-success');
			},
			errorElement : "span",
			errorPlacement : function(error, element) {
				if (element.is(":radio") || element.is(":checkbox")) {
					error.appendTo(element.parent().parent().parent());
				} else {
					error.appendTo(element.parent());
				}
			},
			errorClass : "help-block m-b-none",
			validClass : "help-block m-b-none"

		});
		//调用表单验证的方法，在addStudent()里调用，出入form对象
		//***input控件要设置name属性***
		function validateForm(formdata) {
			var icon = "<i class='fa fa-times-circle'></i> ";
			var validator = formdata.validate({
				rules : {
					price : {
						required : true,
						digits : true
					},

				},
				messages : {
					materialname : icon + "请输入用品名称",
					unit : icon + "请输入用品单位,如盒,瓶等等",
					price : {
						required : icon + "请输入用品价格",
						digits : icon + "价格只能是数字"
					},
					style : {
						required : icon + "请输入用品规格"
					},
					counts : {
						required : icon + "请输入用品数量"
					},
					applymaterialRemark : {
						required : icon + "请输入申购理由"
					}
				},
			});
			//返回表单验证是否通过，true false
			return validator.form();
		}
	</script>
	<script src="${pageContext.request.contextPath}/js/content.js?v=1.0.0"></script>
	<script
		src="${pageContext.request.contextPath}/js/plugins/layer/laydate/laydate.js"></script>
</body>
</html>