<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="gb2312">
</head>
<body>
	<div>
		<a href="<?php echo U(bookcenter);?>">��ҳ</a>
		<?php if(is_array($mybooks)): foreach($mybooks as $key=>$item): ?><div onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
				<p>����:<?php echo $s = iconv("UTF-8","GB2312",$item['bookinfo']['title'])?></p>
				<p>ISBN:<?php echo ($item['isbn']); ?></p>
				<img src="<?php echo ($item['bookinfo']['image']); ?>">
				<p>������:<?php echo ($item['owner_name']); ?>____
				������:<?php echo ($item['keeper_name']); ?>____
				״̬:<?php echo ($item['status']); ?></p>
				<p>����ʱ��:<?php echo ($item['contribute_time']); ?></p>
				</br>
			</div><?php endforeach; endif; ?>
		
	</div>
</body>
</html>