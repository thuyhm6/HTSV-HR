<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var aa=$("#localname").val();
	var bb=$("#localempid").val();
	var cc=$("#localpostgradenoname").val();
	var dd=$("#localpostgradeotherinf").val();
	var ff=$("#localcenter").val();
	var gg=$("#localempofficename").val();
	if(gg!=""&&typeof(gg)!="undefined"){
		$('#titlename').html(aa+" / "+bb+" / "+cc+"("+dd+") / "+ff+" / "+gg);
	}
	if('${POST_FAMILY}'!=''&&'${GRADE_NO}'!=''){
		codeRelation('${POST_FAMILY}','GRADE_NO','${GRADE_NO}');
	}
	var dataSearch='${dataSearch}';
	if('${lowerDepart}'=='Y'){
		$('#lowerDepart_'+dataSearch).attr('checked','checked');
		$('#lowerDepart_'+dataSearch).attr('value','Y');
	}
	if('${MAIN_LIAISON_OFFICE}'=='Y'){
		$('#mainLianluo').attr('checked','checked');
	}
	if('${FINAL_DEGREE_WHETHER}'=='Y'){
		$('#FINAL_DEGREE_WHETHER').attr('value','Y');
		$('#FINAL_DEGREE_WHETHER').attr('checked','checked');
	}
	changeTiaojian();
});
function fangdajing17(flag){
	 var name=encodeURI(encodeURI($('#seach_KEY17').val()));
	 var dataSearch=$('#beginSearch').val();
	$('#fangda17').attr('href','/hrm/empinfo/viewEmpInfoListTanchu?dataSearch='+dataSearch+'&pageNum=1&firstFlag=N&searchChange=informationSearch&seach_KEY='+name);
	if(flag == 'onkeyup')
		$('#fangda17').click();
}

function chaxun(aa){
	if(aa=='emergencyAddress'){
		$('#emergencyAddressSearch').submit();
	}else if(aa=='family'){
		$('#familySearch').submit();
	}else if(aa=='experience'){
		$('#experienceSearch').submit();
	}else if(aa=='education'){
		$('#educationSearch').submit();
	}else if(aa=='bid'){
		$('#bidSearch').submit();
	}else if(aa=='grade'){
		$('#gradeSearch').submit();
	}else if(aa=='address'){
		$('#addressSearch').submit();
	}else if(aa=='recognition'){
		$('#recognitionSearch').submit();
	}else if(aa=='punishment'){
		$('#punishmentSearch').submit();
	}else if(aa=='retire'){
		$('#retireSearch').submit();
	}
	
}
function chooseLower(aa){
	var che=$('#lowerDepart_'+aa).prop('checked');
	if(che==true){
		$('#lowerDepart_'+aa).attr('value','Y');
	}else{
		$('#lowerDepart_'+aa).attr('value','N');
	}
}
function changeTiaojian(){
	allHidden();
	var tiaojian=$('#dataDistinguish').val();
	if(tiaojian=='14014397'){//紧急联系地址
		$('#emergencyAddress').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','emergencyAddress');
	}else if(tiaojian=='14014399'){
		$('#family').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','family');
	}else if(tiaojian=='14014400'){
		$('#experience').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','experience');
	}else if(tiaojian=='14014404'){
		$('#education').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','education');
	}else if(tiaojian=='14014403'){
		$('#bid').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','bid');
	}else if(tiaojian=='14014405'){
		$('#grade').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','grade');
	}else if(tiaojian=='14014398'){
		$('#address').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','address');
	}else if(tiaojian=='14014406'){
		$('#recognition').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','recognition');
	}else if(tiaojian=='14014407'){
		$('#punishment').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','punishment');
	}else if(tiaojian=='14014408'){
		$('#retire').attr('style','width: 98.5%;margin-left:auto;margin-right:auto;');
		$('#beginSearch').attr('value','retire');
	}
	
}
function allHidden(){
	$('#emergencyAddress').attr('style','display:none');
	$('#family').attr('style','display:none');
	$('#experience').attr('style','display:none');
	$('#education').attr('style','display:none');
	$('#bid').attr('style','display:none');
	$('#grade').attr('style','display:none');
	$('#address').attr('style','display:none');
	$('#recognition').attr('style','display:none');
	$('#punishment').attr('style','display:none');
	$('#retire').attr('style','display:none');
}
function rendingxueli(){
	var red=$('#FINAL_DEGREE_WHETHER').prop('checked');
	if(red==true){
		$('#FINAL_DEGREE_WHETHER').attr('value','Y');
	}else{
		$('#FINAL_DEGREE_WHETHER').attr('value','');
	}
}
//发令区分和发令原因的联动
function codeReasonInfor(){
	var parentno=$('#TRANS_CODE').val();
	$.ajax({
		type:'post',
		dateType:'json',
		url:'/hrm/empinfo/codeReason',
		async:false,
		data:{parentno:parentno},
	 success:function(data){
		 var codereasonlist=data.codeReasonList;
		 if(codereasonlist!=""){
			 var str='';
				 str='<select name="TRANS_REASON" id="TRANS_REASON">';
			 var s='';
			 for(var i=0;i<codereasonlist.length;i++){
				 var codeno=codereasonlist[i]['CODE_NO'];
				 var content=codereasonlist[i]['CONTENT'];
				 s=s+'<option value="'+codeno+'">'+content+'</option>';
			 }
			  var strlast='</select>';
			  str=str+s+strlast;
			  $('#falingyuanyin').html(str);
		 }else{
			 $('#falingyuanyin').html('');
		 }
	 }
	});
}
</script>
<div class="pageContent" >

