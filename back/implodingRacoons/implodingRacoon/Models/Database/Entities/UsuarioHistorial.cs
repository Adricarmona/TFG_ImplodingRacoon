using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace implodingRacoon.Models.Database.Entities
{
    public class UsuarioHistorial
    {
        [Key]  
        public int Id { get; set; }

        public int UsuarioId { get; set; }
        public int PartidaId { get; set; }

        [ForeignKey("UsuarioId")]
        public Usuario Usuario { get; set; }

        [ForeignKey("PartidaId")]
        public HistorialPartidas Partida { get; set; }
    }
}
