<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<script>
	$("a[target=ajax]", document).each(function(){
		$(this).click(function(event){
			//重新构建editLi
			$("#editLi").html('');
			$("#editLi").append("<a class='edit' href='' link='/ar/attendanceSettings/updatePersonDynGroupView' target='navTab'><span><spring:message code='button.update'/></span></a>");
			//重新构建editLi
			$("#deleteLi").html('');
			$("#deleteLi").append("<a class='delete' href='' link='/ar/attendanceSettings/deletePersonDynGroup' title='确定要删除吗?' target='ajaxTodo'><span><spring:message code='button.delete'/></span></a>");
			//重新添加target函数
			$("a[target=navTab]", document).each(function(){
				$(this).click(function(event){
					var $this = $(this);
					var title = $this.attr("title") || $this.text();
					var tabid = $this.attr("rel") || "_blank";
					var fresh = eval($this.attr("fresh") || "true");
					var external = eval($this.attr("external") || "false");
					var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
					DWZ.debug(url);
					if (!url.isFinishedTm()) {
						alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
						return false;
					}
					navTab.openTab(tabid, url,{title:title, fresh:fresh, external:external});
		
					event.preventDefault();
				});
			});
			if ($.fn.ajaxTodo) $("a[target=ajaxTodo]", document).ajaxTodo();
			var $this = $(this);
			var seachUrl = $this.attr("href");//查询URL
			//点击班次列表时  更新 修改和删除的链接
			var shiftNo = seachUrl.split("?")[1];//查询参数  班次主键
			$(".edit").attr("href",$(".edit").attr("link") + "?" + shiftNo);
			$(".delete").attr("href",$(".delete").attr("link") + "?" + shiftNo);
			//执行查询
			var rel = $this.attr("rel");
			if (rel) {
				var $rel = $("#"+rel);
				$rel.loadUrl(seachUrl, {}, function(){
					$rel.find("[layoutH]").layoutH();
				});
			}
			event.preventDefault();
		});
	});

	$(function (){
	  	$('#hide_PersonDyngroup').hide();
	})
</script>
<div class="pageContent">
	<div class="panel">
		<c:set value="navTab" var="add_tab"/>
		<c:set value="800" var="add_width"/>
		<c:set value="400" var="add_height"/>
		<c:set value="/ar/attendanceSettings/addPersonDynGroupView" var="add_Url"/>
		<c:set value="ajaxTodo" var="delete_tab"/>
	    <c:set value="/ar/attendanceSettings/deletePersonDynGroup?NO={GROUP_NO}" var="delete_Url"/>     
		<c:set value="navTab" var="edit_tab"/>
		<c:set value="800" var="edit_width"/>
		<c:set value="400" var="edit_height"/>
		<c:set value="/ar/attendanceSettings/updatePersonDynGroupView?NO={GROUP_NO}" var="edit_Url"/>
<!-- 		<div> -->
<!-- 		<ul> -->
<!-- 		<li> -->
<!-- 				<a class="delete" onclick="" href="#"  -->
<!-- 					><span>删除</span></a>					 -->
<!-- 		</li> -->
<!-- 		</ul> -->
		<h1>  <%@ include file="/WEB-INF/view/inc/includeButton.jsp"%> </h1>
<!-- 		</div> -->
		<div class="tabsContent" layoutH="60">
			
				<div layoutH="62" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
				    	<li><a href="">人员管理分组</a>
							<ul>
								<c:if test="${fn:length(PersonDynGroupList) == 0}">
									<li id="hide_PersonDyngroup" ><a href="#"></a></li>
								</c:if>
								<c:forEach items="${PersonDynGroupList}" var="PersonDynGroup" varStatus="i">
									<li>
										<a href="/ar/attendanceSettings/viewPersonDynGroupInfoList?NO=${PersonDynGroup.GROUP_NO}&pageNum=1&menuNo=${menuNo}" target="ajax" rel="jbsxBox1">
											${PersonDynGroup.GROUP_NAME}
										</a>
									</li>
								</c:forEach>	
							</ul>
						</li>
				     </ul>
				</div>
				<div id="jbsxBox1" class="unitBox1" style="margin-left:246px;border-left:none;border-right:none">
					<div class="tabsHeader" style="display:none">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span><!-- 人员明细 --><spring:message code="ar.viewdynamicgroup.title.renyuanmingxi"/></span></a></li>
							</ul>
						</div>
					</div>
						<table class="table" width="99%" layoutH="90">
							<thead>
								<tr>
									<th width="45"><!-- 工号 --><spring:message code="public.title.empId"/></th>
									<th width="45"><!-- 姓名 --><spring:message code="public.title.name"/></th>
									<th width="45"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
									<th width="45"><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
				</div>
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
