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
                btn.textContent = `AddressBook ${book.id}`;
                btn.onclick = () => viewAddressBook(book.id);
                li.appendChild(btn);
                list.appendChild(li);
            });
        });
}

function createAddressBook(event) {
    event.preventDefault(); // prevent form reload
    fetch("/api/addressbooks", { method: "POST" })
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
            const idDisplay = document.getElementById("currentBookId");
            const buddyList = document.getElementById("buddyList");
            if (!buddyList) return;

            idDisplay.textContent = book.id;
            buddyList.innerHTML = "";

            book.buddies.forEach(buddy => {
                const li = document.createElement("li");
                li.textContent = `${buddy.name} — ${buddy.number}`;
                buddyList.appendChild(li);
            });
        });
}

function addBuddy() {
    const id = document.getElementById("currentBookId").textContent;
    if (id === "None") return;

    const name = document.getElementById("buddyNameJs").value;
    const number = document.getElementById("buddyNumberJs").value;
    const address = document.getElementById("buddyAddressJs").value;

    const buddy = { name, number, address };

    fetch(`/api/addressbooks/${id}/buddies`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(buddy)
    })
        .then(res => res.json())
        .then(() => viewAddressBook(id));
}
