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
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		$("#activityRemarkParent").on("mouseover", ".remarkDiv", function () {
			$(this).children("div").children("div").show();
		});
		$("#activityRemarkParent").on("mouseout", ".remarkDiv", function () {
			$(this).children("div").children("div").hide();
		});
		$("#activityRemarkParent").on("mouseover", ".myHref", function () {
			$(this).children("span").css("color","red");
		});
		$("#activityRemarkParent").on("mouseout", ".myHref", function () {
			$(this).children("span").css("color","#E6E6E6");
		});

		$("#saveCreateActivityRemarkBtn").click( function () {
			var noteContent=$.trim($("#remark").val());
			var activityId = '${requestScope.activity.id}';

			if (noteContent === "") {
				alert("备注内容不可以为空");
				return;
			}
			$.ajax({
				url : 'workbench/activity/saveCreateActivityRemark.do',
				data : {
					noteContent : noteContent,
					activityId : activityId
				},
				type : 'post',
				dataType : 'json',
				success : function (data) {
					if (data.code === "1") {
						$("#remark").val("");
						var htmlStr = "";
						htmlStr += "<div id=\"div_"+data.retData.id+"\" class=\"remarkDiv\" style=\"height: 60px;\">"
						htmlStr += "<img title=\"zhangsan\" src=\"image/user-thumbnail.png\" style=\"width: 30px; height:30px;\">"
						htmlStr += "<div style=\"position: relative; top: -40px; left: 40px;\" >"
						htmlStr += "<h5>"+data.retData.noteContent+"</h5>"
						htmlStr += "<font color=\"gray\">市场活动</font> <font color=\"gray\">-</font> <b>\"${requestScope.activity.name}\"</b> <small style=\"color: gray;\"> "+data.retData.createTime+" 由${sessionScope.sessionUser.name}</small>"
						htmlStr += "<div style=\"position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;\">"
						htmlStr += "<a class=\"myHref\" name=\"edit\" activityRemarkId="+data.retData.id+" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>"
						htmlStr += "&nbsp;&nbsp;&nbsp;&nbsp;"
						htmlStr += "<a class=\"myHref\" name=\"delete\" activityRemarkId="+data.retData.id+" href=\"javascript:void(0);\"><span class=\"glyphicon glyphicon-remove\" style=\"font-size: 20px; color: #E6E6E6;\"></span></a>"
						htmlStr += "</div>"
						htmlStr += "</div>"
						htmlStr += "</div>"
						$("#remarkDiv").before(htmlStr);
					} else {
						alert(data.message)
					}
				}
			});
		});

		$("#activityRemarkParent ").on("click", "a[name='delete']", function (){
			var id = $(this).attr("activityRemarkId");

			$.ajax({
				url : 'workbench/activity/deleteActivityRemark.do',
				data : {
					id : id
				},
				type : 'post',
				dataType : 'json',
				success : function (data) {
					if (data.code === "1") {
						$("#div_"+id).remove();
					} else {
						alert(data.message);
					}
				}
			});
		});

		$("#activityRemarkParent ").on("click", "a[name='edit']", function () {
			var id = $(this).attr("activityRemarkId");
			var noteContent = $("#div_"+id+" h5").text();
			$("#remarkId").val(id);
			$("#edit-describe").val(noteContent);
			$("#editRemarkModal").modal("show");
		});
		$("#updateRemarkBtn").click(function () {
			var id = $("#remarkId").val();
			var noteContent = $.trim($("#edit-describe").val());
			$.ajax({
				url : 'workbench/activity/modifyActivityRemark.do',
				data : {
					id : id,
					noteContent : noteContent
				},
				type : 'post',
				dataType : 'json',
				success : function (data) {
					if (data.code === "1")	 {
						$("#div_"+id+" h5").text(noteContent);
						$("#div_"+id+" small").text(" "+data.retData.editTime+" 由${sessionScope.sessionUser.name}修改");
						$("#editRemarkModal").modal("hide");
					} else {
						alert(data.message);
						$("#editRemarkModal").modal("show");
					}
				}
			});
		});
	});
	
</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
                </div>
            </div>
        </div>
    </div>

    

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>市场活动-${requestScope.activity.name} <small>${requestScope.activity.startDate} ~ ${requestScope.activity.endDate}</small></h3>
		</div>
		
	</div>
	
	<br/>
	<br/>
	<br/>

	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: rgb(128,128,128);">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${requestScope.activity.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: rgb(128,128,128);">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${requestScope.activity.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: rgb(128,128,128);">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${requestScope.activity.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: rgb(128,128,128);">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${requestScope.activity.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: rgb(128,128,128);">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${requestScope.activity.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: rgb(128,128,128);">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${requestScope.activity.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: rgb(128,128,128);">${requestScope.activity.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: rgb(128,128,128);">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${requestScope.activity.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: rgb(128,128,128);">${requestScope.activity.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: rgb(128,128,128);">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${requestScope.activity.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div id="activityRemarkParent" style="position: relative; top: 30px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>

		<c:forEach items="${requestScope.activityRemarkList}" var="activityRemark">
			<div id="div_${activityRemark.id}" class="remarkDiv" style="height: 60px;">
				<img title="${activityRemark.createBy}" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
				<div style="position: relative; top: -40px; left: 40px;" >
					<h5>${activityRemark.noteContent}</h5>
					<font color="gray">市场活动</font> <font color="gray">-</font> <b>${requestScope.activity.name}</b> <small style="color: rgb(128,128,128);"> ${activityRemark.editFlag == '1'?activityRemark.editTime:activityRemark.createTime} 由${activityRemark.editFlag == '1'?activityRemark.editBy : activityRemark.createBy}${activityRemark.editFlag == '1'?'修改':'创建'}</small>
					<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
						<a class="myHref" name="edit" activityRemarkId=${activityRemark.id} href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="myHref" name="delete" activityRemarkId=${activityRemark.id} href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
					</div>
				</div>
			</div>
		</c:forEach>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" id="saveCreateActivityRemarkBtn" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
</html>