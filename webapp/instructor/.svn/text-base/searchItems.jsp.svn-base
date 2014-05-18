<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<h1 style="text-align: center; color: #0066FF;">Search Results</h1>

<h4>Part of the text that matches your query is underlined and highlighted:</h4>

<display:table name="${items}" class="viewtable" defaultsort="1" uid="item">	
	<display:column title="item Name">
 		<c:if test="${not empty item.stringContent}">
    			<a href="<c:url value='/instructor/diplayTextItem.html?itemId=${item.id}'/>">${item.nameSearchResult}  </a>
    		</c:if>
            
    		<c:if test="${not empty item.urlContent}">
    			<a href="${item.urlContent}">${item.nameSearchResult}  </a>
    		</c:if>   
   </display:column>          
       
      
           <display:column title="Item Content" sortable="true">    
              <c:if test="${not empty item.stringContentSearchResult}">
       			 ${item.stringContentSearchResult}   
       		  </c:if>   	
              <c:if test="${not empty item.urlContentSearchResult}">
    	       ${item.urlContentSearchResult}    
      		</c:if>
           </display:column>
	 

       

      



		          
</display:table>