<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
function exportExcel(){
	var post = document.getElementById('postName');
	var oldAction = post.action;
	post.action="/pa/salaryCanShu/viewHaoFengSetListExcel";
	post.submit();
	post.action = oldAction;
}

function submitFormViewPaHaoFengList(){
  	var $from = $("#postName",navTab.getCurrentPanel());
  	$from.submit();
}

$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": true,    //分页
    "bAutoWidth":false,//表格宽度自动变化
    "bProcessing":true,
	"bLengthChange": false,  //按多少条记录显示下拉框
	"iDisplayLength": 10, //默认每页显示的记录数
	"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
 	"searching": true,//本地搜索
	"bSort": true,   //排序功能
	"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
	"bScrollInfinite":true,
     "orderClasses": false,
     "order":[],//初始化不用自动排序
     "scrollY": $(document.body).height() - 300,
     "scrollX": true,
     "scrollCollapse": false,
     "deferRender":true,
     //"scroller":true,
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
    },
    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
    "buttons": [
          ] 
});
$(document).ready(function(){
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ar0232();
	});
	initEditFun_ar0232();
	function initEditFun_ar0232(){
		$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
			onblur:function(val,settings){
				var input = $(this).parent().find("input[type=checkbox]");//获取checkbox
		           
		        input.attr("checked",true);
		           
		        $(this).html(val);
				this.editing = false;
			}
		});
	}
	initHaoFeng();
	function initHaoFeng(){ 
		var a="";
		var POST_GRADE_NO=$("#seach_ZHIJI").val();
		$.ajax({
			type : 'post',
			dateType : 'json',
			url : '/pa/salaryCanShu/viewHaoFeng?POST_GRADE_NO='+POST_GRADE_NO,
			data:{a:a},
			success : function(data){
				var list = data.viewHaoFeng;
				if(list!=""){
					var s='';
					for(var i=0;i<list.length;i++){
						var PAY_STEP=list[i]['PAY_STEP'];
						var PAY_STEP_NAME=list[i]['PAY_STEP_NAME'];
						if('${PAY_STEP}' == PAY_STEP){
							s=s+'<option value="'+PAY_STEP+'" selected>'+PAY_STEP_NAME+'</option>';
		   				}else{
		   					s=s+'<option value="'+PAY_STEP+'">'+PAY_STEP_NAME+'</option>';
			   			}
						
					}
					$('#seach_HAOFENG').html(s);
				}
			}
		 });
	}
});
</script>

