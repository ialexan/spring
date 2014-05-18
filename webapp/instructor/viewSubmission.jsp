<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="csns" uri="http://cs.calstatela.edu/csns" %>

<script type="text/javascript">
/* <![CDATA[ */
$(function(){
    $.ajaxSetup({
        cache: false
    });
    $('#dueDate').datetimepicker({
        inline: true,
        showSecond: true,
        timeFormat: 'hh:mm:ss'
    });
    $('#dueDate-link').click(function(){
        $("#dueDateForm").toggle();
    });
    $('#dueDateForm').hide();
    $('#grade').editable( 'editSubmissionGrade.html', {
        submitdata: { 'submissionId': ${submission.id} },
        placeholder: '&nbsp;',
        width: 80,
        event: 'dblclick',
        submit: 'Save'
    });
    $('#grade-link').click(function(){
       $('#grade').trigger('dblclick'); 
    });
    $('#comments').editable( 'editSubmissionComments.html', {
        submitdata: { 'submissionId': ${submission.id} },
        placeholder: '&nbsp;',
        type: 'textarea',
        rows: 10,
        event: 'dblclick',
        submit: 'Save'
    });
    $('#comments-link').click(function(){
        $('#comments').trigger('dblclick'); 
     });
});

function toggleFilePublic( fileId )
{
    $("#file-" + fileId).load("<c:url value='/file/toggleFilePublic.html?fileId=' />" + fileId);
}
/* ]]> */
</script>

<ul id="title">
<li><a class="bc" href="viewSubmissions.html?assignmentId=${submission.assignment.id}">${submission.assignment.section.course.code}
- ${submission.assignment.name}</a></li>
<li><csns:contactLink userId="${submission.student.id}">${submission.student.firstName}
${submission.student.lastName}</csns:contactLink></li>
</ul>

<p><a id="dueDate-link" href="javascript:void(0)">Due Date:</a>
<fmt:formatDate value="${submission.effectiveDueDate.time}" pattern="MM/dd/yyyy HH:mm:ss" /></p>
<form id="dueDateForm" action="editSubmissionDueDate.html" method="post">
<p><input id="dueDate" name="dueDate" class="leftinput" size="20" maxlength="20"
  value="<fmt:formatDate value="${submission.effectiveDueDate.time}" pattern="MM/dd/yyyy HH:mm:ss" />" />
<input type="hidden" name="submissionId" value="${submission.id}" />
<input class="subbutton" type="submit" value="OK" /></p>
</form>

<c:if test="${submission.assignment.online and submission.answerSheet != null}">
<p><b>Submission ID:</b>
<a href="viewOnlineSubmission.html?submissionId=${submission.id}">${submission.id}</a>
  <c:if test="${submission.assignment.pastDue}">[<a
    href="autoGradeSubmission.html?submissionId=${submission.id}">Auto Grade</a>]</c:if>
</p>
</c:if>

<c:if test="${not submission.assignment.online}">
<table class="outer_viewtable" cellpadding="0">
<tr><td>
<display:table name="${submission.files}" uid="file" class="viewtable" requestURI="viewSubmission.html" defaultsort="1">
  <display:column title="Name" sortProperty="name" sortable="true">
    <a href="<c:url value='/download.html?fileId=${file.id}' />">${file.name}</a>
  </display:column>
  <display:column title="Version" property="version" class="center" />
  <display:column title="Size" property="size" sortable="true" />
  <display:column sortProperty="date" title="Last Uploaded" sortable="true">
    <fmt:formatDate value="${file.date}" pattern="yyyy-MM-dd HH:mm:ss" />
  </display:column>
  <display:column class="center">
    <c:set var="img" value="closed_book.png" />
    <c:if test="${file['public']}">
      <c:set var="img" value="open_book.png" />
    </c:if>
    <div id="file-${file.id}">
      <a href="javascript:void(0)" onclick="toggleFilePublic(${file.id});">
        <img border="0" alt="[*]" src="<c:url value='/img/icons/${img}'/>" />
      </a>
    </div>
  </display:column>
</display:table>
</td></tr>
<tr class="rowtypeb" style="text-align: right;"><td>
<a href="uploadToSubmission.html?submissionId=${submission.id}">Upload Additional Files</a> |
<a href="downloadZip.html?submissionId=${submission.id}">Download All Files</a>
</td></tr>
</table>
</c:if>

<h4><a id="grade-link" href="javascript:void(0)">Grade</a></h4>
<div id="grade" class="editable_input">${submission.grade}</div>

<h4><a id="comments-link" href="javascript:void(0)">Comment</a></h4>
<pre id="comments">${submission.comments}</pre>

<button class="subbutton" onclick="gotoUrl('viewSubmissions.html?assignmentId=${submission.assignment.id}');">OK</button>