<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table">
			
	<tr>
		<td class="td_title"  style="width: 5%"><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td class="td_type"  style="width: 5%"><input
			type="text" name="seach_KEY17" id="seach_KEY17" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)fangdajing17('onkeyup');"/>
			</td>
			<td class="td_type" >
			<a class="btnLook" id="fangda17" onclick="fangdajing17('1')" href="/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=informationSearch" lookupGroup="person">
			</a>
			<span style="margin-left: 50px;" id="titlename">${LOCAL_TITLE }</span>
		</td>
	</tr>
	<tr>
	<td class="td_title"><!-- 信息区分 --><spring:message code="hrm.empinfo.NEWS_DIFFERENTIATE"/></td>
	<td width="35%" class="td_type" colspan='3'>
	<ait:SelectSyCodeByCpnyID name="dataDistinguish" id="dataDistinguish" 
                        parentNo="14014396" cnpyID="${defaultCpny}" selected="${dataDistinguish }" limit="all" 
                        onChangeName="changeTiaojian()" />
    </td>
	</tr>
</table>
                    <input type="hidden" id="localname" value="${personinfo.LOCAL_NAME }">
					<input type="hidden" id="localempid" value="${personinfo.EMPID }">
					<input type="hidden" id="localpostgradenoname" value="${personinfo.POST_GRADE_NO_NAME_TITLE }">
					<input type="hidden" id="localpostgradeotherinf" value="${personinfo.POST_GRADE_OTHERINF }">
					<input type="hidden" id="localcenter" value="${personinfo.COST_CENTER_TITLE }">
					<input type="hidden" id="localempofficename" value="${personinfo.EMP_OFFICE_NAME_TITLE }">
			<input type="hidden" id="beginSearch" value="emergencyAddress">
