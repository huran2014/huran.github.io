//���������������
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
    //�����鼮���а�
    var $pointer = $('.hot-name .pointer');
    $pointer.click(function(){
        var $val = $(this).text();
        $val = $val.toString().slice(1,-1);//ȥ��������
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
    //�������а�
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