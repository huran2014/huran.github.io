window.onload = function(){
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

    var star = document.getElementById("star");
    var star_li = star.getElementsByTagName("li");
    var result = document.getElementById("result");
    var i=0;
    var j=0;
    function indexof(arr, ele) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == ele) {
                return i;
            }
        }
        return -1;
    }
    var len = star_li.length;
    for(i=0; i<len; i++){
        star_li[i].index = i;
        star_li[i].onmouseout = function(){
            for(i=0; i<len; i++){
                star_li[i].className = "";
            }
        }
        star_li[i].onclick = function(){
            if(cookie_val){
                var tableWjx = document.getElementById("star");
                var tds = tableWjx.getElementsByTagName("li");
                var index = indexof(tds, this);
                for (var i = 0; i < index + 1; i++) {
                    var td = tds[i];
                    td.innerText = "★";
                }
                for(var j=index+ 1;j<len;j++){
                    var td = tds[j];
                    td.innerText = "☆";
                }
                var num = this.index;
                result.innerHTML = (num+1);
                isclick = true;
                var $book_id = $('.result-num').next().val();
                $.ajax({
                    url: 'http://www.makerway.space/makerway/index.php/Home/Index/bookfen',
                    type: 'POST',
                    datatype: 'jsonp',
                    data:{
                        fen: num+1,
                        book_id:$book_id
                    }
                });
            }else{
                alert("您还没有登录！！");
            }

        }
    }
}