$(document).ready(function() {
  // Get all carousel items
  const carouselItems = $('.carousel-item');

  // Itera su tutti gli elementi del carousel
  carouselItems.each(function() {
    // prendi l'immagine per ogni elemento del carousel
    const image = $(this).find('.carousel-item-img');

    // Creo un nuovo div per lo sfondo sfocato
    const blurredBackground = $('<div>').addClass('carousel-item-blur');

    // setto lo sfondo per il div creato
    blurredBackground.css('background-image', `url('${image.attr('src')}')`);

    // aggiungo il div al carousel
    $(this).append(blurredBackground);
  });
});