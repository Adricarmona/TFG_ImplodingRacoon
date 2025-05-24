namespace implodingRacoon.Models.Database.Dto
{
    public class publicarComentario
    {

        public string Comentario { get; set; }
        public DateTime Fecha { get; set; }
        public int PublicacionId { get; set; }
        public int UsuarioId { get; set; }
    }
}