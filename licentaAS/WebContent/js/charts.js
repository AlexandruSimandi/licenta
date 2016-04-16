var datah;
$(document).ready(function(){
	var chart = c3.generate({
	    bindto: '#chart',
	    padding: {
	        right: 80
	    },
	    size: {
	    	height: 600
	    },
	    data: {
	    	xs: {
	    		'all posts': 'x1',
	    		'posts with location': 'x2',
	    		'posts with photos': 'x3'
	    	},
	        //x: 'x',
	        columns: [
	            ['x1'].concat(labels),
	            ['x2'].concat(locationLabels),
	            ['x3'].concat(photoLabels),
	            ['all posts'].concat(dataCount),
	            ['posts with location'].concat(locationDataCount),
	            ['posts with photos'].concat(photoDataCount)
	            
	        ],
//	        type: 'area-spline'
	        types: {
	        	'all posts': 'area-spline',
	        	'posts with location': 'area-spline',
	        	'posts with photos': 'area-spline'
	        }
	    },
	    axis: {
	        x: {
	            type: 'timeseries',
	            tick: {
	                format: '%Y-%m-%d'
	            }
	        }
	    },
	    subchart: {
	        show: true
	    }
	});
});

