<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
  	$('#hide_viewsummaryformula').hide();
})
</script>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:5px;">
	<div>
		<div class="tabsHeader" style="display:none">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span><!-- 汇总项目列表 --><spring:message code="ar.viewSummaryFormula.title.huizongxiangmuliebiao"/></span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div layoutH="20" style="float:left; display:block; overflow:auto; width:270px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
				    	<a href="javascript" style="display:none"><!-- 汇总项目 --><spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/></a>
							
								<c:if test="${fn:length(SummaryFormulaItemList) == 0}">
									<li id="hide_viewsummaryformula" ><a href="#"></a></li>
								</c:if>
								<c:forEach items="${SummaryFormulaItemList}" var="item" varStatus="i">
									<li><a href="/ar/attendanceSettings/viewFormulaList?ITEM_NO=${item.ITEM_NO}&CPNY_ID=${CPNY_ID}&menuNo=${menuNo}" target="ajax" rel="jbsxBoxItemFormula">${item.ITEM_NAME}</a></li>
								</c:forEach>
							
				     </ul>
				</div>
				<div class="tabsHeader" style="display:none;">
					<div class="tabsHeaderContent">
						<ul>
							<li><a href="javascript:;"><span><!-- 考勤项目 --><spring:message code="ar.viewitemparameter.title.kaoqingxiangmu"/></span></a></li>
						</ul>
					</div>
				</div>
				<div id="jbsxBoxItemFormula" class="unitBox" style="margin-left:270px;">
					<div class="pageContent">
						<div class="formBar">
							<ul class="toolBar">
								<%--
								<li><a class="add"><span>添加</span></a></li>
								<li><a class="delete"><span>删除</span></a></li>
								<li><a class="edit"><span>修改</span></a></li>
								 --%>
							</ul>
						</div>
						<table class="table" width="99%" layoutH="142">
							<thead>
								<tr>
									<th width="5"><!-- 序号 --><spring:message code="ar.viewcycle.title.xuhao"/></th>
									<th width="40"><!-- 条件 --><spring:message code="ar.viewSummaryFormula.title.tiaojian"/></th>
									<th width="70"><!-- 公式 --><spring:message code="ar.viewSummaryFormula.title.gongshi"/></th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>
