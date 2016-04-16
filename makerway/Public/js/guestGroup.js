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
    //////关注
    var $li = $(".attention");
    $li.click(function(event){
        if(cookie_val){
            //var index = $li.index(this);
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
            event.stopPropagation();
        }else{
            alert("您还没有登录!!");
            event.stopPropagation();
        }
    })
    ////点击借阅和预约弹出信息
    var $behaviour = $(".behaviour");
    $behaviour.click(function(event) {
        var $behaviourvalue = $(this).val();
        if ($behaviourvalue == '借阅') {
            var index = $behaviour.index(this);
            $('.main-tan').eq(index).show();
            $('.tan-message').eq(index).show();
            event.stopPropagation();
            event.preventDefault();
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
                $('.main-tan').eq(index).hide();
                $('.tan-message').eq(index).hide();
                event.stopPropagation();
            })
        } else if ($behaviourvalue == '预约') {
            if(cookie_val){
                var index = $behaviour.index(this);
                $('.main-tan').eq(index).show();
                $('.order-message').eq(index).show();
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
                    $('.main-tan').eq(index).hide();
                    $('.order-message').eq(index).hide();
                    event.stopPropagation();
                })
                ///点击确定按钮
                var $tanbutton = $('.tan-button');
                $tanbutton.click(function (event) {
                    $('.main-tan').eq(index).hide();
                    $('.order-message').eq(index).hide();
                    event.stopPropagation();
                })
            }else{
                alert("您还没有登录!!");
                event.stopPropagation();
            }
        }
    });
    //导航栏
    var $li = $('.navigate ul li');
    $li.click(function(){
        $(this).addClass("border");
        $(this).siblings().removeClass("border");
        var index = $li.index(this);
        $('.subnavigate > div').eq(index).show().siblings().hide();
    })
    //公告切换
    var $noticeTitle = $('.notice-title ul li');
    $noticeTitle.click(function(){
        $(this).removeClass("backgroundColor1").addClass("backgroundColor2");
        $(this).siblings().removeClass("backgroundColor2").addClass("backgroundColor1");
        var index = $noticeTitle.index(this);
        $('.notice-body > div').eq(index).show().siblings().hide();
    });
    //最新发布和最后发布切换
    var $cursor = $('.font-cursor');
    $cursor.click(function(){
        var index1 = $cursor.index(this);
        $(this).addClass('font-color').siblings().removeClass('font-color');
        $('.notice-number').eq(index1).show().siblings().hide();
    });
})