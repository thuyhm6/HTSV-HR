/*
 *左右移动 隐藏
 */
function hiddenleft(hiddenId,showId){
	$("#" + hiddenId,navTab.getCurrentPanel()).hide();
	$("#" + showId,navTab.getCurrentPanel()).show();
	$("#layout3",navTab.getCurrentPanel()).show();
	$("#layout5",navTab.getCurrentPanel()).hide();
	$("#layout2",navTab.getCurrentPanel()).hide();
	$("#layout4",navTab.getCurrentPanel()).hide();
}
function showId(showId){
	$("#" + showId,navTab.getCurrentPanel()).show();
	$("#" + showId,navTab.getCurrentPanel()).css("width","430px");
	$("#layout5",navTab.getCurrentPanel()).show();
	$("#layout2",navTab.getCurrentPanel()).show();
	$("#layout3",navTab.getCurrentPanel()).hide();
	$("#layout4",navTab.getCurrentPanel()).hide();
}
function showIdLeft(showId,cssId){
	$("#" + showId,navTab.getCurrentPanel()).show();
	$("#" + cssId,navTab.getCurrentPanel()).css("width","430px");
	$("#layout5",navTab.getCurrentPanel()).show();
	$("#layout2",navTab.getCurrentPanel()).show();
	$("#layout3",navTab.getCurrentPanel()).hide();
	$("#layout4",navTab.getCurrentPanel()).hide();
}
function hiddenRight(hiddenId,showId){
	$("#" + hiddenId,navTab.getCurrentPanel()).hide();
	$("#" + showId,navTab.getCurrentPanel()).show();
	$("#" + showId,navTab.getCurrentPanel()).css("width","98%");
	$("#layout5",navTab.getCurrentPanel()).hide();
	$("#layout2",navTab.getCurrentPanel()).show();
	$("#layout3",navTab.getCurrentPanel()).hide();
	$("#layout4",navTab.getCurrentPanel()).show();
}
//印刷
function print(){
	$("div[sysLong='printDiv']",navTab.getCurrentPanel()).jqprint();
}
//印刷 弹窗
function printDialog(){
	$("div[sysLong='printDiv']",$.pdialog.getCurrent()).jqprint();
}
//评价添加备注
function openEvsCommentWindow(currentActivity,currentPageActivity,index,affirmContent){
	var editFlag = 0;
	if(currentActivity == currentPageActivity){
		editFlag = 1;
	}
	$.pdialog.open(encodeURI("/evs/manage/viewEvsComment?editFlag=" + editFlag + "&index=" + index + "&seach_affirmContent=" + affirmContent), "evsComment", "Comment", {width:500,height:270,mask:true});
}
/*
*名称:图片上传本地预览插件 v1.1
*作者:周祥
*时间:2013年11月26日
*介绍:基于JQUERY扩展,图片上传预览插件 目前兼容浏览器(IE 谷歌 火狐) 不支持safari
*插件网站:http://keleyi.com/keleyi/phtml/image/16.htm
*参数说明: Img:图片ID;Width:预览宽度;Height:预览高度;ImgType:支持文件类型;Callback:选择文件显示图片后回调方法;
*使用方法: 
<div>
<img id="ImgPr" width="120" height="120" /></div>
<input type="file" id="up" />
把需要进行预览的IMG标签外 套一个DIV 然后给上传控件ID给予uploadPreview事件
$("#up").uploadPreview({ Img: "ImgPr", Width: 120, Height: 120, ImgType: ["gif", "jpeg", "jpg", "bmp", "png"], Callback: function () { }});
*/
jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 100,
            Height: 100,
            ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {
                    alert("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    return false
                }
                if ($.browser.msie) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus()
                        } else {
                            _self.blur()
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
                    }
                } else {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                }
                opts.Callback()
            }
        })
    }
});

/**
 * CODE级联
 **/
