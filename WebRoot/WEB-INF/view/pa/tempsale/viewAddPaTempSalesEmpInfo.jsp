<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	//身份证验证
	function validateIdCard(index){
		var num = $("#IDCARD_NO" + index).val();
		var len = num.length, re;
		if (len == 15){
			re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
		}else if (len == 18){
			re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d|X|x)$/);
		}else if (len == 0){
			//alert("输入的身份证号不能为空！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullIdcardNo"/>');
			return false;
		}else {
			//alert("输入的数字位数不对！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkDigitalDigits"/>');
			return false;
		}
		var a = num.match(re);
		var B = null;
		var D = null;
		if (a != null){
			if (len==15){
				D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			else{
				D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			if (!B){
				//alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitA"/>'+ a[0] +'<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitB"/>');
				return false;
			}else{
				$("#BIRTH_DATE" + index).val(D.getFullYear()+"-"+((D.getMonth()+1) < 10 ? "0"+(D.getMonth()+1) : (D.getMonth()+1))+"-"+((D.getDate()) < 10 ? "0"+(D.getDate()) : (D.getDate())));
				return true;
			}
		}else{
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNo"/>');//您输入的身份证号不正确
			return false;
		}
	}
	//添加临工项目人员
	function addrow(){
	    var count = parseInt($("#tempSalesEmpInfoCount").val());
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		htm+='<td class="td_title"  width="18%">姓名</td>';
	   		htm+='<td  class="td_type"  width="30%"><input type="text" id="EMP_NAME' + i + '" name="EMP_NAME' + i + '" class="required textInput"  maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title" width="18%">身份证号码</td>';
	   		htm+='<td  class="td_type" width="30%"><input type="text" id="IDCARD_NO' + i + '" name="IDCARD_NO' + i + '" class="required textInput" onblur="validateIdCard(' + i + ')"  maxlength="20" size="30"></td>';
	   		htm+='<td rowspan="5" width="2%"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">出生年月</td>';
	   		htm+='<td  class="td_type"><input type="text" id="BIRTH_DATE' + i + '" name="BIRTH_DATE' + i + '" class="required textInput"  readonly="true" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title">联系方式</td>';
	   		htm+='<td  class="td_type"><input type="text" id="CELLPHONE' + i + '" name="CELLPHONE' + i + '" class="required textInput" maxlength="20" size="30"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">所属单位</td>';
	   		htm+='<td  class="td_type" colspan="3"><input type="text" id="DEPT_NAME' + i + '" name="DEPT_NAME' + i + '" class="textInput" maxlength="80" size="80"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">评价等级</td>';
	   		htm+='<td  class="td_type"><select name="EVS_GRADE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${grade}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
			htm+='</select></td>';
	   		htm+='<td  class="td_title">黑名单与否</td>';
	   		htm+='<td  class="td_type"><select name="BLACK_LIST_YN' + i + '"><option value="">请选择</option>' ;
	   		htm+='<option value="Y">Y</option>';
	   		htm+='<option value="N">N</option></select>';
			htm+='</td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">工作天数</td>';
	   		htm+='<td  class="td_type"><input type="text" id="WORK_DAYS' + i + '" name="WORK_DAYS' + i + '" class="number required textInput" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title" >应发工资</td>';
	   		htm+='<td  class="td_type"><input type="text" id="EVENT_SALARY' + i + '" name="EVENT_SALARY' + i + '" class="number required textInput"  onchange="changeTotal();" maxlength="20" size="30"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">银行帐号</td>';
	   		htm+='<td  class="td_type"><input type="text" id="BANK_NO' + i + '" name="BANK_NO' + i + '" class="number textInput" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title"></td>';
	   		htm+='<td  class="td_type"></td>';
	   		htm+='</tr>';
	   		htm+='</table>';
	   	$("#createTable").append(htm) ;
	   	count++;  
	    $("#tempSalesEmpInfoCount").attr("value",count) ;
    }
    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    function validateCallbackViewPaTempSalesEmpInfo(form, callback) {
		var $form = $("#viewAddPaTempSalesEmpInfo");
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
	<form id="viewAddPaTempSalesEmpInfo" method="post" action="/pa/tempsale/addPaTempSalesEmpInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewPaTempSalesEmpInfo(this, navTabAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<div class="panel" id="tempSalaryEmpPanel">
			<h1>
				临促人员信息
			</h1>
			<div id="createTable" width="100%">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						姓名
					</td>
					<td class="td_type" width="30%">
						<input type="hidden" name="EVENT_ID" value="${EVENT_ID }" />
						<input type="text" id="EMP_NAME0" name="EMP_NAME0" class="required textInput"  maxlength="20" size="30"/>
					</td>
					<td class="td_title"  width="18%">
						身份证号码
					</td>
					<td class="td_type"  width="30%">
						<input type="text" id="IDCARD_NO0" name="IDCARD_NO0" class="required textInput" onblur="validateIdCard(0)"  maxlength="20" size="30">
					</td>
					<td rowspan='5'  width="2%"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
					<td class="td_title" >
						出生年月
					</td>
					<td class="td_type">
						<input type="text" id="BIRTH_DATE0" name="BIRTH_DATE0" class="required textInput"  readonly="true" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						联系方式
					</td>
					<td class="td_type">
						<input type="text" id="CELLPHONE0" name="CELLPHONE0" class="required textInput" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						所属单位
					</td>
					<td class="td_type" colspan="3">
						<input type="text" id="DEPT_NAME0" name="DEPT_NAME0" class="textInput" maxlength="80" size="80"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						评价等级
					</td>
					<td class="td_type">
						<select name="EVS_GRADE0">
							<option value="">请选择</option>
				   			<c:forEach items="${grade}" var="item" >
				   				<option value="${item.CODE_NO}">${item.CODENAME}</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_title" >
						黑名单与否
					</td>
					<td class="td_type">
						<select name="EVENT_STORE_CODE0">
							<option value="">请选择</option>
					   		<option value="Y">Y</option>
					   		<option value="N">N</option>
				   		</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						工作天数
					</td>
					<td class="td_type">
						<input type="text" id="WORK_DAYS0" name="WORK_DAYS0" class="number required textInput" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						应发工资
					</td>
					<td class="td_type">
						<input type="text" id="EVENT_SALARY0" name="EVENT_SALARY0" class="number required textInput" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						银行帐号
					</td>
					<td class="td_type">
						<input type="text" id="BANK_NO0" name="BANK_NO0" class="number textInput" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
					</td>
					<td class="td_type">
					</td>
				</tr>
			</table>
			</div>
			</div>
		    <input type="hidden" name="count" id="tempSalesEmpInfoCount" value="0">
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>