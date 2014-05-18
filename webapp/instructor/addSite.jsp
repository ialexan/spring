<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<ul id="title">
<li> ${site.section.quarter.quarterName} ${site.section.quarter.year}, ${site.section.course.code} Section ${site.section.number}</li>
</ul>

<h2 style="text-align: center; color: #416DCC ">Add WebSite</h2>

<p>Please Enter Information for Class Website: </p>

<form:form modelAttribute="site">

<input type="hidden" name="sectionId" value="${site.section.id}" />

     
<table class="general">
	<tr><th>Lecture Hours: </th>  <td> <form:input type="text" path="lectureHours" class="forminput" /></td></tr>
	<tr><th>Lecture Room: </th>  <td>  <form:input type="text" path="lectureRoom" class="forminput" /></td></tr>
	<tr><th>office Hours: </th>  <td>  <form:input type="text" path="officeHours" class="forminput" /></td></tr>
	<tr><th></th> <td> <input type="submit" name="create" value="Create" class="subbutton"/> </td> </tr>
</table>


</form:form> 