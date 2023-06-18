$(document).ready(function () {
    var select = $('#recipeTags');
    var newTagInput = $('#newTagInput');
    var addNewTagButton = $('#addNewTagButton');
    var tagDtosInput = $('#tagDtos');
    var tags = /*[[${tags}]]*/ [];
    var selectedTags = [];

    // Populate select with existing tags
    for (var i = 0; i < tags.length; i++) {
        var option = $('<option></option>')
            .val(tags[i].id)
            .text(tags[i].title);
        select.append(option);
    }

    // Update selectedTags array when select options change
    select.on('change', function () {
        selectedTags = select.val() || [];
        tagDtosInput.val(JSON.stringify(selectedTags));
    });

    // Add new tag when the button is clicked
    addNewTagButton.on('click', function () {
        var newTag = newTagInput.val().trim();
        var optionExists = select.find("option[value='" + newTag + "']").length > 0;

        if (newTag !== '' && !optionExists) {
            var option = $('<option></option>')
                .val(newTag)
                .text(newTag);
            select.append(option);
            selectedTags.push(newTag);
            console.log(selectedTags)
            tagDtosInput.val(JSON.stringify(selectedTags));
            newTagInput.val('');

            // Select the newly added tag
            option.prop('selected', true);
        } else {
            // Show helper message for duplicate tags
            $('#tagExistsMessage').show();
        }
    });

    // Hide the helper message when the input changes
    newTagInput.on('input', function () {
        $('#tagExistsMessage').hide();
    });
});
