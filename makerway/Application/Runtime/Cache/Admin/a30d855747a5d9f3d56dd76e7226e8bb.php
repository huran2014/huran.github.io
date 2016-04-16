<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="/passon/Public/css/ad_books.css">
</head>
<body>
	<div>
	<ul>
		<li><strong onclick="document.location='<?php echo U(index);?>'">首页</strong></li>
		<li><strong onclick="document.location='<?php echo U(books);?>'">查看所有书籍</strong></li>
		<li><strong onclick="document.location='<?php echo U(users);?>'">查看所有用户</strong></li>
	</ul>
		<div>
			<ul class="whole">
				<?php foreach ($books as $id => $book): ?>
				<li>
					<ul class="book">
						<li>
							<div class="id">
								<p>书号</p>
								<strong><?php echo ($book['book_id']); ?></strong></br></br>
								<span class="delete" onclick="document.location='<?php echo U(deleteBook,array('book_id'=>$book['book_id']));?>'"><strong>删除此书</strong></span>
							</div>
						</li>
						<li><div>
								<img src="<?php echo ($book['bookinfo']['image']); ?>">
							</div>
						</li>
						<li>
						<div class="info">
							<p>书名：<?php echo ($book['bookinfo']['title']); ?></p>
								<p>所有者：<?php echo ($book['con_name']); ?></p>
								<p>贡献者：<?php echo ($book['owner_name']); ?></p>
							<p>贡献时间：<?php echo ($book['contribute_time']); ?></p>
						</div>
						</li>
					</ul>
				</li>
				<?php endforeach ?>
			</ul>
		</div>
	</div>
</body>
</html>