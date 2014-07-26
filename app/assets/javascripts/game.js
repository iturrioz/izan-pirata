;(function() {
  'use strict';

  var starting = {
    idPartida: 0,
    dimX: 3,
    dimY: 3,

    jugadores: [
      {
        id: 1,
        rejilla: [
          ["AGUA", "AGUA_TOCADA", "AGUA"],
          ["AGUA", "AGUA", "AGUA"],
          ["AGUA", "AGUA", "AGUA"]
        ]
      },
      {
        id: 2,
        rejilla: [
          ["AGUA_TOCADA", "AGUA", "AGUA"],
          ["AGUA", "AGUA", "AGUA"],
          ["AGUA_TOCADA", "AGUA", "AGUA"]
        ]
      }
    ]
  };

  $(function() {

    initializeTable(starting);
  });

  function handleClick(row, col) {
    console.log("Click: "+row+","+col);
  }

  function initializeTable(data) {

    _.each(data.jugadores, function(jugador) {
      fillTable(jugador.rejilla, jugador.id != 1);
    });

    $('#enemy-table td').click(function() {

      var row = $(this).parent().children().index(this);
      var parent = $(this).parent();
      var col = parent.parent().children().index(parent[0]);

      handleClick(row, col);
    });
  }

  function fillTable(data, enemy) {

    _.each(data, function (fila) {
      var row = $('<tr></tr>');

      _.each(fila, function (columna) {
        var col = $('<td></td>').html('&nbsp');

        switch (columna) {
          case "AGUA":
            //col.addClass("");
            break;
          case "AGUA_TOCADA":
            col.addClass("palette-green-sea");
            break;
          case "BARCO":
            if (!enemy) {
              col.addClass("palette-concrete");
            }
            break;
          case "BARCO_TOCADO":
            col.addClass("palette-orange");
            break;
          case "BARCO_HUNDIDO":
            col.addClass("palette-alizarin");
            break;
          default:
        }

        row.append(col);
      });

      $(enemy ? '#enemy-table' : '#my-table').append(row);
    });
  }
})();
