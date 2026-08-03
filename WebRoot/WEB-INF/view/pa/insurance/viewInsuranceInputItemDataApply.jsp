<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(function (){
  	$('#TestA').hide();
  })	
</script>

<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
	
				<div layoutH="45" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href=""><spring:message code="pa.insurance.title.inputItem"/><!--输入项目--></a>
							<ul> 
								<c:if test="${empty isInputItemDataList}">
									<li id="TestA" ><a href="#"></a></li>
								</c:if>
								 <c:forEach items="${isInputItemDataList}" var="item">
								 	<li><a href="/pa/insurance/viewInsuranceInputItemDataApplyList?pageNum=1&seach_PARAM_NO=${item.PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" target="ajax" rel="viewInsuranceInputItemDataApply" id="viewInsuranceInputItemDataApplyTow"><span>${item.ALIAS_NAME}</span></a></li>
						 		 </c:forEach>
							</ul>
						</li>
						
				     </ul>
				</div>
				
				<div id="viewInsuranceInputItemDataApply" class="unitBox" style="margin-left:246px;">
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
					</div>
				</div>
	
			</div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>


