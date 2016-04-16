<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/register.css">
    <title>注册</title>
</head>
<body>
<div class="header">
    <a></a>
</div>
<div class="main">
    <div class="main-m">
        <div class="title">
            <span>注册</span>
        </div>
        <div>
            <form class="form" name="form"  action="<?php echo U(mysqlUser);?>" method="post" onsubmit="return check()">
                <label>用户名：&nbsp;&nbsp;&nbsp;&nbsp;</label><input class="user input" name="user" type="text" placeholder="用户名"/><br/>
                <label>密&nbsp;&nbsp;&nbsp;码：&nbsp;&nbsp;&nbsp;&nbsp;</label><input class="passwd input" name="password" type="password" placeholder="长度为6-16"/><br/>
                <label>确认密码：</label><input class="rpasswd input" name="rpassword" type="password" placeholder="长度为6-16"/><br/>
                <label>手机号：&nbsp;&nbsp;&nbsp;&nbsp;</label><input class="phonenum input" name="phonenum" type="text"  placeholder="真实手机号，方便联系您"/><br/>
                <label>地&nbsp;&nbsp;&nbsp;址：&nbsp;&nbsp;&nbsp;&nbsp;</label><input class="phonenum input" name="address" type="text"  placeholder="常用地址，方便联系您"/><br/>
                <!--<label>验证码：</label><input class="checknum input input1" name="get" type="text"  />-->
                <!--<input class="btn-get" name="get" type="button" value="获取验证码"/>-->
                <input class="btn" name="submit" type="submit" value="注册" /><br/>
                <div class="main-bottom">
                    <span  class="no-user">已是图书漂流岛会员？</span>
                    <a class="register" href="<?php echo U(login);?>">登录</a>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="footer"></div>
</body>
<script type="text/javascript" src="/passon/Public/js/register.js"></script>
</html>