$(function(){
    var $agree = $('.group-pass ul li .agree');
    var $disagree = $('.group-pass ul li .disagree');
    $agree.click(function(){
        var $value = $(this).val();
        var $groupid = $(this).next().next().val();
        var $username = $(this).next().next().next().val();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/grouphandleresult',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $value,
                groupid: $groupid,
                username: $username
            }
        })
        $(this).parent().next().show();
        $(this).parent().hide();
        //$(this).parent().parent().remove();
    });
    $disagree.click(function(){
        var $value = $(this).text();
        var $groupid = $(this).next().val();
        var $username = $(this).next().next().val();
        $.ajax({
            url: 'http://www.makerway.space/makerway/index.php/Home/Index/grouphandleresult',
            type: 'POST',
            datatype: 'jsonp',
            data:{
                val: $value,
                groupid: $groupid,
                username: $username
            }
        })
        $(this).parent().next().next().show();
        $(this).parent().hide();
        //$(this).parent().parent().remove();
    });
})