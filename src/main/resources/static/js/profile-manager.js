$(document).ready(function() {
  $('#edit_profile_pic').click(function() {
    $(this).toggleClass('active');
    $('#profile_pic_form').toggle();
  });

  $('#save_profile_picture').click(function(event) {
    event.preventDefault();

    var fileInput = $('#image')[0];
    if (fileInput.files.length === 0) {
      console.log('No file selected');
      return;
    }

    var formData = new FormData();
    formData.append('file', fileInput.files[0]);

    var csrfToken = $("meta[name='_csrf']").attr("content");
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
      url: '/update/profile',
      type: 'POST',
      data: formData,
      beforeSend: function(xhr) {
        xhr.setRequestHeader(csrfHeader, csrfToken);
      },
      processData: false,
      contentType: false,
      success: function(response) {
        console.log('Picture uploaded successfully');
      },
      error: function(xhr, status, error) {
        console.error('Failed to upload picture', error);
      }
    });
  });
});