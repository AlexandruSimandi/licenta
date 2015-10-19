var WINDOW_WIDTH;
var numberOfLeftSlides;

var cardTitleWidth = 100;
var cardWidth = 1100;
var animationDuration = 1000;

$(window).ready(function(){
	
	arrangePage();
	loadEvents();
	
});

function arrangePage() {
	
	WINDOW_WIDTH = window.innerWidth;
	
	$('#active-slide-title').css('left', WINDOW_WIDTH / 2 - (cardTitleWidth / 2));
	$('#active-slide').css('left', WINDOW_WIDTH / 2 - (cardWidth / 2));
	
	var rightSlideTitles = $('.right-slide-title');
	var rightSlides = $('.right-slide');
	var numberOfRightSlides = rightSlideTitles.length;
	for(var i = numberOfRightSlides - 1; i >= 0; i--){
		$(rightSlideTitles[i]).css('left', WINDOW_WIDTH - (numberOfRightSlides - i) * cardTitleWidth);
		$(rightSlides[i]).css('left', WINDOW_WIDTH / 2 - (cardWidth / 2) + WINDOW_WIDTH);
	}
	
	numberOfLeftSlides = 0;
}

function loadEvents() {
	
	$('#next-button').on('click', function(){
		var activeSlideTitle = $('#active-slide-title');
		activeSlideTitle.animate({left: numberOfLeftSlides * cardTitleWidth}, animationDuration, 'swing');
		activeSlideTitle.removeAttr('id');
		numberOfLeftSlides++;
		
		var activeSlide = $('#active-slide');
		activeSlide.animate({left: -WINDOW_WIDTH + WINDOW_WIDTH / 2 - (cardWidth / 2)}, animationDuration, 'swing');
		activeSlide.removeAttr('id');		
		
		
		var nextOneTitle = $($('.right-slide-title')[0]);
		nextOneTitle.removeClass('right-slide-title');
		nextOneTitle.attr('id', 'active-slide-title');
		nextOneTitle.animate({left: WINDOW_WIDTH / 2 - (cardTitleWidth / 2)}, animationDuration, 'swing');
		
		var nextOne = $($('.right-slide')[0]);
		nextOne.removeClass('right-slide');
		nextOne.attr('id', 'active-slide');
		nextOne.animate({left: WINDOW_WIDTH / 2 - (cardWidth / 2)}, animationDuration, 'swing');		
	});
	
	
	
}