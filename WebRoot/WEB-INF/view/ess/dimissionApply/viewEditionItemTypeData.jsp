<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
  	$('#TestA').hide();
})
</script>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:5px;">
	<div>	
				<div layoutH="8" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
						&nbsp;&nbsp;&nbsp;&nbsp; <spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->：</a>
							
								${EDITION_NO}
								 <br><br/>
				    <ul class="tree treeFolder">
						<li><a href=""><spring:message code="ess.edition.title.sditionItem"/><!--离职交接项目--></a>
							<ul>
								 <c:if test="${empty editionItemTypeList}">
									<li id="TestA" ><a href="#"></a></li>
								 </c:if>
								 <c:forEach items="${editionItemTypeList}" var="item">
								 	<li><a href="/ess/dimissionApply/viewEditionItemTypeParamList?pageNum=1&EDITION_NO1=${item.EDITION_NO}&EDITION_ITEM_TYPE=${item.EDITION_ITEM_TYPE}&seach_CPNY_ID=${item.CPNY_ID}" target="ajax" rel="viewEditionItemTypeData"><span>${item.EDITION_ITEM_NAME}</span></a></li>
						 		 </c:forEach>
							</ul>
						</li>
				     </ul>
				</div>
				
				<div id="viewEditionItemTypeData" class="unitBox" style="margin-left:246px;">
					<div class="pageContent">
						<div class="formBar">
							<ul class="toolBar">
								<%--
								<li><a class="add"><span>添加</span></a></li>
								<li><a class="delete"><span>删除</span></a></li>
								<li><a class="edit"><span>修改</span></a></li>
								<li class="line">line</li>
								 --%>
							</ul>
						</div>
						<table class="table" width="99%" layoutH="260">
							<thead>
								<tr>
									<th width="30"><!-- 交接项目 --><spring:message code="ess.edition.title.editionItem"/></th>
				                    <th width="50"><!-- 描述 --><spring:message code="ar.viewcycle.title.miaoshu"/></th>
				                    <th width="10">显示顺序</th>
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