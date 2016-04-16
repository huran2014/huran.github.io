function check(){
    var $password = $("input[name='password']").val();
    var $rpassword = $("input[name='rpassword']").val();
    var $length = $password.length;
    if($password == "" || $password == null){
        alert("请输入密码!!");
        return false;
    }
    if($rpassword == "" || $rpassword == null){
        alert("请输入密码!!");
        return false;
    }
    if($length<6 || $length>16){
        alert("密码长度为6到16位！");
        return false;
    }
    if($rpassword != $password){
        alert("两次输入密码不一样!!");
        return false;
    }
}
