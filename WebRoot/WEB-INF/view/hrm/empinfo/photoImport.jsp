<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<style type="text/css">
.all{
   padding-left: 20px;
   padding-top: 40px;
}
.one{
	float:left;
	font-size:14px;
	color:#f00;
	width:800px;
	height:360px;
	overflow: auto
	
}
.two{
	float:left;
	font-size:14px;
	color:#f00;
	width:240px;
	height:400px;
	margin-left: 30px;
}
.three{
	float:left;
	font-size:14px;
	color:#f00;
	width:730px;
	height:360px;
	z-index: 1000;
	overflow: auto;
}
.four{
    float:left;
	font-size:14px;
	color:#f00;
	width:240px;
	height:400px;
	margin-left: 30px;
}
</style>
<script type="text/javascript">
$(function(){
	var aa="${applyno}";
	if(aa=="photo_name",navTab.getCurrentPanel()){
		aa="empid";
		$('#qufenname',navTab.getCurrentPanel()).removeAttr('checked');
		$('#qufenempid',navTab.getCurrentPanel()).attr('checked','checked');
	}else{
		aa="name";
		$('#qufenempid',navTab.getCurrentPanel()).removeAttr('checked');
		$('#qufenname',navTab.getCurrentPanel()).attr('checked','checked');
	}
	change(aa);
	$('#photoImage',navTab.getCurrentPanel()).attr('src',$('#photo_1').val());
	if('${POST_FAMILY}'!=""){
		codeRelation('${POST_FAMILY}','GRADE_NO','${GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
	}
	if('${empid_name}'!=""){
		if('${empid_name}'=="localname"){
			$('#empid_name option[value="localname"]',navTab.getCurrentPanel()).attr('selected','selected');
		}else{
			$('#empid_name option[value="empid"]',navTab.getCurrentPanel()).attr('selected','selected');
		}
	}
	if('${isWith}'=="YES"){
		if('${empid_name}'=="localname"){
			$('#isWith',navTab.getCurrentPanel()).attr('checked','checked');
			$('#isWith',navTab.getCurrentPanel()).attr('value','YES');
		}
	}
	var ff=$('#first_1').val();
	$('#photoImage1',navTab.getCurrentPanel()).attr('src',ff);
	if("${twodeptname1}"==2&&"${QUERYDEPTNAME}"!=""){
		chooseDepart("${twodeptname1}");
		queryDepartment();
	}
	
	
});
function choosePhoto(fileno){
	var photoUrl=$('#photo_'+fileno).val();
	$('#photoImage',navTab.getCurrentPanel()).attr('src',photoUrl);
}
function change(a){
	var cc="";
	var bb=$('#fileupload',navTab.getCurrentPanel()).attr('onclick');
     
    if(a=="name"){
		cc="<spring:message code='hrm.empinfo.name' />";//姓名
		if(bb.indexOf("photo_no")>-1){
			bb=bb.replace("photo_no","photo_name");
			$('#radiophoto',navTab.getCurrentPanel()).attr('value','radiophotoname');
		}
		
	}else{
		cc="<spring:message code='hrm.empinfo.empid' />";//社号
		if(bb.indexOf("photo_name")>-1){
			bb=bb.replace("photo_name","photo_no");
			$('#radiophoto',navTab.getCurrentPanel()).attr('value','radiophotoempid');
		}
	}
    $('#fileupload',navTab.getCurrentPanel()).attr('onclick',bb);
	var countnum=$('#countnum',navTab.getCurrentPanel()).val();
	for(var i=1;i<=countnum;i++){
		$('#qufenempidname_'+i,navTab.getCurrentPanel()).html(cc);
	}
	
	
}
function uploadAttDialog(id,val,seq,applyType){
	if(seq.length < 1){
		alertMsg.info("<spring:message code="hrm.alert.empinfo.saveAndUpload" />");//请先保存信息，再上传附件
		return false;
	}
	$.pdialog.open("/sys/notice/uploadWindow?id=" + id + "&val=" + val
			+ "&seq=" + seq + "&applyType=" + applyType, "uploadWindow", 
			"<spring:message code="hrm.alert.empinfo.upload_Enclosure" />", {width:550,height:320,mask:true});//附件上传
}
function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM" />",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  				/* success: function(data){
  					if(data.statusCode=="200"){
  					navTabNum('/hrm/empinfo/photoImport','pageNum=1&menuNo=14013666&navTabId=hr0601','hr0601','照片导入');
  					}
  				} */
  			});
  	}});
	return false;
}



