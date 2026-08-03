<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function exportImport0517(a){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewExcelImportResult0517");
  	alertMsg.confirm(title, {okCall: function(){ doImport0517Export($from);}});
}
function doImport0517Export(from){
  	var $from =$(from);
  	var url ="${base}/hrm/empinfo/viewImportTmpEmpResultExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}

function doTempEmpImport()
{
    var params = $("#viewExcelImportResult0517").serialize();
        
        alertMsg.confirm("确认导入临时职人员数据?",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/hrm/empinfo/createImportTmpEmpResult?" + params,
						success : function(result) {
							if (result == 1){
								alert("数据导入成功！");
							}else{
								alert("数据导入失败！");
							}
							//页面重载
							navTabSearch($("#viewExcelImportResult0517"));
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewExcelImportResult0517" name="viewExcelImportResult0517"
			action="/hrm/empinfo/viewImportTempEmpResultList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					出错与否：
				</th>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>>全部</option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>>是</option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>>否</option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="exportImport0517(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
							</button>
						</div>
					</div>						
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">	
	
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="submit"  onclick="doTempEmpImport()">
				<spring:message code="public.title.submit"/><!--提交--></button>
			</div></div></li>
			<li><div class="button"><div class="buttonContent">
				<button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button>
			</div></div></li>
		</ul>
	</div>	
		
	<table class="table" width="300%" layoutH="206">
		<thead>
			<tr>
				<th width="30">Line<!--excel行号--></th>
				<th>法人代码</th>
				<th>部门 代码</th>
				<th>法人入职日期</th>
				<th>入职类型</th>
				<th>中文姓名</th>
				<th>英文姓名</th>
				<th>试用期开始日期</th>
				<th>试用期结束日期</th>
				<th>试用期比例%</th>
				<th>性别</th>
				<th>身份证号</th>
				<th>最终学历</th>
				<th>生日</th>
				<th>ID卡号</th>
				<th>手机号码</th>
				<th>邮箱</th>
				<th>户口性质</th>
				<th>户口所在地</th>
				<th>工资级号</th>
				<th>工资级号等级</th>
				<th>基本工资</th>
				<th>变动工资</th>
				<th>开户行</th>
				<th>银行账号</th>
				<th>福利地区</th>
				<th>人员类型(CHR)</th>
				<th>工作地区</th>
				<th>工作类型(CHR)</th>
				<th>班号</th>
				<th>促销员所属</th>
				<th>星级级别</th>
				<th>产品</th>
				<th>兼卖产品</th>
				<th>职务</th>
				<th width="100"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${item}" var="item" varStatus="i">			
				<tr>
					<td class='td_right'>${item.LINE_ID}</td>
					<td>${item.CPNY_ID}</td>
					<td>${item.DEPTNO}</td>
					<td><fmt:formatDate value="${item.JOIN_COMPANY_DATE}" pattern="yyyy-MM-dd" /></td>
					<td>${item.JOIN_TYPE_CODE}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.CHINESE_PINYIN}</td>
					<td><fmt:formatDate value="${item.PROB_STRT_DATE}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${item.END_PROBATION_DATE}" pattern="yyyy-MM-dd" /></td>
					<td>${item.PROB_PAY_RAT}</td>
					<td>${item.SEXCODE}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.FINAL_DEGREE_CODE}</td>
					<td><fmt:formatDate value="${item.DOB}" pattern="yyyy-MM-dd" /></td>
					<td>${item.IDCARD_ADDR}</td>
					<td>${item.CELLPHONE}</td>
					<td>${item.EMAIL}</td>
					<td>${item.REG_TYPE_CODE}</td>
					<td>${item.REG_PLACE}</td>
					<td>${item.PAY_GRADE}</td>
					<td>${item.PAY_STEP}</td>
					<td>${item.BASE_PAY}</td>
					<td>${item.VARB_PAY}</td>
					<td>${item.CARD_NAME}</td>
					<td>${item.CARD_NO}</td>
					<td>${item.INSRAREA_ID}</td>
					<td>${item.EMP_TYPE_CODE}</td>
					<td>${item.WORK_AREA}</td>
					<td>${item.PROMTR_WORK_TP}</td>
					<td>${item.SHIFT_NO}</td>
					<td>${item.PROMTR_TP}</td>
					<td>${item.STAR_TP}</td>
					<td>${item.PROD_TP}</td>
					<td>${item.PROD_ADD}</td>
					<td>${item.JOB_TITLE_CD}</td>
					<td>${item.UPLOAD_ERROR_MSG}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<c:set value="/hrm/empinfo/viewImportTempEmpResultList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>