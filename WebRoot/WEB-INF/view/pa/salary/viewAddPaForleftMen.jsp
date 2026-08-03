<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

		
		  
	<form name="updateArDetailForm" id="updateArDetailForm" method="post" action="/ar/attendanceMintenance/updateOrAddArDetail"
	 		 onsubmit="return cancelAgentTransValidateCallback(this, navTabAjaxDone);">
	 	
           <table class="table" width="100%" layoutH="206" >
				<thead>
					<tr>
						<th width="20" align="center" >
			           <input type="checkbox" class="checkboxCtrl" group="c1">
			            </th>
						
						<th width="20%"  align="center">
							<!-- 工号/姓名 --><spring:message code="public.title.empIdAndName"/>
							
						</th>
						
						
						
						<th width="15%"  align="center">
							<!-- 部门 --><spring:message code="public.title.deptName"/>
						</th>
						<th width="12%"  align="center">
							<spring:message code="pa.insurance.title.salaryMonthFor"/><!--支付月-->
						</th>
						<th width="12%"  align="center">
							<spring:message code="pa.insurance.title.salaryAdd"/><!--补发项目-->
						</th>
						<th width="12%"  align="center">
							<spring:message code="pa.insurance.title.salaryAddItem"/><!--补发项目明细-->
						</th>
						<th width="12%"  align="center">
							<spring:message code="pa.insurance.title.salaryAddData"/><!--补发金额-->
						</th>
						<th width="12%"  align="center">
							<spring:message code="pa.insurance.title.salaryMonthNow"/><!--发放月份-->
						</th>
						<th width="30%"  align="center">
							<!-- 备注 --><spring:message code="hr.viewBadArchives.title.REMARK"/>
						</th>
						
					</tr>
				</thead>
				
				<tbody>
				
					
	                       <c:forEach var="i" begin="0" end="9">
						<tr>
						   <td  align="center"  width="20">
				            <input type="checkbox" id="c1_${i}" name="c1" value="c1_${i}">
				            </td>
							
							<td  align="center"  width="20%">
								<input type="hidden" id="PERSON_ID_${i}" name="PERSON_ID_${i}" value=""/>
								<input type="text" id="EMP_ID_${i}" name="EMP_ID_${i}" value="" onkeydown="submitKeyClick_sy0130_add(this,'','','${request_navTabId}',event)" lookupGroup="person"/>
								<div   id="EMP_NAME_${i}" name="EMP_NAME_${i}"  lookupGroup="person"/> sdfsd</div>
								
							</td>
							<td  align="center"  width="15%">
								<input type="text"  id="DEPT_ID_${i}" name="DEPT_ID_${i}" lookupGroup="person" readonly>
							</td>
							<td   align="center" width="12%">
								<ait:date yearName="paYearZhifu_${i}" monthName="paMonthZhifu_${i}"/>
							</td>
							
							<td   align="center" width="12%">
								<select name="bigItem_${i}" class="select" id="bigItem_${i}">
								    <option value="">
								    全部
								    </option>
														
								    <option value="1"
								    >
								    工资项目
								    </option>
								    <option value="2"
								    >
								    保险项目
								    </option>
													
								    </select>
							</td>
							
							<td    align="center" width="12%">
								<select name="smallItem_${i}" id="smallItem_${i}" class="select">
								
								   <option value=""> 
			                             全部
			                          </option>
									<c:forEach items="${paitemlist}" var="itemlist" varStatus="j">
									<option value="${itemlist.ITEM_NO}"> 
			                              ${itemlist.CONTENT}
			                          </option>
								     </c:forEach>
								</select>
							</td>
							<td    align="center" width="12%">
								<input type="text" id="ITEM_DATA_${i}" name="ITEM_DATA__${i}" value=""/>
							</td>
							
							<td   align="center" width="12%" >
								<ait:date yearName="paYearFafang_${i}" monthName="paMonthFafang_${i}"/>
							</td>
							
							<td    align="center" width="30%">
								<input type="text" id="REMARK_${i}" name="REMARK_${i}" value=""/>
							</td>
							
						</tr>
					</c:forEach>
				
				</tbody>
				
			</table>
			<a id="onck" name="onck"  href="" lookupGroup="person"></a>
	 </FORM>
	
