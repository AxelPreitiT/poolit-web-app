const driverOptionSelectorClass = "driver-review-option";
const driverOptionSelectorContainerClass = "driver-review-option-container";
const driverDefaultRatingSelectorValue = 3;

const driverRatingSelectorElement = document.getElementById("driver-review-rating");
const driverOptionSelectorElements = document.getElementsByClassName(driverOptionSelectorClass);
const driverOptionSelectorContainer = document.getElementsByClassName(driverOptionSelectorContainerClass);

const getDriverOptionSelectorElement = (value) => {
    return Array.from(driverOptionSelectorElements).find(optionSelectorElement =>
        optionSelectorElement.id === "driver-" + value
    );
};

const getDriverOptionSelectorContainerElement = (value) => {
    return Array.from(driverOptionSelectorContainer).find(optionSelectorContainerElement =>
        optionSelectorContainerElement.id === "driver-" + value + "-container"
    );
};

const currentDriverRatingSelectorValueMap = {
    value: driverDefaultRatingSelectorValue,
    optionSelectorElement: getDriverOptionSelectorElement(driverDefaultRatingSelectorValue),
    optionSelectorContainerElement: getDriverOptionSelectorContainerElement(driverDefaultRatingSelectorValue)
};

driverRatingSelectorElement.addEventListener("change", () => {
    currentDriverRatingSelectorValueMap.value = driverRatingSelectorElement.value;
    currentDriverRatingSelectorValueMap.optionSelectorElement.setAttribute("disabled", "disabled");
    currentDriverRatingSelectorValueMap.optionSelectorContainerElement.setAttribute("hidden", "hidden");
    const newOptionSelectorElement = getDriverOptionSelectorElement(driverRatingSelectorElement.value);
    const newOptionSelectorContainerElement = getDriverOptionSelectorContainerElement(driverRatingSelectorElement.value);
    newOptionSelectorElement.removeAttribute("disabled");
    newOptionSelectorContainerElement.removeAttribute("hidden");
    currentDriverRatingSelectorValueMap.optionSelectorElement = newOptionSelectorElement;
    currentDriverRatingSelectorValueMap.optionSelectorContainerElement = newOptionSelectorContainerElement;
});
