$(document).ready(function(){
	var data = {
//	    labels: ["January", "February", "March", "April", "May", "June", "July"],
		labels: labels,
	    datasets: [
	        {
	            label: "My First dataset",
	            fillColor: "rgba(151,187,205,0.2)",
	            strokeColor: "rgba(151,187,205,1)",
	            pointColor: "rgba(151,187,205,1)",
	            pointStrokeColor: "#fff",
	            pointHighlightFill: "#fff",
	            pointHighlightStroke: "rgba(151,187,205,1)",
	            data: dataCount
	        } 
	    ]
	};
	
	var options = {
			tooltipTemplate: "<%= value %>",
		    scaleShowGridLines : true,
		    scaleGridLineColor : "rgba(0,0,0,.05)",
		    scaleGridLineWidth : 1,
		    scaleShowHorizontalLines: true,
		    scaleShowVerticalLines: true,
		    bezierCurve : true,
		    bezierCurveTension : 0.4,
		    pointDot : true,
		    pointDotRadius : 4,
		    pointDotStrokeWidth : 1,
		    pointHitDetectionRadius : 20,
		    datasetStroke : true,
		    datasetStrokeWidth : 2,
		    datasetFill : true,
	};
	
	var canvas = document.getElementById("myChart");
	canvas.width = $(canvas).parent().width() - 240;
	canvas.height = $(canvas).parent().height() + 100;
	var ctx = canvas.getContext("2d");
	var myLineChart = new Chart(ctx).Line(data, options);
});

