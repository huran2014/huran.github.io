<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phoneorder.css">
</head>
<body>
<div class="content">
    <!--<div class="title">-->
        <!--<div>-->
            <!--<span>Pass On</span>-->
        <!--</div>-->
    <!--</div>-->
    <div class="main">
        <div class="main-form">
            <!--action待定-->
            <form action="<?php echo U(pbookorder);?>" name="message" item="item">
                <div class="position">您是第<span class="color fontSize"><?php echo ($message['order_num']+1)?></span>位预约者</div>
                <div class="position">预计<span class="color"><?php echo $booklog['end_time']?></span>可以借阅本书！</div>
                <div class="describe"><input name="order" class="describe-button" type="submit" value="预约"/></div>
                <label class="exit"><a href="<?php echo U(phoneExit);?>">取消</a></label>
            </form>
        </div>
    </div>
</div>
</body>
</html>