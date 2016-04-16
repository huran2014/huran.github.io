function check1(){
    var $name = $('.lab-name').val();
    var $phone = $('.lab-phone').val();
    var $address = $('.lab-address').val();
    var $belong = $('.lab-belong').val();
    var $summary = $('.lab-summary').val();
    var moblie= /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/;
    if( $name == '' || $name == null){
        alert("请输入群组名！！");
        return false;
    }
    if( $phone == '' || $phone == null){
        alert("请输入手机号！！");
        return false;
    }
    if(!moblie.test($phone)){
        alert("请输入有效的手机号！");
        return false;
    }
    if( $address == '' || $address == null){
        alert("请输入地址！！");
        return false;
    }
    if( $belong == '请选择'){
        alert("请输入分类名！！");
        return false;
    }
    if( $summary == '' || $summary == null){
        alert("请输入群组简介！！");
        return false;
    }
    if( $summary.length > 100){
        alert("简介内容不超过100字！！");
        return false;
    }
    return true;
}
$(function(){
    var $create = $('.create-btn');
    var $list = $('.group-list');
    var $form = $('.group-form');
    var $exit = $('.create-exit');
    $create.click(function(){
        $list.hide();
        $(this).hide();
        $form.show();
    });
    $exit.click(function(){
        $list.show();
        $create.show();
        $form.hide();
    });
})