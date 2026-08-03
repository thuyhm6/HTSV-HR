/**
 * @author ZhangHuihua@msn.com
 * 
 */

/**
 * 普通ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 */
function validateCallback(form, callback) {
	
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
/**
 * 带文件上传的ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 */
function iframeCallback(form, callback){
	var $form = $(form), $iframe = $("#callbackframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		$form.appendTo($iframe);
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	
	//form.target = "callbackframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
function _iframeResponse(iframe, callback){
	var $iframe = $(iframe), $document = $(document);
	
	$document.trigger("ajaxStart");
	
	$iframe.bind("load", function(event){
		
		$iframe.unbind("load");
		$document.trigger("ajaxStop");
		
		if (iframe.src == "javascript:'%3Chtml%3E%3C/html%3E';" || // For Safari
			iframe.src == "javascript:'<html></html>';") { // For FF, IE
			return;
		}

		var doc = iframe.contentDocument || iframe.document;

		// fixing Opera 9.26,10.00
		if (doc.readyState && doc.readyState != 'complete') return; 
		// fixing Opera 9.64
		if (doc.body && doc.body.innerHTML == "false") return;
	   
		var response;
		
		if (doc.XMLDocument) {
			// response is a xml document Internet Explorer property
			response = doc.XMLDocument;
		} else if (doc.body){
			try{
				response = $iframe.contents().find("body").html().replace("amp;","&");
				response = jQuery.parseJSON(response);
			} catch (e){ // response is html document or plain text
				response = doc.body.innerHTML;
			}
		} else {
			// response is a xml document
			response = doc;
		}
		
		callback(response);
	});
}

/**
 * navTabAjaxDone是DWZ框架中预定义的表单提交回调函数．
 * 服务器转回navTabId可以把那个navTab标记为reloadFlag=1, 下次切换到那个navTab时会重新载入内容. 
 * callbackType如果是closeCurrent就会关闭当前tab
 * 只有callbackType="forward"时需要forwardUrl值
 * navTabAjaxDone这个回调函数基本可以通用了，如果还有特殊需要也可以自定义回调函数.
 * 如果表单提交只提示操作是否成功, 就可以不指定回调函数. 框架会默认调用DWZ.ajaxDone()
 * <form action="/user.do?method=save" onsubmit="return validateCallback(this, navTabAjaxDone)">
 * 
 * form提交后返回json数据结构statusCode=DWZ.statusCode.ok表示操作成功, 做页面跳转等操作. statusCode=DWZ.statusCode.error表示操作失败, 提示错误原因. 
 * statusCode=DWZ.statusCode.timeout表示session超时，下次点击时跳转到DWZ.loginUrl
 * {"statusCode":"200", "message":"操作成功", "navTabId":"navNewsLi", "forwardUrl":"", "callbackType":"closeCurrent"}
 * {"statusCode":"300", "message":"操作失败"}
 * {"statusCode":"301", "message":"会话超时"}
 * {"statusCode":"3000", "message":"上传文件成功"}  
 */
function navTabAjaxDone(json){
	DWZ.ajaxDone(json);
	
	if (json.statusCode == DWZ.statusCode.ok){
		uploadfy_destory();
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			navTabPageBreak({}, json.rel);
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab();}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		}
	}
}


function navTabAjaxDoneWithForm(json){
	DWZ.ajaxDone(json);
	
	if (json.statusCode == DWZ.statusCode.ok){
		uploadfy_destory();
		if (json.formId){
			navTabSearch($("#" + json.formId,json.navTabId));
		}  else { //重新载入当前navTab页面
			navTabPageBreak({}, json.rel);
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab();}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		}
	}
}

function navTabAjaxDoneWithParentParam(json){
	DWZ.ajaxDone(json);
	
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlagWithParentParam(json.navTabId,json.parentParam);
		} else { //重新载入当前navTab页面
			navTabPageBreak({}, json.rel);
		}
		
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab();}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		}
	}
}

function navTabAjaxDoneRefreshCurrentPage(json){
	if (json.statusCode == DWZ.statusCode.ok){
		alertMsg.info(json.message);
		if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			navTab.reloadFlag(json.navTabId);
		} else { //重新载入当前navTab页面
			navTabPageBreak({}, json.rel);
		}
		if ("closeCurrent" == json.callbackType) {
			setTimeout(function(){navTab.closeCurrentTab();}, 100);
		} else if ("forward" == json.callbackType) {
			navTab.reload(json.forwardUrl);
		}
	}
}

