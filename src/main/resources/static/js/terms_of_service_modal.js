$(document).ready(function() {
if($(".registration-form")){
      //mostra la modale
    $("#show_modal").click(function() {
        $("#modal-overlay").fadeIn();
      });

  $("#close-modal").click(function() {
    $("#modal-overlay").fadeOut();
  });
  }
  // disattiva pulsante register
    $('.register-user').prop('disabled', true);

    // listener per la checkbox
    $('#agree_to_terms_of_service').change(function() {
      if (this.checked) {
        // abilita register se check box è selezionata
        $('.register-user').prop('disabled', false);
      } else {
        // disattiva altrimenti
        $('.register-user').prop('disabled', true);
      }
    });

    // ascolta per submit
    $('.registration-form').submit(function(e) {
      if (!$('#agree_to_terms_of_service').is(':checked')) {
        e.preventDefault();
        alert('You need to agree to terms of service');
      }
    });
});