</div>
<!-- 紧急联络处搜索 -->
<div  id="emergencyAddress" style="width: 98.5%;margin-left:auto;margin-right:auto;">
<form id="emergencyAddressSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);" >
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="emergencyAddress">
		  <tr>
		     <td width="5%" class="td_title" >
					<!-- 主要联络处与否  --><spring:message code="hrm.empinfo.MAIN_CONTACT_AREA"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="radio" name="MAIN_LIAISON_OFFICE" id="allLianluo" value="" checked="checked"><!-- 全部 --><spring:message code="hrm.empinfo.ALL"/>
					<input type="radio" name="MAIN_LIAISON_OFFICE" id="mainLianluo" value="Y"><!-- 主要联络处 --><spring:message code="hrm.empinfo.ZHUYAOLIANLUOCHU"/>
                    </td>
             <td width="5%" class="td_title" >
					<!-- 关系 --><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" colspan='3'>
					<ait:SelectSyCodeByCpnyID name="EMER_TYPE_CODE" id="EMER_TYPE_CODE" 
                                 parentNo="1693" cnpyID="${defaultCpny}" selected="${EMER_TYPE_CODE}" limit="all" />
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					<!-- 部门  --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO10" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO10"/>
										<ait:deptTreeIcon name="ISDEPTNO10" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO10" selected="${ISDEPTNO10}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_emergencyAddress" value="N" onclick="chooseLower('emergencyAddress')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职级 --><spring:message code="hrm.contract.Rank"/>
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME" />
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工状态 --><spring:message code="hrm.empinfo.EMP_OFFICE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					<!-- 入社日 --><spring:message code="hrm.empinfo.ATTEND_DATE"/>
				</td>
			<td width="5%" class="td_type" colspan='5'>
			<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('emergencyAddress')" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span><!-- 印刷 --><spring:messsage code="hrm.approve.PRINTING"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=46"><span><!-- 导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT"/></span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.name"/></td>
		 <td width="5%" class="td_title"><!-- 社号 --><spring:message code="hrm.empinfo.empid"/></td>
		 <td width="5%" class="td_title"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></td>
		 <td width="5%" class="td_title"><!-- 职级 --><spring:message code="hrm.contract.Rank"/></td>
		 <td width="5%" class="td_title"><!-- 关系 --><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME"/></td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME"/></td>
		 <td width="5%" class="td_title"><!-- 主要联络处 --><spring:message code="hrm.empinfo.ZHUYAOLIANLUOCHU"/></td>
		 <td width="5%" class="td_title"><!-- 联系电话 --><spring:message code="hrm.empinfo.FAM_PHONE"/></td>
		 <td width="5%" class="td_title"><!-- E-Mail --><spring:message code="hrm.empinfo.EMAIL"/></td>
		 <td width="5%" class="td_title"><!-- 地址 --><spring:message code="hrm.empinfo.FAM_ADDRESS"/></td>
		 </tr>
		 <c:forEach items="${emergencyAddress }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.EMER_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.EMER_NAME }</td>
		 <td class="td_type" >${a.MAIN_LIAISON_OFFICE_NAME }</td>
		 <td class="td_type" >${a.EMER_CELLPHONE}</td>
		 <td class="td_type" >${a.EMER_EMAIL }</td>
		 <td class="td_type" >${a.EMER_ADDRESS }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 家庭搜索 -->		
<div  id="family" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="familySearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="family">
		 <tr>
		     <td width="5%" class="td_title" >
					<!-- 关系 --><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="FAM_TYPE_CODE" id="FAM_TYPE_CODE"
                                 parentNo="950" cnpyID="${defaultCpny}" selected="${FAM_TYPE_CODE}" limit="all" />
                    </td>
             <td width="5%" class="td_title" >
					<!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME"/>
				</td>
				<td width="5%" class="td_type" colspan='3'>
					<input type="type" name="FAM_NAME" id="FAM_NAME" value="${FAM_NAME }">
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					<!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO11" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO11"/>
										<ait:deptTreeIcon name="ISDEPTNO11" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO11" selected="${ISDEPTNO11}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_family" value="N" onclick="chooseLower('family')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职级 --><spring:message code="hrm.contract.Rank"/>
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" />
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工状态 --><spring:message code="hrm.empinfo.EMP_OFFICE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					<!-- 入社日 --><spring:message code="hrm.empinfo.ATTEND_DATE"/>
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		   
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('family')" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=47"><span><!-- 导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT"/></span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME"/></td>
		 <td width="5%" class="td_title"><!-- 社号 --><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></td>
		 <td width="5%" class="td_title"><!-- 职级 --><spring:message code="hrm.contract.Rank"/></td>
		 <td width="5%" class="td_title"><!--员工类型 --><spring:message code="org.title.EMP_TYPE"/>  </td>
		 <td width="5%" class="td_title"><!-- 性别 --><spring:message code="hrm.empinfo.SEXCODE"/></td>
		 <td width="5%" class="td_title"><!-- 入社日期 --><spring:message code="hrm.empinfo.DATE_STARTED"/></td>
		 <td width="5%" class="td_title"><!-- 关系 --><spring:message code="hrm.empinfo.FAM_TYPE_CODE_NAME"/></td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.FAM_NAME"/></td>
		 <td width="5%" class="td_title"><!-- 年龄 --><spring:message code="hrm.empinfo.AGE"/></td>
		 <td width="5%" class="td_title"><!-- 出生日期 --><spring:message code="hrm.empinfo.FAM_BORNDATE"/></td>
		 <td width="5%" class="td_title"><!-- 家庭电话 --><spring:message code="hrm.empinfo.FAM_FAMILY_PHONE"/></td>
		 <td width="5%" class="td_title"><!-- 学历 --><spring:message code="hrm.empinfo.DEGREE_CODE"/></td>
		 <td width="5%" class="td_title"><!-- 工作单位 --><spring:message code="hrm.empinfo.FAM_COMPANY_NAME"/></td>
		 </tr>
		 <c:forEach items="${familySearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.EMP_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.SEXCODE_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.FAM_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.FAM_NAME }</td>
		 <c:if test="${a.AGE>0 }">
		 <td class="td_type" >${a.AGE }</td>
		 </c:if>
		  <c:if test="${a.AGE==0 }">
		 <td class="td_type" ></td>
		 </c:if>
		 <td class="td_type" >${a.FAM_BORNDATE }</td>
		 <td class="td_type" >${a.FAM_FAMILY_PHONE }</td>
		 <td class="td_type" >${a.FAM_EDUCATION_NAME }</td>
		 <td class="td_type" >${a.FAM_COMPANY_NAME }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 经历搜索 -->		
