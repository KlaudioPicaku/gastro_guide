$(document).ready(function () {
    if ($('.select').length > 0) {
        $('.select').select2({
            allowClear: true,
            theme:"classic",
            placeholder:"Select an option"
        });
    }
    // if($(".tag-form").length > 0){
    //     $('#recipeTags').on('select2:unselecting', function(e) {
    //         var value = e.params.args.data.id;
    //         var hiddenField = $('#tagDtos');
    //         var currentValues = hiddenField.val().split(',');
    //         var index = currentValues.indexOf(value.toString());
    //         if (index > -1) {
    //             currentValues.splice(index, 1);
    //             hiddenField.val(currentValues.join(','));
    //         }
    //     });
    // }
});