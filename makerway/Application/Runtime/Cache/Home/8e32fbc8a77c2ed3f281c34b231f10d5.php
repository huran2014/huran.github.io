<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="gb2312">
    <title>pass on</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/index.css">
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
</head>
<body>
    <div class="submybook">
        <ul>
            <?php if(is_array($mybooks)): foreach($mybooks as $key=>$item): ?><li>
                    <div class="main-body-li">
                        <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                        <span class="name">书名：<?php echo $s = iconv("UTF-8","GB2312",$item['bookinfo']['title'])?></span>
                        <span class="author">类别：</span>
                        <span class="favour">创建时间：<?php echo ($item['contribute_time']); ?></span>
                        <span class="comment">书本状态：</span>
                        <input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>
                    </div>
                </li><?php endforeach; endif; ?>
            <span class="fenpage"><?php echo ($page); ?></span>
            <!--<<?php echo ($page); ?>>  &lt;!&ndash;分页输出&ndash;&gt;-->
        </ul>
    </div>
</body>
</html>