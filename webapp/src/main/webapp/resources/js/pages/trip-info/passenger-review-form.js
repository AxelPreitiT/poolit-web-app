const ratingSelectorClass = "passenger-review-rating";
const optionSelectorClass = "passenger-review-option";
const defaultRatingSelectorValue = 3;

const ratingSelectorElements = document.getElementsByClassName(ratingSelectorClass);
const optionSelectorElements = document.getElementsByClassName(optionSelectorClass);

const getOptionSelectorElement = (ratingSelectorElement, value) => {
    return Array.from(optionSelectorElements).find(optionSelectorElement =>
        optionSelectorElement.id === ratingSelectorElement.id + "-" + value
    );
}

const ratingSelectorElementsMap = {};
Array.from(ratingSelectorElements).forEach(ratingSelectorElement => {
    ratingSelectorElementsMap[ratingSelectorElement.id] = {
        value: defaultRatingSelectorValue,
        optionSelectorElement: getOptionSelectorElement(ratingSelectorElement, defaultRatingSelectorValue)
    }
});

for (const ratingSelectorElement of ratingSelectorElements) {
    ratingSelectorElement.addEventListener("change", () => {
        const ratingSelectorElementMapItem = ratingSelectorElementsMap[ratingSelectorElement.id];
        ratingSelectorElementMapItem.value = ratingSelectorElement.value;
        ratingSelectorElementMapItem.optionSelectorElement.setAttribute("disabled", "disabled");
        const newOptionSelectorElement = getOptionSelectorElement(ratingSelectorElement, ratingSelectorElement.value);
        newOptionSelectorElement.removeAttribute("disabled");
        ratingSelectorElementMapItem.optionSelectorElement = newOptionSelectorElement;
    });
}
