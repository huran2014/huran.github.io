$(function(){
    var $li = $('.navigate ul li');
    $li.click(function(){
        $(this).addClass("border");
        $(this).siblings().removeClass("border");
        var index = $li.index(this);
        $('.subnavigate > div').eq(index).show().siblings().hide();
    })
    var $messageli = $('.message ul li');
    $messageli.click(function(){
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $messageli.index(this);
        $('.second-navigate > div').eq(index).show().siblings().hide();
    })
    var $shareli = $('.bookmessage li');
    $shareli.click(function(){
        //$(this).addClass("activecolor");
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $shareli.index(this);
        $('.mybook > div').eq(index).show().siblings().hide();
    })
    var $doingli = $('.dosomething li');
    $doingli.click(function(){
        var index = $doingli.index(this);
        $('.doing > div').eq(index).show().siblings().hide();
    })
    var $booktags = $('.booktags>ul li');
    $booktags.click(function(){
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $booktags.index(this);
        $('.tags > div').eq(index).show().siblings().hide();
    });
    var $friend = $('.friend>ul li');
    $friend.click(function(){
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $friend.index(this);
        $('.friend-message > ul').eq(index).show().siblings().hide();
    });
    var $notice = $('.notice-request>ul li');
    $notice.click(function(){
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $notice.index(this);
        $('.message-body > ul').eq(index).show().siblings().hide();
    });
    var $change1 = $('.change-message1');
    $change1.click(function(){
        $('.messageform31').toggle();
    });
    var $change2 = $('.change-message2');
    $change2.click(function(){
        $('.messageform32').toggle();
    });
    var $change3 = $('.change-message3');
    $change3.click(function(){
        $('.messageform33').toggle();
    });
    var $recall = $('.recall');
    $recall.click(function(){
        var index = $recall.index(this);
        $('.main-tan').eq(index).show();
        $('.tan-message').eq(index).show();
        var $tanbutton = $('.tan-button1');
        $tanbutton.click(function(){
            $('.main-tan').eq(index).hide();
            $('.tan-message').eq(index).hide();
        })
    });
    //消息同意加好友
    var $agree = $('.message-body ul li .agree');
    var $disagree = $('.message-body ul li .disagree');
    $agree.click(function(){
        var $value = $(this).val();
        var $friendname = $(this).next().next().val();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/friendhandleresult',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $value,
                friendname: $friendname
            }
        })
        $(this).parent().next().show();
        $(this).parent().hide();
        //$(this).parent().parent().remove();
    });
    $disagree.click(function(){
        var $value = $(this).text();
        var $friendname = $(this).next().val();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/friendhandleresult',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $value,
                friendname: $friendname
            }
        })
        $(this).parent().next().next().show();
        $(this).parent().hide();
        //$(this).parent().parent().remove();
    });
})