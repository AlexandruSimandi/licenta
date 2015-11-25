var WINDOW_WIDTH;
var numberOfLeftSlides;

var cardTitleWidth = 150;
var cardWidth = 1100;

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
	
	$('#facebook-login').on('click', function(){
		nextSlide(1000);
		simulateLoad();
	});
	
	$('.next-button').on('click', function(){
		nextSlide(1000);
	});
	
}

function nextSlide(animationDuration) {
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
}

function simulateLoad() {
	var loadingBar = $('#loadingBar');
	setTimeout(function() {
		loopLoading(10, loadingBar);
	}, 1000);
}
			   
function loopLoading(increment, loadingBar) {
	setTimeout(function(){
		loadingBar.animate({width: '+=10%'}, 550);
		if(increment > 1){
			loopLoading(increment - 1, loadingBar);
		} else {
			
		}
	}, 550);
}
