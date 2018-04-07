$(document).ready(function (){
    
	var csrf_token = $('input[name="_csrf"]').attr('value');
	
	var table = $('#example').DataTable({
        ajax: '/to-apiAttachment',
    responsive: {
        details: {
            display: $.fn.dataTable.Responsive.display.modal({
                header: function (row) {
                    var data = row.data();
                        return '<div style="word-break: break-all;font-weight: 600;">Details for ' + data[1]+'</div>';
                    }
                }),
                renderer: $.fn.dataTable.Responsive.renderer.tableAll({
                tableClass: 'table'
                })
             }
        },
        columnDefs: [{
           "searchable": true,
           "orderable": false,
           "targets": 0
        },{
           "data": null,
           "defaultContent":'<b>CLICK SOMEWHERE OUTSIDE TO CLOSE IT </b>',
           "targets": 9
        },{
            render: function (data, type, full, meta) {
                return "<div class='text-wrap width-200'>" + data + "</div>";
            },
            targets: [3,4]
        },
        {
            render: function (data, type, full, meta) {
                return "<a style = 'text-decoration:none;' title='Click Here To Download'  href='/toAttachmentDownload/download/"+full[0]+"'><span class='glyphicon glyphicon-download-alt'></span> " + data + "</a>";
            },
            targets: 1
        },{
            render: function (data, type, full, meta) {
                return "<a style = 'text-decoration:none;' href='javascript:;' title='Click Here To Preview' class='preview-pdf' data-pdfurl='/toAttachmentDownload/preview/"+full[0]+"'><span class='glyphicon glyphicon-file'></span> "+full[1]+"</a>";
            },
            targets: 8
        },
        //hide the fourth & fifth column
        { 'visible': false, 'targets': [3,4] }
	    ]
    });
    
    $('table th').click();
    $('table th').click();
    
    table.on('order.dt search.dt', function () {
       table.column(0, { search: 'applied', order: 'applied' }).nodes().each(function (cell, i) {
          cell.innerHTML = i + 1;
       });
    }).draw();
    
    $( document ).on( 'click', '.preview-pdf', function (e) {
	        e.preventDefault();
	        e.stopPropagation();
	        var url = $(this).data('pdfurl');
	        bootbox.dialog({
		          message: '<object class="preview-pdf-file" style="width: 100%;height: 284px;" type="application/pdf" data="'+url+'">Oops! This is not working as expected</object>',      
		          title: "Preview PDF",
		          "className" : "preview-pdf-modal",
		          onEscape: function() {}
	        })
	
	        $('.preview-pdf-modal .modal-content')
	        .draggable({
	        cancel: ".preview-pdf-modal .preview-pdf-file"
	        })
	        .resizable({
	          minWidth: 350,
	          minHeight: 350,
	          alsoResize: '.preview-pdf-modal .bootbox-body'
	        });    
    })
    
});