<script type="text/javascript">

	$(function(){
		
       $(".delete").click(function(){
    	   var checked=false;
    	   var index="";
    	   var s=0;
    	   $("input[name='hfListCheck']",navTab.getCurrentPanel()).each(function(i, obj){
    		   if(obj.checked){
    			   s=(s+1);
    			   checked=true;
    			   index += obj.value+",";
        		   };
    		});
    	    if(!checked){
        	    //请选择要操作的信息！
	   			alertMsg.info("<spring:message code='pa.viewHaoFengSetList.QINGXUANZECAOZUOXINXI.b' />");
	   			return false;
	   	    };
         
	   	    index=index.substring(0,index.length-1);
	   	    if(s!=1){
		   	    //确定要禁用吗？
	   	    	alertMsg.confirm("<spring:message code='pa.viewHaoFengSetList.QUEDINGYAOJINYONGMA.b' />", {
			 		okCall : function() {
			 			$.ajax( {
			 				type : 'POST',
			 				url : "/pa/salaryCanShu/deleteHaoFengSheZhiInfo?PQD_NOS="+index,
			 				dataType : "json",
			 				cache : false,
			 				success : doAjaxDoneWithForm,
			 				error : DWZ.ajaxError
			 			});
			 		  }
			    });
	   	    }else{
		   	    //确定要禁用吗？
	   	    	alertMsg.confirm("<spring:message code='pa.viewHaoFengSetList.QUEDINGYAOJINYONGMA.b' />", {
			 		okCall : function() {
			 			$.ajax( {
			 				type : 'POST',
			 				url : "/pa/salaryCanShu/deleteHaoFengSheZhiInfo?PQD_NO="+index,
			 				dataType : "json",
			 				cache : false,
			 				success : doAjaxDoneWithForm,
			 				error : DWZ.ajaxError
			 			});
			 		  }
			    });

		   	}
	   	    return false;
	   	   
       });    


     //保存
   	$(".edit",navTab.getCurrentPanel()).click(function(){	
   		//获取页面的值
   		var check = 0;
   		var jsonData = '[';
   		$("input[name='hfListCheck']",navTab.getCurrentPanel()).each(function(i, obj){
   			if(obj.checked){
   				if (jsonData.length > 1) {
   					jsonData += ',{';
   					
   				} else {
   					jsonData += '{';
   				}
   				var index = i;
                var startMonth = $("#START_MONYH" + index,navTab.getCurrentPanel()).html();
                var startMonthFormat = startMonth.substr(2,4)+startMonth.substr(0,2);
                if (!/^(?:0[1-9]|1[0-2])(?:19[7-9]\d|2\d{3,3})$/.test(startMonth)){ 
                	check = 1;
               	    alertMsg.warn('<spring:message code="alert.message.pa.salary.startMonthIsNotCorrect"/>');
               	    return false;
                }
                var endMonth = $("#END_MONTH" + index,navTab.getCurrentPanel()).html();
                var endMonthFormat = endMonth.substr(2,4)+endMonth.substr(0,2);
                if(endMonth!=null && endMonth !=""){
                	if (!/^(?:0[1-9]|1[0-2])(?:19[7-9]\d|2\d{3,3})$/.test(endMonth)){
                		check = 2;
                		alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
                		return false;
                	}
                	//如果结束月份比开始月早， 请重新填写结束月！
                	if(endMonthFormat < startMonthFormat){
                		check = 3;
                		alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
                		return false;
                	}
                }
   				
   				jsonData += ' "START_MONTH": "' + $("#START_MONYH" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "END_MONTH": "' + $("#END_MONTH" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "BASE_PAY": "' + $("#BASE_PAY" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "BASE_ALLOWANCE": "' + $("#BASE_ALLOWANCE" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "GRADE_ALLOWANCE": "' + $("#GRADE_ALLOWANCE" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "OPERATING_ALLOWANCE": "' + $("#OPERATING_ALLOWANCE" + index,navTab.getCurrentPanel()).html() + '" ,';
   				jsonData += ' "SEQ": "' + $(this).val() + '" ,';
   				jsonData += ' "UPDATED_BY": "' + '${LoginUser.adminID}' + '" ,';
   				jsonData += ' "UPDATED_IP": "' + '${LoginUser.adminIP}' + '" ,';
   				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
   				jsonData += '}';
   			}
   		});
   		jsonData += ']';
   		if(check != 0){
   			return;
   	   	}
   		if (jsonData.length == 2) {
   	   		//没有需要保存的数据
   			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA' />");
   			return;
   		}
        //确定要保存吗？
   		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",
   	  		  	{okCall:function(){
   			  	$.ajax({
   	  				type: 'POST',
   					url: '/pa/salaryCanShu/updateHaoFengSheZhiInfo',
   					data: [{ name: 'jsonData', value: jsonData }],
   	  				dataType:"json",
   	  				cache: false,
   	  				success: navTabAjaxDoneWithForm,
   	  				error: DWZ.ajaxError
   	  			});
   	  	}});
   	});

   		$("#seach_ZHIJI").change(function(){ 
   			var a="";
   			var POST_GRADE_NO=$("#seach_ZHIJI").val();
   			$.ajax({
   				type : 'post',
   				dateType : 'json',
   				url : '/pa/salaryCanShu/viewHaoFeng?POST_GRADE_NO='+POST_GRADE_NO,
   				data:{a:a},
   				success : function(data){
   				var list = data.viewHaoFeng;
   				if(list!=""){
   					var s='';
   					for(var i=0;i<list.length;i++){
   						var PAY_STEP=list[i]['PAY_STEP'];
   						var PAY_STEP_NAME=list[i]['PAY_STEP_NAME'];
   						s=s+'<option value="'+PAY_STEP+'">'+PAY_STEP_NAME+'</option>';
   					}
   					$('#seach_HAOFENG').html(s);
   				}
   				}
   			 });
   		});
});

