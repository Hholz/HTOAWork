<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/bootstrap/css/bootstrap-treeview.css">
<link href="${pageContext.request.contextPath}/css/plugins/chosen/chosen.css" rel="stylesheet"  type="text/css">
<script
	src="${pageContext.request.contextPath}/js/bootstrap/js/jquery-2.2.3.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/js/bootstrap/js/bootstrap-treeview.js"
	type="text/javascript"></script>
	<!-- Chosen -->
	<script src="${pageContext.request.contextPath}/js/plugins/chosen/chosen.jquery.js"></script>
</head>
<script type="text/javascript">
var config = {
        '.chosen-select': {},
        '.chosen-select-deselect': {
            allow_single_deselect: true
        },
        '.chosen-select-no-single': {
            disable_search_threshold: 1
        },
        '.chosen-select-no-results': {
            no_results_text: 'Oops, nothing found!'
        },
        '.chosen-select-width': {
            width: "95%"
        }
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<script type="text/javascript">
	$(function() {
		$('#mytree').treeview({
			data : $('#treedata').html(),
			showCheckbox : true,
			levels : 3,
			enableLinks : true,
			onNodeChecked : function(event, data) {
				//alert(data.id);
				var url = "${pageContext.request.contextPath }/dailyWork/findemp/"
						+ data.id;
				$.post(url, {
					_method : 'PUT'
				}, initemp, "text");
			},
			onNodeUnchecked: function (event, data) {
				/* $('#emp').html("");
				$('#emp').chosen("destory");
				$('#emp').trigger("liszt:updated");
				$('#emp').chosen(); */
				/* $('#emp').empty();
				$('#emp').html("");
				$('#emp').trigger("chosen:updated"); */
				
			}
		});
	});
	function initemp(data) {
		var opt = "";
		var e="";
		var datas = JSON.parse(data);
		var len = datas.rows.length;
		var i;
		if (len > 0) {
			for (i = 0; i<datas.rows.length; i++) {
				opt += '<option value="'+datas.rows[i].id+'" hassubinfo="true">'
						+ datas.rows[i].empname + '</option>';
						/* $('<option value="'+datas.rows[i].id+'">'+ datas.rows[i].empname + '</option>').appendTo('#emp'); */
			}
			/*$('#emp').html(opt);
			$('#emp').chosen("destory");
			$('#emp').trigger("liszt:updated");
			$('#emp').chosen();   */
			$('#emp').empty();
			$('#emp').html(opt);
			$('#emp').trigger("chosen:updated"); 
			$('#emp').chosen();
		}
	}
</script>
<body>
	<div id="mytree" class="col-md-4"></div>
	<div style="display: none" id="treedata">${deptree}</div>

	<div class="form-group">
		<label class="font-noraml">多选</label>
		<div class="input-group">
			<select class="chosen-select" data-placeholder="选择省份" multiple style="width: 350px;" tabindex="4" id="emp">
			</select>
		</div>
	</div>
</body>
</html>