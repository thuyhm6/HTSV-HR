<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script>
	

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    
    function validateCallbackViewEducationInfo(form, callback) {
		
		var $form = $("#viewEducationInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("form[id='viewEducationInfo'] #count").val());
		
		for (i=0;i<count;i++){
			
			if($("form[id='viewEducationInfo'] #START_YEAR"+i) != null ){

				//var START_YEAR=document.getElementById("START_YEAR"+i).value;
				//var START_MONTH=document.getElementById("START_MONTH"+i).value;
				//var END_YEAR=document.getElementById("END_YEAR"+i).value;
				//var END_MONTH=document.getElementById("END_MONTH"+i).value;
				var START_YEAR = $("form[id='viewEducationInfo'] #START_YEAR"+i).val();
                var START_MONTH = $("form[id='viewEducationInfo'] #START_MONTH"+i).val();
                var END_YEAR = $("form[id='viewEducationInfo'] #END_YEAR"+i).val();
                var END_MONTH = $("form[id='viewEducationInfo'] #END_MONTH"+i).val();
                
				var date1 = START_YEAR+START_MONTH;
				var date2 = END_YEAR+END_MONTH;
				
				if (date1 - date2 > 0) {
					alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//开始时间不能晚于结束时间
					 $("#START_YEAR"+i).focus();
					return false;
				}
			}
			
		}
		//添加时只能有一个最终学历
		var finalFalg = "${FINALNUM}";
		//alert("finalFlag:::"+ finalFlag);
		var fcount=0;
		
		//添加时只能有一个最终学历
	    var fcount = 0;
	    for(i=0;i<count;i++){
	        var ultimate= $("form[id='viewEducationInfo'] #FINAL_DEGREE_WHETHER"+i).val();
	        if(ultimate=='Y'){
	            fcount = fcount + 1;
	        }
	    }
	    if(finalFalg == 1 && fcount > 0){
           alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.FinalDegree"/>');//"最终学历只能有一个"
           return false;
	    }
	    if(finalFalg != 1 && fcount != 1){
	    	alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.FinalDegree"/>');//"最终学历只能有一个"
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
	
	//根据传入的不同名称查询子idgetEducationName
	function getEducationName(id,sourceId,name,toname){
		id=id.replace(name,"");
    	if(sourceId == null || sourceId.length == 0){
    		return ;
    	}
    	
    	//original;
    	//var sel = $("#"+toname+id);
    	//new
    	var sel = $("form[id='viewEducationInfo'] #"+toname+id);
    	
    	sel.empty();
    	$.ajax({
    		 cache: false,
    		 type: 'post',
    		 url: "/hrm/transferOrder/getRecSourceDetailByRecSource?",
    		 data: 'PARENT_CODE_NO=' + sourceId,
    		 dataType:"json",
    		 success: function(data) {
    			$.each(data, function(key,value){
    					if($(data).size() > 0){
    						
    							sel.append('<option value='+value+'>'+key+'</option>'); 
    						
    					}
    			});
    		 }
    	});
    }
	
//jjy
function setClass(obj){
   var num = ($(obj).attr("id")).replaceAll("DEGREE_CODE","");
    //대학이상인 경우 변동 없음
    if($(obj).val()=="123337" || 
            $(obj).val()=="1666" || 
            $(obj).val()=="1667" || 
            $(obj).val()=="1668" ||
            $(obj).val()=="1669"){
    	var sel1 = $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY"+num);
        sel1.empty();

        $.ajax({
             cache: false,
             type: 'post',
             url: "/hrm/transferOrder/getRecSourceDetailByRecSource?",
             data: 'PARENT_CODE_NO=123412&limit=all',
             dataType:"json",
             success: function(data) {
                sel1.append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
                $.each(data, function(key,value){
                        if($(data).size() > 0){
                            sel1.append('<option value='+value+'>'+key+'</option>'); 
                        }
                });
             }
        });

        var sel2 = $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY_TWO"+num);
        sel2.empty();
        $.ajax({
            cache: false,
            type: 'post',
            url: "/hrm/transferOrder/getRecSourceDetailByRecSource?",
            data: 'PARENT_CODE_NO=123412&limit=all',
            dataType:"json",
            success: function(data) {
               sel2.append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>');
               $.each(data, function(key,value){
                       if($(data).size() > 0){
                           sel2.append('<option value='+value+'>'+key+'</option>'); 
                       }
               });
            }
       });
        
        $("form[id='viewEducationInfo'] #SUBJECT"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("form[id='viewEducationInfo'] #SUBJECT_SECOND"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT_SECOND"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>');
    }else{
        
        $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY_TWO"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT_CLASSIFY_TWO"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        
        $("form[id='viewEducationInfo'] #SUBJECT"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("form[id='viewEducationInfo'] #SUBJECT_SECOND"+num).empty();
        $("form[id='viewEducationInfo'] #SUBJECT_SECOND"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>');
    }
}

function finaldegreeEdu(finalId){
    var count = parseInt($("form[id='viewEducationInfo'] #count").val());
    var id=finalId.replace("FINAL_DEGREE_WHETHER","");
    var fcount = 0;
    if($("form[id='viewEducationInfo'] #"+finalId).val()=='Y'){
        
        //将除了选中的id意外 全部都改成 N
        for(i=0;i<count;i++){
            if(i!=id){
                if($("form[id='viewEducationInfo'] #FINAL_DEGREE_WHETHER"+i) != null ){
                    //document.getElementById("FINAL_DEGREE_WHETHER"+i).selectedIndex=0;
                    $("#FINAL_DEGREE_WHETHER > option[value=]").attr("selected", "true");  
                }
            }
        }
    }else{
        for(i=0;i<count;i++){
            if($("form[id='viewEducationInfo'] #FINAL_DEGREE_WHETHER"+i) != null ){
                if( $("#FINAL_DEGREE_WHETHER"+id+" option:selected").val() == "Y" ) {
                    fcount = fcount+1; 
                }
            }
        }
    }
    
    if(fcount >  1){
        alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.FinalDegree"/>');//"最终学历只能有一个"
        return false;
    }

}
</script>


<div class="pageContent">
	<form id="viewEducationInfo" method="post" action="/hrm/empinfo/addEducationInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewEducationInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>          	
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.INSTITUTION_NAME"/>
						<!--学校名-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME0" class="textInput required" maxlength="60" >
						<input type="hidden" name="PERSON_ID" value="${PERSON_ID}" />
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY" />
						<!--专业分类-->
					</td>
					<td class="td_type"  width="170">
					<select class="select" name="SUBJECT_CLASSIFY0"  id="SUBJECT_CLASSIFY0"
						onChange="getEducationName(this.id,this.value,'SUBJECT_CLASSIFY','SUBJECT');">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${subjectClassifyList}" var="item">
	   						<option value="${item.CODE_NO}">${item.CODENAME}</option>
						</c:forEach>
					</select>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.SUBJECTNAME"/>
						<!--专业-->
					</td>
					<td class="td_type">
						<select class="select" name="SUBJECT0" id="SUBJECT0">
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!--请选择 -->
							</option>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.START_DATE"/>
						<!--入学日期-->
					</td>
					<td class="td_type">
						<ait:date yearName="START_YEAR0"  yearMinus="60" yearPlus="5" monthName="START_MONTH0"/>
					</td>
					
					<td rowspan="3"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				
				<tr >
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.DEGREE_NAME"/>
						<!--学历-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="DEGREE_CODE0" parentNo="1665" cnpyID="${defaultCpny}" onChangeName="setClass(this)" />
					</td>
					
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY_TWO" />
						<!--第二专业分类-->
					</td>
					<td class="td_type">
					<select class="select" name="SUBJECT_CLASSIFY_TWO0" id="SUBJECT_CLASSIFY_TWO0" onChange="getEducationName(this.id,this.value,'SUBJECT_CLASSIFY_TWO','SUBJECT_SECOND');">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${subjectClassifyList}" var="item">
	   						<option value="${item.CODE_NO}">${item.CODENAME}</option>
						</c:forEach>
					</select>
					</td>
					
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.SUBJECT_SECOND_NAME"/>
						<!--第二专业-->
					</td>
					<td class="td_type">
						<select class="select" name="SUBJECT_SECOND0" id="SUBJECT_SECOND0">
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!--请选择 -->
							</option>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.END_DATE"/>
						<!--毕业日期-->
					</td>
					<td class="td_type">
						<ait:date yearName="END_YEAR0"  yearMinus="60" yearPlus="5" monthName="END_MONTH0"/>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" />
						<!--所在地-->
					</td>
					<td class="td_type">
						<select class="select" name="SITE_PROVINCE0"  id="SITE_PROVINCE0"
						onChange="getEducationName(this.id,this.value,'SITE_PROVINCE','SITE_CITY');">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${siteProvinceList}" var="item">
	   						<option value="${item.CODE_NO}">${item.CODENAME}</option>
						</c:forEach>
						</select>
						<select class="select" name="SITE_CITY0" id="SITE_CITY0">
							<option><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 --></option>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.FINAL_DEGREE_WHETHER" />
						<!--最终学历与否-->
					</td>
					<td class="td_type">
						<select name="FINAL_DEGREE_WHETHER0" id="FINAL_DEGREE_WHETHER0" onchange="finaldegreeEdu(this.id);">
						 <option value="N">N</option>
						 <c:if test="${FINALNUM eq 0}"><option value="Y">Y</option></c:if>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewPersonalInfo.title.REMARKS"/>
						<!--备注-->
					</td>
					<td class="td_type">
						<input type="text" name="REMARKS0" class="textInput" >
					</td>	
					
					
					<td></td>
					<td></td>
				</tr>			
			</table>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>