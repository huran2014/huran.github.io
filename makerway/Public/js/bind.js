$(function(){
    var $yes = $('.form-yes');
    var $no = $('.form-no');
    $yes.click(function(){
        $('.newUserform').hide();
        $('.oldUserform').show();
    })
    $no.click(function(){
        $('.oldUserform').hide();
        $('.newUserform').show();
    })

})
//验证手机号
function check(){
    if($('.newUserform').is(':visible')){
        var $phonenum = $('.phonenum').val();
        var moblie= /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
        var $addr = $("input[name='address']").val();
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
    }
    if($('.oldUserform').is(':visible')){
        var $oldUser = $('.oldUser').val();
        if($oldUser == "" || $oldUser == null){
            alert("请输入用户名!!");
            return false;
        }
    }
    return true;
}