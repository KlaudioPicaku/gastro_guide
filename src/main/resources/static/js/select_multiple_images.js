$(document).ready(function() {
  if ($('#selected-images').length > 0) {
    $('#image').on('change', function() {
      var selectedImagesContainer = $('#selected-images');
      selectedImagesContainer.empty();

      var files = $(this).prop('files');

      for (var i = 0; i < files.length; i++) {
        var file = files[i];
        var imageContainer = $('<div>').addClass('selected-image');

        var image = $('<img>').attr('src', URL.createObjectURL(file)).addClass('selected-image-thumbnail mt-2');
        image.attr('width', '50');
        image.attr('height', '50');
        imageContainer.append(image);

        var deselectButton = $('<button>').addClass('btn btn-danger').text('X');
        deselectButton.on('click', function() {
          var selectedImageContainer = $(this).parent();
          var selectedFile = selectedImageContainer.data('file'); // Prendi file Associato
          selectedImageContainer.remove();
          removeFileFromInput(selectedFile);
        });
        imageContainer.append(deselectButton);

        // associazione con image container
        imageContainer.data('file', file);

        selectedImagesContainer.append(imageContainer);
      }


    });
  }

  // Rimuovi il file dalla lista
  function removeFileFromInput(file) {
    var input = $('#image')[0];
    var files = Array.from(input.files);
    var index = files.indexOf(file);
    if (index !== -1) {
      files.splice(index, 1);
      input.files = new FileListWrapper(files);
    }
  }

  function FileListWrapper(files) {
    var fileList = new DataTransfer();
    for (var i = 0; i < files.length; i++) {
      fileList.items.add(files[i]);
    }
    return fileList.files;
  }
});