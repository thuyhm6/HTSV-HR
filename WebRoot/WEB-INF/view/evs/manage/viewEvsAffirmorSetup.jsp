<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsAffirmorSetup_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsAffirmorSetupForm",navTab.getCurrentPanel()).submit();
	});
	
	//检索未登记考核对象
	$("#viewEvsAffirmorSetup_SerchNo",navTab.getCurrentPanel()).click(function(){
		$("#searchType",navTab.getCurrentPanel()).val(0);
		$("#viewEvsAffirmorSetupForm",navTab.getCurrentPanel()).submit();
	});
	
	//添加
	$("#viewEvsAffirmorSetup_insert",navTab.getCurrentPanel()).click(function(){
		$.pdialog.open("/evs/manage/viewAddEvsObject?seach_RESUME_SEQ=" + $("#viewEvsAffirmorSetupResumeNo",navTab.getCurrentPanel()).val(), "addEvsObject", "<spring:message code='button.add'/>", {width:400,height:200,mask:true});
	});
	
	$('.evsList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
	        $(this).html(val);
			this.editing = false;
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		}
	});

	$('.evsList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var level = $(this).attr("sysLevel");

			$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).html("");
			$("#affirmorGrade" + level + "_" + index,navTab.getCurrentPanel()).html("");
			$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).attr("sysPersonId","");
			
			submitKeyClick_affirmorEvsAff(val,index,level);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	
	$(".evsList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
	    "lengthMenu": [[50,100,200, 500,1000], [50,100,200, 500,1000]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"searching": true,//本地搜索
		"bSort": true,   //关闭排序功能
		"bInfo": true,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 360,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    } 
	});
	//考核对象生成
	$("#viewEvsAffirmorSetup_Create",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewEvsAffirmorSetup.QUEDINGYAOSHENGCHENGKAOHEDUIXIANGYIKAOHEZHE.a'/>",//确定要生成考核对象及考核者吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/createEvsTarget',
					data: { SEQ: '${RESUME_SEQ}' },
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	//考勤总计
	$("#viewEvsAffirmorSetup_arDetail",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewEvsAffirmorSetup.QUEDINGYAOSHENGCHENGKAOQINSHUJUMA.a'/>",//确定要生成考勤数据吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/evsCreateArDetail',
					data: { SEQ: '${RESUME_SEQ}' },
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	//考核开始
	$("#viewEvsAffirmorSetup_Start",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewEvsAffirmorSetup.QUEDINGYAOKAISHIKAOHEMA.a'/>",//确定要开始考核吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/evsStart',
					data: { SEQ: '${RESUME_SEQ}' },
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  		});
	  	}});
	});
	
	//删除
	$("#viewEvsAffirmorSetup_delete",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input:[name='viewEvsAffirmorSetupCheckBox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked == true){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				jsonData += ' "EVS_OBJECT_SEQ": "' + obj.value + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='evs.viewEvsAffirmorSetup.MEIYOUXUYAOSHANCHUDESHUJU.a'/>");//没有需要删除的数据
			return;
		}
		
		alertMsg.confirm("<spring:message code='js.upload.msg.confirmToDelete'/>",//确定要删除吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/deleteEvsObjectInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: function(json){
			  			DWZ.ajaxDone(json);
			  			$("#viewEvsAffirmorSetupForm",navTab.getCurrentPanel()).submit();
	  				},
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	//保存
	$("#viewEvsAffirmorSetup_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var seq = $(this).attr("sysIndex");
				jsonData += ' "EVS_OBJECT_SEQ": "' + seq + '" ,';
				jsonData += ' "RESUME_SEQ": "${RESUME_SEQ}" ,';
				jsonData += ' "EVS_OCC_GROUP_NAME": "' + $("#EVS_OCC_GROUP_NAME_" + seq,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "EVS_GROUP_NAME": "' + $("#EVS_GROUP_NAME_" + seq,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "LIST_TYPE_NAME": "' + $("#EVS_LIST_TYPE_NAME_" + seq,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ,';
				
				jsonData += ' "AFFIRM_ID1": "' + $("#affirmor1_" + seq,navTab.getCurrentPanel()).attr("sysPersonId") + '" ,';
				jsonData += ' "AFFIRM_ID2": "' + $("#affirmor2_" + seq,navTab.getCurrentPanel()).attr("sysPersonId") + '" ,';
				jsonData += ' "AFFIRM_ID3": "' + $("#affirmor3_" + seq,navTab.getCurrentPanel()).attr("sysPersonId") + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';

		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA'/>");//没有需要保存的数据
			return;
		}
		
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/saveEvsObjectInfo',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});

	//全部反映
	$("#viewEvsAffirmorSetup_feedback",navTab.getCurrentPanel()).click(function(){
		var affirmLevel = $("#EVS_STEP",navTab.getCurrentPanel()).val();
		if(affirmLevel == ''){
			alertMsg.info("<spring:message code='evs.viewEvsAffirmorSetup.QINGXUANZEPINGJIAJIEDUAN.a'/>");//请选择评价阶段
			return false;
		}
		var local_name = $("[id='dwz.person.NameempNameEvs']",navTab.getCurrentPanel()).val();
		var person_id = $("[id='dwz.person.personIdEvs']",navTab.getCurrentPanel()).val();
		var post_grade_name = $("[id='dwz.person.GradeempNameEvs']",navTab.getCurrentPanel()).val();
	
		$("input:[name='viewEvsAffirmorSetupCheckBox']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked == true){
				var seq = $(this).val();
				$("#affirmor" + affirmLevel + "_" + seq,navTab.getCurrentPanel()).html(local_name);
				$("#affirmorGrade" + affirmLevel + "_" + seq,navTab.getCurrentPanel()).html(post_grade_name);
				$("#affirmor" + affirmLevel + "_" + seq,navTab.getCurrentPanel()).attr("sysPersonId",person_id);
				$("#modifyFlag_" + seq,navTab.getCurrentPanel()).html("modify");
			}
		});
	});

	//清除
	$("#viewEvsAffirmorSetup_clean",navTab.getCurrentPanel()).click(function(){
	  	$("[id='dwz.person.empNameEvs']",navTab.getCurrentPanel()).val('');
	  	$("[id='dwz.person.personIdEvs']",navTab.getCurrentPanel()).val( '');
	  	$("[id='dwz.person.InfoempNameEvs']",navTab.getCurrentPanel()).val( '');
	  	$("[id='dwz.person.GradeempNameEvs']",navTab.getCurrentPanel()).val( '' );
	  	$("[id='dwz.person.NameempNameEvs']",navTab.getCurrentPanel()).val( '' );
	});
});
function changeModifyFlag(obj,index){
	$("#modifyFlag_" + index).html("modify");
}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}; 

