<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function downloadExl(url){
		$('#searchViewPaEmpAccountForm').attr("action",url) ;
		$('#searchViewPaEmpAccountForm').attr("onsubmit",'') ;
		$('#searchViewPaEmpAccountForm').submit() ;
		$('#searchViewPaEmpAccountForm').attr("action",'/pa/workManagement/viewPaEmpAccount') ;
		$('#searchViewPaEmpAccountForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
	function searchPopPaEmpAccount(flag){
		var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
		var refreshUrl = '/pa/workManagement/viewPaEmpAccount?noParam=Y' ;
		var refreshMenuCode = 'pa0818' ;
		var refreshMenuName = encodeURI(encodeURI('<spring:message code="hr.viewCondSql.title.ZHANGHUXINXI" />')) ;//账户信息
		$('#searchPopPaEmpAccountId',navTab.getCurrentPanel()).attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name
				+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName);
		if(flag == 'onkeyup')
			$("#searchPopPaEmpAccountId",navTab.getCurrentPanel()).click();
	}
	
	function addPaEmpAccountInfo(url){
		$.pdialog.open(url, 
				"pa0818_add", "<spring:message
				code='pa.addPaEmpAccount.ZHANGHUTIANJIA.C' />", {width:800,height:400,mask:true}); 
	}
	
	function excelimport_pa0818(){
		$("#importExcelDialog_pa0818").attr('href','/pa/excelImport/importExcelData?importFunName=/importPaemp');
		$("#importExcelDialog_pa0818").click();
	}
</script>
<a id="importExcelDialog_pa0818"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0818" href="#" target="navTab" mask="true"><span style="display:none;"><!--导入结果 --><spring:message code="ess.title.DAORUJIEGUO" /></span></a>
<div class="pageHeader">
	<form id="searchViewPaEmpAccountForm" onsubmit="return navTabSearch(this);"
		action="/pa/workManagement/viewPaEmpAccount" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input
						type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)searchPopPaEmpAccount('onkeyup');"/>
						</td>
						<td>
						<a class="btnLook" id="searchPopPaEmpAccountId" onclick="searchPopPaEmpAccount()" href="#" lookupGroup="person">
						</a>
						
					</td>
					<td>${empInfoShow }
					</td>
				</tr>
				<tr>
					<td><!--银行类型 --><spring:message code="pa.viewPaEmpAccount.YINHANGLEIXING.C" /></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_ACCOUNT_TYPE"
							selected="${ACCOUNT_TYPE}" parentNo="14015883"
							cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><!--员工类型 --><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME" /></td>
					<td><ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE"
							selected="${EMP_TYPE_CODE}" parentNo="13864"
							cnpyID="${LoginUser.cpnyId}" limit="all"/></td>
					<td><!--入职日期 --><spring:message code="hr.viewPersonalInfo.title.ENTRY_DATE" /></td>
					<td><input type="text" id="seach_START_DATE_STARTED"
						name="seach_START_DATE_STARTED" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${START_DATE_STARTED}"
						size="20" />
						-<input type="text" id="seach_END_DATE_STARTED"
						name="seach_END_DATE_STARTED" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${END_DATE_STARTED}"
						size="20" /></td>
				</tr>
				<tr>
					<td><!--部门 --><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" /></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="${limit}" id="seach_DEPTNO"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="${limit}" id="seach_DEPTNO" selected="${DEPTNO}"/>
					</td>
					<td><!--任职状态--><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
					<td><ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE"
							selected="${EMP_OFFICE}" parentNo="15118"
							cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><!--账号条件检索--><spring:message code="pa.viewPaEmpAccount.ZHANGHAOTIAOJIANJIANSUO.C" /></td>
					<td>
					    <select name="search_null_NO">
					       <option value="" <c:if test="${null_NO eq ''}">selected="selected"</c:if>><!--请选择--><spring:message code="hr.viewCondSql.title.QINGXUANZE" /></option>
					       <option value="0" <c:if test="${null_NO eq '0'}">selected="selected"</c:if>><!--缺少任意账号--><spring:message code="pa.viewPaEmpAccount.QUESHAORENYIZHANGHAO.C" /></option>
					       <option value="1" <c:if test="${null_NO eq '1'}">selected="selected"</c:if>><!--缺少银行账号--><spring:message code="pa.viewPaEmpAccount.QUESHAOYINHANGZHANGHAO.C" /></option>
					       <option value="2" <c:if test="${null_NO eq '2'}">selected="selected"</c:if>><!--缺少保险号码--><spring:message code="pa.viewPaEmpAccount.QUESHAOBAOXIANHAOMA.b" /></option>
					       <c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					       <option value="3" <c:if test="${null_NO eq '3'}">selected="selected"</c:if>><!--缺少公积金账号--><spring:message code="pa.viewPaEmpAccount.QUESHAOGONGJIJINZHANGHAO.C" /></option>
					       </c:if>
					       <option value="4" <c:if test="${null_NO eq '4'}">selected="selected"</c:if>><!--缺少税号--><spring:message code="pa.viewPaEmpAccount.QUESHAOSHUIHAO.b" /></option>
					    </select>
					</td>
				</tr>
			</table>
			<div class="subBar" >
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"  
						onclick="addPaEmpAccountInfo('/pa/workManagement/addPaEmpAccount')" href="#" >
							<span><!--添加--><spring:message code="org.title.INSERT" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" title="<spring:message code="edu.systemManager.QUEDINGSHIFOUSHANCHU.a" />" callback="doAjaxDoneWithForm" href="/pa/workManagement/doDeletePaEmpAccountInfo?{paramInfo}" target="ajaxTodo">
							<span><!--删除--><spring:message code="org.title.DELETE" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/pa/workManagement/updatePaEmpAccountView?{paramInfo}" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><!--修改--><spring:message code="ess.empInfo.modify" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=11')" href="#" >
							<span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"  href="/pa/excelExport/downloadExcelPaEmp?file=paEmp"  >
							<span><!--模板下载--><spring:message code="ess.message.template_download" /></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" onclick="excelimport_pa0818()" href="#" >
							<span><!--Excel导入--><spring:message code="ess.infoApply.EXCEL_IN" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
				<th>No.</th>
				<th><!--工号--><spring:message code="ess.infoApply.EMP_ID" /></th>
				<th><!--姓名--><spring:message code="ess.infoApply.NAME" /></th>
				<th><!--部门名称--><spring:message code="public.title.empDeptName" /></th>
				<th><!--职级--><spring:message code="pa.insurance.title.postGrade" /></th>
				<th><!--入社日期--><spring:message code="display.emp.statistics.mes207" /></th>
				<th><!--银行类型--><spring:message code="pa.viewPaEmpAccount.YINHANGLEIXING.C" /></th>
				<th><!--银行账号--><spring:message code="rp.report.title.bankcardno" /></th>
				<th><!--开户行--><spring:message code="pa.wagebase.title.openAccountBanks" /></th>
				<th><!--保险号码--><spring:message code="pa.viewPaEmpAccount.BAOXIANHAOMA.b" /></th>
				<th><!--税号--><spring:message code="pa.viewPaEmpAccount.SHUIHAO.b" /></th>
				<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<th><!--公积金账号--><spring:message code="pa.viewPaEmpAccount.GONGJIJINZHANGHAO.C" /></th>
					<th><!--社保缴纳时间--><spring:message code="pa.viewPaEmpAccount.SHEBAOJIAONASHIJIAN.C" /></th>
					<th><!--公积金缴纳时间--><spring:message code="pa.viewPaEmpAccount.GONGJIJINJIAONASHIJIAN.C " /></th>
				</c:if>
				<th><!--创建者--><spring:message code="hrm.contract.creator" /></th>
				<th><!--创建时间--><spring:message code="hrm.contract.Creation_time" /></th>
				<th><!--变更者--><spring:message code="hrm.empinfo.UPDATED_BY" /></th>
				<th><!--变更时间--><spring:message code="hrm.empinfo.UPDATE_DATE" /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${paEmpAccountList}" var="EmpAccount"
			varStatus="i">
			<tr target="paramInfo" rel="PA_EMP_ACCOUNT_NO=${EmpAccount.PA_EMP_ACCOUNT_NO}&empBaseInfo=${EmpAccount.EMPID}/${EmpAccount.LOCAL_NAME}/${EmpAccount.DEPTNAME}">
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${EmpAccount.EMPID}</td>
				<td style="text-align: center">${EmpAccount.LOCAL_NAME}</td>
				<td style="text-align: center">${EmpAccount.DEPTNAME}</td>
				<td style="text-align: center">${EmpAccount.POST_GRADE}</td>
				<td style="text-align: center">${EmpAccount.DATE_STARTED}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_TYPE_NAME}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_NO}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_NAME}</td>
				<td style="text-align: center">${EmpAccount.SECURITY_NO}</td>
				<td style="text-align: center">${EmpAccount.TAX_NO}</td>
				<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<td style="text-align: center">${EmpAccount.FUND_NO}</td>
					<td style="text-align: center">${EmpAccount.SECURITY_PAY_DATE}</td>
					<td style="text-align: center">${EmpAccount.FUND_PAY_DATE}</td>
				</c:if>
				<td style="text-align: center">${EmpAccount.CREATED_BY}</td>
				<td style="text-align: center">${EmpAccount.CREATE_DATE}</td>
				<td style="text-align: center">${EmpAccount.UPDATED_BY}</td>
				<td style="text-align: center">${EmpAccount.UPDATE_DATE}</td>
			</tr>
		</c:forEach>
		<c:if test="${totalCount == 0 }">
			<tr>
				<td style="text-align: left"colspan="14"><!--没有任何数据--><spring:message code="pa.viewPaEmpAccount.MEIYOURENHESHUJU.C" /></td>
			</tr>
		</c:if>
		</tbody>
	</table>
	<c:set value="/pa/workManagement/viewPaEmpAccount" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>