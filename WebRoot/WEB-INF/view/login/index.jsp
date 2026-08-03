<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../inc/initTaglibs.jsp"%>
<%@page import = "com.ait.web.util.GetPassword" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
<title>HVV HR System</title>
<script src="/resources/js/jquery/jquery.min.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.form.js" type="text/javascript"></script>

<link href="/resources/css/dwzUI/themes/index/bootstrap-3.3.4.css" rel="stylesheet" type="text/css" /><!-- BOOTSTRAP CSS -->
<link href="/resources/css/dwzUI/themes/index/bootstrap-reset.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/index/style.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/dwzUI/themes/index/public.css"/>
<link rel="stylesheet" type="text/css" href="/resources/css/dwzUI/themes/index/menu.css"/>
<link rel="icon" type="image/png" href="/resources/images/hanwha-favicon.ico">
<script type="text/javascript" src="/resources/js/index/menu.js"></script>
<script type="text/javascript" src="/resources/js/index/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/index/index.js"></script>
<script type="text/javascript">

	//var canSubmit=true;
	 var flag=1;
	 $(document).ready(function() {
		 
		 	/* var UA=navigator.userAgent;
		 	 if(UA.toString().indexOf('MSIE')==-1 && UA.toString().indexOf('rv:11')==-1){
		 		canSubmit=false;
		 		alert('该系统只支持IE浏览器，请使用IE浏览器进行访问，对此带来的不便，我们深表歉意！！');
		 		window.opener=null;  
			    window.open("","_self");  
			   window.close();
		 	} */
		 	/* if(location.port == '7001'){
		 		$("#COMPANY_ID option[value='TSTO']").attr("selected", true);
		 		$("#COMPANY_ID option[value='SST']").remove();
		 	}else if(location.port == '7011'){
		 		$("#COMPANY_ID option[value='SST']").attr("selected", true);
		 		$("#COMPANY_ID option[value='TSTO']").remove();
		 	} */
		 	
		 	var empnumber = '' ;
		 	var requestURL = '' ;
		 	var reqSysType = '' ;
		 	var reqCpny = '' ;
		 	var reqRole = '' ;
		 	var reqLanguage = '' ;
		 	
		 	//if(empnumber != 'null' && requestURL.indexOf( 'hanwha.eagleoffice.co')!=-1){
		 	if(empnumber != 'null' && ( requestURL.indexOf( '118.190.89.40')!=-1 || requestURL.indexOf( 'hanwha.eagleoffice.co')!=-1)){
		 		 
				document.getElementById('username').value = empnumber;
				document.getElementById('username').readOnly = true;
				document.getElementById('password').value = empnumber;
				document.getElementById('password').readOnly = true;
				$('#sysType').attr('value', reqSysType);
				document.getElementById('sysType').disabled = true;
				$('#language').attr('value', reqLanguage);
				document.getElementById('language').disabled = true;
				document.getElementById('COMPANY_ID').value = reqCpny;
				//$('#COMPANY_ID').attr('value', reqCpny);
				//document.getElementById('COMPANY_ID').disabled = true;
				document.getElementById("btn").style.display='none';
	 			var mess = check();
				if (flag == 1 && mess == null && reqSysType != 'null'&& reqCpny != 'null') {
					$("#Tip").text("Loading...");
					var options = {
						url: '/login/in?loginType=sso&reqSysType='+reqSysType+'&reqCpny='+reqCpny+'&checkHubByIP=N&reqLanguage='+reqLanguage,
						type: 'POST',
						success: function(responseText) {
							if (responseText == "1") {
								if(reqSysType == 'Hub'){
									location.href = "/login/home.do";
								}else{
									location.href = "/login/home_partner.do?reqRole="+reqRole;
								}
							} else{
								$("#Tip").text('登陆失败！');
								document.getElementById('username').readOnly = false;
								document.getElementById('password').readOnly = false;
								document.getElementById('sysType').disabled = false;
								document.getElementById('language').disabled = false;
								//document.getElementById('COMPANY_ID').disabled = false;
								document.getElementById("btn").style.display='block';
							}
						}
					};
					$('#loginForm').ajaxSubmit(options);
					return true;
				}else{
					$("#Tip").text('登陆失败！');
					document.getElementById('username').readOnly = false;
					document.getElementById('password').readOnly = false;
					document.getElementById('language').disabled = false;
					document.getElementById('sysType').disabled = false;
					//document.getElementById('COMPANY_ID').disabled = false;
					document.getElementById("btn").style.display='block';
					return false;
				}
		 	}
			  
		try{
      			var arrCookie = document.cookie.split(';');
      			
      			var userId = '';
      			var company = '';
      			var sysType = '';
      			var language = '';
      			var arrCookie2=null; 
   			if(arrCookie != null){
   				//alert(arrCookie.toString());
   				for(var j=0;j<arrCookie.length;j++){
   					arrCookie2 = arrCookie[j].split(",");
	   				for(var i=0;i<arrCookie2.length;i++){
	   					var arr=arrCookie2[i].split("="); 
	   					//alert(arr[0]);
	   					//alert(arr[1]);
	   					if(arr[0].indexOf("sysAdmin.account") != -1){
	   				             userId=arr[1];
	   				        }
	   	
	   	   			    if(arr[0].indexOf("sysAdmin.company") != -1){
	   	   					     company=arr[1];
	   				        } 
	   	
	   	   			    if(arr[0].indexOf("sysAdmin.sysType") != -1){
	   	   			    	     sysType=arr[1];
	   				        }
	  				        
	    	   			if(arr[0].indexOf("language_cookie") != -1){
	    	   			         language=arr[1];
	    				    }
	   				} 
   				}
   			}	
   			document.getElementById('language').value = language;
   			//document.getElementById('COMPANY_ID').value = company;
   			document.getElementById('username').value = userId;
   			document.getElementById('saveBox').checked = true;
   			document.getElementById('sysType').value = sysType==''?'Partner': sysType;
   			
			/*if(document.getElementById('sysType').value == 'Hub')
            	$('#login_type_text').html('HR'); 
			else
				$('#login_type_text').html('ESS');*/
			
			if(document.getElementById('sysType').value == 'Hub'){
				$('#ko').hide();
		    }else{
		    	$('#ko').show();
			}
			
      		}catch(e){
      			
      		} 
      		if(userId==''){
      			document.getElementById('username').focus();
      		}else{
      			document.getElementById('password').focus();
      		} 

	});
	
	$(document).ready(function(){
			$('#btn').click(function() {
						//var arrCookie = document.cookie;

						/* if(!canSubmit){
							alert('该系统只支持IE浏览器，请使用IE浏览器进行访问，对此带来的不便，我们深表歉意！！');
							return;
						} */
						var mess = check();
						var language = document
								.getElementById('language').value;
						if (flag == 1 && mess == null) {
							var locale_cookie = "";
							if (language == 'zh') {
								locale_cookie = "zh_CN";
							} else if (language == 'vi') {
								locale_cookie = "vi_VN";
							} else if (language == 'en') {
								locale_cookie = "en_US";
							} else {
								locale_cookie = "ko_KR";
							}
							$("#Tip").text("Loading...");
							var options = {
								url : '/login/in?locale='
										+ locale_cookie
										+ '&language='
										+ language
										+ '&checkHubByIP=N',
								type : 'POST',
								success : function(responseText) {
									if (responseText == "1") {
										//保存cookie
										var date = new Date();
										var checkb = document.getElementById('saveBox');
										if (checkb.checked) {
											date.setTime(date.getTime() + 365 * 24 * 3600 * 1000);
										} else {
											date.setTime(date.getTime() - 1000);
										}

										document.cookie = 'sysAdmin.account='
												+ escape(document.getElementById('username').value)
												//+ ',sysAdmin.company1='
												//+ escape(document.getElementById('COMPANY_ID').value)
												+ ',sysAdmin.sysType='
												+ escape(document.getElementById('sysType').value)
												+ ',language_cookie='
												+ escape(document.getElementById('language').value)
												+ ',locale_cookie='
												+ escape(locale_cookie)
												+ ';expires='
												+ date.toGMTString()
												+ ';path=/';

										if ($("#sysType").val() == 'Hub') {
											location.href = "/login/home.do";
										} else {
											location.href = "/login/home_partner.do";
										}

									} else {
										$('#Tip').html(responseText);
									}
								}
							};
							$('#loginForm').ajaxSubmit(options);
							return true;
						} else {
							$('#Tip').html(mess);
							return false;
						}
			});
	});

	function check() {
		var u = $("#username").val();
		var p = $("#password").val();

		var company = $("#COMPANY_ID").val();
		var sysType = $("#sysType").val();
		var language = $("#language").val();

		var msg = null;

		if (company == '') {
			msg = 'please choose company';
		} else if (sysType == '') {
			msg = 'please choose system';
		} else if (language == '') {
			msg = 'please choose language';
		} else {
			if (u.replace(/(^\s*)|(\s*$)/g, "").length == 0)
				if (p.replace(/(^\s*)|(\s*$)/g, "").length == 0)
					msg = 'ID and PASSWORD must input.';
				else
					msg = 'ID must input.';
			else if (p.replace(/(^\s*)|(\s*$)/g, "").length == 0)
				msg = 'PASSWORD must input.';
		}

		return msg;
	}

	$(document).ready(function() {
        /*ESS比Hub多一种语言限定*/
		$('#sysType').change(function(){
			if ($(this).val() == "Hub") {
				$('#ko').hide();
				if ($('#language').val() == 'ko') {
					$('#chooseLanguage').attr("selected", true);
				}
			} else {
				$('#ko').show();
			}
	    });
	});
