<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	$("a[target=ajax]", navTab.getCurrentPanel()).each(function(){
		$(this).click(function(event){
			//重新构建editLi
			$("#editLi").html('');
			$("#editLi").append("<a class='edit' href='' link='/ar/attendanceSettings/updateShiftView' target='navTab'><span><spring:message code='button.update'/></span></a>");
			//重新构建editLi
			$("#deleteLi").html('');
			$("#deleteLi").append("<a class='delete' href='' link='/ar/attendanceSettings/deleteShiftInfo' target='ajaxTodo'><span><spring:message code='button.delete'/></span></a>");
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
			$("#editLi .edit", navTab.getCurrentPanel()).attr("href",$("#editLi .edit").attr("link") + "?" + shiftNo);
			$(".delete", navTab.getCurrentPanel()).attr("href",$(".delete").attr("link") + "?" + shiftNo);
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
	  	$('#hide_viewshift').hide();
	})
</script>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent">
<div class="panel">
	<c:set value="navTab" var="add_tab"/>
	<c:set value="500" var="add_width"/>
	<c:set value="400" var="add_height"/>
	<c:set value="/ar/attendanceSettings/addShiftView?NO=${NO}" var="add_Url"/>
	<c:set value="/ar/attendanceSettings/deleteShiftInfo?NO={SHIFT_NO}" var="delete_Url"/>
	<c:set value="navTab" var="edit_tab"/>
	<c:set value="500" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/ar/attendanceSettings/updateShiftView?NO={SHIFT_NO}" var="edit_Url"/>
	
	<h1><%@ include file="/WEB-INF/view/inc/includeButton.jsp"%></h1>
	</div>
		<div class="tabsContent" layoutH="60">
				<div layoutH="62" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
				    	<li><a href="#"><!-- 班次列表 --><spring:message code="ar.viewshift.title.banciliebiao"/></a>
							<ul>
								<c:if test="${fn:length(shiftList) == 0}">
									<li id="hide_viewshift" ><a href="#"></a></li>
								</c:if>
								<c:forEach items="${shiftList}" var="shift" varStatus="i">
									<li><a href="/ar/attendanceSettings/viewShiftParameter?NO=${shift.SHIFT_NO}&menuNo=${menuNo}&navTabId=${navTabId}" target="ajax" rel="jbsxBoxShift">${shift.SHIFT_NAME}</a></li>
								</c:forEach>	
							</ul>
						</li>
						
				     </ul>
				</div>
				
				<div class="tabsHeader" style="display:none">
					<div class="tabsHeaderContent">
						<ul>
							<li><a href="javascript:;"><span><!-- 班次参数列表 --><spring:message code="ar.viewshift.title.bancicanshuliebiao"/></span></a></li>
						</ul>
					</div>
				</div>
				<div id="jbsxBoxShift" class="unitBox" style="margin-left:246px;">
						
					<div class="pageContent">
						
					
						<table class="table" width="100%" layoutH="90">
							<thead>
								<tr>
									<th width="50"><!-- 班次ID --><spring:message code="ar.viewshift.title.banciID"/></th>
									<th width="50"><!-- 班次类型 --><spring:message code="ar.viewshift.title.bancileixing"/></th>
									<th width="50"><!-- 开始日期 --><spring:message code="public.title.startDate"/></th>
									<th width="50"><!-- 开始时间 --><spring:message code="ess.infoApply.title.startTime"/></th>
									<th width="100"><!-- 结束日期 --><spring:message code="public.title.endDate"/></th>
									<th width="50"><!-- 结束时间 --><spring:message code="ess.infoApply.title.endTime"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
	
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
</div>
