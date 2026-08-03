<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 
<h1>
	月间人力报告
</h1>
<div class="pageHeader">
	<form action="/report/ar/viewHrPersonPowerListExport" method="post">
		<input type="hidden" name="WM_FLAG" value='1'>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<input type="text" name="seach_FROM_DATE" class="Wdate required"
							value="${FROM_DATE}" readonly="true"
							onClick="WdatePicker({dateFmt:'yyyy/MM'})" />
					</td>
					<td>
						--
						<input type="text" name="seach_TO_DATE" class="Wdate required"
							value="${TO_DATE}" readonly="true"
							onClick="WdatePicker({dateFmt:'yyyy/MM'})" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button>
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<h1>
	周间人力报告
</h1>
<div class="pageHeader">

	<form action="/report/ar/viewHrPersonPowerListExport" method="post">
		<input type="hidden" name="WM_FLAG" value='2'>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<input type="text" name="seach_TO_DATE_W" class="Wdate required"
							value="${TO_DATE_W}" readonly="true"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button>
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