$('#allcheck').click(function(){  
    $('input[name="FILE_NO"]',navTab.getCurrentPanel()).prop("checked",this.checked);  
});
$('#allCheck1').click(function(){  
    $('input[name="singleCheck"]',navTab.getCurrentPanel()).prop("checked",this.checked);  
});
function baocun(){
	var ess_no= "";
	var empid="";
	var photourl="";
	 $("#photo_cc input[name='FILE_NO']",navTab.getCurrentPanel()).each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	if($(this).attr('valueempid')!=""){
	        		ess_no += $(this).val() + ",";
		        	empid +=$(this).attr('valueempid') +",";
		        	photourl +=$(this).attr('valueurl') +",";
	        	}
	        } //获取被选中的值
	    });
	   
	 if(empid!=""){
		 ess_no=ess_no.substring(0,ess_no.length-1);
		 empid=empid.substring(0,empid.length-1);
		 photourl=photourl.substring(0,photourl.length-1).replace(/[\r\n]/g,"");
		
		$('#ess_no',navTab.getCurrentPanel()).attr('value',ess_no);
		$('#empid',navTab.getCurrentPanel()).attr('value',empid);
		$('#photourl',navTab.getCurrentPanel()).attr('value',photourl);
		validateAddResumeInfoCallback('updatePersonInfoPhoto',navTabAjaxDone);
	 }else{
		 alert("<spring:message code="hrm.alert.empinfo.nocomplete_upload_information" />");//没有完整上传的信息
		 return false;
	 }
	 
	 
}
//关闭、打开部门所有节点
$(document).ready(function(){
	$("#orgCloseOpen",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewPhotoInfo"); 
		treeObj.expandAll(true);
	});
	$("#orgCloseClose",navTab.getCurrentPanel()).click(function(){
		var treeObj = $.fn.zTree.getZTreeObj("viewPhotoInfo"); 
		treeObj.expandAll(false);
	});
	//模糊搜索部门树，并打开节点
	$("#viewCurrentOrgInfoSrachOrg",navTab.getCurrentPanel()).click(function(){
		if($("#viewCurrentOrgInfo_deptName",navTab.getCurrentPanel()).val() != ""){
			var treeObj = $.fn.zTree.getZTreeObj("viewPhotoInfo"); 
			var treeNodes = treeObj.getNodesByParamFuzzy("DEPTNAME",$("#viewCurrentOrgInfo_deptName",navTab.getCurrentPanel()).val()); 
			for(var i=0;i<treeNodes.length;i++){
				treeObj.expandNode(treeNodes[i].getParentNode());
			}
		}else{
			alertMsg.info("<spring:message code="hrm.alert.empinfo.INPUT_SELECTINFO" />");//请先输入检索信息
		}
	});
});

