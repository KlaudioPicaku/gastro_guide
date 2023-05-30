 $(document).ready(function() {
 if ($('#editFilmForm').length > 0) {
     $('#editFilmForm').submit(function() {
       var selectedImageIds = [];

       $('input[name="removeExistingImage"]:checked').each(function() {
         selectedImageIds.push($(this).val());
       });

       $('#selectedImageIds').val(JSON.stringify(selectedImageIds));

       // include gli input delle immagini
       var formData = new FormData();

      $('input[type="file"]').each(function() {
        var files = $(this)[0].files;
        if (files.length > 0) {
          for (var i = 0; i < files.length; i++) {
            formData.append($(this).attr('name'), files[i]);
          }
        }
      });
        console.log(formData);

       return true;
     });
   }
   if ($("#editDirectorForm").length > 0) {
     $("#editDirectorForm").submit(function() {
       var selectedImageIds = [];
       $('input[name="removeExistingImage"]:checked').each(function() {
         selectedImageIds.push($(this).val());
       });

       $('#selectedImageIds').val(JSON.stringify(selectedImageIds));

       // Include the input field for the image
       var formData = new FormData();

       var fileInput = $('input[type="file"]')[0];
       var files = fileInput.files;
       if (files.length > 0) {
         formData.append($(fileInput).attr('name'), files[0]);
       }

       });
   }
  });