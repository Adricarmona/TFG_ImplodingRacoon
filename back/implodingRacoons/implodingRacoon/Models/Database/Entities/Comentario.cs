using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class Comentario
    {
        [Key]
        public int Id { get; set; }
        public string Descripcion { get; set; }
        public DateTime Fecha { get; set; }

        public int PublicacionId { get; set; }
        [ForeignKey("PublicacionId")]
        public Publicacion Publicacion { get; set; }
    }
}
