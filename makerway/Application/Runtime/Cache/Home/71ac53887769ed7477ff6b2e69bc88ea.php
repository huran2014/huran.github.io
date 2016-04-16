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
                <span class="title-m"><span class="big">P</span><span class="title-top">ASS ON</span><span class="title-bottom">P2P社交图书馆</span></span>
                <span class="title-main"><a href="<?php echo U(index);?>">首页</a></span>
                <span class="title-squre"><a href="<?php echo U(squre);?>">广场</a></span>
                <span class="title-share"><a href="<?php echo U(share);?>">共享库</a></span>
            </div>
            <div class="div-search">
                <form name="val" action="<?php echo U(tagsearch);?>" method="post">
                    <input  class="search" name="search" type="text" placeholder="分类？" />
                    <div class="search-a">
                        <input type="image" src="/passon/Public/images/search.png" name="img">
                        <!--<input type="submit" value="submit"/>-->
                        <!--<img src="/passon/Public/images/search.png"/>-->
                    </div>
                </form>
                <span>热门搜索：</span>
                <span>游戏化思维</span>
                <span>PHP开发宝典</span>
                <span>JAVA编程思想</span>
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
            <span class="username-c">hello <span class="username-d"><a href="<?php echo U(person);?>"><?php echo cookie('username')?></a></span></span>
        </div>
        <!--<div class="classify">-->
        <!--<div class="classify-title">MakerWay>Pass On>共享库</div>-->
        <!--</div>-->
        <!--<div class="view"></div>-->
    </div>
    <div class="main">
        <div class="classify">
            <div class="classify-title">MakerWay>Pass On>分类搜索</div>
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
                                <form name="val" action="<?php echo U(tagsearch);?>" method="post">
                                    <input  class="search1" name="search" type="text" placeholder="分类？" />
                                    <div class="search-b">
                                        <input type="image" src="/passon/Public/images/search.png" name="img">
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="main-body-ul">
                            <ul>
                                <?php if(is_array($book)): foreach($book as $key=>$item): ?><li>
                                        <div class="main-body-li">
                                            <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                            <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                            <span class="author">共享者：<?php echo $item['owner_name']?></span>
                                            <span class="favour">点赞数</span>
                                            <span>状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅'?>，我漂流在<?php echo ($item['keeper_name']); ?>那儿</span>
                                            <input type="button" value="<?php if($item['status']==1) echo '借&nbsp;&nbsp;&nbsp;&nbsp;阅'; if($item['status']==2) echo '预约';if($item['status']==3) echo '借阅'?>" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>
                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="<?php if($item['status']==1) echo '借&nbsp;&nbsp;&nbsp;&nbsp;阅'; if($item['status']==2) echo '预约';if($item['status']==3) echo '借阅'?>" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>
                                        </div>
                                    </li><?php endforeach; endif; ?>
                            </ul>
                            <span class="fenpage"><?php echo ($page); ?></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="main-body-right">
                <div class="right-main">
                    <div class="main-body-hot">
                        <span class="font-weight"><img src="/passon/Public/images/crown.png"/><span class="share-list">相&nbsp;关&nbsp;搜&nbsp;索</span></span>
                        <ul>
                            <li>
                                <span class="hot-name"><span class="red">1</span>书名</span>
                                <span class="hot-subscribe">10本</span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="red">2</span>书名</span>
                                <span class="hot-subscribe">10本</span>
                            </li>
                            <li>
                                <span class="hot-name"><span class="red">3</span>书名</span>
                                <span class="hot-subscribe">10本</span>
                            </li>
                            <li>
                                <span class="hot-name"><span>4</span>书名</span>
                                <span class="hot-subscribe">10本</span>
                            </li>
                            <li>
                                <span class="hot-name"><span class>5</span>书名</span>
                                <span class="hot-subscribe">10本</span>
                            </li>
                            <li>
                                <span class="hot-name"><span>6</span>书名</span>
                                <span class="hot-subscribe">10本</span>
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
        <div class="pass">Pass On Surprise You</div>
        <span>联系邮箱：pass.on@foxmail.com</span>
        <div class="footer-img">
            <img src="/passon/Public/images/logo.png"/>
        </div>
    </div>
</div>
</body>
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