<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
	function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_KEY=$("#seach_KEYTAPE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEYTAPE",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_RECORD_TEST_DATE= $("#seach_RECORD_TEST_DATE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_RECORD_TEST_DATE",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ar/recordTestList/viewRecordTestList?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_RECORD_TEST_DATE="+seach_RECORD_TEST_DATE+"&seach_KEYTAPE="+seach_KEYTAPE);
}

</script>
<div class="pageHeadeeckr">
	<form id="viewRecordTestList" onsubmit="return navTabSearch(this);" action="/ar/recordTestList/viewRecordTestList" rel="pagerForm" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="15%" style="text-align: right">
					 <spring:message code="public.title.deptName"/><!-- 部门 -->：
				</td>
				<td widht="15%">
					 <ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>		
				<td width="15%" style="text-align: right">
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->：
				</td>						
				<td width="15%">
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>	
				<td width="15%" style="text-align: right">
                     	<spring:message code="ess.recordTest.title.RECORD_TEST_DATE"/>：
                </td>			     
				<td width="25%">
				    <input type="text" id="seach_RECORD_TEST_DATE" name="seach_RECORD_TEST_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${RECORD_TEST_DATE}"/> <a class="inputDateButton" href="javascript:;"></a>
				</td> 	
				<td width="15%" style="text-align: right"> 
                      	<spring:message code="ar.viewcycle.title.zhuangtai"/>： 
                 </td>			      
 				<td width="15%"> 
 				   <select name="seach_KEYTAPE" id= "seach_KEYTAPE">
 				   <option value="0"  ${KEYTAPE=='0' ? 'selected' : '' }>
 				   		全部
                     </option>
                     <option value="1"  ${KEYTAPE=='1' ? 'selected' : ''}>
                                               未打卡
                     </option>
                     <option  value="2" ${KEYTAPE=='2' ? 'selected' : ''}>
                                                 已打卡
                     </option>
                   </select>
 				</td> 	 
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent">
							    <button type="submit">
							       <spring:message code="public.title.search"/><!-- 检索 -->
							    </button>
						        </div>
						        </div>
						    </li>
						</ul>
					</div>
				</td>	
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent" >
	<table class="table" width="100%" layoutH="138" nowrapTD="false">
		<thead>
			<tr>
			    <th style="text-align: center" width="60"><spring:message code="sys.affirm.title.indexNum"/><!--序号--></th>
				<th style="text-align: center" width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th style="text-align: center" width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th style="text-align: center" width="120"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th style="text-align: center" width="120"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				
				<th style="text-align: center" width="160"><spring:message code="ess.recordTest.title.RECORD_TEST_DATE"/></th>		
				<th style="text-align: center" width="100"><spring:message code="ess.recordTest.title.RECORD_TEST_ADD"/></th>				
				<th style="text-align: center" width="80"><spring:message code="ess.recordTest.title.RECORD_TEST_REMARK"/><!--备注--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${viewRecordTestList}" var="clock" varStatus="i">			
				<tr target="sid" rel="${clock.PERSON_ID}">
				    <td style="text-align: center">${i.index+1 }</td>
					<td style="text-align: center">${clock.EMPID}</td>
					<td style="text-align: center">${clock.LOCAL_NAME}</td>
					<td style="text-align: center">${clock.DEPT_NAME}</td>
					<td style="text-align: center">${clock.POSITION_NAME}</td>
					
					<td style="text-align: center">${clock.RECORD_TEST_DATE}</td>
					<td style="text-align: center">${clock.RECORD_TEST_ADD}</td>
					<td style="text-align: center">${clock.RECORD_TEST_REMARK}</td>
						
				</tr>			
			</c:forEach>
		</tbody>
	</table>
    <div id="arMacRecordApplyFullDescpView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
    <c:set value="/ar/recordTestList/viewRecordTestList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>