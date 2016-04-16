$(function(){
    var nowpage = 0;
    //给最大的盒子增加事件监听
    $(".container").swipe({
        swipe:function(event, direction, distance, duration, fingerCount) {
            if(direction == "up"){
                nowpage = nowpage + 1;
            }else if(direction == "down"){
                nowpage = nowpage - 1;
            }
            if(nowpage > 4){
                nowpage = 4;
            }
            if(nowpage < 0){
                nowpage = 0;
            }
            $(".container").animate({"top":nowpage * -100 + "%"},400);
            $(".page").eq(nowpage).addClass("cur").siblings().removeClass("cur");
        }
    });
});