<div  id="experience" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="experienceSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="experience">
		 <tr>
		     <td width="5%" class="td_title" >
					<!-- 期间 --><spring:message code="hrm.empinfo.Period"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="START_DATE_EXP" name="START_DATE_EXP" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE_EXP}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_EXP" name="END_DATE_EXP" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE_EXP}" style="float:left;"/>           
                    </td>
             <td width="5%" class="td_title" >
					<!-- 原单位名称 --><spring:message code="hrm.empinfo.YUANDANWEIMINGCHENG"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="type" name="CPNY_NAME" id="CPNY_NAME" value="${CPNY_NAME }">
                    </td>
                     <td width="5%" class="td_title" >
					 <!-- 经历职务 --> <spring:message code="hrm.empinfo.JINGLIZHIWU"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="type" name="POSITION" id="POSITION" value="${POSITION }">
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					<!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO12" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO12"/>
										<ait:deptTreeIcon name="ISDEPTNO12" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO12" selected="${ISDEPTNO12}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_experience" value="N" onclick="chooseLower('experience')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职级 --><spring:message code="hrm.contract.Rank"/>
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					<!-- 主要业务 --><spring:message code="org.title.MAIN_BUSINESS"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工状态 --><spring:message code="hrm.empinfo.Working_status"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					<!-- 入社日 --><spring:message code="hrm.empinfo.ATTEND_DATE"/>
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		   
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('experience')" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=48"><span><!-- 导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT"/></span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code=""/></td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --></td>
		 <td width="5%" class="td_title"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></td>
		 <td width="5%" class="td_title"><!-- 职级 --><spring:message code="hrm.contract.Rank"/></td>
		 <td width="5%" class="td_title"><!-- 开始日期 --><spring:message code="hrm.recruitManage.START_DATE1"/></td>
		 <td width="5%" class="td_title"><!-- 结束日期 --><spring:message code="hrm.recruitManage.END_DATE1"/></td>
		 <td width="5%" class="td_title"><!-- 原单位名称 --><spring:message code="hrm.empinfo.YUANDANWEIMINGCHENG"/></td>
		 <td width="5%" class="td_title"><!-- 经历职务 --><spring:message code="hrm.empinfo.JINGLIZHIWU"/>  </td>
		 <td width="5%" class="td_title"><!-- 前职场年薪 --><spring:message code="hrm.recruitManage.old_PAYROLL"/></td>
		 </tr>
		 <c:forEach items="${experienceSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.START_DATE }</td>
		 <td class="td_type" >${a.END_DATE }</td>
		 <td class="td_type" >${a.CPNY_NAME }</td>
		 <td class="td_type" >${a.POSITION }</td>
		 <td class="td_type" >${a.YEAR_PAY }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 学历搜索 -->		
