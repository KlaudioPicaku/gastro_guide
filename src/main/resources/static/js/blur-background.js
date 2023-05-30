$(document).ready(function() {
if ($("#film-carousel").length>0){
      $('#film-carousel .carousel-item').each(function(index) {
        var imageUrl = $(this).find('img').attr('src');
        $(this).css('--bg-image', 'url(' + imageUrl + ')');
      });
  }
});