function codeRelation(parentCodeValue,sonCodeId,sonValue,sonSelect,typeFlag){
	var sonVal = '';
	if(parentCodeValue != ''){
		$.ajaxSettings.global = false;
		$.ajax( {
			type : "POST",
			url : "/sys/basicMaintenance/getCodeRelation",
			data : { seach_PARENT_CODE_NO : parentCodeValue, type : typeFlag },
			dataType : "json",
			success : function(data) {
				//先清空
				if(sonCodeId == 'MAIN_BUSINESS'){
					$('#' + sonCodeId,navTab.getCurrentPanel()).html("<option value=''>" + sonSelect + "</option>");
				}else{
					$('#' + sonCodeId,navTab.getCurrentPanel()).html("<option value=''>" + sonSelect + "</option>");
				}
				var index = 0;
				//循环填充下拉框
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
						index = index + 1;
						if(sonValue == comment['CODE_NO']){
							sonVal = sonValue;
						}
						$('#' + sonCodeId,navTab.getCurrentPanel()).append('<option value="' + comment['CODE_NO'] + '">' + comment['CONTENT'] + '</option>');
					});
				}
				//如果所选中的值不存在，就不需要执行选中方法
				if(sonVal != null && sonVal != ''){
					$("#" + sonCodeId,navTab.getCurrentPanel()).attr("value",sonVal);
				}
				if(sonCodeId == 'MAIN_BUSINESS'){
					if(index > 0){
						$("#MAIN_BUSINESS",navTab.getCurrentPanel()).get(0).selectedIndex=1;
					}
					$("#MAIN_BUSINESS",navTab.getCurrentPanel()).triggerHandler('change');
				}
			}
		});
		$.ajaxSettings.global = true;
	}else{
		//先清空
		$('#' + sonCodeId,navTab.getCurrentPanel()).html("");
	}
}
/**
 * CODE级联 带请选择
 **/
function codeRelation2(parentCodeValue,sonCodeId,sonValue,typeFlag){
	var sonVal = '';
	if(parentCodeValue != ''){
		$.ajaxSettings.global = false;
		$.ajax( {
			type : "POST",
			url : "/sys/basicMaintenance/getCodeRelation",
			data : { seach_PARENT_CODE_NO : parentCodeValue, type : typeFlag },
			dataType : "json",
			success : function(data) {
				//先清空
				if(sonCodeId == 'MAIN_BUSINESS'){
					$('#' + sonCodeId,navTab.getCurrentPanel()).html("<option value=''>请选择</option>");
				}else{
					$('#' + sonCodeId,navTab.getCurrentPanel()).html("<option value=''>请选择</option>");
				}
				var index = 0;
				//循环填充下拉框
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
						index = index + 1;
						if(sonValue == comment['CODE_NO']){
							sonVal = sonValue;
						}
						$('#' + sonCodeId,navTab.getCurrentPanel()).append('<option value="' + comment['CODE_NO'] + '">' + comment['CONTENT'] + '</option>');
					});
				}
				//如果所选中的值不存在，就不需要执行选中方法
				if(sonVal != null && sonVal != ''){
					$("#" + sonCodeId,navTab.getCurrentPanel()).attr("value",sonVal);
				}
				if(sonCodeId == 'MAIN_BUSINESS'){
					if(index > 0){
						$("#MAIN_BUSINESS",navTab.getCurrentPanel()).get(0).selectedIndex=1;
					}
					$("#MAIN_BUSINESS",navTab.getCurrentPanel()).triggerHandler('change');
				}
			}
		});
		$.ajaxSettings.global = true;
	}else{
		//先清空
		$('#' + sonCodeId,navTab.getCurrentPanel()).html("");
	}
}


/**
 * 上传附件窗口
 */
function uploadAttDialog(id,val,seq,applyType){
	if(seq.length < 1){
		alertMsg.info("请先保存信息，再上传附件");
		return false;
	}
	$.pdialog.open("/sys/notice/uploadWindow?id=" + id + "&val=" + val
			+ "&seq=" + seq + "&applyType=" + applyType, "uploadWindow", "附件上传", {width:550,height:320,mask:true});
}
//照片导入窗口


/**
 * 删除附件
 */
