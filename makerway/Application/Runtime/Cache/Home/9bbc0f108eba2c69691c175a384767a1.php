<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
	<title>myKeeps</title>
	<script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
	<meta http-equiv="Content-Type" content="text/html" charset="gb2312">
</head>
<body>
	<a href="<?php echo U(bookcenter);?>">��ҳ</a>
	<div>
		<form action="<?php echo U(ensure);?>" method="post">
			ISBN:
			<input type="text" name="isbn" id="isbn" /></br>
			<!-- <input type="button" class="send" value="�����鿴"  /></br> -->
			<input type="submit" value="�鿴�����Ϣ"  /></br>
		</form>
	</div>
</body>
<script type="text/javascript">	
	$(function(){
		$('.send').click(function(){
			var s = $('#isbn').val();
			$.ajax({
				type:"POST",
				url:'https://api.douban.com/v2/book/isbn/:'+s,
				dataType:"jsonp",
				success:function(data) {    
        			alert(data.summary);
			     },    
			    error:function() {     
			        alert("��ѯ�������飬��������ISBN��");    
			     }
			})

		})
	})
</script>
</html>