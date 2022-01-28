<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Spring boot Index</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script> 
		$.ajax({ 
			type: "GET", 
			url: "/testString", 
			success: (data) => { 
				console.log(data); 
				$('#contents').html(data); 
			} 
		}); 
	</script>

</head>
<body>
	<h1>test page</h1>
	<div id="contents">
	</div>
</body>
</html>