<% 
 
	    response.setHeader("Content-Type", "text/plain; charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=baopan.txt");
	    response.setHeader("Pragma", "public");
		response.setHeader("Cache-Control", "max-age=0");
		
  
   java.util.List list=new  java.util.ArrayList();
   list=(java.util.List)request.getAttribute("listinfo");
	for(int i=0;i<list.size();i++){
		out.println(list.get(i));
	}
%>
