$(window).ready(function(){
    var WINDOW_WIDTH = window.innerWidth;
    var slides = $('.slide');
    slides.css('margin-left', WINDOW_WIDTH);
    $('.active-slide').css('margin-left', 'auto');
    
    $('#next-slide').on('click', function(){
        $('.active-slide').animate({marginLeft: -WINDOW_WIDTH}, 1000);
        $('.active-slide+.slide').attr('trfale', 'da');
//        $('.active-slide+.slide').animate({marginLeft: 'auto'}, 1000);
    });
});