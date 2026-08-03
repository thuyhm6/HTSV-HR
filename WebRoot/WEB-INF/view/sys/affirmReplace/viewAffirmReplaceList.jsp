<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/sys/affirmReplace/viewAffirmReplaceList?firstFlag=N&pageNum=1&menuNo=${menuNo}&navTabId=sys0136" method="post" rel="pagerForm"
		id="viewAffirmReplaceList" name="viewAffirmReplaceList">
						<!--  <input type="hidden" id="SQL_CPNY_SELECT" name="seach_CPNY_ID" value="${defaultCpny}"/>-->
						<input type="hidden" name="menuNo" id="menuNo" value="${menuNo}"/>
	<div class="searchBar" >
		<table class="searchContent">
		<tr>
		        	<td><!-- 法人 -->
						 法人：
					</td>	
					<td>
					<!--  
						<c:if test="${authority eq '1'}">
							<select id="SQL_CPNY_SELECT" name="seach_CPNY_ID" onchange="reloadPage();">
								<c:forEach items="${companyList}" var="item" varStatus="i">
								<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${authority eq '0'}">
							${defaultCpny}						
						</c:if>-->
						<select id="SQL_CPNY_SELECT" name="seach_CPNY_ID" onchange="reloadPage();">
							<option value="${defaultCpny}">${defaultCpny}</option>
						</select>		
					</td>
					<td>作用域：</td>
					<td>
						<select id="TABLE_NAME" name="TABLE_NAME">
							<option value="ALL" <c:if test="${TABLE_NAME eq 'ALL'}">selected</c:if>>全部</option>
							<option value="ZZ" <c:if test="${TABLE_NAME eq 'ZZ'}">selected</c:if>>最终裁决</option>
							<option value="TS" <c:if test="${TABLE_NAME eq 'TS'}">selected</c:if>>特殊裁决</option>
						</select>
					</td>
					<td>
					 	被替换裁决者：
					</td>
					<td>
						<input id="OLDCHECK_EMPID" name="dwz.person.empIdold" type="text" value=""  lookupGroup="person"/>
						<input type="hidden" name="dwz.person.person_idold" id="OLDCHECK_PERSONID" value="${OLDCHECK_PERSONID}" readOnly lookupGroup="person"/>
			 			<a class="btnLook" href="/sys/affirmReplace/viewAffirmEmpList?firstFlag=1&limit=pa&pageNum=1&newold=old" rel="getemp"lookupGroup="person">
			 		<!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
					<td>
					 	替换裁决者：
					</td>
					<td>
						<input id="NEWCHECK_EMPID" name="dwz.person.empIdnew" type="text" value=""  lookupGroup="person"/>
						<input type="hidden" id="NEWCHECK_PERSONID" name="dwz.person.person_idnew"  value="${NEWCHECK_PERSONID}" readOnly lookupGroup="person"/>
			 			<a class="btnLook" href="/sys/affirmReplace/viewAffirmEmpList?firstFlag=1&limit=pa&pageNum=1&newold=new" rel="getemp"lookupGroup="person">
			 		<!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
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
				</ul>
			</div>
    	</div>
  	</form>
 </div> 
 
 <div class="pageContent">
	
	<c:set value="400" var="add_width"/>
	<c:set value="300" var="add_height"/>
	<c:set value="true" var="add_mask"/>
	<c:set value="裁决者替换" var="add_name"/>
	<c:set value="affirmadd" var="add_rel"/>
	<c:set value="/sys/affirmReplace/affirmReplace?TABLE_NAME={TABLE_NAME}" var="add_Url"/>
	<c:set value="400" var="edit_width"/>
	<c:set value="300" var="edit_height"/>
	<c:set value="true" var="edit_mask"/>
	<c:set value="裁决者终止" var="edit_name"/>
	<c:set value="stopAffirm" var="edit_rel"/>
	<c:set value="/sys/affirmReplace/stopAffirm?TABLE_NAME={TABLE_NAME}" var="edit_Url"/>
	<c:set value="/sys/affirmReplace/deleteAffirmReplace?AFFIRM_REPLACE_NO={sid}" var="delete_url"/>
		
	<div class="formBar">
		<ul class="toolBar">
		<!--
			初始化按钮： 同删除按钮
			添加按钮：add_Url 必填项 JS链接地址
				  add_target_exit 判断target是否存在：存在-弹出新页面；不存在：本页提交
				  add_tab target页面打开方式：弹出；链接到navTab;本页
				  add_mask mask 页面下方div显示 
				  add_width 新打开页面宽度
				  add_height 新打开页面高度
				  add_rel panel名字（navTabId）
				  add_name <a> 显示的名字
			删除按钮：delete_Url delete_target_exit delete_tab delete_width delete_height delete_name 同上
				  delete_range 如果是新打开页面 需要赋值
		   	修改按钮：同上
		 -->
		<c:if test="${toolbarInfo.INSERTR == '1'}">
			 <c:if test="${add_Url ne '' && add_Url ne null}">
				<li id="addLi">
				 
					<a class="add" href="${add_Url}"
						<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
							target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
						</c:if>
							mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
							width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
							height="${add_height eq '' || add_height eq null ? '400' : add_height}"
							<c:if test="${add_rel ne '' && add_rel ne null}">
								rel="${add_rel}"
							</c:if>
							>
							<span>
								<c:choose>
								   <c:when test="${add_name eq '' || add_name eq null}">
								     	<spring:message code="button.add" />
								   </c:when>
								   <c:otherwise>
								   		${add_name}
								   </c:otherwise>
								</c:choose>
							</span>
					</a>
				</li>
			 </c:if>
		</c:if>
		
		<c:if test="${toolbarInfo.INSERTR == '1'}">
			<li id="edit">
				 <a class="update" href="${edit_Url}" target="dialog" rel="stopAffirm" width="${edit_width}" height="${edit_height}" mask="true">
					<span> 终止</span>
				</a>

			</li>
		</c:if>
	

		<c:if test="${toolbarInfo.DELETER == '1'}">
			<li>
				  <a id="deletesql" name="deletesql" class="updateparam"  href="${delete_url}"  title="确定要回退吗?" target="ajaxTodo" >
					<span> 回退</span> </a>
			</li>
		</c:if>
		<li class="line">
			line
		</li>
	</ul>
