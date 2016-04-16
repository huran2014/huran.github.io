$(function(){
    $('#time2').click(function(){
       $checked = $('input[name=book_s]:checked');
        if($checked.length){
            $('.textarea').show();
        }else{
            $('.textarea').hide();
        }
    })
})