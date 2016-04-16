<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phoneborrow.css">
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
            <form class="form-body" action="<?php echo U(phonedb);?>" method="post">
                <label class="time">借阅时间</label><br/>
                <div class="div-width"><input id="time1" name="time" type="radio" value="3" checked="checked"/><label for="time1">三天</label></div>
                <div class="div-width"><input id="time2" name="time" type="radio" value="7"/><label for="time2">一个星期</label></div>
                <div class="div-width"><input id="time3" name="time" type="radio" value="14"/><label for="time3">两个星期</label></div>
                <div class="div-width"><input id="time4" name="time" type="radio" value="30"/><label for="time4">一个月</label></div>
                <div class="damage">书本交接</div>
                <label class="da-title">请评价一下上一位阅读者吧^-^</label><br/>
                <input id="damage1" class="main-left" name="damage" type="radio" value="1"/><label for="damage1">书本完好，为爱书之人点赞</label><br/>
                <input id="damage2" class="main-left" name="damage" type="radio" value="0"/><label for="damage2">略有瑕疵，再接再厉</label><br/>
                <label class="main-left">其他：</label><input name="damage_c" type="text"/><br/>
                <div class="describe"><input class="describe-button" type="submit" value="借阅"/></div>
                <label class="exit"><a href="<?php echo U(phoneExit);?>">取消</a></label>
            </form>
        </div>
    </div>
</div>
</body>
</html>