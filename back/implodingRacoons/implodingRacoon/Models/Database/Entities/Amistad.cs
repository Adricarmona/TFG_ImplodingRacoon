using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace implodingRacoon.Models.Database.Entities
{
    public class Amistad
    {
        public int UsuarioId { get; set; }
        public int AmigoId { get; set; }


        [ForeignKey("AmigoId")]
        public Usuario Amigo { get; set; }
    }
}
