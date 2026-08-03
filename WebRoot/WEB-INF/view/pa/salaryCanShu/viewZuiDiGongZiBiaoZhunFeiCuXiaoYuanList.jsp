<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
		function importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(){
			$("#importExcelDialog_pa0807").attr('href','/pa/excelImport/importPaiQianDiGUanLiInfoData?importFunName=/importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo');
			$("#importExcelDialog_pa0807").click();
		}
</script>

<a id="importExcelDialog_pa0807"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0807"  href="#" target="navTab" mask="true">
<span style="display:none;"><spring:message code="pa.salary.canShu.zDgzFcxyDaoRuJieGuo"/><!--最低工资(非促销员)导入结果 --></span></a>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td  style="color: gray;">
	    				<spring:message code="pa.salary.canShu.faRen"/><!--法人-->
	    			</td>
	    			<td >
	    				<ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="seach_faren" language="zh"  limit="ALL" activity="1" selected="${faren}"/>
	    			</td>
	    			<td  style="color: gray;">
	    				<spring:message code="pa.salary.canShu.niandu"/><!--年度-->
	    			</td>
	    			<td >
	    				<ait:date yearName="seach_niandu" yearSelected="${niandu}" ></ait:date>
	    			</td>
	    		</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="380" var="add_height"/>
	<c:set value="/pa/salaryCanShu/addZuiDiGongZiBiaoZhunFeiCuXiaoYuanView" var="add_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="380" var="edit_height"/>
	<c:set value="/pa/salaryCanShu/updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanView?PQD_NO={PQD_NO}" var="edit_Url"/>
	<div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="buttonActive" id ="exportExcel"
		href="/hrm/empinfo/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanMoBanModle">
	<span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
		</a>
		</li>
		<li>
			<a class="buttonActive" onclick="importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo()">
		<SPAN><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></SPAN>
	 	</a>
		</li>
		<li>
			<a class="buttonActive" id ="exportExcel"
		href="/pa/excelExport/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoList?seach_faren=${faren}&seach_niandu=${niandu}">
							<SPAN><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></SPAN>
		</a>
		</li>
		 <c:if test="${toolbarInfo.INSERTR == '1'}">
			 <c:if test="${init_Url ne '' && init_Url ne null}">
				<li id="addLi">
					<a class="add"
						href="${init_Url}"
						<c:if test="${init_target_exit eq '' || init_target_exit eq null }">
							target="${init_tab eq '' || init_tab eq null ? 'ajaxTodo' : init_tab}"
						</c:if>
						<c:if test="${init_range ne '' && init_range ne null }">
							width="${init_width eq '' || init_width eq null ? '500' : init_width}" 
							height="${init_height eq '' || init_height eq null ? '400' : init_height}"
						</c:if>
						title="
							<c:choose>
							   <c:when test="${init_title eq '' || init_title eq null}">
							     	<spring:message code="button.init.sure" />
							   </c:when>
							   <c:otherwise>
							   		${init_title}
							   </c:otherwise>
							</c:choose>
						">
						<span>
							<c:choose>
							   <c:when test="${init_name eq '' || init_name eq null}">
							     	<spring:message code="button.init" />
							   </c:when>
							   <c:otherwise>
							   		${init_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
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
		
		<c:if test="${toolbarInfo.DELETER == '1'}">
			<c:if test="${delete_Url ne '' && delete_Url ne null}">
				<li id="deleteLi">
					<a class="delete" href="${delete_Url}"
						<c:if test="${delete_target_exit eq '' || delete_target_exit eq null }">
							target="${delete_tab eq '' || delete_tab eq null ? 'ajaxTodo' : delete_tab}"
						</c:if>
						<c:if test="${delete_mask_exit ne '' && delete_mask_exit ne null }">
							mask="${delete_mask eq '' || delete_mask eq null ? 'true' : delete_mask }" 
						</c:if>
						<c:if test="${delete_range ne '' && delete_range ne null }">
							width="${delete_width eq '' || delete_width eq null ? '500' : delete_width}" 
							height="${delete_height eq '' || delete_height eq null ? '400' : delete_height}"
						</c:if>
						 title="
						 	<c:choose>
							   <c:when test="${delete_title eq '' || delete_title eq null}">
							     	<spring:message code="button.delete.sure" />
							   </c:when>
							   <c:otherwise>
							   		${delete_title}
							   </c:otherwise>
							</c:choose>
						 ">
						<span>
							<c:choose>
							   <c:when test="${delete_name eq '' || delete_name eq null}">
							     	<spring:message code="button.delete" />
							   </c:when>
							   <c:otherwise>
							   		${delete_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${toolbarInfo.UPDATER == '1'}">
			<c:if test="${edit_Url ne '' && edit_Url ne null}">
				<li id="editLi">
					<a class="edit"
						href="${edit_Url}"
						<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
							target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						</c:if>
							mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }" 
							width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}" 
							height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
							<c:if test="${edit_rel ne '' && edit_rel ne null}">
								rel="${edit_rel}"
							</c:if>
							><span>
								<c:choose>
								   <c:when test="${edit_name eq '' || edit_name eq null}">
								     	<spring:message code="button.update" />
								   </c:when>
								   <c:otherwise>
								   		${edit_name}
								   </c:otherwise>
								</c:choose>
							</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
	</ul>
</div>
	


					
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th width="50"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<th width="50"><spring:message code="pa.salary.canShu.niandu"/><!--年度--></th>
				<th width="50"><spring:message code="pa.salary.canShu.fulidiqu"/><!--福利地区--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></th>
				<th width="50"><spring:message code="pa.salary.canShu.shepinggongzi"/><!--社平工资--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuixiaojishu"/><!--最小基数--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuidajishu"/><!--最大基数--></th>
				<th width="50"><spring:message code="pa.salary.canShu.qufen"/><!--区分--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr target="PQD_NO" rel="${pQd.NO1}">
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.FR}</center></td>
					<td><center>${pQd.ND}</center></td>
					<td><center>${pQd.FLDQ}</center></td>
					<td><center>${pQd.ZDGZ}</center></td>
					<td><center>${pQd.SPGZ}</center></td>
					<td><center>${pQd.ZXJS}</center></td>
					<td><center>${pQd.ZDJS}</center></td>
					<td><center>${pQd.QF}</center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>