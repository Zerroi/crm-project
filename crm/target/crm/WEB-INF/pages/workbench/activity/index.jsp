<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="UTF-8">

	<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css">
	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

	<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

   	<script type="text/javascript">
		$(function (){
			$(".datetimepicker").datetimepicker({
				language : 'zh-CN',
				format : 'yyyy-mm-dd',
				minView : 'month',
				initialDate : new Date(),
				autoclose : true,
				clearBtn : true,
				todayBtn : true
			});

			$("#createActivityBtn").click(function () {
				$("#createActivityModal").modal("show");
				$("#CreateActivityForm").get(0).reset();
			});

			$("#saveCreateActivityBtn").click(function () {
				var owner = $("#create-marketActivityOwner").val();
				var name = $.trim($("#create-marketActivityName").val());
				var startDate = $("#create-startTime").val();
				var endDate = $("#create-endTime").val();
				var cost = $.trim($("#create-cost").val());
				var description = $.trim($("#create-describe").val());
				if (owner === "") {
					alert("活动所有者不能为空")
					return;
				}
				if (name === "") {
					alert("活动名称不能为空")
					return;
				}
				if (startDate !== "" && endDate !== "") {
					if (endDate < startDate) {
						alert("开始时间不能大于结束时间")
						return;
					}
				}
				if(!(/(^[1-9]\d*|0$)/.test(cost))){
					alert("活动花费应该为非负整数");
					return;
				}
				$.ajax({
					url :'workbench/activity/saveCreateActivity.do',
					data : {
						owner : owner,
						name : name,
						startDate : startDate,
						endDate : endDate,
						cost : cost,
						description : description
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (data.code === "1") {
							$("#createActivityModal").modal("hide");
						} else {
							alert(data.message);
							$("#createActivityModal").modal("show");
						}
					}
				});

			});

			queryActivityByConditionForPage(1,10);

			$("#queryBtn").click( function () {
				queryActivityByConditionForPage(1, $("#pagination_container").bs_pagination('getOption', 'rowsPerPage'));
			});

			$("#checkAll").click( function (){
				$("#tBody input[type='checkbox']").prop("checked", this.checked);
			});

			$("#tBody").on("click" ,"input[type='checkbox']", function (){
				if($("#tBody input[type='checkbox']").size() === $("#tBody input[type='checkbox']:checked").size() ) {
					$("#checkAll").prop("checked", true);
				} else {
					$("#checkAll").prop("checked", false);
				}
			});

			$("#deleteActivityBtn").click(function (){
				var checked = $("#tBody input[type='checkbox']:checked");

				if(checked.size() === 0) {
					alert("请选择正确的活动数量")
					return;
				}
				var ids = "";
				$.each(checked, function () {
					ids += "id="+this.value+"&"
				})
				ids.substring(0, ids.length-1);

				if (window.confirm("确认删除吗？")) {
					$.ajax( {
						url : 'workbench/activity/deleteActivityById.do',
						data : ids,
						type : 'post',
						dataType : 'json',
						success : function (data){
							if (data.code === "1") {
								var paginationContainer = $("#pagination_container");
								queryActivityByConditionForPage(1, paginationContainer.bs_pagination('getOption', 'rowsPerPage'));
							} else {
								alert(data.message);
							}
						}
					})
				}
			});

			$("#modifyActivityBtn").click( function () {
				var checked = $("#tBody input[type='checkbox']:checked");
				if (checked.size() !== 1) {
					alert("请选择正确的市场活动数量");
					return;
				}
				var id = checked[0].value;
				$.ajax({
					url : 'workbench/activity/queryActivityById.do',
					data : {
						id : id
					},
					type : 'post',
					dataType : 'json',
					success : function (data) {
						$("#hiddenActivityId").val(data.id);
						$("#edit-marketActivityOwner").val(data.owner);
						$("#edit-marketActivityName").val(data.name);
						$("#edit-startTime").val(data.startDate);
						$("#edit-endTime").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-describe").val(data.description);
						$("#editActivityModal").modal("show");
					}
				});
			});

			$("#updateActivityBtn").click( function () {
				var owner=$("#edit-marketActivityOwner").val();
				var name=$.trim($("#edit-marketActivityName").val());
				var startDate=$("#edit-startTime").val();
				var endDate=$("#edit-endTime").val();
				var cost=$.trim($("#edit-cost").val());
				var description=$.trim($("#edit-describe").val());
				var id = $("#hiddenActivityId").val();
				if (owner === "") {
					alert("活动所有者不能为空")
					return;
				}
				if (name === "") {
					alert("活动名称不能为空")
					return;
				}
				if (startDate !== "" && endDate !== "") {
					if (endDate < startDate) {
						alert("开始时间不能大于结束时间")
						return;
					}
				}
				if(!(/(^[1-9]\d*|0$)/.test(cost))){
					alert("活动花费应该为非负整数");
					return;
				}
				$.ajax({
					url : 'workbench/activity/saveEditActivity.do',
					data : {
						id : id,
						owner : owner,
						name : name,
						startDate : startDate,
						endDate : endDate,
						cost : cost,
						description : description
					},
					type : 'post',
					dataType : 'json',
					success : function (data) {
						if(data.code === "1") {
							$("#editActivityModal").modal("hide");
							var paginationContainer = $("#pagination_container");
							queryActivityByConditionForPage(paginationContainer.bs_pagination('getOption', 'currentPage'),
									paginationContainer.bs_pagination('getOption', 'rowsPerPage'));
						} else {
							alert(data.message);
							$("#editActivityModal").modal("show");
						}
					}
				});
			});

			$("#exportActivityAllBtn").click( function () {
				window.location.href = "workbench/activity/exportAllActivities.do";
			});

			$("#exportActivityXzBtn").click(function () {
				var checked = $("#tBody input[type='checkbox']:checked");
				if (checked.size() <= 0) {
					alert("请选择你要导出的市场活动");
					return;
				}
				var ids = "";
				$.each(checked, function () {
					ids += "id="+this.value+"&"
				})
				ids.substring(0, ids.length-1);
				console.log(ids)
				window.location.href = "workbench/activity/exportSelectedActivity.do?"+ids;
			});

			//给"导入"按钮添加单击事件
			$("#importActivityBtn").click(function () {
				//收集参数
				var activityFileName=$("#activityFile").val();
				var suffix=activityFileName.substr(activityFileName.lastIndexOf(".")+1).toLocaleLowerCase();//xls,XLS,Xls,xLs,....
				if(suffix!=="xls"){
					alert("只支持xls文件");
					return;
				}
				var activityFile=$("#activityFile")[0].files[0];
				if(activityFile.size>5*1024*1024){
					alert("文件大小不超过5MB");
					return;
				}

				//FormData是ajax提供的接口,可以模拟键值对向后台提交参数;
				//FormData最大的优势是不但能提交文本数据，还能提交二进制数据
				var formData=new FormData();
				formData.append("activityFile",activityFile);
				formData.append("userName","张三");

				//发送请求
				$.ajax({
					url:'workbench/activity/importActivity.do',
					data:formData,
					processData:false,//设置ajax向后台提交参数之前，是否把参数统一转换成字符串：true--是,false--不是,默认是true
					contentType:false,//设置ajax向后台提交参数之前，是否把所有的参数统一按urlencoded编码：true--是,false--不是，默认是true
					type:'post',
					dataType:'json',
					success:function (data) {
						if(data.code==="1"){
							//提示成功导入记录条数
							alert("成功导入"+data.retData+"条记录");
							//关闭模态窗口
							$("#importActivityModal").modal("hide");
							//刷新市场活动列表,显示第一页数据,保持每页显示条数不变
							queryActivityByConditionForPage(1,$("#demo_pag1").bs_pagination('getOption', 'rowsPerPage'));
						}else{
							//提示信息
							alert(data.message);
							//模态窗口不关闭
							$("#importActivityModal").modal("show");
						}
					}
				});
			});
		});

		function queryActivityByConditionForPage(pageNo, pageSize) {
			var name = $("#query_name").val();
			var owner =  $("#query_owner").val();
			var startDate = $("#startTime").val();
			var endDate = $("#endTime").val();
			$.ajax({
				url : 'workbench/activity/queryCountOfActivityByCondition.do',
				data: {
					name : name,
					owner : owner,
					startDate : startDate,
					endDate : endDate,
					pageNo : pageNo,
					pageSize : pageSize
				},
				type: 'post',
				dataType: 'json',
				success : function (data) {
					// $("#totalRowB").text(data.totalRows);
					var htmlStr = '';
					$.each(data.activityList, function(index, obj) {
						htmlStr += "<tr class=\"active\">"
						htmlStr += "<td><input type=\"checkbox\" value=\""+obj.id+"\"/></td>"
						htmlStr+="<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='workbench/activity/detailActivity.do?id="+obj.id+"'\">"+obj.name+"</a></td>";
						htmlStr += "<td>"+obj.owner+"</td>"
						htmlStr += "<td>"+obj.startDate+"</td>"
						htmlStr += "<td>"+obj.endDate+"</td>"
						htmlStr += "</tr>"
					});
					$("#tBody").html(htmlStr);

					var totalPages = 1;
					if(data.totalRows%pageSize === 0) {
						totalPages = data.totalRows/pageSize;
					} else {
						totalPages = Math.ceil(data.totalRows/pageSize);
					}

					$("#pagination_container").bs_pagination( {
						currentPage:pageNo,//当前页号,相当于pageNo
						rowsPerPage:pageSize,//每页显示条数,相当于pageSize
						totalRows:data.totalRows,//总条数
						totalPages: totalPages,  //总页数,必填参数.
						visiblePageLinks:5,//最多可以显示的卡片数
						showGoToPage:true,//是否显示"跳转到"部分,默认true--显示
						showRowsPerPage:true,//是否显示"每页显示条数"部分。默认true--显示
						showRowsInfo:true,//是否显示记录的信息，默认true--显示
						onChangePage : function (event, pageObj) {
							queryActivityByConditionForPage(pageObj.currentPage, pageObj.rowsPerPage);
						}
					})
				}
			})
		}

   	</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" id="CreateActivityForm" role="form">
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${requestScope.userList}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control datetimepicker" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label ">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control datetimepicker" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveCreateActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">
						<input type="hidden" id="hiddenActivityId">
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${requestScope.userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control datetimepicker" id="edit-startTime" readonly>
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control datetimepicker" id="edit-endTime" readonly>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateActivityBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: rgb(128,128,128);">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" id="query_name" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" id="query_owner" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="queryBtn">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createActivityBtn" data-target="#createActivityModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="modifyActivityBtn" data-target="#editActivityModal"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="tBody">
					</tbody>
				</table>
			</div>
				<div id="pagination_container"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>