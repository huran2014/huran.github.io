//点击热门搜索书名
$(function(){
    $dLi = $('.detail span');
    $dLi.click(function(){
        var $val = $(this).find('a').text();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/search',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                search: $val
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/search?search="+$val;
            }
        });
    });
    //热门书籍排行榜
    var $pointer = $('.hot-name .pointer');
    $pointer.click(function(){
        var $val = $(this).text();
        $val = $val.toString().slice(1,-1);//去掉书名号
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/search',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                search: $val
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/search?search="+$val;
            }
        })
    });
    //分享排行榜
    var $namepointer = $('.name-pointer');
    $namepointer.click(function(){
        var $val = $(this).text();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/guestShare',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                search: $val
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/guestShare?val="+$val;
            }
        })
    });
})