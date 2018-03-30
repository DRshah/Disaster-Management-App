
function tryLogin() {

    var email = $('#exampleInputEmail1').val();

    //console.log(email);

    var org = email.split('@')[1].split('.')[0];
    //console.log(orgName);
    localStorage.setItem("orgName", org);
    localStorage.setItem("disasterName", "Irma")

    //window.glob = email.split('@')[1].split('.')[0].toUpperCase();

    console.log(email.split('@')[1].split('.')[0], email.split('@')[0]);

    user = email.split('@')[0];
    localStorage.setItem("user", user);

    window.location.href = "orgPage.html";
}

