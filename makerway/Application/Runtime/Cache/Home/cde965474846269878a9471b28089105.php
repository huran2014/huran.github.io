<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="gb2312">
</head>
<body>
	<div>
		<a href="<?php echo U(bookcenter);?>">首页</a>
		<?php if(is_array($mybooks)): foreach($mybooks as $key=>$item): ?><div onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
				<p>书名:<?php echo $s = iconv("UTF-8","GB2312",$item['bookinfo']['title'])?></p>
				<p>ISBN:<?php echo ($item['isbn']); ?></p>
				<img src="<?php echo ($item['bookinfo']['image']); ?>">
				<p>贡献者:<?php echo ($item['owner_name']); ?>____
				保管者:<?php echo ($item['keeper_name']); ?>____
				状态:<?php echo ($item['status']); ?></p>
				<p>贡献时间:<?php echo ($item['contribute_time']); ?></p>
				</br>
			</div><?php endforeach; endif; ?>
		
	</div>
</body>
</html>