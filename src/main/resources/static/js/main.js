window.onload = function() {
    const createBookBtn = document.getElementById("createBookBtn");
    const addBuddyBtnJs = document.getElementById("addBuddyBtnJs");

    if (createBookBtn) createBookBtn.addEventListener("click", createAddressBook);
    if (addBuddyBtnJs) addBuddyBtnJs.addEventListener("click", addBuddy);

    loadAddressBooks();
};

function loadAddressBooks() {
    fetch("/api/addressbooks")
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("addressBookList");
            if (!list) return;
            list.innerHTML = "";

            data.forEach(book => {
                const li = document.createElement("li");
                const btn = document.createElement("button");
                btn.textContent = book.name || `AddressBook ${book.id}`;
                btn.onclick = () => viewAddressBook(book.id);
                li.appendChild(btn);
                list.appendChild(li);
            });
        });
}

function createAddressBook(event) {
    event.preventDefault();
    const name = document.getElementById("name").value.trim();
    if (!name) return;

    fetch("/api/addressbooks", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name })
    })
        .then(res => res.json())
        .then(book => {
            loadAddressBooks();
            viewAddressBook(book.id);
        });
}

function viewAddressBook(id) {
    fetch(`/api/addressbooks/${id}`)
        .then(res => res.json())
        .then(book => {
            const nameDisplay = document.getElementById("currentBookName");
            const currentBook = document.getElementById("currentBook");
            const buddyList = document.getElementById("buddyList");
            if (!buddyList || !currentBook || !nameDisplay) return;

            nameDisplay.textContent = book.name || `AddressBook ${book.id}`;
            currentBook.dataset.id = book.id;

            buddyList.innerHTML = "";
            (book.buddies || []).forEach(buddy => {
                const li = document.createElement("li");
                li.textContent = `${buddy.name} — ${buddy.number} — ${buddy.address}`;
                buddyList.appendChild(li);
            });
        });
}

function addBuddy() {
    const currentBook = document.getElementById("currentBook");
    const id = currentBook?.dataset.id;
    if (!id) return;

    const name = document.getElementById("buddyNameJs").value;
    const number = document.getElementById("buddyNumberJs").value;
    const address = document.getElementById("buddyAddressJs").value;

    fetch(`/api/addressbooks/${id}/buddies`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, number, address })
    })
        .then(res => res.json())
        .then(() => viewAddressBook(id));
}