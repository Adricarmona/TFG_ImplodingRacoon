using System.ComponentModel.DataAnnotations.Schema;

namespace implodingRacoon.Models.Database.Entities
{
    public class UsuarioHistorial
    {
        public int UsuarioId { get; set; }
        [ForeignKey("UsuarioId")]
        public Usuario Usuario { get; set; }

        public int HistorialPartidaId { get; set; }
        [ForeignKey("HistorialPartidaId")]
        public HistorialPartidas HistorialPartida { get; set; }
    }

}
