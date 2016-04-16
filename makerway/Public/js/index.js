$(function(){
    var $input1 = $('.input-1');
    var $input2 = $('.input-2');
    var $contribute = $('.share-contribute');
    var $share = $('.share-share');
    $input1.hover(function(){
        $contribute.show();
    }).mouseleave(function(){
        $contribute.hide();
    });
    $input2.hover(function(){
        $share.show();
    }).mouseleave(function(){
        $share.hide();
    });
})