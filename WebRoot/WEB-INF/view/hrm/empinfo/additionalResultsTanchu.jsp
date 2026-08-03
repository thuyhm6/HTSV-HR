<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function changeUrlToNavTabNum(url,param,menu_code,menu_name){
		url = encodeURI(encodeURI(url)) ;
		navTabNum(url,'',menu_code,menu_name);
	}
function xuanzhong(codeno){
	var a=$('#ss_'+codeno).prop('checked');
	if(a==true){
		$('#ss_'+codeno).removeAttr('checked');
	}else{
		$('#ss_'+codeno).attr('checked','checked');
	}
	
}
function queding(){
	var arrayno= "";
	var arrayname="";
	 $("#bb input[name=result]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	arrayno += $(this).val() + ",";
	        	arrayname +=$(this).attr('valuename') +",";
	        } //获取被选中的值
	    });
	$('#arrayno').attr('value',arrayno);
	$('#arrayname').attr('value',arrayname);
	var titlename = $("#insertResults input[name='titlename']").val();
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select titlename title_name from hr_employee_search  where titlename = '" + titlename + "' and cpny_id = '${LoginUser.cpnyId}'"},
		dataType:"json",
		cache: false,
		success: function(data){
			if (data.result != '') {
				alertMsg.error("<spring:message code='hrm.empinfo.TITLE_NAME_NOT_DUPLICATE' />");//标题命名不能重复
				return false;
			}
		}
	});
	 validateAddResumeInfoCallback('insertResults',navTabAjaxDone);
	 
}
$('#CheckAll').click(function(){  
    $('input[name="result"]').prop("checked",this.checked);  
});

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				async:false,
  				success:function(data){
  					shuaxin();
  				},
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
function shuaxin(){
	var a="";
	$.ajax({
		type:'post',
		dataType:'json',
		url:'/hrm/empinfo/searchTitlename',
		data:{a:a},
		success:function(data){
			var searchTitlename=data.searchTitlename;
			if(searchTitlename!=""){
				var str='<select id="titleName" name="titleName" >';
				var s='';
				for(var i=0;i<searchTitlename.length;i++){
					var searchno=searchTitlename[i]['SEARCHNO'];
					var titlename=searchTitlename[i]['TITLENAME'];
					s=s+'<option value="'+searchno+'">'+titlename+'</option>';
				}
				str=str+s+'</select>';
				$('#titlenamelist').html(str);
				$.pdialog.closeCurrent();
			}
		}
	});
	
}
function chooseChild(id){
	var check=$('#'+id).prop('checked');
	if(check==true){
		$('#'+id).removeAttr('checked');
	}else{
		$('#'+id).attr('checked','checked');
	}
}
</script>

