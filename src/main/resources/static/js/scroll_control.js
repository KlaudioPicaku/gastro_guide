$(document).ready(function() {
  $('.scroll-button').on('mousedown', function() {
    var scrollWrapper = $(this).closest('.container-scroll').find('.scroll-wrapper');
    var scrollAmount = $(this).hasClass('previous') ? -300 : 300;
    var newScrollLeft = scrollWrapper.scrollLeft() + scrollAmount;

    // Limit scrolling to the available width of the container
    var maxScrollLeft = scrollWrapper[0].scrollWidth - scrollWrapper[0].clientWidth;
    newScrollLeft = Math.max(0, Math.min(newScrollLeft, maxScrollLeft));

    scrollWrapper.animate({ scrollLeft: newScrollLeft }, 300);
  });
});
