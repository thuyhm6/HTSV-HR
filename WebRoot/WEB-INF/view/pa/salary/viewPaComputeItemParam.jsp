<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaComputeItemParam?type=1" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->：
					<input type="text" name="seach_ITEM_ID" value="${ITEM_ID }"/>
				</td>
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_KEY" value="${KEY }"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
	
<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>	
				<div layoutH="146" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href=""></a>
							<ul>
								 <c:forEach items="${proList}" var="item">
								 	<li><a href="/pa/salary/viewPaComputeItemParamList?ITEM_NO=${item.ITEM_NO }" target="ajax" 
								 	       rel="PaComputeItemParam"><span>${item.ITEM_NAME_ZH}</span></a></li>
						 		 </c:forEach>
							</ul>
						</li> 
				     </ul>
				</div>
				
				<div id="PaComputeItemParam" class="unitBox" style="margin-left:246px;">
					<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
						<div class="panelBar">
							<ul class="toolBar">
								<li><a class="add"><span><spring:message code="button.add"/><!--添加--></span></a></li>
								<li><a class="delete"><span><spring:message code="button.delete"/><!--删除--></span></a></li>
								<li><a class="edit"><span><spring:message code="button.update"/><!--修改--></span></a></li>
								<li class="line">line</li>
							</ul>
						</div>
						<table class="table" width="99%" layoutH="260">
							<thead>
								<tr>
									<th width="50"><spring:message code="pa.insurance.title.computeItem"/><!--计算项目--></th>
									<th width="50"><spring:message code="pa.insurance.title.companyID"/><!--公司ID--></th>
									<th width="50"><spring:message code="pa.salary.title.chineseAlias"/><!--中文别名--></th>
									<th width="50"><spring:message code="pa.salary.title.englishAlias"/><!--英文别名--></th>
									<th width="50"><spring:message code="pa.salary.title.koreaAlias"/><!--韩文别名--></th>
									<th width="80"><spring:message code="pa.insurance.title.ifRelatedWithBonus"/><!--是否与奖金有关联--></th>
									<th width="50"><spring:message code="pa.insurance.title.caculateOrder"/><!--计算顺序--></th>
									<th width="50"><spring:message code="pa.insurance.title.precision"/><!--精度--></th>
									<th width="50"><spring:message code="pa.insurance.title.carry"/><!--进位--></th>									
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>	
			</div>						
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>