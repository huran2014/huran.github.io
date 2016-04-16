  function check(){
      var $uname = $(".user").val();
      var $passwd = $(".passwd").val();
      var $rpasswd = $(".rpasswd").val();
      var $length = $passwd.length;
      var $phonenum = $(".phonenum").val();
      var $addr = $("input[name='address']").val();
      var moblie= /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
      if($uname == "" || $uname == null){
            alert("请输入名字!");
            return false;
      }
      if($passwd == "" || $passwd == null){
            alert("请输入密码!!");
            return false;
      }
      if($rpasswd !=$passwd){
          alert("两次密码不一致!!");
          return false;
      }
      if($length<6 || $length>16){
          alert("密码长度为6到16位！");
          return false;
      }
      if($phonenum == "" || $phonenum == null){
          alert("请输入手机号!!");
          return false;
      }
      if(!moblie.test($phonenum)){
          alert("请输入有效的手机号！");
          return false;
      }
      if($addr == '' || $addr == null ){
          alert("地址为空！");
          return false;
      }
        return true;
    }
