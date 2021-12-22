function appendSelectedProduct() {
    let tag = document.createElement('label');
    let sel = document.getElementById('beer_selection');
    let txt = document.createTextNode(sel.options[sel.selectedIndex].text);
    let elem = document.getElementById('typePara');

    tag.appendChild(txt);
    elem.appendChild(tag);
}

function resetProductionPage() {
    let type = document.getElementById('typePara');
    type.removeChild(type.lastChild);
}

function getSelectedBeerType() {
    let sel = document.getElementById('beer_selection');

    return document.createTextNode(sel.options[sel.selectedIndex].text);
}

function executeRefill() {

    return null;
}