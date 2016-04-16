window.onload = function(){
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
        }
    }
}