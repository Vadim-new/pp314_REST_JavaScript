$(async function () {
    await thisUser();
});

async function thisUser() {
    fetch("/api/viewUser")
        .then(res => res.json())
        .then(data => {
            // Добавляем информацию в шапку
            $('#headerUsername').append(data.email);
            let roles = data.roles.map(role => " " + role.role);
            $('#headerRoles').append(roles);

            //Добавляем информацию в таблицу
            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.surname}</td>                                               
                <td>${data.age}</td>
                <td>${data.phoneNumber}</td>
                <td>${data.email}</td>
                <td>${data.userName}</td>
                <td>${data.password}</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}
