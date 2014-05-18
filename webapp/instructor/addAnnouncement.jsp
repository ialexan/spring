<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<ul id="title">
<li> ${announcement.site.section.quarter.quarterName} ${announcement.site.section.quarter.year}, ${announcement.site.section.course.code} Section ${announcement.site.section.number}</li>
</ul>

<h2 style="text-align: center; color: #416DCC ">Add Announcement</h2>

<p>Please Enter Information For the Announcement: </p>

<form:form modelAttribute="announcement">
     
<input type="hidden" name="siteId" value="${announcement.site.id}" />     
     
<table class="general">
	<tr><th>Announcement: </th>  <td> <form:input type="text" path="announcementContent" class="forminput" /></td></tr>
	
	<tr><th></th> <td> <input type="submit" name="add" value="Add" class="subbutton"/> </td> </tr>
</table>


</form:form> 