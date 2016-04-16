<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
<meta name="viewport" id="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
	h1{
		text-align: center;
	}
	table{
		margin: 30px auto 30px auto;
		border:none;
	}
	td{
		border: none;
		padding: 4px;
	}
	tr{
		border-bottom:1px solid white;
		border-top: 1px solid white; 
	}
	.book_action{
		margin-bottom: 30px ;
		text-align: center;
	}
	.btn{
		background-color: #008CFF;
		color: white;
	}
	.btndiv{
		margin: 10px auto 10px auto;
	}
	.book_action1{
		border: 2px solid white;
		text-align: center;
		margin: auto 10%;
		padding: 10px 0;
	}
	.info{
		margin: 5px 0;
	}
	body{
		background-color: #488FCE;
		color: white;
	}
</style>
</head>
<body>
<div class="wrap">
	<div>
		<div>
			<h1><?php echo ($data['title']); ?></h1>
			<img src="<?php echo ($data['image']); ?>" style="display:block;margin:auto" />
		</div>
		<div>
			<table>
				<tr>
					<td align="center" width="200px">ISBN</td>
					<td align="center"><?php echo ($data['isbn']); ?></td>
				</tr>
				<tr>
					<td align="center">作者</td>
					<td align="center"><?php echo ($data['author']); ?></td>
				</tr>
				<tr>
					<td align="center">出版社</td>
					<td align="center"><?php echo ($data['publisher']); ?></td>
				</tr>
				<tr>
					<td align="center">简介</td>
					<td align="center"><?php echo ($data['summary']); ?></td>
				</tr>
				
			</table>
		</div>
		<div class="book_action">
		<table>
			<tr>
			
				<td align="center" ><a href="<?php echo U('doContribute',array('title'=>$data['title'],'image'=>addslashes($data['image']),'translator'=>$data['translator'],'author'=>$data['author'],'isbn'=>$data['isbn'],'publisher'=>$data['publisher'],'summary'=>$data['summary']));?>">我要贡献</a> </th>
				<td align="center"><a href="<?php echo U(contribute);?>">取消 </a> </th>
			</tr>
		</table>
		</div>			
	</div>
</div>

</body>

<script type="text/javascript">
	function bkin(){
		var b=document.getElementById("userinfo");
		b.submit();
	}
	function abc(){
		var b=document.getElementById("bookjs");
		if (b.style.display=="none") b.style.display="block";
		else b.style.display="none";		
	}
	
</script>
</html>