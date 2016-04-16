<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phonerenew.css">
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
            <form class="form-body" action="<?php echo U(pbookrenew);?>" method="post">
                <label class="time">续借时间</label><br/>
                <input id="time1" class="div-width" name="time" type="radio" value="3"/><label for="time1">三天</label><br/>
                <input id="time2" class="div-width" name="time" type="radio" value="5"/><label for="time2">五天</label><br/>
                <input id="time3" class="div-width" name="time" type="radio" value="10"/><label for="time3">十天</label><br/>
                <input id="time4" class="div-width" name="time" type="radio" value="15"/><label for="time4">十五天</label><br/>
                <div class="describe"><input class="describe-button" type="submit" value="续借"/></div>
                <label class="exit"><a href="<?php echo U(phoneExit);?>">取消</a></label>
            </form>
        </div>
    </div>
</div>
</body>
</html>