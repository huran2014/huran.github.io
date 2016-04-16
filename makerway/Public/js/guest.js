$(function(){
    var $keeper = $('.keeper');
    $keeper.click(function(){
        var $value = $(this).text();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/guestShare',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $value
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/guestShare?val="+$value;
            }
        });
    });
    //点击分享者跳转用户界面还是群组界面
    var $usermessage = $('.usermessage');
    var $groupmessage = $('.groupmessage');
    $usermessage.click(function(event){
        $val = $(this).text();
        $val = $val.slice(4);
        console.log($val);
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/guestShare',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $val
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/guestShare?val="+$val;
            }
        });
        event.stopPropagation();
        event.preventDefault();
    });
    $groupmessage.click(function(event){
        $val = Number($(this).next().text());
        console.log($val);

        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/guestGroupmain',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                groupid: $val
            },
            success:function(){
                location.href="http://www.makerway.space/makerway/index.php/Home/Index/guestGroupmain?groupid="+$val;
            }
        });
        event.stopPropagation();
        event.preventDefault();
    });
})