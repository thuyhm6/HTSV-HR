<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
 
	function uploadExcel(form, callback){
		  var imgpath=document.getElementById("proveFileName").value;
		  var imgPostName=imgpath.substr(imgpath.lastIndexOf('\\')+1);//传传的文件名称有后缀
		  var imgPostfix=imgpath.substr(imgpath.lastIndexOf('.')+1);//上传文件的后缀
		  var getid= new Date().getTime();//用来当唯一的附件名称
		  $("#UPLOADING_NAME").val(getid);
		 //alert(imgpath);
		//window.parent.window.document.getElementById("FILE_NAME").value;
		
		//附件名称和路径传回父页面
		var FILE_NAME=document.getElementById("FILE_NAME").value;
		var FILE_URL=document.getElementById("FILE_URL").value;
		//alert(imgPostfix);
		window.parent.window.document.getElementById(FILE_NAME).value=imgPostName;
		window.parent.window.document.getElementById(FILE_URL).value=getid+"."+imgPostfix;
		// return false;
		  if(imgpath != ""){
		     
		         // window.document.addimgform.action= "/pa/excelImport/importExcel";
			      //window.document.addimgform.fireSubmit();
			     
		    	 var $form = $(form), $iframe = $("#callbackframe");
		    		if(!$form.valid()) {return false;}
		    		 
		    		if ($iframe.size() == 0) {alert(1);
		    			$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		    			$form.appendTo($iframe);
		    		}
		    		if(!form.ajax) {
		    			$form.append('<input type="hidden" name="ajax" value="1" />');
		    		}
		    	_iframeResponse($iframe[0], callback || DWZ.ajaxDone); 
		   
		   }else{
			   	//上传文件不能为空！
		        alert("<spring:message code='ar.alert.message.excelimport.fillnull'/>");
		   }
	} 
</script>

<div >
	<center>
		<div>
			 
		<form id="addimgform" method="post" action="/ess/infoApply/proveFileUploading" 
			enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return uploadExcel(this,dialogAjaxDone);" target="callbackframe">
			   <table width="100%" >
				   <tr><td style="text-align: center;padding:5px">&nbsp;&nbsp;<input type="file" name="proveFileName" id="proveFileName"/>
				   <input  type="hidden" id="UPLOADING_NAME" name="UPLOADING_NAME"/>
				   <input  type="hidden" id="FILE_URL" name="FILE_URL" value="${FILE_URL }"/>
				   <input  type="hidden" id=FILE_NAME name="FILE_NAME" value="${FILE_NAME }"/>
				   <iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe></td>
				   <td style="text-align: center;padding:5px"><div class="buttonActive" style="text-align: center;"><div class="buttonContent" style="text-align: center;"><button type="submit" style="text-align: center;"><!-- 提交 --><spring:message code="submit"/></button></div></div>
				   </td></tr>
			   </table>
			 </form>
		</div>
	</center>
</div>
