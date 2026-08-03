<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="../inc/initTaglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>HVV HR System</title><!-- 【${LOGIN_CPNY}】 -->
<link href="/resources/css/print.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/core.css" rel="stylesheet" type="text/css" />

<link href="/resources/js/uploadify/css/uploadify.css" rel="stylesheet" type="text/css"/>
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/hub/style.css" rel="stylesheet" type="text/css" />
<link rel="icon" type="image/png" href="/resources/images/hanwha-favicon.ico">
<style>
      .addselect {
          border-radius: 2px;
          display: inline-block;
          background-color: #ccc;
          height: 12px;
          width: 12px;
          text-align: center;
          color: #fff;
          font-size: 9px;
          font-family: Arial;
          position: relative;
          margin-left: 4px;
          cursor: pointer;
          overflow: hidden;
          vertical-align: top;
          top: 1px;
      }
      .addselect select {
          width: 44px;
          opacity: 0;
          position: absolute;
          left: 0;
          top: 0;
          cursor: pointer;
      }
</style>

<!--[if IE]>
<link href="/resources/css/dwzUI/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="/resources/js/dwzUI/speedup.js" type="text/javascript"></script>
<![endif]-->

<!-- jquery -->
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/util/StringUtil.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.bgiframe.js" type="text/javascript"></script>
<script src="/resources/js/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<script src="/resources/js/xheditor/xheditor-1.1.9-en.min.js" type="text/javascript"></script>

<!-- ztree -->
<script src="/resources/js/ztree/jquery.ztree.all-3.1.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.core.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.util.date.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.validate.method.js" type="text/javascript"></script>

<script src="/resources/js/dwzUI/source/dwz.barDrag.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.drag.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.tree.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.accordion.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.ui.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.theme.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.switchEnv.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.alertMsg.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.contextmenu.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.navTab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.tab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.resize.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.dialog.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.cssTable.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.stable.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.taskBar.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.ajax.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.pagination.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.database.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.datepicker.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.effects.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.panel.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.checkbox.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.history.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.combox.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.print.js" type="text/javascript"></script>

<!--
<script src="/resources/js/dwzUI/dwz.min.js" type="text/javascript"></script>
-->

<script src="/resources/js/dwzUI/dwz.regional.zh.js" type="text/javascript"></script>

<!-- ligerUI -->
<script src="/resources/js/ligerUI/js/ligerui.min.js" type="text/javascript"></script> 
<script src="/resources/js/ligerUI/js/plugins/ligerComboBox.js" type="text/javascript"></script>

<script src="/resources/js/meizzDate.js" type="text/javascript"></script>
<script src="/resources/js/togglebar.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.jqprint-0.3.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.treeTable.js" type="text/javascript"></script>
<script src="/resources/js/jquery/sortable_table.js" type="text/javascript"></script>
<script src="/resources/js/jquery/fixed_table_rc.js" type="text/javascript"></script>
<script src="/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="/resources/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.jeditable.js" type="text/javascript"></script>


<link href="/resources/dataTables/css/dataTables.jqueryui.min.css" rel="stylesheet" type="text/css" />
<link href="/resources/dataTables/css/buttons.dataTables.min.css" rel="stylesheet" type="text/css" />
<link href="/resources/dataTables/css/scroller.dataTables.min.css" rel="stylesheet" type="text/css" />
<link href="/resources/dataTables/css/fixedColumns.dataTables.min.css" rel="stylesheet" type="text/css" />
<script src="/resources/dataTables/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="/resources/dataTables/js/dataTables.buttons.min.js" type="text/javascript"></script>
<script src="/resources/dataTables/js/buttons.flash.min.js" type="text/javascript"></script>
<script src="/resources/dataTables/js/dataTables.scroller.min.js" type="text/javascript"></script>
<script src="/resources/dataTables/js/dataTables.fixedColumns.min.js" type="text/javascript"></script>
<script src="/resources/dataTables/js/dataTables.order.js" type="text/javascript"></script>
<script src="/resources/js/highcharts/highcharts.js" type="text/javascript"></script>
<script type="text/javascript">
$.fn.dataTable.Buttons.swfPath ="/resources/dataTables/swf/flashExport.swf";

