<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<ul id="title">
<li><a class="bc" href="<c:url value='/home.html' />">Home</a></li>
<li>${assignment.section.course.code} - ${assignment.name}</li>
</ul>
<h3>Total points: ${assignment.totalPoints}</h3>

<table class="outer_viewtable" cellpadding="0">
<tr>
<td>
<display:table name="${assignment.submissions}" uid="submission" class="viewtable" requestURI="viewSubmissions.html" defaultsort="1" cellspacing="0">
<display:setProperty name="basic.msg.empty_list_row" value="<tr><td colspan={0}>No submissions yet.</td></tr>" />
    <display:column title="Name" sortable="true" sortProperty="student">
        <c:if test="${! empty submission.grade and ! submission.gradeMailed}"><img src="../img/icons/email.png" border="0" /></c:if>
        <a href="viewSubmission.html?submissionId=${submission.id}">${submission.student.lastName}, ${submission.student.firstName}</a>
    </display:column>
<c:if test="${not assignment.online}">
    <display:column title="# of Files" class="center" property="fileCount" sortable="true" />
</c:if>
    <display:column title="Grade" class="center" property="grade" sortable="true" sortProperty="smartGrade" defaultorder="descending" />
</display:table>
</td></tr>
<tr class="rowtypeb">
<td>
<a href="emailAssignmentGrades.html?assignmentId=${assignment.id}">Email Grades</a>
<c:if test="${not assignment.online}">
| <a href="downloadZip.html?assignmentId=${assignment.id}">Download All Files</a>
</c:if>
<c:if test="${assignment.online and assignment.pastDue}">
| <a href="autoGradeAssignment.html?assignmentId=${assignment.id}">Auto Grade</a>
</c:if>
<br />
</td>
</tr>
</table>
