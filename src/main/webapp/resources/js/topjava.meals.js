$(function () {
    makeEditable({
            ajaxUrl: "ajax/profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "data": "dateTime"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }

                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function filteredData() {
    event.preventDefault();
    $.ajax({
        url: "ajax/profile/meals/filter",
        type: "GET",
        data: $("#filter").serialize()
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw()
    });
}

function resetFilter() {
    $("#filter").trigger("reset");
    $.get("ajax/profile/meals/", context.datatableApi.clear().rows.add(data).draw());
}