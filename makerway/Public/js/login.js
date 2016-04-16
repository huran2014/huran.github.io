function check(){
    var $uname = $(".user").val();
    var $passwd = $(".passwd").val();
    if($uname == "" || $uname == null){
        alert("请输入名字!");
        return false;
    }
    if($passwd == "" || $passwd == null){
        alert("请输入密码!!");
        return false;
    }
    return true;
}
