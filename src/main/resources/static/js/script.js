document.body.addEventListener("htmx:configRequest", function(event) {
    let token = localStorage.getItem("jwt");
    if (token) {
        event.detail.headers["Authorization"] = "Bearer " + token;
    }
});

document.body.addEventListener("htmx:afterRequest", function(event) {
    if (event.detail.xhr.status === 401) {
        alert("Session expired");
        window.location = "/login";
    }
});