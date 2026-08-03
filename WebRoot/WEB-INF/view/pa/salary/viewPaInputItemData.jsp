<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function (){
  	$('#TestA',navTab.getCurrentPanel()).hide();
  });
</script>


		<div class="tabsContent">
				<div layoutH="10" style="float:left; display:block; overflow:auto; width:300px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<ul class="tree treeFolder">
			<li>
				<a href=""> <c:choose>
						<c:when test="${itemType == 1 }"><!--标准项目--><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></c:when>
						<c:when test="${itemType == 2 }"><!--支付调整项目--><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></c:when>
						<c:when test="${itemType == 3 }"><!--支付例外项目--><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" /></c:when>
						<c:when test="${itemType == 4 }"><!--扣除调整项目--><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></c:when>
						<c:when test="${itemType == 5 }"><!--扣除例外项目--><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" /></c:when>
						<c:when test="${itemType == 6 }"><!--保险标准项目--><spring:message code="pa.viewPaMain.BAOXIANBIAOZHUNXIANGMU.C" /></c:when>
						<c:when test="${itemType == 7 }"><!--保险补缴项目--><spring:message code="pa.viewPaMain.BAOXIANBUJIAOXIANGMU.C" /></c:when>
						<c:when test="${itemType == 8 }"><!--保险补扣项目--><spring:message code="pa.viewPaMain.BAOXIANBUKOUXIANGMU.C" /></c:when>
						<c:when test="${itemType == 9 }"><!--保险例外项目--><spring:message code="pa.viewPaMain.BAOXIANLIWAIXIANGMU.C" /></c:when>
						<c:when test="${itemType == 10 }"><!--手工上传统计项目--><spring:message code="pa.viewPaMain.SHOUGONGSHANGCHUANTONGJIXIANGMU.C" /></c:when>
					</c:choose> </a>
				<ul>
					<c:if test="${empty proList}">
						<li id="TestA">
							<a href="#"></a>
						</li>
					</c:if>
					<c:forEach items="${proList}" var="item">
						<li>
							<a
								href="/pa/salary/viewPaInputItemDataList?pageNum=1&itemType=${itemType }&type=${item.DISTINCT_FIELD}&seach_PARAM_NO=${item.PARAM_NO}&seach_CPNY_ID=${CPNY_ID}"
								target="ajax" rel="viewPaInputItemData_${itemType }"><span>${item.ALIAS_NAME}</span>
							</a>
						</li>
					</c:forEach>
				</ul>
			</li>

		</ul>
	</div>
				
				<div id="viewPaInputItemData_${itemType }" class="unitBox" style="margin-left:302px;">
					<div class="pageContent">
					</div>
				</div>				
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>