//初始化右边页面
$(function(){
	openOnRight('/hrm/empinfo/viewEmployeePhotoInfo?flag=1','viewphoto_right_unit');
});
function onClick_viewPhotoInfo(event, treeId, treeNode){
	$("#viewCurrentOrgInfo_deptName",navTab.getCurrentPanel()).val(treeNode.DEPTNAME);
	$("#viewCurrentOrgInfo_deptNo",navTab.getCurrentPanel()).val(treeNode.DEPTNO);
	formSubmit();
}
function changeisWith(){
	var aa=$('#isWith',navTab.getCurrentPanel()).prop('checked');
	if(aa==true){
		$('#isWith',navTab.getCurrentPanel()).attr('value','YES');
	}else{
		$('#isWith',navTab.getCurrentPanel()).attr('value','NO');
	}
}
function qiehuan(){
	var bb=$('#empid_name',navTab.getCurrentPanel()).val();
	if(bb=="empid"){
		$('#isWith',navTab.getCurrentPanel()).attr('disabled','disabled');
	}else{
		$('#isWith',navTab.getCurrentPanel()).removeAttr('disabled');
	}
}
function chaxun(){
	var cc= $('#viewCurrentOrgInfo_deptNo',navTab.getCurrentPanel()).val();
	var dd= $('#empid_namevalue',navTab.getCurrentPanel()).val();
	var ff=$('#twodeptname',navTab.getCurrentPanel()).val();
	var gg=$('#QUERYDEPTNAME',navTab.getCurrentPanel()).val();
	if(ff=="1"){
		if(cc!=""||dd!=""){
			formSubmit();
		}else{
			alert("<spring:message code="hrm.alert.empinfo.deptno_name_empid" />");//请选择部门或者输入姓名,社号!
		}
	}else{
		if(gg!=""||dd!=""){
			formSubmit();
		}else{
			alert("<spring:message code="hrm.alert.empinfo.deptno_name_empid" />");//请选择部门或者输入姓名,社号!
		}
	}
	//alert(cc+"--"+dd+"--"+ff+"--"+gg);
}
function formSubmit(){
	$("tr[name='photo_result']",navTab.getCurrentPanel()).html("");
	var $form = $("#photoImport",navTab.getCurrentPanel());	
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type:'post',
		dataType:'json',
		url:'/hrm/empinfo/viewEmployeePhotoInfo',
		data:$form.serializeArray(),
		success:function(data){
			var list = data.viewEmployeePhoto;
			for (var i = 0; i < list.length; i++) {
				var str = '';
				str = str +  '<tr name="photo_result" onclick="xianshiPhoto('+(i+1)+')" id="results_'+list[i].EMPID+'" style="">'+
				'<td width="5%" class="td_type"  style="text-align: center">'+(i+1)+'.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
				var path = list[i].PHOTO_PATH;
				if(path != null){
					str = str +	'<input type="checkbox" name="singleCheck" id="singleCheck" value="'+list[i].EMPID+'" valueurl="'+list[i].PHOTO_PATH+'" valuename="'+list[i].LOCAL_NAME+'">';
				}
				str = str +  '</td>'+
				'<td width="5%" class="td_type"  style="text-align: center">'+list[i].LOCAL_NAME+'</td>'+
				'<td width="5%" class="td_type"  style="text-align: center">'+list[i].EMPID +'</td>'+
				'<td width="5%" class="td_type"  style="text-align: center">'+list[i].ORG_NAME_LOCAL+'</td>'+
				'<td width="5%" class="td_type"  style="text-align: center">'+list[i].POST_GRADE_NO_NAME+'</td>'+
			'<input type="hidden" id="first_'+(i+1)+'" value="'+list[i].PHOTO_PATH+'"></tr>';
			$("#photo_dd").append(str);
			}
		}
	});
}
function xianshiPhoto(count){
	var path=$('#first_'+count,navTab.getCurrentPanel()).val();
	if(path!='' && path!=null && path !='null'){
		$('#photoImage1',navTab.getCurrentPanel()).attr('src',path);
	}else{
		$('#photoImage1',navTab.getCurrentPanel()).attr('src','/resources/photo/default.jpg');
	}
	$('#singleCheck_'+count,navTab.getCurrentPanel()).attr('checked','checked');
	
}
function uploadAttDialogPhoto(id,val,seq,applyType){
	$.pdialog.open("/sys/notice/uploadWindowPhoto?id=" + id + "&val=" + val
			+ "&seq=" + seq + "&applyType=" + applyType, "uploadWindowPhoto", 
			"<spring:message code="hrm.alert.empinfo.upload_Enclosure" />", {width:550,height:320,mask:true});//附件上传
}
function baocunxingxiang(){
	/* /hrm/empinfo/execute */
	var ess_no="";
	var ess_name="";
	var ess_url="";
	var ess_namejpg="";
	var ess_empidjpg="";
	 $("#photo_dd input[name=singleCheck]",navTab.getCurrentPanel()).each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	if($(this).attr('valueurl')!=""){
	        		ess_no += $(this).val() + ",";
	        		ess_name += $(this).attr('valuename') + ",";
	        		ess_url += $(this).attr('valueurl') + ",";
	        		ess_namejpg += $(this).attr('valuename')+$(this).attr('valueurl').substring($(this).attr('valueurl').indexOf('.'),$(this).attr('valueurl').length) + ","; 
	        		ess_empidjpg += $(this).val()+$(this).attr('valueurl').substring($(this).attr('valueurl').indexOf('.'),$(this).attr('valueurl').length) + ","; 
	        	}
	        } //获取被选中的值
	    });
		 ess_no=ess_no.substring(0,ess_no.length-1);
		 ess_name=ess_name.substring(0,ess_name.length-1);
		 ess_url=ess_url.substring(0,ess_url.length-1);
		 ess_namejpg=ess_namejpg.substring(0,ess_namejpg.length-1);
		 ess_empidjpg=ess_empidjpg.substring(0,ess_empidjpg.length-1);
		 if(ess_no==""){
			 alert("<spring:message code="hrm.alert.empinfo.download_emp" />");//请选择要下载的人员!
		 }else{
			 if(ess_no.indexOf(",")<0){
				 if(ess_url==""){
					 alert("<spring:message code="hrm.alert.empinfo.nophoto" />");//没有照片!
				 }else{
				var downloadname=$('#downloadname',navTab.getCurrentPanel()).val();
				 if(downloadname=="namejpg"){
					 ess_name=ess_name+ess_url.substring(ess_url.indexOf('.'),ess_url.length);
				 }else{
					 ess_name=ess_no+ess_url.substring(ess_url.indexOf('.'),ess_url.length);
				 }
				 $('#baocunxingxiang',navTab.getCurrentPanel()).attr('href','/ess/infoApplyLeave/downloadFilePhoto?fileName='+ess_url+'&file='+ess_name);
				 }
			 }else{
				 var downloadname=$('#downloadname',navTab.getCurrentPanel()).val();
				 if(downloadname=="namejpg"){
					 ess_name=ess_namejpg;
				 }else{
					 ess_name=ess_empidjpg;
				 }
				 $('#baocunxingxiang',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/execute?fileName='+ess_url+'&file='+ess_name);
			 }
		 }

}
function deleteResults(){
	 var ess_no="";
	 var data = "";
	$("#photo_dd input[name=singleCheck]",navTab.getCurrentPanel()).each(function(){ //遍历table里的全部checkbox
        if($(this).attr("checked")){ //如果被选中
        		ess_no = $(this).val();
                data = "'"+ess_no+"',";
        } 
    });
	data = data + "'entity'";
	
	if(ess_no==""){
		alert('<spring:message code="hrm.alert.empinfo.Choice_emp" />');//请先选择员工!
	}
	 alertMsg.confirm ("<spring:message code='hrm.empinfo.SURE_BATCH_CANCEL.Z' />",{//确定要批量取消吗?
	        okCall:function(){
		    	$.ajax({
					type:'POST',
					url:'/hrm/empinfo/deleteEmployeePhotoInfo',
					data:[{ name: 'EMPID', value: data }],
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#photoImport",navTab.getCurrentPanel()));
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	}  ,
					error: DWZ.ajaxError
				});
	        }});
}
function chooseDepart(a){
	if(a=="1"){
		$('#twodeptname',navTab.getCurrentPanel()).attr('value','1');
		$('#orgTree2',navTab.getCurrentPanel()).attr('style','display:none');
		$('#orgTree',navTab.getCurrentPanel()).attr('style','width:280px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;');
	}else{
		$('#twodeptname',navTab.getCurrentPanel()).attr('value','2');
		$('#orgTree',navTab.getCurrentPanel()).attr('style','display:none');
		$('#orgTree2',navTab.getCurrentPanel()).attr('style','width:280px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;');
	}
}
function queryDepartment(){
	var QUERYDEPTNAME=$('#QUERYDEPTNAME',navTab.getCurrentPanel()).val();
	if(QUERYDEPTNAME==""){
		alert('<spring:message code="hrm.alert.empinfo.deptno_notnull" />');//部门不能为空!
	}else{
		$.ajax({
			type:'post',
			dataType:'json',
			url:'/hrm/empinfo/queryDepartment',
			data:{QUERYDEPTNAME:QUERYDEPTNAME},
			success:function(data){
				var qlist=data.queryDepartment;
				var str='<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1"><tr><td colspan="4" style="color:#000;font-size: 15px;">Total:'+qlist.length+'</td></tr><tr><td width="1%" class="td_title" >NO.</td><td width="4%" class="td_title" ><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td><td width="6%" class="td_title" >Dept&nbspName</td><td width="5%" class="td_title" ><spring:message code="org.title.MINISTER" /></td></tr>';
                   for(var i=0;i<qlist.length;i++){
                	   var orgnamelocal="'"+qlist[i]["ORG_NAME_LOCAL"]+"'";
                      str=str+'<tr onclick="tianchong('+orgnamelocal+')"><td width="1%" class="td_type">'+qlist[i]["ROW_NUM"]+'</td><td width="4%" class="td_type">'+qlist[i]["DEPTNO"]+'</td><td width="6%" class="td_type">'+qlist[i]["ORG_NAME_LOCAL"]+'</td><td width="5%" class="td_type">'+qlist[i]["LOCAL_NAME"]+'</td></tr>';
                    }
                   str=str+'</table>';
                    $('#allquerydepartment',navTab.getCurrentPanel()).html(str);
			},
		});
	}
	
}
function tianchong(bb){
    $('#QUERYDEPTNAME',navTab.getCurrentPanel()).attr('value',bb);	
}

