<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
</head>
<body onLoad="javascript:pageTracker._setVar('minelab_liujie');">
<div>
		<a href="<?php echo U(bookcenter);?>">首页</a>
		<?php if(is_array($booklist)): foreach($booklist as $key=>$item): ?><div onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
				<p>书名:<?php echo ($item['bookinfo']['title']); ?></p>
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