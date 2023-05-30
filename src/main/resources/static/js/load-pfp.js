function updateProfilePicture() {
console.log("call");
  $.ajax({
    url: '/get/pfp',
    type: 'GET',
    headers: {
        'Accept': 'application/json'
      },
    success: function(response) {
    console.log(response);
      // copia l'immagine
      var newImage = $('#profile_picture').clone();


      // Update the image source.
      newImage.attr('src', response);

//      console.log(newImage);

      // sostituisco
      $('#profile_picture').replaceWith(newImage);
    },
    error: function(xhr, status, error) {
      console.error('Failed to fetch profile picture', error);
    }
  });
}

$(document).ready(function() {
  // aggiorno l'immagine

  if ($("#profile_picture").length > 0) {
    updateProfilePicture();
  }

  // $('#profile_dropdownMenuButton').click(function(event) {
  //   event.preventDefault();
  //   updateProfilePicture();
  // });
});