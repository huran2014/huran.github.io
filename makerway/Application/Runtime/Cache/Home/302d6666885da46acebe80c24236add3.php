<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phoneforget.css">
</head>
<body>
<div class="content">
    <!--<div class="title">-->
    <!--<div>-->
    <!--<span>Pass On</span>-->
    <!--</div>-->
    <!--</div>-->
    <div class="main">
        <div class="main-title"><span>忘记密码</span></div>
        <div class="main-form">
            <form action="<?php echo U(pForget);?>" method="post" onsubmit="return check()">
                <div class="book-input"><label>用户名：&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="username"/></div>
                <div class="book-input"><label>手机号：&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="text" name="phonenum"/></div>
                <div class="book-input"><label>密&nbsp;&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;&nbsp;&nbsp;</label><input type="password" name="password"/></div>
                <div class="book-input"><label>确认密码：</label><input type="password" name="rpassword"/></div>
                <div class="book-input2"><input type="submit" name="submit" value="修改"/></div>
            </form>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="/passon/Public/js/phoneforget.js"></script>
</html>