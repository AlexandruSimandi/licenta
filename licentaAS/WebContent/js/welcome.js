$(window).ready(function(){
	var WINDOW_WIDTH = window.innerWidth;
	
	$('#active-slide-title').css('left', WINDOW_WIDTH / 2 - 50);
	$('#active-slide').css('left', WINDOW_WIDTH / 2 - 150);
	
	var rightSlideTitles = $('.right-slide-title');
	var rightSlides = $('.right-slide');
	var numberOfRightSlides = rightSlideTitles.length;
	for(var i = numberOfRightSlides - 1; i >= 0; i--){
		$(rightSlideTitles[i]).css('left', WINDOW_WIDTH - (numberOfRightSlides - i) * 100);
		$(rightSlides[i]).css('left', WINDOW_WIDTH / 2 - 150 + WINDOW_WIDTH);
	}
	
	var numberOfLeftSlides = 0;
	
	$('#next-button').on('click', function(){
		var activeSlideTitle = $('#active-slide-title');
		activeSlideTitle.animate({left: numberOfLeftSlides * 100}, 1000, 'swing');
		activeSlideTitle.removeAttr('id');
		numberOfLeftSlides++;
		
		var activeSlide = $('#active-slide');
		activeSlide.animate({left: -WINDOW_WIDTH + WINDOW_WIDTH / 2 - 150}, 1000, 'swing');
		activeSlide.removeAttr('id');		
		
		
		var nextOneTitle = $($('.right-slide-title')[0]);
		nextOneTitle.removeClass('right-slide-title');
		nextOneTitle.attr('id', 'active-slide-title');
		nextOneTitle.animate({left: WINDOW_WIDTH / 2 - 50}, 1000, 'swing');
		
		var nextOne = $($('.right-slide')[0]);
		nextOne.removeClass('right-slide');
		nextOne.attr('id', 'active-slide');
		nextOne.animate({left: WINDOW_WIDTH / 2 - 150}, 1000, 'swing');		
	});
});