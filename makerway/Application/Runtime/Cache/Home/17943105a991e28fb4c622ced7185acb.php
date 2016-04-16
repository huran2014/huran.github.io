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
        <div class="main-img">
            <img src="<?php echo ($bookin['image']); ?>"/>
        </div>
        <div class="book-social">
            <!--action待定-->
            <!--<form action="<?php echo U(phoneNext);?>" method="post">-->
                <input type="button" value="借书" onclick="window.location.href='<?php echo U(phoneBorrow);?>'"/><br/>
                <input type="button" value="还书" onclick="window.location.href='<?php echo U(phoneReturn);?>'"/><br/>
                <input type="button" value="预约" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>
                <input type="button" value="续借" onclick="window.location.href='<?php echo U(phoneRenew);?>'"/>
                <!--<a>借书</a><br/>-->
                <!--<a>还书</a><br/>-->
                <!--<a>预约</a><br/>-->
                <!--<a>续借</a>-->
            <!--</form>-->
        </div>
    </div>
</body>
</html>