<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>

<div class="pageContent">
	<form id="updateSqlMaster" name="updateSqlMaster" method="post" class="pageForm required-validate" 
		action="/disc/autoExcel/updateSqlMaster" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>
					法人：
				</dt>
					<dd>
					<select id= "CPNY_ID" name="CPNY_ID">
            			<option value="${CPNY_ID}" ><c:out value="${CPNY_ID}"/></option>
            			<option value="${master.CPNY_ID}" <c:if test="${master.CPNY_ID ne ''}" >selected</c:if>><c:out value="${master.CPNY_ID}"/></option>
             			<option value="LGE" <c:if test="${master.CPNY_ID eq 'LGE'}" >selected</c:if>> 通用</option>
           			</select>
					</dd>
			</dl>
			<dl>
				<dt>
					模块名称：
				</dt>
					<dd>
					<input type="hidden" name="PGM_NMurl" id="PGM_NMurl" value="${master.PGM_NMurl}"/>						
						<c:if  test="${master.PGM_NMurl eq 'ALL'}">
							<select id="PGM_NM" name="PGM_NM"  >
	             				<option value="PAY" <c:if test="${master.PGM_NM eq 'PAY' }">selected</c:if>>PAY_工资</option>
	             				<option value="ATT" <c:if test="${master.PGM_NM eq 'ATT' }">selected</c:if>>ATT_考勤</option>             				
	             				<option value="EMP" <c:if test="${master.PGM_NM eq 'EMP' }">selected</c:if>>EMP_人员</option>          				
	             				<option value="PAGEOUT" <c:if test="${master.PGM_NM eq 'PAGEOUT' }">selected</c:if>>页面导出</option>
	             			</select>  
	             		</c:if>

	             		<c:if  test="${master.PGM_NMurl eq 'PAY'}">
							<select id="PGM_NM" name="PGM_NM" >
	             			<option value="PAY">PAY_工资</option>
	             			
	             			</select>  
	             		</c:if>
	             		<c:if  test="${master.PGM_NMurl eq 'ATT'}">
							<select id="PGM_NM" name="PGM_NM" >
	             				<option value="ATT">ATT_考勤</option>	        
	             			</select>  
	             		</c:if>
	             		<c:if  test="${master.PGM_NMurl eq 'WEL'}">
							<select id="PGM_NM" name="PGM_NM" >
	             			<option value="WEL">WEL_福利</option>
	             			
	             			</select>  
	             		</c:if>
	             		<c:if  test="${master.PGM_NMurl eq 'EMP'}">
							<select id="PGM_NM" name="PGM_NM" >
	             				<option value="EMP">EMP_人员</option>
	             			</select>  
	             		</c:if>

	 
					</dd>
				</dl>
			
			<dl>
				<dt>
					SQL名称:
				</dt>
				<dd>
					<input type="text" name="SQL_NM" id="SQL_NM"  maxlength="30" value="${master.SQL_NM}"/>
				</dd>
			</dl>
			<dl>
				<dt>
					SQL来源:
				</dt>
				<dd>
					<input type="text" name="SQL_FROM_STMT" id="SQL_FROM_STMT"  maxlength="30" value="${master.SQL_FROM_STMT}"/>
				</dd>
			</dl>
			<dl>
				<dt>
					排序号:
				</dt>
				<dd>
					<input type="text" name="SQL_ORDER_BY_ID" id="SQL_ORDER_BY_ID" class="required alphanumeric"
					 required numeric minlength="1" maxbyte="10" value="${master.SQL_ORDER_BY_ID }" />
				</dd>
			</dl>
			<dl>
				<dt>
					是否启用:
				</dt>
				<dd>
					<input type="checkbox" size="18" value="Y"  name="SQL_STAT" id="SQL_STAT" value="${master.SQL_STAT}" 
						<c:if test="${master.SQL_STAT eq 'Y'}" >
							checked='checked'
						</c:if>
						<c:if test="${master.SQL_STAT eq 'N'}" >					  	
						</c:if> />
				</dd>
			</dl>
			<dl>
				<dt>
					是否特殊:
				</dt>
				<dd>
					<input type="checkbox" size="18" value="Y"  name="IS_SPECIAL" id="IS_SPECIAL" value="${master.IS_SPECIAL}"
						<c:if test="${master.IS_SPECIAL eq 'Y'}" >
							checked='checked'
						</c:if>
						<c:if test="${master.IS_SPECIAL eq 'N'}" >					  	
						</c:if> />
				</dd>
			</dl>
			<dl>
				<dt>
					SQL描述:
				</dt>
				<dd>
					<input type="text" name="SQL_DESC" id="SQL_DESC" maxlength="30" value="${master.SQL_DESC }"/>
				</dd>
			</dl>
			<dl>
				<dt>
					输入人:
				</dt>
				<dd>
					<c:out value='${master.UPDT_NN}'/>  
					<input name="UPDT_USER" type="hidden" readonly="true" size="18" value="<c:out value='${master.UPDT_USER}'/>">
					<input name="SQL_SEQ" id= "SQL_SEQ" type="hidden" value ="${master.SQL_SEQ }">
				</dd>
 
			</dl>
			
			<dl style="height:auto;">
			<table>
				<tr>
					<td class="td_title" style="width:122px;">
						<!--<spring:message code="pa.insurance.title.description"/>描述-->SQL语句:
					</td>
					<td class="td_thype">
						<textarea cols="100" rows="15" class="l-textarea" name="SQL_STMT"
						id="SQL_STMT" style="width: 400px"> ${master.SQL_STMT}</textarea>
					</td>
				</tr>
				
			</table>
			</dl>
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
