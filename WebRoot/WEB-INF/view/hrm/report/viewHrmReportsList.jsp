<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function  openReportwin(){
    var $form = $("#viewHrmReprot");

    var choseVal = '';
    $("#layout1 input:radio").each(function() {
		if (this.checked) {
			choseVal = this.value;
		}
	});
   $("#onReportcode").attr("href","/hrm/report/viewHrmReportCondition?checkVal="+choseVal);
        
}

function callPD(){
	

	
	$("#callPD").submit();	
    
	
}
function navTabSearch_re(form) {

	var $form = $("#callPD");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;

}
</script> 

<div class="pageHeader">
	<form id="callPD"  onsubmit="return navTabSearch_re(this);"  action="/hrm/report/callPD" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
		<input type="text" id="AR_DATE" name="AR_DATE" class="Wdate required"
							readonly="true" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" />
				</td>
			 
				<td></td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
			<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
		      	<li>
					<div >
					     <div  >
						 <span class="button" onclick="callPD()">
							<span class="button">点击生成人员日报</span></span> 
						</div>
					</div>
				</li>
			</c:if>
			<li>
					<div  >
					     <div  >
							<a class="button" id="onReportcode" onclick="openReportwin()" target="dialog" width="1000" height="420"  mask="true">
							<span>打开</span></a>
						</div>
					       
							
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>

 
<div class="pageContent" >
<form id="viewHrmReprot" name="viewHrmReprot" action="" method="post">
   <div id="layout1">
	<table class="table" width="100%" layoutH="90" nowrapTD="false">
			<thead>
			<tr>
			    <th width="100"><spring:message code="hr.report.viewreport.title.table"/><!--表名称--></th>
				<th width="180"><spring:message code="hr.report.viewreport.condition"/><!--条件--></th>
			</tr>
			<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
			<tr><td style="text-align:left"><input type="radio" id="c1" name="c1"
									value="report1" />人员日报现况</td> <td style="text-align:left">按日期检索</td> </tr>
			</c:if>
			<tr><td style="text-align:left"><input type="radio" id="c1" name="c1"
									value="report2" />续签合同意向书</td> <td style="text-align:left">按照员工（可批量上传多员工）、区间（到期日）搜索</td> </tr>
			<tr><td style="text-align:left"><input type="radio" id="c1" name="c1"
									value="report3" />续签合同意向书-10年以上</td> <td style="text-align:left">按照员工（可批量上传多员工）、区间（到期日）搜索</td> </tr>
			<tr><td style="text-align:left"><input type="radio" id="c1" name="c1"
									value="report4" />非正规退社证明单</td> <td style="text-align:left">按照员工（可批量上传多员工）、区间（退社日）、退社原因搜索
			</td> </tr>
			<tr><td style="text-align:left"><input type="radio" id="c1" name="c1"
									value="report5" />社员试用期结束能力评价表</td> <td style="text-align:left">按照员工（可批量上传多员工）、区间（转正日）搜索
			</td> </tr>
		</thead>
		<tbody>
		</tbody>
	</table> 
	</div>
</form>
</div>
