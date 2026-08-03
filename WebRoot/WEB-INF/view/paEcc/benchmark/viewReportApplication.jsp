<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewInsuranceObject" name="viewInsuranceObject" onsubmit="" 
			action="" method="post" rel=>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<th width="25%" style="padding: 4px;">
						<spring:message code="display.paecc.yearmonth"/><!-- 年月-->
						<ait:date yearName="seach_insYear_jx00004" monthName="seach_insMonth_jx0004" yearSelected="" monthSelected=""/>
					</th>
					<th width="35%" style="padding: 4px;" align="center">
						<ul>
							<li style="float:left;padding-top:5px;">
					    		<spring:message code="display.paecc.pici"/>：<!--批次 -->
							</li>
							<li>
								<select></select>
							</li>
						</ul>
					</th>
				</tr>
				
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<a onclick="insertInsuranceCalculationObject()">
								<span>
									<spring:message code="display.paecc.sousuo"/><!-- 搜索 -->
								</span>
							</a>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="display.paecc.shenqing"/><!--申请-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="display.paecc.chakanaffirm"/><!--查看决裁者-->
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
	  <table width="100%"  class="table">
<thead>
  <tr>
    <th rowspan="2">区分</th>
    <th colspan="4">人员</th>
    <th colspan="4">经济补偿金</th>
    <th colspan="4">待通知金</th>
    <th colspan="4">课税</th>
    <th colspan="4">合计</th>
  </tr>
  <tr>
    <th>不续签合同</th>
    <th>劝告离职</th>
    <th>协议离职</th>
    <th>TOTAL</th>
    <th>不续签合同</th>
    <th>劝告离职</th>
    <th>协议离职</th>
    <th>TOTAL</th>
    <th>不续签合同</th>
    <th>劝告离职</th>
    <th>协议离职</th>
    <th>TOTAL</th>
    <th>不续签合同</th>
    <th>劝告离职</th>
    <th>协议离职</th>
    <th>TOTAL</th>
    <th>不续签合同</th>
    <th>劝告离职</th>
    <th>协议离职</th>
    <th>TOTAL</th>
  </tr>
 <thead>
  	<tr align="center">
    <th>合计</th>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
    <td>&nbsp;0</td>
  </tr>
  <tr>
    <th>附件</th>
    <td colspan="20">
		 <input id="PROVE_FILE_NAME_ESS0202" name="PROVE_FILE_NAME" type="text"  readonly/>
		 <a href="/ess/infoApply/proveFiledialog?FILE_NAME=PROVE_FILE_NAME_ESS0202&FILE_URL=PROVE_FILE_URL_ESS0202" target="dialog" mask="true" width="300" height="200" >
		 <input type="button" value="添加" /> </a>
		 <input id="PROVE_FILE_URL_ESS0202" name="PROVE_FILE_URL" type="hidden" />
	</td>
  </tr>
</table>

</div>