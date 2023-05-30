$(document).ready(function() {
    if ($(".cast-item").length > 3 ) {
      const castList = $('#cast-list');
      const expandButton = $('#expand-button');

      const originalListItems = castList.find('.list-group').toArray();
      const hiddenListItems = castList.find('.d-none').toArray();
      const initialVisibleItems = 3;

      expandButton.click(function() {
        if (castList.find('.d-none').length === 0) {
          // Shrink the list
          castList.find('.list-group').slice(initialVisibleItems).addClass('d-none');
          expandButton.text('Expand');
        } else {
          // Expand the list
          castList.find('.list-group').removeClass('d-none');
          expandButton.text('Collapse');
        }
      });
    }else{
    $('#expand-button').hide();
    }
  });