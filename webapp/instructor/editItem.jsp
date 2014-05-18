<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul id="title">
<li> ${item.block.site.section.quarter.quarterName} ${item.block.site.section.quarter.year}, ${item.block.site.section.course.code} Section ${item.block.site.section.number}</li>

</ul>

<h2 style="text-align: center; color: #416DCC">Edit Item</h2>

<p>Please Enter Information For the Item: </p>


  
     
<table class="general">
	
	
	<tr><th>Select Item Type:</th>
	<td>
	    <form method="get" action="editItem.html">    
      		<select class="formselect" name="itemType" onchange="this.form.submit();">
        	   
          		<option value="1" <c:if test="${itemType == 1}">selected="selected"</c:if>>
					Text Content
          		</option>
		  		<option value="2" <c:if test="${itemType == 2}">selected="selected"</c:if>>
					URL Content
          		</option>
		  		<option value="3" <c:if test="${itemType == 3}">selected="selected"</c:if>>
					File Content
          		</option>
		 
      		</select>
      		<input type="hidden" name="itemId" value="${item.id}" /> 
      		<input class="subbutton" type="submit" value="Go" />
		</form>
	
	</td>
	</tr> 
	
	<form:form modelAttribute="item" enctype="multipart/form-data">
      
	<input type="hidden" name="itemType" value="${itemType}" />
	<input type="hidden" name="itemId" value="${item.id}" /> 
	<input type="hidden" name="blockId" value="${item.block.id}" />  
	
	<tr><th>Item Name: </th>  <td> <form:input type="text" path="name" class="forminput" value="${item.name}"/></td>
	</tr>
	                              
	<tr>
 
	         
				<c:if test="${itemType == 1}">
						<th>Text Item Content: </th>  
						<td>
						<c:choose>
						<c:when test="${not empty item.stringContent}"> 
							<form:input type="text" path="stringContent" class="forminput" value="${item.stringContent}"/>
						</c:when>
						<c:otherwise><form:input type="text" path="stringContent" class="forminput" /> </c:otherwise>
						</c:choose>
						</td>
		                 
				</c:if> 
	
				<c:if test="${itemType == 2}">
						<th>URL Item Content: </th>  
						<td> 
						<c:choose>
						<c:when test="${not empty item.urlContent}"> 
						<form:input type="text" path="urlContent" class="forminput" value="${item.urlContent}"/>
						</c:when>
						<c:otherwise><form:input type="text" path="urlContent" class="forminput" value="http://" /></c:otherwise>
						</c:choose>
						</td>
				</c:if>
			
				<c:if test="${itemType == 3}">
					<th>File:</th>
					<td><input type="file" name="uploadedFile" size="50" /></td>
				</c:if>
			
			
	
	</tr>
	
	<tr><th></th> <td> <input type="submit" name="edit" value="Edit" class="subbutton"/> </td> </tr>
	
	</form:form> 

</table>








