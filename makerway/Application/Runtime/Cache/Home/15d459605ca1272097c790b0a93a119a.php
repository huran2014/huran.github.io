<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<title>Makerway</title>
	<script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/passon/Public/css/phonelogin.css">
</head>
<body>
<div class="content">
	<!--<div class="title">-->
	<!--<div>-->
	<!--<span>Pass On</span>-->
	<!--</div>-->
	<!--</div>-->
	<div class="main">
		<div class="book-message">
			<div class="main-img">
				<img src="<?php echo ($bookin['image']); ?>"/>
			</div>
			<!--<span class="book-title"><?php echo $s = iconv("UTF-8","GB2312",$bookin['title'])?></span>-->
			<span class="book-title">《<?php echo ($bookin['title']); ?>》</span>
			<?php if ($book['con_name'] != null): ?>
			<span class="book-info">分享者：<?php echo ($book['con_name']); ?></span>
			<?php endif ?>
			<?php if ($book['con_name'] == null): ?>
			<span class="book-info">分享者：<?php echo ($book['owner_name']); ?></span>
			<?php endif ?>
			<?php if ($book['status']==1 || $book['status']==3) : ?>
			<span class="book-info">状态：可借阅,现在在<?php echo ($book['keeper_name']); ?>那儿</span>
			<?php endif ?>
			<?php if ($book['status']==2 || $book['status']==5) : ?>
			<span class="book-info">状态：已借出，现在在<?php echo ($book['keeper_name']); ?>那儿</span>
			<?php endif ?>
		</div>
		<div class="book-login">
			<!--action待定-->
			<form action="<?php echo U(pLogin);?>" method="post" onsubmit="return check()">
				<div class="book-input"><label>用户名：</label><input type="text" name="username"/></div>
				<div class="book-input"><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：</label><input type="password" name="password"/></div>
				<span class="span1"><a href="<?php echo U(phoneRegister);?>">立即注册>></a></span>
				<span class="span2"><a href="<?php echo U(phoneForget);?>">&lt;&lt;忘记密码?</a></span>
				<div class="book-input2"><input type="submit" name="submit" value="登录"/></div>
			</form>
		</div>
	</div>
</div>
</body>
<script>
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
				(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
			m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-68503572-4', 'auto');
	ga('send', 'pageview');

</script>
<script type="text/javascript" src="/passon/Public/js/phonelogin.js"></script>
</html>