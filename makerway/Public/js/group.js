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
    var $groupmessage = $('.group-message>ul li');
    $groupmessage.click(function(){
        $(this).addClass("activecolor");
        $(this).siblings().removeClass("activecolor");
        var index = $groupmessage.index(this);
        $('.grouptags > div').eq(index).show().siblings().hide();
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