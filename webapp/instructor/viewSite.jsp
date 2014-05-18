<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ROLE_INSTRUCTOR')">     <c:if test="${userIsInstructor=='y'}">
<script>
/* <![CDATA[ */

$(function() {
	
	$( '#item_Table tbody' ).sortable({
		update: function() {
			var order = $('#item_Table tbody').sortable('toArray');	
			$("#info").load("<c:url value='/orderingItems.html?order='/>"+order); 
		}	
});

});

/* ]]> */

</script>
</c:if>
</security:authorize>

<c:choose>

<c:when test="${site =='nothing'}">

<ul id="title">
<li>


The class website does not exist. 
<security:authorize access="hasRole('ROLE_INSTRUCTOR')"> <c:if test="${userIsInstructor=='y'}">
Do you want to create it?

<a href="<c:url value='/instructor/addSite.html?quarterTerm=${quarterTerm}&quarterYear=${quarterYear}&courseCode=${courseCode}&sectionNumber=${sectionNumber}' />">Yes</a> /  
<a href="<c:url value='/instructor/viewSections.html' />">No</a>

</c:if>
</security:authorize>
</li>
</ul>

</c:when>



<c:otherwise>

<ul id="title">

<li style="padding-left:60%;">  
<span style="color: #0066FF; font-weight:bold;">Item Search:</span>
</li>
<li>
<form action="<c:url value='/searchItems.html'/>"> 
	<input class="input_search" type="text" name="query" />
	<input type="hidden" name="siteId" value="${site.id}" /> 
<input class="subbutton" type="submit" name="search" value="Search" />
</form>
</li>
</ul>

<table class="site-table">
<tr>
<td id="wiki_content"> <h1 style="text-align: center"> ${site.section.course.code} - ${site.section.course.name} - Section ${site.section.number} </h1> </td>
</tr>

<tr>
<td id="wiki_content"> <h2 style="text-align: center"> ${site.section.quarter.quarterName} ${site.section.quarter.year} </h2> </td>
</tr>
</table>


<table class="general">
	<tr><th>Time and Location: </th>  <td> ${site.lectureHours} in ${site.lectureRoom}</td></tr>

	
	<tr><th>Instructor:  </th>  <td> <c:forEach items="${site.section.instructors}" var="instructor">   
										${instructor.firstName} ${instructor.lastName} --- email: ${instructor.email1}
									 </c:forEach>
								</td></tr>
		
	<tr><th>Office Hours: </th>  <td> ${site.officeHours}</td></tr>
</table>

 
<br />


<%--   ****************************      --%>

<table>
<tr>
<td>
<table class="outer_viewtable" style="width:400px;">
    <tr class="rowtypea">
      <td>Announcements:</td>
    </tr>

    <tr> 
      <td colspan="2">
        
        <display:table name="${site.announcements}" class="viewtable" defaultsort="1" uid="announcement">
          <display:column title="announcement" sortable="true" sortProperty="announcementContent">
            ${announcement.announcementContent}
          </display:column>
	  	  <display:column class="duedate" title="Date" sortable="true" sortProperty="timestamp">
            <fmt:formatDate value="${announcement.timestamp}" pattern="MM/dd" />
          </display:column>
       
        </display:table>
      
      </td>
    </tr>
    <security:authorize access="hasRole('ROLE_INSTRUCTOR')">  <c:if test="${userIsInstructor=='y'}">
    	<tr class="rowtypeb">
      		<td colspan="2">   
        		<a href="<c:url value='/instructor/addAnnouncement.html?siteId=${site.id}' />">add Announcements</a>
      		</td>
    	</tr>
    </c:if>	
    </security:authorize>
 </table>

</td>

<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>





<td>

  <table class="outer_viewtable" style="width:400px;">
    <tr class="rowtypea">
      <td>Assignments:</td>
    </tr>
    <tr> 
      <td colspan="2">
        <display:table name="${site.section.assignments}" htmlId="assignment-${status.index}"
            uid="assignment" class="viewtable" defaultsort="1" requestURI="viewSections.html">

          <display:column title="Assignment" sortable="true" sortProperty="name">
            <a href="viewSubmissions.html?assignmentId=${assignment.id}">${assignment.name}</a>
          </display:column>

          <display:column class="duedate" title="Due Date" sortable="true" sortProperty="dueDate">
            <fmt:formatDate value="${assignment.dueDate.time}" pattern="yyyy-MM-dd HH:mm:ss" />
          </display:column>
        </display:table>
      </td>
    </tr>
  </table>

</td>
</tr>

</table>



<%--   ************ Displaying Block and Items ****************      --%>



<br />
<br />



<c:if test="${not empty site.blocks}">
  
 <c:forEach items="${site.blocks}" var="block"> 
  <table class="outer_viewtable" style="width:400px;">
    <tr class="rowtypea">
      <td>${block.name}:</td>
    </tr>
	
	
    <tr> 
      <td colspan="2">
      
		
		<%--   ************ Displaying Block and Items ****************      --%>
		
		
		<table id="item_Table" class="viewtable">

<thead>
	<tr>
		<th class="sortable sorted order1">Item</th>
		<security:authorize access="hasRole('ROLE_INSTRUCTOR')"><c:if test="${userIsInstructor=='y'}"> <th>Action</th> </c:if></security:authorize>
	</tr>
</thead>

<tbody>

<c:forEach items="${block.items}" var="item">

	<tr id="${item.id}">
		<td>
		   <c:if test="${not empty item.stringContent}">
            	<a href="<c:url value='/diplayTextItem.html?itemId=${item.id}'/>">${item.name}</a>
            </c:if>
            
            <c:if test="${not empty item.urlContent}">
            	<a href="${item.urlContent}">${item.name}</a>
            </c:if>
            
            <c:if test="${not empty item.fileContent}">
  
            	<a href="<c:url value='/download.html?fileId=${item.fileContent.id}' />">${item.name}</a>
            	
            </c:if>
		</td>
		
		<security:authorize access="hasRole('ROLE_INSTRUCTOR')"> <c:if test="${userIsInstructor=='y'}">
		<td class="action">
				<a href="<c:url value='/instructor/editItem.html?itemId=${item.id}' />">
              			<img alt="[Edit]" title="Edit" src="<c:url value='/img/icons/edit.gif'/>" border="0" /> Edit
            	</a>
		</td>
		</c:if>
		</security:authorize>
		
	</tr>

</c:forEach>
	
	
</tbody>
</table>
		
		
		
		
		
		
		<%--   ************ Displaying Block and Items ****************      --%>
      </td>
    </tr>
	
	<security:authorize access="hasRole('ROLE_INSTRUCTOR')"> <c:if test="${userIsInstructor=='y'}">
	 <tr class="rowtypeb">
      <td colspan="2">
        <a href="<c:url value='/instructor/addItem.html?blockId=${block.id}' />">add Items</a>
      </td>
    </tr>
    </c:if>
  </security:authorize>
  <tr><td><security:authorize access="hasRole('ROLE_INSTRUCTOR')"><c:if test="${userIsInstructor=='y'}"><div id="info">You can shuffle items</div></c:if></security:authorize></td></tr>
  </table>
 
</c:forEach>
</c:if>





<%--   ****************************       --%>

<br />

  <security:authorize access="hasRole('ROLE_INSTRUCTOR')">  <c:if test="${userIsInstructor=='y'}">
<h4 style="margin-left: 40px;">
	[<a href="<c:url value='/instructor/addBlock.html?siteId=${site.id}' />">AddMoreBlocks</a>]
</h4>
</c:if>
</security:authorize>



<%--   ****************************    --%>





</c:otherwise>

</c:choose>

