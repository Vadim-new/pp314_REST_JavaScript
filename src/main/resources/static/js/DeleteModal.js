$('#delete').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showDeleteModal(id);
})

async function showDeleteModal(id) {
    let user = await getUser(id);
    let form = document.forms["formDeleteUser"];
    form.id.value = user.id;
    form.firstName.value = user.name;
    form.lastName.value = user.surname;
    form.userAge.value = user.age;
    form.userPhone.value = user.phoneNumber;
    form.userEmail.value = user.email;
    form.Username.value = user.userName;
    form.password.value = user.password;


    $('#deleteUserRoles').empty();

    await fetch("http://localhost:8081/api/roles")
        .then(res => res.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < user.roles.length; i++) {
                    if (user.roles[i].name === role.role) {
                        selectedRole = true;
                        break;
                    }
                }
                let el = document.createElement("option");
                el.text = role.role;
                el.value = role.id;
                if (selectedRole) el.selected = true;
                $('#deleteUserRoles')[0].appendChild(el);
            })
        });
}
async function getUser(id) {
    let url = "http://localhost:8081/api/users/" + id;
    let response = await fetch(url);
    return await response.json();
}