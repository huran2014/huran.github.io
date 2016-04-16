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
        <!--action待定-->
        <form action="<?php echo U(phoneNext);?>" method="post">
            <input type="text" placeholder="手机号"/><br/>
            <input type="submit" value="下一步"/><br/>
        </form>
    </div>
</div>
</body>
</html>