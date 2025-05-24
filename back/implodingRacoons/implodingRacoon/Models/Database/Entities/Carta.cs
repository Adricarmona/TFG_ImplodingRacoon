using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class Carta
    {
        [Key]
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descripcion { get; set; }
        public string Tipo { get; set; }

        public ICollection<Diseno> Disenos { get; set; } = new List<Diseno>();
    }
}
