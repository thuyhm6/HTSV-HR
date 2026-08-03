<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
	
  	$('#TestA').hide();
  })
</script>
	
<div class="pageContent">
			<div>	
				<div layoutH="8" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href=""><spring:message code="pa.insurance.title.inputItem"/><!--输入项目--></a>
							<ul>
								 <c:if test="${empty paBasicItemParamList}">
									<li id="TestA" ><a href="#"></a></li>
								 </c:if>
								 <c:forEach items="${paBasicItemParamList}" var="item">
								
								 	<li><a href="/pa/wagebase/viewPaBasicItemDataList?pageNum=1&seach_PARAM_NO=${item.PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" target="ajax" rel="viewPaBasicItemData"><span>${item.ALIAS_NAME}</span></a></li>
									<%--
										<li name="test"><a id="${item.PARAM_NO}"  href="/pa/wagebase/viewPaBasicItemDataList?pageNum=1&seach_PARAM_NO=${item.PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" target="ajax" rel="viewPaBasicItemData"><span>${item.ALIAS_NAME}</span></a></li>
						 		 	--%>
						 		 </c:forEach>
							</ul>
						</li>
						
				     </ul>
				</div>
				
				<div id="viewPaBasicItemData" class="unitBox" style="margin-left:242px;">
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
					</div>
				</div>	
			</div>	
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>