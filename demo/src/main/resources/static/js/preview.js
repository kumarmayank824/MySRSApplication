    //This has been added to show preview.
	$( document ).on( 'click', '.preview-pdf', function (e) {
        e.preventDefault();
        e.stopPropagation();
        var url = $(this).data('pdfurl');
        bootbox.dialog({
	          message: '<object class="preview-pdf-file" style="width: 100%;height: 480px;" type="application/pdf" data="'+url+'">Oops! This is not working as expected</object>',      
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
          minHeight: 450,
          alsoResize: '.preview-pdf-modal .bootbox-body'
        });    
     });