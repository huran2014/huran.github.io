<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>图书广场</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/squre.css">
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
</head>
<body>
    <div class="content">
        <div class="head">
            <div class="title">
                <span class="title-m">PASS ON</span>
                <span class="title-main"><a href="<?php echo U(index);?>">首页</a></span>
                <span class="title-squre"><a href="<?php echo U(squre);?>">广场</a></span>
                <span class="title-share"><a href="<?php echo U(share);?>">共享库</a></span>
                <div class="div-search">
                    <input  class="search" name="search" type="text" placeholder="你想找什么？" />
                    <div class="search-a">
                        <img src="/passon/Public/images/search.png" />
                    </div>
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
            <div class="carousel"></div>
            <div class="mark">
                <ul>
                    <li class="green">热门标签</li>
                    <li>PHP</li>
                    <li>移动开发</li>
                    <li>WEB开发</li>
                    <li>JavaScript</li>
                    <li>新手入门</li>
                    <li>前端开发</li>
                    <li>Python</li>
                    <li>Andriod</li>
                </ul>
            </div>
        </div>
        <div class="main">
            <div class="main-social">
                <div class="main-list">
                    <span class="main-title">随便看看</span>
                </div>
                <div class="main-main">
                    <ul>
                        <!--<?php if(is_array($booklist)): foreach($booklist as $key=>$item): ?>-->
                            <!--<li>-->
                                <!--<div class="main-body-li">-->
                                    <!--<img src="<?php echo ($item['bookinfo']['image']); ?>"/>-->
                                    <!--<span class="name">书名:<?php echo $s = iconv("UTF-8","GB2312",$item['bookinfo']['title'])?></span>-->
                                    <!--<span class="author">作者:</span>-->
                                    <!--<span class="favour">点赞数</span>-->
                                    <!--<span class="comment">评论数</span>-->
                                <!--</div>-->
                            <!--</li>-->
                        <!--<?php endforeach; endif; ?>-->
                        <?php if(is_array($booklist)): foreach($booklist as $key=>$item): ?><li>
                            <div class="main-li">
                                <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                <span class="main-li-title"><?php echo $s = iconv("UTF-8","GB2312",$item['bookinfo']['title'])?></span>
                                <span class="main-li-context">在<?php echo ($item['contribute_time']); ?>被<?php echo $s = iconv("UTF-8","GB2312",$item['keeper_name'])?> <?php if($item['status']==1) echo "创建"?> <?php if($item['status']==2) echo "借阅"?> <?php if($item['status']==3) echo "归还"?></span>
                            </div>
                        </li><?php endforeach; endif; ?>
                        <span class="fenpage"><?php echo ($page); ?></span>
                    </ul>
                </div>
            </div>
            <div class="drift">
                <div class="drift-main">
                    <span class="drift-title">>漂流信息</span>
                    <ul>
                        <li>
                            <img />
                            <span></span>
                        </li>
                        <li>
                            <img />
                            <span></span>
                        </li>
                    </ul>
                </div>
                <div class="team">
                    <span class="team-title">>热门小组</span>
                    <span class="team-img">
                        <ul class="team-ul">
                            <li>小组名：</li>
                            <li>创建时间：</li>
                            <li>人数：</li>
                        </ul>
                        <img />
                     </span>
                </div>
                <div>
                    <span class="more-title">>更多线上活动</span>
                </div>
                <div class="advertisement">
                    <img />
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