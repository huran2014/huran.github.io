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
    var $btn = $('.sharebtn');
    $btn.click(function(){
        if(!cookie_val){
            alert('您还没有登录！！！');
        }else{
            $.ajax({
                url: 'http://www.makerway.space/makerway/index.php/Home/Index/friendhandle',
                type: 'POST',
                datatype: 'jsonp',
                success:function(result){
                    if(result== 0){
                        alert('申请发送成功！！');
                    }else if(result == 1){
                        alert('您已经发送申请！！');
                    }else if(result == 2){
                        alert('您已经是他的好友！！');
                    }
                }
            });
        }
    });
})