<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function downloadExl(url){
		$('#searchViewPaEmpAccountForm').attr("action",url) ;
		$('#searchViewPaEmpAccountForm').attr("onsubmit",'') ;
		$('#searchViewPaEmpAccountForm').submit() ;
		$('#searchViewPaEmpAccountForm').attr("action",'navTabSearch') ;
		$('#searchViewPaEmpAccountForm').attr("onsubmit",'return navTabSearch(this);') ;
	}
	function searchPopPaEmpAccount(flag){
		var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
		var refreshUrl = 'navTabSearch?noParam=Y' ;
		var refreshMenuCode = 'pa1301' ;
		var refreshMenuName = encodeURI(encodeURI('账户信息')) ;
		$('#searchPopPaEmpAccountId',navTab.getCurrentPanel()).attr('href','/pa/paView/viewPaChainStaff?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name
				+'&refreshUrl='+refreshUrl+'&refreshMenuCode='+refreshMenuCode+'&refreshMenuName='+refreshMenuName);
		if(flag == 'onkeyup')
			$("#searchPopPaEmpAccountId",navTab.getCurrentPanel()).click();
	}
	function addPaEmpAccountInfo(url){
		$.pdialog.open(url, 
				"pa0818_add", "账户添加", {width:1000,height:600,mask:true});
	}
</script>
<div  id="emergencyAddress" style="width: 98.5%;margin-left:auto;margin-right:auto;">
<form id="informationSearch" method="post" action="/pa/paView/viewPaChainStaff" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>工资区分</td>
					<td><ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE"
							selected="${EMP_TYPE_CODE}" parentNo="13864"
							cnpyID="${LoginUser.cpnyId}" limit="all"/></td>
					<td>支付日期</td>
					<td><input type="text" id="seach_START_DATE_STARTED"
						name="seach_START_DATE_STARTED" class="date required"
						format="yyyy-MM-dd" readonly="true" value="${START_DATE_STARTED}"
						size="20" />
							</td>
					<td>-</td>
					<td><input type="text" id="seach_END_DATE_STARTED"
						name="seach_END_DATE_STARTED" class="date required"
						format="yyyy-MM-dd" readonly="true" value="${END_DATE_STARTED}"
						size="20" /></td>

					<td>以前工资支付日</td>
					<td><input type="text" id="seach_START_DATE_STARTED"
						name="seach_START_DATE_STARTED" class="date required"
						format="yyyy-MM-dd" readonly="true" value="${START_DATE_STARTED}"
						size="20" />
							</td>
					<td>-</td>
					<td><input type="text" id="seach_END_DATE_STARTED"
						name="seach_END_DATE_STARTED" class="date required"
						format="yyyy-MM-dd" readonly="true" value="${END_DATE_STARTED}"
						size="20" /></td>

				</tr>
			</table>
			<div class="subBar">
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
						<a class="buttonActive" onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=11')" href="#" >
							<span>导出到Excel</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div class="pageContent">

	<div id="viewArBaseEmpInfoList" style="float:left; display:block; overflow:auto;width:720px; height:90px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:1
		<table class="table"  id="daTable" style="width:100%;">
			<thead>
				
				<tr>
					<th> 工资人员类型</th>
					<th>前月</th>
					<th>当月</th>
					<th >增减人员</th>
				</tr>
			</thead>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">正式工资</td>
						<td style="text-align:left">645</td>
						<td class='td_center' >688</td>
						<td class='td_center' >43</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">合计</td>
						<td style="text-align:left">645</td>
						<td class='td_center' >688</td>
						<td class='td_center' >43</td>
					</tr>

		</table>
	</div>

	<div id="viewArBaseEmpInfoList" style="float:left; display:block; overflow:auto;width:320px; height:130px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:5
		<table class="table"  id="daTable" style="width:100%;">
			<thead>
				
				<tr>
					<th> 增减人员区分</th>
					<th>增减人员</th>
				</tr>
			</thead>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">招聘</td>
						<td style="text-align:left">22</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">复职</td>
						<td style="text-align:left">0</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">事业场变更</td>
						<td style="text-align:left">0</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">其他</td>
						<td style="text-align:left">40</td>
					</tr>

		</table>
	</div>

	<div id="viewArBaseEmpInfoList" style="float:left; display:block; overflow:auto;width:320px; height:130px; border:solid 1px #CCC; line-height:21px; background:#fff">
		Total:5
		<table class="table"  id="daTable" style="width:100%;">
			<thead>
				
				<tr>
					<th> 增减人员区分</th>
					<th>增减人员</th>
				</tr>
			</thead>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">辞退</td>
						<td style="text-align:left">19</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">停职</td>
						<td style="text-align:left">0</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">事业场变更</td>
						<td style="text-align:left">0</td>
					</tr>
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">其他</td>
						<td style="text-align:left">0</td>
					</tr>

		</table>
	</div>


</div>
	<table class="table" width="100%"  layoutH="130" targetType="dialog" >
	<thead>
			<tr>
				<th>No.</th>
				<th>姓名</th>
				<th>工号</th>
				<th>部门名称</th>
				<th>职级</th>
				<th>主要业务</th>
				<th>S-BANK</th>
				<th>职责代码</th>
				<th>入社日</th>
				<th>入社区分</th>
				<th>复职日</th>
				
			</tr>
	</thead>
		<c:forEach items="${paEmpAccountList}" var="EmpAccount" varStatus="i">
			<tr >
				<td style="text-align: center">${i.count}</td>
				<td style="text-align: center">${EmpAccount.LOCAL_NAME}</td>
				<td style="text-align: center">${EmpAccount.EMPID}</td>
				<td style="text-align: center">${EmpAccount.DEPTNAME}</td>
				<td style="text-align: center">${EmpAccount.POST_GRADE}</td>
				<td style="text-align: center">${EmpAccount.DATE_STARTED}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_TYPE}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_ADDRESS}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_NO}</td>
				<td style="text-align: center">${EmpAccount.ACCOUNT_NAME}</td>
				<td style="text-align: center">${EmpAccount.CREATED_BY}</td>
				
			</tr>
		</c:forEach>
	</table>
