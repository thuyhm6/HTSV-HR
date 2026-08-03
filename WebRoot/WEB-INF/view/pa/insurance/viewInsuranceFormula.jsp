<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<script type="text/javascript">
$(function (){
  	$('#TestA').hide();
  })
</script>
<div class="pageContent">
		<div class="tabsContent">
			<div>
				<div layoutH="10" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
				    	<li><a href="javascript"><spring:message code="pa.insurance.title.computeItem"/><!--计算项目--></a>
							<ul>
								<c:if test="${empty itemList}">
									<li id="TestA" ><a href="#"></a></li>
								</c:if>
								<c:forEach items="${itemList}" var="item" varStatus="i">
								    <li><a href="/pa/insurance/viewInsuranceFormulaList?ITEM_NO=${item.ITEM_NO }&CPNY_ID=${CPNY_ID}&menuNo=${menuNo}" target="ajax" rel="ViewInsuranceFormula">${item.ALIAS_NAME}</a></li>								   
								</c:forEach>	
							</ul>
						</li>					      	
				     </ul>
				</div>
				<div id="ViewInsuranceFormula" class="unitBox" style="margin-left:242px;">
					<div class="pageContent">
					</div>
				</div>
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
</div>