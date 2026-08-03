<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
  	$('#hide_viewitemparameter').hide();
})
</script>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:5px;">
	<div>
		<div class="tabsContent">
			<div>
				<div layoutH="20" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
				    	<li><a href="javascript"><!-- 明细项目库 --><spring:message code="ar.viewitemparameter.title.mingxixiangmuku"/></a>
							<ul>
								<c:if test="${fn:length(itemList) == 0}">
									<li id="hide_viewitemparameter" ><a href="#"></a></li>
								</c:if>
								<c:forEach items="${itemList}" var="item" varStatus="i">
									<li><a href="/ar/attendanceSettings/viewItemParameterList?AR_ITEM_NO=${item.ITEM_NO}&menuNo=${menuNo}" target="ajax" rel="jbsxBoxItemParam">${item.ITEM_NAME}</a></li>
								</c:forEach>	
							</ul>
						</li>
						
				     </ul>
				</div>
				<div id="jbsxBoxItemParam" class="unitBox" style="margin-left:246px;">
					<div class="pageContent">
						<div class="formBar">
							<ul class="toolBar">
								
								<li><a class="add"><span><!-- 添加 --><spring:message code="button.add"/></span></a></li>
								<li><a class="delete"><span><!-- 删除 --><spring:message code="button.delete"/></span></a></li>
								<li><a class="edit"><span><!-- 修改 --><spring:message code="button.update"/></span></a></li>
								<li class="line">line</li>
								 
							</ul>
						</div>
						<table class="table" width="99%" layoutH="260">
							<thead>
								<tr>
									<th width="50"><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></th>
									<th width="50"><!-- 组名称 --><spring:message code="ar.viewitemparameter.title.zumingcheng"/></th>
									<th width="50"><!-- 单位 --><spring:message code="ar.viewitemparameter.title.unit"/></th>
									<th width="50"><!-- 是否参考刷卡 --><spring:message code="ar.viewitemparameter.title.cankaoshuaka"/></th>
									<th width="50"><!-- 是否参考申请 --><spring:message code="ar.viewitemparameter.title.cankaoshenqing"/></th>
									<th width="100"><!-- 有效日期类型 --><spring:message code="ar.viewitemparameter.title.riqileixing"/></th>
									<th width="50"><!-- 活跃状态 --><spring:message code="ar.viewcycle.title.huoyuezhuangtai"/></th>
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