<div  id="education" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="educationSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="education">
		 <tr>
		     <td width="5%" class="td_title" >
					<!-- 期间 --><spring:message code="hrm.empinfo.Period"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="START_DATE_EDU" name="START_DATE_EDU" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
					  value="${START_DATE_EDU}" style="float:left;"/>
					<div style="float:left;">~</div>
					<input type="text" id="END_DATE_EDU" name="END_DATE_EDU" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
					   value="${END_DATE_EDU}" style="float:left;"/>
                    </td>
             <td width="5%" class="td_title" >
					<!-- 学历 --><spring:message code="hrm.empinfo.DEGREE_CODE"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="DEGREE_CODE" selected="${DEGREE_CODE}" parentNo="13769" limit="all"/>
                    </td>
                <td width="5%" class="td_title" >
					<!-- 认定学历与否 --><spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="checkbox" name="FINAL_DEGREE_WHETHER" id="FINAL_DEGREE_WHETHER" value="" onclick="rendingxueli()">
                    </td>
		   </tr>
		   <tr>
		    <td width="5%" class="td_title" >
					<!-- 学校名 --><spring:message code="hrm.recruitManage.INSTITUTION_NAME"/>
				</td>
				<td width="5%" class="td_type" >
				<input type="text" name="INSTITUTION_NAME" id="INSTITUTION_NAME" value="${INSTITUTION_NAME }">
				</td>
			<td width="5%" class="td_title" >
				<!-- 专业 --><spring:message code="hrm.recruitManage.SUBJECT"/>
			</td>
			<td width="5%" class="td_type" >
			<input type="text" name="SUBJECT" id="SUBJECT" value="${SUBJECT }">
			</td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					<!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO13" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO13"/>
					<ait:deptTreeIcon name="ISDEPTNO13" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO13" selected="${ISDEPTNO13}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_education" value="N" onclick="chooseLower('education')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职级 --><spring:message code="hrm.contract.Rank"/>
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工状态 --><spring:message code="hrm.empinfo.EMP_OFFICE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					<!-- 入社日 --><spring:message code="hrm.empinfo.ATTEND_DATE"/>
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		   
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('education')" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=49"><span><!-- 导出到EXECL --><spring:message code="hrm.empinfo.EXPORT"/></span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.name"/></td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --> </td>
		 <td width="5%" class="td_title"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/> </td>
		 <td width="5%" class="td_title"><!-- 职级 --><spring:message code="hrm.contract.Rank"/></td>
		 <td width="5%" class="td_title"><!-- 学历 --><spring:message code="hrm.empinfo.DEGREE_CODE"/></td>
		 <td width="5%" class="td_title"><!-- 开始年月 --><spring:message code="hrm.empinfo.START_YEAR_MONTH"/></td>
		 <td width="5%" class="td_title"><!-- 结束年月 --><spring:message code="hrm.empinfo.END_YEAR_MONTH"/></td>
		 <td width="5%" class="td_title"><!-- 毕业学校 --><spring:message code="hrm.recruitManage.INSTITUTION_SCHOOL"/></td>
		 <td width="5%" class="td_title"><!-- 专业 --><spring:message code="hrm.recruitManage.SUBJECT"/></td>
		 </tr>
		 <c:forEach items="${educationSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.DEGREE_CODE_NAME }</td>
		 <td class="td_type" >${a.START_DATE }</td>
		 <td class="td_type" >${a.END_DATE }</td>
		 <td class="td_type" >${a.INSTITUTION_NAME }</td>
		 <td class="td_type" >${a.SUBJECT }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 资格搜索 -->		
<div  id="bid" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="bidSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="bid">
		 <tr>
		     <td width="5%" class="td_title" >
					<!-- 期间 --><spring:messagge code="hrm.empinfo.Period"/>
				</td>
				<td width="5%" class="td_type" >
					   <input type="text" id="START_DATE_BID" name="START_DATE_BID" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE_BID}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_BID" name="END_DATE_BID" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE_BID}" style="float:left;"/>        
                    </td>
             <td width="5%" class="td_title" >
					<!-- 资格 --><spring:message code="hrm.empinfo.Qualification"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="text" name="QUAL_NAME" id="QUAL_NAME" value="${QUAL_NAME }">
                    </td>
                <td width="5%" class="td_title" >
					<!-- 资格等级 --><spring:message code="hrm.empinfo.Qualification_grade"/>
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="QUAL_LEVEL" name="QUAL_LEVEL" value="${QUAL_LEVEL}">
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					<!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO14" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO14"/>
					<ait:deptTreeIcon name="ISDEPTNO14" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO14" selected="${ISDEPTNO14}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_bid" value="N" onclick="chooseLower('bid')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 职级 --><spring:message code="hrm.contract.Rank"/>
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					<!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/>
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					<!-- 员工状态 --><spring:message code="hrm.empinfo.Working_status"/>
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					<!--入社日  --><spring:message code="hrm.empinfo.ATTEND_DATE"/>
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('bid')" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span><!-- 印刷 --><spring:message code="hrm.approve.PRINTING"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=50"><span><!-- 导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT"/></span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title"><!-- 姓名 --><spring:message code="hrm.empinfo.name"/></td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid"/><!-- 社号 --> </td>
		 <td width="5%" class="td_title"><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></td>
		 <td width="5%" class="td_title"><!-- 职级 --><spring:message code="hrm.contract.Rank"/></td>
		 <td width="5%" class="td_title"><!-- 资格 --><spring:message code="hrm.empinfo.Qualification"/></td>
		 <td width="5%" class="td_title"><!-- 获证日期 --><spring:message code="hrm.empinfo.award_date"/></td>
		 <td width="5%" class="td_title"><!-- 有效日期 --><spring:message code="hrm.empinfo.Valid_date"/></td>
		 <td width="5%" class="td_title"><!-- 资格等级 --><spring:message code="hrm.empinfo.Qualification_grade"/></td>
		 <td width="5%" class="td_title"><!-- 发证机关 --><spring:message code="hrm.empinfo.Issuing_authority"/></td>
		 </tr>
		 <c:forEach items="${bidSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.QUAL_NAME }</td>
		 <td class="td_type" >${a.DATE_OBTAINED }</td>
		 <td class="td_type" >${a.VALIDITY_DATE }</td>
		 <td class="td_type" >${a.QUAL_LEVEL }</td>
		 <td class="td_type" >${a.QUAL_INSTITUTE }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 职级搜索-->		
