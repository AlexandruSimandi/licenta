$(document).ready(function(){
	
	var style = document.createElement('style');
	style.type = 'text/css';
	style.innerHTML = 'body {}';
	document.getElementsByTagName('head')[0].appendChild(style);
	this.stylesheet = document.styleSheets[document.styleSheets.length - 1];
	try {
		this.stylesheet.insertRule('@keyframes move { 100% { transform: translate(100px, 0px); }}', this.stylesheet.rules.length);
	} catch (e) {};
	
	
	
	$('.slide').attr('id','active-slide');
});