$(document).ready(function() {
  if ($("#film-carousel-home").length > 0) {
    $('#film-carousel-home .carousel-item').each(function(index) {
      var imageUrl = $(this).find('img').attr('src');
      $(this).css('--bg-image', 'url(' + imageUrl + ')');
    });
  }
});