<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">
function validateCallbackUpdateHealthInfo(form, callback) {


	var $form = $("#updateEducation");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("HNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	//验证开始日期大于结束日期
	var educationListSizenum= document.getElementById("educationListSize").value;
	var activity ="0";
	
	for (i=0;i<educationListSizenum;i++){
		if(document.getElementById("HNO"+i).checked){
			
			var id=document.getElementById("HNO"+i).value;
			var START_YEAR=document.getElementById("START_YEAR_"+id).value;
			var START_MONTH=document.getElementById("START_MONTH_"+id).value;
			var END_YEAR=document.getElementById("END_YEAR_"+id).value;
			var END_MONTH=document.getElementById("END_MONTH_"+id).value;
			
			var date1 = START_YEAR+START_MONTH;
			var date2 = END_YEAR+END_MONTH;
			
			if (date1 - date2 > 0) {
				activity="1";
				document.getElementById("START_YEAR_"+id).focus();
			}
		}
	}
	
	if(activity>0){
		//开始时间不能晚于结束时间
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');
		return false;
	}
	
	//添加时只能有一个最终学历
	var fcount = 0;
	for(i=0;i<educationListSizenum;i++){
		var ultimate=document.getElementById("FINAL_DEGREE_WHETHER_"+i).value;
		if(ultimate=='Y'){
			fcount = fcount + 1;
		}
	}
	
	if(fcount != 1){
		alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.FinalDegree"/>');//"最终学历只能有一个"
		return false;
	}
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="HNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}

	//根据传入的不同名称查询子idgetEducationName
	function getUEducationName(id,sourceId,name,toname){
		id=id.replace(name,"");
    	if(sourceId == null || sourceId.length == 0){
    		return ;
    	}
    	var sel = $("#"+toname+id);
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
function setClass(obj){
	var num = ($(obj).attr("id")).replaceAll("DEGREE_CODE_","");
	//대학이상인 경우 변동 없음
    if($(obj).val()=="123337" || 
            $(obj).val()=="1666" || 
            $(obj).val()=="1667" || 
            $(obj).val()=="1668" ||
            $(obj).val()=="1669"){
    	var sel1 = $("#SUBJECT_CLASSIFY_"+num);
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

        var sel2 = $("#SUBJECT_CLASSIFY_TWO_"+num);
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
        
        $("#SUBJECT_"+num).empty();
        $("#SUBJECT_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("#SUBJECT_SECOND_"+num).empty();
        $("#SUBJECT_SECOND_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>');
    }else{
        //$("#SUBJECT_CLASSIFY"+num+" > option[value=]").attr("selected", "true");
        //$("#SUBJECT_CLASSIFY_TWO"+num+" > option[value=]").attr("selected", "true");
        
        $("#SUBJECT_CLASSIFY_"+num).empty();
        $("#SUBJECT_CLASSIFY_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("#SUBJECT_CLASSIFY_TWO_"+num).empty();
        $("#SUBJECT_CLASSIFY_TWO_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        
        $("#SUBJECT_"+num).empty();
        $("#SUBJECT_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>'); 
        $("#SUBJECT_SECOND_"+num).empty();
        $("#SUBJECT_SECOND_"+num).append('<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>');
    }
}

function finaldegreeEdu(finalId){
	var count= document.getElementById("educationListSize").value;
    var id=finalId.replace("FINAL_DEGREE_WHETHER_","");
    var fcount = 0;
    if($("#"+finalId).val()=='Y'){
        //var degree= $("#DEGREE_CODE_"+id).val(); // document.getElementById("DEGREE_CODE"+id).value;
        //var subject=$("#SUBJECT"+id+"  option:selected").val();
        
        //将除了选中的id意外 全部都改成 N
        for(i=0;i<count;i++){
            if(i!=id){
                if(document.getElementById("FINAL_DEGREE_WHETHER_"+i) != null ){
                    document.getElementById("FINAL_DEGREE_WHETHER_"+i).selectedIndex=0;
                }
            }
        }
    }else{
    	for(i=0;i<count;i++){
            if(document.getElementById("FINAL_DEGREE_WHETHER_"+i) != null ){
            	if( $("#FINAL_DEGREE_WHETHER_"+id+" option:selected").val() == "Y" ) {
            		fcount = fcount+1; 
            	}
            }
        }
    }
    
    if(fcount >  1){
    	alertMsg.error('<spring:message code="liang.hr.alert.message.viewPersonalInfo.FinalDegree"/>');//"最终学历只能有一个"
        return false;
    }
    
    $("input[name='HNO']").prop("checked",true);

}

</script>

<div class="pageContent">
	<form id="updateEducation" method="post" action="/hrm/empinfo/editEducation" class="pageForm required-validate" onsubmit="return validateCallbackUpdateHealthInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="educationListSize" name="educationListSize" value="${fn:length(educationList)}" />
	<table class="table" width="102.2%" layoutH="90" nowrapTD="false">
		<thead>
			<tr>
				<th width="10" style="display:none"><input name="PERSON_ID" type="hidden" value="${PERSON_ID }"></th>
				<th class="td_title" width="80" >
					<spring:message
						code="hr.viewPersonalInfo.title.INSTITUTION_NAME" />
					<!--学校名-->
				</th>
				<th class="td_title" width="80">
					<spring:message
						code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY" />
					<!--专业分类-->
				</th>
				<th class="td_title" width="100">
					<spring:message
						code="hr.viewPersonalInfo.title.SUBJECTNAME" />
					<!--专业-->
				</th>
				<th class="td_title" width="80">
					<spring:message
						code="liang.hr.viewPersonalInfo.title.SUBJECT_CLASSIFY_TWO" />
					<!--第二专业分类-->
				</th>
				
				<th class="td_title" width="100">
					<spring:message
						code="hr.viewPersonalInfo.title.SUBJECT_SECOND_NAME" />
					<!--第二专业-->
				</th>
				<th class="td_title" width="100">
					<spring:message
						code="liang.hr.viewPersonalInfo.title.START_DATE" />
					<!--入学年月-->
				</th>
				<th class="td_title" width="100">
					<spring:message code="liang.hr.viewPersonalInfo.title.END_DATE" />
					<!--毕业年月-->
				</th>
				<th class="td_title" width="80">
					<spring:message
						code="hr.viewPersonalInfo.title.DEGREE_NAME" />
					<!--学历-->
				</th>
				
				<th class="td_title" width="80">
					<spring:message
						code="hr.viewPersonalInfo.title.SCHOOL_ADDRESS" />
					<!--所在地-->
				</th>
				<th class="td_title" width="80">
					<spring:message
						code="liang.hr.viewPersonalInfo.title.FINAL_DEGREE_WHETHER" />
					<!--最终学历与否-->
				</th>
				<th class="td_title" width="80">
					<spring:message
						code="liang.hr.viewPersonalInfo.title.REMARKS" />
					<!--备注-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${educationList}" var="item" varStatus="i">
				<tr target="healthNo" rel="${item.EDUC_NO}">
					
					<td style="display:none"><input type="checkbox" id="HNO${i.index}" name="HNO" value="${item.EDUC_NO}" /></td>
					
					<td class="td_type" >
						<input onclick="setCheckboxChecked(${i.index})" type="text" id="INSTITUTION_NAME_${i.index}" name="INSTITUTION_NAME_${item.EDUC_NO}" value="${item.INSTITUTION_NAME}" />
					</td>
					<td class="td_type" >
						<select class="select" name="SUBJECT_CLASSIFY_${item.EDUC_NO}"  id="SUBJECT_CLASSIFY_${i.index}" onChange="getUEducationName(this.id,this.value,'SUBJECT_CLASSIFY_','SUBJECT_');setCheckboxChecked(${i.index});">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${subjectClassifyList}" var="recsource">
	   						<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.SUBJECT_CLASSIFY_CODE}">selected</c:if>>${recsource.CODENAME}</option>
						</c:forEach>
					</select>
					</td>
					<td class="td_type" >
						<select onclick="setCheckboxChecked(${i.index})" name="SUBJECT_${item.EDUC_NO}" id="SUBJECT_${i.index}"  >
							<option value="${item.SUBJECT_CODE }">${ item.SUBJECT_NAME }</option>
						</select>
					</td>
					<td class="td_type" >
						<select class="select" name="SUBJECT_CLASSIFY_TWO_${item.EDUC_NO}" id="SUBJECT_CLASSIFY_TWO_${i.index}" onChange="getUEducationName(this.id,this.value,'SUBJECT_CLASSIFY_TWO_','SUBJECT_SECOND_'); setCheckboxChecked(${i.index});">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${subjectClassifyList}" var="recsource">
	   						<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.CLASSIFY_TWO_CODE}">selected</c:if>>${recsource.CODENAME}</option>
						</c:forEach>
					</select>
					</td class="td_type" >
					<td class="td_type" >
						<select onclick="setCheckboxChecked(${i.index})" name="SUBJECT_SECOND_${item.EDUC_NO}" id="SUBJECT_SECOND_${i.index}"  >
							<option value="${item.SUBJECT_SECOND_CODE }">
								${item.SUBJECT_SECOND_NAME }
							</option>
						</select>
					</td>
					<td class="td_type" >
						<ait:date yearName="START_YEAR_${item.EDUC_NO}"  yearMinus="60" yearPlus="5" monthName="START_MONTH_${item.EDUC_NO}" yearSelected="${item.START_YEAR}"  monthSelected="${item.START_MONTH}" onChange="setCheckboxChecked(${i.index})"/>
					</td>
					<td class="td_type" >
						<ait:date yearName="END_YEAR_${item.EDUC_NO}"  yearMinus="60" yearPlus="5" monthName="END_MONTH_${item.EDUC_NO}" yearSelected="${item.END_YEAR}"  monthSelected="${item.END_MONTH}" onChange="setCheckboxChecked(${i.index})"/>
					</td>
					<td class="td_type" >
						<select onclick="setCheckboxChecked(${i.index})" name="DEGREE_CODE_${item.EDUC_NO}" id="DEGREE_CODE_${i.index}" onChange="setClass(this)" >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${degreeCodeList}" var="recsource">
								<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.DEGREE_CODE}">selected</c:if>>
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_type" >
						<select class="select" name="SITE_PROVINCE_${item.EDUC_NO}"  id="SITE_PROVINCE_${i.index}" onChange="getUEducationName(this.id,this.value,'SITE_PROVINCE_','SITE_CITY_');setCheckboxChecked(${i.index});">
						<option value="">
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
							 <!--请选择 -->
						</option>
						<c:forEach items="${siteProvinceList}" var="recsource">
	   						<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.SITE_PROVINCE_CODE}">selected</c:if>>${recsource.CODENAME}</option>
						</c:forEach>
						</select>
						<select class="select" name="SITE_CITY_${item.EDUC_NO}" id="SITE_CITY_${i.index}" onclick="setCheckboxChecked(${i.index})">
							<option value="${item.SITE_CITY_CODE}">${item.SITE_CITY_NAME}</option>
						</select>
					</td>
					<td class="td_type" >
						<select name="FINAL_DEGREE_WHETHER_${item.EDUC_NO}" id="FINAL_DEGREE_WHETHER_${i.index}" onclick="setCheckboxChecked(${i.index})" onchange="finaldegreeEdu(this.id);" >
						 <option value="N" <c:if test="${item.FINAL_DEGREE_WHETHER eq 'N'}">selected</c:if>>N</option>
						 <option value="Y" <c:if test="${item.FINAL_DEGREE_WHETHER eq 'Y'}">selected</c:if>>Y</option>
						</select>
					</td>
					<td class="td_type" >
						<input onclick="setCheckboxChecked(${i.index})" type="text" name="REMARKS_${item.EDUC_NO}" id="REMARKS_${i.index}" value="${item.REMARKS}" class="textInput" >
					</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	
	
	
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