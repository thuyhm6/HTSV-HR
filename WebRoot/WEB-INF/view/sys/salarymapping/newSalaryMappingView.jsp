<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function aa(){
    var ids=document.getElementsByName("CPNY_ID"); 
}

</script>
<div class="pageHeader">
	<form id="updateSalaryMappingInfo" method="post" action="/sys/salarymapping/updateSalaryMappingInfo"
		class="pageForm required-validate"onsubmit="return validateCallback(this, dialogAjaxDoneWithForm);">
		<table class="table" width="100%" layoutH="100" class="user_table">
			<thead>
				<tr width="100%">
					<th width="3%"><spring:message
							code="sys.affirm.indexNum" /> <!--序号-->
					</th>
					<th width="6%"><spring:message
							code="pa.insurance.title.projectType" /> <!--项目类型-->
					</th>
					<th width="12%"><spring:message
							code="pa.insurance.title.projectName" /> <!--项目名称-->
					</th>
					<th width="13%"><spring:message
							code="pa.insurance.title.description" /> <!--描述-->
					</th>
					<th width="4%">HSTV</th>
					<th width="4%">HAE</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>${i.index + 1}&nbsp;</td>
					<td><c:choose>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 1 }"><!--标准项目 --><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></c:when>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 2 }"><!--支付调整项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 3 }"><!--支付例外项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" /></c:when>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 4 }"><!--扣除调整项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 5 }"><!--扣除例外项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" /></c:when>
							<c:when test="${salaryMappingInfo.ITEM_TYPE == 6 }"><!--计算项目 --><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b" /></c:when>
						</c:choose> <input type="hidden"
						name="ITEM_TYPE" id="ITEM_TYPE"
						value="${salaryMappingInfo.ITEM_TYPE}" />
						<input type="hidden" name="ITEM_NO" id="ITEM_NO"
						value="${salaryMappingInfo.ITEM_NO}" />
					</td>
					<td>${salaryMappingInfo.ITEM_NAME}</td>
					<td>${salaryMappingInfo.DESCR}</td>

					<td width="4%"><c:if test="${salaryMappingInfo.HTSV==1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID"
								checked="checked" value='HTSV' />
						</c:if> <c:if test="${salaryMappingInfo.HTSV!=1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID" value='HTSV' />
						</c:if>
					</td>
					<td width="4%"><c:if test="${salaryMappingInfo.HAE==1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID"
								checked="checked" value="HAE" />
						</c:if> <c:if test="${salaryMappingInfo.HAE!=1}">
							<input type="checkbox" id="CPNY_ID" name="CPNY_ID" value="HAE" />
						</c:if>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="aa();">
								<spring:message code="public.title.submit" />
								<!-- 提交 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
