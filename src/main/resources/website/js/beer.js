function getSelectedProduct() {
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

function showHide(id) {
    let elem = document.getElementById(id);
    elem.style.display = (elem.style.display !== 'none') ? 'none' : 'block';
}