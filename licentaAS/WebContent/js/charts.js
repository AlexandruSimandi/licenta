$(document).ready(function(){
	var chart = c3.generate({
	    bindto: '#chart',
	    padding: {
	        right: 30
	    },
	    size: {
	    	height: $(window).height() - 200
	    },
	    data: {
	    	xs: {
	    		'all': 'x1',
	    		'with location': 'x2',
	    		'with photos': 'x3'
	    	},
	        //x: 'x',
	        columns: [
	            ['x1'].concat(labels),
	            ['x2'].concat(locationLabels),
	            ['x3'].concat(photoLabels),
	            ['all'].concat(dataCount),
	            ['with location'].concat(locationDataCount),
	            ['with photos'].concat(photoDataCount)
	            
	        ],
//	        type: 'area-spline'
	        types: {
	        	'all': 'area-spline',
	        	'with location': 'area-spline',
	        	'with photos': 'area-spline'
	        }
	    },
	    axis: {
	        x: {
	            type: 'timeseries',
	            tick: {
	                format: '%m-%Y'
	            },
	    		label: 'time'
	        },
	        y: {
	        	label: 'number of posts'
	        }
	    },
	    subchart: {
	        show: true
	    }
	});
});