function zhiqun(value,num){
	codeRelation(value,'GRADE_NO','${GRADE_NO}','<spring:message code="hr.viewCondSql.title.QINGXUANZE" />');
}
</script>
<div id="viewResumeList_photoImport" >
<div style="clear: both;"></div>
<div class="tabs" currentIndex="${currentIndex }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
		<li><a href="javascript:;">
			<span><spring:message code="hrm.empinfo.photo_login" /><!-- 照片登录 --> </span> </a>
		</li>
		<li><a href="javascript:;">
			<span><spring:message code="hrm.empinfo.search_download" /><!-- 查询及下载  --></span> </a>
		</li>
</ul>
</div>
</div>

<div class="tabsContent" id="displaycheckbox">
<form id="updatePersonInfoPhoto" method="post" action="/hrm/empinfo/updatePersonInfoPhoto" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTabAjaxDone);">
			<input type="hidden" id="ess_no" name ="ess_no" value="">
			<input type="hidden" id="photourl"  name ="photourl" value="">
			<input type="hidden" id="empid" name="empid"  value="">
			<input type="hidden" id="radiophoto" name="radiophoto"  value="radiophotoname">

<div  style="width: 97%;margin-left:auto;margin-right:auto;padding-top: 20px;" >
<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr>
<td class="td_title" width="5%"><spring:message code="hrm.empinfo.Restrict_additional_files" /><!-- 限制附加文件 --></td>
<td class="td_type" width="5%" ><spring:message code="hrm.empinfo.100KB" /><!-- 100KB --></td>
<td class="td_type" width="5%"></td>
<td class="td_type" width="5%"></td>
<td class="td_type" width="5%"></td>