var keyCodeInit=0;
function submitKeyClick_affirmorEvs(obj,event){
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var empIdStr=obj.id.substring(11);
		var personIdStr="personIdEvs";
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onckEvs").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onckEvs").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onckEvs").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onckEvs").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.empNameEvs']",navTab.getCurrentPanel()).val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.personIdEvs']",navTab.getCurrentPanel()).val( jsonObject.personId);
					  	$("[id='dwz.person.InfoempNameEvs']",navTab.getCurrentPanel()).val( jsonObject.empName + "/" + jsonObject.POST_GRADE_NAME + "/" + jsonObject.deptName);
					  	$("[id='dwz.person.GradeempNameEvs']",navTab.getCurrentPanel()).val( jsonObject.POST_GRADE_NAME );
					  	$("[id='dwz.person.NameempNameEvs']",navTab.getCurrentPanel()).val( jsonObject.empName );
					}
				},
				error: DWZ.ajaxError
			});
		}
    }
}
function submitKeyClick_affirmorEvsAff(obj,index,level){
	var empid=obj.replace(/[ ]/g," ");
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=super&pageNum=1"
							+'&seach_KEY='+empid
							+'&personidStr=viewEvsAffInfo'
							+'&empidStr=' + level + '_' + index));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).html(jsonObject.empName);
					$("#affirmorGrade" + level + "_" + index,navTab.getCurrentPanel()).html(jsonObject.POST_GRADE_NAME);
					$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).attr("sysPersonId",jsonObject.personId);
				}
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).html("");
		$("#affirmorGrade" + level + "_" + index,navTab.getCurrentPanel()).html("");
		$("#affirmor" + level + "_" + index,navTab.getCurrentPanel()).attr("sysPersonId","");
	}
}
</script>
<div class="pageHeader">
	<form id="viewEvsAffirmorSetupForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsAffirmorSetup" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsAffirmorSetupResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
						<input type="hidden" id="searchType" name="searchType" value="1">
					</td>
				</tr>
				<tr>
					<td><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门--></td>
					<td>
						<ait:deptList name="seach_DEPTNO" limit="hr" id="viewEvsAffirmorSetup_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewEvsAffirmorSetup_seachDept" selected="${DEPTNO}"/>
						<input type="checkbox" name="seach_SON_FLAG" value="1" <c:if test="${SON_FLAG eq '1' }">checked="checked"</c:if>>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					
					<c:if test="${ACTIVITY ne '4'}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_Create">
									<spring:message code="evs.viewEvsAffirmorSetup.CreateTarget.a"/><!--Create Target-->
								</button>
							</div>
						</div>
					</li>
					</c:if>
					<%-- <li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_SerchNo">
									<spring:message code="evs.viewEvsAffirmorSetup.JIANSUOWEIDENGJIKAOHEDUIXIANG.a"/><!--检索未登记考核对象-->
								</button>
							</div>
						</div>
					</li> --%>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_Serch">
									<spring:message code="button.search"/><!--查询-->
								</button>
							</div>
						</div>
					</li>
					<c:if test="${ACTIVITY ne '4'}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_insert">
									<spring:message code="button.add"/><!--添加-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive" href="/evs/manage/evsAffirmorImportDemo"> 
						<span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载--></span>
						</a>
					</li>
					<li>
						<a class="buttonActive" href="/pa/excelImport/importExcelData?&importFunName=/importEvsAffirmorExcel?resumeSEQ=${RESUME_SEQ}" target="dialog" mask="true" width="400" height="200" >
						<span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span></a>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_delete">
									<spring:message code="button.delete"/><!--删除-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_Save">
									<spring:message code="ess.viewOvertimeInfo.baocun"/><!--保存-->
								</button>
							</div>
						</div>
					</li>
					</c:if>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="downloadExcel('viewEvsAffirmorSetupForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=178','/evs/manage/viewEvsAffirmorSetup')">
									<spring:message code="hrm.empinfo.EXPORT"/><!--导出到Excel-->
								</button>
							</div>
						</div>
					</li>
					<c:if test="${ACTIVITY ne '4'}">
					<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_arDetail">
									<spring:message code="evs.viewEvsAffirmorSetup.KAOQINZONGJI.a"/><!--考勤总计-->
								</button>
							</div>
						</div>
					</li>
					</c:if>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_Start">
									<spring:message code="evs.viewEvsAffirmorSetup.StartEvaluation.a"/><!--Start Evaluation-->
								</button>
							</div>
						</div>
					</li>
					<%-- <li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button">
									<spring:message code="evs.viewEvsAffirmorSetup.YOUJIAN.a"/><!--邮件-->
								</button>
							</div>
						</div>
					</li> --%>
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>


