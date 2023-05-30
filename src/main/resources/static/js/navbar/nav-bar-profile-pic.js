$(document).ready(function() {
  const navbarNav = $("#navbarNav");
  if (navbarNav.length > 0){
    const img = $("#profile-image");
    img.on('load', function() {
      // immagine trovata
    }).on('error', function() {
      // S W I T C H
      img.attr('src', 'images/default_profile_pic.webp');
    });
  }
});