/**
 * dialog上的表单提交回调函数
 * 服务器转回navTabId，可以重新载入指定的navTab. statusCode=DWZ.statusCode.ok表示操作成功, 自动关闭当前dialog
 * 
 * form提交后返回json数据结构,json格式和navTabAjaxDone一致
 */
function dialogAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		uploadfy_destory();
		if (json.navTabId){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} else if (json.rel) {
			
			navTabPageBreak({}, json.rel);
		}
		$.pdialog.closeCurrent();
	}
	//自定义ok1:3000 ；不关闭当前 dialog
	else if(json.statusCode == DWZ.statusCode.ok1){
		if (json.navTabId){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} 
	}
}

function dialogAjaxDoneWithForm(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.formId){
			navTabSearch($("#" + json.formId));
		} else if (json.rel) {
			navTabPageBreak({}, json.rel);
		}
		$.pdialog.closeCurrent();
	}
	//自定义ok1:3000 ；不关闭当前 dialog
	else if(json.statusCode == DWZ.statusCode.ok1){
		if (json.navTabId){
			navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
		} 
	}
}

function doAjaxDoneWithForm(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.formId){
			navTabSearch($("#" + json.formId));
		}
	}
	//自定义ok1:3000 ；不关闭当前 dialog
	else if(json.statusCode == DWZ.statusCode.ok1){
		if (json.formId){
			navTabSearch($("#" + json.formId));
		}
	}
}

/**
 * 处理navTab上的查询, 会重新载入当前navTab
 * @param {Object} form
 */
function navTabSearch(form, navTabId){
	var $form = $(form);
		
	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	
	var params = $(form).serializeArray();
	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
	}
	
	if($("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']")){
		params.push({name: DWZ.pageInfo.numPerPage, value: $("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']").val()}) ;
	}
	
	navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	return false;
}
function navTabSearchWithParam(form, params){
	var $form = $(form);
		
	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	
	//var params = $(form).serializeArray();
	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
	}
	
	if($("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']")){
		params.push({name: DWZ.pageInfo.numPerPage, value: $("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']").val()}) ;
	}
	
	navTab.reload($form.attr('action'), {data: params });
	return false;
}
/**
 * 处理dialog弹出层上的查询, 会重新载入当前dialog
 * @param {Object} form
 */
function dialogSearch(form){
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	$.pdialog.reload($form.attr('action'), {data: $form.serializeArray()});
	return false;
}


/**
 * 刷新弹出框上的form
 * @param {Object} form
 */
function dialogAjaxDoneWithFormNoClose(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		if (json.formId){
			dialogSearch($("#" + json.formId));
		} else if (json.rel) {
			navTabPageBreak({}, json.rel);
		}
	}
}

function dwzSearch(form, targetType){
	if (targetType == "dialog") dialogSearch(form);
	else navTabSearch(form);
	return false;
}
/**
 * 处理div上的局部查询, 会重新载入指定div
 * @param {Object} form
 */
function divSearch(form, rel){
	var $form = $(form);
	if (form[DWZ.pageInfo.pageNum]) form[DWZ.pageInfo.pageNum].value = 1;
	if (rel) {
		var $box = $("#" + rel);
		$box.ajaxUrl({
			type:"POST", url:$form.attr("action"), data: $form.serializeArray(), callback:function(){
				$box.find("[layoutH]").layoutH();
			}
		});
	}
	return false;
}
/**
 * 
 * @param {Object} args {pageNum:"",numPerPage:"",orderField:"",orderDirection:""}
 * @param String formId 分页表单选择器，非必填项默认值是 "pagerForm"
 */
function _getPagerForm($parent, args) {

	var form = $("#pagerForm", $parent).get(0);
	
	if (form) {
		if (args["pageNum"]) form[DWZ.pageInfo.pageNum].value = args["pageNum"];
		if (args["numPerPage"]) form[DWZ.pageInfo.numPerPage].value = args["numPerPage"];
		if (args["orderField"]) form[DWZ.pageInfo.orderField].value = args["orderField"];
		if (args["orderDirection"] && form[DWZ.pageInfo.orderDirection]) form[DWZ.pageInfo.orderDirection].value = args["orderDirection"];
	}
	
	return form;
}


/**
 * 处理navTab中的分页和排序
 * targetType: navTab 或 dialog
 * rel: 可选 用于局部刷新div id号
 * data: pagerForm参数 {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * callback: 加载完成回调函数
 */
