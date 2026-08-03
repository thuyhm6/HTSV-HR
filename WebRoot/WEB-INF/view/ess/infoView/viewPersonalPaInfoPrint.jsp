<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style media="print">.Noprint { DISPLAY: none }</style>
<style type="text/css">.line{border-top:1px solid ;border-left:1px solid ;}</style>
<style type="text/css">.line2{border-bottom:1px solid ;border-right:1px solid;}</style>
<%-- <table width="70%" border="1">
		<tr>
			<td width="50%">
				<div class="panel">
					<h1>
						<spring:message code="ess.viewpersonalpainfo.suodemingxi"/><!--所得明细-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" width="50%">
									<spring:message code="ess.viewpersonalpainfo.jibengongzi"/><!--基本工资-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].BASE_SALARY}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.bonusTitle"/><!--奖金-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].BONUS_SALARY}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.otTotalTitle"/><!--加班费合计-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].TOTAL_OT_FEE}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.paAddTitle"/><!--薪资追补-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].PA_ADD_TOTAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="td_type" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							
							<tr>
								<td class="td_title">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="td_type" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="td_title">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="td_type" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="td_title">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="td_type" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.getTotalTitle"/><!--所得合计-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].ADD_PRO}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="td_type" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
			<td width="50%">
				<div class="panel">
					<h1>
						<spring:message code="ess.viewpersonalpainfo.kongzhimingxi"/><!--扣除明细-->
					</h1>
					<div>
						<table width="100%" border="1" cellpadding="0" cellspacing="0"  class="user_table">
							<tr>
								<td class="td_title" width="50%">
									<spring:message code="ess.viewpersonalpainfo.gerensuodeshui"/><!--个人所得税-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].PERSONAL_INCOME_TAX}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.ENDOWMENTTitle"/><!--养老保险(个人)-->
								</td>
								<td class="td_type" style="text-align:right" width="50%">
									<fmt:formatNumber value="${miProList[0].ENDOWMENT_PERSONAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.MEDICALTitle"/><!--医疗保险(个人)-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].MEDICAL_PERSONAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.UNEMPLOYMENTTitle"/><!--失业保险(个人)-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].UNEMPLOYMENT_PERSONAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.ACCUMULATIONFUNDTitle"/><!--公积金(个人)-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].HOUSE_FUNDING_PERSONAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.SOCIALSECURITYAFTERTitle"/><!--个人社保追缴-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].SOCIAL_SECURITY_AFTER_PER}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.ACCUMULATIONFUNDAFTERTitle"/><!--个人公积金追缴-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].ACCUMULA_FUND_AFTERWARDS_PEL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.OTHERDEDUCTTitle"/><!--其他扣减-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].TAXABLE_DEDUCT_TOTAL}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="pa.title.message.DEDUCTTOTALTitle"/><!--扣减合计-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].MI_PRO}" pattern="#,##0.00"/>
								</td>
							</tr>
							<tr>
								<td class="td_title">
									<spring:message code="ess.viewpersonalpainfo.shifagongzi"/><!--实发工资-->
								</td>
								<td class="td_type" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].ADD_PRO - miProList[0].MI_PRO}" pattern="#,##0.00"/>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</td>
		</tr>
		<tr width="50%">
						<td><img src="\resources\images\button\paInfo_zh.jpg"/></td>
			
		</tr>
	</table>--%>
<table cellspacing="0" cellpadding="1" border="0">
	<tr><td><br><br><br></td></tr>
	<tr>
		<td colspan="3" align="right" class="Noprint"> 
           <input type="button" value="打印" onclick="javascript:window.print()" >  
         </td>
	</tr>
	<tr>
		<td colspan="3" align="center"><font size="5"><b>${essYear }<spring:message code="rp.report.title.year" /> ${essMonth }<spring:message code="rp.report.title.month" /> <spring:message code="liang.hr.viewPersonalInfo.title.PAYROLL"/> </b></font></td>
	</tr>
	<tr>
		<td> 
           &nbsp;
         </td>
	</tr>
	<tr>
		<td colspan="3"  align="center"> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:&nbsp;&nbsp;${addProList[0].DEPT_NAME }</td>
	</tr>
	<tr>
		<td colspan="3" align="right">${GIVE_DATE }</td>
	</tr>
	<tr>
		<td colspan="3"  align="center"> <spring:message code="hr.viewPersonalInfo.title.EMPID"/>:&nbsp;&nbsp;${view_EMPID }&nbsp;&nbsp;&nbsp;&nbsp; <spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>:&nbsp;&nbsp;${view_LOCALNAME }</td>
	</tr>
	<tr><td><br></td></tr>
	<tr>

    	<td>
			<table width="100%" border="0" cellpadding="2" cellspacing="0"  class="line">
							<tr>
								<td class="line2" width="50%">
									&nbsp;&nbsp;<spring:message code="ess.viewpersonalpainfo.jibengongzi"/><!--基本工资-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].BASE_SALARY}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.bonusTitle"/><!--奖金-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].BONUS_SALARY}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.otTotalTitle"/><!--加班费合计-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].TOTAL_OT_FEE}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.paAddTitle"/><!--薪资追补-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].PA_ADD_TOTAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							
							<tr>
								<td class="line2">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.getTotalTitle"/><!--所得合计-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].ADD_PRO}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
							</tr>
						</table>

		</td>
    	<td  align="right">&nbsp;</td>
    	<td  align="right">
			<table width="100%" border="0" cellpadding="2" cellspacing="0"  class="line">
							<tr>
								<td class="line2" width="50%">
									&nbsp;&nbsp;<spring:message code="ess.viewpersonalpainfo.gerensuodeshui"/><!--个人所得税-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].PERSONAL_INCOME_TAX}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.ENDOWMENTTitle"/><!--养老保险(个人)-->
								</td>
								<td class="line2" style="text-align:right" width="50%">
									<fmt:formatNumber value="${miProList[0].ENDOWMENT_PERSONAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.MEDICALTitle"/><!--医疗保险(个人)-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].MEDICAL_PERSONAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.UNEMPLOYMENTTitle"/><!--失业保险(个人)-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].UNEMPLOYMENT_PERSONAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.ACCUMULATIONFUNDTitle"/><!--公积金(个人)-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].HOUSE_FUNDING_PERSONAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.SOCIALSECURITYAFTERTitle"/><!--个人社保追缴-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].SOCIAL_SECURITY_AFTER_PER}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.ACCUMULATIONFUNDAFTERTitle"/><!--个人公积金追缴-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].ACCUMULA_FUND_AFTERWARDS_PEL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.OTHERDEDUCTTitle"/><!--其他扣减-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].TAXABLE_DEDUCT_TOTAL}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="pa.title.message.DEDUCTTOTALTitle"/><!--扣减合计-->&nbsp;&nbsp;
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${miProList[0].MI_PRO}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
							<tr>
								<td class="line2">
									&nbsp;&nbsp;<spring:message code="ess.viewpersonalpainfo.shifagongzi"/><!--实发工资-->
								</td>
								<td class="line2" style="text-align:right">
									<fmt:formatNumber value="${addProList[0].ADD_PRO - miProList[0].MI_PRO}" pattern="#,##0.00"/>&nbsp;&nbsp;
								</td>
							</tr>
						</table>





		</td>
	</tr>
	<tr><td><br></td></tr>
	<tr><td><br></td></tr>
	<tr>
    	<td colspan="3" class="xl64"><img src="${src }"/></td>
	</tr>
</table>