</div>
<form name="affrimReplaceForm" id="affrimReplaceForm" method="post" action="/sys/affirmReplace/deleteAffirmReplace?AFFIRM_REPLACE_NO={sid}" >
	 <!--   onsubmit="return delAndCallback(this, navTabAjaxDone)"> -->
	<table class="table" width="100%" layoutH="210">
		<thead>
			<tr> 
			    <th width="3%">
			    	序号
			    </th>
				<th width="7%"><!--法人-->
					法人
				</th>
				<th width="10%"><!--执行-->
					替换类型
				</th>
				<th width="10%">
					作用域
				</th>
				<th width="15%"><!--模块-->
					被替换裁决者
				</th>
				<th width="15%"><!--SQL名称-->
					替换裁决者
				</th>
				<th width="15%"><!--排序号-->
					生成日期
				</th>
				
				<th width="15%"><!--SQL描述-->
					生成人
				</th>


			</tr>
		</thead>  
			<tbody>
			<c:forEach items="${showlist}" var="isData" varStatus="i">			
				<tr target="sid" rel="${isData.AFFIRM_REPLACE_NO}">
					<td style="text-align:center">${isData.ROWNUM_}</td>
					<td style="text-align:center">${isData.CPNY_ID}</td>
					<td style="text-align:center">
					<c:if test="${isData.REPLACE_TYPE eq'TH'}">替换</c:if>
					<c:if test="${isData.REPLACE_TYPE eq'ZZ'}">终止</c:if>
					</td>
					<td style="text-align:center">
					<c:if test="${isData.TABLE_NAME eq'ZZ'}">最终裁决</c:if>
					<c:if test="${isData.TABLE_NAME eq'TS'}">特殊裁决</c:if>
					<c:if test="${isData.TABLE_NAME eq'ALL'}">全部</c:if>
					</td>
					<td style="text-align:left">${isData.OLDNAME }</td>
					<td style="text-align:left">${isData.NEWNAME }</td>
					<td style="text-align:center">${isData.CREATE_DATE }</td>				
					<td style="text-align:left">${isData.CREATE_BY }</td>
				</tr>			
			</c:forEach>			
		</tbody>
		</table>
		</form>
		<!--  seach_defaultCpny=${defaultCpny}&defaultCpny=${defaultCpny }-->
	<c:set value="/sys/affirmReplace/viewAffirmReplaceList?firstFlag=N&pageNum=1&menuNo=${menuNo}&navTabId=sys0136" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
   