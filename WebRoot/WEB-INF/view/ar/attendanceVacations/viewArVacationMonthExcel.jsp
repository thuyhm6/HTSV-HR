 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><!--考勤查看--><spring:message code='ar.viewArVacationMonthExcel.KAOQINCHAKAN.b' />&gt;<!--个人休假--><spring:message code='ar.viewArVacationMonthExcel.GERENXIUJIA.b' /></title>
	</head>
	  <% 
      
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=yuenianjia.xls");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0"); 
     %>

	<body>
		 <table width="100%" border="1" cellspacing="0" cellpadding="2"
							bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
							style="padding: 2px 2px 2px 2px;">
							<tr align="center" bgcolor="#F5F5F5">
								<td  nowrap="nowrap" rowspan="2">
									<!--工号--><spring:message code="ess.infoApply.EMP_ID" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--姓名--><spring:message code="ess.infoApply.NAME" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--部门--><spring:message code="ess.infoApply.DEPT" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--职系--><spring:message code="is.objmanagement.title.zhixi" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--入社日--><spring:message code="ess.empInfo.date_of_agency" />
								</td>
								<td  nowrap="nowrap" colspan="3">
									<!--工龄明细--><spring:message code="ar.viewArVacationMonthExcel.GONGLINGMINGXI.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--发生基准月--><spring:message code="ar.viewArVacationMonthExcel.FASHENGJIZHUNYUE.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--对象与否--><spring:message code="ar.viewArVacationMonthExcel.DUIXIANGYUFOU.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--基本休假--><spring:message code="ar.viewArVacationMonthExcel.JIBENXIUJIA.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--基本扣除--><spring:message code="ar.viewArVacationMonthExcel.JIBENKOUCHU.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--新标准--><spring:message code="ar.viewArVacationMonthExcel.XINBIAOZHUN.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--新扣除--><spring:message code="ar.viewArVacationMonthExcel.XINKOUCHU.b" />
								</td>
								<td  nowrap="nowrap" rowspan="2">
									<!--年假合计--><spring:message code="ar.viewArVacationMonthExcel.NIANJIAHEJI.b" />
								</td>
							</tr>
							<tr align="center" bgcolor="#F5F5F5">
								<td  nowrap="nowrap">
									<!--本公司--><spring:message code="ar.viewArVacationMonthExcel.BENGONGSI.b" />
								</td>
								<td  nowrap="nowrap">
									<!--其他公司--><spring:message code="ar.viewArVacationMonthExcel.QITAGONGSI.b" />
								</td>
								<td  nowrap="nowrap">
									<!--总计--><spring:message code="liang.hr.viewWorkInfo.title.zongji" />
								</td>
							</tr>
							<c:forEach items="${vacationEmpList}" var="oneResult"
								varStatus="i">
								<tr align="center">
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.EMPID}
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.CHINESENAME} 
										&nbsp;
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										 ${oneResult.DEPTNAME} 
										&nbsp;
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										 ${oneResult.POST_COEFNAME} 
										&nbsp;
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.DATE_STARTED}
									</td><td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.DAY_N}<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
                                    </td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.DAY_W}<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.DAY}<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${year}-${month}-20
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										Y
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.VAC_STANDARD_OLD}&nbsp;<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.VAC_DEDUCT_OLD}&nbsp;<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.VAC_STANDARD_NEW}<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.VAC_DEDUCT_NEW}&nbsp;<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
									<td width="10%"  height="30"
										nowrap="nowrap">
										${oneResult.TOT_VAC_CNT}&nbsp;<!--天--><spring:message code="ar.viewsummaryparameteritem.title.day" />
									</td>
								</tr>
							</c:forEach>
						</table>
	</body>
</html>