<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="section" value="${assignment.section}"/>

<script type="text/javascript">
/* <![CDATA[ */
$(function(){
    $('#publishDate').datetimepicker({
        inline: true,
        showSecond: true,
        timeFormat: 'hh:mm:ss'
    });

    $('#dueDate').datetimepicker({
        inline: true,
        showSecond: true,
        timeFormat: 'hh:mm:ss'
    });
});

function confirmDeleteAssignment( assignmentId )
{
    message = "Are you sure you want to delete this assignment?";
    url = "deleteAssignment.html?assignmentId=" + assignmentId;
    confirmGoto( message, url );
}
/* ]]> */
</script>

<ul id="title">
<li>${section.course.code} ${section.course.name} - ${section.number}</li>
</ul>
<h3>
Edit Assignment |
<a href="#" onclick="confirmDeleteAssignment(${assignment.id});">Delete Assignment</a> <security:authorize access="hasAnyRole('ROLE_ADMIN')">|
<a href="saveAssignmentAsTemplate.html?assignmentId=${assignment.id}">Save As Template</a></security:authorize>
<c:if test="${assignment.online && not assignment.published}">|
<a href="../qa/editQuestionSheet.html?sheetId=${assignment.questionSheet.id}&amp;sectionId=${assignment.section.id}">Edit Questions</a>
</c:if>
</h3>

<form:form commandName="assignment">
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

<c:if test="${not assignment.online}">
  <tr>
    <th>Allowed File Extensions:</th>
    <td>
      <form:input path="allowedFileExtensions" cssClass="leftinput" size="30" maxlength="255" />
      <a id="assignment_file_ext_help_link" href="#">Help</a>
    </td>
  </tr>
</c:if>

<c:if test="${assignment.online and not assignment.published}">
  <tr>
	<th>Number of Sections:</th>
	<td>
	  <form:input path="numOfSections" cssClass="leftinput" size="30" maxlength="255" />
	  <a id="assignment_section_help_link" href="#">Help</a>
	</td>
  </tr>
	
  <tr>
	<th>Description:</th>
	<td>
	  <form:textarea path="questionSheet.description" cssStyle="width: 99%;" rows="15" cols="80" />
	</td>
  </tr>

  <tr>
    <th>Publish Date:</th>
    <td>
      <form:input path="publishDate" cssClass="leftinput" size="30" maxlength="30" />
      <a id="assignment_publish_date_help_link" href="#">Help</a>
    </td>
  </tr>
</c:if>

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

<script type="text/javascript">
/* <![CDATA[ */
  CKEDITOR.replaceAll();
/* ]]> */
</script>
