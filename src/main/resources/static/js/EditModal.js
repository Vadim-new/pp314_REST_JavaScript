$('#edit').on('show.bs.modal', ev => {
    let button = $(ev.relatedTarget);
    let id = button.data('id');
    showEditModal(id);
})

async function showEditModal(id) {
    let user = await getUser(id);
    let form = document.forms["formEditUser"];
    form.id.value = user.id;
    form.firstName.value = user.name;
    form.lastName.value = user.surname;
    form.userAge.value = user.age;
    form.userPhone.value = user.phoneNumber;
    form.userEmail.value = user.email;
    form.Username.value = user.userName;
    form.password.value = user.password;


    $('#editUserRoles').empty();

    await fetch("/api/roles")
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
               $('#editUserRoles')[0].appendChild(el);
            })
        })
}