<div  id="grade" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="gradeSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="grade">
		 <tr>
		    <td width="5%" class="td_title" >
					部门
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO15" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO15"/>
					<ait:deptTreeIcon name="ISDEPTNO15" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO15" selected="${ISDEPTNO15}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_grade" value="N" onclick="chooseLower('grade')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					职群
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					职级
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		   <tr>
		   <td width="5%" class="td_title" >
					员工状态
		   </td>
		   <td width="5%" class="td_type" >
		   <ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
		   </td>
		   <td width="5%" class="td_title" >
					入社日
				</td>
				<td width="5%" class="td_type" colspan='3'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">

	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('grade')" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span>印刷</span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=51"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title">姓名</td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title">部门</td>
		 <td width="5%" class="td_title">产品类型</td>
		 <td width="5%" class="td_title">职级</td>
		 <td width="5%" class="td_title">级别</td>
		 <td width="5%" class="td_title">职责</td>
		 <td width="5%" class="td_title">入社日</td>
		 <td width="5%" class="td_title">提升日</td>
		 </tr>
		 <c:forEach items="${gradeSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.PRODUCT_TYPE_NAME }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.RANK_STATISTICS_NAME }</td>
		 <td class="td_type" >${a.POSITION_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.PROMOTION_DAY }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>		
<!-- 地址搜索 -->		
<div  id="address" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="addressSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="address">
		 <tr>
		    <td width="5%" class="td_title" >
					部门
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO16" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO16"/>
					<ait:deptTreeIcon name="ISDEPTNO16" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO16" selected="${ISDEPTNO16}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_address" value="N" onclick="chooseLower('address')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					职群
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					职级
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					主要业务
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工类型
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工状态
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					入社日
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('address')" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span>印刷</span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=52"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title">姓名</td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title">部门</td>
		 <td width="5%" class="td_title">职级</td>
		 <td width="5%" class="td_title">地址类型</td>
		 <td width="5%" class="td_title">地址</td>
		 <td width="5%" class="td_title">有效开始日</td>
		 </tr>
		 <c:forEach items="${addressSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.ADDRESS_TYPE_NAME }</td>
		 <td class="td_type" >${a.ADDRESS_CONTENT }</td>
		 <td class="td_type" >${a.EFFECTIVE_START_DATE }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>
<!-- 表彰搜索 -->		
<div  id="recognition" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="recognitionSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="recognition">
		 <tr>
		     <td width="5%" class="td_title" >
					期间
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="START_DATE_REC" name="START_DATE_REC" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE_REC}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_REC" name="END_DATE_REC" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE_REC}" style="float:left;"/>           
                    </td>
             <td width="5%" class="td_title" >
					表扬(得奖)名
				</td>
				<td width="5%" class="td_type" colspan='3'>
					<ait:SelectSyCodeByCpnyID name="REWARD_TYPE" id="REWARD_TYPE"
                                 parentNo="14014334" cnpyID="${defaultCpny}" selected="${REWARD_TYPE}" limit="all" />
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					部门
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO17" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO17"/>
					<ait:deptTreeIcon name="ISDEPTNO17" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO17" selected="${ISDEPTNO17}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_recognition" value="N" onclick="chooseLower('recognition')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					职群
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					职级
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					主要业务
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工类型
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工状态
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					入社日
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('recognition')" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span>印刷</span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=53"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title">姓名</td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title">部门</td>
		 <td width="5%" class="td_title">员工类型</td>
		 <td width="5%" class="td_title">职级</td>
		 <td width="5%" class="td_title">入社日</td>
		 <td width="5%" class="td_title">状态</td>
		 <td width="5%" class="td_title">表扬(得奖)名</td>
		 <td width="5%" class="td_title">表扬(得奖)日</td>
		 <td width="5%" class="td_title">授予机关</td>
		 <td width="5%" class="td_title">奖金</td>
		 <td width="5%" class="td_title">奖金支付类型代码</td>
		 <td width="5%" class="td_title">备注</td>
		 <td width="5%" class="td_title">人事卡查询与否</td>
		 </tr>
		 <c:forEach items="${recognitionSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.EMP_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.EMP_OFFICE_NAME }</td>
		 <td class="td_type" >${a.REWARD_TYPE_NAME }</td>
		 <td class="td_type" >${a.REWARD_DATE }</td>
		 <td class="td_type" >${a.REWARD_CNPY }</td>
		 <td class="td_type" >${a.REWARD }</td>
		 <td class="td_type" >${a.REWARD_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.REMARKS }</td>
		 <td class="td_type" >${a.PERSONNEL_CARD_INQUIRY_NAME }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>		
