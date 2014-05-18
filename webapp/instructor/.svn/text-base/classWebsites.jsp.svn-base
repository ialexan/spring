
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
/* <![CDATA[ */
$(function() {
	// $( "#accordion" ).accordion();
	
	$('#accordion').accordion({
		autoHeight: false,
		create: function(event, ui) {
   			var $activeCord = $(this).find('.ui-state-active');
            var contentDiv = $activeCord.next("div");
            contentDiv.load('getQuarter.html?quarterCode=' + $activeCord.attr('data-id'));
   		},
        change: function(event, ui){
               var $activeCord = $(this).find('.ui-state-active');
               var contentDiv = $activeCord.next("div");
               contentDiv.load('getQuarter.html?quarterCode=' + $activeCord.attr('data-id'));
        }

	});
	
	

});
/* ]]> */

</script>


<h1 style="text-align: center; color: #0066FF;">Class Website List</h1>



<div id="accordion">


<c:forEach items="${quarters}" var="quarterItem">
<h3 data-id="${quarterItem.code}"> ${quarterItem.quarterName} ${quarterItem.year} </h3>

<div id="section-${quarterItem.code}"> 
</div>

</c:forEach>

</div>


