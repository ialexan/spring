<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
/* <![CDATA[ */
$(function(){
	$('#dueDate').datetimepicker({
        inline: true,
        showSecond: true,
        timeFormat: 'hh:mm:ss'
    });
});
/* ]]> */
</script>

<ul id="title">
<li>${section.course.code} ${section.course.name} - ${section.number}</li>
</ul>
<h3>
Add Assignment <security:authorize access="hasAnyRole('ROLE_ADMIN')"><c:if test="${section.course.numOfAssignmentTemplates > 0}">|
<a href="viewAssignmentTemplates.html?sectionId=${section.id}">Assignment Templates</a></c:if></security:authorize>
| <a href="viewOnlineAssignments.html?sectionId=${section.id}">Online Assignments</a></h3>

<form:form commandName="assignment">
<input type="hidden" name="sectionId" value="${section.id}" />
<table class="general">
  <tr>
    <th>Name:</th>
    <td>
      <form:input path="name" cssClass="leftinput" size="30" maxlength="255" />
      <div class="error"><form:errors path="name" /></div>
    </td>
  </tr>

  <tr>
    <th>Short name:</th>
    <td>
      <form:input path="shortName" cssClass="leftinput" size="30" maxlength="255" />
      <a id="assignment_short_name_help_link" href="#">Help</a>
    </td>
  </tr>

  <tr>
    <th>Total points:</th>
    <td><form:input path="totalPoints" cssClass="leftinput" size="30" maxlength="255" /></td>
  </tr>

  <tr>
    <th>Allowed File Extensions:</th>
    <td>
      <form:input path="allowedFileExtensions" cssClass="leftinput" size="30" maxlength="255" />
      <a id="assignment_file_ext_help_link" href="#">Help</a>
    </td>
  </tr>

  <tr>
    <th>Due Date:</th>
    <td>
      <form:input path="dueDate" cssClass="leftinput" size="30" maxlength="30" />
    </td>
  </tr>

  <tr>
    <th>Viewable After Due Date:</th>
    <td>
      <form:checkbox path="viewableAfterDueDate" />
      <a id="assignment_viewable_after_due_date_help_link" href="#">Help</a>
    </td>
  </tr>

  <tr><th></th><td><input class="subbutton" type="submit" value="Done" /></td></tr>
</table>
</form:form>