<div  class="pageContent" style="overflow-x: auto;overflow-y: auto">
            <form id="insertResults" method="post" action="/hrm/empinfo/insertResults" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input type="hidden" id="arrayno" name="arrayno" value="">
			<input type="hidden" id="arrayname" name="arrayname" value="">
			
			<table id="bb" class="user_table" width="100%" border="1" cellpadding="0" cellspacing="0" layoutH="50" targetType="dialog">
			<tr>
			<td class="td_type" colspan='3' style="text-align: center;"><!--标题命名:--><spring:message code="hrm.empinfo.TITLE_NAME"/>&nbsp&nbsp<input type="text" class="required" id="titlename" name="titlename"></td>
			</tr>
			
			<tr>
			<td width="5%" class="td_title" style="text-align: center">NO.</td>
			<td width="5%" class="td_title" style="text-align: center"><!-- 添加项目 --><spring:message code="hrm.empinfo.ADD_PROJECT" /></td>
			<td width="5%" class="td_title" style="text-align: center">
			<input type="checkbox" id="CheckAll">
				<!-- 选择 --><spring:message code="public.title.choose" />
			</td>
			</tr>
			<tr >
			<td width="5%" class="td_type" >1</td>
			<td width="5%" class="td_type" ><!-- 姓名 --><spring:message code="hrm.empinfo.name" /></td>
			<td width="5%" class="td_type" ><input type="checkbox" id="" checked="checked"  onclick="return false;"></td>
			<input type="hidden" id="" name="">
			</tr>
			
			<tr>
			<td width="5%" class="td_type" >2</td>
			<td width="5%" class="td_type" ><!-- 社号 --><spring:message code="hrm.empinfo.empid"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" id="" checked="checked"  onclick="return false;"></td>
			</tr>
			
			<tr onclick="chooseChild('depart')">
			<td width="5%" class="td_type" >3</td>
			<td width="5%" class="td_type" ><!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />    </td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" onclick="chooseChild(this.id)" id="depart" name="result" value="DEPTNO" valuename="<spring:message code='hrm.empinfo.ORG_NAME_LOCAL' />"></td>
			</tr>
			
			<tr onclick="chooseChild('bumenzhang')">
			<td width="5%" class="td_type" >4</td>
			<td width="5%" class="td_type" ><!-- 部门长 --><spring:message code="hrm.empinfo.HEAD_DEPARTMENT"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="bumenzhang" name="result" value="HEAD_DEPARTMENT" valuename="<spring:message code='hrm.empinfo.HEAD_DEPARTMENT'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhiqun')">
			<td width="5%" class="td_type" >5</td>
			<td width="5%" class="td_type" ><!-- 职群 --><spring:message code="hrm.empinfo.POST_FAMILY"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhiqun" name="result" value="POST_FAMILY" valuename="<spring:message code='hrm.empinfo.POST_FAMILY'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhijizhiji')">
			<td width="5%" class="td_type" >6</td>
			<td width="5%" class="td_type" ><!-- 职级 --><spring:message code="hrm.contract.Rank" /></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" onclick="chooseChild(this.id)" id="zhijizhiji" name="result" value="POST_GRADE_NO" valuename="<spring:message code='hrm.contract.Rank' />"></td>
			</tr>

			<tr onclick="chooseChild('zhiwu1')">
			<td width="5%" class="td_type" >7</td>
			<td width="5%" class="td_type" ><!-- 职责 --><spring:message code="hrm.contract.POSITION_NO"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhiwu1" name="result" value="POSITION_NO" valuename="<spring:message code='hrm.contract.POSITION_NO'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhiyuanleixing')">
			<td width="5%" class="td_type" >8</td>
			<td width="5%" class="td_type" ><!-- 员工类型 --><spring:message code="hrm.empinfo.EMP_TYPE_CODE_NAME"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhiyuanleixing" name="result" value="EMP_TYPE_CODE" valuename="<spring:message code='hrm.empinfo.EMP_TYPE_CODE_NAME'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhuyaoyewu1')">
			<td width="5%" class="td_type" >9</td>
			<td width="5%" class="td_type" ><!-- 主要业务 --><spring:message code="hrm.empinfo.MAIN_BUSINESS_NAME"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhuyaoyewu1" name="result" value="MAIN_BUSINESS" valuename="<spring:message code='hrm.empinfo.MAIN_BUSINESS_NAME'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('chengbenzhongxin')">
			<td width="5%" class="td_type" >10</td>
			<td width="5%" class="td_type" ><!-- 成本中心 --><spring:message code="hrm.empinfo.COST_CENTER_NAME_LOCAL"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="chengbenzhongxin" name="result" value="COST_CENTER" valuename="<spring:message code='hrm.empinfo.COST_CENTER_NAME_LOCAL'/>"></td>
			</tr>

			<tr onclick="chooseChild('rusheri')">
			<td width="5%" class="td_type" >11</td>
			<td width="5%" class="td_type" ><!-- 入职日期 --><spring:message code="hr.viewPersonalInfo.title.DATE_STARTED"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="rusheri" name="result" value="DATE_STARTED" valuename="<spring:message code='hr.viewPersonalInfo.title.DATE_STARTED'/>"></td>
			</tr>

			<tr onclick="chooseChild('shiyongqijieshuriqi')">
			<td width="5%" class="td_type" >12</td>
			<td width="5%" class="td_type" ><!-- 试用期结束日期 --><spring:message code="hrm.empinfo.END_PROBATION_DATE" /></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="shiyongqijieshuriqi" name="result" value="END_PROBATION_DATE" valuename="<spring:message code='hrm.empinfo.END_PROBATION_DATE' />"></td>
			</tr>
			
			<tr onclick="chooseChild('tuisheri')">
			<td width="5%" class="td_type" >13</td>
			<td width="5%" class="td_type" ><!-- 离职日期 --><spring:message code="hr.viewPersonalInfo.title.DATE_LEFT" /></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="tuisheri" name="result" value="DATE_LEFT" valuename="<spring:message code='hr.viewPersonalInfo.title.DATE_LEFT' />"></td>
			</tr>
			
			<tr onclick="chooseChild('renzhizhuangtai')">
			<td width="5%" class="td_type" >14</td>
			<td width="5%" class="td_type" ><!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="renzhizhuangtai" name="result" value="EMP_OFFICE" valuename="<spring:message code='ess.infoApply.renzhizhuangtai' />"></td>
			</tr>
			
			<tr onclick="chooseChild('shenfenzheng')">
			<td width="5%" class="td_type" >15</td>
			<td width="5%" class="td_type" ><!-- 身份证号 --><spring:message code="hrm.empinfo.IDCARD_NO"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="shenfenzheng" name="result" value="IDCARD_NO" valuename="<spring:message code='hrm.empinfo.IDCARD_NO'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('huozhengriqi')">
			<td width="5%" class="td_type" >16</td>
			<td width="5%" class="td_type" ><!-- 获证日期 --><spring:message code="hrm.empinfo.award_date"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="huozhengriqi" name="result" value="IDCARD_START_DATE" valuename="<spring:message code='hrm.empinfo.award_date'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('qianfajiguan')">
			<td width="5%" class="td_type" >17</td>
			<td width="5%" class="td_type" ><!-- 签发机关 --><spring:message code="hrm.empinfo.QIANFA_JIGUAN.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="qianfajiguan" name="result" value="ISSUING_AUTHORITY" valuename="<spring:message code='hrm.empinfo.QIANFA_JIGUAN.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('xingbie1')">
			<td width="5%" class="td_type" >18</td>
			<td width="5%" class="td_type" ><!-- 性别 --><spring:message code="hrm.empinfo.SEXCODE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="xingbie1" name="result" value="SEXCODE" valuename="<spring:message code='hrm.empinfo.SEXCODE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('chusheng')">
			<td width="5%" class="td_type" >19</td>
			<td width="5%" class="td_type" ><!-- 出生日期 --><spring:message code="hrm.empinfo.FAM_BORNDATE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="chusheng" name="result" value="DOB" valuename="<spring:message code='hrm.empinfo.FAM_BORNDATE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('nianling')">
			<td width="5%" class="td_type" >20</td>
			<td width="5%" class="td_type" ><!-- 年龄 --><spring:message code="hrm.empinfo.AGE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="nianling" name="result" value="AGE" valuename="<spring:message code='hrm.empinfo.AGE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('jiehunqufen')">
			<td width="5%" class="td_type" >21</td>
			<td width="5%" class="td_type" ><!-- 结婚区分 --><spring:message code="hrm.empinfo.MARRY_DIFFERENTIATE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="jiehunqufen" name="result" value="MARITAL_STATUS_CODE" valuename="<spring:message code='hrm.empinfo.MARRY_DIFFERENTIATE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('jiehun')">
			<td width="5%" class="td_type" >22</td>
			<td width="5%" class="td_type" ><!-- 结婚日期 --><spring:message code="hrm.empinfo.MARRY_DATE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="jiehun" name="result" value="WEDDING_DATE" valuename="<spring:message code='hrm.empinfo.MARRY_DATE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('guoji')">
			<td width="5%" class="td_type" >23</td>
			<td width="5%" class="td_type" ><!-- 国籍 --><spring:message code="ess.empInfo.nationality"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="guoji" name="result" value="NATIONALITY_CODE" valuename="<spring:message code='ess.empInfo.nationality'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('minzu')">
			<td width="5%" class="td_type" >24</td>
			<td width="5%" class="td_type" ><!-- 民族 --><spring:message code="hrm.empinfo.NATION_CODE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="minzu" name="result" value="NATION_CODE" valuename="<spring:message code='hrm.empinfo.NATION_CODE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('homephone')">
			<td width="5%" class="td_type" >25</td>
			<td width="5%" class="td_type" ><!-- 家庭电话 --><spring:message code="hr.viewHire.title.HOME_PHONE"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="homephone" name="result" value="HOME_PHONE" valuename="<spring:message code='hr.viewHire.title.HOME_PHONE'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('phone')">
			<td width="5%" class="td_type" >26</td>
			<td width="5%" class="td_type" ><!-- 手机号码 --><spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="phone" name="result" value="CELLPHONE" valuename="<spring:message code='liang.hr.viewPersonalInfo.title.PHONE_NUMBER'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('eaglemornot')">
			<td width="5%" class="td_type" >27</td>
			<td width="5%" class="td_type" ><!-- EagleM 与否 --><spring:message code="hrm.empinfo.EagLem.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="eaglemornot" name="result" value="EXIST_SINGLE" valuename="<spring:message code='hrm.empinfo.EagLem.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('eaglemid')">
			<td width="5%" class="td_type" >28</td>
			<td width="5%" class="td_type" ><!-- EagleM ID --><spring:message code="hrm.empinfo.EagLem_ID.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="eaglemid" name="result" value="SING_ID" valuename="<spring:message code='hrm.empinfo.EagLem_ID.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('personalemail')">
			<td width="5%" class="td_type" >29</td>
			<td width="5%" class="td_type" ><!-- 个人邮箱 --><spring:message code="hrm.empinfo.GEREN_EMAIL.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="personalemail" name="result" value="EMAIL_SECOND" valuename="<spring:message code='hrm.empinfo.GEREN_EMAIL.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('companyemail')">
			<td width="5%" class="td_type" >30</td>
			<td width="5%" class="td_type" ><!-- 公司邮箱 --><spring:message code="hrm.empinfo.GONGSI_EMAIL.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="companyemail" name="result" value="EMAIL" valuename="<spring:message code='hrm.empinfo.GONGSI_EMAIL.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('hukouaddress')">
			<td width="5%" class="td_type" >31</td>
			<td width="5%" class="td_type" ><!-- 户口所在地 --><spring:message code="ess.personalinfo.title.accountAddress"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="hukouaddress" name="result" value="REG_PLACE" valuename="<spring:message code='ess.personalinfo.title.accountAddress'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('chushengaddress')">
			<td width="5%" class="td_type" >32</td>
			<td width="5%" class="td_type" ><!-- 出生地 --><spring:message code="hrm.empinfo.ORIGIN.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="chushengaddress" name="result" value="ORIGIN" valuename="<spring:message code='hrm.empinfo.ORIGIN.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('cvupdatestatus')">
			<td width="5%" class="td_type" >33</td>
			<td width="5%" class="td_type" ><!-- CV update status --><spring:message code="hrm.empinfo.CV_update_status.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="cvupdatestatus" name="result" value="CV_UPDATE_STATUS" valuename="<spring:message code='hrm.empinfo.CV_update_status.Z'/>"></td>
			</tr>
			
			<c:if test="${LoginUser.cpnyId eq 'HAE'}">
			<tr onclick="chooseChild('danganaddress')">
			<td width="5%" class="td_type" >34</td>
			<td width="5%" class="td_type" ><!-- 档案所在地 --><spring:message code="hrm.empinfo.FILE_LOCATION.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="danganaddress" name="result" value="FILE_LOCATION" valuename="<spring:message code='hrm.empinfo.FILE_LOCATION.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('danganzhuanru')">
			<td width="5%" class="td_type" >35</td>
			<td width="5%" class="td_type" ><!-- 档案转入 --><spring:message code="hr.viewContract.title.FILE_INTO_YN_NAME"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="danganzhuanru" name="result" value="FILE_ENTER" valuename="<spring:message code='hr.viewContract.title.FILE_INTO_YN_NAME'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('danganzhuanchu')">
			<td width="5%" class="td_type" >36</td>
			<td width="5%" class="td_type" ><!-- 档案转出 --><spring:message code="hrm.empinfo.FILE_OUT.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="danganzhuanchu" name="result" value="FILE_OUT" valuename="<spring:message code='hrm.empinfo.FILE_OUT.Z'/>"></td>
			</tr>
			</c:if>
			
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<tr onclick="chooseChild('zhengzhimianmao')">
			<td width="5%" class="td_type" >34</td>
			<td width="5%" class="td_type" ><!-- 宗教 --><spring:message code="ess.empInfo.religion"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhengzhimianmao" name="result" value="RELIGION" valuename="<spring:message code='ess.empInfo.religion'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zongjiao')">
			<td width="5%" class="td_type" >35</td>
			<td width="5%" class="td_type" ><!-- 政治面貌 --><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zongjiao" name="result" value="POLITICAL_OUTLOOK" valuename="<spring:message code='hr.viewPersonalInfo.title.POLITY_NAME'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhuzhaiqufen')">
			<td width="5%" class="td_type" >36</td>
			<td width="5%" class="td_type" ><!-- 住宅区分 --><spring:message code="hrm.empinfo.ZHUZHAIQUFEN.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhuzhaiqufen" name="result" value="RESIDENTIAL_DISTINCTION" valuename="<spring:message code='hrm.empinfo.ZHUZHAIQUFEN.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('canjunyufou')">
			<td width="5%" class="td_type" >37</td>
			<td width="5%" class="td_type" ><!-- 参军与否 --><spring:message code="hrm.empinfo.ARMY_OR_NOT.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="canjunyufou" name="result" value="ARMY_OR_NOT" valuename="<spring:message code='hrm.empinfo.ARMY_OR_NOT.Z'/>"></td>
			</tr>
			
			<tr onclick="chooseChild('zhangaiyufou')">
			<td width="5%" class="td_type" >38</td>
			<td width="5%" class="td_type" ><!-- 障碍与否 --><spring:message code="hrm.empinfo.OBSTACLE_OR_NOT.Z"/></td>
			<td width="5%" class="td_type" ><input type="checkbox" onclick="chooseChild(this.id)" id="zhangaiyufou" name="result" value="OBSTACLE_OR_NOT" valuename="<spring:message code='hrm.empinfo.OBSTACLE_OR_NOT.Z'/>"></td>
			</tr>
			</c:if>
			
			</table>
			<input type="button" value="<spring:message code='button.sys.affirm.save' />" onclick="queding()" style="font-size: 15px;margin-top: 20px;"><!-- 保存 -->
	</form>
</div>
