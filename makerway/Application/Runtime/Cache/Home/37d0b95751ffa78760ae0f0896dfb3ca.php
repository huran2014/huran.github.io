<?php if (!defined('THINK_PATH')) exit();?><!DOCTYPE html>
<html lang="en">
<head>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <title>book</title>
    <link rel="stylesheet" type="text/css" href="/passon/Public/css/book.css">
    <script type="text/javascript" src="/passon/Public/js/jquery-1.11.1.min.js"></script>
</head>
</head>
<body onload="initEvent()">
<div class="main-tan"></div>
<div class="tan-message">
    <div>
        <p>现在我在 <?php echo ($book['keeper_name']); ?> 那儿</p>
        <p>联系方式： <?php echo ($user['phone']); ?></p>
        <p>常在地址： <?php echo ($user['addr']); ?></p>
        <input class="tan-button" type="button" value="确定"/>
    </div>
</div>
<?php if ($orderflag == 0): ?>
<div class="order-message">
    <div>
        <p>您是第 <?php echo ($message['order_num']+1); ?> 位预约者</p>
        <p>预计 <?php echo ($booklogmessage['end_time']); ?> 可以借阅本书！</p>
        <input class="order-button" type="button" value="确定"/>
        <input type="button" style="display: none" value=<?php echo ($book['book_id']); ?> />
    </div>
</div>
<?php elseif ($orderflag == 1): ?>
<div class="order-message">
    <div>
        <p>您已经预约过此书，请耐心等待！</p>
        <input class="tan-button" type="button" value="确定"/>
    </div>