</script>

<style type="text/css">
  .Sign_select_right{ float:right; width:200px; height:30px; line-height:30px; border:1px solid #dbd9da;box-shadow:3px 3px 3px #dddddd inset; padding-left:0px}
</style>

</head>
<%--<body>
<div class="header">
    <div class="logo"><img src="/resources/images/logo.jpg" alt=""/></div>
</div>
<div class="logo"><a href="#"><img src="/resources/images/logo.jpg"/></a></div>
<div class="top_bar"></div>
<div class="content">
    <div class="banner">

    </div>
</div>
<div class="footer">
    <div class="login_box">
        <form id="loginForm" onkeydown="if(event.keyCode==13) $('#btn').click();">
        <div class="l">User Login<!-- 用户登录<spring:message code="login.YONGHU_DENGLU.Z" /> --></div>
        <div class="m">
            <p>
                <input type="text" class="userid" name="username" id="username" value="" placeholder="USER ID" />
            </p>
            <p>
                <input type="password" class="password" name="password" id="password" value="" placeholder="PASSWORD" />
                <input type="hidden" id="locale" value="vi_VN" />
            </p>
            <p>
            	<span class="company">
	            	<select class="mySelect" id="COMPANY_ID" name="COMPANY_ID">
	            		<option value="">* Choose Company *</option>
	            		<option value="HTSV">* HTSV *</option>
	            		<option value="HAE">* HAE *</option>
	            		<!--<option value="SPC_DL">* SPC_DL *</option>
	            		<option value="SPC_SH">* SPC_SH *</option>
	            		<option value="SPC_HZ">* SPC_HZ *</option>
	            		<option value="SPC_NJ">* SPC_NJ *</option>-->
	            	</select>
	            	<select class="mySelect" id="language" name="language">
		            		<option id="chooseLanguage" value="">* Choose language *</option>
		            		<option id="zh" value="zh">Chinese</option> 
		            		<option id="ko" value="ko">Korean</option>
		            		<option id="vi" value="vi">Vietnamese</option>
		            </select>
            	</span>
            	<span class="remember"><input type="checkbox" id="saveBox" class="" /><!-- 记住账号 --> save ID</span>
            </p>
            <p>
                <font size=2 color=red><span class="msgTip" id="Tip">${msg}</span></font>
            </p>
        </div>
        <div class="sys_Type">
            <input type="hidden" name="sysType" id="sysType" value="Partner" />
            <span id="login_type_btn">&nbsp;</span>
            <span id="login_type_text">ESS</span>
            <div class="select">
                <div>
                    <p datatype="Partner">ESS</p>
                    <p datatype="Hub">HR</p>
                </div>
            </div>
        </div>
        <div class="login_btn">
            <div id="btn"></div>
        </div>
        </form>
    </div>
</div>
</body>--%>
<body style="background:#fff">
 <div class="Sign_top fix">
  <a href="#"><img src="/resources/images/Sign_logo_login.jpg"/></a>
 </div>
 <!--Sign-->
 <div class="Sign fix">
  <div class="Sign_cn fix">
    <div class="Sign_bg"><img src="/resources/images/Sign_bg.png"/></div>
    <div class="Sign_nr">
     <div class="Sign_bt fix"><img src="/resources/images/Sign_bt.png"/></div>
     <form class="Sign_form fix" id="loginForm" onkeydown="if(event.keyCode==13) $('#btn').click();" method="post">
      <div class="Sign_bl fix">
      <i><img src="/resources/images/Sign_tb1.png"/></i>
      <input type="text" class="Sign_text" name="username" id="username"/>
      </div>
      <div class="Sign_bl fix">
       <i><img src="/resources/images/Sign_tb2.png"/></i>
      <input  type="password" class="Sign_password" name="password" id="password"/>
      </div>
      <div class="Sign_bl1 fix">
       
      </div>
      <div class="Sign_bl1 fix">
            <select class="Sign_select" id="sysType" name="sysType">
                <option id="chooseSystem" value="">* Choose system *</option>
           		<option value="Hub"> Hub </option>
           		<option value="Partner"> ESS </option>
       		</select>
       <select class="Sign_select_right" id="language" name="language">
       			<option id="chooseLanguage" value="">* Choose language *</option>
           		<!-- <option id="zh" value="zh">Chinese</option>  -->
           		<option id="ko" value="ko">Korean</option>
           		<option id="vi" value="vi">Vietnamese</option>
       </select>      
      </div>
      <div class="Sign_bl1 fix" style="margin-top:10px">
        <!--<font size=2 color=red style="padding-top:10px"><span id="Tip">${msg}</span></font>-->
        <!--<select class="Sign_select" id="COMPANY_ID" name="COMPANY_ID">
       			<option value="">* Choose Company *</option>
           		<option value="HTSV">* HTSV *</option>
           		<option value="HAE">* HAE *</option>
       	</select>-->
       	<!--不需要选择法人,隐藏域-->
       	<input type="hidden" id="COMPANY_ID" name="COMPANY_ID" value="HTSV"/>
       	<!--<input type="hidden" id="COMPANY_ID" value="HAE"/>-->
       	<div class="Sign_xz" >
       		<input type="checkbox" id="saveBox" class="Sign_checkbox" />
        	<a href="#">Save ID</a>
       	</div>
      </div>  
      <div class="Sign_bl1 fix" style="height:10px;margin-top:10px">
        <font size=2 color=red style=""><span id="Tip">${msg}</span></font>
      </div>
      <div class="Sign_tj fix">
      <input type="button" class="Sign_submit" value="LOGIN" id="btn"/>
      </div>
     </form>
    </div>
  </div>
 </div>
</body>
</html>
