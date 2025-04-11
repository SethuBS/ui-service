var baseUrl = "http://localhost:8000";
var resourceLocation = "/ui-service/api/v1/book/";

// Show loader
function showLoader() {
  document.getElementById("loader").style.display = "block";
}

// Hide loader
function hideLoader() {
  document.getElementById("loader").style.display = "none";
}

$( document ).ready(function() {
    console.log("Ready...");
    getBooks();


    $('#btn-reset').click(function (e) {
        reset();
    });

    $('#add_button').click(function (e) {
        $('#btn-save').text("Save");
        $('#bookModalLabel').text("Add Book")
        //TO DO: reset();
    });

    $('#btn-save').click(function() {
        var textBtn=$(this).text();

        if(textBtn=='Save'){
            saveBook();
        } else {
            updateBook();
        }
    });

    $("#tblBook").on("click","tbody tr .btn-info", function (e) {
       $tr = $(this).closest('tr');
       var data = $tr.children("td").map(function () {
           return $(this).text();
       }).get();

       if(data[0] !=null){
           $('#bookId').val(data[0]);
           $('#book-name').val(data[1]);
           $('#publish-date').val(data[2]);
           $('#book-price').val(data[3]);
           $('#book-type').val(data[4]);
           $('#book-isbn-number').val(data[5]);
           $('#btn-save').text("Update");
           $('#bookModalLabel').text("Update Book")
       }
    });

    $("#tblBook").on("click","tbody tr .btn-danger", function (e) {
        $("#smallModal").modal();
        $tr = $(this).closest('tr');
        var data = $tr.children("td").map(function () {
            return $(this).text();
        }).get();

        if(data[0] !=null) {
            $('#bookId').val(data[0]);
        }

    });

    $('#myModal').on('hide.bs.modal', function () {
        reset();
        $('#btn-save').text("Save");
        $('#bookModalLabel').text("Save Book");
    });

    $(function () {
        $( "#publish-date" ).datepicker({
            dateFormat: 'dd/mm/yy'
        });
    });
});

function getBooks() {
    showLoader();
    $.ajax({
        url: baseUrl+resourceLocation,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var tableBody = $('#tblBook tbody');
            if(data.length != 0){
                tableBody.empty();
            }
            $(data).each(function (index, element) {
                tableBody.append('<tr><td>'+element.id+'</td>' +
                    '<td>'+element.name+'</td>' +
                    '<td>'+element.publishDate+'</td>' +
                    '<td>' + "R" + parseFloat(element.price) + '</td>' +
                    '<td>'+element.bookType+'</td>'+
                    '<td>'+element.isbnnumber+'</td>' +
                        '<td>'
                    + '<a  class="btn btn-info" data-toggle="modal" data-target="#myModal"><ion-icon name="pencil-outline"></ion-icon>Updated</a>'
                    +'<a   class="btn btn-danger ml-2"><ion-icon name="trash-outline"></ion-icon>Delete</a>'+
                '</td>'+'</tr>');
            })
            hideLoader();
        },
        error: function (data, error) {
        hideLoader();
            alert(error);
        }
    })

}

function saveBook() {
    var book = {};
    book.name = $('#book-name').val();
    book.publishDate =  $('#publish-date').val();
    book.price = parseFloat($('#book-price').val());
    book.bookType = $('#book-type').val();
    book.isbnnumber = $('#book-isbn-number').val();
    var bookObject = JSON.stringify(book);
    console.log(bookObject);
    $.ajax({
        url: baseUrl+resourceLocation,
        type: "POST",
        data: bookObject,
        contentType: 'application/json; charset=utf-8',
        success: function () {
            getBooks();
            reset();
            $("#myModal").modal('hide');
        },
        error: function (data, error) {
            alert(error);
        }
    })

}

function updateBook() {
    var book = {};
    var bookId = $('#bookId').val();
    book.id = bookId;
    book.name = $('#book-name').val();
    book.publishDate =  $('#publish-date').val();
    book.price = parseFloat($('#book-price').val());
    book.bookType = $('#book-type').val();
    book.isbnnumber = $('#book-isbn-number').val();
    var bookObject = JSON.stringify(book);
    console.log(bookObject);
    $.ajax({
        url: baseUrl+resourceLocation+bookId,
        type: "PUT",
        data: bookObject,
        contentType: 'application/json; charset=utf-8',
        success: function () {
            getBooks();
            $("#myModal").modal('hide');
        },
        error: function (data, error) {
            alert(error);
        }
    })

}

function deleteBook() {
    var bookId = $('#bookId').val();
    $.ajax({
        url:baseUrl+resourceLocation+bookId,
        type: 'DELETE',
        success: function () {
            $("#smallModal").modal('hide');
            getBooks();
        },
        error: function (data, error) {
            alert(error);
        }
    })
}

function reset() {
    $('#bookId').val('');
    $('#book-name').val('');
    $('#publish-date').val('');
    $('#book-price').val('');
    $('#book-type').val('');
    $('#book-isbn-number').val('');
}