<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(function (){
  	$('#TestA').hide();
  })	
</script>

<div class="pageContent">	

		<div class="tabsContent">
				<div layoutH="10" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href="javascript"><spring:message code="pa.insurance.title.computeItem"/><!--计算项目--></a>
							<ul>
								<c:if test="${empty paItemList}">
									<li id="TestA" ><a href="#"></a></li>
								</c:if>
								 <c:forEach items="${paItemList}" var="item">
								 	<li><a href="/pa/salary/viewPaFormulaList?ITEM_NO=${item.ITEM_NO}&menuNo=${menuNo}&ITEM_NAME=${item.ITEM_NAME}&CPNY_ID=${CPNY_ID}" target="ajax" rel="viewPaFormulaData"><span>${item.ITEM_NAME }</span></a></li>
						 		 </c:forEach>
							</ul>
						</li>						
				     </ul>
				</div>
				
				<div id="viewPaFormulaData" class="unitBox" style="margin-left:242px;">
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">						
					</div>
				</div>	
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>