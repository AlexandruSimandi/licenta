$(document).ready(function(){
   $(".button-collapse").sideNav();
   $('ul.tabs').tabs();
   setTimeout(function(){
	   $('.collapsible').collapsible({
		   accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
	   });
   },3000);
});