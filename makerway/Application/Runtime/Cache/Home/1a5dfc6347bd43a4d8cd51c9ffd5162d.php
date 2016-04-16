<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>群组中心</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/guestGroup.css">
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
            <span class="username-list">
                    <ul>
                        <li class="register"><a href="<?php echo U(shareRegister);?>">注册</a></li>
                        <li class="login"><a href="<?php echo U(shareLogin);?>">登录</a></li>
                    </ul>
                </span>
            <span class="username-c"><span class="username-d"><a href="<?php echo U(person);?>"><?php echo ($_COOKIE['username']); ?>的个人空间</a></span><span class="username-d"><a href="<?php echo U(logout);?>">注销</a></span></span>
        </div>
    </div>
    <div class="main">
        <div class="classify">
            <div class="classify-title"><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(index);?>">MakerWay</a>>群组中心</div>
        </div>
        <div class="main-left">
            <div class="main-left-person">
                <div class="main-img">
                    <img /><br/>
                    <span class="username"><?php echo ($result['name']); ?></span>
                </div>
                <div class="main-ul">
                    <span>分类：<?php echo ($result['classify']); ?></span>
                    <span>创建时间：<?php echo ($result['date']); ?></span>
                    <span>共享书本：<?php echo ($bookcount); ?></span>
                    <span>成员：<?php echo ($count); ?></span>
                    <span>活动：2</span>
                    <?php if ($flag == 1): ?>
                    <div class="main-a"><a href="<?php echo U(groupcontribute,array('groupid'=>$groupid));?>"><input class="sharebtn" type="button" value="我要分享"/></a></div>
                    <?php elseif ($flag ==0): ?>
                    <div class="main-a"><a href="<?php echo U();?>"><input class="sharebtn" type="button" value="加入群组"/></a></div>
                    <?php endif ?>
                    <span>群组简介：<?php echo ($result['summary']); ?></span>
                </div>
            </div>
        </div>
        <div class="main-right">
            <div class="navigate">
                <ul>
                    <li class="border">联系方式</li>
                    <li>动态</li>
                    <li>书架</li>
                    <li>组员</li>
                    <!--<li>我的读书小组</li>-->
                    <li>公告</li>
                    <li>讨论区</li>
                </ul>
            </div>
            <div class="subnavigate">
                <div class="message"  style="display: block;">
                    <ul style="height: 10px;clear: both;"></ul>
                    <div class="my-pass">
                        <div class="second-navigate">
                            <div class="connect-title"><img src="/passon/Public/images/administrator.png"/>管理员信息</div>
                            <div class="connect-body">
                                <span class="connect-left">联系人</span>
                                <span class="connect-right"><?php echo ($result['owner_name']); ?></span>
                            </div>
                            <div class="connect-body">
                                <span class="connect-left">电话</span>
                                <span class="connect-right"><?php echo ($result['phone']); ?></span>
                            </div>
                            <div class="connect-body">
                                <span class="connect-left">邮箱</span>
                                <span class="connect-right"><?php echo ($result['email']); ?></span>
                            </div>
                            <div class="connect-title"><img src="/passon/Public/images/details.png"/>书籍借阅</div>
                            <div class="connect-body">
                                <span class="connect-left">地址</span>
                                <span>
                                    <span class="connect-right"><?php echo ($result['address']); ?></span><br/>
                                    <!--<span class="connect-right">北京市海淀区西土城路10号北京邮电大学校医院斜对面零壹时光咖啡</span>-->
                                </span>
                            </div>
                            <div class="connect-body">
                                <span class="connect-left">时间</span>
                                <span class="connect-right">周一至周五9:30-17:00</span>
                            </div>
                            <div class="connect-body">
                                <span class="connect-left">备注</span>
                                <span class="connect-right">请直接前往所在地址借书，若我不在，烦请电话联系。</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="display: none">
                    <ul style="height: 10px;clear: both;"></ul>
                    <div class="my-pass">
                        <div class="book_life_experience_p">
                            <section id="cd-timeline" class="cd-container">
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>

                                    <div class="cd-timeline-content">
                                        <h2>哪一本书</h2>
                                        <p>会被我青睐呢</p>
                                        <span class="cd-date">future</span>
                                    </div>
                                </div>
                                <?php if ($booklog): ?>
                                <?php foreach ($booklog as $item): ?>
                                <?php if ($item['status']==2): ?>
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>

                                    <div class="cd-timeline-content">
                                        <h2>借</h2>
                                        <p><span class='keeper' >我</span>借走了《<?php echo ($item['booklog']['title']); ?>》</p>
                                        <p>将在<span class="keeper"><?php echo ($item['end_time']); ?></span>还书</p>
                                        <span class="cd-date"><?php echo ($item['start_time']); ?></span>
                                    </div>
                                </div>
                                <?php elseif ($item['status']==4): ?>
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>

                                    <div class="cd-timeline-content">
                                        <h2>预</h2>
                                        <p><span class='keeper' >我</span>预约了《<?php echo ($item['booklog']['title']); ?>》</p>
                                        <span class="cd-date"><?php echo ($item['start_time']); ?></span>
                                    </div>
                                </div>
                                <?php elseif ($item['status']==5): ?>
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>

                                    <div class="cd-timeline-content">
                                        <h2>续</h2>
                                        <p><span class='keeper' >我</span>续借了《<?php echo ($item['booklog']['title']); ?>》</p>
                                        <p>将在<span class='keeper' ><?php echo ($item['end_time']); ?></span>还书</p>
                                        <span class="cd-date"><?php echo ($item['start_time']); ?></span>
                                    </div>
                                </div>
                                <?php elseif ($item['status']==3): ?>
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>

                                    <div class="cd-timeline-content">
                                        <h2>还</h2>
                                        <p><span class='keeper' >我</span>归还了《<?php echo ($item['booklog']['title']); ?>》</p>
                                        <span class="cd-date"><?php echo ($item['end_time']); ?></span>
                                    </div>
                                </div>
                                <?php endif ?>
                                <?php endforeach ?>
                                <?php endif ?>
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>
                                    <div class="cd-timeline-content">
                                        <h2>我</h2>
                                        <p>来到了Makerway这个奇幻漂流世界</p>
                                        <p>可以尽情畅游书海</p>
                                        <span class="cd-date"><?php echo ($book['contribute_time']); ?></span>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
                <div style="display: none;">
                    <div class="message"  style="display: block;">
                        <ul style="height: 10px;clear: both;"></ul>
                        <div class="my-pass">
                            <div class="mybook" >
                                <div class="submybook" style="display: block;">
                                    <ul>
                                        <?php if(is_array($mybooks)): foreach($mybooks as $key=>$item): ?><li onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor: pointer">
                                                <div class="main-body-li">
                                                    <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                                    <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                                    <?php if ($item['con_name']!=''): ?>
                                                    <span>分享者:<?php echo ($item['con_name']); ?></span>
                                                    <?php else: ?>
                                                    <span>分享者:<?php echo ($item['owner_name']); ?></span>
                                                    <?php endif ?>
                                                    <span class="favour">点赞数</span>
                                                    <span class="status">状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '已借出';if($item['status']==5) echo '已借出'?>，我漂流在<?php echo ($item['keeper_name']); ?>那儿</span>
                                                    <input class="attention" type="submit" name="attention" value="<?php if($item['bookatten']==1) echo '已关注'; if($item['bookatten']==0) echo '关注';?>"/>
                                                    <input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($item['book_id']); ?> />
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="behaviour" type="button" value="<?php if($item['status']==1) echo '借阅'; if($item['status']==2) echo '预约';if($item['status']==3) echo '借阅';if($item['status']==4) echo '预约';if($item['status']==5) echo '预约'?>" style="cursor:pointer"/>
                                                </div>
                                                <div class="main-tan"></div>
                                                <div class="tan-message">
                                                    <div>
                                                        <p>现在我在 <?php echo ($item['keeper_name']); ?> 那儿</p>
                                                        <p>联系方式： <?php echo ($item['groupmessage']['phone']); ?></p>
                                                        <p>常在地址： <?php echo ($item['address']); ?></p>
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
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="booktags" style="display: none;">
                    <div class="tags">
                        <div style="display: block">
                            <ul style="height: 10px;clear: both;"></ul>
                            <div class="my-pass group-member">
                                <span>组员数：<?php echo ($count); ?></span>
                                <ul>
                                    <?php if(is_array($resultmember)): foreach($resultmember as $key=>$item): ?><li>
                                            <img />
                                            <span><?php echo ($item['username']); ?></span>
                                        </li><?php endforeach; endif; ?>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="display: none">
                    <ul style="height: 10px;clear: both;"></ul>
                    <div class="my-pass group-notice">
                        <div class="notice-title">
                            <ul>
                                <li class="backgroundColor2">全部</li>
                                <li class="backgroundColor1">热门</li>
                            </ul>
                            <span>发起活动</span>
                        </div>
                        <div class="notice-body">
                            <div>
                                <div>
                                    <ul style="display: block">
                                        <li class="notice-body-first">
                                    <span class="notice-body-left">
                                        <span>排序：</span>
                                        <span class="font-cursor font-color">最新发帖</span>
                                        <span class="font-cursor">最后发帖</span>
                                    </span>
                                    <span class="notice-body-right">
                                        <span>最后发表</span>
                                        <span>回复</span>
                                        <span>作者</span>
                                    </span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="">
                                    <ul>
                                        <li class="notice-number">
                                            <ul>
                                                <li class="notice-content" style="display: block">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">1/100</span>
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">1/100</span>
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="notice-number" style="display: none">
                                            <ul>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">2/100</span>
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">2/100</span>
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div style="display: none">
                                <div>
                                    <ul style="display: block">
                                        <li class="notice-body-first">
                                    <span class="notice-body-left">
                                        <span>排序：</span>
                                        <span class="font-cursor font-color">最新发帖</span>
                                        <span class="font-cursor">最后发帖</span>
                                    </span>
                                    <span class="notice-body-right">
                                        <span>最后发表</span>
                                        <span>回复</span>
                                        <span>作者</span>
                                    </span>
                                        </li>
                                    </ul>
                                </div>
                                <div>
                                    <ul>
                                        <li class="notice-number">
                                            <ul>
                                                <li class="notice-content" style="display: block">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">3/100</span>
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">3/100</span>
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="notice-number" style="display: none">
                                            <ul>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">4/100</span>
                                                 <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                                <li class="notice-content">
                                                    <span style="line-height: 37px;">【公告】</span>
                                                    <span>关于本小组第一次线下读书活动</span>
                                            <span class="notice-body-right">
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                                <span style="line-height: 37px;">4/100</span>
                                                <span>
                                                    <span>NewCZ</span><br/>
                                                    <span class="notice-content-date">2015-10-31</span>
                                                </span>
                                            </span>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="display: none">
                    <ul style="height: 10px;clear: both;"></ul>
                    <div class="my-pass"></div>
                </div>
            </div>
        </div>
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
<script type="text/javascript" src="/passon/Public/js/guestGroup.js"></script>
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