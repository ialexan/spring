<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>

<script type="text/javascript">
/* <![CDATA[ */
$(function(){
    $('#addSectionFormContent').hide();
    $('#addSectionLink').click(function(){
        if( $('#addSectionFormContent').is(':hidden') )
            $('#courseSelection').load('<c:url value="/course/getCoursesAsOptions.html" />');
        $('#addSectionFormContent').toggle();
    });
});
/* ]]> */
</script>

<ul id="title">
  <li>${quarter} - Courses</li>
  <li class="align_right">
    <form id="quarterSelectionForm" method="get" action="viewSections.html"> Select quarter:  
      <select class="formselect" name="quarter" onchange="this.form.submit();">
        <c:forEach var="q" items="${quarters}">
          <option value="${q.code}" <c:if test="${q.code == quarter.code}">selected="selected"</c:if>>
          ${q}
          </option>
        </c:forEach>
      </select>
      <input class="subbutton" type="submit" value="Go" />
    </form> 
  </li>
</ul>

<c:forEach var="section" items="${sections}" varStatus="status">
  <table class="outer_viewtable">
    <tr class="rowtypea">
      <td><a href="<c:url value='/sites/${quarter.quarterName}${quarter.year}/${section.course.code}/section${section.number}/' />">${section.course.code} ${section.course.name} - ${section.number} </a></td>
      <td class="action">
        <a href="editSection.html?sectionId=${section.id}">
          <img alt="[Edit Section]" title="Edit Section" src="<c:url value='/img/icons/edit.gif'/>" border="0" />
        </a>
      </td>
    </tr>
    <tr> 
      <td colspan="2">
        <display:table name="${section.assignments}" htmlId="assignment-${status.index}"
            uid="assignment" class="viewtable" defaultsort="1" requestURI="viewSections.html">
          <display:column title="Assignment" sortable="true" sortProperty="name">
            <a href="viewSubmissions.html?assignmentId=${assignment.id}">${assignment.name}</a>
          </display:column>
          <display:column class="duedate" title="Due Date" sortable="true" sortProperty="dueDate">
            <fmt:formatDate value="${assignment.dueDate.time}" pattern="yyyy-MM-dd HH:mm:ss" />
          </display:column>
          <display:column class="action" title="Action">
            <c:if test="${assignment.online}">
              <a href="viewOnlineAssignment.html?assignmentId=${assignment.id}">
                <img alt="[View]" title="View" src="<c:url value='/img/icons/view.gif'/>" border="0" /> View
              </a> | 
            </c:if>
            <a href="editAssignment.html?assignmentId=${assignment.id}">
              <img alt="[Edit]" title="Edit" src="<c:url value='/img/icons/edit.gif'/>" border="0" /> Edit
            </a>
          </display:column>
        </display:table>
      </td>
    </tr>
    <tr class="rowtypeb">
      <td colspan="2">
        <a href="addAssignment.html?sectionId=${section.id}">Add Assignment</a> |
        <a href="viewStudents.html?sectionId=${section.id}">View Students</a> |
        <a href="importRoster.html?sectionId=${section.id}">Enroll Students</a> |
        <a href="viewEnrollments.html?sectionId=${section.id}">Enter Grades</a> |
        <a href="<c:url value='/assessment/editCourseJournal.html?sectionId=${section.id}'/>">Assessment</a>
      </td>
    </tr>
  </table>
</c:forEach>

<div class="courseform">
<form id="addSectionForm" action="addSection.html" method="get">
  <a id="addSectionLink">Add Section</a>
  <span id="addSectionFormContent">
    <select id="courseSelection" class="formselect" size="1" name="courseId">
      <option value=""></option>
    </select>
    <input type="hidden" name="quarter" value="${quarter.code}"/>
    <input type="submit" name="submit" class="subbutton" value="OK" />
  </span>
</form>
</div>
