using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace implodingRacoon.Models.Database.Entities
{
    public class SolicitudAmistad
    {
        [Key]
        public int Id { get; set; }

        public int UsuarioEnviaId { get; set; }
        public int UsuarioRecibeId { get; set; }

        [ForeignKey("UsuarioEnviaId")]
        public Usuario UsuarioEnvia { get; set; }

        [ForeignKey("UsuarioRecibeId")]
        public Usuario UsuarioRecibe { get; set; }
    }
}