var WINDOW_WIDTH;
var numberOfLeftSlides;

var cardTitleWidth = 150;
var cardWidth = 1100;

jQuery(function($) {
    // Asynchronously Load the map API 
    var script = document.createElement('script');
    script.src = "http://maps.googleapis.com/maps/api/js?sensor=false&callback=initialize";
    document.body.appendChild(script);
});

$(window).ready(function(){
	
	arrangePage();
	loadEvents();
	
});

function initialize() {
    var map;
    var bounds = new google.maps.LatLngBounds();
    var mapOptions = {
        mapTypeId: 'roadmap'
    };
                    
    // Display a map on the page
    map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
    map.setTilt(45);
        
    // Multiple Markers
//    var markers = [
//        ['London Eye, London', 51.503454,-0.119562],
//        ['Palace of Westminster, London', 51.499633,-0.124755]
//    ];
                        
    // Info Window Content

        
    // Display multiple markers on a map
    var infoWindow = new google.maps.InfoWindow(), marker, i;
    
    // Loop through our array of markers & place each one on the map  
    for( i = 0; i < markers.length; i++ ) {
        var position = new google.maps.LatLng(markers[i][1], markers[i][2]);
        bounds.extend(position);
        marker = new google.maps.Marker({
            position: position,
            map: map,
            title: markers[i][0]
        });
        
        // Allow each marker to have an info window    
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infoWindow.setContent(infoWindowContent[i][0]);
                infoWindow.open(map, marker);
            }
        })(marker, i));

        // Automatically center the map fitting all markers on the screen
        map.fitBounds(bounds);
    }

    // Override our map zoom level once our fitBounds function runs (Make sure it only runs once)
    var boundsListener = google.maps.event.addListener((map), 'bounds_changed', function(event) {
//        this.setZoom(14);
        google.maps.event.removeListener(boundsListener);
    });
}

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
	
	$('.next-button').slice(1).on('click', function(){
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


