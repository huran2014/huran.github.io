<?php if (!defined('THINK_PATH')) exit();?><!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <title>Makerway</title>
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
            <?php if ($book['status']==1 || $book['status']==3): ?>
                <input type="button" value="借书" onclick="window.location.href='<?php echo U(phoneBorrow);?>'"/><br/>
                <input type="button" value="预约" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>
            <?php endif ?>
            <?php if (($book['status']==2 || $book['status']==5) && $book['keeper_name']==$username): ?>
                <input type="button" value="还书" onclick="window.location.href='<?php echo U(phoneReturn);?>'"/><br/>
                <input type="button" value="续借" onclick="window.location.href='<?php echo U(phoneRenew);?>'"/>
            <?php endif ?>
            <?php if ($book['status']==2 && $book['keeper_name']!=$username): ?>
                <input type="button" value="预约" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>
            <?php endif ?>
            <?php if ($book['status']==5 && $book['keeper_name']!=$username): ?>
                <input type="button" value="预约" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>
            <?php endif ?>
                <!--<input type="button" value="借书" onclick="window.location.href='<?php echo U(phoneBorrow);?>'"/><br/>-->
                <!--<input type="button" value="还书" onclick="window.location.href='<?php echo U(phoneReturn);?>'"/><br/>-->
                <!--<input type="button" value="预约" onclick="window.location.href='<?php echo U(phoneOrder);?>'"/><br/>-->
                <!--<input type="button" value="续借" onclick="window.location.href='<?php echo U(phoneRenew);?>'"/>-->
                <!--<a>借书</a><br/>-->
                <!--<a>还书</a><br/>-->
                <!--<a>预约</a><br/>-->
                <!--<a>续借</a>-->
            <!--</form>-->
        </div>
    </div>
</body>
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-68503572-4', 'auto');
    ga('send', 'pageview');

</script>
</html>