</tr>
<tr>
<td class="td_title" width="5%"><spring:message code="hrm.empinfo.Distinguish_choice" /><!-- 区分选择 --></td>
<td class="td_type" width="5%">
<input type="radio" name="qufen" id="qufenempid" value="empid"  onclick="change('empid')" checked="checked">&nbsp&nbsp
	<spring:message code="hrm.empinfo.empid" /><!-- 社号 -->&nbsp&nbsp&nbsp&nbsp
<input type="radio" name="qufen" id="qufenname" value="name" onclick="change('name')">&nbsp&nbsp
	<spring:message code="hrm.empinfo.name" /><!-- 姓名 -->
</td>
<td class="td_type" width="5%"></td>
<td class="td_type" width="5%"></td>
<td class="td_type" width="5%"></td>

</tr>
</table>
	<a type="hidden"  id="fileupload" href="#" onclick="uploadAttDialogPhoto('viewResumeList_photoImport','/hrm/empinfo/photoImport?EDUC_NO=1','photo_name','photo_import')"></a>
	<a class="buttonActive"  target="dialog" href="/pa/fileImport/importFile?importFunName=uploadPhoto">
		<span><spring:message code="hr.viewPersonalInfo.title.UPLOADPHOTO" /><!--Upload photo --></span>
	</a>
	<a class="buttonActive" href="#" onclick="deleteAttList_new('viewResumeList_photoImport','/hrm/empinfo/photoImport?PERSON_ID=${PERSON_ID}&EDUC_NO=1',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
		<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
	<a class="buttonActive" onclick="baocun()" href="#">
		<span><spring:message code="button.sys.affirm.save" /><!-- 保存 --></span></a>
