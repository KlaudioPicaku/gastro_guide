$(document).ready(function() {
    const stepContainer = $('#stepsContainer .step-container');
    const stepCards = stepContainer.find('.step');
    const arrowLeft = $('#stepsContainer .arrow-left');
    const arrowRight = $('#stepsContainer .arrow-right');
    let currentStep = 0;

    // Hide all steps except the first one
    stepCards.not(':first-child').hide();

    function showStep(index) {
        stepCards.hide().removeClass('active');
        stepCards.eq(index).show().addClass('active');
    }

    arrowLeft.on('click', () => {
        if (currentStep > 0) {
            currentStep--;
            showStep(currentStep);
        }
    });

    arrowRight.on('click', () => {
        if (currentStep < stepCards.length - 1) {
            currentStep++;
            showStep(currentStep);
        }
    });

    showStep(currentStep);
});
