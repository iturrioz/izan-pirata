;(function() {
  'use strict';

  var shipsLeft = 0;

  $(function() {

    initializeSelection(selectionParameters);
  });

  function initializeSelection(params) {
    for (var i=0;i<params.dimY;i++) {
      var row = $('<tr></tr>');
      for (var j=0;j<params.dimX;j++) {
        var col = $('<td></td>').html('&nbsp');
        row.append(col);
      }
      $('#selection-table').append(row);
    }

    $('#selection-table td').click(function () {
      if ($(this).hasClass('palette-concrete')) {
        shipsLeft++;
        $(this).removeClass('palette-concrete');
      } else if (shipsLeft > 0) {
        shipsLeft--;
        $(this).addClass('palette-concrete');
      }

      $('#ships-left').text(shipsLeft);
      $('#start-button').prop('disabled', shipsLeft>0);
    });

    $('#start-button').click(function () {
      var data = $('#selection-table td.palette-concrete').map(function (cell) {
        var row = $(this).parent().children().index(this);
        var parent = $(this).parent();
        var col = parent.parent().children().index(parent[0]);

        return {x: row, y: col};
      }).get();

      $.post('/create', data, function(data) {
        console.log('Sent table: ');
        console.log(data);
      });
    });

    shipsLeft = params.ships;
    $('#ships-left').text(shipsLeft);
  }

})();