</div>
<!-- <div  style="padding-left: 300px;padding-top: 20px;">

</div> -->

<div class="all">
<div class="one"  >

<table id="photo_cc" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr>
<td class="td_title"  style="text-align: center" width="1%">NO.&nbsp;&nbsp;&nbsp;<input type="checkbox" id="allcheck"></td>
<td class="td_title"  style="text-align: center" width="5%"><spring:message code="hrm.empinfo.empid" /><!--工号--></td>
<td class="td_title"  style="text-align: center" width="5%"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></td>
<td class="td_title"  style="text-align: center" width="5%">
	<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 -->
</td>
<td class="td_title"  style="text-align: center" width="5%" ><spring:message code="hrm.empinfo.filename" /><!-- 文件名称 --></td>
<td class="td_title"  style="text-align: center" width="5%" ><spring:message code="ess.infoApply.renzhizhuangtai" /><!-- 任职状态 --></td>
<td class="td_title"  style="text-align: center" width="5%"><spring:message code="hrm.contract.distinguish" /><!-- 区分 --></td>
</tr>
<input type="hidden" id="countnum" value="${allcount }">
<span style="color:#000;font-size: 15px;">Total:${allcount }</span>
<c:forEach items="${fileList}" var="item" varStatus="i">
	<tr onclick="choosePhoto(${i.count })">
		<td class='td_type'>&nbsp;&nbsp;&nbsp;&nbsp;${i.count }.&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"  valueempid="${item.EMPID}" valueurl="${item.FILE_URL }" /></td>
		<c:if test="${item.LOCAL_NAME!=null }">
		<td class='td_type'  style="text-align: center">${item.EMPID }</td>
		</c:if>
		<c:if test="${item.LOCAL_NAME==null }">
		<td class='td_type' style="color:red;text-align: center;">
			<spring:message code="hrm.empinfo.nobody" /><!-- 查无此人 --></td>
		</c:if>
		<c:if test="${item.LOCAL_NAME!=null }">
		<c:if test="${item.CHONGFU=='CHONGFU' }">
		<td class='td_type' style="color:red;text-align: center;">${item.LOCAL_NAME }
			(<spring:message code="hrm.empinfo.repeat" />)</td><!-- 注意:名字重复 -->
		</c:if>
		<c:if test="${item.CHONGFU!='CHONGFU' }">
		<td class='td_type' style="text-align: center">${item.LOCAL_NAME }</td>
		</c:if>
		</c:if>
		<c:if test="${item.LOCAL_NAME==null }">
		<td class='td_type' style="color:red;text-align: center;">
			<spring:message code="hrm.empinfo.nobody" /><!-- 查无此人 --></td>
		</c:if>
		<td class='td_type' style="text-align: center">${item.DEPTNO_NAME }</td>
		<td class='td_type' style="text-align: center"><a href="#" onclick="choosePhoto(${i.count })">${item.FILE_NAME}</a></td>
	    <input type="hidden" id="photo_${i.count }" value="${item.FILE_URL }">
	    <td class='td_type' style="text-align: center">${item.EMP_OFFICE_NAME }</td>
	    <td class='td_type' style="text-align: center"><span id="qufenempidname_${i.count }">
	    	<spring:message code="hrm.empinfo.name" /><!-- 姓名 --></span></td>
	</tr>
</c:forEach>

</table>

</div>

<div  class="two"  >
<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr>
<td class="td_title" width="5%"><img id='photoImage' name='photoImage' src='' 
				border=1 style='width:220px;height:276px;' ></td>

</tr>
<tr>
</tr>
</table>
</div>
</div>

</form>
</div>
<div class="tabsContent" id="displaycheckbox1">
<form id="photoImport" method="post" action="/hrm/empinfo/photoImport" class="pageForm required-validate" 
			onsubmit="return navTabSearch(this);">
			<input type="hidden" id="flag" name="flag" value="2">
			<input type="hidden" id="twodeptname" name="twodeptname" value="1">
