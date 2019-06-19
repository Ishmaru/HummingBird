$(document).ready(function() {
  $('.unfollow_btn').hover(function(){
    $(this).removeClass('btn-primary');
    $(this).addClass('btn-danger');
    $(this).html("Unfollow");
  }, function(){
    $(this).html("Following");
    $(this).removeClass('btn-danger');
    $(this).addClass('btn-primary');
  });
  
  function redirectTimer(url){
	  setTimeout(function(){
		  window.location.replace(url);
	  }, 3000);
  }
})