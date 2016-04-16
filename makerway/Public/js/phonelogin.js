 function check(){
    $username = $('input[name=username]').val();
    $password = $('input[name=password]').val();
    $flag = true;
        if($username == "" || $username == null){
            alert("请输入用户名！");
            flag =  false;
        }
        if($password == "" || $password == null){
            alert("请输入密码！");
            flag = false;
        }
        return flag;
    }