<div style="width: 97%;margin-left:auto;margin-right:auto;padding-top: 20px;">
<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr>
<td class="td_title" width="5%"><spring:message code="hrm.empinfo.personal_photo_information" /><!-- 个人照片信息 --></td>
<td class="td_title" width="5%">
<select id="empid_name" name="empid_name" onchange="qiehuan()">
<option value="localname" ><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></option>
<option value="empid" ><spring:message code="hrm.empinfo.empid" /></option>
</select>
</td>
<td class="td_title" width="5%"><input type="text" name="empid_namevalue" id="empid_namevalue" value="${empid_namevalue }"></td>
<td class="td_title" width="5%"><input type="checkbox" name="isWith" id="isWith" value="NO" onclick="changeisWith()">
<spring:message code="hrm.empinfo.Department_include" /><!-- 下位部门包括 --></td>
<td class="td_title" width="5%"><spring:message code="hrm.empinfo.download_filename" /><!-- 下载时文件名 --></td>
<td class="td_title" width="5%">
<select id="downloadname" name="downloadname" >
<option value="namejpg"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></option>
<option value="empidjpg"><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></option>
</select>
</td>
<td class="td_title" width="5%">
<td class="td_title" width="5%">
</tr>
<tr>
<td class="td_title" width="5%"><spring:message code="ess.infoApply.renzhizhuangtai" /><!-- 任职状态 --></td>
<td class="td_title" width="5%">
<ait:SelectSyCodeByCpnyID name="EMP_OFFICE" id="EMP_OFFICE"
                                    parentNo="15118" cnpyID="${defaultCpny}" selected="${EMP_OFFICE}" limit="all" />
</td>
<td class="td_title" width="5%"><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME" /><!-- 员工类型 --></td>
<td class="td_title" width="5%">
<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" id="EMP_TYPE_CODE"
                                    parentNo="13864" cnpyID="${defaultCpny}" selected="${EMP_TYPE_CODE}" limit="all" />
</td>
<td width="5%" class="td_title" ><spring:message code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
<td width="5%" class="td_title" >
	<ait:SelectSyCodeByCpnyID name="POST_FAMILY" selected="${POST_FAMILY}" parentNo="14015812" onChangeName="zhiqun(this.value,'1');" limit="all" />
</td>
<td width="5%" class="td_title" ><spring:message code="hrm.contract.Rank" /><!-- 职级 --></td>
<td width="5%" class="td_title" ><select name="GRADE_NO" id="GRADE_NO" ></select></td>
</tr>
</table>
<div class="tabs" currentIndex="${twodeptname }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
		<li><a href="javascript:;" onclick="chooseDepart('1')">
			<span><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></span></a>
		</li>
		<li><a href="javascript:;" onclick="chooseDepart('2')">
		<span><spring:message code="hrm.empinfo.deptno_search" /><!-- 部门搜索 --> </span></a>
		</li>
</ul>
</div>
</div>
</div>
<div id="orgTree" style="width:280px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;">
	<div style=" float:left; display:block; margin:10px; overflow:auto;width:250px; height:40px; border:solid 1px #CCC; background:#FFF;">
			<table class="searchContent" style="height:30px;line-height:30px;margin-top:5px;">
				<tr>
					<td width="100px;"  class="td_center">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
					<td width="200px;">
						<input type="text" id="viewCurrentOrgInfo_deptName" name="deptName" size="15" value="${deptName }"/>
						<input type="hidden" id="viewCurrentOrgInfo_deptNo" name="deptNo" size="15" value="${deptNo }"/>
						<a class="w_button" id="viewCurrentOrgInfoSrachOrg" href="#">
						<span><spring:message code="button.search" /><!-- 查询 --></span></a>
					</td>
				</tr>
			</table>
	</div>
	<div style=" float:left; display:block; margin-left:10px; overflow:hidden;width:250px;background:#FFF;">
		<input type="radio" id="orgCloseOpen" name="orgClose" value="open"/>&nbsp;&nbsp;
		<spring:message code="org.title.OPEN_ALL" /><!-- 全部打开 -->&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" id="orgCloseClose" name="orgClose" value="close"/>&nbsp;&nbsp;
		<spring:message code="org.title.CLOSE_ALL" /><!-- 全部关闭 -->
	</div>
	<div id="viewCurrentOrgInfo_tree_unit">
		<ait:deptTreeResume name="TREE_DEPTNO_PHOTO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewPhotoInfo" 
		current="1"  clickFun="1"
		style="float:left; display:block; margin:10px; overflow:auto;width:250px; height:400px; border:solid 1px #CCC; line-height:21px; background:#FFF;" selected="${DEPTNO}"/>
	</div>
