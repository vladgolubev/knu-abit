$(document).ready(function() {
	$('table').tablesorter();
});

var tr = document.getElementsByTagName('tr');
for (var i = 1; i < tr.length; i++) {
	if (tr[i].getElementsByTagName('td')[5].innerHTML === '+') {
		tr[i].style.background = "#cfc";
	}
}