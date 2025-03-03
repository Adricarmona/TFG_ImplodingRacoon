using System.ComponentModel.DataAnnotations;

namespace implodingRacoon.Models.Database.Entities
{
    public class HistorialPartidas
    {
        [Key]
        public int Id { get; set; }
        public string Ganador { get; set; }
        public DateTime Fecha { get; set; }
    }
}
