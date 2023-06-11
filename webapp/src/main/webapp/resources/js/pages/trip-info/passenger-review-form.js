const ratingSelectorClass = "passenger-review-rating";
const optionSelectorClass = "passenger-review-option";
const commentTextAreaClass = "passenger-review-comment";
const optionSelectorContainerClass = "passenger-review-option-container";
const defaultRatingSelectorValue = 3;

const ratingSelectorElements = document.getElementsByClassName(ratingSelectorClass);
const optionSelectorElements = document.getElementsByClassName(optionSelectorClass);
const optionSelectorContainer = document.getElementsByClassName(optionSelectorContainerClass);
const commentTextAreaElements = document.getElementsByClassName(commentTextAreaClass);

const getOptionSelectorElement = (ratingSelectorElement, value) => {
    return Array.from(optionSelectorElements).find(optionSelectorElement =>
        optionSelectorElement.id === ratingSelectorElement.id + "-" + value
    );
}

const getOptionSelectorContainerElement = (ratingSelectorElement, value) => {
    return Array.from(optionSelectorContainer).find(optionSelectorContainerElement =>
        optionSelectorContainerElement.id === ratingSelectorElement.id + "-" + value + "-container"
    );
}

const getCommentTextAreaElement = (ratingSelectorElement) => {
    return Array.from(commentTextAreaElements).find(commentTextAreaElement =>
        commentTextAreaElement.id === ratingSelectorElement.id + "-comment"
    );
}

const ratingSelectorElementsMap = {};
Array.from(ratingSelectorElements).forEach(ratingSelectorElement => {
    ratingSelectorElementsMap[ratingSelectorElement.id] = {
        value: defaultRatingSelectorValue,
        optionSelectorElement: getOptionSelectorElement(ratingSelectorElement, defaultRatingSelectorValue),
        optionSelectorContainerElement: getOptionSelectorContainerElement(ratingSelectorElement, defaultRatingSelectorValue),
        commentTextAreaElement: getCommentTextAreaElement(ratingSelectorElement)
    }
});

for (const ratingSelectorElement of ratingSelectorElements) {
    ratingSelectorElement.addEventListener("change", () => {
        const ratingSelectorElementMapItem = ratingSelectorElementsMap[ratingSelectorElement.id];
        ratingSelectorElementMapItem.commentTextAreaElement.value = "";
        ratingSelectorElementMapItem.value = ratingSelectorElement.value;
        ratingSelectorElementMapItem.optionSelectorElement.setAttribute("disabled", "disabled");
        ratingSelectorElementMapItem.optionSelectorContainerElement.setAttribute("hidden", "hidden");
        const newOptionSelectorElement = getOptionSelectorElement(ratingSelectorElement, ratingSelectorElement.value);
        const newOptionSelectorContainerElement = getOptionSelectorContainerElement(ratingSelectorElement, ratingSelectorElement.value);
        newOptionSelectorElement.removeAttribute("disabled");
        newOptionSelectorContainerElement.removeAttribute("hidden");
        ratingSelectorElementMapItem.optionSelectorElement = newOptionSelectorElement;
        ratingSelectorElementMapItem.optionSelectorContainerElement = newOptionSelectorContainerElement;
    });
}
