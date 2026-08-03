<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

$('#teaAllCheck').click(function(){  
    $('input[name="teaCheck"]').prop("checked",this.checked);  
});
function baocun1(){
	var seq= "";
	var AR_DATE_STR="";
	var QUANTITY="";
	var applyno = $('#applyno').val();
	 $("#adjusttable input[name=ajCheck]").each(function(){ //遍历table里的全部checkbox
	        if($(this).attr("checked")){ //如果被选中
	        	    seq += $(this).val() + ",";
	        	    AR_DATE_STR +=$(this).attr('valuename') +",";
	        	    QUANTITY +=$(this).attr('valuenameaj') +",";
	        	
	        } //获取被选中的值
	    });
		 seq=seq.substring(0,seq.length-1);
		 AR_DATE_STR=AR_DATE_STR.substring(0,AR_DATE_STR.length-1);
		 QUANTITY=QUANTITY.substring(0,QUANTITY.length-1);
		 var seqArr = seq.split(',');
		 var dateArr = seq.split(',');
		 if(seqArr.length>1){
		     alert("请选择一条加班记录，不能选择多条");
		     return;
		 }else{
	         $('#reason'+applyno).attr('value','与'+AR_DATE_STR+'倒休');
	         $('#reason'+applyno).attr('readonly','readonly');
	         $('#ajSeq'+applyno).attr('value',seq);
	         $('#ajQUANTITY'+applyno).attr('value',QUANTITY);
	         $.pdialog.closeCurrent();
		 }
}
function quxiao1(){
	 $.pdialog.closeCurrent();
}
function childCheck1(count){
	
	var ischeck=$('#ajCheck_'+count).prop('checked');
	if(ischeck==true){
		$('#ajCheck_'+count).removeAttr('checked');
	}else{
		$('#ajCheck_'+count).attr('checked','checked');
	}
} 
</script>
<div class="pageContent" layoutH="10">
                <ul style="padding-left: 470px;">
					<li>
						<a class="buttonActive" href="#" onclick="baocun1()">
							<span>确定</span>
						</a>
					</li>
					<li >
						<a class="buttonActive"  href="#" onclick="quxiao1()">
							<span>取消</span>
						</a>
					</li>
				</ul>
		<table id="adjusttable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<span>Total:${adjustItemListCount }</span>
		<tr>
		<input type="hidden" id="applyno"  value="${id}" />
		<td class="td_title" width="5%"><input type="checkbox" name="teaAllCheck" id="teaAllCheck" checked="checked">NO</td>
		<td class="td_title" width="5%">加班申请日期</td>
		<td class="td_title" width="5%">加班类型</td>
		<td class="td_title" width="5%">小时</td>
		<td class="td_title" width="5%">截止日期</td>
		</tr>
		<c:forEach items="${adjustItemList}" var="d" varStatus="i">
		<tr onclick="childCheck1('${d.SEQ }')">
		<td class="td_type"  width="5%" >
		<input type="checkbox" onclick="childCheck1('${d.SEQ }')" id="ajCheck_${d.SEQ}" name="ajCheck" value="${d.SEQ }" valuename="${d.AR_DATE_STR}"  valuenameaj="${d.QUANTITY}">
		  ${i.count }</td>
		<td class="td_type"  width="5%" >${d.AR_DATE_STR}</td>
		<td class="td_type"  width="5%" >${d.ITEM_NO}</td>
		<td class="td_type"  width="5%" >${d.QUANTITY}</td>
		<td class="td_type"  width="5%" >${d.FINAL_DATE}</td>
		</tr>
		</c:forEach>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		
				</div>
</div>
