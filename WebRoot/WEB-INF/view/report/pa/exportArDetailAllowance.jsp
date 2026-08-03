<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitAllowance() {

	$("#exportArDetailAllowanceReport").submit();

}
</script>
<div class="pageHeader">
	<form id="exportArDetailAllowanceReport"
		action="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=205" method="post">
		<div class="searchBar">

			<table class="searchContent">
				<tr>
					<td width="10%" style="text-align: center">
						工资月：
					</td>
					<td width="20%">
                        <input name="AR_MONTH_STR" class="required" onClick="WdatePicker({dateFmt:'yyyyMM'})" id=""AR_MONTH_STR"" type="text" value=""/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
					    <div class="buttonActive">
							<div class="buttonContent">
								<button>
									导出到Excel
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>