<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="impactservice.DrupalEditor"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <jsp:include page="../includes.jsp" />
   </head>
  <body>
		<jsp:include page="../header.jsp" />
		<!-- Contents -->
		
		<jsp:include page="helpmenu.jsp" />
		<div class="impactcontent">
		 <div class="cmscontent"> 
    <%try{out.print(DrupalEditor.showDrupalContent("?q=howto",request,response));}catch(DrupalEditor.DrupalEditorException e){out.print(e.getMessage());response.setStatus(e.getCode());}%>                                  			
  			</div> 
		</div>
		
		
	 
  <!-- /Contents -->
	<jsp:include page="../footer.jsp" />
  </body>
</html>