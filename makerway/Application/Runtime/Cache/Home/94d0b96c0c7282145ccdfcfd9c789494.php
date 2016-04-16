<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html"; charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/login.css">
</head>
<body>
<div class="header">
    <a></a>
</div>
<div class="main">
    <div class="main-m">
        <div class="title">登录</div>
        <div>
            <form class="form" name="form"  action="<?php echo U(shareLogin_user);?>" method="post" onsubmit="return check()">
                <label class="font-size">用户名：</label><input class="user input1" name="user" type="user" placeholder="手机号/用户名" /><br/>
                <label class="font-size">密&nbsp;&nbsp;&nbsp;码：</label><input class="passwd input1" name="password" type="password" placeholder="密码" /><br/>
                <input class="checkbox" type="checkbox"/>自动登录
                <a class="forget" href="<?php echo U(forget);?>">忘记密码?>></a>
                <input class="btn" name="submit" type="submit" value="登录"/>
                <div class="main-bottom">
                    <span class="no-user">还没有账号？</span>
                    <a class="register" href="<?php echo U(shareRegister);?>">免费注册</a>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="footer"></div>
</body>
<script type="text/javascript" src="/passon/Public/js/login.js"></script>
</html>