</div>
<div id="orgTree2" style="width:280px;height:670px;line-height:570px;overflow:auto;overflow-x:hidden;float:left;display:none">
	<div style=" float:left; display:block; margin:10px;width:250px; height:40px; border:solid 1px #CCC; background:#FFF;">
			<table class="searchContent" style="height:30px;line-height:30px;margin-top:5px;">
				<tr>
					<td width="100px;"  class="td_center">
						<spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
					<td width="200px;">
						<input type="text" id="QUERYDEPTNAME" name="QUERYDEPTNAME" size="15" value="${QUERYDEPTNAME }"/>
						<input type="hidden" id="viewCurrentOrgInfo_deptNo" name="deptNo" size="15" value="${deptNo }"/>
						<a class="w_button"  href="#" onclick="queryDepartment()">
						<span><spring:message code="button.search" /><!-- 查询 --></span></a>
					</td>
				</tr>
			</table><br>
			<div id="allquerydepartment">
			<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td colspan='4' style="color:#000;font-size: 15px;">Total:</td>
				</tr>
				<tr>
				<td width="1%" class="td_title" >NO.</td>
				<td width="4%" class="td_title" ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
				<td width="6%" class="td_title" >Dept&nbspName</td>
				<td width="5%" class="td_title" ><spring:message code="hrm.empinfo.HEAD_DEPARTMENT" /><!-- 部门长 --></td>
				</tr>
				</table>
				</div>
	</div>
	
</div>
 <a class="buttonActive" id="baocunxingxiang"  href="#" onclick="baocunxingxiang()">
					<span><spring:message code="hrm.empinfo.save_image" /><!-- 保存形象 --></span>
				</a>
				<a class="buttonActive" href="#" onclick="chaxun()">
				<span><spring:message code="button.search" /><!-- 查询 --></span></a>
				<a class="buttonActive" onclick="deleteResults()" href="#">
				<span><spring:message code="button.delete" /><!-- 删除 --></span></a>
				
<div class="all">
<div class="three"  >

<table id="photo_dd" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<%-- <tr><td><span style="color:#000;font-size: 15px;">Total:${viewEmployeePhotoCount }</span></td><td></td><td></td><td></td><td></td></tr>
 --%><tr id="photo_dd_tr">
<td width="5%" class="td_title" style="text-align: center">NO.&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="allCheck1" id="allCheck1"></td>
<td width="5%" class="td_title" style="text-align: center"><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></td>
<td width="5%" class="td_title" style="text-align: center"><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></td>
<td width="5%" class="td_title" style="text-align: center"><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
<td width="5%" class="td_title" style="text-align: center"><spring:message code="hrm.contract.Rank" /><!-- 职级 --></td>
</tr>
<%-- <c:forEach items="${viewEmployeePhoto }" var="ep" varStatus="i">

 <tr onclick="xianshiPhoto(${i.count })" id="results_${ep.EMPID}" style="">
	<td width="5%" class="td_type">${i.count }.&nbsp&nbsp&nbsp<input type="checkbox" name="singleCheck" id="singleCheck" value="${ep.EMPID}" valueurl="${ep.PHOTO_PATH }" valuename="${ep.LOCAL_NAME}"></td>
	<td width="5%" class="td_type">${ep.LOCAL_NAME}</td>
	<td width="5%" class="td_type">${ep.EMPID}</td>
	<td width="5%" class="td_type">${ep.ORG_NAME_LOCAL}</td>
	<td width="5%" class="td_type">${ep.POST_GRADE_NO_NAME}</td>
<input type="hidden" id="first_${i.count }" value="${ep.PHOTO_PATH }">
 </tr>

</c:forEach> --%>
</table>
</div>
<div class="four">
<img id='photoImage1' name='photoImage1' src='' 
				border=1 style='width:220px;height:276px;' >

</div>
</div>
</div>
</form>
</div>
</div>
</div>