$(function(){
	DWZ.init("/resources/dwz.frag.xml", {
		loginUrl:"login_dialog.html", loginTitle:"<spring:message code='hrm.login.LOGIN.Z' />",//登录	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"/resources/css/dwzUI/themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
var openMenuCode = '';
var arSubMenuCode = '' ;
var paSubMenuCode = '' ;
var hrSubMenuCode = '' ;
var orgSubMenuCode = '' ;
var sySubMenuCode = '' ;
var rptSubMenuCode = '' ;
var eduSubMenuCode = '' ;
var evsSubMenuCode = '' ;
function menuControl(menu_code,sub_menu_code,url,param,menuName){
	
	if(openMenuCode == ''){
		document.getElementById(menu_code+"_menu").style.display = "block";
		openMenuCode = menu_code ;
	}else if(openMenuCode != menu_code){
		document.getElementById(openMenuCode+"_menu").style.display = "none";
		document.getElementById(menu_code+"_menu").style.display = "block";
		openMenuCode = menu_code ;
	}
	
	if(menu_code.indexOf("ar") != -1){
		arSubMenuCode = sub_menu_code ;
		showSubMenu(arSubMenuCode) ;
	}else if(menu_code.indexOf("hr") != -1){
		hrSubMenuCode = sub_menu_code ;
		showSubMenu(hrSubMenuCode) ;
	}else if(menu_code.indexOf("pa") != -1){
		paSubMenuCode = sub_menu_code ;
		showSubMenu(paSubMenuCode) ;
	}else if(menu_code.indexOf("org") != -1){
		orgSubMenuCode = sub_menu_code ;
		showSubMenu(orgSubMenuCode) ;
	}else if(menu_code.indexOf("sy") != -1){
		sySubMenuCode = sub_menu_code ;
		showSubMenu(sySubMenuCode) ;
	}else if(menu_code.indexOf("rpt") != -1){
		rptSubMenuCode = sub_menu_code ;
		showSubMenu(rptSubMenuCode) ;
	}else if(menu_code.indexOf("edu") != -1){
		eduSubMenuCode = sub_menu_code ;
		showSubMenu(eduSubMenuCode) ;
	}else if(menu_code.indexOf("evs") != -1){
		evsSubMenuCode = sub_menu_code ;
		showSubMenu(evsSubMenuCode) ;
	}
	
	navTabNum(url,param,menu_code,menuName);
	
	/* $.each($("#navMenu ul li"), function(i,val){  
		$(val).removeClass();    
		$(val).addClass("noselected");
	}); 

	$("#nav_"+menu_code).removeClass("noselected");
	$("#nav_"+menu_code).addClass("selected");
	
	$.each($("div.t1"), function(i,val){      
		val.style.display = "none";
	}); 
	
	var viewMenu = document.getElementById("view_"+menu_code);
	viewMenu.style.display = "block";
	$("#maincoll h2").text(menu_name);
//	$("#maincoll div").
	$("#maincoll .toggleCollapse div").trigger("click"); */
	
	
}

function showSubMenu(openSubMenuCode){
	$(document).bind("click",function(e){ 
		var temp = openSubMenuCode.split('+') ;
		
		for(var i=0;i<temp.length;i++){
		    var target  = $(e.target); 
		   if(target.closest("#"+temp[i]+"_button").length == 0){ 
		        $("#"+temp[i]+"_info #"+temp[i]+"_ul").slideUp();
		    } 
		    else 
		    { 
		        $("#"+temp[i]+"_info #"+temp[i]+"_ul").slideDown();	 
		    }
		}
	});
}

function alertMsgControl(alertID){
	var idLength=alertID.length;
	$.each($("#main_top_nav_id ul li"), function(i,val){  
		$(val).removeClass();    
		$(val).addClass("main_top_nav_off");
		document.getElementById(alertID.substring(0,idLength-1)+(i+1)+"_content").style.display = "none";
	});
	document.getElementById(alertID+"_content").style.display = "";
	$("#"+alertID).removeClass("main_top_nav_off");
	$("#"+alertID).addClass("main_top_nav_on");
}
/*
function for HR
*/


var hrTheTypeJosn = { "hr0210": "transferOrder",
					  "hr0202": "upGrade",
					  "hr0211": "transferPromote",
					  "hr0204": "plurality",
					  "hr0203": "suspend",
					  "hr0209": "hortation",
					  "hr0208": "punishMent",
					  "hr0206": "resign",
					  "hr0214": "payrise",
					  "hr0216": "agent"
					  
} ;

var hrSaveUrlJosn = { "hr0210": "/hrm/transferOrder/viewTransferNormal",
					  "hr0202": "/hrm/transferOrder/viewUpgrade",
					  "hr0211": "/hrm/transferOrder/viewTransferPromote",
					  "hr0204": "/hrm/transferOrder/viewPlurality",
					  "hr0203": "/hrm/transferOrder/viewSuspend",
					  "hr0209": "/hrm/transferOrder/viewHortation",
					  "hr0208": "/hrm/transferOrder/viewPunishMent",
					  "hr0206": "/hrm/transferOrder/viewResign",
					  "hr0214": "/hrm/transferOrder/viewPayrise",
					  "hr0216": "/hrm/transferOrder/viewAgent"
					  
} ; 



function showSearch(navTabId){
	var theType = hrTheTypeJosn[navTabId] ;
	document.getElementById("searchEmp").href = "/hrm/transferOrder/viewEmpSearchList?pageNum=1&navTabId=" +navTabId + "&theType=" + theType;
	document.getElementById("searchEmp").click();
}


function validateCallbackEmpSearch(navTabId) {	
	
	var eids = $("#searchForm").find("input[name='eidsForSearch']").val(); 
	//var url = "/hrm/transferOrder/viewTransferNormal?eids="+eids+"&&pageNum=1&&navTabId=" + navTabId;
	var url = hrSaveUrlJosn[navTabId]+ "?eids=" + eids + "&&pageNum=1&&navTabId=" + navTabId;
	dialogAjaxDone({
			"statusCode":"200", 
			"forwardUrl":url, 
			"navTabId": navTabId
    });
	return false ;
}


var hrUrlJosn = { "hr0101": "/hrm/empinfo/viewPersonalInfo","hr0102": "/hrm/empinfo/viewPromote","hr0103": "/hrm/empinfo/viewEvaluate",
				  "hr0104": "/hrm/empinfo/viewReward","hr0105": "/hrm/empinfo/viewTranslate","hr0106": "/hrm/empinfo/viewTraining",
				  "hr0107": "/hrm/empinfo/viewRelation","hr0108": "/hrm/empinfo/viewHealth","hr0109": "/hrm/empinfo/viewWorkInfo",
				  "hr0110": "/hrm/empinfo/viewAdditional","hr0112": "/hrm/empinfo/viewAccount","hr0111": "/hrm/empinfo/viewCompetence",
				  "hr0114": "/hrm/empinfo/viewContract","hr0115": "/hrm/empinfo/viewGoAbroad","hr0116": "/hrm/empinfo/viewCredential" } ;

function F_HR_ShowMore(obj, navTabId){
		var turn_to_url = hrUrlJosn[navTabId] ;

		if(turn_to_url.length > 0){
			dialogAjaxDone(
	   	    	{
	   	    		"statusCode":"200", 
					"forwardUrl":turn_to_url + "?navTabId=" + navTabId + "&PERSON_ID=" + obj, 
					"navTabId":navTabId
	   	    	}
	   	    ) ;
	   }
	}

function F_HR_SubmitKeyClick(empid,localName,idcardNo,navTabId){
	
   	if(event.keyCode==13){
   		
		var turn_to_url = hrUrlJosn[navTabId] ;
   		$.ajax({
			type: 'POST',
			url: encodeURI('/hrm/empinfo/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo+'&EMP_OFFICE=15119'),//15119设置默认查找在职员工
			dataType:"json",
			cache: false,
			success: function(jsonObject){
					
						if (jsonObject.perCnt==0){
							alert("<spring:message code='alert.message.noBody'/>");
						}
						
						if(jsonObject.perCnt>1 ){
							//15119设置默认查找在职员工
							document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&navTabId=" + navTabId + "&seach_EMPID="+empid+'&seach_LOCAL_NAME='+localName+'&seach_IDCARD_NO='+idcardNo+'&seach_EMP_OFFICE=15119' ));
						
							document.getElementById("onck").click();
							
						}
						
						if(jsonObject.perCnt==1){
							
							 navTabAjaxDone(
				    	    	{
				    	    		"statusCode":"200", 
									"forwardUrl":turn_to_url + "?navTabId=" + navTabId + "&PERSON_ID=" + jsonObject.personId, 
									"callbackType":"forward"
				    	    	}
				    	    ) ;
						}
					},
			error: DWZ.ajaxError
		});
    }
 }
 
 
function seachByDept(navTabId){
	document.getElementById("onck").href = "/hrm/empinfo/viewEmpIdList?pageNum=1&navTabId="+navTabId;
	document.getElementById("onck").click();
}
 
//function changeLanguage(flag){
//	if(flag == 'zh'){
//		$.ajax({
//	 		type:'GET',
//	 		url:"/login/changeLanguage?language="+flag,
//	 		cache: false,
//	 		success: location.reload()
//	 		 
//	 	});
//	}else{
//		alert("暂不支持国际化！");
//		return;
//	}
//}

function changeLanguage(flag){
    var temp="";
    if(flag=='zh'){
    	temp='zh_CN';
    }else if(flag=='en'){
        temp='en_US'; 
    }else if(flag=='vi'){
        temp='vi_VN'; 
    }else{ 
    	temp='ko_KR'; 
    }
 $.cookie('language_cookie',flag ,{expires:365,path:'/'});  
 $.cookie('locale_cookie',temp ,{expires:365,path:'/'});  

 $("#tabsPageHeaderContent li").each(function (i) {
 	if("selected"==$(this).attr("class").replace(/^\s+|\s+$/g, "")){
 		//alert($(this).attr("url"));
 		var url=decodeURIComponent($(this).attr("url"));
 		$.cookie('openUrl',url);
 	}
 }); 
  $.ajax({
		type:'GET',
		url:"/login/changeLanguage?language="+flag+"&locale="+temp,
		cache: false,
		async:false,
		success: location.reload()
	});

}

 var i  = 1;//定义一个全局变量 ，控制下拉收回。
function showUL(liID){
		if( i==0 )
		{
		$("#quick_menu .ulss").slideUp();	
		$("#showUL"+liID).slideDown("slow");
			i=1;
		}else{
			$("#quick_menu .ulss").slideUp();	
		i=0;
  	  }
}
$(document).bind("click",function(e){ 
    var target  = $(e.target); 
    if(target.closest("#manual_button").length == 0){ 
        $("#manual_info #manual").slideUp();
    } 
    else 
    { 
        $("#manual_info #manual").slideDown();	 
    } 
    //语言选择下拉框
    if(target.closest("#Language_button").length == 0){ 
        $("#Language_info #Language_ul").slideUp();
    } 
    else 
    { 
        $("#Language_info #Language_ul").slideDown();	 
    } 
	//待裁决下拉框
    if(target.closest("#tab4_viewInfo").length == 0){ 
        $("#quick_menu .ulss").slideUp();
    } 
    else 
    { 
        $("#quick_menu .ulss").slideDown();	 
    } 
    var menuCodeStr = target.closest("li").attr("tabid");
    //alert(menuCodeStr);
    if(menuCodeStr != null){
    	if(menuCodeStr.indexOf("ar") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("ar0000_menu").style.display = "block";
    			openMenuCode = "ar0000" ;
    			showSubMenu(arSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("pa") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("pa0000_menu").style.display = "block";
    			openMenuCode = "pa0000" ;
    			showSubMenu(paSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("sy") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("sy0000_menu").style.display = "block";
    			openMenuCode = "sy0000" ;
    			showSubMenu(sySubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("hr") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("hr0000_menu").style.display = "block";
    			openMenuCode = "hr0000" ;
    			showSubMenu(hrSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("org") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("org0000_menu").style.display = "block";
    			openMenuCode = "org0000" ;
    			showSubMenu(orgSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("rpt") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("rpt0000_menu").style.display = "block";
    			openMenuCode = "rpt0000" ;
    			showSubMenu(rptSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("edu") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("edu0000_menu").style.display = "block";
    			openMenuCode = "edu0000" ;
    			showSubMenu(eduSubMenuCode) ;
    		}
    	}else if(menuCodeStr.indexOf("evs") != -1){
    		if(openMenuCode != ''){
    			document.getElementById(openMenuCode+"_menu").style.display = "none";
    			document.getElementById("evs0000_menu").style.display = "block";
    			openMenuCode = "evs0000" ;
    			showSubMenu(evsSubMenuCode) ;
    		}
    	}else if(menuCodeStr == 'main'){
   			document.getElementById(openMenuCode+"_menu").style.display = "none";
   			document.getElementById("_menu").style.display = "block";
    	}
    }
    	
});


$(window).load(function () {
	  
var quick_width = $("#quick_menu").width();
var quick_height = $("#quick_menu").height();
var quick_right = 10 - $("#quick_menu").width();
$("#quick_menu").css({right:0});
<c:if test="${tipsMap.total_count_viewInfo le 0}">
$("#quick_menu").show();
$("#quick_menu ul li").hide();
$("#quick_menu ul li ul").hide();
</c:if>
<c:if test="${tipsMap.total_count_viewInfo gt 0}">
$("#quick_menu").show();
$("#quick_menu ul li").show();
$("#quick_menu ul li ul").show();
</c:if>
/* $("#quick_menu").show();
$("#quick_menu ul li").show();
$("#quick_menu ul li ul").show(); */
	$("#quick_menu ul span").toggle(
      function () {
       getTips();
       $("#quick_menu ul li").fadeIn();
       $("#quick_menu").animate({
    			right: 0
 			}, 500, function() {
 				$("#quick_menu ul span").removeClass();
   				$("#quick_menu ul span").addClass("hide");				 			 
  			});
      },
      function () {
        //初始化提示信息
     
      	$("#quick_menu ul li ul").slideUp();
 		$("#quick_menu ul li").fadeOut();
        $("#quick_menu").animate({
    			right: 0
 			}, 500, function() { 				
 				$("#quick_menu ul span").removeClass();
   				$("#quick_menu ul span").addClass("show");
  			});
      }
    );
	
    if($("#qMenuHideFlag").val() == "show"){
    	// show 인 경우(결재건수가 한건이라도 있는 경우)는 퀵메뉴를 클릭해준다.
    	setTimeout(function(){
    	      $("#quick_menu ul span").trigger("click");
    	 },1000);
    }
    $("#showUL4").blur(function(){
    	$("#showUL4").slideUp();
  	});
});


<c:if test="${LoginUser.cpnyId eq 'LGE'}">
$(function(){
	setTimeout(function(){
		$.pdialog.open("/login/manualWindow", "manualWindow", "<spring:message code='hrm.login.XITONGGONGGAO.Z' />", {width:710,height:620,mask:true});//系统公告
		},"2000");
});
</c:if>
function getTips(){
	$.ajaxSettings.global=false;
	$.ajax({
    	type:'get',
    	cache:false,
    	contentType:'application/json',	            			            	
    	url:'/myhome/getTips_home',            	
    	dataType:'json',
    	success:function(data){
    		getTipsAfter(data);
    		if(data.total_count_viewInfo>0){
    			$("#quick_menu").show();
    			$("#quick_menu ul li").show();
    			$("#quick_menu ul li ul").show();
    		}
    	}        	           	
	});
	$.ajaxSettings.global=true;
}

function getTipsAfter(data){
        $("#tab4_viewInfo a b").html(data.total_count_viewInfo);
        if(typeof (data['sy0501']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_1 a b").html(data.sy0501);
        }
		if(typeof (data['ar0901']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_2 a b").html(data.ar0901);
        }
		if(typeof (data['ar0903']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_3 a b").html(data.ar0903);
        }
		if(typeof (data['ar0909']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_4 a b").html(data.ar0909);
        }
		/*if(typeof (data['ar0906']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_5 a b").html(data.ar0906);
        }
		if(typeof (data['ess3444']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_6 a b").html(data.ess3444);
        }
		if(typeof (data['ar0907']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_7 a b").html(data.ar0907);
        }
		if(typeof (data['ar0909']) != "undefined"){
            $("#tab4 #showUL4 #tab4_viewInfo_8 a b").html(data.ar0909);
        }*/
}

$(function(){
    //refresh approval count! //300초(1000 * 5 * 60) =>5분 
    //setInterval("getTips()",1000 * 1 * 60);
});

var iapp=0;
function checkApprovalCount(){
    if(iapp==0){
        /* alert("here & qMenuHideFlag:::" + parent.document.getElementById("qMenuHideFlag").value); */
        var data = getTips();
        //alert("data:::"+data);
        parent.document.getTipsAfter(data);
        iapp=1;
    }
}

function goTab(menuCode,menuNo,menuUrl){
	
}

function openNavTab(url){
	
	if($("#esstransform").val()!="0000"){
		$("#maincoll h2").text($("#esstransform").val());
		}
	var a=url.indexOf("&");
	var b=url.lastIndexOf("&");
	var title_name=url.substring(a+1,b);
	var title="";
	var menu_code="";
	var menu_name="";
	$.ajax({
    	type:"post",
    	cache:false,     			            	
    	url:'/login/titleName',            	
    	data:title_name,
    	dataType:"json",
    	async:false,
    	success:function(data){
    	  if(data != null){
			title=data.TATLENAME,
			menu_code=data.MENU_CODE,
			menu_name=data.MENU_NAME
    	  }
		}
    	});
	navTab.init();
	navTab.openTab("open_Id",url,{title:title,frech:true,date:{}});
	$.cookie('openUrl',null);
	if(typeof(menu_code)!="undefined" && typeof(menu_name)!="undefined" && menu_code!="" && menu_name!=""){
	$("#maincoll h2").text(menu_name);
	menuControl(menu_code,menu_name);}
}
$(document).ready(function(){
	//navTab.openTab(tabid,url)
		if($.cookie('openUrl')!=null){
			openNavTab($.cookie('openUrl'));
		}
	
	});

	//navTab 最多可以打开10个
function navTabNum(url,param,menuCode,name){
	var num=0;
		$(".navTab-tab li[tabid]").each(function(){
			if($(this).attr("tabid")!=menuCode){
				num++;
			}
		});
		
		//alert(url);
	if(num>=12){
		//alert("最多只能打开10个选项卡");
		//alertMsg.info("最多只能打开10个选项卡");
		alertMsg.info("<spring:message code='liang.alert.message.maximumtTenTab'/>");
		return false;
	}else{
		if(typeof(url) != "undefined"){
			if(url.indexOf(',')!=-1){
				var targets = url.split(',');
				if(targets[1]=='_blank'){
					window.open(targets[0]);
					return;
				}
			}else
				$("#home_open").attr("target",'navTab');
			if(url.indexOf('?')!=-1)
				url+='&'+param;
			else
				url+='?'+param;
			$("#home_open").attr("href",url);
			$("#home_open").attr("rel",menuCode);
			$("#home_open").text(name);
			$("#home_open").click();
		}
	}
}


function openOnRight(url,relId){
	$("#right_open").attr("href",url);
	$("#right_open").attr("rel",relId);
	$("#right_open").click();
}

function changeCpny(id){
	  
	 $.ajax({
			type:'GET',
			url:'/login/changeCpny?cpny_id='+id,
			cache: false,
			async:false,
			success: location.reload()
		});
}
function logoutConfirm(url){
	if(confirm("<spring:message code='hrm.login.SURE_LOGOUT.Z' />")){//确定要退出吗？
		location.href=url;
	}
}
</script>
</head>

<body scroll="no">
<div style="display:none;">
<a id="home_open" target="navTab" style="visibility:hidden"></a>
<a id="right_open" target="ajax" rel="" style="visibility:hidden"></a>
</div>
	<div id="layout">
		<div id="header">
			<div class="headerNav" >
				<!-- <div class="backpic"><img src="/resources/images/login/nav_bg.jpg" alt="logo" /></div> -->
				
				<ul class="nav">
					<%-- <c:set value="0000" var="MENU_NAME" />
					<c:forEach var="menuInfo" items="${menuList}" varStatus="i">
						<c:if test="${menuInfo.MENU_CODE == 'ess0000'}">
							<li id="nav_${menuInfo.MENU_CODE}" class="selected">
								<a href="#" onclick="menuControl('${menuInfo.MENU_CODE}','${menuInfo.MENU_NAME}');">
									<span>
										${menuInfo.MENU_NAME}
									</span>
								</a>
							</li>
							<c:set value="${menuInfo.MENU_NAME}" var="MENU_NAME" />
						</c:if>
						<c:if test="${menuInfo.MENU_CODE ne 'ess0000'}">
							<li id="nav_${menuInfo.MENU_CODE}" class="noselected">
								<a href="#" onclick="menuControl('${menuInfo.MENU_CODE}','${menuInfo.MENU_NAME}');">
									<span>
										${menuInfo.MENU_NAME}
									</span>
								</a>
							</li>
						</c:if>
					</c:forEach> --%>
					<!-- 初始化权限内所有菜单 -->
					<div id="${menuInfo.MENU_CODE}_menu" style="display:block;float:right;">
					<li id="manual_info" class="personnel_info">
						<a style="text-decoration:none ;" id="manual_button"><!-- 用户手册下载 --><spring:message code="sys.mainHub.YONGHUSHOUCEXIAZAI.b" /></a>
						<ul id="manual" style="background-image: url(/resources/css/dwzUI/themes/hub/images/top/info.png);background-repeat: no-repeat;background-position: left bottom;width: 120px;">
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-HR.pptx&file=${LoginUser.cpnyId }-Module-HR.pptx"><font color="#090909"><!-- 人事模块 --><spring:message code="sys.mainHub.RENSHIMOKUAI.b" /></font></a>
                            </li>
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-Org.pptx&file=${LoginUser.cpnyId }-Module-Org.pptx"><font color="#090909"><!-- 组织模块 --><spring:message code="sys.mainHub.ZUZHIMOKUAI.b" /></font></a>
                            </li>
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-Edu.pptx&file=${LoginUser.cpnyId }-Module-Edu.pptx"><font color="#090909"><!-- 培训模块 --><spring:message code="sys.home.PEIXUNMOKUAI.b" /></font></a>
                            </li>
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-Evs.pptx&file=${LoginUser.cpnyId }-Module-Evs.pptx"><font color="#090909"><!-- 评价模块 --><spring:message code="sys.home.PINGJIAMOKUAI.b" /></font></a>
                            </li>
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-Att.pptx&file=${LoginUser.cpnyId }-Module-Att.pptx"><font color="#090909"><!-- 考勤模块 --><spring:message code="sys.mainHub.KAOQINMOKUAI.b" /></font></a>
                            </li>
                            <li style="width: 140px;">
                                <a style="text-decoration:none ;" href="/ess/infoApplyLeave/downloadFile?fileName=/resources/manual/${LoginUser.cpnyId }/${LoginUser.cpnyId }-Module-Pay.pptx&file=${LoginUser.cpnyId }-Module-Pay.pptx"><font color="#090909"><!-- 工资模块 --><spring:message code="sys.mainHub.GONGZIMOKUAI.b" /></font></a>
                            </li>
                         </ul>
					</li>
					<c:if test="${admin.username eq 'admin'}">
					<select id="COMPANY_ID_TWO" name="COMPANY_ID_TWO" onchange="changeCpny(this.value)">
						<c:forEach items="${companyList}" var="item" varStatus="i">
							<option value="${item.CPNY_ID }" <c:if test="${admin.cpnyId eq item.CPNY_ID}">selected="selected"</c:if>>${item.CPNY_ID }</option>
						</c:forEach>
					</select>
					</c:if>
					<%-- <li id="logout"><a style="text-decoration:none;" target="view_window" href="/ess/change/in?passwords=${LoginUser.password }&usernames=${LoginUser.username}&COMPANY_IDS=${LoginUser.cpnyId}&PEROSN_ID_ID=${LoginUser.personId}" >ESS<!-- 系统 --><spring:message code="sys.mainHub.XITONG.b" /></a></li> --%>
					<li id="logout"><a style="text-decoration:none;" target="view_window" href="/login/home_partner.do" >ESS<!-- 系统 --><%-- <spring:message code="sys.mainHub.XITONG.b" /> --%></a></li>
					<li id="logout"><a style="text-decoration:none;" onclick="logoutConfirm('/login/out');" href="#" >Logout</a></li>
					<li id="Language_info" class="personnel_info">
						<a style="text-decoration:none ;" id="Language_button">Language</a>
						<ul id="Language_ul" style="background-image: url(/resources/css/dwzUI/themes/partner/images/top/info_MyHome.png);background-repeat: no-repeat;background-position: left bottom;">
							<li><a style="text-decoration:none ;" href="#" onclick="changeLanguage('zh');" style=""><font color="#090909">Chinese<!-- 中文 <spring:message code="pa.bonus.title.chinese" />--></font></a></li>
							<%--<li><a href="#" onclick="changeLanguage('en');" style=""><font color="#090909"><!-- English --><spring:message code="hrm.login.ENGLISH.Z" /></font></a></li>--%>
							<%--<li><a style="text-decoration:none ;" href="#" onclick="changeLanguage('ko');" style=""><font color="#090909">한국어</font></a></li>--%>
							<li><a style="text-decoration:none ;" href="#" onclick="changeLanguage('vi');" style=""><font color="#090909">Vietnamese<!-- 越南语 <spring:message code="hrm.login.YUENANYU.Z" />--></font></a></li>
						
						</ul>
					</li>
					</div>
					<c:forEach var="menuInfo" items="${leftList}" varStatus="i">
						<div id="${menuInfo.MENU_CODE}_menu" style="display: none;float:right;">
							<c:if test="${menuInfo.childMenuList ne null}">
								<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
									<li id="${menuInfo2.MENU_CODE}_info" class="personnel_info">
										<a style="text-decoration:none ;" id="${menuInfo2.MENU_CODE}_button">${menuInfo2.MENU_NAME}</a>
										<ul id="${menuInfo2.MENU_CODE}_ul" style="background-image: url(/resources/css/dwzUI/themes/hub/images/top/info.png);width: 170px;">
				                            
				                            <c:forEach items="${menuInfo2.childMenuList}" var="menuInfo3">
												  <li style="width: 170px;">
<!-- 														  	      <a href="${menuInfo3.MENU_URL}?pageNum=1&menuNo=${menuInfo3.MENU_NO}&navTabId=${menuInfo3.MENU_CODE}"  target="navTab" rel="${menuInfo3.MENU_CODE}">${menuInfo3.MENU_NAME}@@</a>  -->
												  	      <a style="text-decoration:none ;width: 170px;" onclick="navTabNum('${menuInfo3.MENU_URL}','pageNum=1&menuNo=${menuInfo3.MENU_NO}&navTabId=${menuInfo3.MENU_CODE}','${menuInfo3.MENU_CODE}','${menuInfo3.MENU_NAME}');" ><font color="#090909">${menuInfo3.MENU_NAME}</font></a>
												  </li>
											</c:forEach>
			                         	</ul>
									</li>
								</c:forEach>
							</c:if>
						</div>
					</c:forEach>
				</ul>
			</div>
		</div>
			<input type="hidden" id="esstransform" value="${MENU_NAME}"/>

		<%-- <div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div id="maincoll" class="toggleCollapse"><h2>ESS系统</h2><div></div></div>

				<div class="accordion" fillSpace="sidebar">
				
					<c:forEach var="menuInfo" items="${leftList}" varStatus="i"> 
						
						<div id="view_${menuInfo.MENU_CODE}" class="t1"
							<c:if test="${menuInfo.MENU_CODE eq 'ess0000'}">style="display:block;"</c:if>
							<c:if test="${menuInfo.MENU_CODE ne 'ess0000'}">style="display:none;"</c:if>
						>
						    <c:if test="${menuInfo.childMenuList ne null}">
						    	<div class="accordionContent">
									<ul class="tree treeFolder">
										
										<c:forEach items="${menuInfo.childMenuList}" var="menuInfo2">
											<li><a href="javascript:;">${menuInfo2.MENU_NAME}</a>
												 <c:if test="${menuInfo2.childMenuList ne null}">
												    <ul>
														<c:forEach items="${menuInfo2.childMenuList}" var="menuInfo3">
														  <li>
														  	 <c:if test="${menuInfo3.childMenuList ne null}">
														  	     <a href="javascript:;">${menuInfo3.MENU_NAME}</a>
															  	 <ul id="aaaaaaa">	  		
																	 <c:forEach items="${menuInfo3.childMenuList}" var="menuInfo4">
																	     <li>
																		 <a  onclick="navTabNum('${menuInfo4.MENU_URL}','pageNum=1&menuNo=${menuInfo4.MENU_NO}&navTabId=${menuInfo4.MENU_CODE}','${menuInfo4.MENU_CODE}','${menuInfo4.MENU_NAME}');">${menuInfo4.MENU_NAME}</a>
																	     
																	     </li>
																	 </c:forEach>
																 </ul>
															  </c:if>
														      <c:if test="${menuInfo3.childMenuList eq null}">
<!-- 														  	      <a href="${menuInfo3.MENU_URL}?pageNum=1&menuNo=${menuInfo3.MENU_NO}&navTabId=${menuInfo3.MENU_CODE}"  target="navTab" rel="${menuInfo3.MENU_CODE}">${menuInfo3.MENU_NAME}@@</a>  -->
														  	      <a onclick="navTabNum('${menuInfo3.MENU_URL}','pageNum=1&menuNo=${menuInfo3.MENU_NO}&navTabId=${menuInfo3.MENU_CODE}','${menuInfo3.MENU_CODE}','${menuInfo3.MENU_NAME}');" >${menuInfo3.MENU_NAME}</a>
															  </c:if>
														  </li>
														</c:forEach>
													</ul>	
												</c:if>
											 </li>
										</c:forEach>
									</ul>
								</div>
						    </c:if>
					    </div>
					</c:forEach>
					
					
					
					
				</div>
			</div>
		</div> --%>
		
		<div id="container" style="top:20px;left:0px;">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent" id="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" url="/login/home2" class="main"><a href="javascript:;"><span><span class="home_icon"><spring:message code="main.home.message.myPage"/></span></span></a></li>
							<div id="open_id_div" style="display:none;"><li tabid="open_Id" url="/login/home2" class="" ><a href="javascript:;"><span class="open_id_namne"><!-- 标签名称 --><spring:message code="hrm.login.BAIOQIAN_NAME.Z" /></span></a></li></div>
						</ul>
					</div>
				</div>
				<%-- <div style="height: 90%">
					<jsp:include page="home2.jsp" flush="true"/>
				</div> --%>
				<ul class="tabsMoreList">
					<li><a href="javascript:;"><spring:message code="main.home.message.myPage"/></a></li>
				</ul>
				 <div id="quick_menu" class="quick_menu">
						<ul>
                            <li style="padding-right:80px">
                                <div id="tab4">
                                    <div id="tab4_viewInfo"><a href="#" style="margin-top:2px;"><!-- 待审批信息  --><spring:message code="ess.infoApply.daishenpixinxi" />(<b>${tipsMap.total_count_viewInfo}</b>)</a></div>                  
                                    <ul id="showUL4" class="ulss" style="width:230px;background-repeat:repeat;">
                                    	<c:if test="${!empty tipsMap.sy0501}">
                                    		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isHrUser == 1}">
                                                <li id="tab4_viewInfo_1">
                                                    <a href="/hrm/approve/viewEssApplyInfo?pageNum=1&menuNo=14014333&navTabId=sy0501" target="navTab" rel="sy0501" title='<spring:message code="hrm.empinfo.emp_Information_change_management"/>'>Change Request(<b>${tipsMap.sy0501}</b>)</a>
                                                </li>
                                        	</c:if>
                                        </c:if>
                                    	<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
	                                        <c:if test="${!empty tipsMap.ar0901}">
	                                                <li id="tab4_viewInfo_2">
	                                                    <a href="/ess/arConfirm/viewAttendanceExConfirm?seach_CONFIRM_FLAG=0&pageNum=1&menuNo=14015692&navTabId=ar0901&firstFlag=N" target="navTab" rel="ar0901" title='<spring:message code="ar.viewArNavigationPage.KAOQINYICHANGRENSHIQUEREN.b" />'><!-- 考勤异常人事确认 --><spring:message code="ar.viewArNavigationPage.KAOQINYICHANGRENSHIQUEREN.b" />(<b>${tipsMap.ar0901}</b>)</a><!-- 待确认考勤异常 -->
	                                                </li>
	                                        </c:if>
	                                    	<c:if test="${!empty tipsMap.ar0903}">
	                                                <li id="tab4_viewInfo_3">
	                                                    <a href="/ess/arConfirm/viewLeaveConfirmList?seach_CONFIRM_FLAG=0&pageNum=1&menuNo=14015695&navTabId=ar0903" target="navTab" rel="ar0903" title='<spring:message code="ar.viewArNavigationPage.XIUJIARENSHIQUEREN.b" />'><!-- 休假人事确认 --><spring:message code="ar.viewArNavigationPage.XIUJIARENSHIQUEREN.b" />(<b>${tipsMap.ar0903}</b>)</a><!-- 待决裁休假 -->
	                                                </li>
	                                        </c:if>
	                                        <c:if test="${!empty tipsMap.ar0909}">
	                                                <li id="tab4_viewInfo_4">
	                                                    <a href="/ess/arConfirm/viewSickLeaveProofConfirmList?seach_PROOF_YN=0&pageNum=1&menuNo=90000352&navTabId=ar0909" target="navTab" rel="ar0909" title='<spring:message code="sys.main.sickLeaveProofConfirm.b" />'><spring:message code="sys.main.sickLeaveProofConfirm.b" /><!--病假证明提交确认-->(<b>${tipsMap.ar0909}</b>)</a><!-- 待提交病假证明 -->
	                                                </li>
	                                        </c:if>
                                        </c:if>
                                    	<%--<c:if test="${!empty tipsMap.ar0902}">
                                                <li id="tab4_viewInfo_3">
                                                    <a href="/ess/arConfirm/viewPOtApplyInfoConfirmList?seach_CONFIRM_FLAG=0&pageNum=1&menuNo=14015693&navTabId=ar0902" target="navTab" rel="ar0902" title='<spring:message code="ar.viewArNavigationPage.JIABANRENSHIQUEREN.b" />'><!-- 加班人事确认 --><spring:message code="ar.viewArNavigationPage.JIABANRENSHIQUEREN.b" />(<b>${tipsMap.ar0902 }</b>)</a>
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.ar0904}">
                                                <li id="tab4_viewInfo_4">
                                                    <a href="/ess/tempEmp/viewTempEmpConfirmList?seach_ACTIVITY=0&pageNum=1&menuNo=14015696&navTabId=ar0904" target="navTab" rel="ar0904" title='<spring:message code="hrm.login.XIAOSHIG_HR_SURE.Z" />'><!-- 小时工入离职人事确认 --><spring:message code="hrm.login.XIAOSHIG_HR_SURE.Z" />(<b>${tipsMap.ar0904 }</b>)</a>
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.ar0906}">
                                                <li id="tab4_viewInfo_5">
                                                    <a href="/ess/tempEmp/viewEmpLeftConfirmList?seach_ACTIVITY=0&pageNum=1&menuNo=14016232&navTabId=ar0906" target="navTab" rel="ar0906" title='<spring:message code="hrm.login.ZHENGSHIG_HR_SURE.Z" />'><!-- 正式工离职人事确认 --><spring:message code="hrm.login.ZHENGSHIG_HR_SURE.Z" />(<b>${tipsMap.ar0906 }</b>)</a>
                                                </li>
                                        </c:if>
                                        <c:if test="${!empty tipsMap.ess3444}">
	                                                <li id="tab4_viewInfo_6">
	                                                    <a href="/ess/tempEmp/viewChangeShopConfirmList?defaultRoleGroupName=Coordinator&pageNum=1&menuNo=14016228&navTabId=ess3444" target="navTab" rel="ess3444" title='<spring:message code="hrm.login.EMPLOYEE_DIAODIAN_SURE.Z" />'><!-- 员工调店确认 --><spring:message code="hrm.login.EMPLOYEE_DIAODIAN_SURE.Z" />(<b>${tipsMap.ess3444}</b>)</a><!-- 待决裁休假 -->
	                                                </li>
	                                     </c:if>
	                                     <c:if test="${LoginUser.cpnyId eq 'SPC_SH'}">
	                                     <c:if test="${!empty tipsMap.ar0907}">
	                                                <li id="tab4_viewInfo_7">
	                                                    <a href="/ess/tempEmp/viewChangeDeptConfirmList?seach_ACTIVITY=0&pageNum=1&menuNo=80000212&navTabId=ar0907&CONFIRM_FLAG=0" target="navTab" rel="ar0907" title='<spring:message code="hrm.login.EMPLOYEE_DIAODIAN_DUTY_SURE.Z" />'><!-- 员工调店岗位变更人事确认 --><spring:message code="hrm.login.EMPLOYEE_DIAODIAN_DUTY_SURE.Z" />(<b>${tipsMap.ar0907}</b>)</a><!-- 待决裁休假 -->
	                                                </li>
	                                     </c:if>
	                                     </c:if>
                                    	 <c:if test="${!empty tipsMap.ess2017}">
                                                <li id="tab4_viewInfo_6">
                                                    <a href="/ess/editionAffirm/viewEditionAffirmList?pageNum=1&menuNo=218379&navTabId=ess2017" target="navTab" rel="ess2017" title='离职审批'>离职审批(<b>${tipsMap.ess2017}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.hr0306}">
                                                <li id="tab4_viewInfo_7">
                                                    <a href="/hrm/contractInfo/viewExpiredContractApproveList?pageNum=1&menuNo=215968&navTabId=hr0306" target="navTab" rel="hr0306" title='合同审批'>合同审批(<b>${tipsMap.hr0306}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.ess0244}">
                                                <li id="tab4_viewInfo_8">
                                                    <a href="/ess/wageApplication/viewWageApplicationList?pageNum=1&menuNo=218222&navTabId=ess0244" target="navTab" rel="ess0244" title='费用审批'>费用审批(<b>${tipsMap.ess0244}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.pa0901}">
                                                <li id="tab4_viewInfo_9">
                                                    <a href="/pa/tempsale/viewTempSaleAffirmList?pageNum=1&menuNo=218170&navTabId=pa0901" target="navTab" rel="pa0901" title='临促工资审批'>临促工资审批(<b>${tipsMap.pa0901}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.pa0903}">
                                                <li id="tab4_viewInfo_10">
                                                    <a href="/pa/tempsale/viewTempSaleAccrualAffirmList?pageNum=1&menuNo=218582&navTabId=pa0903" target="navTab" rel="pa0903" title='临促预提审批'>临促预提审批(<b>${tipsMap.pa0903}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.pa0907}">
                                                <li id="tab4_viewInfo_11">
                                                    <a href="/pa/tempsale/viewTempSaleConfirmList?pageNum=1&menuNo=277600&navTabId=pa0907" target="navTab" rel="pa0907" title='临促汇总确认'>临促汇总确认(<b>${tipsMap.pa0907}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if>
                                    	<c:if test="${!empty tipsMap.pa0706}">
                                                <li id="tab4_viewInfo_13">
                                                    <a href="/pa/salary/viewPaForLeftMenAffirmList?pageNum=1&menuNo=225&navTabId=pa0706" target="navTab" rel="pa0706" title='离职员工薪资补发审批'>离职员工薪资补发审批(<b>${tipsMap.pa0706}</b>)</a><!-- 待决裁调休假 -->
                                                </li>
                                        </c:if> --%>
                                    </ul> 
                                </div>
                            </li>
							<span class="show"></span>
						</ul>
				</div>
				 <jsp:include page="main_hub.jsp" flush="true" />
				 <%-- <div class="tabsPageHeader">
					<div class="tabsPageHeaderContent" id="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" url="/login/home2" class="main"><a href="javascript:;"><span><span class="home_icon"><spring:message code="main.home.message.myPage"/></span></span></a></li>
							<div id="open_id_div" style="display:none;"><li tabid="open_Id" url="/login/home2" class="" ><a href="javascript:;"><span class="open_id_namne">标签名称</span></a></li></div>
						</ul>
					</div>
				</div> --%>
              </div>
		</div>
</div>
	    <input type="hidden" id="qMenuHideFlag" name="qMenuHideFlag" value="${qMenuHideFlag}" />
        <iframe src="" border="0" width="0" height="0"></iframe>
</body>
</html>