<div class="pageHeader">
	<form id="viewEvsAffirmorSetupForm2" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsAffirmorSetup" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAJIEDUAN.a"/><!--评价阶段--></td>
					<td>
		 				<select id="EVS_STEP" name="EVS_STEP">
		 					<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
		 					<option value="1"><spring:message code="evs.viewEvsAffirmorSetup.YIJIPINGJIAZHE.a"/><!--一级评价者--></option>
		 					<option value="2"><spring:message code="evs.viewEvsAffirmorSetup.ERJIPINGJIAZHE.a"/><!--二级评价者--></option>
		 					<%-- <c:if test="${evsType eq 'performance'}">
		 					<option value="3"><spring:message code="evs.viewEvsAffirmorSetup.SANJIPINGJIAZHE.a"/><!--三级评价者--></option>
		 					</c:if> --%>
		 				</select>
					</td>
					<td><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAREN.a"/><!--评价人--></td>
					<td>
						<input id="dwz.person.personIdEvs" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>
						<input id="dwz.person.GradeempNameEvs" name="POST_GRADE_NAME" value="" type="hidden" lookupGroup="person"/>
						<input id="dwz.person.NameempNameEvs" name="LOCAL_NAME" value="" type="hidden" lookupGroup="person"/>
						<input id="dwz.person.empNameEvs" name="empid" value="" type="text" size="25" alt="<spring:message code="org.title.INPUT_KEY_SELECT"/>" lookupGroup="person" onkeydown="submitKeyClick_affirmorEvs(this,event)" style="float:left;"/>
						<a class="btnLook" id="onckEvs" name="onck" href="/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1&seach_KEY=&empidStr=empNameEvs&personidStr=personIdEvs" lookupGroup="person">
						<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
						<input id="dwz.person.InfoempNameEvs" type="text" value="" size="40" disabled="disabled"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_clean">
									<spring:message code="edu.planManager.QINGCHU.a"/><!--清除-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="viewEvsAffirmorSetup_feedback">
									<spring:message code="hrm.approve.ALL_REACTION"/><!--全部反应-->
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
		<div style="font:bold 12px/20px arial,sans-serif;;float:left;height:20px;line-height:20px;">Total:${objectListSize}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
				<table class="evsList" width="2000px;">
					<thead>
						<tr>
							<th width="30px" rowspan="2">No.</th>
							<th width="30px" rowspan="2"><input type="checkbox" class="checkboxCtrl" group="viewEvsAffirmorSetupCheckBox"/></th>
							<th width="100px" rowspan="2"><spring:message code="ess.infoApply.NAME"/><!--姓名--></th>
							<th width="80px" rowspan="2"><spring:message code="ess.infoApply.EMP_ID"/><!--工号--></th>
							<th width="100px" rowspan="2"><spring:message code="hr.viewCondSql.title.BUMENMINGCHENG"/><!--部门名--></th>
							<th width="80px" rowspan="2"><spring:message code="hr.viewCondSql.title.ZHIJI"/><!--职级--></th>
							<th width="60px" rowspan="2"><spring:message code="hrm.empinfo.ATTEND_DATE"/><!--入社日--></th>
							<th width="100px" rowspan="2" <c:if test="${LoginUser.cpnyId eq 'SST' }">style="display:none;"</c:if>><spring:message code="evs.viewEvsParamInfoList.PINGJIABIAOLEIXING.a"/><!--评价表类型--></th>
							<th width="100px" rowspan="2"><spring:message code="evs.viewEvsAffirmorSetup.PINGJIAQUN.a"/><!--评价群--></th>
							<c:if test="${evsType eq 'ability'}">
							<th width="100px" rowspan="2"><spring:message code="evs.viewEvsParamInfoList.PINGJIAZHIYEQUN.a"/><!--评价职业群--></th>
							</c:if>
							<th width="150px" colspan="2"><spring:message code="evs.viewEvsAffirmorSetup.YICIPINGJIAREN.a"/><!--1次评价人--></th>
							<th width="150px" colspan="2"><spring:message code="evs.viewEvsAffirmorSetup.LAINGCIPINGJIAREN.a"/><!--2次评价人--></th>
							<%-- <c:if test="${evsType eq 'performance'}">
							<th width="150px" colspan="2" ><spring:message code="evs.viewEvsAffirmorSetup.SANCIPINGJIAREN.a"/><!--3次评价人--></th> 
							</c:if> --%>
							<th width="150px" rowspan="2"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="100px" rowspan="2"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
						<tr>
							<th width="70px"><spring:message code="hrm.empinfo.FAM_NAME"/><!--姓名--></th>
							<th width="80px"><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							<th width="70px"><spring:message code="hrm.empinfo.FAM_NAME"/><!--姓名--></th>
							<th width="80px"><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							<%-- <c:if test="${evsType eq 'performance'}">
							<th width="70px" ><spring:message code="hrm.empinfo.FAM_NAME"/><!--姓名--></th>
							<th width="80px" ><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							</c:if> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${objectList}" var="item" varStatus="i">
							<tr>
								<td style="text-align: center" class='td_center'>${i.count}</td>
								<td style="text-align: center" class='td_center'><input type="checkbox" name="viewEvsAffirmorSetupCheckBox" id="viewEvsAffirmorSetupCheckBox_${item.SEQ}" value="${item.SEQ}" /></td>
								<td style="text-align: center" >${item.LOCAL_NAME}</td>
								<td style="text-align: center" >${item.EMPID}</td>
								<td style="text-align: center" >${item.DEPTNAME}</td>
								<td style="text-align: center" >${item.POST_GRADE_NAME}</td>
								<td style="text-align: center"  class='td_center'>${item.DATE_STARTED}</td>
								<td style="text-align: center"  sysLog="select" sysIndex="${item.SEQ}" sysValue='${evsListTypeSelect}' id="EVS_LIST_TYPE_NAME_${item.SEQ}" <c:if test="${LoginUser.cpnyId eq 'SST' }">style="display:none;"</c:if>>${item.LIST_TYPE_NAME}</td>
								<td style="text-align: center"  sysLog="select" sysIndex="${item.SEQ}" sysValue='${evsGroupSelect}' id="EVS_GROUP_NAME_${item.SEQ}">${item.EVS_GROUP_NAME}</td>
								
								<c:if test="${evsType eq 'ability'}">
								<td style="text-align: center"  sysLog="select" sysIndex="${item.SEQ}" sysValue='${evsOccGroupSelect}' id="EVS_OCC_GROUP_NAME_${item.SEQ}">${item.EVS_OCC_GROUP_NAME}</td>
								</c:if>
								
								<td style="text-align: center"  id="affirmor1_${item.SEQ}" sysPersonId="${item.PERSON_ID1}" sysLog="lookUp" sysIndex="${item.SEQ}" sysLevel="1">${item.LOCAL_NAME1}</td>
								<td style="text-align: center"  id="affirmorGrade1_${item.SEQ}">${item.POST_GRADE_NAME1}</td>
								<td style="text-align: center"  id="affirmor2_${item.SEQ}" sysPersonId="${item.PERSON_ID2}" sysLog="lookUp" sysIndex="${item.SEQ}" sysLevel="2">${item.LOCAL_NAME2}</td>
								<td style="text-align: center"  id="affirmorGrade2_${item.SEQ}">${item.POST_GRADE_NAME2}</td>
								<%-- <c:if test="${evsType eq 'performance'}">
								<td style="text-align: center"  id="affirmor3_${item.SEQ}" sysPersonId="${item.PERSON_ID3}" sysLog="lookUp" sysIndex="${item.SEQ}" sysLevel="3" >${item.LOCAL_NAME3}</td>
								<td style="text-align: center"  id="affirmorGrade3_${item.SEQ}" >${item.POST_GRADE_NAME3}</td>
								</c:if> --%>
								<td style="text-align: center" >${item.UPDATED_BY}
									<div id="modifyFlag_${item.SEQ}" sysIndex="${item.SEQ}" sysLog="modifyFlag" style="display:none;"></div>
								</td>
								<td style="text-align: center" >${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
		</div>