</div>
<?php endif ?>
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
             <span class="username-list">
                    <ul>
                        <li class="register"><a href="<?php echo U(commentRegister,array('book_id'=>$book['book_id']));?>">注册</a></li>
                        <li class="login"><a href="<?php echo U(commentLogin,array('book_id'=>$book['book_id']));?>">登录</a></li>
                    </ul>
                </span>
            <span class="username-c"><span class="username-d"><a href="<?php echo U(person);?>"><?php echo ($_COOKIE['username']); ?>的个人空间</a></span><span class="username-d"><a href="<?php echo U(logout);?>">注销</a></span></span>
        </div>
    </div>
    <div class="main">
        <div class="classify">
            <div class="classify-title"><a style="cursor: pointer;color:#000;text-decoration: none" href="<?php echo U(index);?>">MakerWay</a>>《<?php echo $bookin['title']?>》</div>
        </div>
        <div class="main-left">
            <div class="left-body">
                <span class="main-title">《<?php echo $bookin['title']?>》 </span>
                <img src="<?php echo ($bookin['image']); ?>"/>
                <?php if ($book['con_name']!=''): ?>
                <span>分享者:<?php echo ($book['con_name']); ?></span>
                <?php elseif ($book['con_name']=='' && $user['groupflag'] == 1): ?>
                <span class="groupmessage">分享者:<?php echo ($book['owner_name']); ?></span>
                <span style="display: none"><?php echo ($user['groupid']); ?></span>
                <?php elseif ($book['con_name']=='' && $user['groupflag'] == 0): ?>
                <span class="usermessage">分享者:<?php echo ($book['owner_name']); ?></span>
                <?php endif ?>
                <span>点赞数</span>
                <!--<span>评论数</span>-->
                <span>状态：<?php if($book['status']==1) echo '可借阅'; if($book['status']==2) echo '已借出';if($book['status']==3) echo '可借阅';if($book['status']==4) echo '已借出';if($book['status']==5) echo '已借出'?>，我漂流在<?php echo ($book['keeper_name']); ?>那儿</span>
                <input class="main-btn" type="button" value="<?php if($result==1) echo '已关注'; if($result==0) echo '关注';?>" />
                <input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($bookin['book_id']); ?> />
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input class="behaviour" type="button" value="<?php if($book['status']==1) echo '借阅'; if($book['status']==2) echo '预约';if($book['status']==3) echo '借阅';if($book['status']==4) echo '预约';if($book['status']==5) echo '预约'?>" style="cursor:pointer"/>
            </div>
        </div>
        <div class="main-right">
            <div class="right-body">
                <div class="main-navigate">
                    <ul>
                        <li>简介</li>
                        <li>漂流记</li>
                        <li class="border">书友说</li>
                        <li>相关推荐</li>
                    </ul>
                </div>
                <div class="body-message">
                    <div class="book-message" style="display: none">
                        <div>
                            <div class="book-main">
                                <span class="book-info">【基本信息】</span>
                            </div>
                            <div class="book-mainsessage">
                                <span>标签：<?php echo ($bookin['tags']); ?></span>
                                <span>作者：<?php echo ($bookin['author']); ?></span>
                                <span>副标题：<?php echo ($bookinfo['rating']['subtitle']); ?></span>
                                <span>原作名：</span>
                                <span>ISBN：<?php echo ($bookin['isbn']); ?></span>
                                <span>出版社：<?php echo ($bookin['publisher']); ?></span>
                                <span>出版时间：<?php echo ($bookinfo['pubdate']); ?></span>
                            </div>
                        </div>
                        <div>
                            <div class="book-main">
                                <span class="book-info">【作者】</span>
                            </div>
                            <div class="book-mainsessage">
                                <span><?php echo ($bookinfo['author_intro']); ?></span>
                            </div>
                        </div>
                        <div>
                            <div class="book-main">
                                <span class="book-info">【内容】</span>
                            </div>
                            <div class="book-mainsessage">
                                <span><?php echo ($bookinfo['summary']); ?></span>
                            </div>
                        </div>
                        <div>
                            <div class="book-main">
                                <span class="book-info">【目录】</span>
                            </div>
                            <div class="book-mainsessage">
                                <span><?php echo $newstr = str_replace("\n","<br/>",$bookinfo['catalog'])?></span>
                            </div>
                        </div>
                    </div>
                    <div class="read-history" >
                        <!--<ul>-->
                        <!--&lt;!&ndash;<li>xx借阅了XX</li>&ndash;&gt;-->
                        <!--&lt;!&ndash;<li>xx借阅了XX</li>&ndash;&gt;-->
                        <!--&lt;!&ndash;<li>xx借阅了XX</li>&ndash;&gt;-->
                        <!--<?php if(is_array($booklist)): foreach($booklist as $key=>$item): ?>-->
                        <!--<li>-->
                        <!--在<?php echo ($item['contribute_time']); ?>被<?php echo $item['keeper_name']?> <?php if($item['status']==1) echo "创建"?> <?php if($item['status']==2) echo "借阅"?> <?php if($item['status']==3) echo "归还"?>-->
                        <!--</li>-->
                        <!--<?php endforeach; endif; ?>-->
                        <!--</ul>-->
                        <div class="book_life_experience_p">
                            <section id="cd-timeline" class="cd-container">
                                <div class="cd-timeline-block">
                                    <div class="cd-timeline-img cd-picture">
                                        <img src="/passon/Public/images/logo.png" alt="Picture">
                                    </div>
                                    <div class="cd-timeline-content">
                                        <h2>未来</h2>
                                        <p>我会接着漂流去哪儿... ...</p>
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
                                        <p><span class='keeper' ><?php echo ($item['keeper_name']); ?></span>借走了我</p>
                                        <p><span class='keeper' ><?php echo ($item['keeper_name']); ?></span>，将在<?php echo ($item['end_time']); ?>还书</p>
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
                                        <p><span class='keeper' ><?php echo ($item['order_name']); ?></span>预订了我</p>
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
                                        <p><span class='keeper' ><?php echo ($item['keeper_name']); ?></span>续借了我</p>
                                        <p><span class='keeper' ><?php echo ($item['keeper_name']); ?></span>，将在<?php echo ($item['end_time']); ?>还书</p>
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
                                        <p><span class='keeper' ><?php echo ($item['keeper_name']); ?></span>还书了</p>
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
                                        <h2>我出生了</h2>
                                        <p>来到了Makerway这个奇幻漂流世界</p>
                                        <?php if ($book['con_name']!=''): ?>
                                        <p>给我生命的是 <span class='keeper' ><?php echo ($book['con_name']); ?></span></p>
                                        <?php else: ?>
                                        <p>给我生命的是 <span class='keeper' ><?php echo ($book['owner_name']); ?></span></p>
                                        <?php endif ?>
                                        <span class="cd-date"><?php echo ($book['contribute_time']); ?></span>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
                    <div class="comment" style="display: block">
                        <div class="book-comment">
                            <div class="comment-num"><?php echo ($num+0); ?>条书评</div>
                            <div class="comment-message">写书评</div>
                        </div>
                        <div class="comment-comment">
                            <div class="do-comment">
                                <div class="comment-title">
                                    <span>给这本书打个分吧!(可选)</span>
                                    <?php if ($flag1 == 0): ?>
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
                                    <input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($book['book_id']); ?> />
                                    <?php endif ?>
                                    <?php if ($flag1 == 1): ?>
                                    <span>您给这本书打了<?php echo ($fen['fen']); ?>分</span>
                                    <?php endif ?>
                                </div>
                                <div class="comment-top">书评内容：</div>
                                <form name="form" action="<?php echo U(bookcomment);?>" method="post" onsubmit="return check()">
                                    <div class="textarea">
                                        <textarea class="input_text" name="textarea"></textarea>
                                    </div>
                                    <div class="comment-button">
                                        <span></span>
                                        <input type="submit" name="submit" value="发表" />
                                        <input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($book['book_id']); ?> />
                                        <input class="exit" type="button" name="button" value="取消" />
                                    </div>
                                </form>
                            </div>
                            <ul>
                                <?php if(is_array($bookcomment)): foreach($bookcomment as $key=>$item): ?><li>
                                        <div class="main-bookcomment">
                                            <div><?php echo ($item['username']); ?></div>
                                            <div class="color">发布于<?php echo ($item['time']); ?></div>
                                            <div><?php echo ($item['comment']); ?></div>
                                            <span class="comment-span"><span class="comment-spannum"><?php echo ($item['commentnum']['comment_zan']+0)?></span><span class="comment-zannum" title="<?php if($item['commentzan']['flag']==1) echo '取消赞同'; elseif($item['commentzan']['flag']==0) echo '';?>">人赞同</span></span>
                                            <input class="exit" style="display: none" type="text" name="comment" value=<?php echo ($item['id']); ?> />
                                            <!--<input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($book['book_id']); ?> />-->
                                            <span class="com-n"><?php echo ($item['commentnum']['comment_num']+0)?>条评论</span>
                                            <!--<span>人分享</span>-->
                                        </div>
                                        <div class="comment-comment-ul">
                                            <ul>
                                                <?php foreach ($item['comment1'] as $item1): ?>
                                                <li>
                                                    <div class="main-comment-comment">
                                                        <img src=""/>
                                                        <span class="c-n"><?php echo ($item1['username']); ?></span>
                                                        <span class="c-c"><?php echo ($item1['comment']); ?></span>
                                                        <span class="c-time color"><?php echo ($item1['time']); ?></span>
                                                    </div>
                                                </li>
                                                <?php endforeach ?>
                                                <form name="form" action="<?php echo U(cComment);?>" method="post" onsubmit="return check1()">
                                                    <textarea class="write" name="input" placeholder="写下你的评论"></textarea><br/>
                                                    <input class="write-exit" type="button" name="exit" value="取消" />
                                                    <input class="write-button" type="submit" name="submit" value="发表" />
                                                    <input class="exit" style="display: none" type="text" name="comment" value=<?php echo ($item['id']); ?> />
                                                    <input class="exit" style="display: none" type="text" name="bookid" value=<?php echo ($book['book_id']); ?> />
                                                </form>
                                            </ul>
                                        </div>
                                    </li><?php endforeach; endif; ?>
                            </ul>
                        </div>
                    </div>
                    <div style="display: none">
                        <div class="book-more-title">分享过此书的书友还分享过</div>
                        <div class="book-img-list">
                            <ul class="main-body-ul">
                                <?php if(is_array($bookinfo2)): foreach($bookinfo2 as $key=>$item): ?><li>
                                        <div class="main-body-li">
                                            <img src="<?php echo ($item['image']); ?>"/>
                                            <span class="name">《<?php echo ($item['title']); ?>》</span>
                                        </div>
                                    </li><?php endforeach; endif; ?>
                            </ul>
                        </div>
                    </div>
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
<script type="text/javascript" src="/passon/Public/js/book.js"></script>
<script type="text/javascript" src="/passon/Public/js/guest.js"></script>
<script type="text/javascript" src="/passon/Public/js/bookcheck.js"></script>
<script type="text/javascript" src="/passon/Public/js/attention.js"></script>
<script type="text/javascript" src="/passon/Public/js/labelsearch.js"></script>
</html>