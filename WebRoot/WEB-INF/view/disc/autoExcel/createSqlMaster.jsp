<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_insertSqlMaster(form, callback) {
	var $form = $("#addSqlMaster");
	var CPNY_IDv = $("#addSqlMaster select[name='CPNY_ID']:selected").val();
	var PGM_NMv = $("#addSqlMaster select[name='PGM_NM']:selected").val();
	var SQL_NMv = $("#addSqlMaster input[name='SQL_NM']").val();
	var SQL_FROM_STMTv = $("#addSqlMaster input[name='SQL_FROM_STMT']").val();
	var SQL_ORDER_BY_IDv = $("#addSqlMaster input[name='SQL_ORDER_BY_ID']").val();
	var SQL_DESCv = $("#addSqlMaster input[name='SQL_DESC']").val();
	<%--
	var payAreaCd   = $("#PAY_AREA_CD",navTab.getCurrentPanel()).val()==undefined?"":$("#PAY_AREA_CD",navTab.getCurrentPanel()).val();
	var insrareaId  = $("#INSRAREA_ID",navTab.getCurrentPanel()).val()==undefined?"":$("#INSRAREA_ID",navTab.getCurrentPanel()).val();
	var insureId    = $("#INSURE_ID",navTab.getCurrentPanel()).val()==undefined?"":$("#INSURE_ID",navTab.getCurrentPanel()).val();
	var insureRate  = $("#INSURE_RATE",navTab.getCurrentPanel()).val()==undefined?"":$("#INSURE_RATE",navTab.getCurrentPanel()).val();
	var insureValue = $("#INSURE_VALUE",navTab.getCurrentPanel()).val()==undefined?"":$("#INSURE_VALUE",navTab.getCurrentPanel()).val();
	--%>		
	if(CPNY_IDv==""){ 
		alertMsg.error("法人不能为空，请选择法人!");
		$("#CPNY_ID").focus();
		return false;
	}
	if(PGM_NMv==""){ 
		alertMsg.error("模块不能为空，请选择模块!");
		$("#PGM_NM").focus();
		return false;
	}
	if(SQL_NMv==""){ 
		alertMsg.error("SQL名不能为空，请输入SQL名!");
		$("#SQL_NM").focus();
		return false;
	}
	if(SQL_FROM_STMTv==""){ 
		alertMsg.error("SQL来源不能为空，请输入!");
		$("#SQL_FROM_STMT").focus();
		return false;
	}
	if(SQL_ORDER_BY_IDv==""){ 
		alertMsg.error("SQL排序号不能为空，请输入!");
		$("#SQL_ORDER_BY_ID").focus();
		return false;
	}

	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>

<div class="pageContent">
	<form id="createSqlMaster" name="createSqlMaster" method="post" class="pageForm required-validate" 
		action="/disc/autoExcel/addSqlMaster" onsubmit="return validateCallback_insertSqlMaster(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>
					法人：
				</dt>
					<dd>
					<select id= "CPNY_ID" name="CPNY_ID">
            			<option value="<c:out value="${CPNY_ID}"/>" selected>
              				 <c:out value="${CPNY_ID}"/></option>
             			  <option value="Hanwha"> 通用</option>
           			</select>
					</dd>
			</dl>
			<dl>
				<dt>
					模块名称：
				</dt>
					<dd>
					<input type="hidden" id="PGM_NMurl" name="PGM_NMurl" value="${PGM_NMurl}"> 
					 	<select id="PGM_NM" name="PGM_NM" >
					 	<c:if test="${PGM_NMurl eq 'ALL' }">
					 		<option value="EMP">EMP_员工</option>
					 		<option value="PAY">PAY_工资</option>
	             			<option value="ATT">ATT_考勤</option>
	             			<option value="WEL">WEL_福利</option>
	             			<option value="PAGEOUT">页面导出</option>
					 	</c:if>
					 	<c:if test="${PGM_NMurl eq 'EMP'}">
	             			<option value="EMP">EMP_员工</option>
	             		</c:if>
					 	<c:if test="${PGM_NMurl eq 'PAY'}">
	             			<option value="PAY">PAY_工资</option>
	             		</c:if>
	             		<c:if test="${PGM_NMurl eq 'ATT'}">
	             			<option value="ATT">ATT_考勤</option>
	             		</c:if>
	             		<c:if test="${PGM_NMurl eq 'WEL'}">
	             			<option value="WEL">WEL_福利</option>
	             		</c:if>
            			</select>
					</dd>
				</dl>
			
			<dl>
				<dt>
					SQL名称:
				</dt>
				<dd>
					<input type="text" name="SQL_NM" id="SQL_NM"  maxlength="30"/>
				</dd>
			</dl>
			<dl>
				<dt>
					SQL来源:
				</dt>
				<dd>
					<input type="text" name="SQL_FROM_STMT" id="SQL_FROM_STMT"  maxlength="30"/>
				</dd>
			</dl>
			<dl>
				<dt>
					排序号:
				</dt>
				<dd>
					<input type="text" name="SQL_ORDER_BY_ID" id="SQL_ORDER_BY_ID" class="required alphanumeric" required numeric minlength="1" maxbyte="10" />
				</dd>
			</dl>
			<dl>
				<dt>
					是否执行:
				</dt>
				<dd>
					<input type="checkbox" size="18" value="Y" checked='checked' name="SQL_STAT" id="SQL_STAT"  />
				</dd>
			</dl>
			<dl>
				<dt>
					是否特殊:
				</dt>
				<dd>
					<input type="checkbox" size="18" value="Y" name="IS_SPECIAL" id="IS_SPECIAL"  />
				</dd>
			</dl>
			<dl>
				<dt>
					SQL描述:
				</dt>
				<dd>
					<input type="text" name="SQL_DESC" id="SQL_DESC" maxlength="30"/>
				</dd>
			</dl>
			<dl>
				<dt>
					输入人:
				</dt>
				<dd>
					<c:out value='${UPDT_NN}'/>  
					<input name="UPDT_USER" type="hidden" readonly="true" size="18" value="<c:out value='${UPDT_USER}'/>">
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
						id="SQL_STMT" style="width: 400px"></textarea>
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

		
		
		
		
<%-- 	
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
   				 <td height="7" colspan="3" class="popup_bg_top"></td>
  </tr>
    <tr>
    <td height="3" colspan="3" class="popup_bg_bottom"></td>
  </tr>
    <tr>
    <td height="11" colspan="3"></td>
  </tr>
  <tr> 
    <td width="25">&nbsp;</td>
    
  <td width="550" height="15" class="page_title"><chrs:message messageID="Create SqlMaster Detail"/></td>
    <td width="25">&nbsp;</td>
  </tr>
  <tr> 
    <td width="25"></td>
  <td width="550" align="right" valign="top">
   <table width="550" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="14"></td>
        </tr>
        <tr> 
          <td height="2" class="table_header_line"></td>
        </tr>
        <tr> 
          <td> <table width="100%" border="0" cellspacing="1" cellpadding="0"  class="table_line_complex" >
       <tr> 
        <td width="120" class="table_header_complex_d"><chrs:message messageID="Company"/><br></td>
        <td  width="450 "class="table_padding">
           <select name="SUBSD_CD">
            <option value="<c:out value="${SUBSD_CD}"/>" selected>
               <c:out value="${SUBSD_CD}"/></option>
             <option value="LGE">             
               通用</option>
           </select>
        </td>        
       </tr>    
       
       <tr>     
        <td width="120" class="table_header_complex_d"><chrs:message messageID="MOD NM"/><br></td>
        <td width="450"  class="table_padding">
            <select name="PGM_NM" class="input_select_short" style="width:200px">
             <option value="TMP">TMP_LGE临时职</option>
             <option value="PAY">PAY_工资</option>
             <option value="ATT">ATT_考勤</option>
             <option value="WEL">WEL_福利</option>
             <option value="INC">INC_提成</option>
             <option value="EDU">EDU_教育</option>    
             <option value="INF">INF_接口</option>         
            </select>  
        </td>         
       </tr>  
       
       <tr>
            <td width="120" class="table_header_complex_d" align="right"><chrs:message messageID="SQL NM"/></td>
            <td width="450" class="table_padding">
            <input name="SQL_NM" type="text" class="input_textfield" style="width:200px" value="" required>
            </td>  
       </tr>
       

       <tr> 
        <td width="120" class="table_header_complex_d"><chrs:message messageID="SQL FROM"/><br></td>
        <td  width="450 "class="table_padding">
            <input name="SQL_FROM_STMT" type="text" class="input_textfield" style="width:200px" value="手动生成" required>
        </td>        
       </tr>  
      
 
       <tr> 
        <td width="120" class="table_header_complex_d"><chrs:message messageID="ORDER BY ID"/><br></td>
        <td  width="450 "class="table_padding">
        <input name="SQL_ORDER_BY_ID" type="text" class="input_textfield" style="width:200px" value="" required numeric minlength="1" maxbyte="10">
        </td>        
       </tr>        

       <tr> 
        <td width="120" class="table_header_complex_d"><chrs:message messageID="SQL STAT"/><br></td>
        <td  width="450 "class="table_padding">
           <input name="SQL_STAT" type="checkbox" size="18" value="Y" checked='checked'>
       </td>
      </tr> 
      
       <tr> 
        <td width="120" class="table_header_complex_d"><chrs:message messageID="SQL DESC"/><br></td>
        <td  width="450 "class="table_padding">
        <input name="SQL_DESC" type="text" class="input_textfield" style="width:200px" value="">
        </td>        
       </tr>        
       
      <tr>        
        <td width="120" class="table_header_complex_d"><chrs:message messageID="UPDT_USER" /><br></td>
        <td width="450"  class="table_padding">
        <c:out value='${UPDT_NN}'/>      
        <input name="UPDT_USER" type="hidden" readonly="true" class="input_textfield" size="18" value="<c:out value='${UPDT_USER}'/>">
        </td>
      </tr> 
      
      <tr>        
        <td width="120" class="table_header_complex_d"><chrs:message messageID="SQL STMT" /><br></td>
        <td width="450"  class="table_padding">
        <TEXTAREA NAME="SQL_STMT" class="input_textarea" rows=30 cols=30 required></TEXTAREA>
        </td>
      </tr> 
       
     </table>
          </td>
        </tr>
      </table> 
      
  </td>
    <td></td>
  </tr>
    <tr>
      <td height="14"></td>
        <td height="14"></td>
        <td height="14"></td>
    </tr>
    <tr>
      <td></td>
        <td align="right">
          <!-- button Start-->
      <table cellspacing="0" cellpadding="0">
        <tr> 
          <td> <input name="submit2" type="submit" class="button_default" value="<chrs:message messageID="Save"/>" onMouseOver="this.style.color='#650000'" onMouseOut="this.style.color='#202020'" > 
          </td>
          <td width="4"></td>
          <td> <input name="reset4" type="button" class="button_default" value="<chrs:message messageID="Cancel"/>"  onclick="javascript:window.close()" onMouseOver="this.style.color='#650000'" onMouseOut="this.style.color='#202020'"> 
          </td>
        </tr>
      </table>
   <!-- button End-->
     </td>
        <td></td>
    </tr>
  <tr> 
    <td height="21"></td>
    <td height="21"></td>
    <td height="21"></td>
  </tr>
</table>
</form>
<chrs:xjos />
</body>

</html>--%>	