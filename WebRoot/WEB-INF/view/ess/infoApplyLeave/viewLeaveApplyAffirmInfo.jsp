<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<spring:message code="public.title.close"/><!-- 关闭 -->
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
	<table class="user_table" width="100%" layoutH="60" border="0">
		<tr>
			<c:if test="${affirmorListCnt > 0}">
				<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<c:if test="${affirmorListCnt == 0}">
				<td class="td_title" width="10%" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
				决裁等级
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
				决裁者
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
				决裁情况
			</td>
			<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
				决裁批注
			</td>
		</tr>
		<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" width="15%" style="text-align: center">
					<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
						<!--<font color="blue">未决裁</font>-->
						未决裁
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
						<!--<font color="green">已通过</font>-->
						已通过
					</c:if>
					<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
						<!--<font color="red">已否决</font>-->
						已否决
					</c:if>
				</td>
				<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
				<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmorListCnt == 0}">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
		</c:if>
		<tr>
			<td class="td_type" colspan="6"><br/></td>
		</tr>
		<tr>
			<c:if test="${checkorListCnt > 0}">
				<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<c:if test="${checkorListCnt == 0}">
				<td class="td_title" width="10%" style="text-align: center" rowspan="${3 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<td class="td_title" width="15%" style="text-align: center"><!-- Type -->
				Type
			</td>
			<td class="td_title" width="30%" style="text-align: center" colspan="2"><!-- Requests -->
				Requests
			</td>
			<td class="td_title" width="45%" style="text-align: center" colspan="2"><!-- Reviewed -->
				Reviewed
			</td>
		</tr>
		
		<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
			<tr>
				<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
				<td class="td_type" width="30%" style="text-align: center" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;${checkor.AFFIRM_FLAG}
				</td>
				<td class="td_type" width="45%" style="text-align: center" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;${checkor.CHECK_FLAG}
				</td>
			</tr>	
			<tr>
				<td class="td_type" width="30%" style="text-align: center" colspan="2">
					<textarea name="affirmRemark" cols="50" rows="2">[Request]：${checkor.CHECK_REASON}</textarea>
				</td>
				<td class="td_type" width="45%" style="text-align: center" colspan="2">
					<textarea name="checkRemark" cols="50" rows="2">[Check]：${checkor.CHECK_CONTENT}</textarea>
				</td>
			</tr>			
		</c:forEach>	
		
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_title" width="15%" style="text-align: center" rowspan="2">Public</td>
				<td class="td_type" width="30%" style="text-align: left" colspan="2">
					无
				</td>
				<td class="td_type" width="45%" style="text-align: left" colspan="2">
					无
				</td>
			</tr>	
			<tr>
				<td class="td_type" width="30%" colspan="2">
					<textarea name="affirmRemark" cols="50" rows="2" colspan="2">[Request]：</textarea>
				</td>
				<td class="td_type" width="45%" colspan="2">
					<textarea name="checkRemark" cols="50" rows="2" colspan="2">[Check]：</textarea>
				</td>
			</tr>		
		</c:if>	
	</table>
</div>