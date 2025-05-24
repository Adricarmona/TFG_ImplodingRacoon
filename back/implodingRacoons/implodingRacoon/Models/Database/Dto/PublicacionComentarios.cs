using System.ComponentModel.DataAnnotations.Schema;
using implodingRacoon.Models.Database.Entities;

namespace implodingRacoon.Models.Database.Dto
{
    public class PublicacionComentarios
    {
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descripcion { get; set; }
        public DateTime Fecha { get; set; }
        public UserAmigos Usuario { get; set; }

        public ICollection<ComentarioSimple> Comentarios { get; set; } = new List<ComentarioSimple>();
    }
}