function dwzPageBreak(options){
	
	var op = $.extend({ targetType:"navTab", rel:"", data:{pageNum:"", numPerPage:"", orderField:"", orderDirection:""}, callback:null}, options);
	var $parent = op.targetType == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();

	
	if (op.rel) {
		
		var $box = $parent.find("#" + op.rel);
		var form = _getPagerForm($box, op.data);
		
		if (form) {
			$box.ajaxUrl({
				type:"POST", url:$(form).attr("action"), data: $(form).serializeArray(), callback:function(){
					$box.find("[layoutH]").layoutH();
				}
			});
		}
	} else {
		
		var form = _getPagerForm($parent, op.data);
		
		var params = $(form).serializeArray();
		
		if (op.targetType == "dialog") {
			
			if (form) $.pdialog.reload($(form).attr("action"), {data: params, callback: op.callback});
			
		} else {
			
			if (form) navTab.reload($(form).attr("action"), {data: params, callback: op.callback});
			
		}
	}
}
/**
 * 处理navTab中的分页和排序
 * @param args {pageNum:"n", numPerPage:"n", orderField:"xxx", orderDirection:""}
 * @param rel： 可选 用于局部刷新div id号
 */
function navTabPageBreak(args, rel){
	
	dwzPageBreak({targetType:"navTab", rel:rel, data:args});
}
/**
 * 处理dialog中的分页和排序
 * 参数同 navTabPageBreak 
 */
function dialogPageBreak(args, rel){
	
	dwzPageBreak({targetType:"dialog", rel:rel, data:args});
}


function ajaxTodo(url, callback){
	var $callback = callback || navTabAjaxDone;
	if (! $.isFunction($callback)) $callback = eval('(' + callback + ')');
	$.ajax({
		type:'POST',
		url:url,
		dataType:"json",
		cache: false,
		success: $callback,
		error: DWZ.ajaxError
	});
}

/**
 * http://www.uploadify.com/documentation/uploadify/onqueuecomplete/	
 */
function uploadifyQueueComplete(queueData){

  var msg = "The total number of files uploaded: "+queueData.uploadsSuccessful+"<br/>"
    + "The total number of errors while uploading: "+queueData.uploadsErrored+"<br/>"
    + "The total number of bytes uploaded: "+queueData.queueBytesUploaded+"<br/>"
    + "The average speed of all uploaded files: "+queueData.averageSpeed;
  
  if (queueData.uploadsErrored) {
		alertMsg.error(msg);
  } else {
		alertMsg.correct(msg);
  }
}
/**
 * http://www.uploadify.com/documentation/uploadify/onuploadsuccess/
 */
function uploadifySuccess(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
  var fileResult = data.split(";");
  //第一个文件
  if(files==""){
    files = fileResult[0];
    fileName = fileResult[0];
    fileUrl = fileResult[1];
  }else{
    files+=";"+fileResult[0];
    fileName+=";"+fileResult[0];
    fileUrl+=";"+fileResult[1];
  }
  $("#fileNmae",navTab.getCurrentPanel()).html(files);
  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
  $("#fileName",navTab.getCurrentPanel()).val(fileName);
}

/**
 * http://www.uploadify.com/documentation/uploadify/onuploaderror/
 */
function uploadifyError(file, errorCode, errorMsg) {
//	alertMsg.error(errorCode+": "+errorMsg);
}

/**
 * A function that triggers when all file uploads have completed. There is no default event handler.
 * @param {Object} event: The event object.
 * @param {Object} data: An object containing details about the upload process:
 * 		- filesUploaded: The total number of files uploaded
 * 		- errors: The total number of errors while uploading
 * 		- allBytesLoaded: The total number of bytes uploaded
 * 		- speed: The average speed of all uploaded files	
 */
function uploadifyAllComplete(event, data){
	if (data.errors) {
		var msg = "The total number of files uploaded: "+data.filesUploaded+"\n"
			+ "The total number of errors while uploading: "+data.errors+"\n"
			+ "The total number of bytes uploaded: "+data.allBytesLoaded+"\n"
			+ "The average speed of all uploaded files: "+data.speed;
		alert("event:" + event + "\n" + msg);
	}
}
/**
 * http://www.uploadify.com/documentation/
 * @param {Object} event
 * @param {Object} queueID
 * @param {Object} fileObj
 * @param {Object} response
 * @param {Object} data
 */
function uploadifyComplete(event, queueId, fileObj, response, data){
	DWZ.ajaxDone(DWZ.jsonEval(response));
}

