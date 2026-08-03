<%@ page contentType="text/html; charset=UTF-8" language="java"errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script language="JavaScript" type="text/JavaScript">
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/disc/autoExcel/retrieveNoticeList" method="post" rel="pagerForm"
		id="retrieveNoticeList" name="retrieveNoticeList">
	<div class="searchBar" >
		<table class="searchContent">
  			<tr> 
   			 	<td height="11  " colspan="2"></td>
  			</tr>
  			<tr> 
    			<td width="25"></td>
    			<td width="789" valign="top"> 
     		 <!-- Page Title Start 
          			 <chrs:history />-->
      <!-- Page Title End -->
      <!-- Content Start -->
      		<table width="789" border="0" cellspacing="0" cellpadding="0">
				<tr> 
          			<td height="7"></td>
       			</tr>
        		<tr> 
          			<td align="right"> 
            <!-- Button Start -->
           <table width="100%" cellspacing="0" cellpadding="3">
              <tr> 
                <td height="7" colspan="11" align="right">
					<input name="reset3" type="button" class="button_default" value="<chrs:message messageID="Create"/>"   onClick="javascript:showDetailWindow('/sys.disc.retrieveNoticeDetailForm.laf?BLTIN_TP=<c:out value="${BLTIN_TP}"/>')"  onMouseOver="this.style.color='#650000'" onMouseOut="this.style.color='#202020'">                
                </td>
              </tr>
              <tr> 
                <td class="table_search_line" colspan="11"></td>
              </tr>
              <tr class="bg_gray_F7F7F7"> 
                <td width="100" align="right"><chrs:message messageID="Apply Date"/> : </td>
                <td width="280">
                  	<input id="START_DATE" name="START_DATE" value="<c:out value="${START_DATE}"/>" type="text" class="input_textfield" size="14" required readonly>
       				<a href="javascript:showDateSelectDialog(form1.START_DATE)">
       				<img src="/common/images/icon_datepicker.gif" width="13" height="12" border="0" ></a>
       				
       				~
       				
       				<input id="END_DATE" name="END_DATE" value="<c:out value="${END_DATE}"/>" type="text" class="input_textfield" size="14" required required readonly>
       				<a href="javascript:showDateSelectDialog(form1.END_DATE)">
       				<img src="/common/images/icon_datepicker.gif" width="13" height="12" border="0" ></a>

                </td>
                <td></td>
                <td width="100" align="right"><chrs:message messageID="UseYN"/> : </td>
                <td width="150">
					<chrs:codeClass codeClass="SC001" all="All" name="USE_YN" selected="${USE_YN}" />
                </td>
                <td>
                	<!--back to page Begin -->
                	<input type="hidden" name="currentPage" value="<c:out value="${currentPage}"/>">
                	<!--back to page End -->

                	<!--BLTIN_TP begin-->
                	<input type="hidden" name="BLTIN_TP" value="<c:out value="${BLTIN_TP}"/>">
                	<!--BLTIN_TP End -->

                </td>
                <td width="62" align="right"><input name="search" type="button"class="button_search" value="<chrs:message messageID="Search"/>"  onclick=javascript:searchByCondition() onMouseOver="this.style.color='#650000'" onMouseOut="this.style.color='#202020'"> </td>
              </tr>
              <tr> 
                <td class="table_search_line" colspan="11"></td>
              </tr>
            </table>
            <!-- Button End -->
          </td>
        </tr>
        </form>
        <tr> 
          <td> <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="8"> 
                </td>
              </tr>
              <tr> 
                <td height="7" colspan="8"> 
                  <!-- List Start -->
                </td>
              </tr>
              
              <c:if test="${result!=null}" >
	              <tr> 
	                <td width="6%" align="center" class="table_header_simple_c">
	                    <!--modify for list sorting:为message标签增加若干属性-->
	                	<chrs:message 
	                		messageID="content and img" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="TO_NUMBER(BLTIN_BOARD_SEQ)"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                		/>
	                </td>
	                <td width="30%" align="center" class="table_header_simple_c">
	                	<chrs:message messageID="Title" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="TITLE"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                	/><br>
	                </td>
	                <td width="6%" align="center" class="table_header_simple_c">
	                	<chrs:message messageID="Start Date" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="BLTIN_STRT_DATE"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                	/><br>
	                </td>
	                <td width="6%" align="center" class="table_header_simple">
	                	<chrs:message messageID="End Date" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="BLTIN_END_DATE"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                	/><br>
	                </td>
	                <td width="6%" align="center" class="table_header_simple">
	                	<chrs:message messageID="File Count" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="FILE_CNT"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                	/><br>
	                </td>
	                <td width="6%" align="center" class="table_header_simple">
	                	<chrs:message messageID="UseYN" 
	                		link="/sys.disc.retrieveNoticeList.laf"
	                		parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&currentPage=${currentPage}&BLTIN_TP=${BLTIN_TP}"
	                		sortBy="USE_YN"
	                		currentSortBy="${currentSortBy}"
	                		currentSort="${currentSort}"
	                	/><br>
	                </td>
	              </tr>
	              <tr> 
	                <td colspan="8" class="table_header_line"></td>
	              </tr>
	              <tr> 
	                <td height="3" colspan="8"></td>
	              </tr>
	
				<c:forEach items="${result}" var="oneResult">     
	              <tr class="table_padding" onMouseOver=this.style.backgroundColor="#F7F7F7" onMouseOut=this.style.backgroundColor="FFFFFF"> 
	              	<chrs:listing entityname="oneResult" value="BLTIN_BOARD_SEQ" link="\"javascript:showDetailWindow('/sys.disc.retrieveQueryDetailForm.laf?BLTIN_BOARD_SEQ=${oneResult.BLTIN_BOARD_SEQ}')\""/>
	                <chrs:listing entityname="oneResult" value="TITLE" enCutLength="45" cnCutLength="45" link="\"javascript:showDetailWindow('/sys.disc.retrieveNoticeDetailForm.laf?BLTIN_BOARD_SEQ=${oneResult.BLTIN_BOARD_SEQ}')\""/>                   
                    <chrs:listing entityname="oneResult" value="BLTIN_STRT_DATE" />
					<chrs:listing entityname="oneResult" value="BLTIN_END_DATE"  />
					<chrs:listing entityname="oneResult" value="FILE_CNT"  />
					<chrs:listing entityname="oneResult" value="USE_YN" codeClass="SC001" />
			    </tr>  
			    <tr> 
                    <td colspan="8" class="table_line_simple"></td>
                </tr>
				</c:forEach>
	              

	              <tr> 
	                <td colspan="8" class="table_line_simple"></td>
	              </tr>
	            </table>
	            <!-- List End -->
	        
	            <!-- Page Navigation Start-->
	            <!--modify for list sorting:分页的parameters增加两个:SORTBY & SORT-->
	        <chrs:page       
	               link="/sys.disc.retrieveNoticeList.laf"
	               parameters="&START_DATE=${START_DATE}&END_DATE=${END_DATE}&USE_YN=${USE_YN}&SORTBY=${currentSortBy}&SORT=${currentSort}&BLTIN_TP=${BLTIN_TP}"
	               total="${resultCount.CNT}"
	               currentpage="${currentPage}"
	               pagesize= "${pageSize}"
	               beginlabel="paging_prv10"
	               endlabel="paging_next10"
	               prevlabel="paging_prv"
	               nextlabel="paging_next"
	               pageGroupSize="${pageGroupsize}"
	               useJS="false"/>            
	            
	            <!-- Page Navigation End -->
	          </td>
	        </tr>
        
        </c:if>
        
      </table></td>
  </tr>
  <tr> 
    <td height="30" colspan="2"></td>
  </tr>
</table>
</form>
</div>
<!--  <chrs:xjos /> -->

</html>