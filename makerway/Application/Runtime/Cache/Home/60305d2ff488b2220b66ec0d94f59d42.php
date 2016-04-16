<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8">
	<title>我要分享</title>
	<link rel="stylesheet" type="text/css" href="/passon/Public/css/ensure.css">
	<script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div class="content">
		<div class="head">
			<div class="title">
				<div class="title-left">
					<span class="title-m"><span class="big">M</span><span class="title-top">AKERWAY</span><span class="title-bottom">一起分享智慧</span></span>
					<span class="title-main"><a href="<?php echo U(index);?>">首页</a></span>
					<!--<span class="title-squre"><a href="<?php echo U(squre);?>">广场</a></span>-->
					<span class="title-share"><a href="<?php echo U(share);?>">共享库</a></span>
				</div>
				<div class="div-search">
					<form name="search" action="<?php echo U(search);?>" method="post">
						<input  class="search" name="search" type="text" placeholder="书名/分类？" />
						<div class="search-a">
							<input type="image" src="/passon/Public/images/search.png" name="img">
							<!--<input type="submit" value="submit"/>-->
							<!--<img src="/passon/Public/images/search.png"/>-->
						</div>
					</form>
					<span>热门搜索：</span>
					<span class="detail">
                        <span><a>数据库系统概念</a></span>
                        <span><a>CSS 3实战</a></span>
                        <span><a>操作系统概念</a></span>
			        </span>
				</div>
				<!--<span>-->
				<!--<ul>-->
				<!--<li>注册</li>-->
				<!--<li>登录</li>-->
				<!--</ul>-->
				<!--</span>-->
				<!--<span class="username-list">-->
				<!--<ul>-->
				<!--<li class="register"><a href="<?php echo U(register);?>">注册</a></li>-->
				<!--<li class="login"><a href="<?php echo U(login);?>">登录</a></li>-->
				<!--</ul>-->
				<!--</span>-->
				<span class="username-c">hello <span class="username-d"><a href="<?php echo U(person);?>"><?php echo cookie('username')?></a></span></span>
			</div>
		</div>
		<div class="main">
			<div class="classify">
				<div class="classify-title"><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(index);?>">MakerWay</a>><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(person);?>">个人中心</a>>我要分享</div>
			</div>
			<div class="main-body">
				<div>
					<p>ISBN:<?php echo ($book['isbn13']); ?></p>
					<p>书名:<?php echo $book['title']?></p>
					<img src="<?php echo ($book['image']); ?>"/>
					<p>作者:<?php echo $book['author']?></p>
					<p>出版社:<?php echo $book['publisher']?></p>
					<p>简介:<?php echo $book['summary']?></p>
					</br>
				</div>
				<div class="conname">
					<form action="<?php echo U(doContribute);?>" method="post">
						<input name="submit" type="submit" value="确认分享" />
						<input class="current" type='button' onclick="document.location='<?php echo U(contribute);?>'" value="返回重填"/></br></br>
						<input type="text" name="con_name" id='con_name' value="" style="display:none;margin:auto;" />
						<input type="text" name="isbn" value="<?php echo ($book['isbn13']); ?>" style="display:none;margin:auto;" />
					</form></br>
					<label>分享者名字默认是您的用户名，是否&nbsp;&nbsp;</label><span class='change' onclick="ab()">修改</span><label>&nbsp;&nbsp;您的分享备注名？</label></div>
			</div>
				<!--<a href="<?php echo U(doContribute,array('isbn'=>$book['isbn13']));?>">确认贡献</a>-->
				<!--<a href="<?php echo U(contribute);?>">返回重填</a>-->
			</div>
		</div>
	</div>
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
					(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
				m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

		ga('create', 'UA-68503572-4', 'auto');
		ga('send', 'pageview');

	</script>
</body>
<script type="text/javascript" src="/passon/Public/js/labelsearch.js"></script>
<script type="text/javascript">
	function ab(){
		var b=document.getElementById('con_name');
		if (b.style.display=='none') {
			b.style.display='block';
		}else {
			b.style.display='none';
			b.value='';
		}
	}
</script>
</html>