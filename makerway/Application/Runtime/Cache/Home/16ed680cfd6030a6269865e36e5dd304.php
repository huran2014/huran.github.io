<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/guest.css">
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
            <!--<span class="username-list">-->
            <!--&lt;!&ndash;<ul>&ndash;&gt;-->
            <!--&lt;!&ndash;<li class="register"><a href="<?php echo U(register);?>">注册</a></li>&ndash;&gt;-->
            <!--&lt;!&ndash;<li class="login"><a href="<?php echo U(login);?>">登录</a></li>&ndash;&gt;-->
            <!--&lt;!&ndash;</ul>&ndash;&gt;-->
            <!--</span>-->
            <span class="username-c">hello <span class="username-d"><a href="<?php echo U(person);?>"><?php echo cookie('username')?></a></span></span>
        </div>

        <!--<div class="view"></div>-->
    </div>
    <div class="main">
        <div class="classify">
            <div class="classify-title"><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(index);?>">MakerWay</a>><?php echo $user['username']?>的个人中心</div>
        </div>
        <div class="main-left">
            <div class="main-left-person">
                <div class="main-img">
                    <img /><br/>
                    <span class="username"><?php echo $user['username']?></span>
                </div>
                <div class="main-ul">
                    <ul>
                        <li><?php echo ($integral); ?><br/><br/>积分</li>
                        <li>分享达人<br/><br/>等级</li>
                        <li><?php echo ($booknum); ?>本<br/><br/>分享数</li>
                    </ul>
                    <div class="main-a"><a href=""><input class="sharebtn" type="button" value="加为好友"/></a></div>
                </div>
            </div>
        </div>
        <div class="main-right">
            <div class="navigate">
                <ul>
                    <li>Makerway</li>
                    <li class="border">书架</li>
                    <li>书签</li>
                    <li>好友</li>
                </ul>
            </div>
            <div class="subnavigate">
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
                                        <p>会被<?php echo $user['username']?>青睐呢</p>
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
                                        <p><span class='keeper' ><?php echo $user['username']?></span>借走了《<?php echo ($item['booklog']['title']); ?>》</p>
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
                                        <p><span class='keeper' ><?php echo $user['username']?></span>预约了《<?php echo ($item['booklog']['title']); ?>》</p>
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
                                        <p><span class='keeper' ><?php echo $user['username']?></span>续借了《<?php echo ($item['booklog']['title']); ?>》</p>
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
                                        <p><span class='keeper' ><?php echo $user['username']?></span>归还了《<?php echo ($item['booklog']['title']); ?>》</p>
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
                                        <h2><?php echo $user['username']?></h2>
                                        <p>来到了Makerway这个奇幻漂流世界</p>
                                        <p>可以尽情畅游书海</p>
                                        <span class="cd-date"><?php echo ($book['contribute_time']); ?></span>
                                    </div>

                                </div>
                            </section>
                        </div>
                    </div>
                </div>
                <div style="display:block;">
                    <ul class="bookmessage">
                        <li><a href="<?php echo U(guestPersonshare);?>">共享书籍</a></li>
                        <li class="activecolor">借阅信息</li>
                        <li><a href="<?php echo U(guestAttention);?>">关注</a></li>
                    </ul>
                    <div class="mybook" >
                        <div class="submybook"  style="display: none;">
                            <ul>
                                <?php if(is_array($mybooks)): foreach($mybooks as $key=>$item): ?><li>
                                        <div class="main-body-li">
                                            <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                            <span class="name">书名：<?php echo $item['bookinfo']['title']?></span>
                                            <span class="author">标签：<?php echo ($item['bookinfo']['tags']); ?></span>
                                            <span class="favour">创建时间：<?php echo ($item['contribute_time']); ?></span>
                                            <span class="comment">书本状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '可预约';if($item['status']==5) echo '可预约'?></span>
                                            <input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>
                                        </div>
                                    </li><?php endforeach; endif; ?>
                                <span class="fenpage"><?php echo ($page); ?></span>
                            </ul>
                        </div>
                        <div class="submybook">
                            <ul class="dosomething">
                                <li style="cursor: pointer"><a href="<?php echo U(guestKeeps);?>">阅读中</a></li>
                                <li style="cursor: pointer"><a href="<?php echo U(guestOrder);?>">预约中</a></li>
                                <li class="activecolor" style="cursor: pointer"><a href="<?php echo U(guestBookkeeps);?>">保管中</a></li>
                            </ul>
                            <div class="doing">
                                <div style="display: none;">
                                    <ul>
                                        <?php if(is_array($mykeeps)): foreach($mykeeps as $key=>$item): ?><li>
                                                <div class="main-body-li" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
                                                    <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                                    <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                                    <span class="author">标签：<?php echo ($item['bookinfo']['tags']); ?>：</span>
                                                    <span class="favour">创建时间：<?php echo ($item['contribute_time']); ?></span>
                                                    <span class="comment">书本状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '可预约';if($item['status']==5) echo '可预约'?></span>
                                                    <!--<input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>-->
                                                </div>
                                            </li><?php endforeach; endif; ?>
                                        <span class="fenpage"><?php echo ($page); ?></span>
                                    </ul>
                                </div>
                                <div style="display: none;">
                                    <ul>
                                        <?php if(is_array($mykeeps)): foreach($mykeeps as $key=>$item): ?><li>
                                                <div class="main-body-li" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
                                                    <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                                    <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                                    <span class="author">标签：<?php echo ($item['bookinfo']['tags']); ?></span>
                                                    <span class="favour">创建时间：<?php echo ($item['contribute_time']); ?></span>
                                                    <span class="comment">书本状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '可预约';if($item['status']==5) echo '可预约'?></span>
                                                    <!--<input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>-->
                                                </div>
                                            </li><?php endforeach; endif; ?>
                                        <span class="fenpage"><?php echo ($page); ?></span>
                                        <!--<li>-->
                                        <!--<div class="main-body-li">-->
                                        <!--<img src=""/>-->
                                        <!--<span class="name">书名3：<?php echo $item['bookinfo']['title']?></span>-->
                                        <!--<span class="author">类别：</span>-->
                                        <!--<span class="favour">创建时间：</span>-->
                                        <!--<span class="comment">书本状态：</span>-->
                                        <!--<input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>-->
                                        <!--</div>-->
                                        <!--</li>-->
                                    </ul>
                                </div>
                                <div style="display: block;">
                                    <ul>
                                        <?php if(is_array($mykeeps)): foreach($mykeeps as $key=>$item): ?><li>
                                                <div class="main-body-li" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer">
                                                    <img src="<?php echo ($item['bookinfo']['image']); ?>"/>
                                                    <span class="name">《<?php echo $item['bookinfo']['title']?>》</span>
                                                    <span class="author">标签：<?php echo ($item['bookinfo']['tags']); ?></span>
                                                    <span class="favour">还书时间：<?php echo ($item['booklog']['end_time']); ?></span>
                                                    <span class="comment">书本状态：<?php if($item['status']==1) echo '可借阅'; if($item['status']==2) echo '已借出';if($item['status']==3) echo '可借阅';if($item['status']==4) echo '可预约';if($item['status']==5) echo '可预约'?></span>
                                                    <!--<input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>-->
                                                </div>
                                            </li><?php endforeach; endif; ?>
                                        <span class="fenpage"><?php echo ($page); ?></span>
                                        <!--<li>-->
                                        <!--<div class="main-body-li">-->
                                        <!--<img src=""/>-->
                                        <!--<span class="name">书名3：<?php echo $item['bookinfo']['title']?></span>-->
                                        <!--<span class="author">类别：</span>-->
                                        <!--<span class="favour">创建时间：</span>-->
                                        <!--<span class="comment">书本状态：</span>-->
                                        <!--<input type="button" value="借阅历史" onclick="document.location='<?php echo U(bookInfo,array('bookid'=>$item['book_id']));?>'" style="cursor:pointer"/>-->
                                        <!--</div>-->
                                        <!--</li>-->
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div style="display: none">
                            <ul style="height: 10px;clear: both;"></ul>
                            <div class="my-pass"></div>
                        </div>
                    </div>
                </div>
                <div class="booktags" style="display: none;">
                    <ul>
                        <li class="activecolor">书签</li>
                        <li>朋友的书签</li>
                        <li>热门书签</li>
                    </ul>
                    <div class="tags">
                        <div style="display: block">
                            <ul style="height: 10px;clear: both;"></ul>
                            <div class="my-pass"></div>
                        </div>
                        <div style="display: none">
                            <ul style="height: 10px;clear: both;"></ul>
                            <div class="my-pass"></div>
                        </div>
                        <div style="display: none">
                            <ul style="height: 10px;clear: both;"></ul>
                            <div class="my-pass"></div>
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
</body>
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-68503572-4', 'auto');
    ga('send', 'pageview');

</script>
<script type="text/javascript" src="/passon/Public/js/person.js"></script>
<script type="text/javascript" src="/passon/Public/js/labelsearch.js"></script>
</html>