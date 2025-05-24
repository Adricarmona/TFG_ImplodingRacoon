namespace implodingRacoon.Models.Database.Dto
{
    public class ComentarioSimple
    {
        public int Id { get; set; }
        public string Comentario { get; set; }
        public DateTime Fecha { get; set; }
        public string nombreUsuario { get; set; }
    }
}
