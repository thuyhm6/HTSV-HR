<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>

	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table" >';
	   		htm+='<tr>';
	   		htm+='<td align="right" class="td_type"><img src="/resources/images/button/Delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='<td align="right" class="td_title"><spring:message code="ess.infoApply.title.startTime"/></td>';
	   		htm+='<td align="left" class="td_type"><input id="fh' + i + '" name="fh' + i + '" type="text" class="digits required" size="3" maxlength="2" min="0" max="23" >:<input id="fm' + i + '" name="fm' + i + '" type="text" class="digits required" size="3" maxlength="2" min="0" max="59"></td>';
	   		htm+='<td align="right" class="td_title"><spring:message code="ess.infoApply.title.endTime"/></td>';
	   		htm+='<td align="left" class="td_type"><input id="th' + i + '" name="th' + i + '" type="text" class="digits required" size="3" maxlength="2"  min="0" max="23" >:<input id="tm' + i + '" name="tm' + i + '" type="text" class="digits required" size="3" maxlength="2" min="0" max="59"></td>';

	   		htm+='<td align="center" class="td_type"><select id="itemNo' + i + '" name="itemNo' + i + '">' ;
	   			<c:forEach items="${itemList}" var="item">
	   			htm+='<option value="${item.ITEM_NO}" <c:if test="${item.ITEM_NO eq 15}">selected</c:if>>${item.ITEM_NAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td >';
	   		htm+='<table width="100%"  border="0" cellspacing="0" cellpadding="0">';
	   		htm+='<tr>';
	   		htm+='<td align="right" class="l-table-edit-td"><spring:message code="ar.viewshift.title.start"/></td>';
	   		htm+='<td align="left" class="l-table-edit-td"><input type="radio" name="fday' + i + '" value="-1"><spring:message code="ar.viewshift.title.zuori"/>&nbsp;<input type="radio" name="fday' + i + '" value="0" checked><spring:message code="ar.viewshift.title.dangri"/>' ;
	   		htm+='<input type="radio" name="fday' + i + '" value="1"><spring:message code="ar.viewshift.title.ciri"/></td>' ;
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td align="right" class="l-table-edit-td"><spring:message code="ar.viewshift.title.end"/></td>';
	   		htm+='<td align="left" class="l-table-edit-td"><input type="radio" name="tday' + i + '" value="-1"><spring:message code="ar.viewshift.title.zuori"/>&nbsp;<input type="radio" name="tday' + i + '" value="0" checked><spring:message code="ar.viewshift.title.dangri"/>' ;
	   		htm+='<input type="radio" name="tday' + i + '" value="1"><spring:message code="ar.viewshift.title.ciri"/></td>' ;
	   		htm+='</tr>';
	   		htm+='</table>';
	   		htm+='</td>';
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$("#createTable").append(htm) ;
	   	
	   	count++;  
	    $("#count").attr("value",count) ;
    }
	function addDd(){
	    var restCount = parseInt($("#restCount").val());
	   
	    var i = restCount ;
	    var htm="";
	    var str="";
	    htm+='<dd>'
		if(i!=0){
			str="&nbsp;,&nbsp;";
		}
	    htm+=str+'<input id="OT_REST_TIME_START_H'+i+'" name="OT_REST_TIME_START_H'+i+'" type="text"  size="3" maxlength="2" min="0" max="23" >:'
	    htm+='</dd>'
	    htm+='<dd>'
	    htm+='&nbsp;<input id="OT_REST_TIME_START_M'+i+'" name="OT_REST_TIME_START_M'+i+'" type="text"  size="3" maxlength="2" min="0" max="59">'
	    htm+='</dd>'
	    htm+='<dd>'
	    htm+='&nbsp;~&nbsp;<input id="OT_REST_TIME_END_H'+i+'" name="OT_REST_TIME_END_H'+i+'" type="text"  size="3" maxlength="2" min="0" max="23" >:'
	    htm+='</dd>'
	    htm+='<dd>'
	    htm+='&nbsp;<input id="OT_REST_TIME_END_M'+i+'" name="OT_REST_TIME_END_M'+i+'" type="text"  size="3" maxlength="2" min="0" max="59">'
	    htm+='</dd>';
	   	

	   	$("#createDd").append(htm) ;
	   	restCount++;  
	    $("#restCount").attr("value",restCount) ;
    }
    /**
     * 保存
     */
	function f_save(form) {
		// 清空JSON数据
		$("#SHIFT_PARAMETER").attr("value","") ;
		if(!checkSave()){
			$("#SHIFT_PARAMETER").attr("value","") ;
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
			success: navTabAjaxDone || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	}

	/**
	 * 保存验证
	 */
	function checkSave(){
		if($.trim($("#createTable").html()).length <= 1){//"请添加班次的时间信息"
			alert('<spring:message code="ar.alert.message.viewshift.bancicanshuxinxin"/>');
      		return false ;
        }
      	var jsonData = '[' ;
      	for(var i=0; i< $("#count").val() ; i++){

          	if($("#fh" + i).length > 0 
						&& $("#fm" + i).length > 0
						&& $("#th" + i).length > 0
						&& $("#tm" + i).length > 0){
				
          		if($("#fh" + i).val() == null || isNaN($("#fh" + i).val())){//"请正确输入开始时间"
       				alert('<spring:message code="ar.alert.message.viewshift.makesurnstarttime"/>');
       				return false ;
       			}
       		
       			if($("#fm" + i).val() == null || isNaN($("#fm" + i).val())){//"请正确输入开始时间"
    	   			alert('<spring:message code="ar.alert.message.viewshift.makesurnstarttime"/>');
    	   			return false ;
       			}
       			
        		if($("#th" + i).val() == null || isNaN($("#th" + i).val())){//"请正确输入结束时间"
    	   			alert('<spring:message code="ar.alert.message.viewshift.makesurnendtime"/>');
    	   			return false ;
       			}
        	
       			if($("#tm" + i).val() == null || isNaN($("#tm" + i).val())){//"请正确输入结束时间"
    	   			alert('<spring:message code="ar.alert.message.viewshift.makesurnendtime"/>');
    	   			return false ;
       			}

    	   		if (jsonData.length > 1){
    	        	jsonData += ',{'
    	        }else{
    	        	jsonData += '{'
    	        }

    	        jsonData += ' "FROM_TIME": "' + $("#fh" + i).val() + ":" + $("#fm" + i).val() + '", ' ;
    	        jsonData += ' "TO_TIME": "' + $("#th" + i).val() + ":" + $("#tm" + i).val() + '", ' ;
    	        jsonData += ' "ITEM_NO": ' + $("#itemNo" + i).val() + ', ' ;

    	        // 找到以table0为id的table下面所有radio
    	        $("#table" + i).find(":radio").each(function (){
    	            if(this.checked){
    	               if(this.name.indexOf("fday") > -1){
    	            	   jsonData += ' "BEGIN_DAY_OFFSET": ' + this.value + ', ' ;
    	               }else{
    	            	   jsonData += ' "END_DAY_OFFSET": ' + this.value ;
    	               }
    	            } 
    	        });	
    	        jsonData += '}' ;
            }
      	
	   	}
	      	jsonData += ']' ;
		$("#SHIFT_PARAMETER").attr("value",jsonData) ;
		return true ;
    }

    function f_delShiftInfo(obj){
    	$("#"+obj+"").html('');
    }
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addShiftInfo" class="pageForm required-validate" onsubmit="return f_save(this)">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
		<div class="pageFormContent nowrap" layoutH="56">
			<ait:SyLanguage/>
			<dl>
				<dt><!-- 班次ID --><spring:message code="ar.viewshift.title.banciID"/></dt>
				<dd>
					<input type="text" name="SHIFT_ID" class="textInput required"/>
					<input name="SHIFT_PARAMETER" type="hidden" id="SHIFT_PARAMETER" />
					<input name="CPNY_ID" type="hidden" id="CPNY_ID" value="${defaultCpny}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 班次性质 --><spring:message code="ar.viewshift.title.bancixingzhi"/></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="DATATYPE" parentNo="1439" cnpyID="${defaultCpny}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 扣除时间 --><spring:message code="ar.viewshift.title.kouchushijian"/></dt>
				<dd>
					<input type="text" name="DEDUCT_TIME" class="number" size="3" maxlength="3"  min="0" max="23"/>
				</dd>
			</dl>
			
			
			
			
		    <dl>
				<dt><!-- 加班开始时间  --><spring:message code="ar.addShiftView.JIABANKAISHISHIJIAN.b"/></dt>
				<dd>
					<input id="OT_TIME_START_H" name="OT_TIME_START_H" type="text"  size="3" maxlength="2" min="0" max="23" >:
				</dd>
				<dd>
					<input id="OT_TIME_START_M" name="OT_TIME_START_M" type="text"  size="3" maxlength="2" min="0" max="59">
				</dd>
			</dl>
			<!--<dl>
				<dt style="float:left">  加班津贴  <spring:message code="ar.addShiftView.JIABANJINTIE.b"/> </dt>
				 
				 <dd>
			  <input type="text" name="OT_ALLOWANCE" class="number" size="10" maxlength="5"  min="0" max="5000"/>
			   	</dd>
			   
			</dl>
			-->
			<dl>
				<dt style="float:left"> <!-- 工作形态  --><spring:message code="ar.addShiftView.GONGZUOXINGTAI.b"/> </dt>
				<dd>
					<input id="F_WORK_TIME_H" name="F_WORK_TIME_H" type="text"  size="3" maxlength="2" min="0" max="23" >:
				</dd>
				<dd>
					<input id="F_WORK_TIME_M" name="F_WORK_TIME_M" type="text"  size="3" maxlength="2" min="0" max="59"> &nbsp;~&nbsp;
				</dd>
			   <dd>
					<input id="T_WORK_TIME_H" name="T_WORK_TIME_H" type="text"  size="3" maxlength="2" min="0" max="23" >:
				</dd>
				<dd>
					<input id="T_WORK_TIME_M" name="T_WORK_TIME_M" type="text"  size="3" maxlength="2" min="0" max="59">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 班次参数 --><spring:message code="ar.viewshift.title.bancicanshu"/></dt>
				<dd>
					<img src="/resources/images/button/Add_little.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/>	
				</dd>
			 
			</dl>
		
		
			<div id="createTable"></div>
		    <input type="hidden" name="count" id="count" value="0">
		    <input type="hidden" name="restCount" id="restCount" value="0">
		</div>
	</form>	
</div>