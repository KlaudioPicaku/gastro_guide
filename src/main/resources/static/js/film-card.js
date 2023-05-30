$(document).ready(function() {
        $('.film-card').hover(function() {
            var carouselImages = $(this).find('.carousel-image');
            var currentIndex = 0;

            var intervalId = setInterval(function() {
                carouselImages.eq(currentIndex).fadeOut(500, function() {
                    currentIndex = (currentIndex + 1) % carouselImages.length;
                    carouselImages.eq(currentIndex).fadeIn(500);
                });
            }, 2000);

            $(this).data('intervalId', intervalId);
        }, function() {
            var intervalId = $(this).data('intervalId');
            clearInterval(intervalId);
            $(this).find('.carousel-image').stop().fadeOut(500);
        });
    });