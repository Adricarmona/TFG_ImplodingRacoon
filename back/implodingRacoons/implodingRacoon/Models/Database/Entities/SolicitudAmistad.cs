using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class SolicitudAmistad
    {
        [Key]
        public int Id { get; set; }
        public int UsuarioId { get; set; }
        public int UsuarioSolicitadoId { get; set; }

        [ForeignKey("UsuarioId")]
        public Usuario Usuario { get; set; }
        [ForeignKey("UsuarioSolicitadoId")]
        public Usuario UsuarioSolicitado { get; set; }
    }
}
