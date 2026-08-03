<%@ page contentType="text/html; charset=UTF-8"%>

<div id="basicinfo" onmouseout="change('basicinfo');"
	style="border: dashed 1px #cccccc;position: absolute;">
	<input type="hidden" id="basicinfoModel" name="basicinfoModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td align="right">
			<img src="/resources/images/title/top_1.gif" align="center"/>
			</td>
			<td align="left">
				<font size="2"><spring:message code="hrm.basicInfo"/></font>
			</td>						
		</tr>
	</table>
</div>	
<div id="photo" onmouseout="change('photo');"
	style="border: solid 1px #cccccc; position: absolute;">
 	<input type="hidden" id="photoModel" name="photoModel" value=""/>
	<table cellpadding="1" cellspacing="1" align="center">
		<tr>
			<td>
				<img src="/resources/photo/${basicInfo.EMPID}.jpg" width="100"
					height="120">
			</td>
		</tr>
	</table>
</div>
<div id="empid" onmouseout="change('empid');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="empidModel" name="empidModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.empid"/>
			</td>
			<td width="150" style="padding-left: 10px;">
				<input id="empID" name="empID" size="8"
					value="${basicInfo.EMPID }" />
			</td>
		</tr>
	</table>
</div>			
<div id="empname" onmouseout="change('empname');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="empnameModel" name="empnameModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.name"/>
			</td>
			<td width="150" style="padding-left: 10px;">
				<input id="empName" name="empName" size="8"
					value="${basicInfo.CHINESENAME }" />
			</td>
		</tr>
	</table>
</div>
<div id="department" onmouseout="change('department');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="departmentModel" name="departmentModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.dept"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.DEPARTMENT }
			</td>
		</tr>
	</table>
</div>
<div id="empdivision" onmouseout="change('empdivision');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="empdivisionModel" name="empdivisionModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.empDivision"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.EMP_TYPE_NAME }
			</td>
		</tr>
	</table>
</div>
<div id="postbevy" onmouseout="change('postbevy');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="postbevyModel" name="postbevyModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.postBevy"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.POSTGROUP }
			</td>
		</tr>
	</table>
</div>
<div id="postgrade" onmouseout="change('postgrade');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="postgradeModel" name="postgradeModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.postGrade"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.POSTGRADE }
			</td>
		</tr>
	</table>
</div>
<div id="empstatus" onmouseout="change('empstatus');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="empstatusModel" name="empstatusModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.EmpStatus"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.STATUS}
			</td>
		</tr>
	</table>
</div>
<div id="post" onmouseout="change('post');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="postModel" name="postModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.post"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.POST }
			</td>
		</tr>
	</table>
</div>
<div id="position" onmouseout="change('position');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="positionModel" name="positionModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.position"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.POSITION }
			</td>
		</tr>
	</table>
</div>
<div id="hiredate" onmouseout="change('hiredate');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="hiredateModel" name="hiredateModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.hireDate"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.JOINDATE }
			</td>
		</tr>
	</table>
</div>
<div id="promotiondate" onmouseout="change('promotiondate');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="promotiondateModel" name="promotiondateModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.promotionDate"/>
			</td>
			<td width="150" style="padding-left: 10px;">							
					${basicInfo.PROMOTIONDATE}
			</td>
		</tr>
	</table>
</div>
<div id="operation" onmouseout="change('operation');"
	style="border: solid 1px #cccccc; position: absolute;">
	<input type="hidden" id="operationModel" name="operationModel" value=""/>
	<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
		<tr>
			<td width="100" bgcolor="#cccccc" align="center">
				<spring:message code="hrm.operation"/>
			</td>
			<td width="400" style="padding-left: 10px;">							
					${basicInfo.BUSINESS }
			</td>
		</tr>
	</table>
</div>