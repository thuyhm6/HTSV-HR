<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
</script>

<div class="pageContent">  
	<form id="pageForm" method="post" action="/ess/dimissionApply/addDimissionEditionInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
	<table  class="user_table" width="90%" layoutH="48" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="30%" class="td_title"><spring:message code="ess.dimission.title.editionnumber"/><!--版本号--></td>
				<td width="70%" class="td_type">
                    <input id="EDITION_NO" name="EDITION_NO" type="text" size="30" class="required alphanumeric"/>			   
				</td>
			</tr>
			<tr>
			<td colspan="2">
				<table class="table" width="90%">
					<thead>
						<tr>
							<th width="150"><spring:message
									code="is.company.title.PERSON_TYPE" />
								<!--人员类型-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${empTypeNameList}" var="jobType" varStatus="i">
							<c:if test="${i.count%3 == 1 }">
							<tr target="EMP_TYPE_CODE" rel="${jobType.CODE_NO}">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" />&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</c:if>
							<c:if test="${i.count%3 == 2 }">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" />&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</c:if>
							<c:if test="${i.count%3 == 0 }">
								<td><input type="checkbox" name="check_EMP_TYPE_CODE"
									value="${jobType.CODE_NO}" />&nbsp;&nbsp;${jobType.EMP_TYPE_NAME}
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				</td>
			</tr>

			<tr>		
				<td width="30%" class="td_title"><spring:message code="sys.essParam.title.ifEnabled"/><!-- 是否启用--></td>
				<td width="70%" class="td_type">
				<input name="ACTIVITY" type="radio" value="1" checked/><!-- 启用 --><spring:message code="sys.arAffirmPost.title.able"/>
				<input name="ACTIVITY" type="radio" value="0"/><!-- 不启用 --><spring:message code="sys.arAffirmPost.title.enable"/>
				</td>    
			</tr>

			<tr>
				<td width="30%" class="td_title"><spring:message code="ar.viewItem.title.shuoming"/><!-- 说明 --></td>
				<td width="70%" class="td_type" colspan="3">
			        <textarea rows="3" name="REMARK" id = "REMARK"></textarea>
				</td>
		     </tr>
	</table>
	
		    <div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
  </form>	
</div>