function deleteAttList(id,val,callback){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info("请先选择要删除的附件");
		return false;
	}

	alertMsg.confirm("确定要删除吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}


/**
 * 上传附件窗口(新增)
 */
function uploadAttDialogInsert(){
	$.pdialog.open("/sys/notice/uploadWindowInsert", "uploadWindow", "Upload_File", {width:550,height:320,mask:true});
}

/**
 * 删除附件(新增)
 */
function deleteAttListInsert(){
	var flag=false;
	$("input[name='FILE_NO']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			$(this).parent().parent().remove();
			flag = true;
		}
	});
	if(flag == false){
		alertMsg.info("请先选择要删除的附件");
		return false;
	}
}

/**
 * excel导出带form表单搜索条件
 * @param url
 * @return
 */
function downloadExcel(formId,excelUrl,searchUrl){
	$('#' + formId,navTab.getCurrentPanel()).attr("action",excelUrl);
	$('#' + formId,navTab.getCurrentPanel()).attr("onsubmit",'');
	$('#' + formId,navTab.getCurrentPanel()).submit();
	$('#' + formId,navTab.getCurrentPanel()).attr("action",searchUrl);
	$('#' + formId,navTab.getCurrentPanel()).attr("onsubmit",'return navTabSearch(this);');
}



/**
 * 评价确定
 * @param url
 * @return
 */
