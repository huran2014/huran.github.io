$(function(){
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

    var $li = $('.main-navigate ul li');
    $li.click(function(){
        var index = $li.index(this);
        $(this).addClass("border").siblings().removeClass("border");
        $('.body-message > div').eq(index).show().siblings().hide();
    })
    var $btn = $('.comment-message');
    var $comment = $('.do-comment');
    var $exit = $('.exit');
    var $com = $('.com-n');
    var $writeexit = $('.write-exit');
    var $comm = $('.comment-comment-ul');
    $btn.click(function(){
        $comment.toggle();
    })
    $writeexit.click(function(){
        //$comment.toggle();
        var index = $writeexit.index(this);
        $comm.eq(index).hide();
    })
    $exit.click(function(){
        $comment.hide();
    })
    $com.click(function(){
        var index = $com.index(this);
        $comm.eq(index).toggle();
    })
    var $behaviour = $('.behaviour');
    $behaviour.click(function(){
        var $behaviourvalue = $(this).val();
        if ($behaviourvalue == '借阅') {
            //var index = $behaviour.index(this);
            $('.main-tan').show();
            $('.tan-message').show();
            event.stopPropagation();
            ///阻止点击阴影层跳转
            var $maintan = $('.main-tan');
            $maintan.click(function (event) {
                event.stopPropagation();
            });
            ///阻止点击白色文本框跳转
            var $tanmessage = $('.tan-message');
            $tanmessage.click(function (event) {
                event.stopPropagation();
            });
            ///点击确定按钮
            var $tanbutton = $('.tan-button');
            $tanbutton.click(function (event) {
                $('.main-tan').hide();
                $('.tan-message').hide();
                event.stopPropagation();
            })
        } else if ($behaviourvalue == '预约') {
            if (cookie_val) {
                //var index = $behaviour.index(this);
                $('.main-tan').show();
                $('.order-message').show();
                event.stopPropagation();
                ///阻止点击阴影层跳转
                var $maintan = $('.main-tan');
                $maintan.click(function (event) {
                    event.stopPropagation();
                });
                ///阻止点击白色文本框跳转
                var $tanmessage = $('.tan-message');
                $tanmessage.click(function (event) {
                    event.stopPropagation();
                });
                ///点击确定按钮
                var $orderbutton = $('.order-button');
                $orderbutton.click(function (event) {
                    var $book_id = $(this).next().val();
                    $.ajax({
                        url: 'http://www.makerway.space/makerway/index.php/Home/Index/shareorder',
                        type: 'POST',
                        datatype: 'jsonp',
                        data: {
                            book_id: $book_id
                        }
                    });
                    $('.main-tan').hide();
                    $('.order-message').hide();
                    event.stopPropagation();
                })
                var $tanbutton = $('.tan-button');
                $tanbutton.click(function (event) {
                    $('.main-tan').hide();
                    $('.order-message').hide();
                    event.stopPropagation();
                })
            } else {
                alert("您还没有登录!!");
                event.stopPropagation();
            }
        }
    })
    var $mainbtn = $(".main-btn");
    $mainbtn.click(function(){
        if(cookie_val){
            var $book_id = $(this).next().val();
            var $val = $(this).val();
            $.ajax({
                url: 'http://www.makerway.space/makerway/index.php/Home/Index/attention',
                type: 'POST',
                datatype: 'jsonp',
                data:{
                    book_id: $book_id,
                    val: $val
                }
            });
            if($(this).val()=="关注"){
                $(this).val("已关注");
            }else if($(this).val()=="已关注"){
                $(this).val("关注");
            }
        }else{
            alert("您还没有登录!!");
        }
    })
    var $commentspan = $(".comment-zannum");
    $commentspan.click(function(){
        var index = $commentspan.index(this);
        var $kk = $(this).attr("title");
        if(cookie_val){
                var $num = $('.comment-spannum').eq(index).text();

                var $comment_id = $('.comment-span').next().val();
                $.ajax({
                    url: 'http://www.makerway.space/makerway/index.php/Home/Index/commentnum',
                    type: 'POST',
                    datatype: 'jsonp',
                    data:{
                        comment_id: $comment_id,
                        num: Number($num),
                        val: $kk
                    }
                });
            if($kk==""){
                $(this).attr("title","取消赞同");
                $('.comment-spannum').eq(index).text(Number($num)+1);
            }else if($kk=="取消赞同"){
                $(this).attr("title","");
                $('.comment-spannum').eq(index).text(Number($num)-1);
            }
        }else{
            alert("您还没有登录!!");
        }
    })
    var $keeper = $('.keeper');
    $keeper.click(function(){
        var $keepername = $(this).text();
        $.ajax({
            //url: 'http://localhost:99/passon/index.php/Home/Index/commentnum',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                username: $keepername
            }
        });
    })
})