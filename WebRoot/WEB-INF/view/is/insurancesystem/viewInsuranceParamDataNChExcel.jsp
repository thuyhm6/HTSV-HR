 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=theIsParamDataNChInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" style="padding: 2px 2px 2px 2px;border-collapse:collapse">
		<tr>
	    	<td align="center" colspan="8">
	    		<b>
	    			<font size="+2"><%--保险参数信息表--%>
	    	     		保险参数信息表
					</font>
	    		</b>
	    	</td>
	    </tr>
		<tr>
			<th width="5%">
		    	序号
		    </th>
			<th width="10%"><!--法人-->
				法人
			</th>
			<th width="10%"><!--福利地区-->
				福利地区
			</th>
			<th width="10%"><!--福利项目-->
				福利项目
			</th>
			
			<th width="10%"><!--地区比率-->
				地区比率
			</th>
			<th width="10%"><!--地区金额-->
				地区金额
			</th>
			<th width="10%"><!--启用状态-->
				启用状态
			</th>
			<c:if test="${defaultCpny eq 'LGEQA' }">
				<th width="10%"><!-- 四舍五入 -->
					四舍五入
				</th>
			</c:if>
			<th width="25%"><!--备注-->
				备注
			</th>
		</tr>
		<c:forEach items="${isParamDataList}" var="isData" varStatus="i" >			
			<tr target="sid" rel="${isData.DATA_NO }">
				<td style="text-align:center">${i.index+1 }</td>
				<td style="text-align:center">${isData.CPNY_ID }</td>
				<td style="text-align:center">${isData.INSRAREA_NAME }</td>
				<td style="text-align:center">${isData.INSURE_NAME }</td>
				
				<td style="text-align:right">${isData.INSURE_RATE }</td>
				<td style="text-align:right">${isData.INSURE_VALUE }</td>
				<td style="text-align:center">
					<c:if test="${isData.ACTIVITY eq '1'}">
						<font color="green">启用</font>
					</c:if>
					<c:if test="${isData.ACTIVITY eq '0'}">
						<font color="blue">未启用</font>
					</c:if>
				</td>
				<c:if test="${defaultCpny eq 'LGEQA' }">
					<td style="text-align:center">
					   	<c:if test="${isData.CARRY_WAY eq 'A' }">向上进一位</c:if>
						<c:if test="${isData.CARRY_WAY eq 'B' }">四舍五入，保留两位小数 </c:if>
						<c:if test="${isData.CARRY_WAY eq 'C' }">四舍五入后取整</c:if>
						<c:if test="${isData.CARRY_WAY eq 'D' }">向下取整</c:if>
						<c:if test="${isData.CARRY_WAY eq 'E' }">保留一位小数，第二位进1 </c:if>
						<c:if test="${isData.CARRY_WAY eq null }">未选择 </c:if>
						<c:if test="${isData.CARRY_WAY eq '' }">未选择 </c:if>
					</td><!-- 四舍五入 -->
					</c:if>
				<td style="text-align:left">${isData.REMARK }</td>	
			</tr>			
		</c:forEach>		
	</table>  