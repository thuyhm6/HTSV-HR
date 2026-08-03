 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>考勤查看&gt;个人休假</title>
	</head>
	  <% 
      
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=niannianjia.xls");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0"); 
     %>

	<body>
		 <table width="100%" border="1" cellspacing="0" cellpadding="2"
							bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
							style="padding: 2px 2px 2px 2px;">
							<tr align="center" bgcolor="#F5F5F5">
                                <td  nowrap="nowrap" rowspan="2">
                                    	工号
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                   	 姓名
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	部门
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	职系
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	入社日
                                </td>
                                <td  nowrap="nowrap" colspan="3">
	                                                                                                   工龄明细
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	发生基准月
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	对象与否
                                </td>
                                
                                  <td class="info_title_01" nowrap="nowrap" rowspan="2">
                                    	影响年休假的休假天数
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	基本休假
                                </td>
                                <td  nowrap="nowrap" rowspan="2">
                                    	基本扣除
                                </td>
                                 
                                <td  nowrap="nowrap" rowspan="2">
                                    	年假合计
                                </td>
                            </tr>
                            <tr align="center" bgcolor="#F5F5F5">
                                <td  nowrap="nowrap">
                                    	本公司
                                </td>
                                <td  nowrap="nowrap">
                                    	其他公司
                                </td>
                                <td  nowrap="nowrap">
                                    	总计
                                </td>
                            </tr>
							<c:forEach items="${arVacationUpdateYearList}" var="oneResult"
                                varStatus="i">
                                <tr align="center" >
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.EMPID}
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.LOCAL_NAME} 
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
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                       ${oneResult.INSIDE}月
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                         ${oneResult.OUTSIDE}月
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.OUTSIDE+oneResult.INSIDE}月
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${vac_idb}-12-20
                                    </td>
                                  <td width="10%" height="30"
                                        nowrap="nowrap">
                                         <c:if test="${oneResult.ACTIVITY==0||oneResult.ACTIVITY==1}">
                                                   Y</c:if> 
                                                   <c:if test="${oneResult.ACTIVITY==2}">
                                                   N</c:if> 
                                    </td>
                                    <td width="10%" height="30"
                                        nowrap="nowrap">
                                        ${oneResult.LEAVE_TOTAL}
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.TOT_VAC_CNT}&nbsp;天
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.LAST_YEAR_VAC}&nbsp;天
                                    </td>
                                    <td width="10%"  height="30"
                                        nowrap="nowrap">
                                        ${oneResult.TOT_VAC_CNT+oneResult.LAST_YEAR_VAC}&nbsp;天
                                    </td>
                                </tr>
                            </c:forEach>
						</table>
	</body>
</html>