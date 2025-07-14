document.addEventListener("htmx:configRequest", function(event) {
    let token = localStorage.getItem("jwt");
    if (token) {
        event.detail.headers["Authorization"] = "Bearer " + token;
    }
});

document.addEventListener("htmx:afterRequest", function(event) {
    if (event.detail.xhr.status === 401) {
        alert("Session expired");
        window.location = "/login";
    }
});

document.addEventListener("htmx:afterRequest", function(evt) {
    console.log(evt.detail.requestConfig.path);
    if (evt.detail.requestConfig.path === "/login") {
        const xhr = evt.detail.xhr;
        try {
            const json = JSON.parse(xhr.responseText);
            console.log(json);
            if (json.token) {
                localStorage.setItem("jwt", json.token);
                htmx.ajax('GET', '/dashboard', {
                    headers: {
                        'Authorization': 'Bearer ' + localStorage.getItem('jwt')
                    },
                    target: '#meta',
                    swap: 'innerHTML'
                });
            }
        } catch (e) {
            document.getElementById("login-result").innerText = xhr.responseText;
        }
    }
});