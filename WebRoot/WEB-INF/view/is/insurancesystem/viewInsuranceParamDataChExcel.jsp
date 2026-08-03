 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

   <%
		response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=theIsParamDataInfo.xls");
		response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0"); 
   %>
	<table width="100%" border="2" align="center" cellpadding="0" cellspacing="0" style="padding: 2px 2px 2px 2px;border-collapse:collapse">
		<tr>
	    	<td align="center" colspan="9">
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
			<th width="10%"><!--大区编码-->
				大区编码
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
			<th width="25%"><!--备注-->
				备注
			</th>
		</tr>
		<c:forEach items="${isParamDataList}" var="isData" varStatus="i" >			
			<tr target="sid" rel="${isData.DATA_NO }">
				<td style="text-align:center">${i.index+1 }</td>
				<td style="text-align:center">${isData.CPNY_ID }</td>
				<td style="text-align:center">${isData.PAY_AREA_CD }</td>
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
				<td style="text-align:left">${isData.REMARK }</td>	
			</tr>			
		</c:forEach>		
	</table>  