<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
$(document).ready(function() { 
	//给所有的td单元格绑定一个click事件 
	//如果这个table的id为table1，那单给table1的所有td加click事件代码为： 
	// var tds = $("#table1").find("td"); 
	 var params=$("#forexcel tr").length;
	 $("#count").val(params);
	}); 
function ajaxAdd_add_sqlparam(iStatus_index) {
	if(iStatus_index>0||iStatus_index==0){
	var seach_SQL_PARAM_TP='seach_SQL_PARAM_TP'+iStatus_index;
	var SQL_PARAM_TP_DESC='SQL_PARAM_TP_DESC'+iStatus_index;
	var PARAM='PARAM'+iStatus_index;
	var cpny='${defaultCpny}';
	$.ajaxSettings.global = false;
	 $.ajax( {
		
		type : "POST",
		url : "/disc/autoExcel/retrieveParamDesc",
		data : { seach_SQL_PARAM_TP : $('#'+seach_SQL_PARAM_TP+'').val() , defaultCpny : $('#cpnyid').val(),param:$('#'+PARAM+'').val(),PGM_NMurl : $('#PGM_NMurl').val()},
		dataType : "json",
		success : function(data) {
			$('#'+SQL_PARAM_TP_DESC+'').html("");
			var html = '';
			if (typeof (data['result']) != "undefined") {
				html+=data['result'];
			}
			//$('#'+SQL_PARAM_TP_DESC+'').innerText=html;
			document.getElementById(''+SQL_PARAM_TP_DESC+'').innerText=html;
		}
	});
	$.ajaxSettings.global = true;
	}
}

	
</script>
<div class="pageContent">
	<form id="updateSqlParamList" name="updateSqlParamList" method="post" class="pageForm required-validate" 
		action="/disc/autoExcel/updateSqlParamList" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
		<div>
				<dl>
				<dt>
					<!-- SQL序号 --><spring:message code="disc.autoExcel.SQL_NO.Z" />：
				</dt>
					<dd>
					<input type="text" name="SQL_SEQ" id="SQL_SEQ"  readonly value="${master.SQL_SEQ}"/>
					</dd>
			</dl>
			<dl>
				<dt>
					<!-- SQL模块 --><spring:message code="disc.autoExcel.SQL_MODULE.Z" />：
				</dt>
					<dd>
					<input type="text" name="PGM_NM" id="PGM_NM"   readonly value="${master.PGM_NM}"/>
					<input type="hidden" name="PGM_NMurl" id="PGM_NMurl" value="${PGM_NMurl}"/>
					
					</dd>
				</dl>
			<dl>	
				<dt>
					<!-- SQL名字 --><spring:message code="disc.autoExcel.SQL_NAME.Z" />：
				</dt>
					<dd>
					<input type="text" name="SQL_NM" id="SQL_NM"   readonly value="${master.SQL_NM}"/>
				</dd>
			</dl>

			<dl>	
				<dt>
					<!-- 创建时间 --><spring:message code="sys.basic.title.createDate" />：
				</dt>
				<dd>
					<input type="text" name="RGST_DTIME" id="RGST_DTIME"   readonly value="${master.RGST_DTIME}"/>
				</dd>
			</dl>
			<dl>	
				<dt>
					<!-- 更新时间 --><spring:message code="inct.salesman.updateTime" />：
				</dt>
				<dd>
					<input type="text" name="UPDT_DTIME" id="UPDT_DTIME"   readonly value="${master.UPDT_DTIME}"/>
				</dd>
			</dl>
			<dl>	
				<dt>
					<!-- 更新人 --><spring:message code="inct.salesman.updateBy" />：
				</dt>
				<dd>
					<input type="text" name="UPDT_NN" id="UPDT_NN"   readonly value="${UPDT_NN}"/>
				</dd>
			</dl>
		</div>
		<input type="hidden" name="count" id="count" value="1">	
		<table class="table" >
						<thead>
							<tr> 
						    <th width="100">
						    	<!-- 参数 --><spring:message code="disc.autoExcel.PARAMETER.Z" />
						    </th>
							<th width="100">
								<!-- 英语描述 --><spring:message code="disc.autoExcel.ENGLISH_DESCRIPTION.Z" />
							</th>
							<th width="100">
								<!-- 汉语描述 --><spring:message code="disc.autoExcel.CHINESE_DESCRIPTION.Z" />
							</th>
							<th width="100">
								<!-- 参数类型 --><spring:message code="disc.autoExcel.PARAMETER_TYPE.Z" />
							</th>
							
							<th width="350">
								info
							</th>
							<th width="50">
								sort
							</th>

						</tr>
					</thead> 
					<tbody id="forexcel">
					<c:forEach items="${paramlist}" var="isData" varStatus="iStatus">
						
					<tr target="sid" rel="${isData.SQL_PARAM_NO }" height="50"  >
						<td style="text-align:center">
							<input type="hidden" value="${isData.SQL_PARAM_NO }" name="SQL_PARAM_NO${iStatus.index}" id="SQL_PARAM_NO${iStatus.index}">
							<input type="text" name="PARAM${iStatus.index}" id="PARAM${iStatus.index}" value="${isData.PARAM}"  readonly>
						</td>
						<td style="text-align:left">
							<textarea  <c:if test="${isData.PARAM eq 'CPNY_ID' }">readonly</c:if> name="EN_SQL_PARAM_DESC${iStatus.index}" id="EN_SQL_PARAM_DESC${iStatus.index}"cols="10" rows="1" >${isData.EN_SQL_PARAM_DESC }</textarea>
						</td>
						<td style="text-align:left">
							<textarea <c:if test="${isData.PARAM eq 'CPNY_ID' }">readonly</c:if> name="CN_SQL_PARAM_DESC${iStatus.index}" id="CN_SQL_PARAM_DESC${iStatus.index}"cols="10" rows="1" >${isData.CN_SQL_PARAM_DESC }</textarea>
						</td>
						<td style="text-align:left">
							<!--<ait:SelectSyCodeByCpnyID id="seach_SQL_PARAM_TP" name="seach_SQL_PARAM_TP" 
							parentNo="278688" selected="${SQL_PARAM_TP}" cnpyID="${defaultCpny}" limit="all"/>
							<select id= "SQL_PARAM_TP${iStatus.index}" name="SQL_PARAM_TP${iStatus.index}" onchange="javascript:retrieveParamDesc('updateSqlParamList');">
            					<option value="<c:out value="${isData.SQL_PARAM_TP}"/>" selected>${isData.SQL_PARAM_TP}</option>
             					<option value="text">文本</option>
								<option value="date">时间</option>
           					</select>-->
           					<c:if test="${isData.PARAM ne 'CPNY_ID' }">
           					 <ait:SelectSyCodeByCpnyIDForExcel id="seach_SQL_PARAM_TP${iStatus.index}" name="seach_SQL_PARAM_TP${iStatus.index}" parentNo="211026" selected="${isData.SQL_PARAM_TP}" cnpyID="${CPNY_ID}" limit="all" onChangeName="ajaxAdd_add_sqlparam(${iStatus.index} )"/>
							</c:if>
							<c:if test="${isData.PARAM eq 'CPNY_ID' }"><!-- 文本 --><spring:message code="disc.autoExcel.TEXT.Z" />
							 <input type="hidden" name="seach_SQL_PARAM_TP${iStatus.index}" id="seach_SQL_PARAM_TP${iStatus.index}" value="text"/>
							</c:if>
						</td>
						
						<td style="text-align:left">
						<textarea <c:if test="${isData.PARAM eq 'CPNY_ID' }">readonly</c:if> name="SQL_PARAM_TP_DESC${iStatus.index}"  id="SQL_PARAM_TP_DESC${iStatus.index}" cols="35" rows="1"  >${isData.SQL_PARAM_TP_DESC }</textarea>
						</td>
						
						<td style="text-align:center" >
						   
							<input  <c:if test="${isData.SORT_CD eq '' || isData.SORT_CD eq null}"> value="${iStatus.index}"</c:if>
							<c:if test="${isData.SORT_CD ne '' || isData.SORT_CD ne null}"> value="${isData.SORT_CD}"</c:if>
							 name="SORT_CD${iStatus.index}" id="SORT_CD${iStatus.index}"width="2"/>
						</td>
						
						<!--  <td style="text-align:left" hidden>${isData.REMARK }</td>
						<td style="text-align:LEFT" hidden>${isData.CREATE_DATE }</td>-->
						
					</tr>			
					</c:forEach>
					</tbody>			
					</table>
			
			<div>
			<table class="table">
				<dl>
					<dt  >
					<!-- SQL内容 --><spring:message code="disc.autoExcel.SQL_CONTENT.Z" />：
					</dt>
					<dd >
					<textarea disabled="disabled" name="SQL_STMT" id="SQL_STMT" cols="80" rows="20"  class="input_textarea">${master.SQL_STMT}</textarea>
					</dd>
				</dl>
			</table>
			</div>
		<input type="hidden" name="count" id="count" value="${iStatus.index}">	
			<input type="hidden" name="cpnyid" id="cpnyid" value="${defaultCpny}">
			</div>
		<div class="formBar" layoutH="260">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/><!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
