<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>搜索库</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/search.css">
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
</head>
<body>
<div class="content">
    <div class="head">
        <div class="title">
            <div class="title-left">
                <span class="title-m"><span class="big">M</span><span class="title-top">AKERWAY</span><span class="title-bottom">一起分享智慧</span></span>
                <span class="title-main"><a href="<?php echo U(index);?>">首页</a></span>
                <!--<span class="title-squre"><a href="<?php echo U(squre);?>">广场</a></span>-->
                <span class="title-share"><a href="<?php echo U(share);?>">共享库</a></span>
            </div>
            <div class="div-search">
                <form name="search" action="<?php echo U(search);?>" method="post">
                    <input  class="search" name="search" type="text" placeholder="书名/分类？" />
                    <div class="search-a">
                        <input type="image" src="/passon/Public/images/search.png" name="img">
                        <!--<input type="submit" value="submit"/>-->
                        <!--<img src="/passon/Public/images/search.png"/>-->
                    </div>
                </form>
                <span>热门搜索：</span>
                <span class="detail">
                    <span><a>数据库系统概念</a></span>
                    <span><a>CSS 3实战</a></span>
                    <span><a>操作系统概念</a></span>
                </span>
            </div>
            <!--<span>-->
            <!--<ul>-->
            <!--<li>注册</li>-->
            <!--<li>登录</li>-->
            <!--</ul>-->
            <!--</span>-->
                 <span class="username-list">
                    <ul>
                        <li class="register"><a href="<?php echo U(register);?>">注册</a></li>
                        <li class="login"><a href="<?php echo U(login);?>">登录</a></li>
                    </ul>
                </span>
            <span class="username-c"><span class="username-d"><a href="<?php echo U(person);?>"><?php echo ($_COOKIE['username']); ?>的个人空间</a></span><span class="username-d"><a href="<?php echo U(logout);?>">注销</a></span></span>
        </div>
        <!--<div class="classify">-->
        <!--<div class="classify-title">MakerWay>Pass On>共享库</div>-->
        <!--</div>-->
        <!--<div class="view"></div>-->
    </div>
    <div class="main">
        <div class="classify">
            <div class="classify-title"><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(index);?>">MakerWay</a>>搜索库</div>
        </div>
        <div class="main-body">
            <div>
                <div class="main-body-left">
                    <div class="main-ll">
                        <div class="main-title">
                            <div class="navigate">
                                <ul>
                                    <li>《<?php echo ($search); ?>》的搜索结果</li>
                                </ul>
                            </div>
                            <!--<div class="checkbox">-->
                                <!--<input type="checkbox"/><label>仅显示可借书籍</label>-->
                            <!--</div>-->
                            <div class="main-search">
                                <form name="search" action="<?php echo U(search);?>" method="post">
                                    <input  class="search1" name="search" type="text" placeholder="书名/分类？" />
                                    <div class="search-b">
                                        <input type="image" src="/passon/Public/images/search.png" name="img">
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="main-body-ul">
                            <?php if ($flag==1): ?>
                            <ul>
                                <?php if(is_array($book)): foreach($book as $key=>$item): ?><li onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor: pointer">
                                        <div class="main-body-li">
                                            <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                            <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                            <?php if ($item['con_name']!=''): ?>
                                            <span class="usermessage">分享者:<?php echo ($item['con_name']); ?></span>
                                            <?php elseif ($item['con_name']=='' && $item['bookuser']['groupflag'] == 1): ?>
                                            <span class="groupmessage">分享者:<?php echo ($item['owner_name']); ?></span>
                                            <span style="display: none"><?php echo ($item['bookuser']['groupid']); ?></span>
                                            <?php elseif ($item['con_name']=='' && $item['bookuser']['groupflag'] == 0): ?>
                                            <span class="usermessage">分享者:<?php echo ($item['owner_name']); ?></span>
                                            <?php endif ?>
                                            <span class="favour">点赞数</span>
                                            <span>状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '已借出';if($item['status']==5) echo '已借出'?>，我漂流在<?php echo ($item['keeper_name']); ?>那儿</span>
                                            <input class="attention" type="submit" name="attention" value="<?php if($item['bookatten']==1) echo '已关注'; if($item['bookatten']==0) echo '关注';?>"/>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="behaviour" type="button" value="<?php if($item['status']==1) echo '借阅'; if($item['status']==2) echo '预约';if($item['status']==3) echo '借阅';if($item['status']==4) echo '预约';if($item['status']==5) echo '预约'?>" style="cursor:pointer"/>
                                        </div>
                                        <div class="main-tan"></div>
                                        <div class="tan-message">
                                            <div>
                                                <p>现在我在 <?php echo ($item['bookuser']['username']); ?> 那儿</p>
                                                <p>联系方式： <?php echo ($item['bookuser']['phone']); ?></p>
                                                <p>常在地址： <?php echo ($item['bookuser']['addr']); ?></p>
                                                <input class="tan-button" type="button" value="确定"/>
                                            </div>
                                        </div>
                                        <?php if ($item['orderflag']==0): ?>
                                        <div class="order-message">
                                            <div>
                                                <p>您是第 <?php echo ($item['message']['order_num']+1); ?> 位预约者</p>
                                                <p>预计 <?php echo ($item['booklog']['end_time']); ?> 可以借阅本书！</p>
                                                <input class="order-button" type="button" value="确定"/>
                                                <input type="button" style="display: none" value=<?php echo ($item['book_id']); ?> />
                                            </div>
                                        </div>
                                        <?php elseif ($item['orderflag']==1): ?>
                                        <div class="order-message">
                                            <div>
                                                <p>您已经预约过此书，请耐心等待！</p>
                                                <input class="tan-button" type="button" value="确定"/>
                                            </div>
                                        </div>
                                        <?php endif ?>
                                    </li><?php endforeach; endif; ?>
                            </ul>
                            <span class="fenpage"><?php echo ($page); ?></span>
                            <?php elseif ($flag==0): ?>
                                <div class="search-result">
                                    <p>暂无结果哦</p>
                                </div>
                            <?php endif ?>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-body-right">
                <div class="right-main">
                    <div class="last-share">
                        <span class="font-weight"><img src="/passon/Public/images/crown.png"/><span class="share-list">相&nbsp;关&nbsp;搜&nbsp;索</span></span>
                        <ul>
                            <li>
                                <span class="hot-name"><span class="red marginleft">1</span><span class="pointer">《<?php echo ($bookhot[0]['title']); ?>》</span></span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="red marginleft">2</span><span class="pointer">《<?php echo ($bookhot[1]['title']); ?>》</span></span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="red marginleft">3</span><span class="pointer">《<?php echo ($bookhot[2]['title']); ?>》</span></span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="marginleft">4</span><span class="pointer">《<?php echo ($bookhot[3]['title']); ?>》</span></span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="marginleft">5</span><span class="pointer">《<?php echo ($bookhot[4]['title']); ?>》</span></span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="marginleft">6</span><span class="pointer">《<?php echo ($bookhot[5]['title']); ?>》</span></span>
                            </li>
                        </ul>
                    </div>
                    <!--<div class="last-share">-->
                        <!--<span class="font-weight1"><img src="/passon/Public/images/rocket.png"/><span class="share-list">热&nbsp;门&nbsp;书&nbsp;籍&nbsp;排&nbsp;行&nbsp;榜</span></span>-->
                        <!--<ul>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span class="yellow">1</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span class="yellow">2</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span class="yellow">3</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span>4</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span class>5</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                            <!--<li>-->
                                <!--<span class="hot-name"><span>6</span>书名</span>-->
                                <!--<span class="hot-subscribe">10本</span>-->
                            <!--</li>-->
                        <!--</ul>-->
                    <!--</div>-->
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <!--<div class="pass">Pass On Surprise You</div>-->
        <!--<span>联系邮箱：pass.on@foxmail.com</span>-->
        <!--<div class="footer-img">-->
            <!--<img src="/passon/Public/images/logo.png"/>-->
        <!--</div>-->
    </div>
</div>
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-68503572-4', 'auto');
    ga('send', 'pageview');

</script>
</body>
<script type="text/javascript" src="/passon/Public/js/share.js"></script>
<script type="text/javascript" src="/passon/Public/js/guest.js"></script>
<script type="text/javascript" src="/passon/Public/js/labelsearch.js"></script>
<script>
    var allcookies = document.cookie;
    function getCookie(cookie_name)
    {
        var allcookies = document.cookie;
        var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度
        if (cookie_pos != -1)
        {
// 把cookie_pos放在值的开始，只要给值加1即可。
            cookie_pos += cookie_name.length + 1;      //这里我自己试过，容易出问题，所以请大家参考的时候自己好好研究一下。。。
            var cookie_end = allcookies.indexOf(";", cookie_pos);
            if (cookie_end == -1)
            {
                cookie_end = allcookies.length;
            }
            var value = unescape(allcookies.substring(cookie_pos, cookie_end)); //这里就可以得到你想要的cookie的值了。。。
        }
        return value;
    }
    var cookie_val = getCookie("username");
    if(cookie_val){
        $(".username-list").hide();
        $(".username-c").show();

    }else{
        $(".username-list").show();
        $(".username-c").hide();
    }
</script>
</html>