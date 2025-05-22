namespace implodingRacoon.Models.Database.Dto
{
    public class PublicacionDto
    {
        public int Id { get; set; }
        public string Titulo { get; set; }
        public string Descripcion { get; set; }
        public DateTime Fecha { get; set; }
        public int UsuarioId { get; set; }
        public UserPerfil Usuario { get; set; }
    }
}
