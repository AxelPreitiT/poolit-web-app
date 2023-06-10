const carOptionSelectorClass = "car-review-option";
const carOptionSelectorContainerClass = "car-review-option-container";
const carDefaultRatingSelectorValue = 3;

const carRatingSelectorElement = document.getElementById("car-review-rating");
const carOptionSelectorElements = document.getElementsByClassName(carOptionSelectorClass);
const carOptionSelectorContainer = document.getElementsByClassName(carOptionSelectorContainerClass);

const getCarOptionSelectorElement = (value) => {
    return Array.from(carOptionSelectorElements).find(optionSelectorElement =>
        optionSelectorElement.id === "car-" + value
    );
};

const getCarOptionSelectorContainerElement = (value) => {
    return Array.from(carOptionSelectorContainer).find(optionSelectorContainerElement =>
        optionSelectorContainerElement.id === "car-" + value + "-container");
};

const currentCarRatingSelectorValueMap = {
    value: carDefaultRatingSelectorValue,
    optionSelectorElement: getCarOptionSelectorElement(carDefaultRatingSelectorValue),
    optionSelectorContainerElement: getCarOptionSelectorContainerElement(carDefaultRatingSelectorValue)
};

carRatingSelectorElement.addEventListener("change", () => {
    currentCarRatingSelectorValueMap.value = carRatingSelectorElement.value;
    currentCarRatingSelectorValueMap.optionSelectorElement.setAttribute("disabled", "disabled");
    currentCarRatingSelectorValueMap.optionSelectorContainerElement.setAttribute("hidden", "hidden");
    const newOptionSelectorElement = getCarOptionSelectorElement(carRatingSelectorElement.value);
    const newOptionSelectorContainerElement = getCarOptionSelectorContainerElement(carRatingSelectorElement.value);
    newOptionSelectorElement.removeAttribute("disabled");
    newOptionSelectorContainerElement.removeAttribute("hidden");
    currentCarRatingSelectorValueMap.optionSelectorElement = newOptionSelectorElement;
    currentCarRatingSelectorValueMap.optionSelectorContainerElement = newOptionSelectorContainerElement;
});