<!-- 惩戒搜索 -->		
<div  id="punishment" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="punishmentSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="punishment">
		 <tr>
		     <td width="5%" class="td_title" >
					期间
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="START_DATE_PUN" name="START_DATE_PUN" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE_PUN}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_PUN" name="END_DATE_PUN" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE_PUN}" style="float:left;"/>           
                    </td>
             <td width="5%" class="td_title" >
					惩罚名
				</td>
				<td width="5%" class="td_type" colspan='3'>
					<ait:SelectSyCodeByCpnyID name="PUNISH_CODE" id="PUNISH_CODE"
                                 parentNo="14014355" cnpyID="${defaultCpny}" selected="${PUNISH_CODE}" limit="all" />
                    </td>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					部门
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO18" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO18"/>
					<ait:deptTreeIcon name="ISDEPTNO18" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO18" selected="${ISDEPTNO18}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_punishment" value="N" onclick="chooseLower('punishment')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					职群
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					职级
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					主要业务
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工类型
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工状态
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					入社日
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('punishment')" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span>印刷</span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=54"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title">姓名</td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title">部门</td>
		 <td width="5%" class="td_title">员工类型</td>
		 <td width="5%" class="td_title">职级</td>
		 <td width="5%" class="td_title">入社日</td>
		 <td width="5%" class="td_title">状态</td>
		 <td width="5%" class="td_title">惩罚名</td>
		 <td width="5%" class="td_title">惩罚日</td>
		 <td width="5%" class="td_title">惩罚机关名</td>
		 <td width="5%" class="td_title">减薪开始日</td>
		 <td width="5%" class="td_title">减薪结束日</td>
		 <td width="5%" class="td_title">解除日</td>
		 <td width="5%" class="td_title">惩罚原因</td>
		 <td width="5%" class="td_title">人事卡查询与否</td>
		 </tr>
		 <c:forEach items="${punishmentSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.EMP_TYPE_CODE_NAME }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.EMP_OFFICE_NAME }</td>
		 <td class="td_type" >${a.PUNISH_CODE_NAME }</td>
		 <td class="td_type" >${a.PUNISH_DATE}</td>
		 <td class="td_type" >${a.PUNISH_DEPARTMENT }</td>
		 <td class="td_type" >${a.PAYCUT_START_DATE }</td>
		 <td class="td_type" >${a.PAYCUT_END_DATE }</td>
		 <td class="td_type" >${a.RELEASE_DATE }</td>
		 <td class="td_type" >${a.PUNISH_REASON }</td>
		 <td class="td_type" >${a.PERSONNEL_CARD_INQUIRY_NAME }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>		
