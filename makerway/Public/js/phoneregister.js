function check(){
    $username = $('input[name=username]').val();
    $password = $('input[name=password]').val();
    $phonenum = $('input[name=phonenum]').val();
    var $myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})||(17[0-9]{1}))+\d{8})$/;
    $flag = true;
        if($username == "" || $username == null){
            alert("请输入用户名！");
            flag = false;
        }
        if($password == "" || $password == null){
            alert("请输入密码！");
            flag = false;
        }
        if($phonenum == "" || $phonenum == null){
            alert("请输入手机号！");
            flag = false;
        }
        if(!$myreg.test($phonenum)){
            alert("请输入正确的手机号！");
            flag = false;
        }
        return flag;
    }