function viewAffirmTarget1_confirm(){
	//获取页面的值
	var jsonData = '[';
	$("input:[name='EVS_OBJECT_SEQ']",navTab.getCurrentPanel()).each(function(i, obj){
		if($("#evsGrade_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).find("option:selected").attr("name") == ''){
			alertMsg.warn("There are no evaluation of the object, please evaluation"); //还有未评价的对象，请先评价
			return false;
		}
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_OBJECT_SEQ": "' + $(obj).val() + '" ,';
		jsonData += ' "SEQ": "' + $(obj).parent().find('input:[name="EVS_SEQ"]').val() + '" ,';
		jsonData += ' "FLAG": "1" ,';
		if( isNaN($("#evsPoint_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).val())){
			jsonData += ' "EVS_POINT": "" ,';
		}else{
			jsonData += ' "EVS_POINT": "' + $("#evsPoint_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).val() + '" ,';
		}
		jsonData += ' "EVS_GRADE": "' + $("#evsGrade_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).find("option:selected").attr("name") + '" ,';
		jsonData += ' "AFFIRM_CONTENT": "' + $("#affirmContent_" + $(obj).attr("sysIndex"),navTab.getCurrentPanel()).val() + '" ';
		jsonData += '}';
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		alertMsg.info("No need to implement the data"); //没有需要实行的数据
		return;
	}
	alertMsg.confirm("Are you sure you want to implement it?", //确定要实行吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/evs/manage/modifyObjectActivityForAffirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: function(){
		  			alertMsg.info("Confirm success"); //确定成功
		  			$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
		  		},
  				error: DWZ.ajaxError
  			});
  	}});
}

//评价考核页面 分配率计算
function affirmTargetFun(){
	$('td:[syslong="rat"]',navTab.getCurrentPanel()).each(function(i, obj){
		var cnt = 0;
		$("select[name='EVS_GRADE']",navTab.getCurrentPanel()).each(function(i, object){
			if($(obj).attr("sysGrade") == "empty"){
				if($(object).find("option:selected").text() == ''){
					cnt = cnt + 1;
				}
			}else{
				if($(object).find("option:selected").text() == $(obj).attr("sysGrade")){
					cnt = cnt + 1;
				}
			}
		});
		$(obj).html(cnt);

		if($(obj).attr("sysGrade") != "empty"){
			$(obj).parent().find('td:[syslong="cnt_' + $(obj).attr("sysGrade") + '"]').html(parseInt(cnt * 100/$("#viewAffirmTarget_cnt",navTab.getCurrentPanel()).html()));
		}

		//如果都评价完成  实行
		if($('td:[sysGrade="empty"]',navTab.getCurrentPanel()).html() == '0'){
			$("#viewAffirmTargetConfirm",navTab.getCurrentPanel()).show();
		}
	});
}



function addTime(time1,time2){
	var temp = 0;
	var time1Temp = strToFloat(time1);
	if(time1Temp + time2 >= 24){
		temp = time1Temp + time2 - 24;
	}else{
		temp = time1Temp + time2;
	}
	return floatToStr(temp);
}

function addTime2(time1,time2){
	var temp = 0;
	var temp1Float = strToFloat(time1);
	var temp2Float = strToFloat(time2);
	if(temp1Float < temp2Float){
		temp = temp1Float + 24;
	}else{
		temp = temp1Float;
	}
	return temp - temp2Float;
}

function addTime3(time1,time2){
	var temp = 0;
	var temp1Float = strToFloat(time1);
	if(temp1Float < time2){
		temp = temp1Float + 24;
	}else{
		temp = temp1Float;
	}
	return temp - time2;
}

function strToFloat(val){
	var hh = val.substring(0,2);
	var mm = val.substring(3,5);
	var time = parseFloat(hh);
	if(mm != "00"){
		time = time + 0.5;
	}
	return time;
}

function floatToStr(val){
	var result = "";
	var hhResult = parseInt(val);
	var mmResult = val - parseInt(val);
	if(mmResult == 0){
		result = hhResult + "00";
	}else{
		result = hhResult + "30";
	}
	return composeTime(result);
}

function composeTime(val){
	var temp = val.replace(/\D/g,'');
	if(temp.length == 1){
		temp = '0' + temp + ':00';
	}else if(temp.length == 2){
		temp = temp + ':00';
	}else if(temp.length == 3){
		temp = '0' + temp.substring(0,1) + ":" + temp.substring(1,3);
	}else{
		temp = temp.substring(0,2) + ":" + temp.substring(2,4);
	}
	return temp;
}


/**
 * 2017-09-11  lipeng
 * 由于js文件中不能添加国际化,所以在此添加用参数传入提示信息
 * 上传附件窗口
 */
function uploadAttDialog_new(id,val,seq,applyType,first_save_flag,dialog_title){
	if(seq.length < 1){
		alertMsg.info(first_save_flag);//js.upload.msg.saveAndUpload 请先保存信息，再上传附件
		return false;
	}//js.upload.msg.fileToUpload  附件上传
	$.pdialog.open("/sys/notice/uploadWindow?id=" + id + "&val=" + val
			+ "&seq=" + seq + "&applyType=" + applyType, "uploadWindow", dialog_title, {width:550,height:320,mask:true});
}

/**
 * 2017-09-11  lipeng
 * 由于js文件中不能添加国际化,所以在此添加用参数传入提示信息
 * 删除附件
 */
function deleteAttList_new(id,val,callback,delete_selected_msg,delete_confirm_msg){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info(delete_selected_msg);//js.upload.msg.firstSelectDelete   请先选择要删除的附件
		return false;
	}

	alertMsg.confirm(delete_confirm_msg,//js.upload.msg.confirmToDelete 确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}


/**
 * 2017-09-11  lipeng
 * 由于js文件中不能添加国际化,所以在此添加用参数传入提示信息
 * 上传附件窗口(新增)
 * param dialog窗口名
 */
function uploadAttDialogInsert_new(dialog_title){
	//js.upload.msg.fileToUpload  附件上传
	$.pdialog.open("/sys/notice/uploadWindowInsert", "uploadWindow", dialog_title, {width:550,height:320,mask:true});
}

/**
 * 2017-09-11  lipeng
 * 由于js文件中不能添加国际化,所以在此添加用参数传入提示信息
 * 删除附件(新增)
 * param 删除错误提示
 */
function deleteAttListInsert_new(delete_selected_msg){
	var flag=false;
	$("input[name='FILE_NO']",navTab.getCurrentPanel()).each(function(){
		if($(this).attr("checked") == "checked"){
			$(this).parent().parent().remove();
			flag = true;
		}
	});
	if(flag == false){
		alertMsg.info(delete_selected_msg);//js.upload.msg.firstSelectDelete   请先选择要删除的附件
		return false;
	}
}