<!-- 退职搜索 -->		
<div  id="retire" style="width: 98.5%;margin-left:auto;margin-right:auto;display:none">
<form id="retireSearch" method="post" action="/hrm/empinfo/informationSearch" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
		 <input type="hidden" id="dataSearch" name="dataSearch" value="retire">
		 <tr>
		     <td width="5%" class="td_title" >
					期间
				</td>
				<td width="5%" class="td_type" >
					<input type="text" id="START_DATE_RET" name="START_DATE_RET" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE_RET}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE_RET" name="END_DATE_RET" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE_RET}" style="float:left;"/>           
                    </td>
             <td width="5%" class="td_title" >
					人事命令
				</td>
				<td width="5%" class="td_type" >
					<select name="TRANS_CODE" id="TRANS_CODE" onchange="codeReasonInfor()">
					<option value="">请选择</option>
					<option value="14013964">外包退职</option>
					<option value="14013965">被动退职</option>
					<option value="14013966">主动离职</option>
					</select>
                    </td>
	        <td width="5%" class="td_title" >
				发令细节名
			</td>
			<td width="5%" class="td_type" >
			<div id="falingyuanyin">
	        </td>
	        </div>
		   </tr>
		 <tr>
		    <td width="5%" class="td_title" >
					部门
				</td>
				<td width="8%" class="td_type" >
					<ait:deptList name="ISDEPTNO19" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO19"/>
					<ait:deptTreeIcon name="ISDEPTNO19" cpnyId="${defaultCpny}" limit="super" id="ISDEPTNO19" selected="${ISDEPTNO19}"/>
                    <input type="checkbox" name="lowerDepart" id="lowerDepart_retire" value="N" onclick="chooseLower('retire')">下位部门包括
                    </td>
                    <td width="5%" class="td_title" >
					职群
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14014288" onChangeName="codeRelation(this.value,'GRADE_NO','${GRADE_NO}');"
									   	 limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					职级
				</td>
				<td width="5%" class="td_type" >
					<select name="GRADE_NO" id="GRADE_NO" >
					</select>
                    </td>
		   </tr>
		   
		    <tr>
		    <td width="5%" class="td_title" >
					主要业务
				</td>
				<td width="8%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="MAIN_BUSINESS" id="MAIN_BUSINESS"
                                    parentNo="400098" cnpyID="${defaultCpny}" selected="${MAIN_BUSINESS}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工类型
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
                    </td>
                    <td width="5%" class="td_title" >
					员工状态
				</td>
				<td width="5%" class="td_type" >
					<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
                    </td>
		   </tr>
		   <tr>
		   <td width="5%" class="td_title" >
					入社日
				</td>
				<td width="5%" class="td_type" colspan='5'>
					<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			  value="${START_DATE}" style="float:left;"/>
			<div style="float:left;">~</div>
			<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
			   value="${END_DATE}" style="float:left;"/>
                    </td>
		   </tr>
		   
		 </table>
		 <div class="formBar">
	<ul class="toolBar">
		<c:if test="${toolbarInfo.INSERTR == '1'}">
		</c:if>
			<li>
				<a class="buttonActive" onclick="chaxun('retire')" href="#"><span>查询</span></a>
			</li>
			<li>
				<a class="add" href="#" onclick="window.print()"><span>印刷</span></a>
			</li>
			<li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=55"><span>导出到EXECL</span></a>					
			</li>
	</ul>
</div>
		 <span style="float:left;font-size: 15px">Total:${count }</span>
		 <table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
		 <tr>
		 <td width="5%" class="td_title">NO.</td>
		 <td width="5%" class="td_title">姓名</td>
		 <td width="5%" class="td_title"><spring:message code="hrm.empinfo.empid" /></td>
		 <td width="5%" class="td_title">部门</td>
		 <td width="5%" class="td_title">职级</td>
		 <td width="5%" class="td_title">入社日期</td>
		 <td width="5%" class="td_title">退职日期</td>
		 <td width="5%" class="td_title">退职区分</td>
		 <td width="5%" class="td_title">退休原因代码</td>
		 <td width="5%" class="td_title">联系电话</td>
		 <td width="5%" class="td_title">主要业务</td>
		 <td width="5%" class="td_title">备注</td>
		 </tr>
		 <c:forEach items="${retireSearch }" var="a" varStatus="i">
		 <tr>
		 <td class="td_type" >${i.count }</td>
		 <td class="td_type" >${a.LOCAL_NAME }</td>
		 <td class="td_type" >${a.EMPID }</td>
		 <td class="td_type" >${a.ORG_NAME_LOCAL }</td>
		 <td class="td_type" >${a.POST_GRADE_NO_NAME }</td>
		 <td class="td_type" >${a.DATE_STARTED }</td>
		 <td class="td_type" >${a.DATE_RETIRE }</td>
		 <td class="td_type" >${a.TRANS_CODE_NAME }</td>
		 <td class="td_type" >${a.TRANS_REASON_NAME }</td>
		 <td class="td_type" >${a.CELLPHONE }</td>
		 <td class="td_type" >${a.MAIN_BUSINESS }</td>
		 <td class="td_type" >${a.REMARK }</td>
		 </tr>
		 </c:forEach> 
		 </table>
	</form>	
		</div>								
						