</script>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewHaoFengSetList"  method="post" id="postName" name="postName">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td>
			    		<!--职级 --><spring:message code="pa.insurance.title.postGrade" />
			    	</td>
			    	<td >
						<ait:SelectSyCodeByCpnyID name="seach_ZHIJI" id="seach_ZHIJI" parentNo="14015815" selected="${POST_GRADE_NO}" limit="ALL"/>	    			
					</td>
			    	<td>
			    		<!--年资等级 --><spring:message code="hrm.empinfo.PAY_STEP_NO.Z" />
			    	</td>
			    	<td >
						<select name="seach_HAOFENG" id="seach_HAOFENG">
								<option value="${PAY_STEP }">${PAY_STEP_NAME[0].CONTENT}</option>
						</select> 	    			
					</td>		
	    			<td>
	    				 <spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->
	    			</td>
	    			<td >
				    	<select name="seach_ACTIVITY" id="seach_ACTIVITY">
				    	<option value=""><!--请选择 --><spring:message code="org.title.PLEASE_SELECT" /></option>
				    				<option value="1" <c:if test="${ACTIVITY eq '1'||ACTIVITY == null }"> selected</c:if>><!--启用 --><spring:message code="sys.arAffirmPost.title.able" /></option>
				    				<option value="0" <c:if test="${ACTIVITY eq '0' }"> selected</c:if>><!--未启用 --><spring:message code="sys.arAffirmPost.title.enable" /></option>
							 
						</select>	    
	    			</td> 
	    			
		    	</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="searchSubmit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="480" var="add_height"/>
	<c:set value="/pa/salaryCanShu/addHaoFengGuanLi" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/pa/salaryCanShu/deleteHaoFengSheZhiInfo?PQD_NO={PQD_NO}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="460" var="edit_height"/>
	<c:set value="/pa/salaryCanShu/updateHaoFengSheZhiView?PQD_NO={PQD_NO}" var="edit_Url"/>
	<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id ="exportExcel"
		 onClick="exportExcel();">
							<SPAN><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></SPAN>
		    </a>
		</li>
			
		<li id="addLi">
				 
					<a class="add" href="${add_Url}" style='text-decoration:none;'
						target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
						mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
						width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
						height="${add_height eq '' || add_height eq null ? '400' : add_height}"
							rel="${add_rel}">
							<span>
								<spring:message code="button.add" />
								   ${add_name}
							</span>
					</a>
		</li>
			 
		<li id="deleteLi">
					<a class="delete" href="#" style='text-decoration:none;'>
						<!--target="${delete_tab eq '' || delete_tab eq null ? 'ajaxTodo' : delete_tab}"
						mask="${delete_mask eq '' || delete_mask eq null ? 'true' : delete_mask }" 
						width="${delete_width eq '' || delete_width eq null ? '500' : delete_width}" 
						height="${delete_height eq '' || delete_height eq null ? '400' : delete_height}"
						title="${delete_title}"-->
						<span>
							<!--禁用 --><spring:message code="pa.viewHaoFengSetList.JINYONG.b" />
							${delete_name}
						</span>
					</a>
		</li>
			
		<li id="editLi">
					<a class="edit" href="#" style='text-decoration:none;'>
						<!--target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }" 
						width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}" 
						height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
						rel="${edit_rel}"-->
						
						<span>
							<!--保存 --><spring:message code="ess.message.save" />
							${edit_name}
						</span>
					</a>
		</li>
			
	</ul>
</div>
  
					
	<table width="99%" class="orderList">
		<thead>
		    <tr>
			    <th><input type="checkbox" class="checkboxCtrl" group="hfListCheck" /></th>
				<th><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th><!--法人 --><spring:message code="sys.essParam.title.legalPerson" /></th>
				<th style="display:none"><!--职群 --><spring:message code="ess.empInfo.zhiqun" /></th>
				<th><!--职级 --><spring:message code="ess.infoApply.Rank" /></th>
				<th><!--年资等级 --><spring:message code="hrm.recruitManage.NIANZI_DENGJI.Z" /></th>
				<th class="titleColor"><spring:message code="hrm.empinfo.START_YEAR_MONTH"/><!--开始月份--></th>
				<th class="titleColor"><spring:message code="hrm.empinfo.END_YEAR_MONTH"/><!--结束月份--></th>
				<th class="titleColor"><spring:message code="pa.salary.canShu.jibengongzi"/><!--基本工资--></th>
				<th style="display:none"><!--基本津贴 --><spring:message code="pa.viewHaoFengSetList.JIBENJINTIE.b" /></th>
				<th style="display:none"><!--级别津贴 --><spring:message code="pa.viewHaoFengSetList.JIBIEJINTIE.b" /></th>
				<th style="display:none"><!--营业津贴 --><spring:message code="pa.viewHaoFengSetList.YINGYEJINTIE.b" /></th>
				<th><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${haoFengInfo}" var="item" varStatus="i">
				<tr target="PQD_NO" rel="${item.SEQ}">
				    <td class="td_center" style="text-align: center; padding-top: 7px;">
				         <input type="checkbox" id="hfListCheck_${i.index}" name="hfListCheck" value="${item.SEQ}" />
				    </td>
					<td ><center> ${i.index + 1}</center></td>
					<td ><center>${item.CPNY_ID}</center></td>
					<td style="display:none"><center>${item.POST_FAMILY}</center></td>
					<td ><center>${item.POST_GRADE_NO}</center></td>
					<td ><center>${item.PAY_STEP}</center></td>
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;" id="START_MONYH${i.index}">${item.START_MONTH}</td>
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;" id="END_MONTH${i.index}">${item.END_MONTH}</td>
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;" id="BASE_PAY${i.index}">${item.BASE_PAY}</td>
					
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;display:none" id="BASE_ALLOWANCE${i.index}">${item.BASE_ALLOWANCE}</td>
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;display:none" id="GRADE_ALLOWANCE${i.index}">${item.GRADE_ALLOWANCE}</td>
					<td sysLog="text" sysIndex="${i.index}" style="text-align:center;display:none" id="OPERATING_ALLOWANCE${i.index}">${item.OPERATING_ALLOWANCE}</td>
					<td style="text-align:center;" id="ACTIVITY${i.index}">
						
						   <c:if test="${item.ACTIVITY eq '1' }"> <img src="/resources/images/a_1.gif"></c:if> 
						   <c:if test="${item.ACTIVITY eq '0' }"> <img src="/resources/images/a_0.gif"></c:if> 
						
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>