$.fn.extend({
	ajaxTodo:function(){
		return this.each(function(){
			var $this = $(this);
			$this.click(function(event){
				var url = unescape($this.attr("href")).replaceTmById($(event.target).parents(".unitBox:first"));
				DWZ.debug(url);
				if (!url.isFinishedTm()) {
					alertMsg.error($this.attr("warn") || DWZ.msg("alertSelectMsg"));
					return false;
				}
				var title = $this.attr("title");
				if (title) {
					alertMsg.confirm(title, {
						okCall: function(){
							ajaxTodo(url, $this.attr("callback"));
						}
					});
				} else {
					ajaxTodo(url, $this.attr("callback"));
				}
				event.preventDefault();
			});
		});
	},
	dwzExport: function(){
		function _doExport($this) {
			var $p = $this.attr("targetType") == "dialog" ? $.pdialog.getCurrent() : navTab.getCurrentPanel();
			var $form = $("#pagerForm", $p);
			var url = $this.attr("href");
			window.location = url+(url.indexOf('?') == -1 ? "?" : "&")+$form.serialize();
		}
		
		return this.each(function(){
			var $this = $(this);
			$this.click(function(event){
				var title = $this.attr("title");
				if (title) {
					alertMsg.confirm(title, {
						okCall: function(){_doExport($this);}
					});
				} else {_doExport($this);}
			
				event.preventDefault();
			});
		});
	}
});
//要传进的参数分别为 -1，人员类型组select id，人员类型select id，法人选项id，要查询的是否为group，权限super/hr/ar/pa
//var ajaxEmpTypeForGroupToList;
function ajaxEmpTypeForGroupToList(EMP_TYPE,GROUP_ID,TYPE_ID,CPNY_ID,LIMIT) {
		//if (ajaxEmpTypeForGroupToList != null) {
		//	ajaxEmpTypeForGroupToList.abort();
		//}
		//alert(GROUP_ID.value);
		var GROUP_IDv;
		var CPNY_IDv;
		var TYPE_IDv;
		var LIMITv;
		if(EMP_TYPE!=-1){
			GROUP_IDv=$("#"+GROUP_ID).val();
			CPNY_IDv=$("#"+CPNY_ID).val();
			TYPE_IDv=TYPE_ID;
			LIMITv=$("#"+LIMIT).val();
		}else if(EMP_TYPE==-1){
			GROUP_IDv=GROUP_ID.value;
			CPNY_IDv=CPNY_ID.value;
			TYPE_IDv=TYPE_ID.id;
			LIMITv=LIMIT.value;
		}
		//alert(GROUP_IDv+"|"+CPNY_IDv+"|"+TYPE_IDv+"|"+LIMITv);
		$.ajaxSettings.global = false;
		ajaxEmpTypeForGroupToList2 = $.ajax( {
			type : "POST",
			url : "/hrm/jobType/getEmpTypeForGroupToList",
			//data : { EMP_TYPE_GROUP:$("#"+GROUP_ID).val() , CPNYID : $("#"+CPNY_ID).val(),limit:LIMIT},
			data : { EMP_TYPE_GROUP:GROUP_IDv , CPNYID : CPNY_IDv, limit:LIMITv},
			dataType : "json",
			success : function(data) {
				$('#'+TYPE_IDv).html("");
				var html = '<option value="">请选择</option>';
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#'+TYPE_IDv).html(html);
				//TYPE_ID.html(html);
				if(EMP_TYPE != -1){
					$("#"+TYPE_IDv).val(EMP_TYPE);
					//TYPE_ID.val(EMP_TYPE);
				}
			}
		});
		$.ajaxSettings.global = true;
}

function divAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		uploadfy_destory();
		if (json.divId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			openOnRight(json.divIdUrl,json.divId);
		}
	}
}

function dialogDivAjaxDone(json){
	DWZ.ajaxDone(json);
	if (json.statusCode == DWZ.statusCode.ok){
		uploadfy_destory();
		if (json.divId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
			openOnRight(json.divIdUrl,json.divId);
		}
		$.pdialog.closeCurrent();
	}
}

function uploadfy_destory(){
	if ($('#testFileInput_Leave').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_Leave').uploadify('destroy');
	}
	if ($('#testFileInput_Lot').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_Lot').uploadify('destroy');
	}
	if ($('#testFileInput_Pot').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_Pot').uploadify('destroy');
	}
	if ($('#testFileInput_arMacRec').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_arMacRec').uploadify('destroy');
	}
	if ($('#testFileInput_annual').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_annual').uploadify('destroy');
	}
	if ($('#testFileInput_resume').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	     $('#testFileInput_resume').uploadify('destroy');
	}
}
