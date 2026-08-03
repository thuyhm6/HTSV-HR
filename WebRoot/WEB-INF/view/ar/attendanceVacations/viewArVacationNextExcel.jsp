 <%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
	<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>考勤查看&gt;个人休假</title>
	</head>
	  <% 
      
        response.setHeader("Content-Type", "application/vnd.ms-excel; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=yiniannianjia.xls");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=0"); 
     %>

	<body>
		 <table width="100%" border="1" cellspacing="0" cellpadding="2"
							bordercolorlight="#E7E7E7" bordercolordark="#FFFFFF"
							style="padding: 2px 2px 2px 2px;">
							<tr align="center" bgcolor="#F5F5F5">
                                <th >
                                    	工号
                                </th>
                                <th >
                                    	姓名
                                </th>
                                <th >
                                    	部门
                                </th>
                                <th >
                                    	职系
                                </th>
                                <th >
                                    	入社日
                                </th>
                                <th >
                                    	年假数
                                </th>
                            </tr>
							<c:forEach items="${arVacationEmpList}" var="oneResult" varStatus="i">
                                <tr align="center" onclick="band('#f4f7fa','black')">
                                    <td nowrap="nowrap">
                                        ${oneResult.EMPID}
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.LOCAL_NAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.DEPTNAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.POST_COEFNAME} 
                                        &nbsp;
                                    </td>
                                    <td nowrap="nowrap">
                                        ${oneResult.DATE_STARTED}
                                    </td>
                                    <td nowrap="nowrap">
                                         ${oneResult.BENNIAN}天
                                    </td>
                                </tr>
                            </c:forEach>
						</table>
	</body>
</html>