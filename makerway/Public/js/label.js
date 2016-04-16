//书籍分类的点击
$(function(){
    var $li = $('.view-left-classify ul li');
    var $title = $('.view-left-name ul li');
    var $detail = $('.view-left-detail ul');
    var $dLi = $('.view-left-detail ul li');
    $li.hover(function(){
        var index = $li.index(this);
        $title.eq(index).show().siblings().hide();
        $detail.eq(index).show().siblings().hide();
    })
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
    })
})