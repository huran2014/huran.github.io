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
            <!--action����-->
            <!--<form action="<?php echo U(phoneNext);?>" method="post">-->
                <input type="button" value="����" onclick="window.location.href='<?php echo U(phoneBorrow);?>'"/><br/>
                <input type="button" value="����" onclick="window.location.href='<?php echo U(phoneReturn);?>'"/><br/>
                <input type="button" value="ԤԼ" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>
                <input type="button" value="����" onclick="window.location.href='<?php echo U(phoneRenew);?>'"/>
                <!--<a>����</a><br/>-->
                <!--<a>����</a><br/>-->
                <!--<a>ԤԼ</a><br/>-->
                <!--<a>����</a>-->
            <!--</form>-->
        </div>
    </div>
</body>
</html>