<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<ul id="title">
<li> ${block.site.section.quarter.quarterName} ${block.site.section.quarter.year}, ${block.site.section.course.code} Section ${block.site.section.number}</li>
</ul>

<h2 style="text-align: center; color: #416DCC ">Add Block</h2>

<p>Please Enter Information For the block: </p>

<form:form modelAttribute="block">
     
<input type="hidden" name="siteId" value="${block.site.id}" />     
     
<table class="general">
	<tr><th>Block Name: </th>  <td> <form:input type="text" path="name" class="forminput" /></td></tr>
	
	<tr><th></th> <td> <input type="submit" name="add" value="Add" class="subbutton"/> </td> </tr>
</table>


</form:form> 