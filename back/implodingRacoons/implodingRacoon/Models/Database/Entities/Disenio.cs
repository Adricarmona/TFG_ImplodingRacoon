using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class Diseno
    {
        [Key]
        public int Id { get; set; }
        public string Nombre { get; set; }
        public string Imagen { get; set; }
    }
}
