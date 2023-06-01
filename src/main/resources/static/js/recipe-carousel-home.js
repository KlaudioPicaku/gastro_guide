$(document).ready(function() {
  if ($("#recipe-carousel-home").length > 0) {
    $('#recipe-carousel-home .carousel-item').each(function(index) {
      var imageUrl = $(this).find('img').attr('src');
      $(this).css('--bg-image', 'url(' + imageUrl + ')');
    });
  }
});