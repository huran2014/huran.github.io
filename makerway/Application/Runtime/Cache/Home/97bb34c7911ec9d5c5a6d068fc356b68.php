<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Pass On</title>
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/phonereturn.css">
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
            <form class="form-body" action="<?php echo U(pbookreturn);?>" method="post">
                <label class="time">给个评价吧?</label><br/>
                <span>给这本书打个分吧!(可选)</span>
                <span>
                    <ul id="star">
                        <li>☆</li>
                        <li>☆</li>
                        <li>☆</li>
                        <li>☆</li>
                        <li>☆</li>
                    </ul>
                </span>
                <span id="result"></span>
                <span class="result-num">分</span>
                <input class="exit" style="display: none" type="text" name="bookid" value=28 />
                <span>评价理由：</span>
                <div class="textarea">
                    <p>留下书评可以让更多人了解你哦，我们期待给您带来更多优秀书友的关注和交流！</p>
                    <textarea name="book_c" rows="5" cols="30" ></textarea>
                </div>
                <div class="describe"><input class="describe-button" type="submit" value="还书"/></div>
                <label class="exit"><a href="<?php echo U(phoneExit);?>">取消</a></label>
            </form>
        </div>
    </div>
</div>
</body>
    <script type="text/javascript" src="/passon/Public/js/phonereturn.js"></script>
    <script type="text/javascript" src="/passon/Public/js/bookreturn.js"></script>
</html>