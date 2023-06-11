$(document).ready(function () {
    // Aggiungi campo ingrediente al clic del pulsante
    $("#add_ingredient_field").click(function () {
        addIngredientField();
    });

    // Aggiungi campo passo al clic del pulsante
    $("#add_step_field").click(function () {
        addStepField();
    });

    // Elimina campo ingrediente al clic del pulsante
    $(document).on("click", ".delete-ingredient-field", function () {
        $(this).closest(".ingredient-row").remove();
        updateIngredientIndices();
    });

    // Elimina campo passo al clic del pulsante
    $(document).on("click", ".delete-step-field", function () {
        var stepRow = $(this).closest(".step-row");
        var stepDescriptionRow = stepRow.next(".step-description-row");
        stepRow.remove();
        stepDescriptionRow.remove();
        updateStepIndices();
    });

    function addIngredientField() {
        var ingredientIndex = $(".ingredient-row").length;

        var ingredientRow = $(".ingredient-row:first");
        var clonedRow = ingredientRow.clone();

        // Aggiorna gli ID e i nomi
        clonedRow.find("input[id^='ingredientName_']").attr("id", "ingredientName_" + ingredientIndex);
        clonedRow.find("input[id^='ingredientName_']").attr("name", "ingredients[" + ingredientIndex + "].name");

        clonedRow.find("input[id^='ingredientQuantity_']").attr("id", "ingredientQuantity_" + ingredientIndex);
        clonedRow.find("input[id^='ingredientQuantity_']").attr("name", "ingredients[" + ingredientIndex + "].quantity");

        // Aggiungi il pulsante di eliminazione e l'evento di ascolto
        var deleteButton = $('<button type="button" class="btn btn-danger delete-ingredient-field mr-2"><i class="bi bi-trash-fill"></i></button>');
        deleteButton.click(function () {
            $(this).closest(".ingredient-row").remove();
            updateIngredientIndices();
        });

        // Aggiungi il pulsante di eliminazione alla fine della riga degli ingredienti
        clonedRow.find(".delete-ingredient-button").addClass("d-flex align-items-center mt-6").append(deleteButton);

        // Aggiungi la riga clonata
        $("#ingredientFields").append(clonedRow);
    }
    function addTag() {
        var selectBox = $("#tagsSelect");
        var newTagInput = $("#newTagInput");
        var tagValue = newTagInput.value.trim();

        // Check if the entered tag already exists in the select box
        var optionExists = Array.from(selectBox.options).some(function(option) {
            return option.text.toLowerCase() === tagValue.toLowerCase();
        });

        if (tagValue !== "" && !optionExists) {
            // Create a new option element for the entered tag
            var newOption = document.createElement("option");
            newOption.value = tagValue;
            newOption.text = tagValue;
            newOption.selected = true;

            // Add the new option to the select box
            selectBox.add(newOption);
        }

        // Clear the input field
        newTagInput.value = "";
    }

    $("#addTagButton").on("click",function () {
        addTag();
    })

    function addStepField() {
        var stepIndex = $(".step-row").length;

        var stepRow = $(".step-row:first");
        var clonedRow = stepRow.clone();

        // Aggiorna gli ID e i nomi
        clonedRow.find("input[id^='stepName']").attr("id", "stepName" + stepIndex);
        clonedRow.find("input[id^='stepName']").attr("name", "steps[" + stepIndex + "].title");

        clonedRow.find("input[id^='stepEstimatedTime']").attr("id", "stepEstimatedTime" + stepIndex);
        clonedRow.find("input[id^='stepEstimatedTime']").attr("name", "steps[" + stepIndex + "].estimatedDuration");

        clonedRow.find("textarea[id^='stepDescription']").attr("id", "stepDescription" + stepIndex);
        clonedRow.find("textarea[id^='stepDescription']").attr("name", "steps[" + stepIndex + "].body");

        // Aggiungi il pulsante di eliminazione e l'evento di ascolto
        var deleteButton = $('<button type="button" class="btn btn-danger delete-step-field mr-2"><i class="bi bi-trash-fill"></i></button>');
        deleteButton.click(function () {
            var stepRow = $(this).closest(".step-row");
            var stepDescriptionRow = stepRow.next(".step-description-row");
            stepRow.remove();
            stepDescriptionRow.remove();
            updateStepIndices();
        });

        // Aggiungi il pulsante di eliminazione alla fine della riga dei passi
        clonedRow.find(".delete-step-button").addClass("d-flex align-items-center mt-6").append(deleteButton);

        // Aggiungi la riga clonata
        $("#stepFields").append(clonedRow);
    }

    function updateIngredientIndices() {
        $(".ingredient-row").each(function (index) {
            $(this)
                .find("input[id^='ingredientName_']")
                .attr("id", "ingredientName_" + index)
                .attr("name", "ingredients[" + index + "].name");

            $(this)
                .find("input[id^='ingredientQuantity_']")
                .attr("id", "ingredientQuantity_" + index)
                .attr("name", "ingredients[" + index + "].quantity");
        });
    }

    function updateStepIndices() {
        $(".step-row").each(function (index) {
            $(this)
                .find("input[id^='stepName']")
                .attr("id", "stepName" + index)
                .attr("name", "steps[" + index + "].title");

            $(this)
                .find("input[id^='stepEstimatedTime']")
                .attr("id", "stepEstimatedTime" + index)
                .attr("name", "steps[" + index + "].estimatedDuration");

            $(this)
                .next(".step-description-row")
                .find("textarea[id^='stepDescription']")
                .attr("id", "stepDescription" + index)
                .attr("name", "steps[" + index + "].body");
        });
    }
});
