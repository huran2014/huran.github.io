<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="gb2312">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phonemain.css">
</head>
<body>
<div class="content">
    <div class="book-next">
        <!--action����-->
        <form action="<?php echo U(phoneNext);?>" method="post">
            <input type="text" placeholder="�ֻ���"/><br/>
            <input type="submit" value="��һ��"/><br/>
        </form>
    </div>
</div>
</body>
</html>