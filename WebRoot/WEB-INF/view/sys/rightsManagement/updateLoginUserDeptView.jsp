<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ page import="java.util.*" %>

<%!
	public void writeDeptTree(JspWriter out, List deptList) 
	{
		try {
			int listSize = deptList.size() ;
			
			for(int i = 0 ; i < listSize ; ++i){
				Map deptMap = (LinkedHashMap)deptList.get(i) ;
				System.out.println("listSize>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ deptMap.get("CONTENT"));
				String checked = (deptMap.get("ISCHECKED").toString().equals("1") ? "checked=\"true\"" : "" ) ;
				
				if(deptMap.get("childDeptList") != null){
					
					out.print("<li><a tname=\"DEPTNO\" tvalue=\"" + deptMap.get("DEPTNO") + "\" " + checked + ">" + deptMap.get("CONTENT") + "</a><ul>") ;
					
					this.writeDeptTree(out, (List)deptMap.get("childDeptList")) ;
					
					out.print("</ul></li>") ;
				}
				else{
					out.print("<li><a tname=\"DEPTNO\" tvalue=\"" + deptMap.get("DEPTNO") + "\" " + checked + ">" + deptMap.get("CONTENT") + "</a></li>") ;	
				}
			}
			
			
		} catch (Exception e) {}
	} 
%> 

<div id="loginUserDept" style=" float:left; display:block; margin:10px; overflow:auto; width:600px; height:230px; border:solid 1px #CCC; line-height:21px; background:#FFF;">
	<ul class="tree treeFolder treeCheck expand" >
		<% 
			List loginUserDeptList = (List)request.getAttribute("loginUserDeptList") ;
			
			this.writeDeptTree(out , loginUserDeptList) ;
		%>
	</ul>
</div>
