$(document).ready(function() {
  $('.scroll-button').on('mousedown', function() {
    var scrollWrapper = $(this).closest('.container-scroll').find('.scroll-wrapper');
    var scrollAmount = $(this).hasClass('previous') ? -300 : 300;
    scrollWrapper.animate({ scrollLeft: scrollWrapper.scrollLeft() + scrollAmount }, 300);
  });
});